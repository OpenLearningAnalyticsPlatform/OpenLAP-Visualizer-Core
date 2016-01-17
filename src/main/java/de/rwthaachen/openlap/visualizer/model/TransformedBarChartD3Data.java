package de.rwthaachen.openlap.visualizer.model;

import java.util.List;

/**
 * Created by bas on 1/17/16.
 */
public class TransformedBarChartD3Data extends TransformedData{
    List<String> xAxisValues;
    List<String> yAxisValues;

    public List<String> getxAxisValues() {
        return xAxisValues;
    }

    public void setxAxisValues(List<String> xAxisValues) {
        this.xAxisValues = xAxisValues;
    }

    public List<String> getyAxisValues() {
        return yAxisValues;
    }

    public void setyAxisValues(List<String> yAxisValues) {
        this.yAxisValues = yAxisValues;
    }
}
