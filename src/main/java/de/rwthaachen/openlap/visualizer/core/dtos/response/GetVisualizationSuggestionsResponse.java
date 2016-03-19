package de.rwthaachen.openlap.visualizer.core.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rwthaachen.openlap.visualizer.core.dtos.VisualizationSuggestionDetails;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetVisualizationSuggestionsResponse {

    private List<VisualizationSuggestionDetails> suggestions;

    public List<VisualizationSuggestionDetails> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<VisualizationSuggestionDetails> suggestions) {
        this.suggestions = suggestions;
    }
}
