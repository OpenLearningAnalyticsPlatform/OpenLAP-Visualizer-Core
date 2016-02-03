package de.rwthaachen.openlap.visualizer.core.framework.factory;

import de.rwthaachen.openlap.visualizer.core.exceptions.DataTransformerCreationException;
import de.rwthaachen.openlap.visualizer.core.framework.DataTransformer;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;
import org.xeustechnologies.jcl.exception.JclException;
import org.xeustechnologies.jcl.proxy.CglibProxyProvider;
import org.xeustechnologies.jcl.proxy.ProxyProviderFactory;

import java.io.InputStream;

/**
 * A concrete implementation of the DataTransformerFactory interface, providing a method for the creation of the
 * DataTransformer adapters
 *
 * @author Bassim Bashir
 */
public class DataTransformerFactoryImpl implements DataTransformerFactory {

    private JclObjectFactory jclObjectFactory;
    private JarClassLoader jarClassLoader;

    public DataTransformerFactoryImpl(String locationOfJar){
        jarClassLoader = new JarClassLoader();
        jarClassLoader.add(locationOfJar);
        initializeClassLoader();
    }

    public DataTransformerFactoryImpl(InputStream jarStream){
        jarClassLoader = new JarClassLoader();
        jarClassLoader.add(jarStream);
        initializeClassLoader();
    }

    private void initializeClassLoader() {
        jarClassLoader.getParentLoader().setOrder(1);
        jarClassLoader.getLocalLoader().setOrder(2);
        jarClassLoader.getThreadLoader().setOrder(3);
        jarClassLoader.getCurrentLoader().setOrder(4);
        jarClassLoader.getSystemLoader().setOrder(5);

        // Set default to cglib (from version 2.2.1)
        ProxyProviderFactory.setDefaultProxyProvider(new CglibProxyProvider());

        //Create a factory of castable objects/proxies
        jclObjectFactory = JclObjectFactory.getInstance(true);
    }

    @Override
    public DataTransformer createDataTransformer(String typeOfDataTransformer) throws DataTransformerCreationException {
        try {
            return (DataTransformer) jclObjectFactory.create(jarClassLoader, typeOfDataTransformer);
        } catch (JclException jclException) {
            throw new DataTransformerCreationException(jclException.getLocalizedMessage());
        }
    }
}
