package de.rwthaachen.openlap.visualizer.core.exceptions;

/**
 * Created by bas on 1/12/16.
 */
public class UnTransformableData extends BaseException{
    public UnTransformableData(String message) {
        super(message,UnTransformableData.class.getSimpleName(),"");
    }
}
