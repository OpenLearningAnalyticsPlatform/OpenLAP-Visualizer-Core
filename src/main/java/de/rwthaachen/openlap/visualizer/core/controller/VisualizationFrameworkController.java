package de.rwthaachen.openlap.visualizer.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.rwthaachen.openlap.visualizer.core.dtos.error.BaseErrorDTO;
import de.rwthaachen.openlap.visualizer.core.dtos.request.UpdateVisualizationFrameworkRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.request.UpdateVisualizationMethodRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.request.UploadVisualizationFrameworksRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.request.ValidateVisualizationMethodConfigurationRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.response.*;
import de.rwthaachen.openlap.visualizer.core.exceptions.DataSetValidationException;
import de.rwthaachen.openlap.visualizer.core.exceptions.VisualizationFrameworkDeletionException;
import de.rwthaachen.openlap.visualizer.core.exceptions.VisualizationFrameworksUploadException;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationMethod;
import de.rwthaachen.openlap.visualizer.core.service.VisualizationFrameworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/frameworks")
public class VisualizationFrameworkController {

    @Autowired
    VisualizationFrameworkService visualizationFrameworkService;

    @RequestMapping(value = "/{idOfFramework}/update", method = RequestMethod.PUT)
    public UpdateVisualizationFrameworkResponse updateVisualizationFramework(@PathVariable Long idOfFramework, @RequestBody UpdateVisualizationFrameworkRequest updateVisualizationFrameworkRequest) {
        UpdateVisualizationFrameworkResponse response = new UpdateVisualizationFrameworkResponse();
        response.setVisualizationFramework(visualizationFrameworkService.updateVisualizationFrameworkAttributes(updateVisualizationFrameworkRequest.getVisualizationFramework(), idOfFramework));
        return response;
    }

    @RequestMapping(value = "/{idOfFramework}", method = RequestMethod.DELETE)
    public DeleteVisualizationFrameworkResponse deleteVisualizationFramework(@PathVariable String idOfFramework) {
        DeleteVisualizationFrameworkResponse response = new DeleteVisualizationFrameworkResponse();
        response.setSuccess(visualizationFrameworkService.deleteVisualizationFramework(idOfFramework));
        return response;
    }

    @RequestMapping(value = "/{idOfFramework}", method = RequestMethod.GET)
    public VisualizationFrameworkDetailsResponse getFrameworkDetails(@PathVariable Long idOfFramework) {
        VisualizationFrameworkDetailsResponse response = new VisualizationFrameworkDetailsResponse();
        response.setVisualizationFramework(visualizationFrameworkService.findVisualizationFrameworkById(idOfFramework));
        return response;

    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public UploadVisualizationFrameworkResponse uploadNewVisualizationFramework(@RequestParam("frameworkJarBundle") MultipartFile frameworkJarBundle,
                                                                                @RequestParam ("frameworkConfig") String frameworksUploadRequest) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UploadVisualizationFrameworksRequest request = objectMapper.readValue(frameworksUploadRequest, UploadVisualizationFrameworksRequest.class);
            visualizationFrameworkService.uploadVisualizationFrameworks(request.getVisualizationFrameworks(), frameworkJarBundle);
            UploadVisualizationFrameworkResponse response = new UploadVisualizationFrameworkResponse();
            response.setSuccess(true);
            return response;
        }catch (IOException ioexception){
            throw new VisualizationFrameworksUploadException("Frameworks config not properly formed : "+ioexception.getMessage());
        }

    }

    @RequestMapping(value = "/{idOfFramework}/methods/{idOfMethod}", method = RequestMethod.GET)
    public VisualizationMethodDetailsResponse getFrameworkMethodDetails(@PathVariable Long idOfFramework, @PathVariable Long idOfMethod) {
        VisualizationMethodDetailsResponse response = new VisualizationMethodDetailsResponse();
        VisualizationMethod visualizationMethod = visualizationFrameworkService.findVisualizationMethodById(idOfMethod);
        if (visualizationMethod != null)
            response.setVisualizationMethod(visualizationMethod);
        else
            response.setVisualizationMethod(new VisualizationMethod("", "",null,null));
        return response;
    }

    /**
     * update only the attributes such as description, data transformer of the method
     */
    @RequestMapping(value = "/{idOfFramework}/methods/{idOfMethod}", method = RequestMethod.PUT)
    public UpdateVisualizationMethodResponse updateVisualizationFrameworkMethod(@PathVariable Long idOfFramework, @PathVariable Long idOfMethod, @RequestBody UpdateVisualizationMethodRequest updateVisualizationMethodRequest) {
        UpdateVisualizationMethodResponse response = new UpdateVisualizationMethodResponse();
        response.setVisualizationMethod(visualizationFrameworkService.updateVisualizationMethodAttributes(updateVisualizationMethodRequest.getVisualizationMethod(), idOfMethod));
        return response;
    }

    @RequestMapping(value = "/{idOfFramework}/methods/{idOfMethod}/validateConfiguration", method = RequestMethod.POST)
    public ValidateVisualizationMethodConfigurationResponse validateMethodConfiguration(@PathVariable Long idOfFramework, @PathVariable Long idOfMethod, @RequestBody ValidateVisualizationMethodConfigurationRequest validateVisualizationMethodConfigurationRequest) {
        ValidateVisualizationMethodConfigurationResponse response = new ValidateVisualizationMethodConfigurationResponse();
        try {
            response.setConfigurationValid(visualizationFrameworkService.validateVisualizationMethodConfiguration(idOfMethod, validateVisualizationMethodConfigurationRequest.getConfigurationMapping()));
        } catch (DataSetValidationException exception) {
            response.setConfigurationValid(false);
            response.setValidationMessage(exception.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public VisualizationFrameworksDetailsResponse getVisualizationFrameworks() {
        VisualizationFrameworksDetailsResponse visualizationFrameworksDetailsResponse = new VisualizationFrameworksDetailsResponse();
        visualizationFrameworksDetailsResponse.setVisualizationFrameworks(visualizationFrameworkService.findAllVisualizationFrameworks());
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
