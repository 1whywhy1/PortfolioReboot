package com.thisisivan;

//import com.mysql.cj.log.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterPage extends JFrame implements ActionListener{
    //region Variables
    private JPanel mainPanel;
    private JLabel headerLabel;
    private JPanel headerPanel;
    private JTextField usernameText;
    private JTextField firstNameText;
    private JTextField lastNameText;
    private JPasswordField passwordField;
    private JPasswordField passwordReField;

    private JTextField emailText;
    private JButton saveButton;
    private JButton loginButton;
    private JFormattedTextField dobFormatedText;
    private JFormattedTextField createDateText;
    private JComboBox<UserType> userTypeCombo;
    private JPanel registrationPanel;
    private JLabel titleLabel;
    private LoginPage loginPage;
    public static Logger logger;
    //endregion

    //region Set up logger
    static {
        logger = MyLogger.SetupLogger("NewUserLog", System.getProperty("user.dir")
                + System.getProperty("file.separator") + "\\Logs\\NewUserLog.log");
    }
    //endregion

    //region Constructors
    public RegisterPage(){}

    public RegisterPage(LoginPage loginPage) {
        this.loginPage = loginPage;
        loginButton.addActionListener(this);
        saveButton.addActionListener(e->RegisterNewUser());
        userTypeCombo.setModel(new DefaultComboBoxModel<>(UserType.values()));

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Registration Form");
        this.setContentPane(mainPanel);
        //this.setPreferredSize(new Dimension(420,420));
        //this.setUndecorated(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
    //endregion

    // Register a new user and add DB entry
    //region RegisterNewUser
    private void RegisterNewUser() {
        try{
            if( String.valueOf(passwordField.getPassword()).equals( String.valueOf(passwordReField.getPassword())) && Validation.isValidPassword(passwordField.getPassword().toString()))
            {
                User newUser = new User(usernameText.getText(), String.valueOf(passwordField.getPassword()),
                        firstNameText.getText(), lastNameText.getText(), dobFormatedText.getText(),
                        UserType.valueOf(userTypeCombo.getSelectedItem().toString()), emailText.getText(), createDateText.getText());
                MySQLCon.InsertNewUser(newUser);

                // Clear the form fields
                usernameText.setText("");
                passwordField.setText("");
                passwordReField.setText("");
                firstNameText.setText("");
                lastNameText.setText("");
                dobFormatedText.setText("");
                userTypeCombo.setSelectedIndex(0);
                emailText.setText("");

                JOptionPane.showMessageDialog(null, "Congratulations! You have registered", "Registration success", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Passwords do not match", "Password error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error: " + e.getCause(), JOptionPane.ERROR_MESSAGE);
            logger.log(Level.WARNING,e.getMessage(),e);
        }
    }
    //endregion

    //region ActionListener logic
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton)
        {
            loginPage.setVisible(true);
            this.dispose();
        }
    }
    //endregion

    //region Custom Components
    private void createUIComponents() {
        // Set date formatting
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        dobFormatedText = new JFormattedTextField(dateFormat);
        dobFormatedText.setColumns(10);

        createDateText = new JFormattedTextField(dateFormat);
        createDateText.setEditable(false);
        createDateText.setColumns(10);
        createDateText.setValue(new Date());
    }
    //endregion
}
