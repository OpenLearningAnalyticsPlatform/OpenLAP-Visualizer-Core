package de.rwthaachen.openlap.visualizer.core.dtos.request;

import DataSet.OLAPPortConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateVisualizationMethodConfigurationRequest {

    private OLAPPortConfiguration configurationMapping;

    public OLAPPortConfiguration getConfigurationMapping() {

        return configurationMapping;
    }

    public void setConfigurationMapping(OLAPPortConfiguration configurationMapping) {
        this.configurationMapping = configurationMapping;
    }
}
