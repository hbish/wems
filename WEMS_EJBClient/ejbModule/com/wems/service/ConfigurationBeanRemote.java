package com.wems.service;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import com.wems.data.UserDataClass;
import com.wems.data.UserGroupDataClass;

@Remote
public interface ConfigurationBeanRemote {

//	WEMS-CON-F-03 (Ref HMI)
	
//	WEMS-CON-F-05
//	WEMS-CON-F-06
	public void registerSensor();
//	WEMS-CON-F-07
//	WEMS-CON-F-12
	public boolean updateUserGroup(String ugnOLD, String ugnNEW, String email, String desc);
	public boolean addUser(String firstName, String lastName, String userName, String password, String contact, String email, String userGroup, String userType);	//	WEMS-CON-F-08
	public boolean updateUserPassword(String password, int userid);
	public boolean updateUser(String firstName, String lastName, String userName, String contact, String email, int userGroupid);//	WEMS-CON-F-10
//	WEMS-CON-F-11 (DB specific)
	//	WEMS-CON-F-09
	public boolean removeUser(int id);
	public List<UserDataClass> getUsersForUserGroup(int ugid);
	public List<Integer> getAllUserGroupId();
	public List<String> getAllUserGroupNames();
	public List<Integer> getAllUserId();
	public List<UserDataClass> getAllUsers();
	public UserDataClass getUserByUsername(String username);
	public UserDataClass getUserById(int id);
	
	public boolean addUserGroup(String name, String desc, String email);
	
	public List<UserGroupDataClass> getUserGroupList();
	
	public boolean removeUserGroup(int id);

	public int getUserGroupIdByUsername(String name);
	public List<String> getUserTypeNames();
	public UserGroupDataClass getUGDetails(String ugname);
}
