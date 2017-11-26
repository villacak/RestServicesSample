package au.com.rest.test.business;

import au.com.rest.test.business.enums.ReasonType;
import au.com.rest.test.business.mappers.UserMapper;
import au.com.rest.test.dao.PersistenceDAO;
import au.com.rest.test.entities.user.UserDetailsEntity;
import au.com.rest.test.entities.user.UserEntity;
import au.com.rest.test.entities.user.UserSecurityEntity;
import au.com.rest.test.enums.KeyValueForSearch;
import au.com.rest.test.pojos.UserApp;
import au.com.rest.test.pojos.UserAppDetails;
import au.com.rest.test.services.helper.ServicesHelper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

@Path("/v1/login")
@Consumes(MediaType.WILDCARD)
@Produces(MediaType.APPLICATION_JSON)
public class LoginCheck {

    private final int MAX_TRYS = 3;

    /**
     * Check if the credentials exist or not
     *
     * @param userApp
     * @return
     */
    @POST
    @Path("check")
    public Response checkLogin(final UserApp userApp) {
        final Response response;
        if (userApp.getLogin() == null || userApp.getPassword() == null) {
            final ServicesHelper helper = new ServicesHelper();
            response =  helper.badLoginPayload();
        } else {
            // Here the right thing to do is have a rest call to a rest inner service, in this case
            // the persistence service layer.
            // But here we will just call the service.
            final PersistenceDAO daoUser = new PersistenceDAO();
            final UserSecurityEntity security = daoUser.getUserSecurity(userApp.getLogin(), KeyValueForSearch.LOGIN);
            if (security.getId() > 0 &&
                    security.getUserEntity().getPassword() != null &&
                    security.getReason() == ReasonType.ALLWOED.getCode()) {
                userApp.setId(security.getId());
                response = Response.ok(userApp).build();
            } else {
                if (security != null) {
                    final Timestamp ts = new Timestamp(System.currentTimeMillis());
                    int trys = security.getFailedTrys();
                    if (trys < MAX_TRYS) {
                        security.setFailedTrys(trys++);
                    } else {
                        security.setReason(ReasonType.TOO_MANY_TRYS.getCode());
                    }
                    security.setLastAccess(ts);
                    daoUser.saveData(security);
                }
                response = Response.status(Response.Status.FORBIDDEN).build();
            }
        }

        return response;
    }


    /**
     * Create a new user if doesn't exist
     *
     * Everywhere you see PersistenceDAO will be replaced by a rest client call
     *
     * @param userAppDetails
     * @return
     */
    @PUT
    @Path("createUser")
    public Response createUser(final UserAppDetails userAppDetails) {
        final Response response;
        if (userAppDetails.getLogin() == null || userAppDetails.getPassword() == null) {
            final ServicesHelper helper = new ServicesHelper();
            response =  helper.badLoginPayload();
        } else {
            final PersistenceDAO daoUser = new PersistenceDAO();
            final UserDetailsEntity tempUserEntity = daoUser.getUserDetails(userAppDetails.getLogin(), KeyValueForSearch.LOGIN);
            if (tempUserEntity != null) {
                final ServicesHelper helper = new ServicesHelper();
                response =  helper.existingUserLoginPayload();
            } else {
                final UserMapper mapper = new UserMapper();
                UserDetailsEntity entity = mapper.toUserDetailsEntity(userAppDetails);

                final PersistenceDAO dao = new PersistenceDAO();
                entity = dao.saveData(entity);
                final String message = "User created with success.";
                response = Response.ok(message).entity(entity).build();
            }
        }
        return response;
    }
}
