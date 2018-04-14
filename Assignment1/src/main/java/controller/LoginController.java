package controller;

import database.Constants;
import model.User;
import model.validation.Notification;
import service.user.AuthenticationService;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Alex on 18/03/2017.
 */
public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private LoginOccuredCallback loginOccuredCallback;

    public LoginController(LoginOccuredCallback loginOccuredCallback, AuthenticationService authenticationService) {
        this.loginView = new LoginView();
        this.loginOccuredCallback = loginOccuredCallback;
        this.authenticationService = authenticationService;
        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    public void setLoginVisible(Boolean visible) {
        this.loginView.setVisible(visible);
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = authenticationService.login(username, password);
            if (loginNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                loginView.setVisible(false);
                if (isAdmin(loginNotification)) {
                    loginOccuredCallback.loginOccured(Constants.Roles.ADMINISTRATOR);
                } else {
                    loginOccuredCallback.loginOccured(Constants.Roles.EMPLOYEE);
                }
            }
        }

        private boolean isAdmin(Notification<User> loginNotification) {
            //System.out.println(loginNotification.getResult().getRoles().get(0).getRole().equals(Constants.Roles.ADMINISTRATOR));
            return loginNotification.getResult().getRoles().get(0).getRole().equals(Constants.Roles.ADMINISTRATOR);
        }
    }


    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password, false);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                }
            }
        }
    }


}
