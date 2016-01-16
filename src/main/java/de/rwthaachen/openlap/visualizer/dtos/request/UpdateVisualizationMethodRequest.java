package de.rwthaachen.openlap.visualizer.dtos.request;

import de.rwthaachen.openlap.visualizer.model.VisualizationMethod;

/**
 * Created by bas on 1/16/16.
 */
public class UpdateVisualizationMethodRequest {

    private VisualizationMethod visualizationMethod;

    public VisualizationMethod getVisualizationMethod() {
        return visualizationMethod;
    }

    public void setVisualizationMethod(VisualizationMethod visualizationMethod) {
        this.visualizationMethod = visualizationMethod;
    }
}
