package de.rwthaachen.openlap.visualizer.dtos.request;

/**
 * Created by bas on 1/13/16.
 */
public class DeleteVisualizationFrameworkRequest {

    private String frameworkName;
    private long frameworkId;

    public String getFrameworkName() {
        return frameworkName;
    }

    public void setFrameworkName(String frameworkName) {
        this.frameworkName = frameworkName;
    }

    public long getFrameworkId() {
        return frameworkId;
    }

    public void setFrameworkId(long frameworkId) {
        this.frameworkId = frameworkId;
    }
}
