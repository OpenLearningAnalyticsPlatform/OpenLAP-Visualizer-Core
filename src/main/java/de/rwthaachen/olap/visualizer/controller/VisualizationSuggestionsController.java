package de.rwthaachen.olap.visualizer.controller;

import de.rwthaachen.olap.visualizer.dtos.request.RequestDTO;
import de.rwthaachen.olap.visualizer.dtos.response.ResponseDTO;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bas on 11/17/15.
 */
@RestController
@RequestMapping("/suggestions")
public class VisualizationSuggestionsController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseDTO visualizationSuggestionsForAnalyticsMethod(@RequestParam(value = "methodName", defaultValue = "") String analyticsMethod, @RequestParam(value = "methodCategory", defaultValue = "") String analyticsMethodCategory) {
        return new ResponseDTO();

    }

    @RequestMapping(value = "/{idOfSuggestion}/update", method = RequestMethod.PUT)
    public ResponseDTO updateVisualizationSuggestion(@RequestBody RequestDTO requestbody) {
        return new ResponseDTO();

    }

    @RequestMapping(value = "/{idOfSuggestion}/delete", method = RequestMethod.DELETE)
    public ResponseDTO deleteVisualizationSuggestion() {
        return new ResponseDTO();

    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseDTO createVisualizationSuggestion(@RequestBody RequestDTO requestDTOBody) {
        return new ResponseDTO();

    }
}
