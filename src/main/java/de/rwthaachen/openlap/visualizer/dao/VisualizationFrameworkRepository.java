package de.rwthaachen.openlap.visualizer.dao;

import de.rwthaachen.openlap.visualizer.model.VisualizationFramework;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by bas on 12/12/15.
 */
public interface VisualizationFrameworkRepository extends CrudRepository<VisualizationFramework,Long> {

    VisualizationFramework findByName (String VIS_FRAMEWORK_NAME);

}

