package de.rwthaachen.openlap.visualizer.core.controller;

import de.rwthaachen.openlap.visualizer.core.dtos.VisualizationSuggestionDetails;
import de.rwthaachen.openlap.visualizer.core.dtos.request.AddNewVisualizationSuggestionRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.request.GetVisualizationSuggestionsRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.request.UpdateVisualizationSuggestionRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.response.*;
import de.rwthaachen.openlap.visualizer.core.service.VisualizationSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suggestions")
public class VisualizationSuggestionController {

    @Autowired
    private VisualizationSuggestionService visualizationSuggestionService;

    @RequestMapping(value = "/{idOfSuggestion}", method = RequestMethod.GET)
    public GetVisualizationSuggestionDetailsResponse getVisualizationSuggestionDetails(@PathVariable Long idOfSuggestion) {
        GetVisualizationSuggestionDetailsResponse response = new GetVisualizationSuggestionDetailsResponse();
        VisualizationSuggestionDetails visualizationSuggestionDetails = visualizationSuggestionService.getSuggestionDetails(idOfSuggestion);
        response.setVisualizationSuggestionDetails(visualizationSuggestionDetails);
        return response;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public GetVisualizationSuggestionsResponse getSuggestionsForDataset(@RequestBody GetVisualizationSuggestionsRequest suggestionsRequest) {
        GetVisualizationSuggestionsResponse response = new GetVisualizationSuggestionsResponse();
        List<VisualizationSuggestionDetails> visualizationSuggestionList = visualizationSuggestionService.getSuggestionsForDataSetConfiguration(suggestionsRequest.getDataSetConfiguration());
        response.setSuggestions(visualizationSuggestionList);
        return response;
    }

    @RequestMapping(value = "/{idOfSuggestion}/update", method = RequestMethod.PUT)
    public UpdateVisualizationSuggestionResponse updateVisualizationSuggestion(@PathVariable Long idOfSuggestion, @RequestBody UpdateVisualizationSuggestionRequest request) {
        UpdateVisualizationSuggestionResponse response = new UpdateVisualizationSuggestionResponse();
        visualizationSuggestionService.updateVisualizationSuggestionAttributes(idOfSuggestion,request.getVisualizationSuggestion());
        response.setSuccess(true);
        return response;
    }

    @RequestMapping(value = "/{idOfSuggestion}/delete", method = RequestMethod.DELETE)
    public DeleteVisualizationSuggestionResponse deleteVisualizationSuggestion(@PathVariable Long idOfSuggestion) {
        DeleteVisualizationSuggestionResponse response = new DeleteVisualizationSuggestionResponse();
        boolean success = visualizationSuggestionService.deleteVisualizationSuggestions(idOfSuggestion);
        response.setSuccess(success);
        return response;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public AddNewVisualizationSuggestionResponse createVisualizationSuggestion(@RequestBody AddNewVisualizationSuggestionRequest request) {
        AddNewVisualizationSuggestionResponse response = new AddNewVisualizationSuggestionResponse();
        long idOfNewSuggestion = visualizationSuggestionService.createVisualizationSuggestion(request.getVisualizationMethodId(),request.getOlapDataSet());
        response.setSuggestionId(idOfNewSuggestion);
        return response;
    }

}
