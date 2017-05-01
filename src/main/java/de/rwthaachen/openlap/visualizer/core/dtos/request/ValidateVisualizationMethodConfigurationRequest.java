package de.rwthaachen.openlap.visualizer.core.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rwthaachen.openlap.dataset.OpenLAPPortConfig;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateVisualizationMethodConfigurationRequest {

    private OpenLAPPortConfig configurationMapping;

    public OpenLAPPortConfig getConfigurationMapping() {

        return configurationMapping;
    }

    public void setConfigurationMapping(OpenLAPPortConfig configurationMapping) {
        this.configurationMapping = configurationMapping;
    }
}
