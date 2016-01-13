package de.rwthaachen.openlap.visualizer.dtos.request;

import de.rwthaachen.openlap.visualizer.model.VisualizationFramework;

import java.util.List;

/**
 * Request DTO representing the contents of an incoming request to upload visualization frameworks
 */
public class UploadVisualizationFrameworksRequest {

    private List<VisualizationFramework> visualizationFrameworks;

    public List<VisualizationFramework> getVisualizationFrameworks() {
        return visualizationFrameworks;
    }

    public void setVisualizationFrameworks(List<VisualizationFramework> visualizationFrameworks) {
        this.visualizationFrameworks = visualizationFrameworks;
    }
}
