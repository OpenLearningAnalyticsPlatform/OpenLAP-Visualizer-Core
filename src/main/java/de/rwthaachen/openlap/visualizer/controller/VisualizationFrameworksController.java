package de.rwthaachen.openlap.visualizer.controller;

import de.rwthaachen.openlap.visualizer.dtos.error.BaseErrorDTO;
import de.rwthaachen.openlap.visualizer.dtos.request.UpdateVisualizationFrameworkRequest;
import de.rwthaachen.openlap.visualizer.dtos.request.UpdateVisualizationMethodRequest;
import de.rwthaachen.openlap.visualizer.dtos.response.*;
import de.rwthaachen.openlap.visualizer.exceptions.VisualizationFrameworkDeletionException;
import de.rwthaachen.openlap.visualizer.exceptions.VisualizationFrameworksUploadException;
import de.rwthaachen.openlap.visualizer.model.VisualizationFramework;
import de.rwthaachen.openlap.visualizer.model.VisualizationMethod;
import de.rwthaachen.openlap.visualizer.service.VisualizationFrameworksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/frameworks")
public class VisualizationFrameworksController {

    @Autowired
    VisualizationFrameworksService visualizationFrameworksService;

    @RequestMapping(value = "/{idOfFramework}/update", method = RequestMethod.PUT)
    public UpdateVisualizationFrameworkResponse updateVisualizationFramework(@PathVariable long idOfFramework, @RequestBody UpdateVisualizationFrameworkRequest updateVisualizationFrameworkRequest) {
        UpdateVisualizationFrameworkResponse response = new UpdateVisualizationFrameworkResponse();
        visualizationFrameworksService.updateVisualizationFrameworkAttributes(updateVisualizationFrameworkRequest.getVisualizationFramework(), idOfFramework);
        response.setSuccess(true);
        return response;
    }

    @RequestMapping(value = "/{idOfFramework}/delete", method = RequestMethod.DELETE)
    public DeleteVisualizationFrameworkResponse deleteVisualizationFramework(@PathVariable String idOfFramework) {
        visualizationFrameworksService.deleteVisualizationFramework(idOfFramework);
        DeleteVisualizationFrameworkResponse response = new DeleteVisualizationFrameworkResponse();
        response.setSuccess(true);
        return response;
    }

    @RequestMapping(value = "/{idOfFramework}", method = RequestMethod.GET)
    public VisualizationFrameworkDetailsResponse getFrameworkDetails(@PathVariable long idOfFramework) {
        VisualizationFrameworkDetailsResponse response = new VisualizationFrameworkDetailsResponse();
        response.setVisualizationFramework(visualizationFrameworksService.findVisualizationFrameworkById(idOfFramework));
        return response;

    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public UploadVisualizationFrameworkResponse uploadNewVisualizationFramework(@RequestParam("frameworkJarBundle") MultipartFile frameworkJarBundle,
                                                                                @RequestParam("frameworkConfiguration") List<VisualizationFramework> frameworkConfigurations) {
        visualizationFrameworksService.uploadVisualizationFrameworks(frameworkConfigurations, frameworkJarBundle);
        UploadVisualizationFrameworkResponse response = new UploadVisualizationFrameworkResponse();
        response.setSuccess(true);
        return response;

    }

    @RequestMapping(value = "/{idOfFramework}/methods/{idOfMethod}", method = RequestMethod.GET)
    public VisualizationMethodDetailsResponse getFrameworkMethodDetails(@PathVariable long idOfFramework, @PathVariable long idOfMethod) {
        VisualizationMethodDetailsResponse response = new VisualizationMethodDetailsResponse();
        VisualizationMethod visualizationMethod = visualizationFrameworksService.findVisualizationMethodById(idOfMethod);
        if (visualizationMethod != null)
            response.setVisualizationMethod(visualizationMethod);
        else
            response.setVisualizationMethod(new VisualizationMethod("", ""));
        return response;
    }

    /**
     * update only the attributes such as description, data transformer of the method
     */
    @RequestMapping(value = "/{idOfFramework}/methods/{idOfMethod}", method = RequestMethod.PUT)
    public UpdateVisualizationMethodResponse updateVisualizationFrameworkMethod(@PathVariable long idOfFramework, @PathVariable long idOfMethod, @RequestBody UpdateVisualizationMethodRequest updateVisualizationMethodRequest) {
        UpdateVisualizationMethodResponse response = new UpdateVisualizationMethodResponse();
        visualizationFrameworksService.updateVisualizationMethodAttributes(updateVisualizationMethodRequest.getVisualizationMethod(), idOfMethod);
        response.setSuccess(true);
        return response;
    }

    @RequestMapping(value = "/frameworks", method = RequestMethod.GET)
    public VisualizationFrameworksDetailsResponse getVisualizationFrameworks() {
        VisualizationFrameworksDetailsResponse visualizationFrameworksDetailsResponse = new VisualizationFrameworksDetailsResponse();
        visualizationFrameworksDetailsResponse.setVisualizationFrameworks(visualizationFrameworksService.findAllVisualizationFrameworks());
        return visualizationFrameworksDetailsResponse;
    }

    @ExceptionHandler(VisualizationFrameworksUploadException.class)
    public ResponseEntity<Object> handleVisFrameworkUploadException(VisualizationFrameworksUploadException exception, HttpServletRequest request) {
        BaseErrorDTO error = BaseErrorDTO.createBaseErrorDTO(exception.getMessage(), "", "");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(VisualizationFrameworkDeletionException.class)
    public ResponseEntity<Object> handleVisFrameworkUploadException(VisualizationFrameworkDeletionException exception, HttpServletRequest request) {
        BaseErrorDTO error = BaseErrorDTO.createBaseErrorDTO(exception.getMessage(), "", "");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
