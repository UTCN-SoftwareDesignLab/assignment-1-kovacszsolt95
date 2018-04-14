package service.user;

import model.User;
import model.validation.Notification;

/**
 * Created by Alex on 11/03/2017.
 */
public interface AuthenticationService {

    Notification<Boolean> register(String username, String password, boolean hasAdminRights);

    Notification<User> login(String username, String password);

    Notification<Boolean> update(Long ID, String username, String password);

    boolean logout(User user);

}
