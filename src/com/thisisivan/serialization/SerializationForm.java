package com.thisisivan.serialization;

import com.thisisivan.User;
import com.thisisivan.UserType;
import com.thisisivan.WelcomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.Serializable;
import java.util.ArrayList;

public class SerializationForm extends JFrame {
    //region Variables
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel userPanel;
    private JTextField firstNameText;
    private JTextField lastNameText;
    private JTextField emailText;
    private JButton backButton;
    private JTextField usernameText;
    private JComboBox<UserType> userTypeCombo;
    private JButton serialiseButton;
    private JButton deserialiseButton;
    private JLabel titleLabel;
    private JTextField passwordText;

    private WelcomePage welcomePage;
    //endregion

    //region Constructor
    public SerializationForm(){}

    public SerializationForm(WelcomePage welcomePage)
    {
        this.welcomePage = welcomePage;
        userTypeCombo.setModel(new DefaultComboBoxModel<>(UserType.values()));

        //region Set ActionListeners
        backButton.addActionListener(e->Close());
        serialiseButton.addActionListener(e->SerializeToFile());
        deserialiseButton.addActionListener(e->DeserializeFromFile());

        //endregion

        //region CloseOperation
        // Set Closing Operation and add listener to open a confirmation window.
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure to close Serialization?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    Close();
                }
            }
        };
        this.addWindowListener(exitListener);
        //endregion

        this.setTitle(titleLabel.getText());
        this.setContentPane(mainPanel);
        this.setPreferredSize(new Dimension(500,400));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    //endregion

    //region Serialize
    private void SerializeToFile() {
        try {
            Serializer serializeClass = new Serializer();

            serializeClass.SerializeThisUser(usernameText.getText(), String.valueOf(passwordText.getText()),
                    firstNameText.getText(), lastNameText.getText(),
                    UserType.valueOf(userTypeCombo.getSelectedItem().toString()), emailText.getText());

            usernameText.setText("");
            passwordText.setText("");
            firstNameText.setText("");
            lastNameText.setText("");
            userTypeCombo.setSelectedIndex(0);
            emailText.setText("");
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Serialization Failed!",  JOptionPane.ERROR_MESSAGE);
        }
    }
    //endregion

    //region Deserialize
    private void DeserializeFromFile()
    {
        try {
            Deserializer serializeClass = new Deserializer();
            User user = serializeClass.DeserializeUser();
            if (user != null){
                usernameText.setText(user.getUserName());
                passwordText.setText(user.getPassword());
                firstNameText.setText(user.getFirstName());
                lastNameText.setText(user.getLastName());
                userTypeCombo.setSelectedItem(user.getUserType());
                emailText.setText(user.getEmail());
            }
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Deserialization Failed!",  JOptionPane.ERROR_MESSAGE);
        }
    }
    //endregion

    //region Utils
    private void Close()
    {
        this.welcomePage.setVisible(true);
        this.dispose();
    }
    //endregion

}
