package de.rwthaachen.openlap.visualizer.controller;

import de.rwthaachen.openlap.visualizer.dtos.error.BaseErrorDTO;
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
import java.util.Optional;

/**
 * Created by bas on 11/17/15.
 */
@RestController
@RequestMapping("/frameworks")
public class VisualizationFrameworksController {

    @Autowired
    VisualizationFrameworksService visualizationFrameworksService;

    @RequestMapping(value = "/{idOfFramework}/update", method = RequestMethod.PUT)
    public ResponseDTO updateVisualizationFramework(@PathVariable String idOfFramework, @RequestBody RequestDTO requestDTOBody) {
        return new ResponseDTO();
    }

    @RequestMapping(value = "/{idOfFramework}/delete", method = RequestMethod.DELETE)
    public DeleteVisualizationFrameworkResponse deleteVisualizationFramework(@PathVariable String idOfFramework){
        visualizationFrameworksService.deleteVisualizationFramework(idOfFramework);
        DeleteVisualizationFrameworkResponse response = new DeleteVisualizationFrameworkResponse();
        response.setSuccess(true);
        return response;
    }

    @RequestMapping(value = "/{idOfFramework}", method = RequestMethod.GET)
    public VisualizationFrameworkDetailsResponse getFrameworkDetails (@PathVariable long idOfFramework){
        VisualizationFrameworkDetailsResponse response = new VisualizationFrameworkDetailsResponse();
        response.setVisualizationFramework(visualizationFrameworksService.findVisualizationFrameworkById(idOfFramework));
        return response;

    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public UploadVisualizationFrameworkResponse uploadNewVisualizationFramework(@RequestParam ("frameworkJarBundle") MultipartFile frameworkJarBundle,
                                                                                @RequestParam ("frameworkConfiguration") List<VisualizationFramework> frameworkConfigurations){
        visualizationFrameworksService.uploadVisualizationFrameworks(frameworkConfigurations,frameworkJarBundle);
        UploadVisualizationFrameworkResponse response = new UploadVisualizationFrameworkResponse();
        response.setSuccess(true);
        return response;

    }

    @RequestMapping(value = "/{idOfFramework}/methods/{idOfMethod}", method = RequestMethod.GET)
    public VisualizationMethodDetailsResponse getFrameworkMethodDetails (@PathVariable long idOfFramework, @PathVariable long idOfMethod){
        VisualizationMethodDetailsResponse response = new VisualizationMethodDetailsResponse();
        VisualizationFramework visualizationFramework = visualizationFrameworksService.findVisualizationFrameworkById(idOfFramework);
        if(visualizationFramework != null) {
            Optional<VisualizationMethod> visualizationMethod = visualizationFramework.getVisualizationMethods()
                    .stream()
                    .filter((method) -> method.getId() == idOfMethod)
                    .findFirst();
            if(visualizationMethod.isPresent())
                response.setVisualizationMethod(visualizationMethod.get());
            else
                response.setVisualizationMethod(new VisualizationMethod("",""));
        }else{
            response.setVisualizationMethod(new VisualizationMethod("",""));
        }
        return response;
    }

    /**
     * update only the attributes such as description, data transformer of the method*/
    @RequestMapping(value = "/{idOfFramework}/methods/{idOfMethod}", method = RequestMethod.PUT)
    public UpdateVisualizationMethodResponse updateVisualizationFrameworkMethod (@PathVariable long idOfFramework, @PathVariable long idOfMethod, @RequestBody VisualizationMethod visualizationMethod){
        UpdateVisualizationMethodResponse response = new UpdateVisualizationMethodResponse();
        VisualizationFramework visualizationFramework = visualizationFrameworksService.findVisualizationFrameworkById(idOfFramework);
        if(visualizationFramework != null) {
            Optional<VisualizationMethod> visMethod = visualizationFramework.getVisualizationMethods()
                    .stream()
                    .filter((method) -> method.getId() == idOfMethod)
                    .findFirst();
            if (visMethod.isPresent()) {

            }
        }
        return response;
    }

    @ExceptionHandler(VisualizationFrameworksUploadException.class)
    public ResponseEntity<Object> handleVisFrameworkUploadException(VisualizationFrameworksUploadException exception, HttpServletRequest request) {
        BaseErrorDTO error = BaseErrorDTO.createBaseErrorDTO(exception.getMessage(),"","");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error,headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(VisualizationFrameworkDeletionException.class)
    public ResponseEntity<Object> handleVisFrameworkUploadException(VisualizationFrameworkDeletionException exception, HttpServletRequest request) {
        BaseErrorDTO error = BaseErrorDTO.createBaseErrorDTO(exception.getMessage(),"","");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error,headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
