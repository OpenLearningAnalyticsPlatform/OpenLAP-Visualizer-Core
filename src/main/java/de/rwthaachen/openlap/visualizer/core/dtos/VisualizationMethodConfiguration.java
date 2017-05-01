package de.rwthaachen.openlap.visualizer.core.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.rwthaachen.openlap.dataset.OpenLAPDataSet;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualizationMethodConfiguration {

    private OpenLAPDataSet input;
    private OpenLAPDataSet output;

    public OpenLAPDataSet getInput() {
        return input;
    }

    public void setInput(OpenLAPDataSet input) {
        this.input = input;
    }

    public OpenLAPDataSet getOutput() {
        return output;
    }

    public void setOutput(OpenLAPDataSet output) {
        this.output = output;
    }
}
