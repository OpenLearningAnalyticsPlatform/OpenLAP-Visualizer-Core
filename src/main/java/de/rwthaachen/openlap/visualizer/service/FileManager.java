package de.rwthaachen.openlap.visualizer.service;

import de.rwthaachen.openlap.visualizer.exceptions.FileManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bas on 12/16/15.
 */
@Service
public class FileManager {

    @Autowired
    private ConfigurationService configurationService;

    public void saveFile(String fileName, MultipartFile fileToSave) throws FileManagerException {
        if (configurationService.getFileManagerStorageLocation() == null || fileToSave == null || configurationService.getFileManagerStorageLocation().isEmpty())
            throw new FileManagerException("Saving file failed");
        if (fileToSave.isEmpty())
            throw new FileManagerException("The file has no contents");
        try {
            //create the the file storage directory if it does not exist
            File directory = new File(configurationService.getFileManagerStorageLocation());
            if(!directory.exists())
                directory.mkdir();
            //first copy to a temp directory and then use the ATOMIC_MOVE to avoid any thread issues
            Path fileTemporaryStorageLocation = Paths.get(configurationService.getFileManagerStorageLocation(), configurationService.getFileManagerTempStorageLocation());
            Files.copy(fileToSave.getInputStream(), fileTemporaryStorageLocation, StandardCopyOption.REPLACE_EXISTING);
            try {
                Files.move(fileTemporaryStorageLocation.resolve(fileName), Paths.get(configurationService.getFileManagerStorageLocation(), fileName), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            }catch (AtomicMoveNotSupportedException atomicMoveNotSupported){
                // TODO: simply copy the file over if the atomic move is not supported
            }
        } catch (IOException | SecurityException exception) {
            throw new FileManagerException(exception.getLocalizedMessage());
        }
    }

    public List<String> listFilesInDirectory(String location) {
        File fileDirectory = Paths.get(location).toFile();
        if (fileDirectory.isDirectory())
            return Arrays.asList(fileDirectory.list());
        else
            return new ArrayList<>();
    }

    public void deleteFile(String fileToDelete) throws FileManagerException{
        Path filePath = Paths.get(configurationService.getFileManagerStorageLocation(),fileToDelete);
        try {
            Files.delete(filePath);
        }catch (IOException exception){
            throw new FileManagerException(exception.getLocalizedMessage());
        }
    }
}
