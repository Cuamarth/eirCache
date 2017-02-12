package abs.eircache.service.impl;

import abs.eircache.model.ResponseModel;
import abs.eircache.service.EirCacheService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


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

    private Log log = LogFactory.getLog(EirCacheServiceImpl.class);

    public ResponseModel getAPIResponse(String requestURL,String params) {

        try {
            ResponseModel responseModel = new ResponseModel();

            ResponseEntity entity = restTemplate.getForEntity(buildFullGetUrl(requestURL,params), String.class, params);

            responseModel.setStatus(entity.getStatusCode());
            responseModel.setType(entity.getHeaders().getContentType());
            responseModel.setResponse(entity.getBody()==null? "":entity.getBody().toString());

            return  responseModel;
        }catch(RestClientException ex){
            log.error("Error doing restClient request  content to servlet",ex);
            return new ResponseModel(ex.getMessage(),MediaType.TEXT_PLAIN, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Cacheable("remoteRequests")
    public ResponseModel getAPIResponseCached(String requestURL, String params) {
        log.info("Request "+requestURL+"?"+params+" not available in cache, requesting to external API");
        return getAPIResponse(requestURL,params);

    }




    private String  buildFullGetUrl(String requestURL, String query){

        String fullURL=baseURL + requestURL+"?"+query;
        log.info("External API url used :"+fullURL);
        return fullURL;
    }



}
