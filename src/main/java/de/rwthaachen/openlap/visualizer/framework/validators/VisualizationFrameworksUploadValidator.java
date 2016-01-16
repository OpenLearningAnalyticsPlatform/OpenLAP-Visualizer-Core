package de.rwthaachen.openlap.visualizer.framework.validators;

import de.rwthaachen.openlap.visualizer.exceptions.DataTransformerCreationException;
import de.rwthaachen.openlap.visualizer.exceptions.VisualizationCodeGeneratorCreationException;
import de.rwthaachen.openlap.visualizer.exceptions.VisualizationFrameworksUploadException;
import de.rwthaachen.openlap.visualizer.framework.factory.DataTransformerFactory;
import de.rwthaachen.openlap.visualizer.framework.factory.DataTransformerFactoryImpl;
import de.rwthaachen.openlap.visualizer.framework.factory.VisualizationCodeGeneratorFactory;
import de.rwthaachen.openlap.visualizer.framework.factory.VisualizationCodeGeneratorFactoryImpl;
import de.rwthaachen.openlap.visualizer.model.VisualizationFramework;
import de.rwthaachen.openlap.visualizer.model.VisualizationMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

/**
 * Class contains the logic and functions to validate the config object of visualization frameworks
 */
//TODO: convert the framework config to json and encapsulate it in the exception to send back to the client upon failure of validation
public class VisualizationFrameworksUploadValidator {

    //TODO: return information about why the validation failed!!!
    public boolean validateVisualizationFrameworksUploadConfiguration(List<VisualizationFramework> frameworksConfig, MultipartFile frameworksJar) throws VisualizationFrameworksUploadException {
        // first step of validation, check if all the fields are not null
        if (frameworksConfig.stream().filter(new Predicate<VisualizationFramework>() {
            @Override
            public boolean test(VisualizationFramework visualizationFramework) {
                if (visualizationFramework.getName() != null && visualizationFramework.getFrameworkLocation() != null &&
                        visualizationFramework.getDescription() != null && visualizationFramework.getUploadedBy() != null &&
                        !visualizationFramework.getName().isEmpty() && !visualizationFramework.getUploadedBy().isEmpty() &&
                        !visualizationFramework.getDescription().isEmpty() && !visualizationFramework.getFrameworkLocation().isEmpty()
                        && visualizationFramework.getVisualizationMethods() != null) {
                    //lets go deeper into the visualization methods definition
                    if (visualizationFramework.getVisualizationMethods().size() > 0) {
                        for (VisualizationMethod visualizationMethod : visualizationFramework.getVisualizationMethods()) {
                            if (visualizationMethod.getName() != null && visualizationMethod.getImplementingClassName() != null
                                    && !visualizationMethod.getName().isEmpty() && !visualizationMethod.getImplementingClassName().isEmpty()
                                    && visualizationMethod.getDataTransformerMethod() != null && visualizationMethod.getDataTransformerMethod().getName() != null
                                    && visualizationMethod.getDataTransformerMethod().getImplementingClassName() != null
                                    && !visualizationMethod.getDataTransformerMethod().getName().isEmpty()
                                    && !visualizationMethod.getDataTransformerMethod().getImplementingClassName().isEmpty()) {
                                return false; // do not include in the filtered list
                            } else {
                                return true;
                            }
                        }
                    } else {
                        return true;
                    }
                }
                return true;
            }
        }).count() > 0) {
            return false;
        }
        //now try loading the classes and confirm if they implement the required interfaces
        try {
            DataTransformerFactory dataTransformerFactory = new DataTransformerFactoryImpl(frameworksJar.getInputStream());
            VisualizationCodeGeneratorFactory visualizationCodeGeneratorFactory = new VisualizationCodeGeneratorFactoryImpl(frameworksJar.getInputStream());
            for(VisualizationFramework visualizationFramework : frameworksConfig){
                for(VisualizationMethod visualizationMethod : visualizationFramework.getVisualizationMethods()){
                    //lets first check the code generator
                    try {
                        if(visualizationCodeGeneratorFactory.createVisualizationCodeGenerator(visualizationMethod.getImplementingClassName())==null)
                            return false;
                        //now the data transformer
                        if(dataTransformerFactory.createDataTransformer(visualizationMethod.getDataTransformerMethod().getImplementingClassName())==null)
                            return false;
                    }catch (DataTransformerCreationException | VisualizationCodeGeneratorCreationException exception){
                        throw new VisualizationFrameworksUploadException(exception.getMessage());
                    }
                }
            }
        }catch (IOException exception){
            throw new VisualizationFrameworksUploadException(exception.getMessage());
        }
        return true;
    }
}
