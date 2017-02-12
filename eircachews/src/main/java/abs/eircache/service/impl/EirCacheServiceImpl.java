package abs.eircache.service.impl;

import abs.eircache.model.ResponseModel;
import abs.eircache.service.EirCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


/**
 * @inheritDoc
 * @author beniteza
 *
 */
@Service
public class EirCacheServiceImpl implements EirCacheService {

    @Value("${ws.base.url}")
    private String baseURL;

    @Autowired
    RestTemplate restTemplate;

    public ResponseModel getAPIResponse(String requestURL,String params) {

        try {
            ResponseModel responseModel = new ResponseModel();

            ResponseEntity entity = restTemplate.getForEntity(buildFullGetUrl(requestURL,params), String.class, params);

            responseModel.setStatus(entity.getStatusCode());
            responseModel.setType(entity.getHeaders().getContentType());
            responseModel.setResponse(entity.getBody()==null? "":entity.getBody().toString());

            return  responseModel;
        }catch(RestClientException ex){
            return new ResponseModel(ex.getMessage(),MediaType.TEXT_PLAIN, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Cacheable("remoteRequests")
    public ResponseModel getAPIResponseCached(String requestURL, String params) {
        return getAPIResponse(requestURL,params);

    }




    private String  buildFullGetUrl(String requestURL, String query){
        return  baseURL + requestURL+"?"+query;
    }



}
