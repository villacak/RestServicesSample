package au.com.rest.test.pojos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class FailResponse {

    private Response.Status status;
    private String message;

    public Response.Status getStatus() {
        return status;
    }

    public void setStatus(Response.Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getJSONAsString() throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonInString = mapper.writeValueAsString(this);
        return jsonInString;
    }
}
