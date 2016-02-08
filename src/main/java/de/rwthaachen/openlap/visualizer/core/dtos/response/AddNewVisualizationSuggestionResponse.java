package de.rwthaachen.openlap.visualizer.core.dtos.response;

import de.rwthaachen.openlap.visualizer.core.model.VisualizationSuggestion;

/**
 * Created by bas on 1/28/16.
 */
public class AddNewVisualizationSuggestionResponse {

    private VisualizationSuggestion visualizationSuggestion;

    public VisualizationSuggestion getVisualizationSuggestion() {
        return visualizationSuggestion;
    }

    public void setVisualizationSuggestion(VisualizationSuggestion visualizationSuggestion) {
        this.visualizationSuggestion = visualizationSuggestion;
    }
}
