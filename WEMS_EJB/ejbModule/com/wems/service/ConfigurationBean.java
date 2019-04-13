package com.wems.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;

import com.wems.data.UserDataClass;
import com.wems.data.UserGroupDataClass;
import com.wems.entity.*;
import com.wems.eao.*;
import com.wems.util.MD5;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;


/**
 * Session Bean implementation class ConfigurationBean
 */
@Stateless
@LocalBean
@WebService
public class ConfigurationBean implements ConfigurationBeanRemote {
	@EJB UserEao userEao;
	@EJB EmailerBean emailer;
	MD5 myMd5 = new MD5();
    String myHash = null;

    /**
     * Default constructor. 
     */
    public ConfigurationBean() {
    }


	@Override
	public void registerSensor() {
		/* add device to the device table
		 * add to event log [EventsBean.addEvent()]
		 */
		
	}
	
	//Add user to the database
	@Override
	public boolean addUser(String firstName, String lastName, String userName, String password, String contact, String email, String userGroupName, String userTypeName){
		/* add user to the user table 
		 * add to event log [EventsBean.addEvent()]
		 */
		User user = new User();
		String myPassword = password;
		
		user.setUserId(1);
		user.setContactNumber(contact);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setLockedOut(0);
		user.setLoginAttempts(0);
		
        try {
          myHash = myMd5.getMD5Hash(myPassword);
        } catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
        }
		
