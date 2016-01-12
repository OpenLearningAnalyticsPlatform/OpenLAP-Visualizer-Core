package de.rwthaachen.openlap.visualizer.framework.adapters;

import DataSet.OLAPDataSet;
import de.rwthaachen.openlap.visualizer.model.TransformedData;

/**
 * Created by bas on 12/6/15.
 */
public interface DataTransformer {

    /**
     * @param olapDataSet The dataset which needs to be transformed in a
     *                    dataset that is understood by the visualization code
     * @return null if the data could not be transformed
     * */
    TransformedData transformData(OLAPDataSet olapDataSet);
}
