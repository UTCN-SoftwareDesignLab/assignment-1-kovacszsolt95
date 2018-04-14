package controller;

import model.User;
import model.validation.Notification;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import service.user.AuthenticationService;
import service.user.UserServices;
import view.AdminView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminController {
    private AdminView adminView;
    private UserRepository userRepository;
    private RightsRolesRepository rightsRolesRepository;
    private AuthenticationService authenticationService;
    private UserServices userServices;

    public AdminController(AuthenticationService authenticationService, UserServices userServices) {
        this.authenticationService = authenticationService;
        this.userServices = userServices;
        this.adminView = new AdminView();
        adminView.setCreateBtnACtionListener(new CreateEmployeeButtonListener());
        adminView.setEmplyeeSelectedBtnListener(new EmployeeSelectedComboBoxListener());
        adminView.setUpdateBtnActionListener(new UpdateEmployeeActionListner());
        update();
    }

    public void update() {
        this.adminView.setEmployeesBox(userServices.getEmployeeIDs());
    }

    public void setVisible(Boolean visible) {
        this.adminView.setVisible(visible);
    }

    private class CreateEmployeeButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getEmployeeName();
            String password = adminView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password, false);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Could not add employee, please try again later");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Succesfully added new employee");
                    update();
                }
            }
        }
    }

    private class UpdateEmployeeActionListner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = adminView.getEmployeeName();
            String password = adminView.getPassword();
            Long ID = adminView.getSelectedEmployeeId();

            Notification<Boolean> updateNotification = authenticationService.update(ID, username, password);
            if (updateNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), updateNotification.getFormattedErrors());
            } else {
                if (!updateNotification.getResult()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Could not update employee, please try again later");
                } else {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "Succesfully updated employee");
                    update();
                }
            }
        }
    }

    private class EmployeeSelectedComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long employeeID = adminView.getSelectedEmployeeId();
            Notification<User> selectEmployeeNotification = userServices.findByID(employeeID);
            if (selectEmployeeNotification.hasErrors()) {
                JOptionPane.showMessageDialog(adminView.getContentPane(), selectEmployeeNotification.getFormattedErrors());
            } else {
                User selectedUser = selectEmployeeNotification.getResult();
                adminView.setEmployeeName(selectedUser.getUsername());
                adminView.setPassword(selectedUser.getPassword());
            }
        }
    }
}
