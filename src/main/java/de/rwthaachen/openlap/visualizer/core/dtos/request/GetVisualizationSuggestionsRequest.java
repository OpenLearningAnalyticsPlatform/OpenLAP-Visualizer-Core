package de.rwthaachen.openlap.visualizer.core.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.dataset.OpenLAPPortConfig;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetVisualizationSuggestionsRequest {

    private OpenLAPPortConfig olapPortConfiguration;
    private OpenLAPDataSet dataSetConfiguration;

    public OpenLAPPortConfig getOlapPortConfiguration() {
        return olapPortConfiguration;
    }

    public void setOlapPortConfiguration(OpenLAPPortConfig olapPortConfiguration) {
        this.olapPortConfiguration = olapPortConfiguration;
    }

    public OpenLAPDataSet getDataSetConfiguration() {
        return dataSetConfiguration;
    }

    public void setDataSetConfiguration(OpenLAPDataSet dataSetConfiguration) {
        this.dataSetConfiguration = dataSetConfiguration;
    }
}
