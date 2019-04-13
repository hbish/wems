package com.wems.xml;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;

import com.wems.entity.*;


/**
 * The persistent class for a serialised xml packet
 */
public class SensorXMLData {

	private String id;
	
	private String name;
	
	private String streetno;
	
	private String streetname;
	
	private String suburb;
	
	private String state;
	
	private String building;
	
	private String level;
	
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getStNo() {
		return streetno;
	}

	public void setStNo(String streetno) {
		this.streetno = streetno;
	}
	
	public String getStName() {
		return streetname;
	}

	public void setStName(String streetname) {
		this.streetname = streetname;
	}
	
	public String getSuburb() {
		return suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
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
	
	public LinkedList<SensorXMLDevice> getDeviceList(){
		return deviceList;
	}
	
	public int getDeviceNumber(){
		return lastIndex;
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Room Details - ");
		sb.append("ID:" + getID());
		sb.append(";    ");
		sb.append("NAME:" + getName());
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
