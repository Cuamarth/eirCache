package abs.eircache.service;


import abs.eircache.model.ResponseModel;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;

/**
 * Service which does a call to to https://developers.alliescomputing.com/postcoder-web-api with the params supplied and returns the result
 * @beniteza
 */
public interface EirCacheService {


    /**
     * Gets a reponse from external API
     *
     * @return
     */
    ResponseModel getAPIResponse(String requestURL,String params);



    /**
     * Gets a cached  reponse from external API
     *
     * @return
     */
    ResponseModel getAPIResponseCached(String requestURL, String params);
}
