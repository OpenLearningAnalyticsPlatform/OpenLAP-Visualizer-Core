package de.rwthaachen.olap.visualizer.controller;

import de.rwthaachen.olap.visualizer.models.request.Request;
import de.rwthaachen.olap.visualizer.models.response.Response;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bas on 11/17/15.
 */
@RestController
@RequestMapping("/frameworks")
public class VisualizationFrameworksController {

    @RequestMapping(value = "/{idOfFramework}/update", method = RequestMethod.PUT)
    public Response updateVisualizationFramework(@PathVariable String idOfFramework, @RequestBody Request requestBody) {
        return new Response();
    }

    @RequestMapping(value = "/{idOfFramework}/delete", method = RequestMethod.DELETE)
    public Response deleteVisualizationFramework(@PathVariable String idOfFramework){
        return new Response();

    }

    @RequestMapping(value = "/{idOfFramework}", method = RequestMethod.GET)
    public Response getFrameworkDetails (@PathVariable String idOfFramework){
        return new Response();

    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public Response uploadNewVisualizationFramework(@RequestBody Request requestBody){
        return new Response();

    }

    @RequestMapping(value = "/{idOfFramework}/methods/{idOfMethod}", method = RequestMethod.GET)
    public Response getFrameworkMethodDetails (@PathVariable String idOfFramework, @PathVariable String idOfMethod){
        return new Response();

    }

    /**
     * update only the attributes such as description, data transformer of the method*/
    @RequestMapping(value = "/{idOfFramework}/methods/{idOfMethod}", method = RequestMethod.PUT)
    public Response updateVisualizationFrameworkMethod (@PathVariable String idOfFramework, @PathVariable String idOfMethod, @RequestBody Request requestBody){
        return new Response();

    }

    //Important: Removal and Creation of new methods is done via upload of Framework jar!

}
