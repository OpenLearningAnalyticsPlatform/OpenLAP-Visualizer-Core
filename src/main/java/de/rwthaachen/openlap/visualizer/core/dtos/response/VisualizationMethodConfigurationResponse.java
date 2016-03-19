package de.rwthaachen.openlap.visualizer.core.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rwthaachen.openlap.visualizer.core.dtos.VisualizationMethodConfiguration;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualizationMethodConfigurationResponse {

    private VisualizationMethodConfiguration methodConfiguration;

    public VisualizationMethodConfiguration getMethodConfiguration() {
        return methodConfiguration;
    }

    public void setMethodConfiguration(VisualizationMethodConfiguration methodConfiguration) {
        this.methodConfiguration = methodConfiguration;
    }
}
