package com.wems.xml;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;


/**
 * The persistent class for a serialised xml device data
 */
public class SensorXMLDevice {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String type;
	
	private String brand;
	
	private String model;
	
	private String serial;
	
	private String mac;
	
	private boolean connected;

	private boolean powerStatus;

	private double watt;
	
	private LinkedList<SensorXMLParameter> parameterList;
	
	int lastIndex;

	public SensorXMLDevice(){
		parameterList = new LinkedList<SensorXMLParameter>();
		lastIndex = -1;
	}
	
	public SensorXMLDevice(String id){
		parameterList = new LinkedList<SensorXMLParameter>();
		lastIndex = -1;
		this.id = id;
	}
	
	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
	
	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}
		
	public boolean getConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}
	
	public boolean getPowerStatus() {
		return powerStatus;
	}

	public void setPowerStatus(boolean powerStatus) {
		this.powerStatus = powerStatus;
	}
	
	public void addParameter(String name){
			lastIndex++;
			parameterList.add(new SensorXMLParameter(name));
	}
	
	public void addParameterValue(String value){
		parameterList.get(lastIndex).setValue(value);
	}
	
	public LinkedList<SensorXMLParameter> getParameterList(){
		return parameterList;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Device Details - ");
		sb.append("ID:" + getID());
		sb.append("\n");
		sb.append("Connected:" + getConnected());
		sb.append("\n");
		sb.append("PowerStatus:" + getPowerStatus());
		sb.append("\n");
		sb.append("Type:" + getType());
		sb.append("\n");
		sb.append("Brand" + getBrand());
		sb.append(";    ");
		sb.append("Model:" + getModel());
		sb.append("\n");
		sb.append("Serial:" + getSerial());
		sb.append("\n");
		sb.append("MacAddress:" + getMac());
		sb.append("\n");
		for(int i = 0; i<=lastIndex; i++){
			String temp = parameterList.get(i).toString();
			sb.append(temp);
		}
		sb.append("\n");
		
		return sb.toString();
	}

	
}