package abs.eircache.model;


import javassist.SerialVersionUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.Serializable;

/**
 *
 * Response Model  used to give information to controller callers
 *
 */
public class ResponseModel  implements Serializable{

    String response;

    MediaType type;

    HttpStatus status;

    private long SerialVersionUID = 12456L;


    public ResponseModel(){};

    public ResponseModel(String response, MediaType type, HttpStatus status){

        this.response=response;
        this.type=type;
        this.status=status;
    };




    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
