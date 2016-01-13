package de.rwthaachen.openlap.visualizer.controller;

import de.rwthaachen.openlap.visualizer.dtos.request.GenerateVisualizationCodeRequest;
import de.rwthaachen.openlap.visualizer.service.VisualizationEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * A Spring controller which exposes the API of the Visualizer component
 */
@RestController
public class VisualizationEngineController {

    @Autowired
    VisualizationEngineService visualizationEngineService;

    @RequestMapping(value = "/generateVisualizationCode", method = RequestMethod.POST)
    public ResponseDTO generateVisualizationCode(@RequestBody GenerateVisualizationCodeRequest generateVisualizationCodeRequest) {
        //check which service method to invoke
        if (generateVisualizationCodeRequest.getFrameworkName() != null && !generateVisualizationCodeRequest.getFrameworkName().isEmpty()
                && generateVisualizationCodeRequest.getMethodName() != null && !generateVisualizationCodeRequest.getMethodName().isEmpty())
            visualizationEngineService.generateClientVisualizationCode(generateVisualizationCodeRequest.getFrameworkName(), generateVisualizationCodeRequest.getMethodName(), generateVisualizationCodeRequest.getDataSet());
        else

        return new ResponseDTO();
    }

}
