package com.wems.xml;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;


/**
 * The persistent class for a serialised xml packet
 */
public class SensorXMLData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private boolean connected;

	private boolean powerStatus;

	private double watt;
	
	LinkedList<SensorXMLDevice> deviceList;
	
	int lastIndex;

	public SensorXMLData(){
		deviceList = new LinkedList<SensorXMLDevice>();
		lastIndex = -1;
	}
	
	public SensorXMLData(String id){
		this.id = id;
		deviceList = new LinkedList<SensorXMLDevice>();
		lastIndex = -1;
	}
	
	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
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
	
	public double getWatt() {
		return watt;
	}

	public void setWatt(double watt) {
		this.watt = watt;
	}
	
	
	public void addDevice(String id){
		lastIndex++;
		deviceList.add(new SensorXMLDevice(id));
	}
	public void addDeviceConnected(boolean connected){
		deviceList.get(lastIndex).setConnected(connected);
	}
	
	public void addDevicePowerStatus(boolean power){
		deviceList.get(lastIndex).setPowerStatus(power);
	}
	
	public void addDeviceParameter(String name){
		deviceList.get(lastIndex).addParameter(name);
	}
	
	public void addDeviceParameterValue(String value){
		deviceList.get(lastIndex).addParameterValue(value);
	}
	
	public void addDeviceType(String type){
		deviceList.get(lastIndex).setType(type);
	}
	
	public void addDeviceBrand(String brand){
		deviceList.get(lastIndex).setBrand(brand);
	}
	
	public void addDeviceModel(String model){
		deviceList.get(lastIndex).setModel(model);
	}
	
	public void addDeviceSerial(String serial){
		deviceList.get(lastIndex).setSerial(serial);
	}
	
	public void addDeviceMac(String mac){
		deviceList.get(lastIndex).setMac(mac);
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Room Details - ");
		sb.append("ID:" + getID());
		sb.append(";    ");
		sb.append("Connected:" + getConnected());
		sb.append(";    ");
		sb.append("Power Status:" + getPowerStatus());
		sb.append(";    ");
		sb.append("Room Watt:" + getWatt());
		sb.append(";    "+"\n\n");
		for(int i=0;i<=lastIndex;i++){
			String temp = deviceList.get(i).toString();
			sb.append(temp + "\n");
		}

		return sb.toString();
	}
}
