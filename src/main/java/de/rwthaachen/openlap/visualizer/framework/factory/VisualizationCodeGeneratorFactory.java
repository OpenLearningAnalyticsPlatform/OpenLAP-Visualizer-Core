package de.rwthaachen.openlap.visualizer.framework.factory;

import de.rwthaachen.openlap.visualizer.framework.VisualizationCodeGenerator;

/**
 * Created by bas on 12/10/15.
 */
public interface VisualizationCodeGeneratorFactory {

    VisualizationCodeGenerator createVisualizationCodeGenerator(String nameOfCodeGenerator);
}
