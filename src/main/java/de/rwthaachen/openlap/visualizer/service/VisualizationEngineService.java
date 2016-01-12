package de.rwthaachen.openlap.visualizer.service;

import DataSet.OLAPDataSet;
import de.rwthaachen.openlap.visualizer.dao.VisualizationFrameworkRepository;
import de.rwthaachen.openlap.visualizer.exceptions.BaseException;
import de.rwthaachen.openlap.visualizer.exceptions.VisualizationMethodNotFoundException;
import de.rwthaachen.openlap.visualizer.framework.VisualizationCodeGenerator;
import de.rwthaachen.openlap.visualizer.framework.adapters.DataTransformer;
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

    public String generateClientVisualizationCode(String frameworkName, String methodName, OLAPDataSet dataSet) throws BaseException{
        VisualizationFramework visualizationFramework = visualizationFrameworkRepository.findByName(frameworkName);

        Optional<VisualizationMethod> visualizationMethod = visualizationFramework.getVisualizationMethods()
                .stream()
                .filter((method)-> method.getName().equals((methodName)))
                .findFirst();

        if(visualizationMethod.isPresent()){
            //visualization method found
            VisualizationMethod visMethod = visualizationMethod.get();
            ClassLoader visualizerClassLoader = new DynamicClassLoader(visualizationFramework.getFrameworkLocation());
            VisualizationCodeGenerator codeGenerator = visualizerClassLoader.loadCodeGenerator(visMethod.getImplementingClassName());
            DataTransformer dataTransformer = visualizerClassLoader.loadDataTransformer(visMethod.getDataTransformerMethod().getImplementingClassName());
            return codeGenerator.generateVisualizationCode(dataSet,dataTransformer);
        }
        else{
            throw new VisualizationMethodNotFoundException("The method: "+methodName+" for the framework: "+frameworkName+" was not found");
        }
    }

    public String generateClientVisualizationCode(long frameworkId, long methodId){

    }
}
