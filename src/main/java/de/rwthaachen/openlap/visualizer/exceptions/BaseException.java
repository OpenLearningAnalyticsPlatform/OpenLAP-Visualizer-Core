package de.rwthaachen.openlap.visualizer.exceptions;

/**
 * All the exceptions should extend this base error and add their relevant
 * details
 */
public class BaseException extends RuntimeException {

    private String exceptionType;
    private String localStatusCode;

    protected BaseException(String message, String typeOfException, String localStatusCode) {
        super(message);
        this.exceptionType = typeOfException;
        this.localStatusCode = localStatusCode;
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
