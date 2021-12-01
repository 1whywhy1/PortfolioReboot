package com.thisisivan;

import com.thisisivan.texteditor.searchuser.SearchBy;
import com.thisisivan.texteditor.searchuser.SearchForEdit;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLCon {

    //region Variables
    private static ResultSet rs;
    private static Connection con;
    private static PreparedStatement pstmt;
    private static Logger logger;
    public static String serverName;
    public static String dbUsername;
    public static String dbPassword;
    public static String connectionString;
    private static boolean  newCred = false;


    static {
       try {
           //JOptionPane.showOptionDialog()
           if (newCred){

               connectionString = "jdbc:mysql://localhost:3306/advanced_java_ivan_k";
               dbUsername = "root";
               dbPassword = "Russianvodka5";
           } else {
               serverName = JOptionPane.showInputDialog(null, "Name of your MySQL Server? *case sensitive*", "MySQL Name Info", JOptionPane.QUESTION_MESSAGE);
               dbUsername = JOptionPane.showInputDialog(null, "Username  for MySQL Server? *case sensitive*", "MySQL Username Info", JOptionPane.QUESTION_MESSAGE);
               dbPassword = JOptionPane.showInputDialog(null, "Password  for MySQL Server? *case sensitive*", "MySQL Password Info", JOptionPane.QUESTION_MESSAGE);
               connectionString = "jdbc:mysql://localhost:3306/" + serverName;
           }

       } catch (Exception e)
       {
           System.exit(1);
       }
    }
    //endregion

    //region Set up logger
    static{
        logger = MyLogger.SetupLogger("MySqlLog", System.getProperty("user.dir")
                + System.getProperty("file.separator") + "\\Logs\\MySqlLog.log");


    }
    //endregion

    //region LoginViaMySQL
    public static boolean Login(String username, String password){
        con = null;
        pstmt = null;
        rs = null;

        try {
            // The following is depreciated, but we spoke of this in class
            // Class.forName("com.mysql.jdbc.Driver");
            con = GetConnection();
            if(con!= null) {
                pstmt = con.prepareStatement("select username, password from user where username =? and password =?");
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                rs = pstmt.executeQuery();

                if (rs.next())
                {
                    return rs.getString(1).equals(username) && rs.getString(2).equals(password);
                }
                else
                    return false;
            } else
                throw new NullPointerException("Connection with database not established");
        }
        catch (SQLException | NullPointerException e){
            //put logger here
            e.printStackTrace();
            return false;
        } finally {
            // Clean up all objects
            DBUtils.CleanUp(rs, pstmt,con);
        }
    }
    //endregion

    //region GetConnection
    private static Connection GetConnection() {
        try {
            return DriverManager.getConnection(connectionString, dbUsername, dbPassword);
        }
        catch (SQLException e)
        {
            logger.log(Level.WARNING, e.getMessage(),e);
            //e.printStackTrace();
            return null;
        }
    }

    //endregion

    //region SearchUser
    public static ArrayList<User> SearchUser(String value, SearchBy searchBy) throws Exception {
        ArrayList<User> users = new ArrayList<User>();

        con = null;
        pstmt = null;
        rs = null;

        try {
            // The following is depreciated, but we spoke of this in class
            // Class.forName("com.mysql.jdbc.Driver");
            con = GetConnection();
            if(con!= null) {

                pstmt = con.prepareStatement("select id, username, firstname, lastname, dob, userType, email, created from user where ? =?");
                pstmt.setString(1, searchBy.toString());
                pstmt.setString(2, value);
                //.setString(2, password);
                rs = pstmt.executeQuery();
                if (rs.first() && rs.next())
                {
                    while(rs.next()) {
                        try {
                            User user = new User(rs.getInt(1), rs.getString(2), "", rs.getString(4), rs.getString(5), rs.getString(6), UserType.valueOf(rs.getString(7)), rs.getString(8));
                            users.add(user);
                        }
                        catch (Exception e)
                        {
                            throw new Exception(e.getMessage());
                        }
                    }
                }
                return users;
            } else
                throw new NullPointerException("Connection with database not established");
        }
        catch (SQLException | NullPointerException e){

            logger.log(Level.WARNING, e.getMessage(),e);
            return null;
        } finally {
            // Clean up all objects
            DBUtils.CleanUp(rs, pstmt,con);
        }
    }

    //endregion

    //region SearchUserRS
    public static ResultSet SearchUserRS(String value, SearchBy searchBy) throws SQLException, ClassNotFoundException {
        ArrayList<User> users = new ArrayList<User>();

        con = null;
        pstmt = null;
        rs = null;

        try {
            con = GetConnection();
            if(con!= null) {
                String query;
                if(searchBy == SearchBy.all)
                {
                    query = "select *" +
                            "from user";
                }
                else
                {
                    String column = searchBy.name();
                    query = "select id,"
                            + " username,"
                            + " firstname,"
                            + " lastname,"
                            + " dob,"
                            + " userType,"
                            + " email,"
                            + " created from user where "
                            + column + " = ?";
                }

                pstmt = con.prepareStatement(query);

                if(searchBy != searchBy.all) {
                    pstmt.setString(1, searchBy.toString());
                    pstmt.setString(1, value);
                }

                System.out.println(pstmt);
                rs = pstmt.executeQuery();

                return rs;
            } else
                throw new NullPointerException("Connection with database not established");
        }
        catch (SQLException | NullPointerException e){
            logger.log(Level.WARNING, e.getMessage(),e);
            return null;
        } finally {
            // Clean up all objects
            //DBUtils.closePreparedStatement(pstmt);
            //DBUtils.closeConnection(con);
            //DBUtils.CleanUp(rs, pstmt,con);
            //CloseQuietly();
        }
    }
    //endregion

    //region Delete User
    public static void DeleteUser(User user, SearchForEdit searchBy, String value)
    {
        con = null;
        pstmt = null;
        rs = null;
        String query = "";

        try {
            // The following is depreciated, but we spoke of this in class
            // Class.forName("com.mysql.jdbc.Driver");

            // Fetch connection
            con = GetConnection();
            if(con!= null) {

                // Set PreparedStatement
                String str = searchBy.toString();
              //  String query = "";
                if (searchBy.equals(SearchForEdit.id)) {
                    query = "DELETE FROM user WHERE id = ?";
                } else if (searchBy.equals(SearchForEdit.email)) {
                    query = "DELETE FROM user WHERE email = ?";
                }

                pstmt = con.prepareStatement(query);
                pstmt.setString(1, value);

                pstmt.executeUpdate();

            }
            else {
                throw new NullPointerException("Connection with database not established");
            }
        }
        catch (SQLException | NullPointerException e){
            logger.log(Level.WARNING, e.getMessage(),e);
        }
    }

    //endregion

    //region InsertNewUser
    public static void InsertNewUser(User user) {
        con = null;
        pstmt = null;
        rs = null;

        try {
            // The following is depreciated, but we spoke of this in class
            // Class.forName("com.mysql.jdbc.Driver");

            // Fetch connection
            con = GetConnection();
            if(con!= null) {

                // Set PreparedStatement
                String query = "INSERT INTO user ("
                        + " id,"
                        + " username,"
                        + " password,"
                        + " firstname,"
                        + " lastname,"
                        + " dob,"
                        + " usertype,"
                        + " email,"
                        + " created ) VALUES ("
                        + "null, ?, ?, ?, ?, ?, ?, ?, ?)";

                pstmt = con.prepareStatement(query);

                // Set the parameters
                pstmt.setString(1, user.getUserName());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getFirstName());
                pstmt.setString(4, user.getLastName());
                pstmt.setString(5, user.getDOB());
                pstmt.setString(6, String.valueOf(user.getUserType().getValue()));
                pstmt.setString(7, user.getEmail());
                pstmt.setString(8,user.getCreatedOn());

                System.out.println(pstmt);

                pstmt.executeUpdate();

            }
            else {
                logger.info("Connection with database not established");
                throw new NullPointerException("Connection with database not established");
            }
        }
        catch(SQLIntegrityConstraintViolationException exc)
        {
            //JOptionPane.showMessageDialog(null, "User with this information already exists", "Registration failed", JOptionPane.ERROR_MESSAGE);
            IllegalArgumentException iex = new IllegalArgumentException("Registration failed - User with this information already exists");
            logger.log(Level.WARNING, iex.getMessage(),iex);
            RegisterPage.logger.log(Level.WARNING, iex.getMessage(),iex);
            throw iex;
        }
        catch (SQLException | NullPointerException e){
            logger.log(Level.WARNING, e.getMessage(),e);
        }
    }
    //endregion

    //region UpdateUser
    public static void UpdateUser(User user, SearchBy searchBy, String value) {
        con = null;
        pstmt = null;
        rs = null;

        try {
            // The following is depreciated, but we spoke of this in class
            // Class.forName("com.mysql.jdbc.Driver");

            // Fetch connection
            con = GetConnection();
            if(con!= null) {

                // Set PreparedStatement
                String query = "UPDATE user "
                        + "SET"
                        //+ " id = null,"
                        + " username = ?,"
                        + " firstname = ?,"
                        + " lastname = ?,"
                        + " dob = ?,"
                        + " usertype = ?,"
                        + " email = ?"
               //         + " created = null"
                        + " WHERE " + searchBy.name() + " = ?;";

                pstmt = con.prepareStatement(query);

                // Set the parameters
                pstmt.setString(1, user.getUserName());
                pstmt.setString(2, user.getFirstName());
                pstmt.setString(3, user.getLastName());
                pstmt.setString(4, user.getDOB());
                pstmt.setString(5, String.valueOf(user.getUserType().getValue()));
                pstmt.setString(6, user.getEmail());
                pstmt.setString(7, value);

                System.out.println("Update statement - " + pstmt);
                int i = pstmt.executeUpdate();
                System.out.println(i);
            }
            else {
                logger.info("Connection with database not established");
                throw new NullPointerException("Connection with database not established");
            }
        }
        catch(SQLIntegrityConstraintViolationException exc)
        {
            LoginPage.logger.info("Registration failed - User with this information already exists");
            JOptionPane.showMessageDialog(null, "User with this information already exists", "Registration failed", JOptionPane.ERROR_MESSAGE);
        }
        catch (SQLException | NullPointerException e){
            logger.log(Level.WARNING, e.getMessage(),e);
        }
    }
    //endregion


    //region Utility
    public static void CloseQuietly() throws SQLException, ClassNotFoundException {
        if(rs != null && pstmt !=null && con != null)
            DBUtils.CleanUp(rs, pstmt,con);
    }

    //endregion

}
