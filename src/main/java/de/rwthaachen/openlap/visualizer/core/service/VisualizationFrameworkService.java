package de.rwthaachen.openlap.visualizer.core.service;

import DataSet.OLAPPortConfiguration;
import de.rwthaachen.openlap.visualizer.core.dao.DataTransformerMethodRepository;
import de.rwthaachen.openlap.visualizer.core.dao.VisualizationFrameworkRepository;
import de.rwthaachen.openlap.visualizer.core.dao.VisualizationMethodRepository;
import de.rwthaachen.openlap.visualizer.core.dtos.VisualizationMethodConfiguration;
import de.rwthaachen.openlap.visualizer.core.exceptions.*;
import de.rwthaachen.openlap.visualizer.core.framework.factory.VisualizationCodeGeneratorFactory;
import de.rwthaachen.openlap.visualizer.core.framework.factory.VisualizationCodeGeneratorFactoryImpl;
import de.rwthaachen.openlap.visualizer.core.framework.validators.VisualizationFrameworksUploadValidator;
import de.rwthaachen.openlap.visualizer.core.model.DataTransformerMethod;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationMethod;
import de.rwthaachen.openlap.visualizer.framework.VisualizationCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A service which provides functions to perform CRUD operations on the Visualization Frameworks or Visualization Methods. In addition, also methods to get information about the stored
 * frameworks
 *
 * @author Bassim Bashir
 */
@Service
public class VisualizationFrameworkService {

    @Autowired
    private VisualizationFrameworkRepository visualizationFrameworkRepository;

    @Autowired
    private VisualizationMethodRepository visualizationMethodRepository;

    @Autowired
    private DataTransformerMethodRepository dataTransformerMethodRepository;

    @Autowired
    private FileManager fileManager;

    /**
     * @return The list of VisualizationFrameworks existing in the system
     */
    public List<VisualizationFramework> findAllVisualizationFrameworks() {
        List<VisualizationFramework> visualizationFrameworkList = new ArrayList<>();
        visualizationFrameworkRepository.findAll().forEach(visualizationFrameworkList::add);
        return visualizationFrameworkList;
    }

    /**
     * @param visualizationFrameworkId The id of the VisualizationFramework to retrieve
     * @return The VisualizationFramework represented by the provided id
     * @throws VisualizationFrameworkNotFoundException when the framework was not found
     */
    public VisualizationFramework findVisualizationFrameworkById(long visualizationFrameworkId) throws VisualizationFrameworkNotFoundException {
        if (!visualizationFrameworkRepository.exists(visualizationFrameworkId))
            throw new VisualizationFrameworkNotFoundException("The framework with id: " + visualizationFrameworkId + " does not exist.");

        return visualizationFrameworkRepository.findOne(visualizationFrameworkId);
    }

    /**
     * @param visualizationMethodId The id of the VisualizationMethod to retrieve
     * @return The VisualizationMethod represented by the provided id
     * @throws VisualizationMethodNotFoundException when the method was not found
     */
    public VisualizationMethod findVisualizationMethodById(long visualizationMethodId) throws VisualizationMethodNotFoundException {
        if (!visualizationMethodRepository.exists(visualizationMethodId))
            throw new VisualizationMethodNotFoundException("The method with id: " + visualizationMethodId + " does not exist.");

        return visualizationMethodRepository.findOne(visualizationMethodId);
    }

    /**
     * Performs the upload of the visualization framework by copying over the jar bundle and making the relevant Database entries
     *
     * @param frameworkList The configuration of the frameworks in the provided jar file
     * @param jarFile       The jar bundle which contains the package framework implementation
     * @throws VisualizationFrameworkUploadException If the validation of the provided configuration failed or copying the provided jar file was not successful
     */
    public void uploadVisualizationFrameworks(List<VisualizationFramework> frameworkList, MultipartFile jarFile) throws VisualizationFrameworkUploadException {
        //validate the jar classes first before passing it on to the File Manager
        VisualizationFrameworksUploadValidator visualizationFrameworksUploadValidator = new VisualizationFrameworksUploadValidator();
        if (visualizationFrameworksUploadValidator.validateVisualizationFrameworksUploadConfiguration(frameworkList, jarFile)) {
            //if the configuration is valid then perform the upload, i.e db entries and copying of the jar
            // first copy the file over
            try {
                Path fileName = Paths.get(jarFile.getOriginalFilename()).getFileName();
                String savedFilePath;
                if (fileName != null) {
                    savedFilePath = fileManager.saveJarFile(fileName.toString(), jarFile);
                } else {
                    savedFilePath = fileManager.saveJarFile("", jarFile);
                }
                // set the file location in all the framework objects
                frameworkList.forEach(framework -> framework.setFrameworkLocation(savedFilePath));
                // set the framework in each of the methods as it is a bidirectional relationship
                frameworkList.forEach(framework -> framework.getVisualizationMethods().forEach(method -> method.setVisualizationFramework(framework)));
                visualizationFrameworkRepository.save(frameworkList);
            } catch (FileManagerException fileManagerException) {
                throw new VisualizationFrameworkUploadException(fileManagerException.getMessage());
            }
        } else {
            throw new VisualizationFrameworkUploadException("Upload configuration is not correct");
        }
    }

