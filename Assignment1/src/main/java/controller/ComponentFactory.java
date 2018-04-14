package controller;

import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import service.client.ClientServices;
import service.client.ClientServicesMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserServices;
import service.user.UserServicesMySQL;

import java.sql.Connection;

/**
 * Created by Alex on 18/03/2017.
 */
public class ComponentFactory {

    private final AuthenticationService authenticationService;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ClientServices clientServices;
    private static ComponentFactory instance;
    private final AccountService accountService;
    private final UserServices userServices;

    public AccountService getAccountService() {
        return accountService;
    }

    public ClientServices getClientServices() {
        return clientServices;
    }

    public static ComponentFactory instance() {
        if (instance == null) {
            instance = new ComponentFactory();
        }
        return instance;
    }

    private ComponentFactory() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(false).getConnection();
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.clientRepository = new ClientRepositoryMySQL(connection);
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.clientServices = new ClientServicesMySQL(clientRepository);
        this.accountService = new AccountServiceMySQL(accountRepository);
        this.userServices = new UserServicesMySQL(userRepository, rightsRolesRepository);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public UserServices getUserServices() {
        return userServices;
    }
}
