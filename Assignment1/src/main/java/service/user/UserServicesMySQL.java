package service.user;

import database.Constants;
import model.User;
import model.validation.Notification;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserServicesMySQL implements UserServices {
    private UserRepository userRepository;
    private RightsRolesRepository rightsRolesRepository;

    public UserServicesMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<Long> getEmployeeIDs() {
        List<User> users = userRepository.findAll();
        List<Long> userIDs = new ArrayList<>();
        for (User user : users) {
            System.out.println(user.getUsername());
            System.out.println(rightsRolesRepository.findRolesForUser(user.getId()).get(0).getRole());
            if (rightsRolesRepository.findRolesForUser(user.getId()).get(0).getRole().equals(Constants.Roles.EMPLOYEE)) {
                userIDs.add(user.getId());
            }
        }
        return userIDs;
    }

    @Override
    public Notification<User> findByID(Long ID) {
        return userRepository.findByID(ID);
    }
}
