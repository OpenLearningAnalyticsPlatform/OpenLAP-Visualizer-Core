package de.rwthaachen.openlap.visualizer.core.dtos.response;

import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;

import java.util.List;

/**
 * Created by bas on 1/17/16.
 */
public class VisualizationFrameworksDetailsResponse {

    private List<VisualizationFramework> visualizationFrameworks;

    public List<VisualizationFramework> getVisualizationFrameworks() {
        return visualizationFrameworks;
    }

    public void setVisualizationFrameworks(List<VisualizationFramework> visualizationFrameworks) {
        this.visualizationFrameworks = visualizationFrameworks;
    }
}
