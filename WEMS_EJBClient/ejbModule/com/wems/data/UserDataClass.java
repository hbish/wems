package com.wems.data;

public class UserDataClass {
	
	private int userId;

	private String contactNumber;

	private String email;

	private String firstName;

	private boolean isTempPass;

	private String lastName;

	private int lockedOut;

	private int loginAttempts;

	private String passHash;

	private String username;
	
	private String userGroup;
	
	private String userType;
	private int userGroupID;
	
	
	//Constructor
	public UserDataClass(){
		
	}
	//Getters
	public int getUserId(){
		return userId;
	}
	
	public String getUserGroup(){
		return userGroup;
	}
	
	public int getUserGroupID(){
		return userGroupID;
	}
	
	public String getContactNumber(){
		return contactNumber;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public String getPassHash(){
		return passHash;
	}
	
	public String getUserName(){
		return username;
	}
	
	public String getUserType(){
		return userType;
	}
	
	public boolean getTempPass(){
		return isTempPass;
	}
	
	public int getLockedOut(){
		return lockedOut;
	}

	public int getLoginAttempts(){
		return loginAttempts;
	}
	
	//Setters
	public void setId(int id){
		this.userId = id;
	}
	
	public void setUserGroup(String userGroup){
		this.userGroup = userGroup;
	}
	
	public void setUserGroupID(int userGroupID){
		this.userGroupID = userGroupID;
	}
	
	public void setContactNumber(String contactNumber){
		this.contactNumber = contactNumber;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public void setPassHash(String passHash){
		this.passHash = passHash;
	}

	public void setUserName(String username){
		this.username = username;
	}
	
	public void setUserType(String userType){
		this.userType = userType;
	}
	
	public void setIsTempPass(boolean isTempPass){
		this.isTempPass = isTempPass;
	}
	
	public void setLockedOut(int lockedOut){
		this.lockedOut = lockedOut;
	}
	
	public void setLoginAttempts(int loginAttempts){
		this.loginAttempts = loginAttempts;
	}
	
	
	
}
