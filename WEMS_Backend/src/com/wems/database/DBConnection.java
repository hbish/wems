package com.wems.database;

import java.sql.*;

import com.wems.config.*;
import com.wems.exception.*;



public class DBConnection
{
    public DBConnection () {
    }
    
    public static Connection getDBConnection (Config config) throws DBConnectionException {
        String dbURL = new String (config.DB_JDBC_PROTOCOL);
        dbURL = dbURL.concat (config.DB_HOST + "/");
        dbURL = dbURL.concat (config.DB_NAME);
        return getDBConnection (dbURL, config.DB_JDBC_CLASS_NAME, config.DB_USER, config.DB_PASSWORD);
    }
    
    public static Connection getDBConnection (String url, String driver, String user, String pass) throws DBConnectionException {
        Connection conn = null;
        try {
            Class.forName (driver).newInstance ();                // get driver
            conn = DriverManager.getConnection (url, user, pass); // get database connection
            return conn;
        } catch (Exception e) {
            throw new DBConnectionException (e);
        }
    }
}