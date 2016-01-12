package de.rwthaachen.openlap.visualizer.framework.factory;

import de.rwthaachen.openlap.visualizer.exceptions.ClassLoaderException;
import de.rwthaachen.openlap.visualizer.framework.VisualizationCodeGenerator;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;
import org.xeustechnologies.jcl.exception.JclException;
import org.xeustechnologies.jcl.proxy.CglibProxyProvider;
import org.xeustechnologies.jcl.proxy.ProxyProviderFactory;

import java.io.InputStream;

/**
 * Created by bas on 1/12/16.
 */
public class VisualizationCodeGeneratorFactoryImpl implements VisualizationCodeGeneratorFactory {

    private JclObjectFactory jclObjectFactory;
    private JarClassLoader jarClassLoader;

    public VisualizationCodeGeneratorFactoryImpl(String locationOfJar){
        jarClassLoader = new JarClassLoader();
        jarClassLoader.add(locationOfJar);
        initializeClassLoader();
    }

    public VisualizationCodeGeneratorFactoryImpl(InputStream jarStream){
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
    public VisualizationCodeGenerator createVisualizationCodeGenerator(String nameOfCodeGenerator) {
        //Create and cast object of loaded class
        try {
            return (VisualizationCodeGenerator) jclObjectFactory.create(jarClassLoader, nameOfCodeGenerator);
        } catch (JclException jclException) {
            throw new ClassLoaderException(jclException.getLocalizedMessage());
        }
    }
}