		user.setPassHash(myHash);
		user.setUsername(userName);
		UserGroup userGroup;		
		try {
			userGroup = userEao.getUserGroupByName(userGroupName);
		} catch (Exception exception) {
			System.out.print("DEBUG: "+userGroupName + " NOT FOUND");
			return false;
		}
		UserType userType;		
		try {
			userType = userEao.getUserTypeByName(userTypeName);
		} catch (Exception exception) {
			return false;
		}
		UserSession userSession;		
		try {
			userSession = userEao.getUserSession(1);
		} catch (Exception exception) {
			return false;
		}
		user.setUserSession(userSession);
		user.setUserGroupBean(userGroup);
		user.setUserType(userType);
		user.setIsTempPass(false);
		try {
			userEao.addNewUser(user);
		} catch (Exception exception) {
			return false;
		}
		return true;
		
	}

	//Get List of User Group Names
	@Override
	public List<String> getUserTypeNames(){
		List<String> allTypes = userEao.getAllUserTypes();
		return allTypes;		
	}

	
	@Override
	//removed userID and userTypeID since these aren't edited by the user at edit_user.jsp - David K
	public boolean updateUser(String firstName, String lastName, String userName, String contact, String email, int userGroupid) {
		/* update user to the user table
		 * add to event log [EventsBean.addEvent()]
		 */
		User user = null;
		try{
			//user = userEao.getUserById(userid);
			user = userEao.getUserByUsername(userName);
		} catch(Exception e){
			return false;
		}
		user.setFirstName(firstName);
		user.setContactNumber(contact);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setLockedOut(0);
		user.setLoginAttempts(0);
		user.setUsername(userName);
		UserGroup userGroup;		
		try {
			userGroup = userEao.getUserGroupById(userGroupid);
		} catch (Exception exception) {
			return false;
		}
		UserType userType;		
		try {
			userType = userEao.getUserTypeById(1);
		} catch (Exception exception) {
			return false;
		}		
		user.setUserGroupBean(userGroup);
		user.setUserType(userType);
		try {
			userEao.updateUser(user);
		} catch (Exception exception) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean updateUserGroup(String ugnOLD, String ugnNEW, String email, String desc) {

		UserGroup ug = null;
		try{
			ug = userEao.getUserGroupByName(ugnOLD);
		} catch(Exception e){
			return false;
		}
		ug.setUserGroupName(ugnNEW);
		ug.setUserGroupEmail(email);
		ug.setUserGroupDescription(desc);
		try {
			userEao.updateUserGroup(ug);
		} catch (Exception exception) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean updateUserPassword(String password, int userid) {
		/* update user to the user table
		 * add to event log [EventsBean.addEvent()]
		 */
		User user = null;
		try{
			user = userEao.getUserById(userid);
		} catch(Exception e){
			return false;
		}

		user.setPassHash(password);

		try {
			userEao.updateUser(user);
		} catch (Exception exception) {
			return false;
		}
		return true;
	}


	@Override
	public boolean removeUser(int id) {
		/* remove user to the user table
		 * add to event log [EventsBean.addEvent()]
		 */
		User user = null;
		try{
			user = userEao.getUserById(id);
		}catch(Exception e){
			return false;
		}
		try{
			userEao.removeUser(user);
		}catch(Exception e){
			return false;
		}
		emailer.sendEmailForDeleteUser(user);
		return true;
	}
	
	//Get User Details given ID
	@Override
	public UserDataClass getUserById(int id){
		User user = null;
		UserDataClass userData = new UserDataClass();
		DataConverter converter = new DataConverter();
		try{
			user = userEao.getUserById(id);
		}catch (Exception e){
			return userData;
		}
		userData = converter.convertUser(user);
		return userData;
	}
	
	//Get User Details given username
	@Override
	public UserDataClass getUserByUsername(String username){
		System.out.println("***in the getUserByUsername method with username= " + username);
		User user = null;
		UserDataClass userData = new UserDataClass();
		DataConverter converter = new DataConverter();
		try{
			user = userEao.getUserByUsername(username);
			System.out.println("***now in try block - getUserByUserName in ConfigBean class: " + user.getFirstName());
		}catch (Exception e){
			//System.out.printlnr("***now in catch block - erorr= " + e.getMessage());
			e.printStackTrace();
			return userData;
		}
		userData = converter.convertUser(user);
		return userData;
	}
	
	//Get List of User IDs
	@Override
	public List<Integer> getAllUserId(){
		List<Integer> allUserId = userEao.getAllUsersId();
		return allUserId;
	}
	//Get List of User Group Names
	@Override
	public List<String> getAllUserGroupNames(){
		List<UserGroup> allUserGroups = userEao.getAllUserGroups();
		List<String> userGroupNames = new ArrayList<String>();
		
		for (UserGroup usergroup: allUserGroups){
			userGroupNames.add(usergroup.getUserGroupName());
		}
		return userGroupNames;
	}
	
	//Get List of User Group IDs
	@Override
	public List<Integer> getAllUserGroupId(){
		List<UserGroup> allUserGroups = userEao.getAllUserGroups();
		List<Integer> userGroupNames = new ArrayList<Integer>();
		for (UserGroup usergroup: allUserGroups){
			userGroupNames.add(usergroup.getUserGroupId());
		}
		return userGroupNames;
	}
	
	//Get List of Users for given USer Group ID
	@Override
	public List<UserDataClass> getUsersForUserGroup(int ugid){
		List<UserDataClass> uDataList = new LinkedList<UserDataClass>();
		DataConverter converter = new DataConverter();
		UserGroup ug = null;
		try{
			ug = userEao.getUserGroupById(ugid);
		}catch (Exception e){
			return uDataList;
		}
		Set<User> uSet = ug.getUsers();
		Iterator<User> itr = uSet.iterator();
		while(itr.hasNext()){
			uDataList.add(converter.convertUser(itr.next()));
		}
		return uDataList;
	}
	

	//Add new User Group
	@Override
	public boolean addUserGroup(String name, String desc, String email) {
		UserGroup ug = new UserGroup();
	    ug.setUserGroupName(name);
	    ug.setUserGroupDescription(desc);
	    ug.setUserGroupEmail(email);
	    try {
	      userEao.addUserGroup(ug);
	    } catch (Exception e) {
	      e.printStackTrace();
	      return false;
	    }
	    return true;
	 }
	 
	@Override
	public UserGroupDataClass getUGDetails(String ugname){
		UserGroup ug = null;
		UserGroupDataClass ugd = null;
		DataConverter converter = new DataConverter();
		try{
			ug = userEao.getUserGroupByName(ugname);
		}catch (Exception e){
			return ugd;
		}
		return (converter.convertUserGroup(ug));
	}
	 //Get All User Groups
	 @Override
	 public List<UserGroupDataClass> getUserGroupList() {
	    List<UserGroup> ugList = null;
	    DataConverter converter = new DataConverter();
	    try {
	      ugList = userEao.getAllUserGroups();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    List <UserGroupDataClass> ugDataList = new LinkedList<UserGroupDataClass>();
	    Iterator<UserGroup> itr = ugList.iterator();
	    while (itr.hasNext()){
	    	ugDataList.add(converter.convertUserGroup(itr.next()));
	    }
	    return ugDataList;
	 }
	
	 //Remove User Group given its id
	 @Override
	 public boolean removeUserGroup(int id) {
	    UserGroup ug = null;
	    try {
	    	System.out.println("***in ConfigBean removeUserGroup try block, ID=" + id);
	      ug = userEao.getUserGroupById(id);
	      userEao.removeUserGroup(ug);
	    } catch (Exception e) {
	    	System.out.println("***in ConfigBean removeUserGroup catch block, ID =" + id);
	      e.printStackTrace();
	      return false;
	    }
	    return true;
	 }
	 
	//Get User group given username
		 @Override
		 public int getUserGroupIdByUsername(String name) {
		    UserGroup ug = null;
		    try {
		      ug = userEao.getUserGroupByUsername(name);
		      return ug.getUserGroupId();
		    } catch (Exception e) {
		      e.printStackTrace();
		      return -1;
		    }
		 }


		@Override
		public List<UserDataClass> getAllUsers() {
			List<User> users = null;
			List<UserDataClass> userDataList = new LinkedList<UserDataClass>();
			DataConverter converter = new DataConverter();
			try{
				users = userEao.getAllUsers();
			} catch (Exception e){
				System.out.println("ERROR: Cannot get list of all users " + e);
				return userDataList;
			}
			Set<User> uSet = new HashSet<User>(users);
			Iterator<User> itr = uSet.iterator();
			while(itr.hasNext()){
				userDataList.add(converter.convertUser(itr.next()));
			}
			return userDataList;
		}

}
