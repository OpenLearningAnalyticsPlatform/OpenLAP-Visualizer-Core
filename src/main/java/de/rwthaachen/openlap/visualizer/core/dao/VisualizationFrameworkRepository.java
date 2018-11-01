package de.rwthaachen.openlap.visualizer.core.dao;

import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by bas on 12/12/15.
 */
public interface VisualizationFrameworkRepository extends CrudRepository<VisualizationFramework,Long> {
    public VisualizationFramework findByName (String VIS_FRAMEWORK_NAME);
    public List<VisualizationFramework> findAllByOrderByNameAsc();
}

