package de.rwthaachen.openlap.visualizer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by bas on 1/12/16.
 */
@Service
public class ConfigurationService {

    @Value("${visualizationFrameworksJarStorageLocation}")
    String visualizationFrameworksJarStorageLocation;

    public String getVisualizationFrameworksJarStorageLocation() {
        return visualizationFrameworksJarStorageLocation;
    }

    public void setVisualizationFrameworksJarStorageLocation(String visualizationFrameworksJarStorageLocation) {
        this.visualizationFrameworksJarStorageLocation = visualizationFrameworksJarStorageLocation;
    }
}
