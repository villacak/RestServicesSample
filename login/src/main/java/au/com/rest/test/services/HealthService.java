package au.com.rest.test.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/v1/health")
public class HealthService {


    @GET
    public Response checkHealth() {
        final String responseMessage = "Service working.";
        final Response response = Response.ok(responseMessage).build();
        return response;
    }


}
