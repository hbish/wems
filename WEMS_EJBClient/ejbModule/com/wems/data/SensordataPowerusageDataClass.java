package com.wems.data;

import java.util.Date;

public class SensordataPowerusageDataClass {
	
	private int powerusageuid;

	private Date time;
	
	private double watt;
	
	private int roomuid;
	
	//Constructor
	
	public SensordataPowerusageDataClass(){
		
	}
	
	//Getters
	
	public int getPowerusageuid(){
		return powerusageuid;
	}
	
	public Date getTime(){
		return time;
	}
	
	public double getWatt(){
		return watt;
	}
	
	public int getroomuid(){
		return roomuid;
	}

}
