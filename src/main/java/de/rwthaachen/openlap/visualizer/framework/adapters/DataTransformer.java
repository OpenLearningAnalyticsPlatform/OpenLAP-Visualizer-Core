package de.rwthaachen.openlap.visualizer.framework.adapters;

import DataSet.OLAPDataSet;
import de.rwthaachen.openlap.visualizer.model.TransformedData;

/**
 * Created by bas on 12/6/15.
 */
public interface DataTransformer {

    TransformedData transformData(OLAPDataSet olapDataSet);
}
