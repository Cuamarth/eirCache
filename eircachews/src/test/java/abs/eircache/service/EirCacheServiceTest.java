package abs.eircache.service;

import abs.eircache.model.ResponseModel;
import abs.eircache.service.impl.EirCacheServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


/**
 * @author beniteza
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EirCacheServiceTest {



    @Spy
    RestTemplate restTemplate=new RestTemplate();

    @InjectMocks
    private EirCacheService service=new EirCacheServiceImpl();

    @Before
    public void setUp(){
        ReflectionTestUtils.setField(service,"baseURL","");
    }

    @Test
    public void testGetApi() throws Exception {

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo("/pcw/PCW45-12345-12345-1234X/address/ie/D02X285?lines=3&format=json")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("{ \"addressline1\" : \"Dept of Communications, Climate Change and Natural Resources\", \"addressline2\" : \"29-31 Adelaide Road\"}", MediaType.APPLICATION_JSON));


        ResponseModel responseModel=service.getAPIResponseCached("/pcw/PCW45-12345-12345-1234X/address/ie/D02X285","lines=3&format=json");

        Assert.assertTrue(responseModel.getResponse().contains("Dept of Communications, Climate Change and Natural Resources"));
    }

    @Test
    public void testgetApiWithException() throws Exception {

        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo("/pcw/PCW45-12345-12345-1234X/address1/ie/D02X2851?lines=3&format=json")).andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());


        ResponseModel responseModel=service.getAPIResponseCached("/pcw/PCW45-12345-12345-1234X/address1/ie/D02X2851","lines=3&format=json");

        Assert.assertTrue(responseModel.getStatus().equals(HttpStatus.INTERNAL_SERVER_ERROR));
    }




}
