package com.thisisivan;

import com.thisisivan.fileprocessingandpolimorphism.*;
import com.thisisivan.flagdisplay.FlagsGuiReboot;
import com.thisisivan.texteditor.searchuser.EditDeleteUserForm;
import com.thisisivan.texteditor.searchuser.SearchUserPage;
import com.thisisivan.serialization.SerializationForm;
import com.thisisivan.stack.StackForm;
import com.thisisivan.texteditor.EditorJFrame;

import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JFrame{
    //region Variables
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JLabel welcomeLabel;
    private JButton logoutButton;
    private JButton textFileButton;
    private JButton serializationAndDeserializationButton;
    private JButton textEditorButton;
    private JButton searchUserButton;
    private JButton editDeleteButton;
    private JButton newUserButton;
    private JButton imageFileProcessingButton;
    private JButton workingWithDataStructureButton;
    private JButton editProfileButton;
    private Credentials userCred;
    //endregion

    //region Constructor

    public WelcomePage(){}

    public WelcomePage(Credentials userCred){

        this.userCred = userCred;
        welcomeLabel.setText("Welcome, " + userCred.getUserName() + "!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setPreferredSize(new Dimension(500,600));
        logoutButton.addActionListener(e->Logout());
        textFileButton.addActionListener(e->OpenTextFile());
        textEditorButton.addActionListener(e->OpenTextEditor());
        searchUserButton.addActionListener(e->OpenSearchUser());
        editDeleteButton.addActionListener(e->OpenEditDeleteUser());
        imageFileProcessingButton.addActionListener(e->OpenFlagsOfTheWorld());
        serializationAndDeserializationButton.addActionListener(e-> { this.setVisible(false); new SerializationForm(this); });
        newUserButton.addActionListener(e-> { this.setVisible(false); new UserPage(this); });
        editProfileButton.addActionListener(e-> { this.setVisible(false); new UserPage(this, userCred.getUserName()); });
        workingWithDataStructureButton.addActionListener(e-> { this.setVisible(false); new StackForm(this); });

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
    //endregion

    //region Logout
    private void Logout() {
        LoginPage loginPage = new LoginPage("Portfolio");
        this.dispose();
    }
    //endregion

    //region Open SearchUser
    private void OpenSearchUser()
    {
        SearchUserPage searchUserPage = new SearchUserPage(this);
        this.setVisible(false);

    }
    //endregion

    //region Open EditDeleteUser
    private void OpenEditDeleteUser()
    {
        EditDeleteUserForm editDeleteUserForm = new EditDeleteUserForm(this, userCred.getUserName());
        this.setVisible(false);
    }
    //endregion

    //region Open TextFile
    private void OpenTextFile(){
        ReadWriteTextFileForm textFile = new ReadWriteTextFileForm(this);
        this.setVisible(false);
    }
    //endregion

    //region Open TextFile Editor
    private void OpenTextEditor(){
        EditorJFrame textEditor = new EditorJFrame(this);
        textEditor.setLocationRelativeTo(null);
        textEditor.setVisible(true);
        this.setVisible(false);
    }
    //endregion

    //region Open Flags Image Processing
    private void OpenFlagsOfTheWorld()
    {
        FlagsGuiReboot flagsGuiReboot = new FlagsGuiReboot(this);
        this.setVisible(false);
    }
    //endregion
}
