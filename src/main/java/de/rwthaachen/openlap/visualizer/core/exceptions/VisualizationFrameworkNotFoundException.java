package de.rwthaachen.openlap.visualizer.core.exceptions;

public class VisualizationFrameworkNotFoundException extends BaseException {

    public VisualizationFrameworkNotFoundException(String message) {
        super(message,VisualizationFrameworkNotFoundException.class.getSimpleName(),"");
    }
}
