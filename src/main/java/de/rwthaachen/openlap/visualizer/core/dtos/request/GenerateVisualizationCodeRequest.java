package de.rwthaachen.openlap.visualizer.core.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.dataset.OpenLAPPortConfig;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerateVisualizationCodeRequest {

    private String frameworkName;
    private String methodName;
    private long frameworkId;
    private long methodId;
    private OpenLAPDataSet dataSet;
    private OpenLAPPortConfig portConfiguration;
    private Map<String,Object> params;

    public String getFrameworkName() {
        return frameworkName;
    }

    public void setFrameworkName(String frameworkName) {
        this.frameworkName = frameworkName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getFrameworkId() {
        return frameworkId;
    }

    public void setFrameworkId(long frameworkId) {
        this.frameworkId = frameworkId;
    }

    public long getMethodId() {
        return methodId;
    }

    public void setMethodId(long methodId) {
        this.methodId = methodId;
    }

    public OpenLAPDataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(OpenLAPDataSet dataSet) {
        this.dataSet = dataSet;
    }

    public OpenLAPPortConfig getPortConfiguration() {
        return portConfiguration;
    }

    public void setPortConfiguration(OpenLAPPortConfig portConfiguration) {
        this.portConfiguration = portConfiguration;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
