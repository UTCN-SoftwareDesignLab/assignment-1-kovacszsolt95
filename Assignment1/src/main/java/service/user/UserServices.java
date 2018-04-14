package service.user;

import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserServices {
    List<Long> getEmployeeIDs();

    Notification<User> findByID(Long ID);
}
