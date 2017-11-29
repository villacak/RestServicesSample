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

import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.Instant;

public class LoginCheck {

    private final int MAX_TRYS = 3;
    private final String TOKEN = "token";

    /**
     * Check if the credentials exist or not
     *
     * @param userApp
     * @return
     */
    public Response checkLogin(final UserApp userApp) {
        final Response response;
        if (userApp.getLogin() == null || userApp.getPassword() == null) {
            final ServicesHelper helper = new ServicesHelper();
            response = helper.badLoginPayload();
        } else {
            // Here the right thing to do is have a rest call to a rest inner service, in this case
            // the persistence service layer.
            // But here we will just call the service.
            final PersistenceDAO daoUser = new PersistenceDAO();
            final UserSecurityEntity security = daoUser.getUserSecurity(userApp.getLogin(), KeyValueForSearch.LOGIN);
            final Timestamp timestampNow = new Timestamp(System.currentTimeMillis());
            if (security.getId() > 0 &&
                    security.getUserEntity().getPassword() != null &&
                    security.getReason() == ReasonType.ALLWOED.getCode() &&
                    security.getUserEntity().getPassword().equals(userApp.getPassword())) {
                userApp.setId(security.getId());
                security.setLastAccess(timestampNow);
                response = Response.ok(userApp).header(TOKEN, Instant.now().toString()).build();
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
                }
                security.setLastAccess(timestampNow);
                daoUser.saveData(security);
                daoUser.closeEntityManager();
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
                UserDetailsEntity entityDetails = mapper.toUserDetailsEntity(userAppDetails);
                UserEntity entity = entityDetails.getUserEntity();
                entityDetails = daoUser.saveData(entityDetails);

                if (entityDetails.getUserEntity() != null &&
                        entityDetails.getUserEntity().getPassword().equals(entity.getPassword())) {
                    final UserSecurityEntity security = new UserSecurityEntity();
                    security.setLastAccess(Timestamp.from(Instant.now()));
                    security.setFailedTrys(0);
                    security.setReason(ReasonType.ALLWOED.getCode());
                    security.setUserEntity(entityDetails.getUserEntity());
                    security.setAccountState(ReasonType.ALLWOED.name());
                    daoUser.saveData(security);
                }
                final String message = "User created with success.";
                response = Response.ok(message).header(TOKEN, Instant.now().toString()).entity(entityDetails).build();
                entityDetails = null;
                entity = null;
            }
            daoUser.closeEntityManager();
        }
        return response;
    }
}
