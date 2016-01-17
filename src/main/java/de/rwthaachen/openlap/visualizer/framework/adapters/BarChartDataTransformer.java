package de.rwthaachen.openlap.visualizer.framework.adapters;

import DataSet.*;
import de.rwthaachen.openlap.visualizer.exceptions.DataSetValidationException;
import de.rwthaachen.openlap.visualizer.model.TransformedBarChartD3Data;
import de.rwthaachen.openlap.visualizer.model.TransformedData;
import exceptions.OLAPDataColumnException;

/**
 * Created by bas on 1/17/16.
 */
public class BarChartDataTransformer extends DataTransformer {

    public BarChartDataTransformer(){
        super();
    }

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
    public TransformedData transformData(OLAPDataSet olapDataSet) {
        TransformedBarChartD3Data transformedBarChartD3Data = new TransformedBarChartD3Data();
        olapDataSet.getColumnsAsList(true).forEach(olapDataColumn -> {
            //in this Data transformer y axis contains only INTEGERS
            if(olapDataColumn.getConfigurationData().getType().equals(OLAPColumnDataType.INTEGER)){
                transformedBarChartD3Data.setyAxisValues(olapDataColumn.getData());
            }else{
                transformedBarChartD3Data.setxAxisValues(olapDataColumn.getData());
            }
        });
        return transformedBarChartD3Data;
    }

    @Override
    public boolean isDataTransformable(OLAPPortConfiguration olapPortConfiguration) throws DataSetValidationException{
        OLAPDataSetConfigurationValidationResult validationResult = this.getInput().validateConfiguration(olapPortConfiguration);
        if(validationResult.isValid())
            return true;
        else
            throw new DataSetValidationException(validationResult.getValidationMessage());
    }
}
