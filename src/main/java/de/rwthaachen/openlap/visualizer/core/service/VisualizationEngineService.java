package de.rwthaachen.openlap.visualizer.core.service;

import DataSet.OLAPDataSet;
import DataSet.OLAPPortConfiguration;
import de.rwthaachen.openlap.visualizer.core.dao.VisualizationFrameworkRepository;
import de.rwthaachen.openlap.visualizer.core.exceptions.VisualizationCodeGenerationException;
import de.rwthaachen.openlap.visualizer.core.framework.factory.DataTransformerFactory;
import de.rwthaachen.openlap.visualizer.core.framework.factory.DataTransformerFactoryImpl;
import de.rwthaachen.openlap.visualizer.core.framework.factory.VisualizationCodeGeneratorFactory;
import de.rwthaachen.openlap.visualizer.core.framework.factory.VisualizationCodeGeneratorFactoryImpl;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationMethod;
import de.rwthaachen.openlap.visualizer.framework.DataTransformer;
import de.rwthaachen.openlap.visualizer.framework.VisualizationCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
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

    /**
     * Generates the visualization code
     *
     * @param dataSet       The data set containing the data to visualize
     * @param frameworkName The name of the VisualizationFramework to use
     * @param methodName    The name of the VisualizationMethod to use
     * @return The client visualization code
     * @throws VisualizationCodeGenerationException If the generation of the visualization code was not successful
     */
    public String generateClientVisualizationCode(String frameworkName, String methodName, OLAPDataSet dataSet, OLAPPortConfiguration portConfiguration, Map<String, Object> additionalParams) throws VisualizationCodeGenerationException {
        VisualizationFramework visualizationFramework = visualizationFrameworkRepository.findByName(frameworkName);

        Optional<VisualizationMethod> visualizationMethod = visualizationFramework.getVisualizationMethods()
                .stream()
                .filter((method) -> method.getName().equals((methodName)))
                .findFirst();

        if (visualizationMethod.isPresent()) {
            //visualization method found
            VisualizationMethod visMethod = visualizationMethod.get();
            //ask the factories for the instances
            DataTransformerFactory dataTransformerFactory = new DataTransformerFactoryImpl(visualizationFramework.getFrameworkLocation());
            VisualizationCodeGeneratorFactory visualizationCodeGeneratorFactory = new VisualizationCodeGeneratorFactoryImpl(visualizationFramework.getFrameworkLocation());
            VisualizationCodeGenerator codeGenerator = visualizationCodeGeneratorFactory.createVisualizationCodeGenerator(visMethod.getImplementingClass());
            DataTransformer dataTransformer = dataTransformerFactory.createDataTransformer(visMethod.getDataTransformerMethod().getImplementingClass());
            return codeGenerator.generateVisualizationCode(dataSet,portConfiguration, dataTransformer, additionalParams);
        } else {
            throw new VisualizationCodeGenerationException("The method: " + methodName + " for the framework: " + frameworkName + " was not found");
        }
    }

    /**
     * Generates the visualization code
     *
     * @param olapDataSet The data set containing the data to visualize
     * @param frameworkId The id of the VisualizationFramework to use
     * @param methodId    The id of the VisualizationMethod to use
     * @return The client visualization code
     * @throws VisualizationCodeGenerationException If the generation of the visualization code was not successful
     */
    public String generateClientVisualizationCode(long frameworkId, long methodId, OLAPDataSet olapDataSet, OLAPPortConfiguration portConfiguration, Map<String, Object> additionalParams) throws VisualizationCodeGenerationException {
        VisualizationFramework visualizationFramework = visualizationFrameworkRepository.findOne(frameworkId);

        Optional<VisualizationMethod> visualizationMethod = visualizationFramework.getVisualizationMethods()
                .stream()
                .filter((method) -> method.getId() == methodId)
                .findFirst();

        if (visualizationMethod.isPresent()) {
            VisualizationMethod visMethod = visualizationMethod.get();
            //ask the factories for the instances
            DataTransformerFactory dataTransformerFactory = new DataTransformerFactoryImpl(visualizationFramework.getFrameworkLocation());
            VisualizationCodeGeneratorFactory visualizationCodeGeneratorFactory = new VisualizationCodeGeneratorFactoryImpl(visualizationFramework.getFrameworkLocation());
            DataTransformer dataTransformer = dataTransformerFactory.createDataTransformer(visMethod.getDataTransformerMethod().getImplementingClass());
            VisualizationCodeGenerator codeGenerator = visualizationCodeGeneratorFactory.createVisualizationCodeGenerator(visMethod.getImplementingClass());
            return codeGenerator.generateVisualizationCode(olapDataSet,portConfiguration, dataTransformer, additionalParams);
        } else {
            throw new VisualizationCodeGenerationException("The method: " + methodId + " for the framework: " + frameworkId + " was not found");
        }
    }
}
