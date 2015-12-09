package de.rwthaachen.olap.visualizer.controller;

import de.rwthaachen.olap.visualizer.dtos.request.RequestDTO;
import de.rwthaachen.olap.visualizer.dtos.response.ResponseDTO;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bas on 11/17/15.
 */
@RestController
@RequestMapping("/frameworks")
public class VisualizationFrameworksController {

    @RequestMapping(value = "/{idOfFramework}/update", method = RequestMethod.PUT)
    public ResponseDTO updateVisualizationFramework(@PathVariable String idOfFramework, @RequestBody RequestDTO requestDTOBody) {
        return new ResponseDTO();
    }

    @RequestMapping(value = "/{idOfFramework}/delete", method = RequestMethod.DELETE)
    public ResponseDTO deleteVisualizationFramework(@PathVariable String idOfFramework){
        return new ResponseDTO();

    }

    @RequestMapping(value = "/{idOfFramework}", method = RequestMethod.GET)
    public ResponseDTO getFrameworkDetails (@PathVariable String idOfFramework){
        return new ResponseDTO();

    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseDTO uploadNewVisualizationFramework(@RequestBody RequestDTO requestDTOBody){
        return new ResponseDTO();

    }

    @RequestMapping(value = "/{idOfFramework}/methods/{idOfMethod}", method = RequestMethod.GET)
    public ResponseDTO getFrameworkMethodDetails (@PathVariable String idOfFramework, @PathVariable String idOfMethod){
        return new ResponseDTO();

    }

    /**
     * update only the attributes such as description, data transformer of the method*/
    @RequestMapping(value = "/{idOfFramework}/methods/{idOfMethod}", method = RequestMethod.PUT)
    public ResponseDTO updateVisualizationFrameworkMethod (@PathVariable String idOfFramework, @PathVariable String idOfMethod, @RequestBody RequestDTO requestDTOBody){
        return new ResponseDTO();

    }

    //Important: Removal and Creation of new methods is done via upload of Framework jar!

}
