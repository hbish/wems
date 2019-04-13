package com.wems.data;

import java.util.Date;

public class SensordataDeviceusageDataClass {

	private int deviceusageuid;

	private boolean powerstatus;
	
	private Date time;
	
	private int deviceuid;
	
	//Constructor
	
	public SensordataDeviceusageDataClass(){
		
	}
	
	//Getters
	
	public int getDeviceusageuid(){
		return deviceusageuid;
	}
	
	public boolean getPowerstatus(){
		return powerstatus;
	}
	
	public Date getTime(){
		return time;
	}
	
	public int getDeviceuid(){
		return deviceuid;
	}

	
}
