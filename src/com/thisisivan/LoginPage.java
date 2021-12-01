package com.thisisivan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class LoginPage extends JFrame implements ActionListener {

    //region Variables
    private JPanel mainPanel;
    private JTextField userText;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton resetButton;
    private JLabel passwordLabel;
    private JLabel userLabel;
    private JLabel messageLabel;
    private JButton registerButton;
    private JLabel headerLabel;
    private JPanel headerPanel;
    private JButton closeButton;
    private JLabel titleLabel;
    public static Logger logger;
    //endregion

    //region Set up logger
    static {
        logger = MyLogger.SetupLogger("LoginLog", System.getProperty("user.dir")+ System.getProperty("file.separator") + "\\Logs\\LoginLog.log");
        //System.out.println(System.getProperty("user.dir")+ System.getProperty("file.separator") + "\\LoginLog.log");

    }
    //endregion

    //region Constructor
    LoginPage(String title)
    {
        super(title);

        Font pageFont = new Font (null,Font.ITALIC,20);
        mainPanel.setForeground(Color.red);
        this.setForeground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setPreferredSize(new Dimension(420,420));
        //this.setUndecorated(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        closeButton.addActionListener(this);
        resetButton.addActionListener(this);
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);
    }
    //endregion

    //region ActiveListener
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == resetButton)
        {
            messageLabel.setText("");
            userText.setText("");
            passwordField.setText("");
        }

        // Provide registration form
        else if(e.getSource() == registerButton)
        {
            RegisterPage registerPage = new RegisterPage(this);
            this.setVisible(false);
        }
        else if(e.getSource() == loginButton) {
            // Check provided credentials, write errors to log, open main menu if success
            try {
                Credentials credentials = new Credentials(userText.getText(), String.valueOf(passwordField.getPassword()));

                try {
                    if (credentials.ValidateWithDB()) {
                        WelcomePage welcomePage = new WelcomePage(credentials);
                        this.dispose();
                    } else {
                        messageLabel.setText("Credentials do not match");
                    }
                } catch (Exception ex) {
                    logger.log(Level.INFO, ex.getMessage(), ex);
                }

            } catch (Exception ex) {

                logger.log(Level.INFO, ex.getMessage(), ex);
            }
            finally {
                userText.setText("");
                passwordField.setText("");
            }
        }
        else if(e.getSource() == closeButton)
        {
            System.exit(1);
        }
    }
    //endregion
}
