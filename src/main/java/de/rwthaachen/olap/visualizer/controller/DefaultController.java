package de.rwthaachen.olap.visualizer.controller;

import de.rwthaachen.olap.visualizer.exceptions.UnmappedURIException;
import de.rwthaachen.olap.visualizer.models.error.BaseError;
import de.rwthaachen.olap.visualizer.models.request.Request;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller which contains the code to send as a response for any unmapped requests
 */
@RestController
public class DefaultController {
    @RequestMapping("/**")
    public void unmappedRequest(@RequestBody Request requestBody, HttpServletRequest request) {
        String uri = request.getRequestURI();
        throw new UnmappedURIException("The endpoint " + uri + " is not supported");
    }

    @ExceptionHandler(UnmappedURIException.class)
    public BaseError handleUnmappedURIException(UnmappedURIException exception, HttpServletRequest request) {
        //log the error
        //create a client response
        BaseError error = new BaseError();
        error.setHttpStatusCode(exception.getHTTPStatusCode().toString());
        error.setErrorMessage(exception.getLocalizedMessage());
        return error;
    }
}
