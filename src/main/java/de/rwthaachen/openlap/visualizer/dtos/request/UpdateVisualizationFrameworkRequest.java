package de.rwthaachen.openlap.visualizer.dtos.request;

import de.rwthaachen.openlap.visualizer.model.VisualizationFramework;

/**
 * Created by bas on 1/16/16.
 */
public class UpdateVisualizationFrameworkRequest {

    private VisualizationFramework visualizationFramework;

    public VisualizationFramework getVisualizationFramework() {
        return visualizationFramework;
    }

    public void setVisualizationFramework(VisualizationFramework visualizationFramework) {
        this.visualizationFramework = visualizationFramework;
    }
}
