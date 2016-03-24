package de.rwthaachen.openlap.visualizer.core.dtos.request;

import DataSet.OLAPDataSet;
import DataSet.OLAPPortConfiguration;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerateVisualizationCodeRequest {

    private String frameworkName;
    private String methodName;
    private long frameworkId;
    private long methodId;
    private OLAPDataSet dataSet;
    private OLAPPortConfiguration portConfiguration;
    private Map<String,Object> params;

    public OLAPDataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(OLAPDataSet dataSet) {
        this.dataSet = dataSet;
    }

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

    public Map<String,Object> getAdditionalParameters() { return params; }

    public void setAdditionalParameters(Map<String,Object> params) {
        this.params = params;
    }

    public OLAPPortConfiguration getPortConfiguration() {
        return portConfiguration;
    }

    public void setPortConfiguration(OLAPPortConfiguration portConfiguration) {
        this.portConfiguration = portConfiguration;
    }
}
