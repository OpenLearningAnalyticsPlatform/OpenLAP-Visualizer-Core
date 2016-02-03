package de.rwthaachen.openlap.visualizer.core.dtos.request;

import DataSet.OLAPDataSet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by bas on 1/13/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenerateVisualizationCodeRequest {

    private String frameworkName;
    private String methodName;
    private long frameworkId;
    private long methodId;
    private OLAPDataSet dataSet;

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
}
