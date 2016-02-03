package de.rwthaachen.openlap.visualizer.core.exceptions;

public class DataRepositoryException extends BaseException {

    public DataRepositoryException(String message){
        super(message,DataRepositoryException.class.getSimpleName(),"");
    }
}
