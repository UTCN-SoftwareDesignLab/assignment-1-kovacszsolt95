package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class EmployeeView extends JFrame {

    private JPanel contentPane;
    private JTextField clientNameField;
    private JTextField cnpField;
    private JTextField adressField;
    private JTextField dateCreatedField;
    private JTextField balanceField;
    private JTextField AmountField;
    private JButton btnTransfer;
    private JButton btnAddBalance;
    private JComboBox allAccountsBox;
    private JComboBox accountsBox;
    private JButton newClientBtn;
    private JButton updateClientBtn;
    private JComboBox clientsBox;
    private JButton createNewAccountBtn;
    private JButton deleteClientBtn;
    private JButton deleteAccBtn;

    public String getClientName() {
        return clientNameField.getText();
    }

    public void setClientNameField(String clientName) {
        this.clientNameField.setText(clientName);
    }

    public String getCnpField() {
        return cnpField.getText();
    }

    public void setCnpField(String CNP) {
        this.cnpField.setText(CNP);
    }

    public String getAdressField() {
        return adressField.getText();
    }

    public void setAdressField(String adress) {
        this.adressField.setText(adress);
    }


    public void setDateCreatedField(Date dateofCreation) {
        this.dateCreatedField.setText(dateofCreation.toString());
    }

    public String getBalanceField() {
        return balanceField.getText();
    }

    public void setBalanceField(Long balance) {
        this.balanceField.setText(balance.toString());
    }

    public String getAmountField() {
        return AmountField.getText();
    }

    public void setAmountField(Long Amount) {
        AmountField.setText(Amount.toString());
    }


    public void setBtnTransferListener(ActionListener listener) {
        this.btnTransfer.addActionListener(listener);
    }

    public void setBtnAddBalanceListener(ActionListener listener) {
        this.btnAddBalance.addActionListener(listener);
    }

    public void setCreateAccountListener(ActionListener listener) {
        this.createNewAccountBtn.addActionListener(listener);
    }

    public Long getSelectedIDFromAllAccountsBox() {
        return (Long) allAccountsBox.getSelectedItem();
    }

    public void setAllAccountsBox(List<Long> idKeyset) {
        this.allAccountsBox.removeAllItems();
        for (Long id : idKeyset) {
            this.allAccountsBox.addItem(id);
        }
    }

    public Long getSelectedIDFromAccountsBox() {
        return (Long) accountsBox.getSelectedItem();
    }

    public void setAccountsBox(List<Long> idKeySet) {
        this.accountsBox.removeAllItems();
        for (Long id : idKeySet) {
            this.accountsBox.addItem(id);
        }
    }

    public void setClientsBoxListener(ActionListener listener) {
        this.clientsBox.addActionListener(listener);
    }

    public void setNewClientBtnListner(ActionListener listner) {
        this.newClientBtn.addActionListener(listner);
    }

    public void setUpdateClientBtnListener(ActionListener listener) {
        this.updateClientBtn.addActionListener(listener);
    }

    public Long getSelectedIDFromClientsBox() {
        return (Long) clientsBox.getSelectedItem();
    }

    public void setClientsBox(List<Long> idKeySet) {
        this.clientsBox.removeAllItems();
        for (Long id : idKeySet) {
            this.clientsBox.addItem(id);

        }
    }

    public void setAccountSelectedListener(ActionListener listener) {
        this.accountsBox.addActionListener(listener);
    }

    public void setDeleteAccBtnListener(ActionListener listener) {
        this.deleteAccBtn.addActionListener(listener);
    }

    public void setDeleteClientBtn(ActionListener listener) {
        this.deleteClientBtn.addActionListener(listener);
    }

    /**
     * Create the frame.
     */
    public EmployeeView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 516, 369);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblClient = new JLabel("Client ID");
        lblClient.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
        lblClient.setBounds(21, 10, 67, 36);
        contentPane.add(lblClient);

        clientsBox = new JComboBox();
        clientsBox.setBounds(89, 15, 77, 26);
        contentPane.add(clientsBox);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
        lblName.setBounds(21, 56, 57, 26);
        contentPane.add(lblName);

        JLabel lblPersonal = new JLabel("CNP");
        lblPersonal.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
        lblPersonal.setBounds(21, 92, 67, 26);
        contentPane.add(lblPersonal);

        clientNameField = new JTextField();
        clientNameField.setBounds(69, 61, 125, 21);
        contentPane.add(clientNameField);
        clientNameField.setColumns(10);

        cnpField = new JTextField();
        cnpField.setBounds(69, 97, 125, 21);
        cnpField.setToolTipText("13 digits Personal Code");
        contentPane.add(cnpField);
        cnpField.setColumns(10);

        JLabel lblAdress = new JLabel("Adress");
        lblAdress.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
        lblAdress.setBounds(10, 128, 50, 26);
        contentPane.add(lblAdress);

        adressField = new JTextField();
        adressField.setBounds(69, 128, 125, 21);
        contentPane.add(adressField);
        adressField.setColumns(10);

        updateClientBtn = new JButton("Update");
        updateClientBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
        updateClientBtn.setBounds(54, 175, 112, 21);
        contentPane.add(updateClientBtn);

        newClientBtn = new JButton("Add New Client");
        newClientBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
        newClientBtn.setBounds(38, 220, 146, 21);
        contentPane.add(newClientBtn);

        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setBackground(Color.DARK_GRAY);
        separator.setForeground(Color.DARK_GRAY);
        separator.setBounds(225, 10, 2, 302);
        contentPane.add(separator);

        JLabel lblClientsAccounts = new JLabel("Clients Accounts");
        lblClientsAccounts.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
        lblClientsAccounts.setBounds(247, 22, 112, 21);
        contentPane.add(lblClientsAccounts);

        accountsBox = new JComboBox();
        accountsBox.setBounds(380, 25, 57, 21);
        contentPane.add(accountsBox);

        JLabel lblCreated = new JLabel("Created");
        lblCreated.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
        lblCreated.setBounds(251, 74, 87, 21);
        contentPane.add(lblCreated);

        dateCreatedField = new JTextField();
        dateCreatedField.setBounds(328, 76, 160, 19);
        contentPane.add(dateCreatedField);
        dateCreatedField.setColumns(10);

        JLabel lblBalance = new JLabel("Balance");
        lblBalance.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
        lblBalance.setBounds(247, 117, 67, 21);
        contentPane.add(lblBalance);

        balanceField = new JTextField();
        balanceField.setBounds(328, 119, 77, 21);
        contentPane.add(balanceField);
        balanceField.setColumns(10);

        JLabel lblAmount = new JLabel("Amount");
        lblAmount.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
        lblAmount.setBounds(249, 207, 77, 21);
        contentPane.add(lblAmount);

        AmountField = new JTextField();
        AmountField.setBounds(380, 209, 86, 19);
        contentPane.add(AmountField);
        AmountField.setColumns(10);

        JLabel lblTargetAccountsId = new JLabel("Target Account's ID");
        lblTargetAccountsId.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
        lblTargetAccountsId.setBounds(247, 238, 146, 21);
        contentPane.add(lblTargetAccountsId);

        allAccountsBox = new JComboBox();
        allAccountsBox.setBounds(380, 238, 86, 21);
        contentPane.add(allAccountsBox);

        btnAddBalance = new JButton("Add Balance");
        btnAddBalance.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnAddBalance.setBounds(247, 269, 112, 43);
        contentPane.add(btnAddBalance);

        btnTransfer = new JButton("Transfer");
        btnTransfer.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTransfer.setBounds(370, 269, 100, 43);
        contentPane.add(btnTransfer);

        JSeparator separator_1 = new JSeparator();
        separator_1.setForeground(Color.DARK_GRAY);
        separator_1.setBackground(Color.DARK_GRAY);
        separator_1.setBounds(237, 175, 251, 10);
        contentPane.add(separator_1);

        createNewAccountBtn = new JButton("Create New Account");
        createNewAccountBtn.setBounds(359, 146, 111, 21);
        contentPane.add(createNewAccountBtn);

        deleteClientBtn = new JButton("Delete Client");
        deleteClientBtn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
        deleteClientBtn.setBounds(38, 258, 146, 26);
        contentPane.add(deleteClientBtn);

        deleteAccBtn = new JButton("Delete Acc");
        deleteAccBtn.setBounds(247, 148, 100, 19);
        contentPane.add(deleteAccBtn);

    }
}
