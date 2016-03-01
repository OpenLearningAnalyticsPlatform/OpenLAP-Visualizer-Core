package de.rwthaachen.openlap.visualizer.core.exceptions;

public class VisualizationSuggestionNotFoundException extends BaseException{
    public VisualizationSuggestionNotFoundException(String message) {
        super(message,VisualizationSuggestionNotFoundException.class.getSimpleName(),"");
    }
}
