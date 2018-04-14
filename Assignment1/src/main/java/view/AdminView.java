package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class AdminView extends JFrame {

    private JPanel contentPane;
    private JComboBox employeesBox;
    private JTextField password;
    private JTextField name;
    private JComboBox dateBox;
    private JTextArea reportArea;
    private JButton updateBtn;
    private JButton createBtn;
    /**
     * Launch the application.
     */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					employeeView frame = new employeeView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/

    /**
     * Create the frame.
     */
    public AdminView() {
        setTitle("ADMIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 457, 390);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        JLabel lblEmployees = new JLabel("EmployeesID");
        lblEmployees.setFont(new Font("Arial", Font.PLAIN, 15));
        lblEmployees.setBounds(24, 10, 97, 27);
        contentPane.add(lblEmployees);

        password = new JTextField();
        password.setToolTipText("Must contain min 8 characters, 1 special char, 1 numeric char.");
        password.setBounds(268, 59, 125, 19);
        contentPane.add(password);

        name = new JTextField();
        name.setBounds(62, 59, 125, 19);
        contentPane.add(name);
        name.setColumns(10);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblName.setBounds(10, 58, 59, 16);
        contentPane.add(lblName);

        JLabel lblPassword = new JLabel("password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblPassword.setBounds(198, 57, 91, 19);
        contentPane.add(lblPassword);

        employeesBox = new JComboBox();
        employeesBox.setBounds(115, 14, 142, 23);
        contentPane.add(employeesBox);

        JLabel lblNewLabel = new JLabel("Select Date");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(24, 146, 91, 35);
        contentPane.add(lblNewLabel);

        dateBox = new JComboBox();
        dateBox.setBounds(139, 148, 150, 27);
        contentPane.add(dateBox);

        reportArea = new JTextArea();
        reportArea.setBounds(24, 202, 384, 120);
        reportArea.setText("Not yet implemented :(");
        contentPane.add(reportArea);

        updateBtn = new JButton("Update");
        updateBtn.setBounds(115, 99, 88, 27);
        contentPane.add(updateBtn);

        createBtn = new JButton("Create");
        createBtn.setBounds(215, 99, 91, 27);
        contentPane.add(createBtn);
    }

    public Long getSelectedEmployeeId() {
        return (Long) employeesBox.getSelectedItem();
    }

    public void setEmployeesBox(List<Long> idKeyset) {
        this.employeesBox.removeAllItems();
        for (Long id : idKeyset) {
            this.employeesBox.addItem(id);
        }
    }

    public String getPassword() {
        return password.getText();
    }

    public void setPassword(String password) {
        this.password.setText(password);
    }

    public String getEmployeeName() {
        return name.getText();
    }

    public void setEmployeeName(String name) {
        this.name.setText(name);
    }

    public JComboBox getDateBox() {
        return dateBox;
    }

    public void setDateBox(List<Date> employeeActionDates) {
        this.dateBox.removeAllItems();
        for (Date date : employeeActionDates) {
            this.dateBox.addItem(date);
        }
    }

    public JTextArea getReportArea() {
        return reportArea;
    }

    public void setReportArea(String report) {
        this.reportArea.setText(report);
    }

    public void setCreateBtnACtionListener(ActionListener listener) {
        this.createBtn.addActionListener(listener);
    }

    public void setUpdateBtnActionListener(ActionListener listener) {
        this.updateBtn.addActionListener(listener);
    }

    public void setEmplyeeSelectedBtnListener(ActionListener listener) {
        this.employeesBox.addActionListener(listener);
    }

    public void setDateSelectedACtionListener(ActionListener listener) {
        this.dateBox.addActionListener(listener);

    }

}
