package de.rwthaachen.openlap.visualizer.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.dataset.OpenLAPPortConfig;
import de.rwthaachen.openlap.visualizer.OpenLAPVisualizerApplication;
import de.rwthaachen.openlap.visualizer.core.dao.DataTransformerMethodRepository;
import de.rwthaachen.openlap.visualizer.core.dao.VisualizationFrameworkRepository;
import de.rwthaachen.openlap.visualizer.core.dao.VisualizationMethodRepository;
import de.rwthaachen.openlap.visualizer.core.dao.VisualizationSuggestionRepository;
import de.rwthaachen.openlap.visualizer.core.dtos.VisualizationMethodConfiguration;
import de.rwthaachen.openlap.visualizer.core.exceptions.*;
import de.rwthaachen.openlap.visualizer.core.exceptions.DataSetValidationException;
import de.rwthaachen.openlap.visualizer.core.framework.factory.VisualizationCodeGeneratorFactory;
import de.rwthaachen.openlap.visualizer.core.framework.factory.VisualizationCodeGeneratorFactoryImpl;
import de.rwthaachen.openlap.visualizer.core.framework.validators.VisualizationFrameworksUploadValidator;
import de.rwthaachen.openlap.visualizer.core.model.DataTransformerMethod;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationMethod;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationSuggestion;
import de.rwthaachen.openlap.visualizer.framework.DataTransformer;
import de.rwthaachen.openlap.visualizer.framework.VisualizationCodeGenerator;
import de.rwthaachen.openlap.visualizer.framework.exceptions.*;
import de.rwthaachen.openlap.visualizer.framework.exceptions.UnTransformableData;
import de.rwthaachen.openlap.visualizer.framework.model.TransformedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A service which provides functions to perform CRUD operations on the Visualization Frameworks or Visualization Methods. In addition, also methods to get information about the stored
 * frameworks
 *
 * @author Bassim Bashir
 */
@Service
public class VisualizationFrameworkService {

    private static final Logger log =
            LoggerFactory.getLogger(OpenLAPVisualizerApplication.class);
    @Autowired
    private VisualizationFrameworkRepository visualizationFrameworkRepository;
    @Autowired
    private VisualizationMethodRepository visualizationMethodRepository;
    @Autowired
    private DataTransformerMethodRepository dataTransformerMethodRepository;
    @Autowired
    private VisualizationSuggestionRepository visualizationSuggestionRepository;
    @Autowired
    private FileManager fileManager;

    //private VisualizationCodeGeneratorFactory visualizationCodeGeneratorFactory;

    private ObjectMapper objectMapper;

    @PostConstruct
    public void initializeVisualizationFrameworkService() {
        objectMapper = new ObjectMapper();
    }

    /**
     * @return The list of VisualizationFrameworks existing in the system
     */
    public List<VisualizationFramework> findAllVisualizationFrameworks() {
        List<VisualizationFramework> visualizationFrameworkList = new ArrayList<>();
        visualizationFrameworkRepository.findAllByOrderByNameAsc().forEach(visualizationFrameworkList::add);
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

    public String getFrameworkScript(Long idOfFramework, Long idOfMethod) {
        VisualizationMethod visualizationMethod = visualizationMethodRepository.findOne(idOfMethod);
        if (visualizationMethod != null) {
            VisualizationCodeGeneratorFactory visualizationCodeGeneratorFactory = new VisualizationCodeGeneratorFactoryImpl(visualizationMethod.getVisualizationFramework().getFrameworkLocation());
            VisualizationCodeGenerator codeGenerator = visualizationCodeGeneratorFactory.createVisualizationCodeGenerator(visualizationMethod.getImplementingClass());
            return encodeURIComponent(codeGenerator.getVisualizationLibraryScript());
        } else {
            throw new DataSetValidationException("The visualization method represented by the id: " + idOfMethod + " not found.");
        }
    }

    /**
     * Performs the upload of the visualization framework by copying over the jar bundle and making the relevant Database entries
     *
     * @param frameworkList The configuration of the frameworks in the provided jar file
     * @param jarFile       The jar bundle which contains the package framework implementation
     * @throws VisualizationFrameworkUploadException If the validation of the provided configuration failed or copying the provided jar file was not successful
     */
    @Transactional(rollbackFor = {RuntimeException.class})
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
                    if (!fileManager.fileExists(fileName.toString()))
                        savedFilePath = fileManager.saveJarFile(fileName.toString(), jarFile);
                    else
                        throw new VisualizationFrameworkUploadException("The file being uploaded : " + fileName.toString() + "  already exists.");
                } else {
                    savedFilePath = fileManager.saveJarFile("", jarFile);
                }
                //second save the visualization frameworks being uploaded
                // set the file location in all the framework objects
                frameworkList.forEach(framework -> framework.setFrameworkLocation(savedFilePath));
                // set the framework in each of the methods as it is a bidirectional relationship
                frameworkList.forEach(framework -> framework.getVisualizationMethods().forEach(method -> method.setVisualizationFramework(framework)));


