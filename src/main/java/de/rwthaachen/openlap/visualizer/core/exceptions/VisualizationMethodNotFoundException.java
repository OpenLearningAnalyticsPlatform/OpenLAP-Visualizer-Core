package de.rwthaachen.openlap.visualizer.core.exceptions;

/**
 * Created by bas on 1/12/16.
 */
public class VisualizationMethodNotFoundException extends BaseException {

    public VisualizationMethodNotFoundException(String message) {
        super(message,VisualizationMethodNotFoundException.class.getSimpleName(),"");
    }
}
