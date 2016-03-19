package de.rwthaachen.openlap.visualizer.core.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationMethod;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateVisualizationMethodRequest {

    private VisualizationMethod visualizationMethod;

    public VisualizationMethod getVisualizationMethod() {
        return visualizationMethod;
    }

    public void setVisualizationMethod(VisualizationMethod visualizationMethod) {
        this.visualizationMethod = visualizationMethod;
    }
}
