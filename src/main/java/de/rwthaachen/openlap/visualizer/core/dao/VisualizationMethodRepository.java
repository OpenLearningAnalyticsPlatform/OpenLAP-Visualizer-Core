package de.rwthaachen.openlap.visualizer.core.dao;

import de.rwthaachen.openlap.visualizer.core.model.VisualizationMethod;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by bas on 12/12/15.
 */
public interface VisualizationMethodRepository extends CrudRepository<VisualizationMethod, Long> {
    VisualizationMethod findByImplementingClass (String implementingClassName);
}
