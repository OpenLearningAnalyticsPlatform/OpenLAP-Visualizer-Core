package de.rwthaachen.openlap.visualizer.core.service;

import DataSet.OLAPDataSet;
import DataSet.OLAPPortConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.rwthaachen.openlap.visualizer.OpenLAPVisualizerApplication;
import de.rwthaachen.openlap.visualizer.core.dao.VisualizationMethodRepository;
import de.rwthaachen.openlap.visualizer.core.dao.VisualizationSuggestionRepository;
import de.rwthaachen.openlap.visualizer.core.dtos.VisualizationSuggestionDetails;
import de.rwthaachen.openlap.visualizer.core.exceptions.VisualizationSuggestionException;
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
 * Created by bas on 1/9/16.
 */

@Service
public class VisualizationSuggestionService {

    private static final Logger logger = LoggerFactory.getLogger(OpenLAPVisualizerApplication.class);
    @Autowired
    private VisualizationSuggestionRepository visualizationSuggestionRepository;
    @Autowired
    private VisualizationMethodRepository visualizationMethodRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<VisualizationSuggestionDetails> getSuggestionsForDataSetConfiguration(OLAPPortConfiguration olapPortConfiguration) {
        Iterable<VisualizationSuggestion> suggestionList = visualizationSuggestionRepository.findAll();
        List<VisualizationSuggestionDetails> matchedSuggestions = new ArrayList<>();
        suggestionList.forEach(suggestion -> {
            //convert the string json content into an object
            try {
                OLAPDataSet dataSet = objectMapper.readValue(suggestion.getOlapDataSetConfiguration(), OLAPDataSet.class);
                // if the configuration matches then add it to the list
                if (dataSet.validateConfiguration(olapPortConfiguration).isValid()) {
                    VisualizationMethod visualizationMethod = suggestion.getVisualizationMethod();
                    VisualizationSuggestionDetails suggestionDetails = new VisualizationSuggestionDetails();
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

    public VisualizationSuggestionDetails getSuggestionDetails(long suggestionId) throws VisualizationSuggestionException {
        VisualizationSuggestion suggestion = visualizationSuggestionRepository.findOne(suggestionId);
        if (suggestion == null)
            throw new VisualizationSuggestionException("Visualization suggestion with id: " + suggestionId + " not found.");
        else {
            VisualizationSuggestionDetails visualizationSuggestionDetails = new VisualizationSuggestionDetails();
            visualizationSuggestionDetails.setMethodId(suggestion.getVisualizationMethod().getId());
            visualizationSuggestionDetails.setMethodName(suggestion.getVisualizationMethod().getName());
            visualizationSuggestionDetails.setFrameworkId(suggestion.getVisualizationMethod().getVisualizationFramework().getId());
            visualizationSuggestionDetails.setFrameworkName(suggestion.getVisualizationMethod().getVisualizationFramework().getName());
            return visualizationSuggestionDetails;
        }
    }

    public boolean deleteVisualizationSuggestions(long suggestionId) throws VisualizationSuggestionException {
        if (visualizationSuggestionRepository.exists(suggestionId)){
            visualizationSuggestionRepository.delete(suggestionId);
            if(visualizationSuggestionRepository.exists(suggestionId))
                return false;
            else
                return true;
        }
        else
            throw new VisualizationSuggestionException("Visualization suggestion with id: " + suggestionId + " not found.");
    }

    public void updateVisualizationSuggestionAttributes(long suggestionId, VisualizationSuggestion newAttributes) throws VisualizationSuggestionException{
        if(visualizationSuggestionRepository.exists(suggestionId)){
            VisualizationSuggestion suggestionToUpdate = visualizationSuggestionRepository.findOne(suggestionId);
            if(newAttributes.getVisualizationMethod()!=null) {
                // only the id is needed for an update
                if(visualizationMethodRepository.exists(newAttributes.getVisualizationMethod().getId())) {
                    // if the method exists, then lets update it
                    VisualizationMethod visualizationMethod = visualizationMethodRepository.findOne(newAttributes.getVisualizationMethod().getId());
                    suggestionToUpdate.setVisualizationMethod(visualizationMethod);
                }
            }
            if(newAttributes.getOlapDataSetConfiguration()!=null && !newAttributes.getOlapDataSetConfiguration().isEmpty())
                suggestionToUpdate.setOlapDataSetConfiguration(newAttributes.getOlapDataSetConfiguration());

            visualizationSuggestionRepository.save(suggestionToUpdate);
        }
        else{
            throw new VisualizationSuggestionException("The suggestion with the id: "+suggestionId+" does not exist. Update failed.");
        }
    }

    public Long createVisualizationSuggestion(long visualizationMethodId, OLAPDataSet dataSet) throws VisualizationSuggestionException{
        if(visualizationMethodRepository.exists(visualizationMethodId)){
            if(dataSet != null){
                VisualizationSuggestion newVisualizationSuggestion =  new VisualizationSuggestion();
                VisualizationMethod visualizationMethod = visualizationMethodRepository.findOne(visualizationMethodId);
                newVisualizationSuggestion.setVisualizationMethod(visualizationMethod);
                try {
                    newVisualizationSuggestion.setOlapDataSetConfiguration(objectMapper.writeValueAsString(dataSet));
                    newVisualizationSuggestion = visualizationSuggestionRepository.save(newVisualizationSuggestion);
                    return newVisualizationSuggestion.getId();
                }catch(JsonProcessingException exception){
                    throw new VisualizationSuggestionException("Creation of a new visualization suggestion failed, could not serialize the provided OLAPDataset.");
                }
            }
            else{
                throw new VisualizationSuggestionException("For a new suggestion an OLAPDataset with column configuration needs to be provided. The data itself is not required!");
            }
        }
        else{
            throw new VisualizationSuggestionException("Cannot create a visualization suggestion as the provided vis method Id: "+visualizationMethodId+", does not exist.");
        }
    }
}

