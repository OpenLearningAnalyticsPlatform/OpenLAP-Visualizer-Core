package de.rwthaachen.openlap.visualizer.service;

import DataSet.OLAPDataSet;
import de.rwthaachen.openlap.visualizer.dao.VisualizationFrameworkRepository;
import de.rwthaachen.openlap.visualizer.exceptions.BaseException;
import de.rwthaachen.openlap.visualizer.exceptions.VisualizationCodeGenerationException;
import de.rwthaachen.openlap.visualizer.exceptions.VisualizationMethodNotFoundException;
import de.rwthaachen.openlap.visualizer.framework.VisualizationCodeGenerator;
import de.rwthaachen.openlap.visualizer.framework.adapters.DataTransformer;
import de.rwthaachen.openlap.visualizer.framework.factory.DataTransformerFactory;
import de.rwthaachen.openlap.visualizer.framework.factory.DataTransformerFactoryImpl;
import de.rwthaachen.openlap.visualizer.framework.factory.VisualizationCodeGeneratorFactory;
import de.rwthaachen.openlap.visualizer.framework.factory.VisualizationCodeGeneratorFactoryImpl;
import de.rwthaachen.openlap.visualizer.model.VisualizationFramework;
import de.rwthaachen.openlap.visualizer.model.VisualizationMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service which provides methods to generate visualization code. The service can thought of as the orchestrator which takes care of calling the
 * relevant data transformer to transform the data and then passing it over to the visualization code generator to the get the client visualization code
 *
 * @author Bassim Bashir
 */
@Service
public class VisualizationEngineService {

    @Autowired
    VisualizationFrameworkRepository visualizationFrameworkRepository;

    @Autowired
    ConfigurationService configurationService;

    /**
     * Generates the visualization code
     *
     * @param dataSet       The data set containing the data to visualize
     * @param frameworkName The name of the framework to use
     * @param methodName    The name of the visualization technique to use
     * @return The visualization code
     * @throws VisualizationCodeGenerationException If the generation of the visualization code was not successful
     */
    public String generateClientVisualizationCode(String frameworkName, String methodName, OLAPDataSet dataSet) throws VisualizationCodeGenerationException {
        VisualizationFramework visualizationFramework = visualizationFrameworkRepository.findByName(frameworkName);

        Optional<VisualizationMethod> visualizationMethod = visualizationFramework.getVisualizationMethods()
                .stream()
                .filter((method) -> method.getName().equals((methodName)))
                .findFirst();

        if (visualizationMethod.isPresent()) {
            //visualization method found
            VisualizationMethod visMethod = visualizationMethod.get();
            //ask the factories for the instances
            DataTransformerFactory dataTransformerFactory = new DataTransformerFactoryImpl(configurationService.getVisualizationFrameworksJarStorageLocation());
            VisualizationCodeGeneratorFactory visualizationCodeGeneratorFactory = new VisualizationCodeGeneratorFactoryImpl(configurationService.getVisualizationFrameworksJarStorageLocation());
            VisualizationCodeGenerator codeGenerator = visualizationCodeGeneratorFactory.createVisualizationCodeGenerator(visMethod.getImplementingClassName());
            DataTransformer dataTransformer = dataTransformerFactory.createDataTransformer(visMethod.getDataTransformerMethod().getImplementingClassName());
            return codeGenerator.generateVisualizationCode(dataSet, dataTransformer);
        } else {
            throw new VisualizationCodeGenerationException("The method: " + methodName + " for the framework: " + frameworkName + " was not found");
        }
    }

    /**
     * Generates the visualization code
     *
     * @param olapDataSet The data set containing the data to visualize
     * @param frameworkId The id of the framework to use
     * @param methodId    The id of the visualization technique to use
     * @return The visualization code
     * @throws VisualizationCodeGenerationException If the generation of the visualization code was not successful
     */
    public String generateClientVisualizationCode(long frameworkId, long methodId, OLAPDataSet olapDataSet) throws VisualizationCodeGenerationException {
        VisualizationFramework visualizationFramework = visualizationFrameworkRepository.findOne(frameworkId);

        Optional<VisualizationMethod> visualizationMethod = visualizationFramework.getVisualizationMethods()
                .stream()
                .filter((method) -> method.getId() == methodId)
                .findFirst();

        if (visualizationMethod.isPresent()) {
            VisualizationMethod visMethod = visualizationMethod.get();
            //ask the factories for the instances
            DataTransformerFactory dataTransformerFactory = new DataTransformerFactoryImpl(configurationService.getVisualizationFrameworksJarStorageLocation());
            VisualizationCodeGeneratorFactory visualizationCodeGeneratorFactory = new VisualizationCodeGeneratorFactoryImpl(configurationService.getVisualizationFrameworksJarStorageLocation());
            VisualizationCodeGenerator codeGenerator = visualizationCodeGeneratorFactory.createVisualizationCodeGenerator(visMethod.getImplementingClassName());
            DataTransformer dataTransformer = dataTransformerFactory.createDataTransformer(visMethod.getDataTransformerMethod().getImplementingClassName());
            return codeGenerator.generateVisualizationCode(olapDataSet, dataTransformer);
        } else {
            throw new VisualizationCodeGenerationException("The method: " + methodId + " for the framework: " + frameworkId + " was not found");
        }
    }
}
