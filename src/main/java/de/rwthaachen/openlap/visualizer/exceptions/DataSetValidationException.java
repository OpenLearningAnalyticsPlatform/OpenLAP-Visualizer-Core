package de.rwthaachen.openlap.visualizer.exceptions;

/**
 * Created by bas on 1/17/16.
 */
public class DataSetValidationException extends BaseException {
    public DataSetValidationException(String message) {
        super(message, DataSetValidationException.class.getSimpleName(), "");

    }
}

