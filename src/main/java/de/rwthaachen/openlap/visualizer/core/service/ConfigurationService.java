package de.rwthaachen.openlap.visualizer.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service which contains the configuration parameters of the System. All the properties added to the properties file should be made accessible throughout the system
 * via this service
 *
 * @author Bassim Bashir
 *
 */
@Service
public class ConfigurationService {

    @Value("${visualizationFrameworksJarStorageLocation}")
    private String visualizationFrameworksJarStorageLocation;

    @Value("${fileManagerStorageLocation}")
    private String fileManagerStorageLocation;

    @Value("${fileManagerTempStorageLocation}")
    private String fileManagerTempStorageLocation;

    @Value("${jarBundleExtension}")
    private String jarBundleExtension;

    @Value("${apiVersionNumber}")
    private String apiVersionNumber;

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

    public String getJarBundleExtension() {
        return jarBundleExtension;
    }

    public void setJarBundleExtension(String jarBundleExtension) {
        this.jarBundleExtension = jarBundleExtension;
    }

    public String getApiVersionNumber() {
        return apiVersionNumber;
    }

    public void setApiVersionNumber(String apiVersionNumber) {
        this.apiVersionNumber = apiVersionNumber;
    }
}
