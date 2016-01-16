package de.rwthaachen.openlap.visualizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OpenLAPVisualizerApplication {

    public static String API_VERSION_NUMBER = "v1";

    public static void main(String[] args) {
        SpringApplication.run(OpenLAPVisualizerApplication.class, args);
    }

}
