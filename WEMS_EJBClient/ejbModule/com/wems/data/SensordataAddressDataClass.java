package com.wems.data;

public class SensordataAddressDataClass {

	private int addressuid;

	private String building;

	private String level;

	private String state;

	private String streetname;

	private String streetnumber;

	private String suburb;
	
	private int userGroup;
	
	//Constructor
	
	public SensordataAddressDataClass(){
		
	}
	
	//Getters
	
	public int getAddressuid(){
		return addressuid;
	}

	public String getBuilding(){
		return building;
	}
	
	public String getLevel(){
		return level;
	}	
	
	public String getState(){
		return state;
	}
	
	public String getStreetname(){
		return streetname;
	}
	
	public String getStreetnumber(){
		return streetnumber;
	}
	
	public String getSuburb(){
		return suburb;
	}
	
	public int getUserGroup(){
		return userGroup;
	}

}
