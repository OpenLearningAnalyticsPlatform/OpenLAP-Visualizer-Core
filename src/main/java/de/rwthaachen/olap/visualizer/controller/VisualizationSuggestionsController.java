package de.rwthaachen.olap.visualizer.controller;

import de.rwthaachen.olap.visualizer.models.request.Request;
import de.rwthaachen.olap.visualizer.models.response.Response;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bas on 11/17/15.
 */
@RestController
@RequestMapping("/suggestions")
public class VisualizationSuggestionsController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Response visualizationSuggestionsForAnalyticsMethod(@RequestParam(value = "methodName", defaultValue = "") String analyticsMethod, @RequestParam(value = "methodCategory", defaultValue = "") String analyticsMethodCategory) {
        return new Response();

    }

    @RequestMapping(value = "/{idOfSuggestion}/update", method = RequestMethod.PUT)
    public Response updateVisualizationSuggestion(@RequestBody Request requestbody) {
        return new Response();

    }

    @RequestMapping(value = "/{idOfSuggestion}/delete", method = RequestMethod.DELETE)
    public Response deleteVisualizationSuggestion() {
        return new Response();

    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public Response createVisualizationSuggestion(@RequestBody Request requestBody) {
        return new Response();

    }
}
