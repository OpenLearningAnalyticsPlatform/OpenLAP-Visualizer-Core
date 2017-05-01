package de.rwthaachen.openlap.visualizer.core.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rwthaachen.openlap.dataset.OpenLAPDataSet;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddNewVisualizationSuggestionRequest {
    private Long visualizationMethodId;
    private OpenLAPDataSet olapDataSet;

    public Long getVisualizationMethodId() {
        return visualizationMethodId;
    }

    public void setVisualizationMethodId(Long visualizationMethodId) {
        this.visualizationMethodId = visualizationMethodId;
    }

    public OpenLAPDataSet getOlapDataSet() {
        return olapDataSet;
    }

    public void setOlapDataSet(OpenLAPDataSet olapDataSet) {
        this.olapDataSet = olapDataSet;
    }
}
