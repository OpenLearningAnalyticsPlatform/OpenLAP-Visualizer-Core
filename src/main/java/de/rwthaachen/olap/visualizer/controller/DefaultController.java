package de.rwthaachen.olap.visualizer.controller;

import de.rwthaachen.olap.visualizer.exceptions.UnmappedURIException;
import de.rwthaachen.olap.visualizer.models.error.BaseError;
import de.rwthaachen.olap.visualizer.models.request.Request;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller which contains the code to send as a response for any unmapped requests
 */
@RestController
public class DefaultController {
    @RequestMapping("/**")
    public void unmappedRequest(@RequestBody Request<?> requestBody, HttpServletRequest request) {
        String uri = request.getRequestURI();
        throw new UnmappedURIException("The endpoint " + uri + " is not supported");
    }

    @ExceptionHandler(UnmappedURIException.class)
    public ResponseEntity<Object> handleUnmappedURIException(UnmappedURIException exception, HttpServletRequest request) {
        //log the error
        //create a client response
        BaseError error = new BaseError(exception.getLocalStatusCode(),exception.getLocalizedMessage(),"","");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(error,headers,HttpStatus.NOT_IMPLEMENTED);
    }
}
