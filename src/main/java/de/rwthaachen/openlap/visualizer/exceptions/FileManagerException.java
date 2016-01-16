package de.rwthaachen.openlap.visualizer.exceptions;

/**
 * Created by bas on 12/16/15.
 */
public class FileManagerException extends BaseException {

    public FileManagerException(String message) {
        super(message,FileManagerException.class.getSimpleName(),"");
    }
}
