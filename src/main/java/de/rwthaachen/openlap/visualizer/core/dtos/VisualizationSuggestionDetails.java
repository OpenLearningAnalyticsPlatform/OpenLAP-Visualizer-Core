package de.rwthaachen.openlap.visualizer.core.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualizationSuggestionDetails {

    private long suggestionId;
    private long methodId;
    private String methodName;
    private long frameworkId;
    private String frameworkName;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getFrameworkName() {
        return frameworkName;
    }

    public void setFrameworkName(String frameworkName) {
        this.frameworkName = frameworkName;
    }

    public long getMethodId() {
        return methodId;
    }

    public void setMethodId(long methodId) {
        this.methodId = methodId;
    }

    public long getFrameworkId() {
        return frameworkId;
    }

    public void setFrameworkId(long frameworkId) {
        this.frameworkId = frameworkId;
    }

    public long getSuggestionId() {
        return suggestionId;
    }

    public void setSuggestionId(long suggestionId) {
        this.suggestionId = suggestionId;
    }
}
