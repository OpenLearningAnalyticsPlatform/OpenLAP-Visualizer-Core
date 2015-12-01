package de.rwthaachen.olap.visualizer.controller;

import de.rwthaachen.olap.visualizer.models.request.Request;
import de.rwthaachen.olap.visualizer.models.response.Response;
import org.springframework.web.bind.annotation.*;

/**
 * A Spring controller which exposes the API of the Visualizer component
 * */
@RestController
public class VisualizationEngineController {

    @RequestMapping(value = "/generateVisualizationCode", method = RequestMethod.POST)
    public Response generateVisualizationCode(@RequestBody Request requestBody) {

    }

}
