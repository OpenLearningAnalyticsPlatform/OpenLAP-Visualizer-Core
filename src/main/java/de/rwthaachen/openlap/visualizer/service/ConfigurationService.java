package de.rwthaachen.openlap.visualizer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by bas on 1/12/16.
 */
@Service
public class ConfigurationService {

    @Value("${visualizationFrameworksJarStorageLocation}")
    private String visualizationFrameworksJarStorageLocation;

    @Value("${fileManagerStorageLocation}")
    private String fileManagerStorageLocation;

    @Value("${fileManagerTempStorageLocation}")
    private String fileManagerTempStorageLocation;

    public String getVisualizationFrameworksJarStorageLocation() {
        return visualizationFrameworksJarStorageLocation;
    }

    public void setVisualizationFrameworksJarStorageLocation(String visualizationFrameworksJarStorageLocation) {
        this.visualizationFrameworksJarStorageLocation = visualizationFrameworksJarStorageLocation;
    }

    public String getFileManagerStorageLocation() {
        return fileManagerStorageLocation;
    }

    public void setFileManagerStorageLocation(String fileManagerStorageLocation) {
        this.fileManagerStorageLocation = fileManagerStorageLocation;
    }

    public String getFileManagerTempStorageLocation() {
        return fileManagerTempStorageLocation;
    }

    public void setFileManagerTempStorageLocation(String fileManagerTempStorageLocation) {
        this.fileManagerTempStorageLocation = fileManagerTempStorageLocation;
    }
}
