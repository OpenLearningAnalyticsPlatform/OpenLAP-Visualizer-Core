package de.rwthaachen.openlap.visualizer.core.exceptions;

public class DataSetValidationException extends BaseException {
    public DataSetValidationException(String message) {
        super(message, DataSetValidationException.class.getSimpleName(), "");

    }
}

