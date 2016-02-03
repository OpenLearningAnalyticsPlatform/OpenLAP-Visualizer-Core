package de.rwthaachen.openlap.visualizer.core.dtos.request;

import de.rwthaachen.openlap.visualizer.core.model.VisualizationSuggestion;

/**
 * Created by bas on 1/28/16.
 */
public class UpdateVisualizationSuggestionRequest {

    private VisualizationSuggestion visualizationSuggestion;

    public VisualizationSuggestion getVisualizationSuggestion() {
        return visualizationSuggestion;
    }

    public void setVisualizationSuggestion(VisualizationSuggestion visualizationSuggestion) {
        this.visualizationSuggestion = visualizationSuggestion;
    }
}
