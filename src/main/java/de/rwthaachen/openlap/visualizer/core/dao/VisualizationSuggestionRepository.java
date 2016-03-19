package de.rwthaachen.openlap.visualizer.core.dao;

import de.rwthaachen.openlap.visualizer.core.model.VisualizationMethod;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationSuggestion;
import org.springframework.data.repository.CrudRepository;

public interface VisualizationSuggestionRepository extends CrudRepository<VisualizationSuggestion,Long> {

    Iterable<VisualizationSuggestion> findByVisualizationMethod (VisualizationMethod visualizationMethod);

}
