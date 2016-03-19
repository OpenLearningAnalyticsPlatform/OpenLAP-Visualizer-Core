package de.rwthaachen.openlap.visualizer.core.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateVisualizationMethodConfigurationResponse {

    private boolean configurationValid;
    private String validationMessage;

    public boolean isConfigurationValid() {
        return configurationValid;
    }

    public void setConfigurationValid(boolean configurationValid) {
        this.configurationValid = configurationValid;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }
}
