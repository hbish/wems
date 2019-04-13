package com.wems.data;

public class ConfigurationDataClass {
	
	private int configurationsId;

	private String currentUsers;

	private int maxUsers;

	private String systemName;

	//Constructor
	
	public ConfigurationDataClass(){
		
	}
	
	//Getters
	
	public int getConfigurationsId(){
		return configurationsId;
	}
	
	public String getCurrentUsers(){
		return currentUsers;
	}

	public int getMaxUsers(){
		return maxUsers;
	}
	
	public String getSystemName(){
		return systemName;
	}
}
