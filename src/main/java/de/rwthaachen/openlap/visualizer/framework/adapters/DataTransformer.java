package de.rwthaachen.openlap.visualizer.framework.adapters;

import DataSet.OLAPDataSet;
import DataSet.OLAPDataSetConfigurationValidationResult;
import DataSet.OLAPPortConfiguration;
import de.rwthaachen.openlap.visualizer.exceptions.DataSetValidationException;
import de.rwthaachen.openlap.visualizer.model.TransformedData;

public interface DataTransformer {

    /**
     * @param olapDataSet The dataset which needs to be transformed in a
     *                    dataset that is understood by the visualization code
     * @return null if the data could not be transformed
     * */
    TransformedData transformData(OLAPDataSet olapDataSet);

}
