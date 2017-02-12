package abs.eircache.ws.controller;

import abs.eircache.model.ResponseModel;
import abs.eircache.service.EirCacheService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsondoc.core.annotation.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *@author  beniteza
 *
 * Main controller used to work as mirror of https://developers.alliescomputing.com/postcoder-web-api
 *
 */
@Api(name = "EirCodeController", description = "Methods to get petitions from https://developers.alliescomputing.com/postcoder-web-api based on cache ", group = "Main")
@RestController
@RequestMapping({"/address", "/addressgeo","/position","/rgeo"})
public class EirCodeController {


    @Autowired
    EirCacheService eirCacheService;

    private Log log = LogFactory.getLog(EirCodeController.class);

    /**
     * Accepts up to 5 slash parameters in request and do the exact same call to external service
     *
     *
     * @param request
     * @param response
     */
    @RequestMapping({"/*", "/*/*","/*/*/*","/*/*/*/*","/*/*/*/*/*"})
    public String  mirrorResponse(HttpServletRequest request, HttpServletResponse response ){



        log.info("Proccessing "+request.getRequestURI()+"?"+request.getQueryString() +" will be returned from cache if available");
        ResponseModel responseModel=eirCacheService.getAPIResponseCached(request.getRequestURI(),request.getQueryString());

        if (!responseModel.getStatus().equals(HttpStatus.OK)){
            return processErrorResponse(response,responseModel.getStatus());

        }
        log.info("Returning OK status  for request");
        response.setContentType(responseModel.getType().toString());
        response.setStatus(responseModel.getStatus().value());

        return responseModel.getResponse();
    }


    private String processErrorResponse(HttpServletResponse response,HttpStatus status){

        response.setStatus(status.value());
        log.info("Returning "+status.value()+" status  for request");
        return "";

    }


}
