package de.rwthaachen.openlap.visualizer.core.dtos;

import DataSet.OLAPDataSet;

public class VisualizationMethodConfiguration {

    private OLAPDataSet input;
    private OLAPDataSet output;

    public OLAPDataSet getInput() {
        return input;
    }

    public void setInput(OLAPDataSet input) {
        this.input = input;
    }

    public OLAPDataSet getOutput() {
        return output;
    }

    public void setOutput(OLAPDataSet output) {
        this.output = output;
    }
}
