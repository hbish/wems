package com.wems.data;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class AlertLogDataClass {
	private int id;

	private String importance;

    @Temporal( TemporalType.TIMESTAMP)
	private Date timestamp;

	private String trigger;
	
	private String parameterName;
	
	private String device;
	
	private String room;
	
	private String floor;
	
	private String building;
	
	
	//Constructor
	public AlertLogDataClass(){
		
	}
	
	//Getters
	public int getId(){
		return id;
	}
	
	public String getImportance(){
		return importance;
	}
	
	public Date getTime(){
		return timestamp;
	}
	
	public String getTrigger(){
		return trigger;
	}

	
	public String getParameterName(){
		return parameterName;
	}
	
	public String getDevice(){
		return device;
	}
	
	public String getRoom(){
		return room;
	}
	
	public String getFloor(){
		return floor;
	}
	
	public String getBuilding(){
		return building;
	}
	
	//Setters
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setImportance(String importance){
		this.importance = importance;
	}
	
	public void setTime(Date time){
		this.timestamp= time;
	}
	
	public void setTrigger(String trigger){
		this.trigger= trigger;
	}

	
	public void setParameterName(String parameterName){
		this.parameterName= parameterName;
	}
	
	public void setDevice(String device){
		this.device= device;
	}
	
	public void setRoom(String room){
		this.room= room;
	}
	
	public void setFloor(String floor){
		this.floor= floor;
	}
	
	public void setBuilding(String building){
		this.building= building;
	}
	
	
	
}
