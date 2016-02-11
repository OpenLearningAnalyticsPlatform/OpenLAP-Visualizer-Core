package de.rwthaachen.openlap.visualizer;

import DataSet.OLAPDataSet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.rwthaachen.openlap.visualizer.core.dtos.request.GenerateVisualizationCodeRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.response.GenerateVisualizationCodeResponse;
import de.rwthaachen.openlap.visualizer.core.dtos.response.VisualizationFrameworksDetailsResponse;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationMethod;
import org.junit.Before;
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
import java.util.Optional;
import java.util.Random;

import static junit.framework.TestCase.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by bas on 12/1/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OpenLAPVisualizerApplication.class)
@WebAppConfiguration
@ActiveProfiles("development")
public class VisualizationEngineTest {
    private static final Logger log =
            LoggerFactory.getLogger(OpenLAPVisualizerApplication.class);
    private static String GENERATE_VIS_CODE_ENDPOINT = "/generateVisualizationCode";
    private static String D3_BAR_CHART_SAMPLE_OLAP_DATASET = "olapValidDataSetD3BarChart.json";
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


    public void generateVisualizationCode(String olapDataSetSampleFile, String frameworkName, String methodName) throws Exception{
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(VisualizationFrameworkControllerTest.GET_FRAMEWORKS_LIST_ENDPOINT)
                .contentType(contentType))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        VisualizationFrameworksDetailsResponse visualizationFrameworksDetailsResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), VisualizationFrameworksDetailsResponse.class);
        log.info("List of frameworks : " + objectWriter.writeValueAsString(visualizationFrameworksDetailsResponse));
        if (visualizationFrameworksDetailsResponse.getVisualizationFrameworks().size() == 0) {
            log.error("No Visualization Frameworks exist, cannot update. Endpoint test : " + VisualizationFrameworkControllerTest.GET_FRAMEWORKS_LIST_ENDPOINT + " failed");
            fail("No Visualization Frameworks exist, code generation cannot proceed.");
        }
        Optional<VisualizationFramework> visualizationFramework = visualizationFrameworksDetailsResponse.getVisualizationFrameworks()
                .stream()
                .filter((framework) -> framework.getName().equals(frameworkName))
                .findFirst();
        if(visualizationFramework.isPresent()){
            OLAPDataSet sampleDataSet = objectMapper.readValue(new File(getClass().getClassLoader().getResource(olapDataSetSampleFile).toURI()), OLAPDataSet.class);
            GenerateVisualizationCodeRequest generateVisualizationCodeRequest = new GenerateVisualizationCodeRequest();
            generateVisualizationCodeRequest.setDataSet(sampleDataSet);
            generateVisualizationCodeRequest.setFrameworkId(visualizationFramework.get().getId());
            Optional<VisualizationMethod> visualizationMethod = visualizationFramework.get().getVisualizationMethods()
                    .stream()
                    .filter((method) -> method.getName().equals(methodName))
                    .findFirst();
            if(visualizationMethod.isPresent()){
                generateVisualizationCodeRequest.setMethodId(visualizationMethod.get().getId());
                // now make the request to get the client visualization code
                mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(GENERATE_VIS_CODE_ENDPOINT)
                        .contentType(contentType)
                        .content(objectMapper.writeValueAsString(generateVisualizationCodeRequest)))
                        .andExpect(status().is(HttpStatus.OK.value()))
                        .andReturn();
                GenerateVisualizationCodeResponse generateVisualizationCodeResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), GenerateVisualizationCodeResponse.class);
                log.info("Generated visualization code: "+objectMapper.writeValueAsString(generateVisualizationCodeResponse));
            }else{
                log.error("Visualization method with name: "+methodName+" not found for framework: "+frameworkName);
                fail("Visualization method with name: "+methodName+" not found for framework: "+frameworkName);
            }
        }else{
            log.error("Visualization framework with the name : "+frameworkName+" not found");
            fail("Visualization framework with the name : "+frameworkName+" not found");
        }
    }

    @Test
    public void visualizationCodeGenerationTest(){
        try {
            logTestHeader(GENERATE_VIS_CODE_ENDPOINT);
            generateVisualizationCode(D3_BAR_CHART_SAMPLE_OLAP_DATASET,"d3js","Bar Chart");
            generateVisualizationCode(GOOGLE_PIE_CHART_SAMPLE_OLAP_DATASET,"Google Charts","Pie Chart");
            logTestFooter(GENERATE_VIS_CODE_ENDPOINT);
        } catch (Exception exception) {
            log.error("Vis code  generation test failed.",exception);
            exception.printStackTrace();
        }
    }

    private void logTestHeader(String testEndpointURL) {
        log.info("***** Starting test : " + testEndpointURL + " *****");
    }

    private void logTestFooter(String testEndpointURL) {
        log.info("***** End of test : " + testEndpointURL + " *****");
    }
}
