package com.wems.data;

public class AlertSettingDataClass {
	int alertid;
	int deviceuid;
	String priority;
	String building;
	String level;
	String room;
	String deviceid;
	String devicetype;
	String parametername;
	String trigger;
	String ugname;
	
	public AlertSettingDataClass(){
		
	}
	
	//Getters
	public int getAlertId(){
		return this.alertid;
	}
	public String getPriority(){
		return this.priority;
	}
	
	public String getBuilding(){
		return this.building;
	}
	
	public String getLevel(){
		return this.level;
	}
	
	public String getRoom(){
		return this.room;
	}
	
	public String getDeviceId(){
		return this.deviceid;
	}
	
	public int getDeviceuid(){
		return this.deviceuid;
	}
	
	public String getDeviceType(){
		return this.devicetype;
	}
	
	public String getParameterName(){
		return this.parametername;
	}
	
	public String getTrigger(){
		return this.trigger;
	}
	
	public String getUG(){
		return this.ugname;
	}
	
	//Setters
	public void  setAlertId(int alertid){
		this.alertid = alertid;
	}
	public void setPriority(String priority){
		this.priority = priority;
	}
	
	public void setBuilding(String building){
		this.building = building;
	}
	
	public void setLevel(String level){
		this.level = level;
	}
	
	public void setRoom(String room){
		this.room = room;
	}
	
	public void setDeviceId(String deviceid){
		this.deviceid = deviceid;
	}
	
	public void setDeviceuid(int deviceuid){
		this.deviceuid = deviceuid;
	}
	
	public void setDeviceType(String devicetype){
		this.devicetype = devicetype;
	}
	
	public void setParameterName(String parametername){
		this.parametername = parametername;
	}
	
	public void setTrigger(String trigger){
		this.trigger = trigger;
	}
	
	public void setUG(String ugname){
		this.ugname = ugname;
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getPriority()+";");
		sb.append(getBuilding()+";");
		sb.append(getLevel()+";");
		sb.append(getRoom()+";");
		sb.append(getDeviceId()+";");
		sb.append(getDeviceType()+";");
		sb.append(getParameterName()+";");
		sb.append(getTrigger()+";");
		return sb.toString();
	}
}
