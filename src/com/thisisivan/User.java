package com.thisisivan;

import com.thisisivan.searchuser.SearchBy;
import com.thisisivan.searchuser.SearchForEdit;

import javax.swing.*;
import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.zip.DataFormatException;

public class User extends Credentials{

    //region Properties
    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) throws DataFormatException {
        if(firstName.isBlank()) {
            throw new DataFormatException("First Name cannot be blank");
        }
        else if(Validation.isValidName(firstName)) {
            this.firstName = firstName;
        }
        else {
            throw new DataFormatException("Invalid format - Name should be: 2 - 30 characters long. Contain only letters.");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws DataFormatException {
        if(lastName.isBlank()) {
            throw new DataFormatException("Last Name cannot be blank");
        }
        else if(Validation.isValidName(firstName))
            this.lastName = lastName;
        else
        {
            throw new DataFormatException("Invalid format - Name should be: 2 - 30 characters long. Contain only letters.");
        }
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) throws DataFormatException {
        if(DOB.isBlank()) {
            throw new DataFormatException("Date of Birth cannot be blank");
        }
        else
        {
            this.DOB = DOB;
        }
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws DataFormatException {

            if(email.isBlank()) {
                throw new DataFormatException("Email cannot be blank");
            }
            else if (Validation.isValidEmail(email))
                this.email = email;
            else
                throw new DataFormatException("This does not look like an email");
    }

    public String getCreatedOn() {
        return createdOn;
    }

    //might need to pass String in
    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws DataFormatException {
        this.id = id;
    }

    //endregion

    //region Variables
    private int id;
    private String firstName;
    private String lastName;
    private String DOB;
    private UserType userType;
    private String email;
    private String createdOn;

    //endregion

    //region Constructors
    public User() {}

    public User(String username, String password, String firstName, String lastName, String DOB, UserType userType, String email) throws Exception {
        try {
            setUserName(username);
            setPassword(password);
            setFirstName(firstName);
            setLastName(lastName);
            setDOB(DOB);
            setUserType(userType);
            setEmail(email);
            //createdOn()
        }
        catch (Exception e)
        {
            throw e;
        }

    }

    public User(String username, String password, String firstName, String lastName, UserType userType, String email) throws Exception {

        try {
            setUserName(username);
            setPassword(password);
            setFirstName(firstName);
            setLastName(lastName);
            setDOB(DOB);
            setUserType(userType);
            setEmail(email);
            //createdOn()
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public User( int id, String username, String password, String firstName, String lastName, String DOB, UserType userType, String email) throws Exception {

        try {
            setId(id);
            setUserName(username);
            setPassword(password);
            setFirstName(firstName);
            setLastName(lastName);
            setDOB(DOB);
            setUserType(userType);
            setEmail(email);
            //createdOn()
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public User(int id ,String username, String firstName, String lastName, String DOB, UserType userType, String email) throws Exception {
        try {
            setId(id);
            setUserName(username);
            setFirstName(firstName);
            setLastName(lastName);
            setDOB(DOB);
            setUserType(userType);
            setEmail(email);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public User(String username, String password, String firstName, String lastName, String DOB, UserType userType, String email, String createdOn) throws Exception {

        try {
            setUserName(username);
            setPassword(password);
            setFirstName(firstName);
            setLastName(lastName);
            setDOB(DOB);
            setUserType(userType);
            setEmail(email);
            setCreatedOn(createdOn);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public User(String id,String username, String password, String firstName, String lastName, String DOB, UserType userType, String email, String createdOn) throws Exception {
        try {
            setId(Integer.parseInt(id));
            setUserName(username);
            setPassword(password);
            setFirstName(firstName);
            setLastName(lastName);
            setDOB(DOB);
            setUserType(userType);
            setEmail(email);
            setCreatedOn(createdOn);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    //endregion

    //region DeleteUserFromDB
    public void Delete(SearchForEdit searchBy, String value)
    {
        MySQLCon.DeleteUser(this, searchBy, value);
    }
    //endregion

    //region UpdateUserToDB
    public void Update() { MySQLCon.UpdateUser(this, SearchBy.id, String.valueOf(this.id));}
    //endregion
}
