package de.rwthaachen.openlap.visualizer.core.dtos.response;

import de.rwthaachen.openlap.visualizer.core.dtos.VisualizationMethodConfiguration;

public class VisualizationMethodConfigurationResponse {

    private VisualizationMethodConfiguration methodConfiguration;

    public VisualizationMethodConfiguration getMethodConfiguration() {
        return methodConfiguration;
    }

    public void setMethodConfiguration(VisualizationMethodConfiguration methodConfiguration) {
        this.methodConfiguration = methodConfiguration;
    }
}
