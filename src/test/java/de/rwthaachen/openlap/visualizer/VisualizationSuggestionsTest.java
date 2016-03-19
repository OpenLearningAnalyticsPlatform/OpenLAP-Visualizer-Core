package de.rwthaachen.openlap.visualizer;

import DataSet.OLAPDataSet;
import DataSet.OLAPPortConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.rwthaachen.openlap.visualizer.core.dtos.request.AddNewVisualizationSuggestionRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.request.GetVisualizationSuggestionsRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.request.UpdateVisualizationSuggestionRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.response.*;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationSuggestion;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Random;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OpenLAPVisualizerApplication.class)
@WebAppConfiguration
@ActiveProfiles("development")
public class VisualizationSuggestionsTest {
    private static final Logger log =
            LoggerFactory.getLogger(OpenLAPVisualizerApplication.class);

    private static String CREATE_VISUALIZATION_SUGGESTION_ENDPOINT ="/suggestions/new";
    private static String DELETE_VISUALIZATION_SUGGESTION_ENDPOINT ="/suggestions/?";
    private static String UPDATE_VISUALIZATION_SUGGESTION_ENDPOINT ="/suggestions/?";
    private static String GET_VISUALIZATION_SUGGESTION_BYPORT_CONFIGURATION_ENDPOINT ="/suggestions/listByPortConfiguration";
    private static String GET_VISUALIZATION_SUGGESTION_BYDATASET_CONFIGURATION_ENDPOINT ="/suggestions/listByDataSetConfiguration";
    private static String D3_BAR_CHART_SAMPLE_OLAP_DATASET = "olapValidDataSetD3BarChart.json";
    private static String D3_BAR_CHART_VALID_OLAP_PORT_CONFIG = "olapValidPortConfigurationD3BarChart.json";
    private static String GOOGLE_PIE_CHART_SAMPLE_OLAP_DATASET = "olapValidDataSetGooglePieChart.json";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private ObjectWriter objectWriter;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        objectMapper = new ObjectMapper();
        objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Ignore
    public void createVisualizationSuggestionTest() throws Exception{
        logTestHeader(CREATE_VISUALIZATION_SUGGESTION_ENDPOINT);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(VisualizationFrameworkControllerTest.GET_FRAMEWORKS_LIST_ENDPOINT)
                .contentType(contentType))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        VisualizationFrameworksDetailsResponse visualizationFrameworksDetailsResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), VisualizationFrameworksDetailsResponse.class);
        if (visualizationFrameworksDetailsResponse.getVisualizationFrameworks().size() == 0) {
            log.error("No Visualization Frameworks exist, cannot create suggestion. Endpoint test : " + VisualizationFrameworkControllerTest.GET_FRAMEWORKS_LIST_ENDPOINT + " failed");
            fail("No Visualization Frameworks exist, cannot create suggestion.");
        }
        //choose one framework from the list at random
        int randomFrameworkIndex = new Random().nextInt(visualizationFrameworksDetailsResponse.getVisualizationFrameworks().size());
        //choose a method from the framework at random
        int randomMethodIndex = new Random().nextInt(visualizationFrameworksDetailsResponse.getVisualizationFrameworks().get(randomFrameworkIndex).getVisualizationMethods().size());
        AddNewVisualizationSuggestionRequest addNewVisualizationSuggestionRequest = new AddNewVisualizationSuggestionRequest();
        OLAPDataSet dataSet = objectMapper.readValue(new File(getClass().getClassLoader().getResource(D3_BAR_CHART_SAMPLE_OLAP_DATASET).toURI()), OLAPDataSet.class);
        addNewVisualizationSuggestionRequest.setOlapDataSet(dataSet);
        addNewVisualizationSuggestionRequest.setVisualizationMethodId(visualizationFrameworksDetailsResponse.getVisualizationFrameworks().get(randomFrameworkIndex).getVisualizationMethods().get(randomMethodIndex).getId());
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(CREATE_VISUALIZATION_SUGGESTION_ENDPOINT)
                .contentType(contentType)
                .content(objectMapper.writeValueAsString(addNewVisualizationSuggestionRequest)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        AddNewVisualizationSuggestionResponse addNewVisualizationSuggestionResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AddNewVisualizationSuggestionResponse.class);
        log.info("Add new visualization suggestion response: "+objectMapper.writeValueAsString(addNewVisualizationSuggestionResponse));
        logTestFooter(CREATE_VISUALIZATION_SUGGESTION_ENDPOINT);
    }

    @Test
    public void getVisualizationSuggestionsByDatasetConfiguration() throws Exception{
        logTestHeader(GET_VISUALIZATION_SUGGESTION_BYDATASET_CONFIGURATION_ENDPOINT);
        GetVisualizationSuggestionsRequest getVisualizationSuggestionsRequest = new GetVisualizationSuggestionsRequest();
        getVisualizationSuggestionsRequest.setDataSetConfiguration(objectMapper.readValue(new File(getClass().getClassLoader().getResource(D3_BAR_CHART_SAMPLE_OLAP_DATASET).toURI()), OLAPDataSet.class));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(GET_VISUALIZATION_SUGGESTION_BYDATASET_CONFIGURATION_ENDPOINT)
                .contentType(contentType)
                .content(objectMapper.writeValueAsString(getVisualizationSuggestionsRequest)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        GetVisualizationSuggestionsResponse getVisualizationSuggestionsResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GetVisualizationSuggestionsResponse.class);
        log.info("Get visualization suggestion response: "+objectMapper.writeValueAsString(getVisualizationSuggestionsResponse));
        logTestFooter(GET_VISUALIZATION_SUGGESTION_BYDATASET_CONFIGURATION_ENDPOINT);
    }

    @Test
    @Ignore
    public void removeVisualizationSuggestionTest() throws Exception{
        logTestHeader(DELETE_VISUALIZATION_SUGGESTION_ENDPOINT);
        // first get the list of visualization suggestions
        GetVisualizationSuggestionsRequest getVisualizationSuggestionsRequest = new GetVisualizationSuggestionsRequest();
        getVisualizationSuggestionsRequest.setOlapPortConfiguration(objectMapper.readValue(new File(getClass().getClassLoader().getResource(D3_BAR_CHART_VALID_OLAP_PORT_CONFIG).toURI()), OLAPPortConfiguration.class));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(GET_VISUALIZATION_SUGGESTION_BYPORT_CONFIGURATION_ENDPOINT)
                .contentType(contentType)
                .content(objectMapper.writeValueAsString(getVisualizationSuggestionsRequest)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        GetVisualizationSuggestionsResponse getVisualizationSuggestionsResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GetVisualizationSuggestionsResponse.class);
        if (getVisualizationSuggestionsResponse.getSuggestions().size() == 0) {
            log.error("No Visualization Suggestions exist, cannot delete. Endpoint test : " + DELETE_VISUALIZATION_SUGGESTION_ENDPOINT + " failed");
            fail("No Visualization Suggestions exist, cannot delete.");
        }
        //next pick one at random
        int randomSuggestionIndex = new Random().nextInt(getVisualizationSuggestionsResponse.getSuggestions().size());
        String suggestionDeleteURL = DELETE_VISUALIZATION_SUGGESTION_ENDPOINT.replace("?", Long.toString(getVisualizationSuggestionsResponse.getSuggestions().get(randomSuggestionIndex).getSuggestionId()));
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(suggestionDeleteURL)
                .contentType(contentType))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        DeleteVisualizationSuggestionResponse deleteVisualizationSuggestionResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), DeleteVisualizationSuggestionResponse.class);
        assertTrue(deleteVisualizationSuggestionResponse.getSuccess());
        logTestFooter(DELETE_VISUALIZATION_SUGGESTION_ENDPOINT);
    }

    @Test
    @Ignore
    public void updateVisualizationSuggestionTest() throws Exception{
        logTestHeader(UPDATE_VISUALIZATION_SUGGESTION_ENDPOINT);
        // first get the list of visualization suggestions
        GetVisualizationSuggestionsRequest getVisualizationSuggestionsRequest = new GetVisualizationSuggestionsRequest();
        getVisualizationSuggestionsRequest.setOlapPortConfiguration(objectMapper.readValue(new File(getClass().getClassLoader().getResource(D3_BAR_CHART_VALID_OLAP_PORT_CONFIG).toURI()), OLAPPortConfiguration.class));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(GET_VISUALIZATION_SUGGESTION_BYPORT_CONFIGURATION_ENDPOINT)
                .contentType(contentType)
                .content(objectMapper.writeValueAsString(getVisualizationSuggestionsRequest)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        GetVisualizationSuggestionsResponse getVisualizationSuggestionsResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GetVisualizationSuggestionsResponse.class);
        if (getVisualizationSuggestionsResponse.getSuggestions().size() == 0) {
            log.error("No Visualization Suggestions exist, cannot update. Endpoint test : " + UPDATE_VISUALIZATION_SUGGESTION_ENDPOINT + " failed");
            fail("No Visualization Suggestions exist, cannot update.");
        }
        //next pick one at random
        int randomSuggestionIndex = new Random().nextInt(getVisualizationSuggestionsResponse.getSuggestions().size());
        String updateSuggestionURL = UPDATE_VISUALIZATION_SUGGESTION_ENDPOINT.replace("?", Long.toString(getVisualizationSuggestionsResponse.getSuggestions().get(randomSuggestionIndex).getSuggestionId()));
        UpdateVisualizationSuggestionRequest updateVisualizationSuggestionRequest = new UpdateVisualizationSuggestionRequest();
        VisualizationSuggestion newVisualizationSuggestion = new VisualizationSuggestion();
        newVisualizationSuggestion.setOlapDataSetConfiguration(objectWriter.writeValueAsString((objectMapper.readValue(new File(getClass().getClassLoader().getResource(GOOGLE_PIE_CHART_SAMPLE_OLAP_DATASET).toURI()), OLAPDataSet.class))));
        updateVisualizationSuggestionRequest.setVisualizationSuggestion(newVisualizationSuggestion);
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(updateSuggestionURL)
                .contentType(contentType)
                .content(objectMapper.writeValueAsString(updateVisualizationSuggestionRequest)))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        UpdateVisualizationSuggestionResponse updateVisualizationSuggestionResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),UpdateVisualizationSuggestionResponse.class);
        assertNotNull(updateVisualizationSuggestionResponse.getVisualizationSuggestion());
        logTestFooter(UPDATE_VISUALIZATION_SUGGESTION_ENDPOINT);
    }

    private void logTestHeader(String testEndpointURL) {
        log.info("***** Starting test : " + testEndpointURL + " *****");
    }

    private void logTestFooter(String testEndpointURL) {
        log.info("***** End of test : " + testEndpointURL + " *****");
    }

}
