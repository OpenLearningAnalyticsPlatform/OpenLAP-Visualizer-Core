package de.rwthaachen.openlap.visualizer.framework.factory;

import de.rwthaachen.openlap.visualizer.exceptions.DataTransformerCreationException;
import de.rwthaachen.openlap.visualizer.framework.adapters.DataTransformer;

/**
 * The interface specifying the Factory functions for creating the DataTransformer objects
 *
 * @author Bassim Bashir
 */
public interface DataTransformerFactory {

    /**
     * The function instantiates a DataTransformer object by the class name specified as an argument to the function
     *
     * @param typeOfDataTransformer The name of the DataTransformer to instantiate
     * @return The object of the DataTransformer, null in the case that the DataTransformer could not be instantiated
     * @throws DataTransformerCreationException Throws a wrapped DataTransformerCreationException specifying the cause of failure while trying to create the DataTransfomer object
     */
    DataTransformer createDataTransformer(String typeOfDataTransformer) throws DataTransformerCreationException;
}
