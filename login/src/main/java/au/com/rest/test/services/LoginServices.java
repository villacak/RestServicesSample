package au.com.rest.test.services;

import au.com.rest.test.business.LoginCheck;
import au.com.rest.test.pojos.FailResponse;
import au.com.rest.test.pojos.UserApp;
import au.com.rest.test.pojos.UserAppDetails;
import au.com.rest.test.services.helper.ServicesHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/v1/login")
public class LoginServices {


    /**
     * This service will check if the user exist
     * At moment I didn;t have time to crete the filter.
     * Should do it this weekend..
     * Then it will return a token within the header as well
     * if success validated
     *
     * URL example: http://localhost:8080/resttest/v1/login/check
     *
     * raw payload example
     * {"login": "userLogin", "password": "myTestPassword"}
     *
     * @param userApp
     * @return
     */
    @POST
    @Path("check")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(final UserApp userApp) {
        Response response = null;
        if (userApp == null) {
            final ServicesHelper helper = new ServicesHelper();
            response = helper.emptyPayload();
        } else {
            try {
                final LoginCheck loginCheck = new LoginCheck();
                response = loginCheck.checkLogin(userApp);
            } catch (Exception e) {
                final FailResponse fail = new FailResponse(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
                response = Response.serverError().entity(fail).type(MediaType.APPLICATION_JSON_TYPE).build();
            }
        }
        return response;
    }


    /**
     * Create a new user if it doesn't exist
     *
     *
     * URL example: http://localhost:8080/resttest/v1/login/create
     * raw payload example
     * {
     *   "login": "mylogin",
     *   "password": "mypassword",
     *   "fullName": "firstName lastName",
     *   "email": "firstName.lastName@somewhere.com.au",
     *   "phone": "1234 567 890"
     * }
     *
     * @param userDetails
     * @return
     */
    @PUT
    @Path("create")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createNewUser(final UserAppDetails userDetails) {
        final Response response;
        if (userDetails == null) {
            final ServicesHelper helper = new ServicesHelper();
            response = helper.emptyPayload();
        } else {
            final LoginCheck loginCheck = new LoginCheck();
            response = loginCheck.createUser(userDetails);
        }
        return response;
    }
}
