package com.wems.database;

import java.util.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import com.wems.config.*;
import com.wems.exception.DBConnectionException;
import com.wems.model.*;




public class DataAccess {
    private Connection _conn = null;      //data members
    private static DataAccess _da = null; //data members
    
    public static synchronized DataAccess create (Config config) {
        if(_da == null) {                 // Singleton Pattern
            _da = new DataAccess(config);
            return _da;
        }
        return (_da);
    }
    
    public DataAccess (Config config)
    {
        try {
            _conn = DBConnection.getDBConnection(config);
        } catch (DBConnectionException ex) {
            ex.printStackTrace ();
        }
    }
    
    private Connection getDBConnection (Config config) throws DBConnectionException
    {
        return DBConnection.getDBConnection (config);
    }
    
    public boolean isUserAccountExist(Config config, User user) throws DBConnectionException, SQLException
    {
        boolean isExist = false;
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        
        try {
            String passHash = MD5.getMD5HashVal(user.password());
            conn = getDBConnection(config);
            String query = new String (
                    "SELECT * FROM User u" +
                    " WHERE CONVERT(u.username USING latin1) COLLATE latin1_general_cs = '" + user.username() + "'" +
                    " AND CONVERT(u.passHash USING latin1) COLLATE latin1_general_cs = '" + passHash + "'" +
                    " AND (userTypeId = '" + config.SYS_USER_ACCESS_0 + "' OR userTypeId = '" + config.SYS_USER_ACCESS_1 + "')" +
                    " AND lockedOut = '0'");
            pStatement = conn.prepareStatement(query);
            rs = pStatement.executeQuery();
            
            if (rs.next ())
            {
                isExist = true;
                query = new String("SELECT userId FROM user WHERE username = '" + user.username() + "'");
                pStatement = conn.prepareStatement (query);
                rs = pStatement.executeQuery ();
                if(rs.next ())
                {
                    int userId = rs.getInt("userId");
                    query = new String("UPDATE user SET loginAttempts = '0' WHERE userId = '" + userId + "'");
                    pStatement = conn.prepareStatement (query);
                    pStatement.executeUpdate ();
                }
            }
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
        return isExist;
    }
    
    public int currentLoginAttempts(Config config, User user) throws DBConnectionException, SQLException
    {
        int loginAttempts = 0;
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        
        try {
            conn = getDBConnection(config);
            String query = new String (
                    "SELECT loginAttempts FROM User u" +
                    " WHERE CONVERT(u.username USING latin1) COLLATE latin1_general_cs = '" + user.username() + "'");
            pStatement = conn.prepareStatement(query);
            rs = pStatement.executeQuery();
            
            if (rs.next ()) {
                loginAttempts = rs.getInt("loginAttempts");
            }
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
        return loginAttempts;
    }
    
    public void setFailedLoginAttempts(Config config, User user) throws DBConnectionException, SQLException
    {
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        int lockedOut = 0;
        
        if (user.isLockedOut()) lockedOut = 1;
        else                    lockedOut = 0;
        
        try {
            conn = getDBConnection(config); 
            String query = new String("SELECT userId FROM user WHERE username = '" + user.username() + "'");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
            if(rs.next ())
            {
                int userId = rs.getInt("userId");
                query = new String("UPDATE user SET loginAttempts = '" + String.valueOf(user.loginAttempts()) + "', lockedOut = '" + lockedOut + "' WHERE userId = '" + userId + "'");
                pStatement = conn.prepareStatement (query);
                pStatement.executeUpdate ();
            }
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
    }
    
    public User getUserAccess(Config config, String username) throws DBConnectionException, SQLException
    {
        User getUser = null;
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        Connection conn = null;
        
        try {
            conn = getDBConnection(config);
            String query = new String (
                    "SELECT userId, firstName, lastName FROM User u" +
                    " WHERE u.username = '" + username + "'");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
            
            if (rs.next ()) {
                getUser = new User(
                                rs.getInt("userId"),
                                rs.getString("firstName"),
                                rs.getString("lastName"));
            }
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
        return getUser;
    }
    
    public boolean isValidUser(Config config, User user) throws DBConnectionException, SQLException
    {
        boolean isExist = false;
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        
        try {
            conn = getDBConnection(config);
            String query = new String (
                    "SELECT * FROM User u" +
                    " WHERE CONVERT(u.username USING latin1) COLLATE latin1_general_cs = '" + user.username() + "'");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
            
            if (rs.next ()) {
                isExist = true;
            }
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                rs.close();
                pStatement.close();
                conn.close();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
        return isExist;
    }
    
    public boolean isValidEmailUser(Config config, User user) throws DBConnectionException, SQLException
    {
        boolean isExist = false;
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        
        try {
            conn = getDBConnection(config);
            String query = new String (
                    "SELECT * FROM User u" +
                    " WHERE CONVERT(u.username USING latin1) COLLATE latin1_general_cs = '" + user.username() + "'" +
                    " AND u.email = '" + user.email() + "'");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
            
            if (rs.next ()) {
                isExist = true;
            }
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                rs.close();
                pStatement.close();
                conn.close();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
        return isExist;
    }
    
    public boolean isUserExistWithTempPass(Config config, User user) throws DBConnectionException, SQLException
    {
        boolean isExist = false;
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        
        try {
            conn = getDBConnection(config);
            String query = new String (
                    "SELECT * FROM User u" +
                    " WHERE CONVERT(u.username USING latin1) COLLATE latin1_general_cs = '" + user.username() + "'" +
                    " AND u.isTempPass = '1'");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
            
            if (rs.next ()) {
                isExist = true;
            }
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
        return isExist;
    }
    
    public void setPassword(Config config, User user) throws DBConnectionException, SQLException
    {
        PreparedStatement pStatement = null;
        Connection conn = null;
        ResultSet rs = null;
        int userId = -1;
        
        int isTempPass;
        if (user.isTempPass())  isTempPass = 1;
        else                    isTempPass = 0;
        
        try {
            String passHash = MD5.getMD5HashVal(user.password());
            conn = getDBConnection(config);
            
            String query = new String("SELECT userId FROM user WHERE username = '" + user.username() + "'");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
        
            if(rs.next ())
            {
                userId = rs.getInt("userId");
                query = new String("UPDATE user SET passHash = '" + passHash + "', " +
                                   "isTempPass='" + String.valueOf(isTempPass) + "', " +
                                   "loginAttempts = '0', lockedOut = '0' " +
                                   "WHERE userId = '" + userId + "'");
                pStatement = conn.prepareStatement (query);
                pStatement.executeUpdate ();
            }
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
    }
    
    public Object[] getUserNumbers(Config config) throws DBConnectionException, SQLException
    {
        PreparedStatement pStatement = null;
        Connection conn = null;
        ResultSet rs = null;
        double mobNumber = 0;
        ArrayList<String> getNumString = new ArrayList<String>();
        
        try {
            conn = getDBConnection(config);
            String query = new String("SELECT firstName, contactNumber FROM user");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
        
            while(rs.next())
            {
                if (rs.getString("contactNumber").length() == config.SMS_VALIDNUMBER) // Basic Validation
                {
                    String numberString = rs.getString("firstName") + " - " + rs.getString("contactNumber");
                    getNumString.add(numberString);
                }
            }
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
        return getNumString.toArray();
    }
    
    public void addUserAccount(Config config, User user) throws DBConnectionException, SQLException
    {
        PreparedStatement pStatement = null;
        Connection conn =  null;
        
        int lockedOut;
        if (user.isLockedOut()) lockedOut = 1;
        else                    lockedOut = 0;
        
        try {
            String passHash = MD5.getMD5HashVal(user.password());
            conn = getDBConnection(config);
            String query = new String ("INSERT INTO User (" +
                    "userTypeId, " +
                    "firstName, " +
                    "lastName, " +
                    "username, " +
                    "email, " +
                    "passHash, " +
                    "contactNumber, " +
                    "sessionId, " +
                    "loginAttempts, " +
                    "lockedOut, " +
                    "userGroup, " +
                    "isTempPass) VALUES (" +
                    "'" + String.valueOf(user.userTypeId()) + "'" +
                    ", '" + user.firstName() + "'" +
                    ", '" + user.lastName() + "'" +
                    ", '" + user.username() + "'" +
                    ", '" + user.email() + "'" +
                    ", '" + passHash + "'" +
                    ", '" + user.phNumber() + "'" +
                    ", '1'" + // Session ID
                    ", '" + user.loginAttempts() + "'" +
                    ", '" + String.valueOf(lockedOut) + "'" +
                    ", '" + String.valueOf(user.userGroup()) + "'" +
                    ", '0')");// Temp Password
            pStatement = conn.prepareStatement (query);
            pStatement.executeUpdate ();
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
    }
    
    public void editUserAccount(Config config, User user) throws DBConnectionException, SQLException
    {
        PreparedStatement pStatement = null;
        Connection conn = null;
        ResultSet rs = null;
        
        int lockedOut;
        if (user.isLockedOut()) lockedOut = 1;
        else                    lockedOut = 0;
        
        try {
            conn = getDBConnection(config);
            String query = "";
            if (user.password().equals("")) // No Password Change
            {
                query = new String("UPDATE user SET "+
                               "firstName = '"      + user.firstName() + "', " +
                               "lastName = '"       + user.lastName() + "', " +
                               "username = '"       + user.username() + "', " +
                               "email = '"          + user.email() + "', " +
                               "contactNumber = '"  + user.phNumber() + "', " +
                               "userTypeId = '"     + String.valueOf(user.userTypeId()) + "', " +
                               "userGroup = '"      + String.valueOf(user.userGroup()) + "', " +
                               "loginAttempts = '"  + String.valueOf(user.loginAttempts()) + "', " +
                               "lockedOut = '"      + String.valueOf(lockedOut) + "' " +
                               "WHERE userId = '"   + String.valueOf(user.userId()) + "'");
            }
            else // Password Change
            {
                String passHash = MD5.getMD5HashVal(user.password());
                query = new String("UPDATE user SET "+
                               "firstName = '"      + user.firstName() + "', " +
                               "lastName = '"       + user.lastName() + "', " +
                               "username = '"       + user.username() + "', " +
                               "email = '"          + user.email() + "', " +
                               "passHash = '"       + passHash + "', " +
                               "contactNumber = '"  + user.phNumber() + "', " +
                               "userTypeId = '"     + String.valueOf(user.userTypeId()) + "', " +
                               "userGroup = '"      + String.valueOf(user.userGroup()) + "', " +
                               "loginAttempts = '"  + String.valueOf(user.loginAttempts()) + "', " +
                               "lockedOut = '"      + String.valueOf(lockedOut) + "' " +
                               "WHERE userId = '"   + String.valueOf(user.userId()) + "'");
            }
            pStatement = conn.prepareStatement(query);
            pStatement.executeUpdate();
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
    }
       
    public void deleteUserAccount(Config config, int userId) throws DBConnectionException, SQLException
    {
        PreparedStatement pStatement;
        String query = new String ("DELETE FROM user WHERE userId = '" + userId + "'" );
        
        try {
            pStatement = _conn.prepareStatement (query);
            pStatement.executeUpdate ();
            pStatement.close ();
        } catch (SQLException ex) {
            throw new DBConnectionException (ex);
        }
    }
    
    public ArrayList<GenericValue> userTypeValues(Config config) throws DBConnectionException, SQLException 
    {
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        ArrayList<GenericValue> value = new ArrayList<GenericValue>();
        
        try 
        {
            conn = getDBConnection(config);
            String query = new String ("SELECT userTypeId, userTypeName FROM user_type");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
        
            while(rs.next ())
            {
                value.add(new GenericValue(rs.getInt("userTypeId"),
                                           rs.getString("userTypeName")));
            }
        }
        catch (Exception ex) 
        {
            throw new DBConnectionException(ex);
        } 
        finally 
        {
            try 
            {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace ();  
            }
        }
        return value;
    }
    
    public ArrayList<GenericValue> userGroupValues(Config config) throws DBConnectionException, SQLException 
    {
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        ArrayList<GenericValue> value = new ArrayList<GenericValue>();
        
        try 
        {
            conn = getDBConnection(config);
            String query = new String ("SELECT userGroupId, userGroupName FROM user_group");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
        
            while(rs.next ())
            {
                value.add(new GenericValue(rs.getInt("userGroupId"),
                                           rs.getString("userGroupName")));
            }
        }
        catch (Exception ex) 
        {
            throw new DBConnectionException(ex);
        } 
        finally 
        {
            try 
            {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace ();  
            }
        }
        return value;
    }
    
    public ArrayList<GenericValue> roomValues(Config config) throws DBConnectionException, SQLException 
    {
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        ArrayList<GenericValue> value = new ArrayList<GenericValue>();
        
        try 
        {
            conn = getDBConnection(config);
            String query = new String ("SELECT roomuid, id FROM sensordata_room");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
        
            while(rs.next ())
            {
                value.add(new GenericValue(rs.getInt("roomuid"),
                                           rs.getString("id")));
            }
        }
        catch (Exception ex) 
        {
            throw new DBConnectionException(ex);
        } 
        finally 
        {
            try 
            {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace ();  
            }
        }
        return value;
    }
    
    public UserGroups updateUserGroups(Config config) throws DBConnectionException, SQLException 
    {
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        UserGroups userGroups = new UserGroups();
        
        try 
        {
            conn = getDBConnection(config);
            String query = new String ("SELECT * FROM user_group");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
        
            while(rs.next ())
            {
                userGroups.add(new UserGroup(
                            rs.getInt   ("userGroupId"),
                            rs.getString("userGroupName"),
                            rs.getString("userGroupEmail"),
                            rs.getString("userGroupDescription")));
            }
        }
        catch (Exception ex) 
        {
            throw new DBConnectionException(ex);
        } 
        finally 
        {
            try 
            {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace ();  
            }
        }
        return userGroups;
    }
    
    public UserGroup updateUserGroupDetails(Config config, int userGroupId) throws DBConnectionException, SQLException 
    {
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        UserGroup userGroup = null;
        
        try 
        {
            conn = getDBConnection(config);
            String query = new String ("SELECT * FROM user_group WHERE userGroupId = '" + String.valueOf(userGroupId) + "'");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
            
            if(rs.next())
            {
                userGroup = new UserGroup(
                            rs.getString("userGroupName"),
                            rs.getString("userGroupEmail"),
                            rs.getString("userGroupDescription"));
            }
        }
        catch (Exception ex) 
        {
            throw new DBConnectionException(ex);
        } 
        finally 
        {
            try 
            {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace ();  
            }
        }
        return userGroup;
    }
    
    public void addUserGroup(Config config, UserGroup userGroup) throws DBConnectionException, SQLException
    {
        PreparedStatement pStatement = null;
        Connection conn = null;
        
        try {
            conn = getDBConnection(config);
            String query = new String ("INSERT INTO user_group (" +
                    "userGroupName, "  +
                    "userGroupEmail, " +
                    "userGroupDescription) VALUES (" +
                    "'"   + userGroup.userGroupName() + "'" +
                    ", '" + userGroup.userGroupEmail() + "'" +
                    ", '" + userGroup.userGroupDescription()+ "')");
            pStatement = conn.prepareStatement (query);
            pStatement.executeUpdate ();
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
    }
    
    public void editUserGroup(Config config, UserGroup userGroup) throws DBConnectionException, SQLException
    {
        PreparedStatement pStatement = null;
        Connection conn = null;
        ResultSet rs = null;
        
        try {
            conn = getDBConnection(config);
            String query = "";
            query = new String("UPDATE user_group SET " +
                           "userGroupName = '"      + userGroup.userGroupName() + "', " +
                           "userGroupEmail = '"     + userGroup.userGroupEmail() + "', " +
                           "userGroupDescription = '"    + userGroup.userGroupDescription() + "' " +
                           "WHERE userGroupId = '"  + String.valueOf(userGroup.userGroupId() + "'"));

            pStatement = conn.prepareStatement(query);
            pStatement.executeUpdate();
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
    }
    
    public UserGroup getUserGroup(Config config, int usergroupid) throws DBConnectionException, SQLException
    {
        UserGroup getUserGroup = null;
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        Connection conn = null;
        
        try {
            conn = getDBConnection(config);
            String query = new String (
                    "SELECT u.userGroupId, u.userGroupName, u.userGroupEmail FROM user_group u" +
                    " WHERE u.userGroupId = '" + usergroupid + "'");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
            
            if (rs.next ()) {
                getUserGroup = new UserGroup(
                                rs.getString("userGroupName"),
                                rs.getString("userGroupEmail"));
            }
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
        return getUserGroup;
    }
    
    public Object[] getUserType(Config config, int userTypeId, int userGroupId) throws DBConnectionException, SQLException
    {
        Object[] obj = new Object[2];
        UserGroup userGroup = null;
        UserType getUserType = null;
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        Connection conn = null;
        
        try {
            conn = getDBConnection(config);
            String query = new String (
                    "SELECT u.userTypeId, u.userTypeName FROM user_type u" +
                    " WHERE u.userTypeId = '" + userTypeId + "'");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery();
            
            if (rs.next ()) {
                getUserType = new UserType(rs.getString("userTypeName"));
            }
            userGroup = getUserGroup(config,userGroupId);
            obj[0] = getUserType;
            obj[1] = userGroup;
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
        return obj;
    }
    
    public Object[] getUser(Config config, int userId)  throws DBConnectionException, SQLException
    {
        Object[] obj = new Object[2];
        UserGroup userGroup = null;
        User getUser = null;
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        Connection conn = null;
        
        try {
            conn = getDBConnection(config);
            String query = new String (
                    "SELECT u.userId, u.firstName, u.lastName, u.username, u.email, u.userGroup FROM user u" +
                    " WHERE u.userId = '" + userId + "'");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
            
            if (rs.next ()) {
                getUser = new User(
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("username"),
                                rs.getString("email"),
                                rs.getInt("userGroup"));
            }
            userGroup = getUserGroup(config,getUser.userGroup());
            obj[0] = getUser;
            obj[1] = userGroup;
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
        return obj;
    }
    
    public void deleteUserGroup(Config config, int userGroupId) throws DBConnectionException, SQLException
    {
        PreparedStatement pStatement;
        String query = new String ("DELETE FROM user_group WHERE userGroupId = '" + userGroupId + "'" );
        
        try {
            pStatement = _conn.prepareStatement (query);
            pStatement.executeUpdate ();
            pStatement.close ();
        } catch (SQLException ex) {
            throw new DBConnectionException (ex);
        }
    }
    
    public Users updateUsers(Config config) throws DBConnectionException, SQLException 
    {
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        Users users = new Users();
        boolean isLockedOut = false;
        
        try 
        {
            conn = getDBConnection(config);
            String query = new String ("SELECT * FROM User");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
        
            while(rs.next ())
            {
                if (rs.getInt("lockedOut") == 1) isLockedOut = true;
                else                             isLockedOut = false;
                users.add(new User(
                            rs.getInt   ("userId"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("passHash"),
                            rs.getString("contactNumber"),
                            rs.getInt   ("userTypeId"),
                            rs.getInt   ("userGroup"),
                            rs.getInt   ("loginAttempts"),
                            isLockedOut));
            }
        }
        catch (Exception ex) 
        {
            throw new DBConnectionException(ex);
        } 
        finally 
        {
            try 
            {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace ();  
            }
        }
        return users;
    }
    
    public User updateUserDetails(Config config, int userId) throws DBConnectionException, SQLException 
    {
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        User user = null;
        boolean isLockedOut = false;
        
        try 
        {
            conn = getDBConnection(config);
            String query = new String ("SELECT * FROM User WHERE userId = '" + String.valueOf(userId) + "'");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
            
            if(rs.next())
            {
                if (rs.getInt("lockedOut") == 1) isLockedOut = true;
                else                             isLockedOut = false;
                
                user = new User(
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("passHash"),
                            rs.getString("contactNumber"),
                            rs.getInt   ("userTypeId"),
                            rs.getInt   ("userGroup"),
                            rs.getInt   ("loginAttempts"),
                            isLockedOut);
            }
        }
        catch (Exception ex) 
        {
            throw new DBConnectionException(ex);
        } 
        finally 
        {
            try 
            {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace ();  
            }
        }
        return user;
    }
    
    public Events updateEvents(Config config) throws DBConnectionException, SQLException 
    {
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        Events events = new Events();
        
        try 
        {
            conn = getDBConnection(config);
            String query = new String ("SELECT * FROM events_log");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
        
            while(rs.next ())
            {
                events.add(new Event(
                            rs.getInt ("eventsId"),
                            rs.getInt ("deviceId"),
                            rs.getInt ("alertId"),
                            rs.getInt ("eventType"),
                            rs.getDate("timestamp")));
            }
        }
        catch (Exception ex) 
        {
            throw new DBConnectionException(ex);
        } 
        finally 
        {
            try 
            {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace ();  
            }
        }
        return events;
    }

    public AlertSettings updateAlertSettings(Config config) throws DBConnectionException, SQLException 
    {
        PreparedStatement pStatement = null;
        ResultSet rs                 = null;
        Connection conn              = null;
        AlertSettings alertSettings  = new AlertSettings();
        
        try 
        {
            conn = getDBConnection(config);
            String query = new String ("SELECT * FROM alert_setting");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
        
            while(rs.next ())
            {
                alertSettings.add(new AlertSetting(
                            rs.getInt    ("alertId"),
                            rs.getInt    ("alertUserGroup"),
                            rs.getInt    ("score"),
                            rs.getInt    ("dataParameterId"),
                            rs.getString ("alertType"),
                            rs.getString ("alertPriority"),
                            rs.getDate   ("timestamp"),
                            rs.getDouble ("minThresholdValue"),
                            rs.getDouble ("maxThresholdValue"),
                            rs.getDouble ("exactThresholdValue"),
                            rs.getBoolean("minThresholdEnabled"),
                            rs.getBoolean("maxThresholdEnabled"),
                            rs.getBoolean("exactThresholdEnabled"),
                            rs.getBoolean("scoreEnabled")));
            }
        }
        catch (Exception ex) 
        {
            throw new DBConnectionException(ex);
        } 
        finally 
        {
            try 
            {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace ();  
            }
        }
        return alertSettings;
    }
    
    public Alerts updateAlerts(Config config) throws DBConnectionException, SQLException 
    {
        PreparedStatement pStatement = null;
        ResultSet rs    = null;
        Connection conn = null;
        Alerts alerts   = new Alerts();
        Blob blob       = null;
        byte[] dump     = new byte[0];
        
        try 
        {
            conn = getDBConnection(config);
            String query = new String ("SELECT * FROM alert_log");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
        
            while(rs.next ())
            {
                try {
                    blob = rs.getBlob("sensorDatadump");
                    dump = blob.getBytes(1,(int)blob.length()); // materialize BLOB
                } catch (Exception e) {}
                
                alerts.add(new Alert(
                            rs.getInt   ("alertsId"),
                            rs.getString("alertType"),
                            rs.getString("alertPriority"),
                            rs.getDate  ("timestamp"),
                            dump));
            }
        }
        catch (Exception ex) 
        {
            throw new DBConnectionException(ex);
        } 
        finally 
        {
            try 
            {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace ();  
            }
        }
        return alerts;
    }
    
    public Alert getAlert(Config config, int alertsId) throws DBConnectionException, SQLException 
    {
        PreparedStatement pStatement = null;
        ResultSet rs    = null;
        Connection conn = null;
        Alert alert     = null;
        Blob blob       = null;
        byte[] dump     = new byte[0];
        
        try 
        {
            conn = getDBConnection(config);
            String query = new String ("SELECT * FROM alert_log WHERE alertsId = '" + alertsId + "'" );
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
        
            if(rs.next())
            {
                try {
                    blob = rs.getBlob("sensorDatadump");
                    dump = blob.getBytes(1,(int)blob.length()); // materialize BLOB
                } catch (Exception e) {}
                
                alert = new Alert(
                            rs.getInt   ("alertsId"),
                            rs.getString("alertType"),
                            rs.getString("alertPriority"),
                            rs.getDate  ("timestamp"),
                            dump);
            }
        }
        catch (Exception ex) 
        {
            throw new DBConnectionException(ex);
        } 
        finally 
        {
            try 
            {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace ();  
            }
        }
        return alert;
    }

    public UserTypes updateUserTypes(Config config) throws DBConnectionException, SQLException 
    {
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        UserTypes userTypes = new UserTypes();
        
        try 
        {
            conn = getDBConnection(config);
            String query = new String ("SELECT * FROM User_Type");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
        
            while(rs.next ())
            {
                userTypes.add(new UserType(
                            rs.getInt   ("userTypeId"),
                            rs.getString("userTypeName"),
                            rs.getString("userTypeDescription")));
            }
        }
        catch (Exception ex) 
        {
            throw new DBConnectionException(ex);
        } 
        finally 
        {
            try 
            {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace ();  
            }
        }
        return userTypes;
    }
    
    public void addUserType(Config config, UserType userType) throws DBConnectionException, SQLException
    {
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        
        try {
            conn = getDBConnection(config);
            String query = new String ("INSERT INTO User_Type (" +
                    "userTypeName, "  +
                    "userTypeDescription) VALUES (" +
                    "'" +   userType.userTypeName() + "'" +
                    ", '" + userType.userTypeDescription() + "')");
            pStatement = conn.prepareStatement (query);
            pStatement.executeUpdate ();
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
    }
    
    public void editUserType(Config config, UserType userType) throws DBConnectionException, SQLException
    {
        PreparedStatement pStatement = null;
        Connection conn = null;
        ResultSet rs = null;
        
        try {
            conn = getDBConnection(config);
            String query = "";
            query = new String("UPDATE User_Type SET " +
                           "userTypeName = '"      + userType.userTypeName() + "', " +
                           "userTypeDescription = '"     + userType.userTypeDescription() + "' " +
                           "WHERE userTypeId = '"  + String.valueOf(userType.userTypeId() + "'"));

            pStatement = conn.prepareStatement(query);
            pStatement.executeUpdate();
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
    }
    
    public void deleteUserType(Config config, int userTypeId) throws DBConnectionException, SQLException
    {
        PreparedStatement pStatement;
        String query = new String ("DELETE FROM User_Type WHERE userTypeId = '" + userTypeId + "'" );
        
        try {
            pStatement = _conn.prepareStatement (query);
            pStatement.executeUpdate ();
            pStatement.close ();
        } catch (SQLException ex) {
            throw new DBConnectionException (ex);
        }
    }
    
    public Devices updateDevices(Config config) throws DBConnectionException, SQLException 
    {
        PreparedStatement pStatement = null;
        ResultSet rs =  null;
        Connection conn =  null;
        Devices devices = new Devices();
        boolean connected = false;
        
        try 
        {
            conn = getDBConnection(config);
            String query = new String ("SELECT * FROM sensordata_device");
            pStatement = conn.prepareStatement (query);
            rs = pStatement.executeQuery ();
        
            while(rs.next ())
            {
                if (rs.getInt("connected") == 1) connected = true;
                else                             connected = false;
                devices.add(new Device(
                            rs.getInt   ("deviceuid"),
                            rs.getInt   ("id"),
                            rs.getInt   ("roomuid"),
                            rs.getInt   ("userGroup"),
                            rs.getString("type"),
                            rs.getString("brand"),
                            rs.getString("model"),
                            rs.getString("serial"),
                            rs.getString("macaddress"),
                            connected));
            }
        }
        catch (Exception ex) 
        {
            throw new DBConnectionException(ex);
        } 
        finally 
        {
            try 
            {
                rs.close ();
                pStatement.close ();
                conn.close ();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace();  
            }
        }
        return devices;
    }
    
    public void addDevice(Config config, Device device) throws DBConnectionException, SQLException {
        PreparedStatement pStatement = null;
        Connection conn =  null;
        int connected;
        if (device.connected()) connected = 1;
        else                    connected = 0;
        
        try {
            conn = getDBConnection(config);
            String query = new String ("INSERT INTO sensordata_device (" +
                    "id, " +
                    "roomuid, " +
                    "userGroup, " +
                    "type, " +
                    "brand, " +
                    "model, " +
                    "serial, " +
                    "macaddress, " +
                    "connected) VALUES (" +
                    "'"   + String.valueOf(device.id())        + "'" +
                    ", '" + String.valueOf(device.roomuid())   + "'" +
                    ", '" + String.valueOf(device.userGroup()) + "'" +
                    ", '" + device.type()                      + "'" +
                    ", '" + device.brand()                     + "'" +
                    ", '" + device.model()                     + "'" +
                    ", '" + device.serial()                    + "'" +
                    ", '" + device.macaddress()                + "'" +
                    ", '" + String.valueOf(connected)          + "')");
            pStatement = conn.prepareStatement (query);
            pStatement.executeUpdate ();
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
    }
    
    public void editDevice(Config config, Device device) throws DBConnectionException, SQLException {
        PreparedStatement pStatement = null;
        Connection conn = null;
        ResultSet rs = null;
        int connected;
        if (device.connected()) connected = 1;
        else                    connected = 0;
        
        try {
            conn = getDBConnection(config);
            String query = new String("UPDATE sensordata_device SET "+
                               "id = '"              + String.valueOf(device.id()) + "', " +
                               "roomuid = '"         + String.valueOf(device.roomuid()) + "', " +
                               "userGroup = '"       + String.valueOf(device.userGroup()) + "', " +
                               "type = '"            + device.type() + "', " +
                               "brand = '"           + device.brand() + "', " +
                               "model = '"           + device.model() + "', " +
                               "serial = '"          + device.serial() + "', " +
                               "macaddress = '"      + device.macaddress() + "', " +
                               "connected = '"       + String.valueOf(connected) + "' " +
                               "WHERE deviceuid = '" + String.valueOf(device.deviceuid()) + "'");
            pStatement = conn.prepareStatement(query);
            pStatement.executeUpdate();
        } catch (Exception ex) {
            throw new DBConnectionException (ex);
        } finally {
            try {
                pStatement.close ();
                conn.close ();
            } catch (SQLException ex) {
                throw new SQLException ();
            } catch (NullPointerException ex) {
                throw new NullPointerException ();
            }
        }
    }
    
    public void deleteDevice(Config config, int deviceuid) throws DBConnectionException, SQLException {
        PreparedStatement pStatement;
        String query = new String ("DELETE FROM sensordata_device WHERE deviceuid = '" + deviceuid + "'" );
        
        try {
            pStatement = _conn.prepareStatement (query);
            pStatement.executeUpdate ();
            pStatement.close ();
        } catch (SQLException ex) {
            throw new DBConnectionException (ex);
        }
    }
}