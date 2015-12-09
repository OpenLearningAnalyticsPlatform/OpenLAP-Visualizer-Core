package de.rwthaachen.openlap.visualizer.controller;

import de.rwthaachen.openlap.visualizer.dtos.request.RequestDTO;
import de.rwthaachen.openlap.visualizer.dtos.response.ResponseDTO;
import org.springframework.web.bind.annotation.*;

/**
 * A Spring controller which exposes the API of the Visualizer component
 * */
@RestController
public class VisualizationEngineController {

    @RequestMapping(value = "/generateVisualizationCode", method = RequestMethod.POST)
    public ResponseDTO generateVisualizationCode(@RequestBody RequestDTO requestDTOBody) {
        return new ResponseDTO();
    }

}
