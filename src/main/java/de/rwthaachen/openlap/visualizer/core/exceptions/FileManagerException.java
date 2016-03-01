package de.rwthaachen.openlap.visualizer.core.exceptions;

public class FileManagerException extends BaseException {

    public FileManagerException(String message) {
        super(message,FileManagerException.class.getSimpleName(),"");
    }
}
