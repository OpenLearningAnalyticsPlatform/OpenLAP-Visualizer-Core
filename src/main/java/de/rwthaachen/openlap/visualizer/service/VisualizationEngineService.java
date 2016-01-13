package de.rwthaachen.openlap.visualizer.service;

import DataSet.OLAPDataSet;
import de.rwthaachen.openlap.visualizer.dao.VisualizationFrameworkRepository;
import de.rwthaachen.openlap.visualizer.exceptions.BaseException;
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
 * Created by bas on 1/1/16.
 */
@Service
public class VisualizationEngineService {

    @Autowired
    VisualizationFrameworkRepository visualizationFrameworkRepository;

    @Autowired
    ConfigurationService configurationService;

    public String generateClientVisualizationCode(String frameworkName, String methodName, OLAPDataSet dataSet) throws BaseException{
        VisualizationFramework visualizationFramework = visualizationFrameworkRepository.findByName(frameworkName);

        Optional<VisualizationMethod> visualizationMethod = visualizationFramework.getVisualizationMethods()
                .stream()
                .filter((method)-> method.getName().equals((methodName)))
                .findFirst();

        if(visualizationMethod.isPresent()){
            //visualization method found
            VisualizationMethod visMethod = visualizationMethod.get();
            //ask the factories for the instances
            DataTransformerFactory dataTransformerFactory = new DataTransformerFactoryImpl(configurationService.getVisualizationFrameworksJarStorageLocation());
            VisualizationCodeGeneratorFactory visualizationCodeGeneratorFactory = new VisualizationCodeGeneratorFactoryImpl(configurationService.getVisualizationFrameworksJarStorageLocation());
            VisualizationCodeGenerator codeGenerator = visualizationCodeGeneratorFactory.createVisualizationCodeGenerator(visMethod.getImplementingClassName());
            DataTransformer dataTransformer = dataTransformerFactory.createDataTransformer(visMethod.getDataTransformerMethod().getImplementingClassName());
            return codeGenerator.generateVisualizationCode(dataSet,dataTransformer);
        }
        else{
            throw new VisualizationMethodNotFoundException("The method: "+methodName+" for the framework: "+frameworkName+" was not found");
        }
    }

    public String generateClientVisualizationCode(long frameworkId, long methodId, OLAPDataSet olapDataSet){
        VisualizationFramework visualizationFramework = visualizationFrameworkRepository.findOne(frameworkId);

        Optional<VisualizationMethod> visualizationMethod = visualizationFramework.getVisualizationMethods()
                .stream()
                .filter((method)-> method.getId() == methodId)
                .findFirst();

        if(visualizationMethod.isPresent()){
            VisualizationMethod visMethod = visualizationMethod.get();
            //ask the factories for the instances
            DataTransformerFactory dataTransformerFactory = new DataTransformerFactoryImpl(configurationService.getVisualizationFrameworksJarStorageLocation());
            VisualizationCodeGeneratorFactory visualizationCodeGeneratorFactory = new VisualizationCodeGeneratorFactoryImpl(configurationService.getVisualizationFrameworksJarStorageLocation());
            VisualizationCodeGenerator codeGenerator = visualizationCodeGeneratorFactory.createVisualizationCodeGenerator(visMethod.getImplementingClassName());
            DataTransformer dataTransformer = dataTransformerFactory.createDataTransformer(visMethod.getDataTransformerMethod().getImplementingClassName());
            return codeGenerator.generateVisualizationCode(olapDataSet,dataTransformer);
        }
        else{
            throw new VisualizationMethodNotFoundException("The method: "+methodId+" for the framework: "+frameworkId+" was not found");
        }
    }
}
