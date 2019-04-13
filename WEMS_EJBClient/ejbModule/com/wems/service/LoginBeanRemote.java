package com.wems.service;
import javax.ejb.Remote;

@Remote
public interface LoginBeanRemote {
	
	 public boolean checkPassword(String username, String password);
	 public boolean isUser(String username);
	 public void createSession(String username, String sIPAddress);
	 public void removeSession(String username, String sIPAddress);
	 public String getUserNameByIP(String ip);
	 public int getUserGroupIDByName(String name);
}
