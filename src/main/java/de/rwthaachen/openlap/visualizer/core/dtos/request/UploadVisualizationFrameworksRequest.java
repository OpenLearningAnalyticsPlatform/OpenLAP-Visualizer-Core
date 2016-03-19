package de.rwthaachen.openlap.visualizer.core.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadVisualizationFrameworksRequest {

    private List<VisualizationFramework> visualizationFrameworks;

    public List<VisualizationFramework> getVisualizationFrameworks() {
        return visualizationFrameworks;
    }

    public void setVisualizationFrameworks(List<VisualizationFramework> visualizationFrameworks) {
        this.visualizationFrameworks = visualizationFrameworks;
    }
}
