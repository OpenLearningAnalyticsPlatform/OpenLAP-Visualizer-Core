package de.rwthaachen.openlap.visualizer.core.dtos.response;

import de.rwthaachen.openlap.visualizer.core.model.VisualizationMethod;

/**
 * Created by bas on 1/15/16.
 */
public class VisualizationMethodDetailsResponse {

    private VisualizationMethod visualizationMethod;

    public VisualizationMethod getVisualizationMethod() {
        return visualizationMethod;
    }

    public void setVisualizationMethod(VisualizationMethod visualizationMethod) {
        this.visualizationMethod = visualizationMethod;
    }
}
