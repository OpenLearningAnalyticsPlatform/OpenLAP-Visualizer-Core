package de.rwthaachen.openlap.visualizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.rwthaachen.openlap.visualizer.core.dtos.request.UpdateVisualizationFrameworkRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.request.UploadVisualizationFrameworksRequest;
import de.rwthaachen.openlap.visualizer.core.dtos.response.DeleteVisualizationFrameworkResponse;
import de.rwthaachen.openlap.visualizer.core.dtos.response.UpdateVisualizationFrameworkResponse;
import de.rwthaachen.openlap.visualizer.core.dtos.response.UploadVisualizationFrameworkResponse;
import de.rwthaachen.openlap.visualizer.core.dtos.response.VisualizationFrameworksDetailsResponse;
import de.rwthaachen.openlap.visualizer.core.model.VisualizationFramework;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Random;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OpenLAPVisualizerApplication.class)
@WebAppConfiguration
@ActiveProfiles("development")
public class VisualizationFrameworkControllerTest {
    private static final Logger log =
            LoggerFactory.getLogger(OpenLAPVisualizerApplication.class);

    public static String GET_FRAMEWORKS_LIST_ENDPOINT = "/frameworks/list";
    private static String UPDATE_FRAMEWORK_DETAILS_ENDPOINT = "/frameworks/?/update";
    private static String DELETE_FRAMEWORK_ENDPOINT = "/frameworks/?";
    private static String UPLOAD_FRAMEWORKS_ENDPOINT = "/frameworks/upload";
    private static String D3_VISUALIZATIONS_JAR = "openlap-visualizer-framework-sample-d3-1.0.jar";
    private static String FRAMEWORKS_JAR_INVALID = "visualizationFrameworksInvalid.jar";
    private static String VISUALIZATION_FRAMEWORKS_VALID_UPLOAD_CONFIG = "visualizationFrameworksUploadValidConfigD3.json";
    private static String VISUALIZATION_FRAMEWORKS_INVALID_UPLOAD_CONFIG = "visualizationFrameworksUploadInvalidConfig.json";

    private static String GOOGLE_CHARTS_VALID_UPLOAD_CONFIG = "visualizationFrameworksUploadValidConfigGooglePieChart.json";
    private static String GOOGLE_CHARTS_VISUALIZATIONS_JAR = "openlap-visualizer-framework-sample-google-chart-1.0.jar";
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
    public void uploadVisualizationFrameworks() {
        try {
            logTestHeader(UPLOAD_FRAMEWORKS_ENDPOINT);
            UploadVisualizationFrameworksRequest uploadVisualizationFrameworksRequest = objectMapper.readValue(new File(getClass().getClassLoader().getResource(VISUALIZATION_FRAMEWORKS_VALID_UPLOAD_CONFIG).toURI()), UploadVisualizationFrameworksRequest.class);
            MockMultipartFile jarFileToUpload = createMultiPartFile(D3_VISUALIZATIONS_JAR);
            MvcResult mvcResult = mockMvc.perform
                    (
                            MockMvcRequestBuilders.fileUpload(UPLOAD_FRAMEWORKS_ENDPOINT)
                                    .file(jarFileToUpload)
                                    .param("frameworkConfig", objectMapper.writeValueAsString(uploadVisualizationFrameworksRequest))
                                    .contentType(MediaType.MULTIPART_FORM_DATA)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andReturn();
            UploadVisualizationFrameworkResponse uploadVisualizationFrameworkResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UploadVisualizationFrameworkResponse.class);
            assertTrue(uploadVisualizationFrameworkResponse.getSuccess());
            // now google charts
            uploadVisualizationFrameworksRequest = objectMapper.readValue(new File(getClass().getClassLoader().getResource(GOOGLE_CHARTS_VALID_UPLOAD_CONFIG).toURI()), UploadVisualizationFrameworksRequest.class);
            jarFileToUpload = createMultiPartFile(GOOGLE_CHARTS_VISUALIZATIONS_JAR);
            mvcResult = mockMvc.perform
                    (
                            MockMvcRequestBuilders.fileUpload(UPLOAD_FRAMEWORKS_ENDPOINT)
                                    .file(jarFileToUpload)
                                    .param("frameworkConfig", objectMapper.writeValueAsString(uploadVisualizationFrameworksRequest))
                                    .contentType(MediaType.MULTIPART_FORM_DATA)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andReturn();
            uploadVisualizationFrameworkResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UploadVisualizationFrameworkResponse.class);
            assertTrue(uploadVisualizationFrameworkResponse.getSuccess());
            logTestFooter(UPLOAD_FRAMEWORKS_ENDPOINT);
        } catch (Exception exception) {
            log.error("Test url: " + UPLOAD_FRAMEWORKS_ENDPOINT + " failed.", exception);
            fail(exception.getMessage());
        }
    }

