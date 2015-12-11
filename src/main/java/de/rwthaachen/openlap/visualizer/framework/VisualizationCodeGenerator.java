package de.rwthaachen.openlap.visualizer.framework;

import DataSet.OLAPDataSet;
import de.rwthaachen.openlap.visualizer.exceptions.BaseException;
import de.rwthaachen.openlap.visualizer.framework.adapters.DataTransformer;
import de.rwthaachen.openlap.visualizer.model.TransformedData;

/**
 * Created by bas on 12/6/15.
 */
public abstract class VisualizationCodeGenerator {

    private DataTransformer dataTransformer; //the data transformer (adapter) to transform OLAPDataset to Visualizer parseable data

    public abstract String visualizationCode(TransformedData<?> transformedData);

    public String generateVisualizationCode(OLAPDataSet olapDataSet, String typeOfDataTransformer) throws BaseException {
        return "";
    }

}
