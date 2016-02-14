package de.rwthaachen.openlap.visualizer.core.service;

import de.rwthaachen.openlap.visualizer.core.exceptions.FileManagerException;
import org.apache.commons.io.FileUtils;
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
     * @return The absolute location where the file is stored
     */
    public String saveJarFile(String fileName, MultipartFile fileToSave) throws FileManagerException {
        if (configurationService.getFileManagerStorageLocation() == null || fileToSave == null || configurationService.getFileManagerStorageLocation().isEmpty())
            throw new FileManagerException("Saving file failed");
        if (fileToSave.isEmpty())
            throw new FileManagerException("The file has no contents");
        // if the filename is not provided
        if (fileName == null || fileName.isEmpty()){
            // then generate one with the timestamp
            fileName = "jar_"+fileToSave.getName()+"_"+System.currentTimeMillis();
        }
        fileName+=configurationService.getJarBundleExtension(); //add the JAR extension

        try {
            //create the the file storage directory if it does not exist
            Path fileStorageLocation = Paths.get(configurationService.getFileManagerStorageLocation());
           /* if (!fileStorageLocation.toFile().exists())
                fileStorageLocation.toFile().mkdirs();*/

            //first copy to a temp directory and then use the ATOMIC_MOVE to avoid any thread issues
            //Path fileTemporaryStorageLocation = Paths.get(configurationService.getFileManagerStorageLocation(), configurationService.getFileManagerTempStorageLocation());
            /*if(!fileTemporaryStorageLocation.toFile().exists())
                fileTemporaryStorageLocation.toFile().mkdirs();*/

            //Files.copy(fileToSave.getInputStream(),Paths.get(fileTemporaryStorageLocation.toString()),StandardCopyOption.REPLACE_EXISTING);
            FileUtils.copyInputStreamToFile(fileToSave.getInputStream(),Paths.get(fileStorageLocation.toString(),fileName).toFile());
            Path fileFinalPath = Paths.get(configurationService.getFileManagerStorageLocation(), fileName);
            /*try {
                Files.move(Paths.get(fileTemporaryStorageLocation.toString()), Paths.get(fileStorageLocation.toString()) , StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException atomicMoveNotSupported) {
                //if the atomic move didn't work then try copying over the file
                Files.copy(Paths.get(fileTemporaryStorageLocation.toString(),fileName), Paths.get(fileStorageLocation.toString()), StandardCopyOption.REPLACE_EXISTING);
            }*/
            return fileFinalPath.toString();
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
        Path filePath = Paths.get(fileToDelete);
        try {
            Files.delete(filePath);
        } catch (IOException exception) {
            throw new FileManagerException(exception.getMessage());
        }
    }
}
