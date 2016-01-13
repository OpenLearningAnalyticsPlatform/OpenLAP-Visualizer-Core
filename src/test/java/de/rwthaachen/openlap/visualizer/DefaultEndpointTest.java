package de.rwthaachen.openlap.visualizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.rwthaachen.openlap.visualizer.controller.DefaultController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;

/**
 * Created by bas on 12/1/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class,classes = DefaultController.class)
@WebAppConfiguration
public class DefaultEndpointTest {
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Before
    public void setup() throws Exception {
        objectMapper = new ObjectMapper();
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new DefaultController()).setMessageConverters(jackson2HttpMessageConverter).build();
    }

    @Test
    public void testNonExistingEndpoints() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/something")
                .content(objectMapper.writeValueAsString(new RequestDTO<>()))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isNotImplemented())
                .andDo(MockMvcResultHandlers.print());
    }

}