    /**
     * Removes a previously added VisualizationFramework from the system. This includes all the Database entries alongwith the
     * stored JAR (if no other framework is referencing it)
     *
     * @param frameworkId The id of the VisualizationFramework to delete. The function will first try
     *                    to parse the parameter into an id if that doesn't then to treat it as a framework name
     * @return true if the deletion of the VisualizationFramework was successful
     * @throws VisualizationFrameworkDeletionException if the deletion of the VisualizationFramework encountered problems such as the file couldn't be removed
     */
    @Transactional(rollbackFor = {RuntimeException.class})
    public boolean deleteVisualizationFramework(String frameworkId) throws VisualizationFrameworkDeletionException {
        if (frameworkId == null || frameworkId.isEmpty())
            throw new VisualizationFrameworkDeletionException("The id of the framework to delete should be null or empty string");
        try {
            long idOfFramework = Long.parseLong(frameworkId);
            //first load the framework to get the jar location
            VisualizationFramework visualizationFramework = visualizationFrameworkRepository.findOne(idOfFramework);
            String frameworkLocation = visualizationFramework.getFrameworkLocation();
            if (visualizationFramework == null)
                throw new VisualizationFrameworkDeletionException("Could not delete the framework with id: " + frameworkId + ", not found");
            // remove all the database entries
            visualizationFramework.getVisualizationMethods().forEach(visualizationMethod -> {
                //for each of the visualization method, delete the data transformer that it points to
                dataTransformerMethodRepository.delete(visualizationMethod.getDataTransformerMethod().getId());
            });
            visualizationFrameworkRepository.delete(idOfFramework);
            //finally delete the jar, if no other framework references it
            List<VisualizationFramework> frameworksReferenced = findAllVisualizationFrameworks().stream()
                    .filter((framework) -> framework.getFrameworkLocation().equals((frameworkLocation)))
                    .collect(Collectors.toList());
            if (frameworksReferenced.size() > 0) {
                try {
                    fileManager.deleteFile(visualizationFramework.getFrameworkLocation());
                } catch (FileManagerException fileManagerException) {
                    throw new VisualizationFrameworkDeletionException(fileManagerException.getMessage());
                }
            }

            if (visualizationFrameworkRepository.exists(idOfFramework))
                return false;
            else
                return true;
        } catch (NumberFormatException numberFormatException) {
            throw new VisualizationFrameworkDeletionException("The id: " + frameworkId + " is not parseable, should be a long value");
        }
    }

    /**
     * Updates the attributes of a VisualizationMethod
     *
     * @param newAttributes An instance filled with the new values of the visualization method. Except for the visualization framework, the method id and the data transformer id
     *                      all other attributes can be updated
     * @param idOfMethod    the id of the VisualizationMethod to be updated
     * @return VisualizationMethod the updated object of the VisualizationMethod
     * @throws VisualizationMethodNotFoundException if the VisualizationMethod to update was not found
     */
    public VisualizationMethod updateVisualizationMethodAttributes(VisualizationMethod newAttributes, long idOfMethod) throws VisualizationMethodNotFoundException {
        if (!visualizationMethodRepository.exists(idOfMethod))
            throw new VisualizationMethodNotFoundException("The VisualizationMethod with the id: " + idOfMethod + " does not exist!");

        VisualizationMethod visualizationMethod = visualizationMethodRepository.findOne(idOfMethod);
        // update the name of the method
        if (newAttributes.getName() != null && !newAttributes.getName().isEmpty())
            visualizationMethod.setName(newAttributes.getName());

        // update the implementing class
        if (newAttributes.getImplementingClass() != null && !newAttributes.getImplementingClass().isEmpty())
            visualizationMethod.setImplementingClass(newAttributes.getImplementingClass());

        if (newAttributes.getDataTransformerMethod() != null) {
            if (dataTransformerMethodRepository.exists(newAttributes.getDataTransformerMethod().getId())) {
                DataTransformerMethod newDataTransformerMethod = dataTransformerMethodRepository.findOne(newAttributes.getDataTransformerMethod().getId());
                //finally set the data transformer method
                visualizationMethod.setDataTransformerMethod(newDataTransformerMethod);
            }
        }
        //commit the changes
        return visualizationMethodRepository.save(visualizationMethod);
    }

