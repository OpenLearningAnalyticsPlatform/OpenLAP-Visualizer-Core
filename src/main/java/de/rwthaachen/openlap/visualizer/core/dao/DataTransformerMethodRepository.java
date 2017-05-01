package de.rwthaachen.openlap.visualizer.core.dao;

import de.rwthaachen.openlap.visualizer.core.model.DataTransformerMethod;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by bas on 12/12/15.
 */
public interface DataTransformerMethodRepository extends CrudRepository<DataTransformerMethod, Long> {
    DataTransformerMethod findByImplementingClass (String implementingClassName);
}
