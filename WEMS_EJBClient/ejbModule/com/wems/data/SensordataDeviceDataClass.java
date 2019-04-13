package com.wems.data;

public class SensordataDeviceDataClass {
	
	private int deviceuid;

	private String brand;

	private boolean connected;

	private int id;

	private String macaddress;

	private String model;

	private String serial;

	private String type;
	
	private String parameters;
	
	private String powerstatus;
	
	private int numAlerts;

	//Constructor
	
	public SensordataDeviceDataClass(){
		
	}

	//Getters
	
	public int getDeviceuid(){
		return deviceuid;
	}
	
	public String getBrand(){
		return brand;
	}
	
	public boolean getConnected(){
		return connected;
	}
	
	public int getId(){
		return id;
	}
	
	public String getModel(){
		return model;
	}
	
	public String getMacaddress(){
		return macaddress;
	}
	
	public String getSerial(){
		return serial;
	}
	
	public String getType(){
		return type;
	}
	
	public String getParameters(){
		return parameters;
	}
	
	public String getPowerStatus(){
		return powerstatus;
	}

	public int getNumAlerts(){
		return numAlerts;
	}
	
	//Setters
	public void setDeviceuid(int deviceuid){
		this.deviceuid = deviceuid;
	}
	
	public void setBrand(String brand){
		this.brand = brand;
	}
	
	public void setConnected(boolean connected){
		this.connected = connected;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setModel(String model){
		this.model = model;
	}
	
	public void setMacaddress(String mac){
		this.macaddress = mac;
	}
	
	public void setSerial(String serial){
		this.serial = serial;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setParameters(String parameters){
		this.parameters = parameters;
	}
	
	public void setPowerStatus(String powerstatus){
		this.powerstatus = powerstatus;
	}
	
	public void setNumAlerts(int numAlerts){
		this.numAlerts = numAlerts;
	}
	

}
