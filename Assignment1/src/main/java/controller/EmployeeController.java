package controller;


import model.Account;
import model.Client;
import model.validation.Notification;
import service.account.AccountService;
import service.client.ClientServices;
import view.EmployeeView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeController {
    EmployeeView employeeView;
    ClientServices clientServices;
    AccountService accountService;

    public EmployeeController(ClientServices clientServices, AccountService accountService) {
        this.accountService = accountService;
        this.clientServices = clientServices;
        this.employeeView = new EmployeeView();
        this.setEmployeeVisible(false);
        this.employeeView.setClientsBoxListener(new employeeSelectedComboBoxListener());
        this.employeeView.setNewClientBtnListner(new NewClientButtonListener());
        this.employeeView.setUpdateClientBtnListener(new UpdateClientBtnListener());
        this.employeeView.setDeleteClientBtn(new DeleteClientActionListener());
        this.employeeView.setAccountSelectedListener(new AccounteSelectedComboBoxListener());
        this.employeeView.setCreateAccountListener(new NewAccountButtonListener());
        this.employeeView.setDeleteAccBtnListener(new DeleteAccountActionListener());
        this.employeeView.setBtnAddBalanceListener(new AddBalancetActionListener());
        this.employeeView.setBtnTransferListener(new TransferBalancetActionListener());
        updateClientBox();
    }

    public void setEmployeeVisible(boolean isVisible) {
        this.employeeView.setVisible(isVisible);
    }

    public void updateClientBox() {
        employeeView.setClientsBox(clientServices.findAllIDs());
    }

    void updateAccountsBox(Long selectedClientID) {
        this.employeeView.setAllAccountsBox(accountService.findAllIDs());
        this.employeeView.setAccountsBox(accountService.findAllIDByOwnerId(selectedClientID));
    }

    private class employeeSelectedComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long clientsID = employeeView.getSelectedIDFromClientsBox();
            Notification<Client> clientSelectedNotification = clientServices.findById(clientsID);
            if (clientSelectedNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), clientSelectedNotification.getFormattedErrors());
            } else {
                Client client = clientSelectedNotification.getResult();
                employeeView.setClientNameField(client.getName());
                employeeView.setAdressField(client.getAdress());
                employeeView.setCnpField(client.getPersonalNumericCode());
                updateAccountsBox(clientsID);
            }
        }
    }

    private class AccounteSelectedComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long accountsID = employeeView.getSelectedIDFromAccountsBox();
            Notification<Account> accountSelectedNotification = accountService.findById(accountsID);
            if (accountSelectedNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), accountSelectedNotification.getFormattedErrors());
            } else {
                Account account = accountSelectedNotification.getResult();
                employeeView.setBalanceField(account.getBalance());
                employeeView.setDateCreatedField(account.getDateOfCreation());
            }
        }
    }

    private class NewClientButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = employeeView.getClientName();
            String adress = employeeView.getAdressField();
            String CNP = employeeView.getCnpField();
            Notification<Boolean> registerNotification = clientServices.createClient(name, CNP, adress);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Could not add new Client, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client has been added!");
                    updateClientBox();
                }
            }
        }
    }

    private class NewAccountButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long ownerID = employeeView.getSelectedIDFromClientsBox();
            String balance = employeeView.getBalanceField();
            Notification<Boolean> registerNotification = accountService.createAccount(ownerID, "Saving", balance);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Could not add new Account, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "New Account has been created!");
                    updateAccountsBox(employeeView.getSelectedIDFromClientsBox());
                }
            }
        }
    }

    private class UpdateClientBtnListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = employeeView.getClientName();
            String adress = employeeView.getAdressField();
            String CNP = employeeView.getCnpField();
            Long id = employeeView.getSelectedIDFromClientsBox();
            Notification<Boolean> registerNotification = clientServices.update(id, name, CNP, adress);
            System.out.println(registerNotification);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Failed to updateClientBox Client, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client has been updated!");
                }
            }
        }
    }

    private class DeleteClientActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = employeeView.getSelectedIDFromClientsBox();
            Notification<Boolean> registerNotification = clientServices.deleteByID(id);
            System.out.println(registerNotification);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Failed to delete Client, try again later");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Client has been deleted!");
                    updateClientBox();
                }
            }
        }
    }

    private class DeleteAccountActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = employeeView.getSelectedIDFromAccountsBox();
            Notification<Boolean> deleteNotification = accountService.deleteByID(id);
            System.out.println(deleteNotification);
            if (deleteNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), deleteNotification.getFormattedErrors());
            } else {
                if (!deleteNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Failed to delete Account, try again later");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Account has been deleted!");
                    updateAccountsBox(employeeView.getSelectedIDFromClientsBox());
                }
            }
        }
    }

    private class AddBalancetActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long id = employeeView.getSelectedIDFromAccountsBox();
            String amount = employeeView.getAmountField();
            Notification<Boolean> balanceNotification = accountService.addBalanceTo(id, amount);
            System.out.println(balanceNotification);
            if (balanceNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), balanceNotification.getFormattedErrors());
            } else {
                if (!balanceNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Failed to add balance, try again later");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Balance has been updated!");
                }
            }
        }
    }

    private class TransferBalancetActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Long idFrom = employeeView.getSelectedIDFromAccountsBox();
            Long idTo = employeeView.getSelectedIDFromAllAccountsBox();
            String amount = employeeView.getAmountField();
            Notification<Boolean> balanceNotification = accountService.transfer(idFrom, idTo, amount);
            System.out.println(balanceNotification);
            if (balanceNotification.hasErrors()) {
                JOptionPane.showMessageDialog(employeeView.getContentPane(), balanceNotification.getFormattedErrors());
            } else {
                if (!balanceNotification.getResult()) {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Transfer Failed, try again later");
                } else {
                    JOptionPane.showMessageDialog(employeeView.getContentPane(), "Transaction succesful!");
                }
            }
        }
    }
}
