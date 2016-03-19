package de.rwthaachen.openlap.visualizer.core.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateVisualizationFrameworkRequest {

    private VisualizationFramework visualizationFramework;

    public VisualizationFramework getVisualizationFramework() {
        return visualizationFramework;
    }

    public void setVisualizationFramework(VisualizationFramework visualizationFramework) {
        this.visualizationFramework = visualizationFramework;
    }
}
