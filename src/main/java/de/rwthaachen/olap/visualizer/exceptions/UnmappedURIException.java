package de.rwthaachen.olap.visualizer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_IMPLEMENTED, reason="No such endpoint implemented")
public class UnmappedURIException extends BaseException {

    @Override
    public HttpStatus getHTTPStatusCode() {
        return HttpStatus.NOT_IMPLEMENTED;
    }

    public UnmappedURIException(String customMessage){
        super(customMessage);
    }

}
