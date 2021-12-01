package com.thisisivan.searchuser;

import com.thisisivan.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.util.Locale;

public class SearchUserPage extends JFrame{
    //region Variables
    private JPanel mainPane;
    private JTable searchUsersTable;
    private JPanel headerPane;
    private JLabel headerLabel;
    private JComboBox<SearchBy> cmbSearchCriteria;
    private JButton searchButton;
    private JTextField searchValueText;
    private JButton backButton;
    private JButton clearButton;
    private JPanel buttonsPane;
    private JPanel searchPane;
    private JLabel titleLabel;

    private WelcomePage welcomePage;
    //endregion

    //region Constructors
    public SearchUserPage(){}
    public SearchUserPage(WelcomePage welcomePage){
        this.welcomePage = welcomePage;

        cmbSearchCriteria.setModel(new DefaultComboBoxModel<>(SearchBy.values()));
        cmbSearchCriteria.addActionListener(e->ClearText());
        backButton.addActionListener(e->Close());
        searchButton.addActionListener(e->Search());
        clearButton.addActionListener(e->ClearTable());
        //cmbSearchCriteria = new JComboBox(SearchBy.values());
        //this.setForeground(Color.white);

        //region CloseOperation
        // Set Closing Operation and add listener to open a confirmation window.
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure to close User Search?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    Close();
                }
            }
        };
        this.addWindowListener(exitListener);
        //endregion

        this.setTitle("Search Users");
        this.setContentPane(mainPane);
        this.setPreferredSize(new Dimension(800,600));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    //endregion

    //region Search Users from DB by criteria from combobox and display in a table.
    private void Search()
    {
        try
        {
            String[] columnNames = {"ID", "Username", "First Name", "Last Name", "DOB", "UserType", "Email", "Created On"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            searchUsersTable.setModel(tableModel);

            ResultSet rs = MySQLCon.SearchUserRS(searchValueText.getText(), SearchBy.valueOf(cmbSearchCriteria.getSelectedItem().toString()));

            // Reading every line and forming an output line
            if(!rs.next())
            {
                JOptionPane.showMessageDialog(null, "User not found", "InfoBox: Error", JOptionPane.ERROR_MESSAGE);
                throw new NullPointerException("No record found");
            }
            else do {
                String id = rs.getString("id");
                String username = rs.getString("username");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                String DOB = rs.getString("dob");
                String userType = rs.getString("usertype");
                String email = rs.getString("email");
                String created = rs.getString("created");

                // create a single array of one row's worth of data
                String[] data = { id, username, firstName,lastName,DOB,userType,email,created } ;
                tableModel.addRow(data);
            } while (rs.next());

            // Clean up DB interaction
            MySQLCon.CloseQuietly();

            // Place model into JTable
            searchUsersTable.setModel(tableModel);
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

    //region ClearTable
    private void ClearTable()
    {
        searchUsersTable.setModel(new DefaultTableModel());
    }
    //endregion

    //region ClearText
    private void ClearText()
    {
        searchValueText.setText("");
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
