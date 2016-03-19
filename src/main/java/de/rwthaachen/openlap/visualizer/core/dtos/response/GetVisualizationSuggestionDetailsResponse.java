package de.rwthaachen.openlap.visualizer.core.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rwthaachen.openlap.visualizer.core.dtos.VisualizationSuggestionDetails;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetVisualizationSuggestionDetailsResponse {

    private VisualizationSuggestionDetails visualizationSuggestionDetails;

    public VisualizationSuggestionDetails getVisualizationSuggestionDetails() {
        return visualizationSuggestionDetails;
    }

    public void setVisualizationSuggestionDetails(VisualizationSuggestionDetails visualizationSuggestionDetails) {
        this.visualizationSuggestionDetails = visualizationSuggestionDetails;
    }
}
