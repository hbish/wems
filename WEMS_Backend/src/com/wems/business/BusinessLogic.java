package com.wems.business;

import java.util.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import com.wems.config.*;
import com.wems.database.DataAccess;
import com.wems.exception.DBConnectionException;
import com.wems.model.*;



public class BusinessLogic
{    
    private static BusinessLogic _logic = null;
    private DataAccess _da;
    private Config config;
    
    public static synchronized BusinessLogic create(Config config) {
        if(_logic == null) {
            _logic = new BusinessLogic(config);
            return _logic;
        }
        return _logic;
    }
    
    public BusinessLogic(Config config) {
        this.config = config;
        _da = DataAccess.create(config);
    }
     
    public boolean validateLogin(String username, String password) throws DBConnectionException, SQLException, NullPointerException {
        boolean isExist = false;
        User user = new User(username, password, false);
        if(_da.isUserAccountExist(config, user))
            isExist = true;
        return isExist;
    }
    
    public boolean validateUser(String username, String email) throws DBConnectionException, SQLException, NullPointerException {
        boolean isExist = false;
        User user = new User(username, email);
        if(_da.isValidEmailUser(config, user))
            isExist = true;
        return isExist;
    }
    
    public boolean isUserExistWithTempPass(String username) throws DBConnectionException, SQLException, NullPointerException {
        boolean isExist = false;
        User user = new User(username);
        if(_da.isUserExistWithTempPass(config, user))
            isExist = true;
        return isExist;
    }
    
    public void assignTempPassword(String username, String password) throws DBConnectionException, SQLException, NullPointerException {
        User user = new User(username, password, true);
        _da.setPassword(config, user);
    }
    
    public void assignNewPassword(String username, String password) throws DBConnectionException, SQLException, NullPointerException {
        User user = new User(username, password, false);
        _da.setPassword(config, user);
    }
    
    public boolean isValidUser(String username) throws DBConnectionException, SQLException, NullPointerException {
        User user = new User(username);
        return _da.isValidUser(config, user);
    }
    
    public int currentLoginAttempts(String username) throws DBConnectionException, SQLException, NullPointerException {
        User user = new User(username);
        return _da.currentLoginAttempts(config, user);
    }
    
    public boolean setFailedLoginAttempts(String username, int loginAttempts, int maxLoginAttempts) throws DBConnectionException, SQLException, NullPointerException {
        boolean isLockedOut;
        
        if(loginAttempts >= maxLoginAttempts)
            isLockedOut = true;
        else
            isLockedOut = false;
            
        User user = new User(username, loginAttempts, isLockedOut);
        _da.setFailedLoginAttempts(config, user);
        return isLockedOut;
    }
    
    public ArrayList<GenericValue> userTypeValues() throws DBConnectionException, SQLException, NullPointerException {
        return _da.userTypeValues(config);
    }
    
    public ArrayList<GenericValue> userGroupValues() throws DBConnectionException, SQLException, NullPointerException {
        return _da.userGroupValues(config);
    }
    
    public ArrayList<GenericValue> roomValues() throws DBConnectionException, SQLException, NullPointerException {
        return _da.roomValues(config);
    }
    
    public void addUser(String firstName, String lastName, String username, String email, String password, String phNumber, int userTypeId, int userGroup, int loginAttempts, boolean isLockedOut) throws DBConnectionException, SQLException, NullPointerException {
        User user = new User(firstName, lastName, username, email, password, phNumber, userTypeId, userGroup, loginAttempts, isLockedOut);
        _da.addUserAccount(config, user);
    }
    
    public void editUser(int userId, String firstName, String lastName, String username, String email, String password, String phNumber, int userTypeId, int userGroup, int loginAttempts, boolean isLockedOut) throws DBConnectionException, SQLException, NullPointerException {
        User user = new User(userId, firstName, lastName, username, email, password, phNumber, userTypeId, userGroup, loginAttempts, isLockedOut);
        _da.editUserAccount(config, user);
    }
    
    public void deleteUser(int userId) throws DBConnectionException, SQLException, NullPointerException {
        _da.deleteUserAccount(config, userId);
    }
    
    public Users updateUsers() throws DBConnectionException, SQLException, NullPointerException {
        return _da.updateUsers(config); 
    }
    
    public User updateUserDetails(int userId) throws DBConnectionException, SQLException, NullPointerException {
        return _da.updateUserDetails(config, userId);
    }
    