    @Test
    public void getListOfVisualizationFrameworks() {
        try {
            logTestHeader(GET_FRAMEWORKS_LIST_ENDPOINT);
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(GET_FRAMEWORKS_LIST_ENDPOINT)
                    .contentType(contentType))
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andReturn();
            VisualizationFrameworksDetailsResponse visualizationFrameworksDetailsResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), VisualizationFrameworksDetailsResponse.class);
            log.info("Number of visualization frameworks existing in the system : " + visualizationFrameworksDetailsResponse.getVisualizationFrameworks().size());
            log.info("List of frameworks : " + objectWriter.writeValueAsString(visualizationFrameworksDetailsResponse));
            logTestFooter(GET_FRAMEWORKS_LIST_ENDPOINT);
        } catch (Exception exception) {
            log.error("Test url: " + GET_FRAMEWORKS_LIST_ENDPOINT + " failed.", exception);
            fail(exception.getMessage());
        }
    }

    @Ignore
    @Test
    public void updateVisualizationFrameworkDetails() {
        try {
            logTestHeader(UPDATE_FRAMEWORK_DETAILS_ENDPOINT);
            //first lets ask for a list of frameworks
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(GET_FRAMEWORKS_LIST_ENDPOINT)
                    .contentType(contentType))
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andReturn();
            VisualizationFrameworksDetailsResponse visualizationFrameworksDetailsResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), VisualizationFrameworksDetailsResponse.class);
            if (visualizationFrameworksDetailsResponse.getVisualizationFrameworks().size() == 0) {
                log.error("No Visualization Frameworks exist, cannot update. Endpoint test : " + GET_FRAMEWORKS_LIST_ENDPOINT + " failed");
                fail("No Visualization Frameworks exist, cannot update.");
            }
            //choose one framework from the list at random at update it
            int randomIndex = new Random().nextInt(visualizationFrameworksDetailsResponse.getVisualizationFrameworks().size());
            String visFrameworkUpdateURL = GET_FRAMEWORKS_LIST_ENDPOINT.replace("?", Long.toString(visualizationFrameworksDetailsResponse.getVisualizationFrameworks().get(randomIndex).getId()));
            log.info("Framework chosen at random, finalized visualization framework update URL: " + visFrameworkUpdateURL);
            VisualizationFramework updatedVisualizationFramework = visualizationFrameworksDetailsResponse.getVisualizationFrameworks().get(randomIndex);
            updatedVisualizationFramework.setCreator("Creator updated to: Unknown");
            updatedVisualizationFramework.setDescription("The description has been manipulated with");
            updatedVisualizationFramework.setName("The name of the vis framework has been tampered with");
            UpdateVisualizationFrameworkRequest updateVisualizationFrameworkRequest = new UpdateVisualizationFrameworkRequest();
            updateVisualizationFrameworkRequest.setVisualizationFramework(updatedVisualizationFramework);
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(visFrameworkUpdateURL, updateVisualizationFrameworkRequest)
                    .contentType(contentType))
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andReturn();
            UpdateVisualizationFrameworkResponse response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UpdateVisualizationFrameworkResponse.class);
            assertTrue(response.getVisualizationFramework() != null);
            logTestFooter(visFrameworkUpdateURL);
        } catch (Exception exception) {
            log.error("Test url: " + UPDATE_FRAMEWORK_DETAILS_ENDPOINT + " failed.", exception);
            fail(exception.getMessage());
        }
    }

    @Test
    public void deleteVisualizationFramework() {
        try {
            logTestHeader(DELETE_FRAMEWORK_ENDPOINT);
            //first lets ask for a list of frameworks
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(GET_FRAMEWORKS_LIST_ENDPOINT)
                    .contentType(contentType))
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andReturn();
            VisualizationFrameworksDetailsResponse visualizationFrameworksDetailsResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), VisualizationFrameworksDetailsResponse.class);
            log.info("List of frameworks : " + objectWriter.writeValueAsString(visualizationFrameworksDetailsResponse));
            if (visualizationFrameworksDetailsResponse.getVisualizationFrameworks().size() == 0) {
                log.error("No Visualization Frameworks exist, cannot update. Endpoint test : " + GET_FRAMEWORKS_LIST_ENDPOINT + " failed");
                fail("No Visualization Frameworks exist, cannot delete.");
            }
            //choose one framework from the list at random at update it
            int randomIndex = new Random().nextInt(visualizationFrameworksDetailsResponse.getVisualizationFrameworks().size());
            String visFrameworkDeleteURL = DELETE_FRAMEWORK_ENDPOINT.replace("?", Long.toString(visualizationFrameworksDetailsResponse.getVisualizationFrameworks().get(randomIndex).getId()));
            mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(visFrameworkDeleteURL)
                    .contentType(contentType))
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andReturn();
            DeleteVisualizationFrameworkResponse deleteVisualizationFrameworkResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), DeleteVisualizationFrameworkResponse.class);
            assertTrue(deleteVisualizationFrameworkResponse.getSuccess());
            logTestFooter(visFrameworkDeleteURL);
        } catch (Exception exception) {
            log.error("Test url: " + DELETE_FRAMEWORK_ENDPOINT + " failed.", exception);
            fail(exception.getMessage());
        }
    }

    private void logTestHeader(String testEndpointURL) {
        log.info("***** Starting test : " + testEndpointURL + " *****");
    }

    private void logTestFooter(String testEndpointURL) {
        log.info("***** End of test : " + testEndpointURL + " *****");
    }

    private MockMultipartFile createMultiPartFile(String jarFileName) throws IOException, URISyntaxException {
        URL jarFileURL = getClass().getClassLoader().getResource(jarFileName);
        FileInputStream jarFileInputStream = new FileInputStream(new File(jarFileURL.toURI()));
        return new MockMultipartFile("frameworkJarBundle", jarFileURL.getFile(), "multipart/form-data", jarFileInputStream);
    }

    @Test
    public void getVisualizationFrameworkDetails() {

    }

    @Test
    public void validateMethodConfiguration() {

    }

}
