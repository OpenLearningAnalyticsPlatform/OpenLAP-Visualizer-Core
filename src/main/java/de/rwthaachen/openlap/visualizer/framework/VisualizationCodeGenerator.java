package de.rwthaachen.openlap.visualizer.framework;

import DataSet.OLAPDataSet;
import de.rwthaachen.openlap.visualizer.exceptions.BaseException;
import de.rwthaachen.openlap.visualizer.exceptions.UnTransformableData;
import de.rwthaachen.openlap.visualizer.framework.adapters.DataTransformer;
import de.rwthaachen.openlap.visualizer.model.TransformedData;

/**
 * Created by bas on 12/6/15.
 */
public abstract class VisualizationCodeGenerator {

    public abstract String visualizationCode(TransformedData<?> transformedData);

    public String generateVisualizationCode(OLAPDataSet olapDataSet, DataTransformer dataTransformer) throws BaseException {
        dataTransformer.
        TransformedData transformedData = dataTransformer.transformData(olapDataSet);
        if(transformedData == null)
            //TODO
            throw new UnTransformableData("Data could not be transformed"); //add a json dump of olapdataset
        // via adding a toString
        else
        //TODO: Escape the HTML code
            return visualizationCode(transformedData);
    }


}