    public User getUserAccess(String username) throws DBConnectionException, SQLException, NullPointerException {
        return _da.getUserAccess(config, username);
    }
    
    public UserGroups updateUserGroups() throws DBConnectionException, SQLException, NullPointerException {
        return _da.updateUserGroups(config);
    }
    
    public UserGroup updateUserGroupDetails(int userGroupId) throws DBConnectionException, SQLException, NullPointerException {
        return _da.updateUserGroupDetails(config, userGroupId);
    }
    
    public void addUserGroup(String userGroupName, String userGroupEmail, String userGroupDescription) throws DBConnectionException, SQLException, NullPointerException {
        UserGroup userGroup = new UserGroup(userGroupName, userGroupEmail, userGroupDescription);
        _da.addUserGroup(config, userGroup);
    }
    
    public void editUserGroup(int userGroupId, String userGroupName, String userGroupEmail, String userGroupDescription) throws DBConnectionException, SQLException, NullPointerException {
        UserGroup userGroup = new UserGroup(userGroupId, userGroupName, userGroupEmail, userGroupDescription);
        _da.editUserGroup(config, userGroup);
    }
    
    public void deleteUserGroup(int userGroupId) throws DBConnectionException, SQLException, NullPointerException {
        _da.deleteUserGroup(config, userGroupId);
    }

    public UserGroup getUserGroup(int usergroupid) throws DBConnectionException, SQLException, NullPointerException {
        return _da.getUserGroup(config, usergroupid);
    }
    
    public Object[] getUserType(int userTypeId, int userGroupId) throws DBConnectionException, SQLException, NullPointerException {
        return _da.getUserType(config, userTypeId, userGroupId);
    }
    
    public Object[] getUser(int userId) throws DBConnectionException, SQLException, NullPointerException {
        return _da.getUser(config, userId);
    }
    
    public Events updateEvents() throws DBConnectionException, SQLException, NullPointerException {
        return _da.updateEvents(config); 
    }
    
    public AlertSettings updateAlertSettings() throws DBConnectionException, SQLException, NullPointerException {
        return _da.updateAlertSettings(config);
    }
    
    public Alerts updateAlerts() throws DBConnectionException, SQLException, NullPointerException {
        return _da.updateAlerts(config);
    }
    
    public Alert getAlert(int alertsId) throws DBConnectionException, SQLException, NullPointerException {
        return _da.getAlert(config, alertsId);
    }
    
    public UserTypes updateUserTypes() throws DBConnectionException, SQLException, NullPointerException {
        return _da.updateUserTypes(config); 
    }
    
    public void addUserType(String userTypeName, String userTypeDescription) throws DBConnectionException, SQLException, NullPointerException {
        UserType userType = new UserType(userTypeName, userTypeDescription);
        _da.addUserType(config, userType);
    }
    
    public void editUserType(int userTypeId, String userTypeName, String userTypeDescription) throws DBConnectionException, SQLException, NullPointerException {
        UserType userType = new UserType(userTypeId, userTypeName, userTypeDescription);
        _da.editUserType(config, userType);
    }

    public void deleteUserType(int userTypeId) throws DBConnectionException, SQLException, NullPointerException {
        _da.deleteUserType(config, userTypeId);
    }
    
    public Devices updateDevices() throws DBConnectionException, SQLException, NullPointerException {
        return _da.updateDevices(config);
    }
    
    public void addDevice(int id, int roomuid, int userGroup, String type, String brand, String model, String serial, String macaddress, boolean connected) throws DBConnectionException, SQLException, NullPointerException {
        Device device = new Device(id, roomuid, userGroup, type, brand, model, serial, macaddress, connected);
        _da.addDevice(config, device);
    }
    
    public void editDevice(int deviceuid, int id, int roomuid, int userGroup, String type, String brand, String model, String serial, String macaddress, boolean connected) throws DBConnectionException, SQLException, NullPointerException {
        Device device = new Device(deviceuid, id, roomuid, userGroup, type, brand, model, serial, macaddress, connected);
        _da.editDevice(config, device);
    }

    public void deleteDevice(int deviceuid) throws DBConnectionException, SQLException, NullPointerException {
        _da.deleteDevice(config, deviceuid);
    }
    
    public Object[] getUserNumbers() throws DBConnectionException, SQLException, NullPointerException {
        return _da.getUserNumbers(config);
    }
}