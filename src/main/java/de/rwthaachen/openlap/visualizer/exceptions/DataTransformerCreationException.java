package de.rwthaachen.openlap.visualizer.exceptions;

/**
 * Created by bas on 12/9/15.
 */
public class DataTransformerCreationException extends BaseException {

    public DataTransformerCreationException(String message) {
        super(message,DataTransformerCreationException.class.getSimpleName(),"");
    }

}
