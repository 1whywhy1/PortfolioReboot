package com.thisisivan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DBUtils {

    public static void closeResultSet(ResultSet rs){
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                //Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void closePreparedStatement(PreparedStatement ps){
        if(ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                //Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
               // Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void CleanUp(ResultSet rs, PreparedStatement pstmt, Connection con){
        closeResultSet(rs);
        closePreparedStatement(pstmt);
        closeConnection(con);
    }
}
