package de.rwthaachen.olap.visualizer.exceptions;

public class UnmappedURIException extends BaseException {

    @Override
    public String getLocalStatusCode() {
        return "UnmappedURI";
    }

    public UnmappedURIException(String customMessage){
        super(customMessage);
    }

}
