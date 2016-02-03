package de.rwthaachen.openlap.visualizer.core.dtos.response;

import de.rwthaachen.openlap.visualizer.core.dtos.VisualizationSuggestionDetails;

/**
 * Created by bas on 1/30/16.
 */
public class GetVisualizationSuggestionDetailsResponse {

    private VisualizationSuggestionDetails visualizationSuggestionDetails;

    public VisualizationSuggestionDetails getVisualizationSuggestionDetails() {
        return visualizationSuggestionDetails;
    }

    public void setVisualizationSuggestionDetails(VisualizationSuggestionDetails visualizationSuggestionDetails) {
        this.visualizationSuggestionDetails = visualizationSuggestionDetails;
    }
}
