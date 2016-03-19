package de.rwthaachen.openlap.visualizer.core.dtos.request;

import DataSet.OLAPDataSet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddNewVisualizationSuggestionRequest {
    private Long visualizationMethodId;
    private OLAPDataSet olapDataSet;

    public Long getVisualizationMethodId() {
        return visualizationMethodId;
    }

    public void setVisualizationMethodId(Long visualizationMethodId) {
        this.visualizationMethodId = visualizationMethodId;
    }

    public OLAPDataSet getOlapDataSet() {
        return olapDataSet;
    }

    public void setOlapDataSet(OLAPDataSet olapDataSet) {
        this.olapDataSet = olapDataSet;
    }
}
