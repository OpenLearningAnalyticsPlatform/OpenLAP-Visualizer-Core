package de.rwthaachen.openlap.visualizer.core.dtos.request;

import DataSet.OLAPDataSet;

/**
 * Created by bas on 1/28/16.
 */
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
