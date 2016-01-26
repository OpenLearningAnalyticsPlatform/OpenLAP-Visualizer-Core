package de.rwthaachen.openlap.visualizer.dtos.request;

import DataSet.OLAPPortConfiguration;

/**
 * Created by bas on 1/19/16.
 */
public class ValidateVisualizationMethodConfigurationRequest {

    private OLAPPortConfiguration configurationMapping;

    public OLAPPortConfiguration getConfigurationMapping() {

        return configurationMapping;
    }

    public void setConfigurationMapping(OLAPPortConfiguration configurationMapping) {
        this.configurationMapping = configurationMapping;
    }
}
