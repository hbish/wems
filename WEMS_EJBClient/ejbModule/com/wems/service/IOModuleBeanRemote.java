package com.wems.service;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

@Remote
public interface IOModuleBeanRemote {
//	WEMS-IOM-F-01 Not possible sensors push data we dont pull data from sensors
//	WEMS-IOM-F-02 Not possible since we dont pull data 
//	WEMS-IOM-F-03 .... same as above
//	WEMS-IOM-F-04 
	public String processSensorXML(String xml);
//	WEMS-IOM-F-05 Not possible, we transact using XML so no API needed (avoids coupling)

	
//	LEGACY CODE
//	//Getters
//public List<String> getRoomIdList(String establishmentId);
	public List<String> getEstablishmentIdList();
//	public boolean isRoomConnected(String id);
//	public boolean isPowerStatus(String id);
//	public boolean isDeviceConnected(String roomId, int deviceId);
//	
//	//Setters
	public boolean addNewAddress(String establishmentId, String streetNumber, String streetName, String suburb, String state, String building, String level);
//	public boolean addNewRoom(String roomId, String name, boolean connected, boolean powerstatus, String establishmentId);
//	public boolean addPowerUsageSample(Date time, double watt, String roomID);
	public boolean addNewDevice(int deviceId, String type, String brand, String model, String serial, String macaddress, boolean connected, String roomId);
//	public boolean addDeviceUsageSample(boolean powerstatus, Date time, int deviceId, String roomId);
//	public boolean addNewDeviceParameter(String name, double value, Date time, int deviceId, String roomId);
//	public boolean updateAddress(String establishmentId, String streetNumber, String streetName, String suburb, String state, String building, String level);
//	public boolean updateRoom(String roomId, String name, boolean connected, boolean powerstatus, String establishmentId);
//	public boolean updateDevice(String roomId, int deviceId, String type, String brand, String model, String serial, String macaddress, boolean connected);
//	public boolean updateDeviceParameter(String roomId, int deviceId, String name, double value, Date time);
}
