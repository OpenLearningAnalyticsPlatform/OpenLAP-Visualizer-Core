package de.rwthaachen.openlap.visualizer.exceptions;

public class DataRepositoryException extends BaseException {

    public DataRepositoryException(String message) {
        super(message, DataRepositoryException.class.getSimpleName(), "");
    }
}
