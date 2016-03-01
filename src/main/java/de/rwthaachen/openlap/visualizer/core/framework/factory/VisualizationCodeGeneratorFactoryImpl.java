package de.rwthaachen.openlap.visualizer.core.framework.factory;

import de.rwthaachen.openlap.visualizer.core.exceptions.DataTransformerCreationException;
import de.rwthaachen.openlap.visualizer.core.exceptions.VisualizationCodeGeneratorCreationException;
import de.rwthaachen.openlap.visualizer.framework.VisualizationCodeGenerator;
import org.xeustechnologies.jcl.JarClassLoader;
import org.xeustechnologies.jcl.JclObjectFactory;
import org.xeustechnologies.jcl.exception.JclException;
import org.xeustechnologies.jcl.proxy.CglibProxyProvider;
import org.xeustechnologies.jcl.proxy.ProxyProviderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A concrete implementation of the VisualizationCodeGeneratorFactory interface, providing a method for the creation of the
 * VisualizationCodeGenerators. Uses the JCL (https://github.com/kamranzafar/JCL) library
 *
 * @author Bassim Bashir
*/
public class VisualizationCodeGeneratorFactoryImpl implements VisualizationCodeGeneratorFactory {

    private JclObjectFactory jclObjectFactory;
    private JarClassLoader jarClassLoader;

    public VisualizationCodeGeneratorFactoryImpl(String locationOfJar){
        jarClassLoader = new JarClassLoader();
        try {
            jarClassLoader.add(new FileInputStream(new File(locationOfJar)));
        }catch (IOException ioException){
            throw new DataTransformerCreationException(ioException.getMessage());
        }
        initializeClassLoader();
    }

    public VisualizationCodeGeneratorFactoryImpl(InputStream jarStream){
        jarClassLoader = new JarClassLoader();
        jarClassLoader.add(jarStream);
        initializeClassLoader();
    }

    private void initializeClassLoader() {
        jarClassLoader.getThreadLoader().setOrder(1);
        jarClassLoader.getCurrentLoader().setOrder(2);

        jarClassLoader.getParentLoader().setEnabled(false);
        jarClassLoader.getSystemLoader().setEnabled(false);
        jarClassLoader.getLocalLoader().setEnabled(false);


        // Set default to cglib (from version 2.2.1)
        ProxyProviderFactory.setDefaultProxyProvider(new CglibProxyProvider());

        //Create a factory of castable objects/proxies
        jclObjectFactory = JclObjectFactory.getInstance(true);
    }

    @Override
    public VisualizationCodeGenerator createVisualizationCodeGenerator(String nameOfCodeGenerator) throws VisualizationCodeGeneratorCreationException {
        //Create and cast object of loaded class
        try {
            return (VisualizationCodeGenerator) jclObjectFactory.create(jarClassLoader, nameOfCodeGenerator);
        } catch (JclException jclException) {
            throw new VisualizationCodeGeneratorCreationException(jclException.getLocalizedMessage());
        }
    }
}
