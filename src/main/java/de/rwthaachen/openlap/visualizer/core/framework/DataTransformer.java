package de.rwthaachen.openlap.visualizer.core.framework;

import DataSet.OLAPDataSet;
import de.rwthaachen.openlap.visualizer.core.model.TransformedData;

public interface DataTransformer {

    /**
     * @param olapDataSet The dataset which needs to be transformed in a
     *                    dataset that is understood by the visualization code
     * @return null if the data could not be transformed
     * */
    TransformedData transformData(OLAPDataSet olapDataSet);

}
