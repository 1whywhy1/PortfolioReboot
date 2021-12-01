package com.thisisivan.stack;

import com.thisisivan.WelcomePage;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class StackForm extends JFrame implements IStackPrinter{
    //region Variables
    private JPanel mainPane;
    private JPanel headerPanel;
    private JLabel headerLabel;
    private JLabel titleLabel;
    private JButton backButton;
    private JTextArea textArea;
    private JButton startButton;
    private WelcomePage welcomePage;
    private String str;
    //endregion

    //region Constructors
    public StackForm(){}

    public StackForm(WelcomePage welcomePage)
    {
        this.welcomePage = welcomePage;

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

        startButton.addActionListener(e->StartDemo());

        this.setTitle("Registration Form");
        this.setContentPane(mainPane);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    //endregion

    //region Logic
    private void StartDemo()
    {
        // Subscribe to event
        StackClass.AddListener(this);

        str = "";
        StackClass.PlayDemo();
    }

    // To print Stack after every step of the way
    @Override
    public void printStack(String st) {
        str += st;
        textArea.setText(str);
    }
    //endregion

    //region Utils
    // Closing operation
    private void Close()
    {
        this.welcomePage.setVisible(true);
        this.dispose();
    }
    //endregion
}
