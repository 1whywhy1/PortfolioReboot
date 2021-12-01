package com.thisisivan.texteditor.searchuser;

import com.thisisivan.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.AccessException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;

public class EditDeleteUserForm extends JFrame {
    //region Variables
    private JPanel mainPane;
    private JComboBox<SearchForEdit> cmbSearchCriteria;
    private JTextField searchValueText;
    private JPanel userPanel;
    private JTextField firstNameText;
    private JTextField lastNameText;
    private JTextField emailText;
    private JButton saveButton;
    private JButton backButton;
    private JTextField usernameText;
    private JFormattedTextField dobFormatedText;
    private JFormattedTextField createDateText;
    private JComboBox<UserType> userTypeCombo;
    private JButton searchButton;
    private JButton deleteButton;
    private JButton editButton;
    private JTextField userIDText;
    private JPanel headerPanel;
    private JLabel headerLabel;
    private WelcomePage welcomePage;
    private ArrayList<JTextField> fields;
    User user;
    private String currentUsername;
    //endregion

    //region Constructors
    public EditDeleteUserForm(){}

    public EditDeleteUserForm(WelcomePage welcomePage, String username)
    {
        currentUsername = username;
        JComponent comp = new JComboBox();

        fields = new ArrayList<JTextField>();
        fields.add(usernameText);
        fields.add(firstNameText);
        fields.add(lastNameText);
        fields.add(dobFormatedText);
        fields.add(emailText);
        EnableFields(fields,false);


        this.welcomePage = welcomePage;

        //region Set ActionListeners
        cmbSearchCriteria.addActionListener(e->ClearText());
        backButton.addActionListener(e->Close());
        searchButton.addActionListener(e->Search());
        deleteButton.addActionListener(e->DeleteUser());
        editButton.addActionListener(e->EditButton());
        saveButton.addActionListener(e->SaveButton());

        cmbSearchCriteria.setModel(new DefaultComboBoxModel<>(SearchForEdit.values()));
        userTypeCombo.setModel(new DefaultComboBoxModel<>(UserType.values()));

        //endregion

        //region CloseOperation
        // Set Closing Operation and add listener to open a confirmation window.
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure to close Edit/Delete User?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    Close();
                }
            }
        };
        this.addWindowListener(exitListener);
        //endregion

        ShowButtons(false);

        this.setTitle("Search Users");
        this.setContentPane(mainPane);
        this.setPreferredSize(new Dimension(500,600));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    //endregion

    //region Search a user by ID or Email and display if found
    private void Search()
    {
        ShowButtons(false);

        try
        {
            ResultSet rs = MySQLCon.SearchUserRS(searchValueText.getText(), SearchBy.valueOf(cmbSearchCriteria.getSelectedItem().toString()));

            // Reading every line and forming an output line
            if(!rs.next())
            {
                JOptionPane.showMessageDialog(null, "User not found", "InfoBox: Error", JOptionPane.ERROR_MESSAGE);
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

                user = new User(rs.getString("id"), rs.getString("username"), rs.getString("firstname"),
                        rs.getString("lastname"), rs.getString("lastname"), rs.getString("dob"),
                        ut, rs.getString("email"),
                        rs.getString("created"));

                userIDText.setText(rs.getString("id"));
                usernameText.setText(rs.getString("username"));
                firstNameText.setText(rs.getString("firstname"));
                lastNameText.setText(rs.getString("lastname"));
                dobFormatedText.setText(rs.getString("dob"));
                userTypeCombo.setSelectedIndex(rs.getInt("usertype"));
                emailText.setText(rs.getString("email"));
                createDateText.setText(rs.getString("created"));

                editButton.setVisible(true);
            }

            // Clean up DB interaction
            MySQLCon.CloseQuietly();

        }
        catch (NullPointerException ex)
        {

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    //endregion

    //region Delete User
    private void DeleteUser()
    {
        String pass = JOptionPane.showInputDialog(null, "Confirm your password to proceed", "Authorisation", JOptionPane.QUESTION_MESSAGE);
        if (!pass.isBlank()) {
            try{
            Credentials delCred = new Credentials(currentUsername,pass);
            if (delCred.ValidateWithDB())
                {
                if(cmbSearchCriteria.getSelectedItem().toString() == SearchForEdit.id.name())
                {
                    user.Delete(SearchForEdit.valueOf(cmbSearchCriteria.getSelectedItem().toString()), userIDText.getText());
                }
                else if(cmbSearchCriteria.getSelectedItem().toString() == SearchForEdit.email.name())
                {
                    user.Delete(SearchForEdit.valueOf(cmbSearchCriteria.getSelectedItem().toString()), emailText.getText());
                }
                JOptionPane.showMessageDialog(null, "User Deleted", "Operation successful",JOptionPane.ERROR_MESSAGE );
            }
            } catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null, "Access denied", "Operation unsuccessful",JOptionPane.ERROR_MESSAGE );

                AccessException aex = new AccessException("Access denied - Failed access attempt");
                RegisterPage.logger.log(Level.WARNING,aex.getMessage(), aex);
            }
        }
    }
    //endregion

    //region Utilities
    private void ClearText()
    {
        searchValueText.setText("");
    }

    private void EnableFields(ArrayList<JTextField> texts, boolean status)
    {
        texts.forEach(jTextField -> {jTextField.setEditable(status);});
        userTypeCombo.setEnabled(status);
    }

    private void EditButton()
    {
        EnableFields(fields,true);
        saveButton.setVisible(true);
        deleteButton.setVisible(true);
        editButton.setVisible(false);
    }

    private void SaveButton()
    {
        editButton.setVisible(true);
        deleteButton.setVisible(false);
        saveButton.setVisible(false);

        try {
            user = new User(Integer.parseInt(userIDText.getText()), usernameText.getText(), firstNameText.getText(), lastNameText.getText(), dobFormatedText.getText(), UserType.valueOf(userTypeCombo.getSelectedItem().toString()), emailText.getText());
            user.Update();
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error: " + e.getCause(),  JOptionPane.ERROR_MESSAGE);
        }
        EnableFields(fields,false);
    }

    private void ShowButtons(boolean status)
    {
        editButton.setVisible(status);
        saveButton.setVisible(status);
        deleteButton.setVisible(status);

    }

    private void Close()
    {
        this.welcomePage.setVisible(true);
        this.dispose();
    }

    //endregion

    private void createUIComponents() {
        // Set date formatting
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        dobFormatedText = new JFormattedTextField(dateFormat);
        dobFormatedText.setColumns(10);

        createDateText = new JFormattedTextField(dateFormat);
        createDateText.setEditable(false);
        createDateText.setColumns(10);
    }

}
