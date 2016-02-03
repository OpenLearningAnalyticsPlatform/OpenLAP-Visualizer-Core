package de.rwthaachen.openlap.visualizer.core.dtos.request;

import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;

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
