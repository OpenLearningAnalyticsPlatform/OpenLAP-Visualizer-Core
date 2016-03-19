package de.rwthaachen.openlap.visualizer.core.controller;

import de.rwthaachen.openlap.visualizer.core.dtos.VisualizationSuggestionDetails;
import de.rwthaachen.openlap.visualizer.core.dtos.error.BaseErrorDTO;
import de.rwthaachen.openlap.visualizer.core.dtos.request.AddNewVisualizationSuggestionRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.request.GetVisualizationSuggestionsRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.request.UpdateVisualizationSuggestionRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.response.*;
import de.rwthaachen.openlap.visualizer.core.exceptions.VisualizationSuggestionCreationException;
import de.rwthaachen.openlap.visualizer.core.exceptions.VisualizationSuggestionNotFoundException;
import de.rwthaachen.openlap.visualizer.core.service.VisualizationSuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * A Spring controller which exposes an API for the client to perform CRUD operations on the VisualizationSuggestions
 *
 * @author Bassim Bashir
 */
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

    @RequestMapping(value = "/listByDataSetConfiguration", method = RequestMethod.POST)
    public GetVisualizationSuggestionsResponse getSuggestionsByDatasetConfiguration(@RequestBody GetVisualizationSuggestionsRequest suggestionsRequest) {
        GetVisualizationSuggestionsResponse response = new GetVisualizationSuggestionsResponse();
        List<VisualizationSuggestionDetails> visualizationSuggestionList = visualizationSuggestionService.getSuggestionsForDataSetConfiguration(suggestionsRequest.getDataSetConfiguration());
        response.setSuggestions(visualizationSuggestionList);
        return response;
    }

    @RequestMapping(value = "/{idOfSuggestion}", method = RequestMethod.PUT)
    public UpdateVisualizationSuggestionResponse updateVisualizationSuggestion(@PathVariable Long idOfSuggestion, @RequestBody UpdateVisualizationSuggestionRequest request) {
        UpdateVisualizationSuggestionResponse response = new UpdateVisualizationSuggestionResponse();
        response.setVisualizationSuggestion(visualizationSuggestionService.updateVisualizationSuggestionAttributes(idOfSuggestion, request.getVisualizationSuggestion()));
        return response;
    }

    @RequestMapping(value = "/{idOfSuggestion}", method = RequestMethod.DELETE)
    public DeleteVisualizationSuggestionResponse deleteVisualizationSuggestion(@PathVariable Long idOfSuggestion) {
        DeleteVisualizationSuggestionResponse response = new DeleteVisualizationSuggestionResponse();
        boolean success = visualizationSuggestionService.deleteVisualizationSuggestions(idOfSuggestion);
        response.setSuccess(success);
        return response;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public AddNewVisualizationSuggestionResponse createVisualizationSuggestion(@RequestBody AddNewVisualizationSuggestionRequest request) {
        AddNewVisualizationSuggestionResponse response = new AddNewVisualizationSuggestionResponse();
        response.setVisualizationSuggestion(visualizationSuggestionService.createVisualizationSuggestion(request.getVisualizationMethodId(), request.getOlapDataSet()));
        return response;
    }


    @ExceptionHandler(VisualizationSuggestionNotFoundException.class)
    public ResponseEntity<Object> handleVisualizationSuggestionNotFoundException(VisualizationSuggestionNotFoundException exception, HttpServletRequest request) {
        BaseErrorDTO error = BaseErrorDTO.createBaseErrorDTO(exception.getMessage(), "", "");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VisualizationSuggestionCreationException.class)
    public ResponseEntity<Object> handleVisualizationSuggestionCreationException(VisualizationSuggestionCreationException exception, HttpServletRequest request) {
        BaseErrorDTO error = BaseErrorDTO.createBaseErrorDTO(exception.getMessage(), "", "");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, headers, HttpStatus.BAD_REQUEST);
    }
}
