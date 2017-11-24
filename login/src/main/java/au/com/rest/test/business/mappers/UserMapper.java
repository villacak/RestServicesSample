package au.com.rest.test.business.mappers;

import au.com.rest.test.entities.user.UserDetailsEntity;
import au.com.rest.test.entities.user.UserEntity;
import au.com.rest.test.pojos.UserApp;
import au.com.rest.test.pojos.UserAppDetails;

/**
 * Once both modules became projects this mapper packege is not needed anymore
 * as the map will be straight from JSON to pojos and if validation succeed or any other needed task
 * to be done.
 * Then is just call the service.
 */
public class UserMapper {

    public UserEntity toUserEntity(final UserApp payload) {
        final UserEntity toReturn = new UserEntity();
        toReturn.setId(payload.getId());
        toReturn.setLogin(payload.getLogin());
        toReturn.setPassword(payload.getPassword());
        return toReturn;
    }


    public UserApp toUser(final UserEntity entity) {
        final UserApp toReturn = new UserApp();
        toReturn.setId(entity.getId());
        toReturn.setLogin(entity.getLogin());
        toReturn.setPassword(entity.getPassword());
        return toReturn;
    }


    public UserDetailsEntity toUserDetailsEntity(final UserAppDetails payload) {
        final UserEntity innerToReturn = toUserEntity((UserApp) payload);
        final UserDetailsEntity toReturn = new UserDetailsEntity();
        toReturn.setEmail(payload.getEmail());
        toReturn.setFullName(payload.getFullName());
        toReturn.setPhone(payload.getPhone());
        toReturn.setUserEntity(innerToReturn);
        return toReturn;
    }


    public UserAppDetails toUserDetailsEntity(final UserDetailsEntity payload) {
        final UserApp innerToReturn = toUser(payload.getUserEntity());
        final UserAppDetails toReturn = new UserAppDetails();
        toReturn.setEmail(payload.getEmail());
        toReturn.setFullName(payload.getFullName());
        toReturn.setPhone(payload.getPhone());
        toReturn.setLogin(innerToReturn.getLogin());
        toReturn.setPassword(innerToReturn.getPassword());
        return toReturn;
    }


}
