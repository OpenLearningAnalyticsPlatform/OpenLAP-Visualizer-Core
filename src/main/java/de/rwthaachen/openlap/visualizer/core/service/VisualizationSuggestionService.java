package de.rwthaachen.openlap.visualizer.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rwthaachen.openlap.dataset.OpenLAPDataSet;
import de.rwthaachen.openlap.visualizer.OpenLAPVisualizerApplication;
import de.rwthaachen.openlap.visualizer.core.dao.VisualizationMethodRepository;
import de.rwthaachen.openlap.visualizer.core.dao.VisualizationSuggestionRepository;
import de.rwthaachen.openlap.visualizer.core.dtos.VisualizationSuggestionDetails;
import de.rwthaachen.openlap.visualizer.core.exceptions.VisualizationSuggestionCreationException;
import de.rwthaachen.openlap.visualizer.core.exceptions.VisualizationSuggestionNotFoundException;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationMethod;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationSuggestion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A service which provides functions to perform CRUD operations on the VisualizationSuggestions
 *
 * @author Bassim Bashir
 */
@Service
public class VisualizationSuggestionService {

    private static final Logger logger = LoggerFactory.getLogger(OpenLAPVisualizerApplication.class);
    @Autowired
    private VisualizationSuggestionRepository visualizationSuggestionRepository;
    @Autowired
    private VisualizationMethodRepository visualizationMethodRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Finds and returns a list of VisualizationSuggestions matching the provided OpenLAPDataSet
     *
     * @param openLAPDataSet The OpenLAPDataSet for which to search VisualizationSuggestion for
     * @return list of VisualizationSuggestions matching the OpenLAPDataSet
     */
    public List<VisualizationSuggestionDetails> getSuggestionsForDataSetConfiguration(OpenLAPDataSet openLAPDataSet) {
        Iterable<VisualizationSuggestion> suggestionList = visualizationSuggestionRepository.findAll();
        List<VisualizationSuggestionDetails> matchedSuggestions = new ArrayList<>();
        suggestionList.forEach(suggestion -> {
            //convert the string json content into an object
            try {
                OpenLAPDataSet dataSet = objectMapper.readValue(suggestion.getOlapDataSetConfiguration(), OpenLAPDataSet.class);
                // if the configuration matches then add it to the list
                if (dataSet.compareToOpenLAPDataSet(openLAPDataSet)) {
                    VisualizationMethod visualizationMethod = suggestion.getVisualizationMethod();
                    VisualizationSuggestionDetails suggestionDetails = new VisualizationSuggestionDetails();
                    suggestionDetails.setSuggestionId(suggestion.getId());
                    suggestionDetails.setMethodId(visualizationMethod.getId());
                    suggestionDetails.setMethodName(visualizationMethod.getName());
                    suggestionDetails.setFrameworkId(visualizationMethod.getVisualizationFramework().getId());
                    suggestionDetails.setFrameworkName(visualizationMethod.getVisualizationFramework().getName());
                    matchedSuggestions.add(suggestionDetails);
                }
            } catch (IOException exception) {
                //if the conversion failed then ignore and move on to the next entry, log the error
                logger.error("Suggestions matcher failed to deserialize a database entry, reason: " + exception.getMessage() + ". Suggestion stored content : " + suggestion.getOlapDataSetConfiguration());
            }
        });
        return matchedSuggestions;
    }


    /**
     * Finds and returns a VisualizationSuggestion matching the provided id
     *
     * @param suggestionId The id of the VisualizationSuggestion to search for
     * @return VisualizationSuggestion found in the database
     * @throws VisualizationSuggestionNotFoundException if the VisualizationSuggestion does not exist
     */
    public VisualizationSuggestionDetails getSuggestionDetails(long suggestionId) throws VisualizationSuggestionNotFoundException {
        VisualizationSuggestion suggestion = visualizationSuggestionRepository.findOne(suggestionId);
        if (suggestion == null)
            throw new VisualizationSuggestionNotFoundException("Visualization suggestion with id: " + suggestionId + " not found.");
        else {
            VisualizationSuggestionDetails visualizationSuggestionDetails = new VisualizationSuggestionDetails();
            visualizationSuggestionDetails.setMethodId(suggestion.getVisualizationMethod().getId());
            visualizationSuggestionDetails.setMethodName(suggestion.getVisualizationMethod().getName());
            visualizationSuggestionDetails.setFrameworkId(suggestion.getVisualizationMethod().getVisualizationFramework().getId());
            visualizationSuggestionDetails.setFrameworkName(suggestion.getVisualizationMethod().getVisualizationFramework().getName());
            return visualizationSuggestionDetails;
        }
    }

