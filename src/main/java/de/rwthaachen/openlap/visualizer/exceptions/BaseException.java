package de.rwthaachen.openlap.visualizer.exceptions;

import de.rwthaachen.openlap.visualizer.OpenLAPVisualizerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * All the exceptions should extend this base error and add their relevant
 * details
 */
public class BaseException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(OpenLAPVisualizerApplication.class);
    private String exceptionType;
    private String localStatusCode;

    protected BaseException(String message, String typeOfException, String localStatusCode) {
        super(message);
        this.exceptionType = typeOfException;
        this.localStatusCode = localStatusCode;
        logger.error(message,this); // log the error
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getLocalStatusCode() {
        return localStatusCode;
    }

    public void setLocalStatusCode(String localStatusCode) {
        this.localStatusCode = localStatusCode;
    }
}