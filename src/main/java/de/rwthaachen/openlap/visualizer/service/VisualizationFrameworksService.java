package de.rwthaachen.openlap.visualizer.service;

import de.rwthaachen.openlap.visualizer.dao.VisualizationFrameworkRepository;
import de.rwthaachen.openlap.visualizer.exceptions.DataRepositoryException;
import de.rwthaachen.openlap.visualizer.framework.validators.VisualizationFrameworksUploadValidator;
import de.rwthaachen.openlap.visualizer.model.VisualizationFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bas on 12/12/15.
 */
@Service
public class VisualizationFrameworksService {

    private static String JAR_FILE_EXTENSION = ".jar";
    @Value("${visualizationFrameworksJarStorageLocation}")
    String visualizationFrameworksJarStorageLocation;
    @Autowired
    private VisualizationFrameworkRepository visualizationFrameworkRepository;

    public List<VisualizationFramework> findAllVisualizationFrameworks() {
        List<VisualizationFramework> visualizationFrameworkList = new ArrayList<>();
        visualizationFrameworkRepository.findAll().forEach(visualizationFrameworkList::add);
        return visualizationFrameworkList;
    }

    public VisualizationFramework findVisualizationFrameworkByName(String visualizationFrameworkName) throws DataRepositoryException {
        if (visualizationFrameworkName != null && !visualizationFrameworkName.isEmpty()) {
            return visualizationFrameworkRepository.findByName(visualizationFrameworkName);
        } else {
            throw new DataRepositoryException("Provided visualization framework name is invalid : " + visualizationFrameworkName);
        }
    }

    public void uploadVisualizationFrameworks(List<VisualizationFramework> frameworkList, MultipartFile jarFile) {
        // set the file location in all the framework objects
        frameworkList.forEach(framework -> framework.setFrameworkLocation(Paths.get(visualizationFrameworksJarStorageLocation,jarFile.getName()+JAR_FILE_EXTENSION).toAbsolutePath().toString()));
        //validate the jar classes first before passing it on to the File Manager
        VisualizationFrameworksUploadValidator visualizationFrameworksUploadValidator = new VisualizationFrameworksUploadValidator();
        if(visualizationFrameworksUploadValidator.validateVisualizationFrameworksUploadConfiguration(frameworkList,jarFile)){
            //if the configuration is valid then perform the upload, i.e db entries and copying of the jar

        }else{
            //TODO: report the error, possibly the validator should return the information of what went wrong
        }
    }
}
