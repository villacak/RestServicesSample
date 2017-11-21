package au.com.rest.test.business.mappers;

import au.com.rest.test.entities.user.UserEntity;
import au.com.rest.test.pojos.UserApp;

/**
 * Once both modules became projects this mapper packege is not needed anymore
 * as the map will be straight from JSON to pojos and if validation succeed or any other needed task
 * to be done.
 * Then is just call the service.
 */
public class UserMapper {

    public UserEntity toUserEntity(final UserApp userApp) {
        final UserEntity toReturn = new UserEntity();
        toReturn.setId(userApp.getId());
        toReturn.setLogin(userApp.getLogin());
        toReturn.setPassword(userApp.getPassword());
        return toReturn;
    }


    public UserApp toUserEntity(final UserEntity userEntity) {
        final UserApp toReturn = new UserApp();
        toReturn.setId(userEntity.getId());
        toReturn.setLogin(userEntity.getLogin());
        toReturn.setPassword(userEntity.getPassword());
        return toReturn;
    }


}
