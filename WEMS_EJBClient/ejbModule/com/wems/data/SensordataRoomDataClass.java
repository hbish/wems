package com.wems.data;

public class SensordataRoomDataClass {
	

	private int roomuid;

	private boolean connected;

	private String id;

	private String name;

	private double powerusage;
	
	private int numAlerts;
	
	//Constructor
	public SensordataRoomDataClass(){
		
	}
	
	//Getters
	public int getRoomuid(){
		return roomuid;
	}
	
	public boolean getConnected(){
		return connected;
	}
	
	public String getId(){
		return id;	
	}
	
	public String getName(){
		return name;
	}
	
	public double getPowerUsage(){
		return powerusage;
	}
	
	public int getNumAlerts(){
		return numAlerts;
	}
	
	//Setters
	public void setRoomuid(int roomuid){
		this.roomuid = roomuid;
	}
	
	public void setConnected(boolean connected){
		this.connected = connected;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setPowerUsage(double powerusage){
		this.powerusage = powerusage;
	}
	
	public void setNumAlerts(int numAlerts){
		this.numAlerts = numAlerts;
	}

}
