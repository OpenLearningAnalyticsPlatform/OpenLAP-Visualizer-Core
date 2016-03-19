package de.rwthaachen.openlap.visualizer.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.rwthaachen.openlap.visualizer.core.dtos.error.BaseErrorDTO;
import de.rwthaachen.openlap.visualizer.core.dtos.request.UpdateVisualizationFrameworkRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.request.UpdateVisualizationMethodRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.request.UploadVisualizationFrameworksRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.request.ValidateVisualizationMethodConfigurationRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.response.*;
import de.rwthaachen.openlap.visualizer.core.exceptions.*;
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

/**
 * A Spring controller which exposes an API for the client to upload new VisualizationFrameworks as well as
 * perform CRUD operations on VisualizationMethod and their VisualizationFrameworks
 *
 * @author Bassim Bashir
 */
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
    public DeleteVisualizationFrameworkResponse deleteVisualizationFramework(@PathVariable Long idOfFramework) {
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
        } catch (VisualizationFrameworkUploadException exception){
            throw  new VisualizationFrameworkUploadException(exception.getMessage());
        }catch (IOException exception){
            throw new RuntimeException (exception.getMessage());
        }
    }

    @RequestMapping(value = "/{idOfFramework}/methods/{idOfMethod}", method = RequestMethod.GET)
    public VisualizationMethodDetailsResponse getFrameworkMethodDetails(@PathVariable Long idOfFramework, @PathVariable Long idOfMethod) {
        VisualizationMethodDetailsResponse response = new VisualizationMethodDetailsResponse();
        response.setVisualizationMethod(visualizationFrameworkService.findVisualizationMethodById(idOfMethod));
        return response;
    }

    @RequestMapping(value = "/{idOfFramework}/methods/{idOfMethod}", method = RequestMethod.DELETE)
    public DeleteVisualizationMethodResponse deleteVisualizationMethod(@PathVariable Long idOfMethod) {
        DeleteVisualizationMethodResponse response = new DeleteVisualizationMethodResponse();
        response.setSuccess(visualizationFrameworkService.deleteVisualizationMethod(idOfMethod));
        return response;
    }

    @RequestMapping(value = "/{idOfFramework}/data_transformer/{idOfTransformer}", method = RequestMethod.DELETE)
    public DeleteDataTransformerResponse deleteDataTransformer(@PathVariable Long idOfTransformer) {
        DeleteDataTransformerResponse response = new DeleteDataTransformerResponse();
        response.setSuccess(visualizationFrameworkService.deleteDataTransformer(idOfTransformer));
        return response;
    }

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

    @RequestMapping(value = "/{idOfFramework}/methods/{idOfMethod}/configuration", method = RequestMethod.GET)
    public VisualizationMethodConfigurationResponse getMethodConfiguration(@PathVariable Long idOfFramework, @PathVariable Long idOfMethod) {
        VisualizationMethodConfigurationResponse response = new VisualizationMethodConfigurationResponse();
        response.setMethodConfiguration(visualizationFrameworkService.getMethodConfiguration(idOfMethod));
        return response;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public VisualizationFrameworksDetailsResponse getVisualizationFrameworks() {
        VisualizationFrameworksDetailsResponse visualizationFrameworksDetailsResponse = new VisualizationFrameworksDetailsResponse();
        visualizationFrameworksDetailsResponse.setVisualizationFrameworks(visualizationFrameworkService.findAllVisualizationFrameworks());
        return visualizationFrameworksDetailsResponse;
    }

    @ExceptionHandler(VisualizationFrameworkUploadException.class)
    public ResponseEntity<Object> handleVisualizationFrameworkUploadException(VisualizationFrameworkUploadException exception, HttpServletRequest request) {
        BaseErrorDTO error = BaseErrorDTO.createBaseErrorDTO(exception.getMessage(), "", "");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(VisualizationFrameworkDeletionException.class)
    public ResponseEntity<Object> handleVisualizationFrameworkDeletionException(VisualizationFrameworkDeletionException exception, HttpServletRequest request) {
        BaseErrorDTO error = BaseErrorDTO.createBaseErrorDTO(exception.getMessage(), "", "");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(VisualizationFrameworkNotFoundException.class)
    public ResponseEntity<Object> handleVisualizationFrameworkNotFoundException(VisualizationFrameworkNotFoundException exception, HttpServletRequest request) {
        BaseErrorDTO error = BaseErrorDTO.createBaseErrorDTO(exception.getMessage(), "", "");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VisualizationMethodNotFoundException.class)
    public ResponseEntity<Object> handleVisualizationMethodNotFoundException(VisualizationMethodNotFoundException exception, HttpServletRequest request) {
        BaseErrorDTO error = BaseErrorDTO.createBaseErrorDTO(exception.getMessage(), "", "");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error, headers, HttpStatus.NOT_FOUND);
    }

}
