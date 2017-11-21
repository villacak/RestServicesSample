package au.com.rest.test.business;

import au.com.rest.test.business.mappers.UserMapper;
import au.com.rest.test.dao.PersistenceDAO;
import au.com.rest.test.entities.user.UserEntity;
import au.com.rest.test.pojos.UserApp;
import au.com.rest.test.services.helper.ServicesHelper;

import javax.ws.rs.core.Response;

public class LoginCheck {

    public Response checkLogin(final UserApp userApp) {
        final Response response;
        if (userApp.getLogin() == null || userApp.getPassword() == null) {
            final ServicesHelper helper = new ServicesHelper();
            response =  helper.badLoginPayload();
        } else {
            // Here the right thing to do is have a rest call to a rest inner service, in this case
            // the persistence service layer.
            // But here we will just call the service.
            final UserMapper localMapper = new UserMapper();
            final PersistenceDAO daoUser = new PersistenceDAO();
            final UserEntity tempUserEntity = daoUser.getUser(userApp.getLogin());
            if (tempUserEntity.getId() > 0 && tempUserEntity.getPassword() != null) {
                userApp.setId(tempUserEntity.getId());
                response = Response.ok(userApp).build();
            } else {
                response = Response.status(Response.Status.FORBIDDEN).build();
            }
        }

        return response;
    }
}
