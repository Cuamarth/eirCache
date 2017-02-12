package abs.eircache.ws.controller;

import abs.eircache.model.ResponseModel;
import abs.eircache.service.EirCacheService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author beniteza
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EirCodeControllerTest {


    @Autowired
    private MockMvc mvc;

    @Mock
    private EirCacheService eirCacheService;

    @Test
    public void testSuccessCall() throws Exception {

        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        ResponseModel responseModel=new ResponseModel("{\"addressline1\":\"Dept of Communications, Climate Change and Natural Resources\",\"addressline2\":\"29-31 Adelaide Road\"}",
                MediaType.APPLICATION_JSON, HttpStatus.OK);

        when(eirCacheService.getAPIResponseCached("/pcw/PCW45-12345-12345-1234X/address/ie/D02X285","lines=3&format=json")).thenReturn(responseModel);

        mvc.perform(MockMvcRequestBuilders.get("/pcw/PCW45-12345-12345-1234X/address/ie/D02X285?lines=3&format=json").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Dept of Communications, Climate Change and Natural Resources")));
    }




}