    /**
     * Deletes a VisualizationSuggestion matching the provided id
     *
     * @param suggestionId The id of the VisualizationSuggestion to delete
     * @return true if the VisualizationSuggestion was successfully deleted, otherwise false
     * @throws VisualizationSuggestionNotFoundException if the VisualizationSuggestion does not exist
     */
    public boolean deleteVisualizationSuggestions(long suggestionId) throws VisualizationSuggestionNotFoundException {
        if (visualizationSuggestionRepository.exists(suggestionId)) {
            visualizationSuggestionRepository.delete(suggestionId);
            return visualizationSuggestionRepository.exists(suggestionId);
        } else
            throw new VisualizationSuggestionNotFoundException("Visualization suggestion with id: " + suggestionId + " not found.");
    }

    /**
     * Updates a VisualizationSuggestion matching the provided id
     *
     * @param suggestionId  The id of the VisualizationSuggestion to delete
     * @param newAttributes The instance of VisualizationSuggestion containing the new values
     * @return updated VisualizationSuggestion instance
     * @throws VisualizationSuggestionNotFoundException if the VisualizationSuggestion does not exist
     */
    public VisualizationSuggestion updateVisualizationSuggestionAttributes(long suggestionId, VisualizationSuggestion newAttributes) throws VisualizationSuggestionNotFoundException {
        if (visualizationSuggestionRepository.exists(suggestionId)) {
            VisualizationSuggestion suggestionToUpdate = visualizationSuggestionRepository.findOne(suggestionId);
            if (newAttributes.getVisualizationMethod() != null) {
                // only the id is needed for an update
                if (visualizationMethodRepository.exists(newAttributes.getVisualizationMethod().getId())) {
                    // if the method exists, then lets update it
                    VisualizationMethod visualizationMethod = visualizationMethodRepository.findOne(newAttributes.getVisualizationMethod().getId());
                    suggestionToUpdate.setVisualizationMethod(visualizationMethod);
                }
            }
            if (newAttributes.getOlapDataSetConfiguration() != null && !newAttributes.getOlapDataSetConfiguration().isEmpty())
                suggestionToUpdate.setOlapDataSetConfiguration(newAttributes.getOlapDataSetConfiguration());

            return visualizationSuggestionRepository.save(suggestionToUpdate);
        } else {
            throw new VisualizationSuggestionNotFoundException("The suggestion with the id: " + suggestionId + " does not exist. Update failed.");
        }
    }

    /**
     * Creates a new VisualizationSuggestion
     *
     * @param visualizationMethodId The id of the VisualizationMethod to which to attach the new VisualizationSuggestion to
     * @param dataSet               The OpenLAPDataSet containing the configuration for which this new VisualizationSuggestion applies
     * @return the newly created instance of the VisualizationSuggestion
     * @throws VisualizationSuggestionCreationException if the creation of the VisualizationSuggestion failed
     */
    public VisualizationSuggestion createVisualizationSuggestion(long visualizationMethodId, OpenLAPDataSet dataSet) throws VisualizationSuggestionCreationException {
        if (visualizationMethodRepository.exists(visualizationMethodId)) {
            if (dataSet != null) {
                VisualizationSuggestion newVisualizationSuggestion = new VisualizationSuggestion();
                VisualizationMethod visualizationMethod = visualizationMethodRepository.findOne(visualizationMethodId);
                newVisualizationSuggestion.setVisualizationMethod(visualizationMethod);
                try {
                    newVisualizationSuggestion.setOlapDataSetConfiguration(objectMapper.writeValueAsString(dataSet));
                    return visualizationSuggestionRepository.save(newVisualizationSuggestion);
                } catch (JsonProcessingException exception) {
                    throw new VisualizationSuggestionCreationException("Creation of a new visualization suggestion failed, could not serialize the provided OLAPDataset.");
                }
            } else {
                throw new VisualizationSuggestionCreationException("For a new suggestion an OLAPDataset with column configuration needs to be provided. The data itself can be ignored.");
            }
        } else {
            throw new VisualizationSuggestionCreationException("Cannot create a visualization suggestion as the provided vis method Id: " + visualizationMethodId + ", does not exist.");
        }
    }
}

