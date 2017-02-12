package abs.eircache.test;

import abs.eircache.WebserviceApp;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * @author beniteza
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EirCodeControllerIT {


    @Autowired
    private TestRestTemplate template;




    @Test
    public void testSuccessCall() throws Exception {

        ResponseEntity<String> response = template.getForEntity("/address/ie/NR147PZ?lines=3&format=json",
                String.class);


       assertEquals(response.getStatusCode(),HttpStatus.OK);
       JSONArray array= new JSONArray(response.getBody());
       assertTrue(array.length()>0);


    }


    @Test
    public void testFailedCall() throws Exception {

        ResponseEntity<String> response = template.getForEntity("/address/novalidcountry/NR147PZ?lines=3&format=json",
                String.class);


        assertEquals(response.getStatusCode(),HttpStatus.INTERNAL_SERVER_ERROR);



    }


}
