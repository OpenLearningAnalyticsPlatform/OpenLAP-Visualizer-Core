package de.rwthaachen.openlap.visualizer.core.dtos.response;

import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;

/**
 * Created by bas on 1/15/16.
 */
public class VisualizationFrameworkDetailsResponse {

    private VisualizationFramework visualizationFramework;

    public VisualizationFramework getVisualizationFramework() {
        return visualizationFramework;
    }

    public void setVisualizationFramework(VisualizationFramework visualizationFramework) {
        this.visualizationFramework = visualizationFramework;
    }
}
