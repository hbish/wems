package com.wems.data;

public class SensordataParameterDataClass {

	private int parameteruid;

	private String name;

    private String value;
    
    private int deviceuid;
    
	//Constructor
    
	public SensordataParameterDataClass(){
		
	}
	
	//Getters
	
	public int getParameteruid(){
		return parameteruid;
	}
	
	public String getName(){
		return name;
	}
	
	public String getValue(){
		return value;
	}
	
	public int getDeviceuid(){
		return deviceuid;
	}
	
}
