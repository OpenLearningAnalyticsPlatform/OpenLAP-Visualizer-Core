package de.rwthaachen.openlap.visualizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import de.rwthaachen.openlap.visualizer.core.dtos.request.GenerateVisualizationCodeRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

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
    private static String FILE_OLAP_TEST_DATASET = "olapValidDataSet.json";

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
    public void visualizationCodeGenerationTest(){
        GenerateVisualizationCodeRequest generateVisualizationCodeRequest;
    }

    @Test
    public void testNonExistingEndpoints() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/something")
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isNotImplemented())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

    }

}