    /**
     * Updates the attributes of a VisualizationFramework
     *
     * @param newAttributes An instance filled with the new values of the visualization framework. Only the attributes namely, description and uploadedBy can be
     *                      updated
     * @param idOfFramework the id of the VisualizationFramework to be updated
     * @return The updated VisualizationFramework object
     * @throws VisualizationFrameworkNotFoundException If the VisualizationFramework to update was not found
     */
    public VisualizationFramework updateVisualizationFrameworkAttributes(VisualizationFramework newAttributes, long idOfFramework) throws VisualizationFrameworkNotFoundException {
        if (!visualizationFrameworkRepository.exists(idOfFramework))
            throw new VisualizationFrameworkNotFoundException("The framework with id: " + idOfFramework + " does not exist!");

        VisualizationFramework visualizationFramework = visualizationFrameworkRepository.findOne(idOfFramework);

        if (newAttributes.getDescription() != null && !newAttributes.getDescription().isEmpty())
            visualizationFramework.setDescription(newAttributes.getDescription());
        if (newAttributes.getCreator() != null && !newAttributes.getCreator().isEmpty())
            visualizationFramework.setCreator(newAttributes.getCreator());

        return visualizationFrameworkRepository.save(visualizationFramework);
    }

    /**
     * Validates the configuration of the VisualizationMethod (i.e. the inputs that it accepts) with the provided OLAPPortConfiguration.
     *
     * @param visualizationMethodId The id of the VisualizationMethod for which to validate the configuration
     * @param olapPortConfiguration The OLAPPortConfiguration against which to validate the method configuration
     * @return true if the the provided port configuration matches the configuration of the VisualizationMethod
     * @throws DataSetValidationException If the validation encountered an error
     */
    public boolean validateVisualizationMethodConfiguration(long visualizationMethodId, OLAPPortConfiguration olapPortConfiguration) throws DataSetValidationException {
        VisualizationMethod visualizationMethod = visualizationMethodRepository.findOne(visualizationMethodId);
        if (visualizationMethod != null) {
            //ask the factories for the instance
            VisualizationCodeGeneratorFactory visualizationCodeGeneratorFactory = new VisualizationCodeGeneratorFactoryImpl(visualizationMethod.getVisualizationFramework().getFrameworkLocation());
            VisualizationCodeGenerator codeGenerator = visualizationCodeGeneratorFactory.createVisualizationCodeGenerator(visualizationMethod.getImplementingClass());
            return codeGenerator.isDataProcessable(olapPortConfiguration);
        } else {
            throw new DataSetValidationException("The visualization method represented by the id: " + visualizationMethodId + " not found.");
        }
    }

    /**
     * Gets the configuration of a VisualizationMethod
     *
     * @param visualizationMethodId The id of the VisualizationMethod for which to get the configuration
     * @return The VisualizationMethodConfiguration instance
     * @throws VisualizationMethodNotFoundException if the VisualizationMethod was not found
     */
    public VisualizationMethodConfiguration getMethodConfiguration(long visualizationMethodId) throws VisualizationMethodNotFoundException {
        if (!visualizationMethodRepository.exists(visualizationMethodId))
            throw new VisualizationMethodNotFoundException("The visualization method with the id : " + visualizationMethodId + " does not exist.");

        VisualizationMethod visualizationMethod = visualizationMethodRepository.findOne(visualizationMethodId);
        //ask the factories for the instance
        VisualizationCodeGeneratorFactory visualizationCodeGeneratorFactory = new VisualizationCodeGeneratorFactoryImpl(visualizationMethod.getVisualizationFramework().getFrameworkLocation());
        VisualizationCodeGenerator codeGenerator = visualizationCodeGeneratorFactory.createVisualizationCodeGenerator(visualizationMethod.getImplementingClass());
        VisualizationMethodConfiguration visualizationMethodConfiguration = new VisualizationMethodConfiguration();
        visualizationMethodConfiguration.setInput(codeGenerator.getInput());
        visualizationMethodConfiguration.setOutput(codeGenerator.getOutput());
        return visualizationMethodConfiguration;
    }
}
