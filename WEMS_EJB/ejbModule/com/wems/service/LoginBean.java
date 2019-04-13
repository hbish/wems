package com.wems.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;

import com.wems.eao.UserEao;
import com.wems.entity.User;
import com.wems.entity.UserSession;
import com.wems.util.MD5;

/**
 * Session Bean implementation class LoginBean
 */
@Stateless
@LocalBean
@WebService
public class LoginBean implements LoginBeanRemote {
	@EJB UserEao userEao;
	
	MD5 myMd5 = new MD5();
    /**
     * Default constructor. 
     */
    public LoginBean() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public int getUserGroupIDByName(String name){
    	try{
    		System.out.println("in try block in LoginBean");
    	return userEao.checkUserGroupByName(name);
    	}
    	catch (Exception e){
    		System.out.println("in catch block in LoginBean");
    		return -1;
    	}
    }
    
    @Override
    public String getUserNameByIP(String ip){
    	User user;
    	List<UserSession> usList = new LinkedList<UserSession>();
    	UserSession session = null;
    	try{
			 usList = userEao.getUserSessionByIP(ip);
		} catch (Exception exception) {
			return"";
		}
    	if (usList.size() < 1) {
    		return "";
    	}
    	
    	session = usList.get(usList.size()-1);
    	try{
    		user = userEao.getUserBySession(session);	
    	}catch(Exception e){
    		return "";
    	}
    	return user.getUsername();
    }
    //Login User
    @Override
    public boolean checkPassword(String username, String password){
    	User user = null;
    	String myHash = null;
		if(isUser(username)){
			user = userEao.getUserByUsername(username);
			try {
                  myHash = myMd5.getMD5Hash(password);
                  System.out.print(myHash);
                  System.out.print(password);
                } catch (NoSuchAlgorithmException e) {
                  e.printStackTrace();
                }
			if (user.getPassHash().equals(myHash)){
				return true;
			} 
		}
		return false;
    }
    
	//Check if a user exist by username
    @Override
	public boolean isUser(String username){
		try{
			userEao.getUserByUsername(username);
		} catch (Exception exception){
			return false;
		}
		return true;
	}
    
    @Override
    public void createSession(String username, String sIPAddress){
    	List<UserSession> usList = new LinkedList<UserSession>();
		UserSession usersession = new UserSession();
		usersession.setIpAddress(sIPAddress);
		usersession.setLoggedInTime(new Date());
		usersession.setSessionStatus(0);
		User user = userEao.getUserByUsername(username);
		Set<User> userSet = new HashSet<User>();
		userSet.add(user);
		usersession.setUsers(userSet);
		try {
			userEao.addUserSession(usersession);
		} catch (Exception exception) {
			return;
		}
		try{
			 usList = userEao.getUserSessionByIP(sIPAddress);
		} catch (Exception exception) {
			return;
		}
		user.setUserSession(usList.get(usList.size()-1));
		try{
			userEao.updateUser(user);
		} catch (Exception exception){
			return;
		}
		return;
	}

	@Override
	public void removeSession(String username, String sIPAddress) {
		List<UserSession> usList = new LinkedList<UserSession>();
		
		User user = userEao.getUserByUsername(username);
		UserSession session = user.getUserSession();
		session.setIpAddress("");
		Set<User> sessionUsers = session.getUsers();
		Set<User> newSessionUsers = new HashSet<User>();
		for(User sessionUser: sessionUsers) {
			if (sessionUser != user) {
				newSessionUsers.add(sessionUser); 
			}
		}
		session.setUsers(newSessionUsers);
		try {
			userEao.updateUserSession(session);
		} catch (Exception exception) {
			return;
		}
		try{
			 usList = userEao.getUserSessionByIP(sIPAddress);
		} catch (Exception exception) {
			return;
		}

		user.setUserSession(session);
		try{
			userEao.updateUser(user);
		} catch (Exception exception){
			return;
		}
		return;
	}
    
  

}
