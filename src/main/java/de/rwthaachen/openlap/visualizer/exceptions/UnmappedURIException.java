package de.rwthaachen.openlap.visualizer.exceptions;

public class UnmappedURIException extends BaseException {

    public UnmappedURIException(String customMessage){
        super(customMessage, UnmappedURIException.class.getSimpleName(),"");
    }

}
