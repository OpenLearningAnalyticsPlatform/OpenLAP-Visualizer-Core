package de.rwthaachen.openlap.visualizer.core.dtos.response;

import de.rwthaachen.openlap.visualizer.core.dtos.VisualizationSuggestionDetails;

import java.util.List;

/**
 * Created by bas on 1/28/16.
 */
public class GetVisualizationSuggestionsResponse {

    private List<VisualizationSuggestionDetails> suggestions;

    public List<VisualizationSuggestionDetails> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<VisualizationSuggestionDetails> suggestions) {
        this.suggestions = suggestions;
    }
}
