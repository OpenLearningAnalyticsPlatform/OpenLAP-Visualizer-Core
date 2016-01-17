package de.rwthaachen.openlap.visualizer.dtos.response;

import de.rwthaachen.openlap.visualizer.model.VisualizationFramework;

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
