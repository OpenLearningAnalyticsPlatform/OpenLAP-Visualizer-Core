package de.rwthaachen.olap.visualizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class Application {

    public static String API_VERSION_NUMBER = "v1";
    private static String API_VERSION_NUMBER_PROPERTY = "api.version.number";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        Properties prop = new Properties();
        String propFileName = "config.properties";

        InputStream inputStream = Application.class.getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            try{
                prop.load(inputStream);
                if((prop.getProperty(API_VERSION_NUMBER_PROPERTY) != null) && !(prop.getProperty(API_VERSION_NUMBER_PROPERTY).isEmpty()))
                   API_VERSION_NUMBER = prop.getProperty(API_VERSION_NUMBER_PROPERTY);
            }catch (Exception exception){
                // loading of properties file failed, set all the variables to default
            }
        } else {
            System.out.println("property file '" + propFileName + "' not found in the classpath");
        }

    }

}
