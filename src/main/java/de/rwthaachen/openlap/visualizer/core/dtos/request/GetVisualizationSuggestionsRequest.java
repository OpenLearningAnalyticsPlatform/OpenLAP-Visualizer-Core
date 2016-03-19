package de.rwthaachen.openlap.visualizer.core.dtos.request;

import DataSet.OLAPDataSet;
import DataSet.OLAPPortConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetVisualizationSuggestionsRequest {

    private OLAPPortConfiguration olapPortConfiguration;
    private OLAPDataSet dataSetConfiguration;

    public OLAPPortConfiguration getOlapPortConfiguration() {
        return olapPortConfiguration;
    }

    public void setOlapPortConfiguration(OLAPPortConfiguration olapPortConfiguration) {
        this.olapPortConfiguration = olapPortConfiguration;
    }

    public OLAPDataSet getDataSetConfiguration() {
        return dataSetConfiguration;
    }

    public void setDataSetConfiguration(OLAPDataSet dataSetConfiguration) {
        this.dataSetConfiguration = dataSetConfiguration;
    }
}
