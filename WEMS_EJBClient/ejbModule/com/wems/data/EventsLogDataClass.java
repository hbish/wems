package com.wems.data;

import java.util.Date;

public class EventsLogDataClass {
	
	private int eventsId;

	private int eventType;
	
	private Date timestamp;
	
	private int alertId;
	
	private int deviceId;
	
	//Constructor
	
	public EventsLogDataClass(){
		
	}
	
	//Getters
	
	public int getEventsId(){
		return eventsId;
	}
	
	public int getEventsType(){
		return eventType;
	}

	public Date getTimestamp(){
		return timestamp;
	}
	
	public int getAlertId(){
		return alertId;
	}
	
	public int getDeviceId(){
		return deviceId;
	}
	
}
