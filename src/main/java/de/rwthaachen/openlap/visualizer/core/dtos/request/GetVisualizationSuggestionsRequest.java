package de.rwthaachen.openlap.visualizer.core.dtos.request;

import DataSet.OLAPPortConfiguration;

/**
 * Created by bas on 1/28/16.
 */
public class GetVisualizationSuggestionsRequest {

    private OLAPPortConfiguration dataSetConfiguration;

    public OLAPPortConfiguration getDataSetConfiguration() {
        return dataSetConfiguration;
    }

    public void setDataSetConfiguration(OLAPPortConfiguration dataSetConfiguration) {
        this.dataSetConfiguration = dataSetConfiguration;
    }
}
