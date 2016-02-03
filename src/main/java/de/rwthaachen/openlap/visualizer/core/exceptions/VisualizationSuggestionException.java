package de.rwthaachen.openlap.visualizer.core.exceptions;

/**
 * Created by bas on 1/30/16.
 */
public class VisualizationSuggestionException extends BaseException{
    public VisualizationSuggestionException(String message) {
        super(message,VisualizationSuggestionException.class.getSimpleName(),"");
    }
}
