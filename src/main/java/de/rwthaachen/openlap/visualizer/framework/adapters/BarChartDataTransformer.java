package de.rwthaachen.openlap.visualizer.framework.adapters;

import DataSet.OLAPColumnDataType;
import DataSet.OLAPDataSet;
import de.rwthaachen.openlap.visualizer.model.TransformedBarChartD3Data;
import de.rwthaachen.openlap.visualizer.model.TransformedData;

public class BarChartDataTransformer extends DataTransformer {

    @Override
    public TransformedData transformData(OLAPDataSet olapDataSet) {
        TransformedBarChartD3Data transformedBarChartD3Data = new TransformedBarChartD3Data();
        olapDataSet.getColumnsAsList(true).forEach(olapDataColumn -> {
            //in this Data transformer y axis contains only INTEGERS
            if (olapDataColumn.getConfigurationData().getType().equals(OLAPColumnDataType.INTEGER)) {
                transformedBarChartD3Data.setyAxisValues(olapDataColumn.getData());
            } else {
                transformedBarChartD3Data.setxAxisValues(olapDataColumn.getData());
            }
        });
        return transformedBarChartD3Data;
    }
}
