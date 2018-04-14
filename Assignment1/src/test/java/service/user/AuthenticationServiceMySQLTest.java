package service.user;

import database.DBConnectionFactory;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;

/**
 * Created by Alex on 11/03/2017.
 */
public class AuthenticationServiceMySQLTest {

    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;

    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        authenticationService = new AuthenticationServiceMySQL(
                userRepository,
                rightsRolesRepository
        );
    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }


    @Test
    public void register() throws Exception {
        Assert.assertTrue(
                authenticationService.register("TestUsername@test.com", "Test Password1?",false).getResult()
        );
    }

    @Test
    public void login() throws Exception {
        String username = "TEST@test.com";
        String password = "123456Asd?";
        authenticationService.register(username, password,false);

        User user = authenticationService.login(username, password).getResult();

        Assert.assertNotNull(user);
    }

    @Test
    public void logout() throws Exception {

    }

}