package de.rwthaachen.openlap.visualizer.framework.adapters;

import DataSet.OLAPDataSet;
import DataSet.OLAPDataSetConfigurationValidationResult;
import DataSet.OLAPPortConfiguration;
import de.rwthaachen.openlap.visualizer.exceptions.DataSetValidationException;
import de.rwthaachen.openlap.visualizer.model.TransformedData;

/**
 * Created by bas on 12/6/15.
 */
public abstract class DataTransformer {

    private OLAPDataSet input;
    private OLAPDataSet output;

    protected DataTransformer(){
        initializeDataSetConfiguration();
    }

    protected abstract void initializeDataSetConfiguration();
    /**
     * @param olapDataSet The dataset which needs to be transformed in a
     *                    dataset that is understood by the visualization code
     * @return null if the data could not be transformed
     * */
    public abstract TransformedData transformData(OLAPDataSet olapDataSet);

    public abstract boolean isDataTransformable(OLAPPortConfiguration olapPortConfiguration) throws DataSetValidationException;

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
