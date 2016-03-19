package de.rwthaachen.openlap.visualizer.core.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateVisualizationFrameworkResponse {
    private VisualizationFramework visualizationFramework;

    public VisualizationFramework getVisualizationFramework() {
        return visualizationFramework;
    }

    public void setVisualizationFramework(VisualizationFramework visualizationFramework) {
        this.visualizationFramework = visualizationFramework;
    }
}
