package de.rwthaachen.olap.visualizer.exceptions;

import org.springframework.http.HttpStatus;

/**
 * All the exceptions should extend this base error and add their relevant
 * details
 */
public abstract class BaseException extends RuntimeException{

    public abstract HttpStatus getHTTPStatusCode ();

    protected BaseException(String message){
        super(message);
    }
}