                //Validating all the values before starting to save in the database.
                String validateFrameworkException = "";
                for (VisualizationFramework framework : frameworkList) {
                    VisualizationFramework availableFramework = visualizationFrameworkRepository.findByName(framework.getName());
                    if(availableFramework != null)
                        validateFrameworkException += ";The visualization Framework with name '" + framework.getName() + "'  already exists.";

                    List<DataTransformerMethod> dataTransformerMethodList = new ArrayList<DataTransformerMethod>();

                    for (VisualizationMethod method : framework.getVisualizationMethods()) {

                        DataTransformerMethod newDataTransformer = new DataTransformerMethod();
                        newDataTransformer.setName(method.getDataTransformerMethod().getName());
                        newDataTransformer.setImplementingClass(method.getDataTransformerMethod().getImplementingClass());

                        //Optional oldDataTransformer = dataTransformerMethodList.stream().filter(o -> o.getName().equals(newDataTransformer.getName()) && o.getImplementingClass().equals(newDataTransformer.getImplementingClass())).findFirst();
                        Optional oldDataTransformer = dataTransformerMethodList.stream().filter(o -> o.getImplementingClass().equals(newDataTransformer.getImplementingClass())).findFirst();

                        if (!oldDataTransformer.isPresent()) {
                            DataTransformerMethod classDataTransformer = dataTransformerMethodRepository.findByImplementingClass(method.getDataTransformerMethod().getImplementingClass());
                            if(classDataTransformer != null)
                                validateFrameworkException += ";The Data Transformer with implementing class name '" + method.getDataTransformerMethod().getImplementingClass() + "'  already exists.";

                        }

                        VisualizationMethod classVisualizationMethod = visualizationMethodRepository.findByImplementingClass(method.getImplementingClass());
                        if(classVisualizationMethod != null)
                            validateFrameworkException += ";The Visualization Type with implementing class name '" + method.getImplementingClass() + "'  already exists.";
                    }
                }




                //commenting out this line since it is not able to support new visualization framework which have multiple visualization methods using the same data transformer class
                //visualizationFrameworkRepository.save(frameworkList);

                if(validateFrameworkException == null || validateFrameworkException.isEmpty()) {
                    //Manually saving the framework, related methods and data transformers
                    for (VisualizationFramework framework : frameworkList) {
                        VisualizationFramework newFramework = new VisualizationFramework();
                        newFramework.setName(framework.getName());
                        newFramework.setCreator(framework.getCreator());
                        newFramework.setDescription(framework.getDescription());
                        newFramework.setFrameworkLocation(framework.getFrameworkLocation());

                        VisualizationFramework savedFramework = visualizationFrameworkRepository.save(newFramework);

                        List<DataTransformerMethod> dataTransformerMethodList = new ArrayList<DataTransformerMethod>();

                        for (VisualizationMethod method : framework.getVisualizationMethods()) {

                            DataTransformerMethod newDataTransformer = new DataTransformerMethod();
                            newDataTransformer.setName(method.getDataTransformerMethod().getName());
                            newDataTransformer.setImplementingClass(method.getDataTransformerMethod().getImplementingClass());

                            //Optional oldDataTransformer = dataTransformerMethodList.stream().filter(o -> o.getName().equals(newDataTransformer.getName()) && o.getImplementingClass().equals(newDataTransformer.getImplementingClass())).findFirst();
                            Optional oldDataTransformer = dataTransformerMethodList.stream().filter(o -> o.getImplementingClass().equals(newDataTransformer.getImplementingClass())).findFirst();

                            DataTransformerMethod savedDataTransformer;

                            if (oldDataTransformer.isPresent()) {
                                savedDataTransformer = (DataTransformerMethod) oldDataTransformer.get();
                            } else {
                                savedDataTransformer = dataTransformerMethodRepository.save(newDataTransformer);
                                dataTransformerMethodList.add(savedDataTransformer);
                            }

                            VisualizationMethod newMethod = new VisualizationMethod();
                            newMethod.setName(method.getName());
                            newMethod.setImplementingClass(method.getImplementingClass());
                            newMethod.setDataTransformerMethod(savedDataTransformer);
                            newMethod.setVisualizationFramework(savedFramework);

                            VisualizationMethod savedMethod = visualizationMethodRepository.save(newMethod);
                        }
                    }
                }
                else {
                    if (fileManager.fileExists(fileName.toString()))
                        fileManager.deleteJarFile(fileName.toString());
                    throw new VisualizationFrameworkUploadException(validateFrameworkException.substring(1));
                }

