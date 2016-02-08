package de.rwthaachen.openlap.visualizer.core.dtos.response;

import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;

/**
 * Created by bas on 11/17/15.
 */
public class UpdateVisualizationFrameworkResponse {
    private VisualizationFramework visualizationFramework;

    public VisualizationFramework getVisualizationFramework() {
        return visualizationFramework;
    }

    public void setVisualizationFramework(VisualizationFramework visualizationFramework) {
        this.visualizationFramework = visualizationFramework;
    }
}
