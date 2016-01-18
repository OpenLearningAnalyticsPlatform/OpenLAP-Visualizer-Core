package de.rwthaachen.openlap.visualizer.framework;

import DataSet.OLAPColumnDataType;
import DataSet.OLAPDataColumnFactory;
import DataSet.OLAPDataSet;
import de.rwthaachen.openlap.visualizer.model.TransformedBarChartD3Data;
import de.rwthaachen.openlap.visualizer.model.TransformedData;
import exceptions.OLAPDataColumnException;

public class D3BarChartVisualizationCodeGenerator extends VisualizationCodeGenerator {

    @Override
    protected void initializeDataSetConfiguration() {
        this.setInput(new OLAPDataSet());
        this.setOutput(new OLAPDataSet());
        try {
            this.getInput().addOLAPDataColumn(
                    OLAPDataColumnFactory.createOLAPDataColumnOfType("xAxisStrings", OLAPColumnDataType.STRING, true)
            );
            this.getInput().addOLAPDataColumn(
                    OLAPDataColumnFactory.createOLAPDataColumnOfType("yAxisValues", OLAPColumnDataType.INTEGER, true)
            );
        } catch (OLAPDataColumnException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String visualizationCode(TransformedData transformedData) {
        TransformedBarChartD3Data transformedBarChartD3Data = (TransformedBarChartD3Data) transformedData;
        StringBuilder stringBuilder = new StringBuilder();
        return null;
    }
}