                //TODO this needs to be fixed since its giving error
                //third create the visualization suggestion entries for all the methods
                //add the input datasets of the uploaded visualization code generators, for automatic suggestions
                VisualizationCodeGeneratorFactory visualizationCodeGeneratorFactory = new VisualizationCodeGeneratorFactoryImpl(jarFile.getInputStream());
                List<VisualizationSuggestion> visualizationSuggestions = new ArrayList<>();
/*
                frameworkList.forEach(framework -> {
                    framework.getVisualizationMethods().forEach(visualizationMethod -> {
                        try {
                            // serialize and de-serialize the DataSet to avoid the issue with the ClassCastException, because the VisualizationCodeGenerator is loaded in another class loader
                            //and the OpenLAPDataSet in this piece code is loaded in another.
                            VisualizationCodeGenerator codeGenerator = visualizationCodeGeneratorFactory.createVisualizationCodeGenerator(visualizationMethod.getImplementingClass());
                            OpenLAPDataSet inputDataSet = null;
                            try {
                                inputDataSet = objectMapper.readValue(codeGenerator.getInputAsJsonString(), OpenLAPDataSet.class);
                            } catch (IOException ex) {
                                log.error("Error in deserializing codegenerator input config.Not adding method:" + visualizationMethod.getName() + ", to suggestions", ex);
                            }
                            if (inputDataSet != null && inputDataSet.getColumnsConfigurationData().size() != 0) {
                                VisualizationSuggestion visualizationSuggestion = new VisualizationSuggestion();
                                visualizationSuggestion.setVisualizationMethod(visualizationMethod);
                                visualizationSuggestion.setOlapDataSetConfiguration(objectMapper.writeValueAsString(inputDataSet));
                                visualizationSuggestions.add(visualizationSuggestion);
                            }
                        } catch (JsonProcessingException jsonProcessingException) {
                            //log the error and move on to the next
                            log.error("Suggestion for method with id : " + visualizationMethod.getId() + " ,not added.", jsonProcessingException);
                        }
                    });
                });*/
                //save all the suggestions in the database
                //visualizationSuggestionRepository.save(visualizationSuggestions);
            } catch (FileManagerException | IOException exception) {
                throw new VisualizationFrameworkUploadException(exception.getMessage());
            }
        } else {
            throw new VisualizationFrameworkUploadException("Upload configuration is not correct");
        }
    }

    /**
     * Removes a previously added VisualizationFramework from the system. This includes all the Database entries alongwith the
     * stored JAR (if no other framework is referencing it). DataTransformers will not be deleted as other VisualizationMethods in other frameworks might reference them
     *
     * @param idOfFramework The id of the VisualizationFramework to delete.
     * @return true if the deletion of the VisualizationFramework was successful
     * @throws VisualizationFrameworkDeletionException if the deletion of the VisualizationFramework encountered problems such as the file couldn't be removed
     */
    @Transactional(rollbackFor = {RuntimeException.class})
    public boolean deleteVisualizationFramework(long idOfFramework) throws VisualizationFrameworkDeletionException {
        if (!visualizationFrameworkRepository.exists(idOfFramework))
            throw new VisualizationFrameworkDeletionException("Could not delete the framework with id: " + idOfFramework + ", not found");
        //first load the framework to get the jar location
        VisualizationFramework visualizationFramework = visualizationFrameworkRepository.findOne(idOfFramework);
        String frameworkLocation = visualizationFramework.getFrameworkLocation();
        // remove all the database entries
        //get rid of the suggestions
        visualizationFramework.getVisualizationMethods().forEach(visualizationMethod -> {
            visualizationSuggestionRepository.delete(visualizationSuggestionRepository.findByVisualizationMethod(visualizationMethod));
        });
        //finally delete the vis framework and its methods
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

        return !visualizationFrameworkRepository.exists(idOfFramework);
    }

    /**
     * Removes a previously added VisualizationMethod from the system along with all the VisualizationSuggestions which refer to it.
     * DataTransformers will not be deleted as other VisualizationMethods might reference them
     *
     * @param idOfMethod The id of the VisualizationMethod to delete.
     * @return true if the deletion of the VisualizationMethod was successful
     * @throws VisualizationMethodDeletionException if the deletion of the VisualizationMethod encountered problems such as the file couldn't be removed
     */
    @Transactional(rollbackFor = {RuntimeException.class})
    public boolean deleteVisualizationMethod(long idOfMethod) throws VisualizationMethodDeletionException {
        if (!visualizationMethodRepository.exists(idOfMethod))
            throw new VisualizationMethodDeletionException("Could not delete the method with id: " + idOfMethod + ", not found");

        //first load the method
        VisualizationMethod visualizationMethod = visualizationMethodRepository.findOne(idOfMethod);
        //get rid of the suggestions
        visualizationSuggestionRepository.delete(visualizationSuggestionRepository.findByVisualizationMethod(visualizationMethod));
        //finally delete the vis method
        visualizationMethodRepository.delete(idOfMethod);

        return !visualizationMethodRepository.exists(idOfMethod);
    }

    /**
     * Removes a previously added DataTransformer from the system if no VisualizationMethod references it
     *
     * @param idOfTransformer The id of the DataTransformer to delete.
     * @return true if the deletion of the DataTransformer was successful
     * @throws DataTransformerDeletionException if the deletion of the DataTransformer encountered problems
     */
    @Transactional(rollbackFor = {RuntimeException.class})
    public boolean deleteDataTransformer(long idOfTransformer) throws DataTransformerDeletionException {
        if (!dataTransformerMethodRepository.exists(idOfTransformer))
            throw new DataTransformerDeletionException("Could not delete the data transformer with id: " + idOfTransformer + ", not found");

        dataTransformerMethodRepository.delete(idOfTransformer);

        return !dataTransformerMethodRepository.exists(idOfTransformer);
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

        List<VisualizationSuggestion> updatedVisualizationSuggestions = new ArrayList<>();
        // update the implementing class
        if (newAttributes.getImplementingClass() != null && !newAttributes.getImplementingClass().isEmpty()) {
            visualizationMethod.setImplementingClass(newAttributes.getImplementingClass());
            VisualizationMethodConfiguration visualizationMethodConfiguration = getMethodConfiguration(idOfMethod);
            visualizationSuggestionRepository.findByVisualizationMethod(visualizationMethod).forEach(suggestion -> {
                //update the suggestion entries with the new OpenLAPDataSet input configuration
                try {
                    suggestion.setOlapDataSetConfiguration(objectMapper.writeValueAsString(visualizationMethodConfiguration.getInput()));
                    updatedVisualizationSuggestions.add(suggestion);
                } catch (JsonProcessingException exception) {
                    log.error("Could not update suggestion with id: " + suggestion.getId() + " while update method with id: " + idOfMethod, exception);
                }
            });
        }

        if (newAttributes.getDataTransformerMethod() != null) {
            if (dataTransformerMethodRepository.exists(newAttributes.getDataTransformerMethod().getId())) {
                DataTransformerMethod newDataTransformerMethod = dataTransformerMethodRepository.findOne(newAttributes.getDataTransformerMethod().getId());
                //finally set the data transformer method
                visualizationMethod.setDataTransformerMethod(newDataTransformerMethod);
            }
        }
        //commit the changes
        visualizationMethod = visualizationMethodRepository.save(visualizationMethod);
        if (updatedVisualizationSuggestions.size() > 0)
            visualizationSuggestionRepository.save(updatedVisualizationSuggestions);

        return visualizationMethod;
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
     * Validates the configuration of the VisualizationMethod (i.e. the inputs that it accepts) with the provided OpenLAPPortConfig.
     *
     * @param visualizationMethodId The id of the VisualizationMethod for which to validate the configuration
     * @param olapPortConfiguration The OpenLAPPortConfig against which to validate the method configuration
     * @return true if the the provided port configuration matches the configuration of the VisualizationMethod
     * @throws DataSetValidationException If the validation encountered an error
     */
    public boolean validateVisualizationMethodConfiguration(long visualizationMethodId, OpenLAPPortConfig olapPortConfiguration) throws DataSetValidationException {
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
        // serialize and de-serialize the DataSet to avoid the issue with the ClassCastException, because the VisualizationCodeGenerator is loaded in another class loader
        //and the OpenLAPDataSet in this piece code is loaded in another.
        OpenLAPDataSet inputDataSet = null;
        OpenLAPDataSet outputDataSet = null;
        try {
            inputDataSet = codeGenerator.getInput();
            //inputDataSet = objectMapper.readValue(codeGenerator.getInputAsJsonString(), OpenLAPDataSet.class);
            // outputDataSet = objectMapper.readValue(codeGenerator.getOutputAsJsonString(), OpenLAPDataSet.class);
        } catch (Exception ex) {
            log.error("Error in deserializing codegenerator input/output config.", ex);
        }
        VisualizationMethodConfiguration visualizationMethodConfiguration = new VisualizationMethodConfiguration();
        visualizationMethodConfiguration.setInput(inputDataSet);
        visualizationMethodConfiguration.setOutput(outputDataSet);
        return visualizationMethodConfiguration;
    }

    public String encodeURIComponent(String component) {
        String result = null;

        try {
            result = URLEncoder.encode(component, "UTF-8")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            result = component;
        }
        return result;
    }
}
