package com.thisisivan;

import com.thisisivan.searchuser.SearchBy;

import javax.swing.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserPage extends JFrame implements ActionListener{

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
    private JButton registerButton;
    private JButton backButton;
    private JFormattedTextField dobFormatedText;
    private JFormattedTextField createDateText;
    private JComboBox<UserType> userTypeCombo;
    private JPanel registrationPanel;
    private JLabel titleLabel;
    private JButton editButton;
    private JButton saveButton;
    private ArrayList<JComponent> fields;
    private WelcomePage welcomePage;
    private User user;
    //endregion

    //region Constructors
    public UserPage(){}

    public UserPage(WelcomePage welcomePage) {

        this.welcomePage = welcomePage;

        fields = new ArrayList<JComponent>();
        fields.add(usernameText);
        fields.add(firstNameText);
        fields.add(lastNameText);
        fields.add(dobFormatedText);
        fields.add(userTypeCombo);
        fields.add(emailText);
        fields.add(passwordField);
        fields.add(passwordReField);

        System.out.println("fields is " + fields.size());

        registerButton.setVisible(true);
        saveButton.setVisible(false);
        editButton.setVisible(false);

        // Add action listeners
        backButton.addActionListener(this);
        registerButton.addActionListener(e->RegisterNewUser());

        userTypeCombo.setModel(new DefaultComboBoxModel<>(UserType.values()));

        //region CloseOperation
        // Set Closing Operation and add listener to open a confirmation window.
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure to close this window?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    Close();
                }
            }
        };
        this.addWindowListener(exitListener);
        //endregion

        this.setTitle("Registration Form");
        this.setContentPane(mainPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    public UserPage(WelcomePage welcomePage, String username)
    {
        this(welcomePage);
        System.out.println("works");
        System.out.println("fields 2 is " + fields.size());
        EnableFields(fields,true);
        System.out.println("works2");
        this.setTitle("Profile");
        registerButton.setVisible(false);
        saveButton.setVisible(false);
        editButton.setVisible(true);

        FetchProfile(username);

    }

    //endregion

    //region Register a new user and add DB entry
    private void RegisterNewUser() {
        try{

            if( String.valueOf(passwordField.getPassword()).equals( String.valueOf(passwordReField.getPassword())))
            {

                User user = new User(usernameText.getText(), String.valueOf(passwordField.getPassword()),
                        firstNameText.getText(), lastNameText.getText(), dobFormatedText.getText(),
                        UserType.valueOf(userTypeCombo.getSelectedItem().toString()), emailText.getText(), createDateText.getText());
                MySQLCon.InsertNewUser(user);


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
        }
    }
    //endregion

    //region Close Listener
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == backButton)
        {
            welcomePage.setVisible(true);
            this.dispose();
        }
    }
    //endregion

    private void FetchProfile(String username){
        try
        {
            ResultSet rs = MySQLCon.SearchUserRS(username, SearchBy.username);

            // Reading every line and forming an output line
            if(!rs.next())
            {   System.out.println( "rs string " + rs.getString(0));
                //JOptionPane.showMessageDialog(null, "User not found", "InfoBox: Error", JOptionPane.ERROR_MESSAGE);
                throw new NullPointerException("No record found");
            }
            else
            {
                // Set JTextComponents editable

                UserType ut = UserType.User;
                if (rs.getInt("usertype") == 1)
                {
                    ut = UserType.Moderator;
                } else if (rs.getInt("usertype") == 2)
                {
                    ut = UserType.Admin;
                }

                user = new User(rs.getString("id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("firstname"),
                        rs.getString("lastname"), rs.getString("dob"),
                        ut, rs.getString("email"),
                        rs.getString("created"));


                usernameText.setText(user.getUserName());
                firstNameText.setText(user.getFirstName());
                lastNameText.setText(user.getLastName());
                dobFormatedText.setText(user.getDOB());
                userTypeCombo.setSelectedItem(user.getUserType());
                emailText.setText(rs.getString("email"));
                createDateText.setText(rs.getString("created"));

                EnableFields(fields, true);
            }

            // Clean up DB interaction
            MySQLCon.CloseQuietly();
        }
        catch (Exception e)
        {e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "Profile is not accessible", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Closing operation
    private void Close()
    {
        this.welcomePage.setVisible(true);
        this.dispose();
    }

    // Sets ability to edit fields
    private void EnableFields(ArrayList<JComponent> texts, boolean status)
    {
        texts.forEach(jComponent -> {jComponent.setEnabled(status);});
    }

    private void createUIComponents() {
        // Set date formatting
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        dobFormatedText = new JFormattedTextField(dateFormat);
        dobFormatedText.setColumns(10);

        createDateText = new JFormattedTextField(dateFormat);
        createDateText.setEditable(false);
        createDateText.setColumns(10);
        createDateText.setValue(new Date());
        System.out.println("value " + createDateText.getText());
    }
}
