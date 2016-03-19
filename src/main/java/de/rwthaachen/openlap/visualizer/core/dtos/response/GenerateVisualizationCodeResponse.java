package de.rwthaachen.openlap.visualizer.core.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerateVisualizationCodeResponse {
    private String visualizationCode;

    public String getVisualizationCode() {
        return visualizationCode;
    }

    public void setVisualizationCode(String visualizationCode) {
        this.visualizationCode = visualizationCode;
    }
}
