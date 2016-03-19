package de.rwthaachen.openlap.visualizer.core.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationSuggestion;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddNewVisualizationSuggestionResponse {

    private VisualizationSuggestion visualizationSuggestion;

    public VisualizationSuggestion getVisualizationSuggestion() {
        return visualizationSuggestion;
    }

    public void setVisualizationSuggestion(VisualizationSuggestion visualizationSuggestion) {
        this.visualizationSuggestion = visualizationSuggestion;
    }
}
