package controller;

import database.Constants;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import service.account.AccountService;
import service.client.ClientServices;
import service.user.AuthenticationService;
import service.user.UserServices;


public class MasterController implements LoginOccuredCallback {
    private LoginController loginCtrl;
    private AdminController adminCtrl;
    private EmployeeController employeeCtrl;
    private ComponentFactory componentFactory;

    public MasterController() {
        this.componentFactory = ComponentFactory.instance();
        AuthenticationService authenticationService = componentFactory.getAuthenticationService();
        UserRepository userRepository = componentFactory.getUserRepository();
        RightsRolesRepository rightsRolesRepository = componentFactory.getRightsRolesRepository();
        ClientServices clientServices = componentFactory.getClientServices();
        AccountService accountService = componentFactory.getAccountService();
        UserServices userServices = componentFactory.getUserServices();
        this.adminCtrl = new AdminController(authenticationService, userServices);
        this.adminCtrl.setVisible(false);
        this.loginCtrl = new LoginController(this, authenticationService);
        this.employeeCtrl = new EmployeeController(clientServices, accountService);
    }

    @Override
    public void loginOccured(String loginType) {
        loginCtrl.setLoginVisible(false);
        if (loginType.equals(Constants.Roles.ADMINISTRATOR)) {
            this.adminCtrl.setVisible(true);
        } else {
            this.employeeCtrl.setEmployeeVisible(true);
        }
    }
}
