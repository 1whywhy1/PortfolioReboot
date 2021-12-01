package com.thisisivan;

import javax.swing.*;
import java.io.Serializable;
import java.util.zip.DataFormatException;

public class Credentials implements Serializable {
    //region Variables
    private String userName;
    private transient String password;
    //endregion

    //region Constructors
    public Credentials()
    {

    }

    public Credentials(String uName, String pass) throws DataFormatException {
        setUserName(uName);
        setPassword(pass);
    }
    //endregion

    //region Properties
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {

        if(Validation.isValidName(userName))
            this.userName = userName;
        else
        {
            JOptionPane.showMessageDialog(null,
                    "Username should be:\n" +
                            "4 - 30 characters long.\n" +
                            "Contain only letters", "Invalid format - Name", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws DataFormatException {
        if(Validation.isValidName(password))
            this.password = password;
        else
        {
            JOptionPane.showMessageDialog(null,
                    "Password should be:\n" +
                            "3 - 30 characters long.\n" +
                            "Not contain forbidden characters", "Invalid format - Password", JOptionPane.ERROR_MESSAGE);
              throw new DataFormatException("Wrong password format");
        }

        this.password = password;
    }
    //endregion

    //region ValidateWithDB
    public boolean ValidateWithDB(){
        return(MySQLCon.Login(userName,password));
    }
    //endregion
}
