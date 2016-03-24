package de.rwthaachen.openlap.visualizer.core.controller;

import de.rwthaachen.openlap.visualizer.core.dtos.error.BaseErrorDTO;
import de.rwthaachen.openlap.visualizer.core.dtos.request.GenerateVisualizationCodeRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.response.GenerateVisualizationCodeResponse;
import de.rwthaachen.openlap.visualizer.core.exceptions.VisualizationCodeGenerationException;
import de.rwthaachen.openlap.visualizer.core.service.VisualizationEngineService;
import de.rwthaachen.openlap.visualizer.framework.exceptions.UnTransformableData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * A Spring controller which exposes the API of the VisualizationEngine. The endpoints provide functionality to generate
 * client visualization code
 *
 * @author Bassim Bashir
 */
@RestController
public class VisualizationEngineController {

    @Autowired
    VisualizationEngineService visualizationEngineService;

    @RequestMapping(value = "/generateVisualizationCode", method = RequestMethod.POST)
    public GenerateVisualizationCodeResponse generateVisualizationCode(@RequestBody GenerateVisualizationCodeRequest generateVisualizationCodeRequest) {
        GenerateVisualizationCodeResponse visualizationCodeResponse = new GenerateVisualizationCodeResponse();
        //check which service method to invoke
        if (generateVisualizationCodeRequest.getFrameworkName() != null && !generateVisualizationCodeRequest.getFrameworkName().isEmpty()
                && generateVisualizationCodeRequest.getMethodName() != null && !generateVisualizationCodeRequest.getMethodName().isEmpty())
            visualizationCodeResponse.setVisualizationCode(visualizationEngineService.generateClientVisualizationCode(generateVisualizationCodeRequest.getFrameworkName(), generateVisualizationCodeRequest.getMethodName(), generateVisualizationCodeRequest.getDataSet(),generateVisualizationCodeRequest.getPortConfiguration(), generateVisualizationCodeRequest.getAdditionalParameters()));
        else
            visualizationCodeResponse.setVisualizationCode(visualizationEngineService.generateClientVisualizationCode(generateVisualizationCodeRequest.getFrameworkId(), generateVisualizationCodeRequest.getMethodId(), generateVisualizationCodeRequest.getDataSet(),generateVisualizationCodeRequest.getPortConfiguration(), generateVisualizationCodeRequest.getAdditionalParameters()));
        return visualizationCodeResponse;
    }

    @ExceptionHandler(VisualizationCodeGenerationException.class)
    public ResponseEntity<Object> handleVisualizationCodeGenerationException(VisualizationCodeGenerationException exception, HttpServletRequest request) {
        BaseErrorDTO error = BaseErrorDTO.createBaseErrorDTO(exception.getMessage(), "", "");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnTransformableData.class)
    public ResponseEntity<Object> handleDataTransformationException(UnTransformableData exception, HttpServletRequest request) {
        BaseErrorDTO error = BaseErrorDTO.createBaseErrorDTO(exception.getMessage(), "", "");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
