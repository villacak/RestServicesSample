package au.com.rest.test.services;

import au.com.rest.test.business.LoginCheck;
import au.com.rest.test.pojos.UserApp;
import au.com.rest.test.services.helper.ServicesHelper;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("/v1/Login")
@Consumes("*/*")
public class LoginServices {


    /**
     * This service will check if the user exist
     * At moment I didn;t have time to crete the filter.
     * Should do it this weekend..
     * Then it will return a token within the header as well
     * if success validated
     *
     * @param userApp
     * @return
     */
    @POST
    public Response login(final UserApp userApp) {
        final Response response;
        if (userApp == null) {
            final ServicesHelper helper = new ServicesHelper();
            response = helper.emptyPayload();
        } else {
             final LoginCheck loginCheck = new LoginCheck();
            response = loginCheck.checkLogin(userApp);
        }
        return response;
    }


    @PUT
    public Response createNewUser(final String userDetails) {
        final Response response = null;


        return response;
    }


}