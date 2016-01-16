package de.rwthaachen.openlap.visualizer.exceptions;

/**
 * Created by bas on 12/10/15.
 */
public class VisualizationCodeGeneratorCreationException extends BaseException {
    public VisualizationCodeGeneratorCreationException(String message){
        super(message,VisualizationCodeGeneratorCreationException.class.getSimpleName(),"");
    }
}
