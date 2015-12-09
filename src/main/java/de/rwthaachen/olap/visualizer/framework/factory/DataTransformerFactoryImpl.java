package de.rwthaachen.olap.visualizer.framework.factory;

import de.rwthaachen.olap.visualizer.exceptions.BaseException;
import de.rwthaachen.olap.visualizer.exceptions.DataTransformerCreationException;
import de.rwthaachen.olap.visualizer.framework.adapters.DataTransformer;

import java.lang.reflect.Modifier;

/**
 * A concrete implementation of the DataTransformerFactory interface, providing a method for the creation of the
 * DataTransformer adapters
 *
 * @author Bassim Bashir
 */
public class DataTransformerFactoryImpl implements DataTransformerFactory {

    @Override
    public DataTransformer createDataTransformer(String typeOfDataTransformer) throws BaseException {
        if (typeOfDataTransformer == null || typeOfDataTransformer.isEmpty())
            return null;

        // lets first get the class instance
        Class dataTransformerClass = null;
        try {
            dataTransformerClass = Class.forName(typeOfDataTransformer);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new DataTransformerCreationException("Class " + typeOfDataTransformer + " not found");
        }
        if (dataTransformerClass.isInterface()) {
            throw new DataTransformerCreationException("Cannot instantiate the class " + typeOfDataTransformer + ", as it is an interface");
        }
        if (Modifier.isAbstract(dataTransformerClass.getModifiers())) {
            throw new DataTransformerCreationException("Cannot instantiate the class " + typeOfDataTransformer + ", as it is an abstract class");
        }
        try {
            dataTransformerClass.getConstructor(new Class[]{});
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new DataTransformerCreationException("Not able to access the default constructor of the class " + typeOfDataTransformer);
        }
        //this means that all the checks passed now lets create the object
        try {
            return (DataTransformer) dataTransformerClass.newInstance();
        } catch (IllegalAccessException | InstantiationException exception) {
            throw new DataTransformerCreationException(exception.getLocalizedMessage());
        }
    }
}
