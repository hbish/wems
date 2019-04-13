package com.wems.data;

public class UserGroupDataClass {
	
	private int userGroupId;

	private String userGroupDescription;

	private String userGroupEmail;

	private String userGroupName;
	
	//Constructor
	
	public UserGroupDataClass(){	
		
	}
	
	//Getters
	
	public int getUserGroupId(){
		return userGroupId;
	}
	
	public String getUserGroupDescription(){
		return userGroupDescription;
	}
	
	public String getUserGroupEmail(){
		return userGroupEmail;
	}
	
	public String getUserGroupName(){
		return userGroupName;
	}
	
	//Setters
	public void setUserGroupId(int userGroupId){
		this.userGroupId = userGroupId;
	}
	
	public void setDesc(String userGroupDescription){
		this.userGroupDescription = userGroupDescription;
	}
	
	public void setEmail(String userGroupEmail){
		this.userGroupEmail = userGroupEmail;
	}
	
	public void setName(String userGroupName){
		this.userGroupName = userGroupName;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getUserGroupId()+";");
		sb.append(getUserGroupName()+";");
		sb.append(getUserGroupDescription()+";");
		sb.append(getUserGroupEmail()+";");
		return sb.toString();
	}
	
}

