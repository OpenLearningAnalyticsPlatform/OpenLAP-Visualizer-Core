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
 * A service which provides functions to perform operations on the system's storage.
 *
 * @author Bassim Bashir
 */
@Service
public class FileManager {

    @Autowired
    private ConfigurationService configurationService;

    /**
     * Saves the provided file in the storage.
     *
     * @param fileName   The custom name of the file. If the name is null or empty then the name of the provided file will be used
     * @param fileToSave The file to save
     * @throws FileManagerException If the saving of the file was not successful
     */
    public void saveFile(String fileName, MultipartFile fileToSave) throws FileManagerException {
        if (configurationService.getFileManagerStorageLocation() == null || fileToSave == null || configurationService.getFileManagerStorageLocation().isEmpty())
            throw new FileManagerException("Saving file failed");
        if (fileToSave.isEmpty())
            throw new FileManagerException("The file has no contents");
        // if the filename is not provided then take the name of the provided jar file
        if (fileName == null || fileName.isEmpty())
            fileName = fileToSave.getName();

        try {
            //create the the file storage directory if it does not exist
            File directory = new File(configurationService.getFileManagerStorageLocation());
            if (!directory.exists())
                directory.mkdir();
            //first copy to a temp directory and then use the ATOMIC_MOVE to avoid any thread issues
            Path fileTemporaryStorageLocation = Paths.get(configurationService.getFileManagerStorageLocation(), configurationService.getFileManagerTempStorageLocation(), fileName);
            Files.copy(fileToSave.getInputStream(), fileTemporaryStorageLocation, StandardCopyOption.REPLACE_EXISTING);
            try {
                Files.move(fileTemporaryStorageLocation.resolve(fileName), Paths.get(configurationService.getFileManagerStorageLocation(), fileName), StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException atomicMoveNotSupported) {
                //if the atomic move didn't work then try copying over the file
                Files.copy(fileTemporaryStorageLocation.resolve(fileName), Paths.get(configurationService.getFileManagerStorageLocation(), fileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException | SecurityException exception) {
            throw new FileManagerException(exception.getMessage());
        }
    }

    /**
     * @return A list of file name in the system's storage
     */
    public List<String> listFilesInDirectory(String location) {
        File fileDirectory = Paths.get(location).toFile();
        if (fileDirectory.isDirectory())
            return Arrays.asList(fileDirectory.list());
        else
            return new ArrayList<>();
    }

    /**
     * Deletes a file from the system's storage
     *
     * @param fileToDelete The name of the file to delete
     * @throws FileManagerException If the file could not be deleted
     */
    public void deleteFile(String fileToDelete) throws FileManagerException {
        Path filePath = Paths.get(configurationService.getFileManagerStorageLocation(), fileToDelete);
        try {
            Files.delete(filePath);
        } catch (IOException exception) {
            throw new FileManagerException(exception.getMessage());
        }
    }
}
