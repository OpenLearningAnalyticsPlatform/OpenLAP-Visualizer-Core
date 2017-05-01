package de.rwthaachen.openlap.visualizer.core.dao;

import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by bas on 12/12/15.
 */
public interface VisualizationFrameworkRepository extends CrudRepository<VisualizationFramework,Long> {
    VisualizationFramework findByName (String VIS_FRAMEWORK_NAME);
}

