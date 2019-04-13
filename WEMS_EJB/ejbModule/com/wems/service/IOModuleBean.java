package com.wems.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;

import java.util.LinkedList;
import java.util.List;

import com.wems.eao.SensorDataEao;
import com.wems.entity.*;
import com.wems.xml.*;

/**
 * Session Bean implementation class IOModuleBean
 */
@Stateless
@LocalBean
@WebService
public class IOModuleBean implements IOModuleBeanRemote {
	@EJB SensorDataEao sensorDataEao;
	@EJB EventsBean eventBean;
	@EJB AlarmsBean alarmBean;
	boolean done;
    /**
     * Default constructor. 
     */
    public IOModuleBean() {
        // TODO Auto-generated constructor stub
    }
    
    
    @Override
	public String processSensorXML(String xml) {
		// TODO Auto-generated method stub
		XMLSerialiser xmlSerialiser = new XMLSerialiser();
		if (xml != null){
		System.out.print("DEBUGBEAN - " + xml);
		System.out.print("DEBUGBEAN - " + xml.length());
		} else {
			System.out.print("DEBUGBEAN - " + "You FUCKED up");
		}
		
		SensorXMLData parsedData = xmlSerialiser.parseXML(xml);
		eventBean.processRoomEvent(parsedData);
		return "OK";
	}

    
    @Override
    public boolean addNewDevice(int deviceId, String type, String brand,
			String model, String serial, String macaddress, boolean connected,
			String roomID) {
		SensordataRoom sensordataRoom;		
		try {
			sensordataRoom = sensorDataEao.getSensordataRoom(roomID);
		} catch (Exception exception) {
			return false;
		}
		SensordataDevice sensordataDevice = new SensordataDevice();
		//sensordataDevice.setId(deviceId);
		sensordataDevice.setType(type);
		sensordataDevice.setBrand(brand);
		sensordataDevice.setModel(model);
		sensordataDevice.setSerial(serial);
		sensordataDevice.setMacaddress(macaddress);
		sensordataDevice.setConnected(connected);
		sensordataDevice.setSensordataRoom(sensordataRoom);
		try {
			sensorDataEao.addNewSensordataDevice(sensordataDevice);
		} catch (Exception exception) {
			return false;
		}
		return true;
	}
    	
    	@Override
    	public boolean addNewAddress(String establishmentId, String streetNumber,
    			String streetName, String suburb, String state, String building,
    			String level) {
    		SensordataAddress sensordataAddress = new SensordataAddress();
    		sensordataAddress.setStreetnumber(streetNumber);
    		sensordataAddress.setStreetname(streetName);
    		sensordataAddress.setSuburb(suburb);
    		sensordataAddress.setState(state);
    		sensordataAddress.setBuilding(building);
    		sensordataAddress.setLevel(level);
    		try {
    			sensorDataEao.addNewSensordataAddress(sensordataAddress);
    		} catch (Exception exception) {
    			return false;
    		}
    		return true;
    	}
	@Override
	public List<String> getEstablishmentIdList() {
	    // TODO Auto-generated method stub
	    return null;
	}
    	
    /*-- LEGACY CODE --
	@Override
	public boolean isRoomConnected(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isPowerStatus(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<String> getRoomIdList(String establishmentId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isDeviceConnected(String roomId, int deviceId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addNewAddress(String establishmentId, String streetNumber,
			String streetName, String suburb, String state, String building,
			String level) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addNewRoom(String roomId, String name, boolean connected,
			boolean powerstatus, String establishmentId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addPowerUsageSample(Date time, double watt, String roomID) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addNewDevice(int deviceId, String type, String brand,
			String model, String serial, String macaddress, boolean connected,
			String roomID) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addDeviceUsageSample(boolean powerstatus, Date time,
			int deviceId, String roomId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addNewDeviceParameter(String name, double value, Date time,
			int deviceId, String roomId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean updateRoom(String roomId, String name, boolean connected,
			boolean powerstatus, String establishmentId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean updateAddress(String establishmentId, String streetNumber,
			String streetName, String suburb, String state, String building,
			String level) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean updateDevice(String roomId, int deviceId, String type,
			String brand, String model, String serial, String macaddress,
			boolean connected) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean updateDeviceParameter(String roomId, int deviceId,
			String name, double value, Date time) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public List<String> getEstablishmentIdList() {
		// TODO Auto-generated method stub
		return null;
	}

    
//	@Override
//	public boolean isRoomConnected(String id) {
//		boolean status = false;
//		try { 
//			status = sensorDataEao.getSensordataRoom(id).getConnected();
//		} catch (Exception exception) {
//			//Return false if we cant find the room
//			return false;
//		}
//		return status;
//	}
//
//	@Override
//	public boolean isPowerStatus(String id) {
//		boolean status = false;
//		try { 
//			status = sensorDataEao.getSensordataRoom(id).getPowerstatus();
//		} catch (Exception exception) {
//			//Return false if we cant find the room
//			return false;
//		}
//		return status;
//	}
//
//	@Override
//	public List<String> getRoomIdList(String establishmentId) {
//		List<SensordataRoom> sensordataRooms;
//		List<String> roomIdList = new ArrayList<String>();
//		try {
//			sensordataRooms = sensorDataEao.getRooms();
//		} catch (Exception exception) {
//			return null;
//		}
//		for (SensordataRoom room : sensordataRooms) {
//			roomIdList.add(room.getId());
//		}
//		return roomIdList;
//	}
//
//	@Override
//	public boolean isDeviceConnected(String roomId, int deviceId) {
//		SensordataRoom room = null;
//		boolean status = false;
//		try { 
//			room = sensorDataEao.getSensordataRoom(roomId);
//			int roomUID = room.getRoomuid();
//			status = sensorDataEao.getRoomDevice(roomUID, deviceId).getConnected();
//		} catch (Exception exception) {
//			//Return false if we cant find the room
//			return false;
//		}
//		return status;
//	}
//
//	@Override
//	public boolean addNewAddress(String establishmentId, String streetNumber,
//			String streetName, String suburb, String state, String building,
//			String level) {
//		SensordataAddress sensordataAddress = new SensordataAddress();
//		sensordataAddress.setEstablishmentid(establishmentId);
//		sensordataAddress.setStreetnumber(streetNumber);
//		sensordataAddress.setStreetname(streetName);
//		sensordataAddress.setSuburb(suburb);
//		sensordataAddress.setState(state);
//		sensordataAddress.setBuilding(building);
//		sensordataAddress.setLevel(level);
//		try {
//			sensorDataEao.addNewSensordataAddress(sensordataAddress);
//		} catch (Exception exception) {
//			return false;
//		}
//		return true;
//	}
//
//	@Override
//	public boolean addNewRoom(String roomId, String name, boolean connected,
//			boolean powerstatus, String establishmentId) {
//		//Try grab the address data based on the establishment id
//		SensordataAddress sensordataAddress;
//		try {
//			sensordataAddress = sensorDataEao.getSensordataAddress(establishmentId);
//		} catch (Exception exception) {
//			return false;
//		}
//		
//		SensordataRoom sensordataRoom = new SensordataRoom();
//		sensordataRoom.setId(roomId);
//		sensordataRoom.setName(name);
//		sensordataRoom.setConnected(connected);
//		sensordataRoom.setPowerstatus(powerstatus);
//		sensordataRoom.setSensordataAddress(sensordataAddress);
//		try {
//			sensorDataEao.addNewSensordataRoom(sensordataRoom);
//		} catch (Exception exception) {
//			return false;
//		}
//		
//		return true;
//	}
//
//	@Override
//	public boolean addPowerUsageSample(Date time, double watt, String roomID) {
//		SensordataRoom sensordataRoom;
//		try {
//			sensordataRoom = sensorDataEao.getSensordataRoom(roomID);
//		} catch (Exception exception) {
//			return false;
//		}
//		
//		SensordataPowerusage sensordataPowerusage = new SensordataPowerusage();
//		sensordataPowerusage.setTime(time);
//		sensordataPowerusage.setWatt(watt);
//		sensordataPowerusage.setSensordataRoom(sensordataRoom);
//		try {
//			sensorDataEao.addNewSensordataPowerusage(sensordataPowerusage);
//		} catch (Exception exception) {
//			return false;
//		}
//		return true;
//	}
//
//	@Override
//	public boolean addNewDevice(int deviceId, String type, String brand,
//			String model, String serial, String macaddress, boolean connected,
//			String roomID) {
//		SensordataRoom sensordataRoom;
//		try {
//			sensordataRoom = sensorDataEao.getSensordataRoom(roomID);
//		} catch (Exception exception) {
//			return false;
//		}
//		SensordataDevice sensordataDevice = new SensordataDevice();
//		sensordataDevice.setId(deviceId);
//		sensordataDevice.setType(type);
//		sensordataDevice.setBrand(brand);
//		sensordataDevice.setModel(model);
//		sensordataDevice.setSerial(serial);
//		sensordataDevice.setMacaddress(macaddress);
//		sensordataDevice.setConnected(connected);
//		sensordataDevice.setSensordataRoom(sensordataRoom);
//		try {
//			sensorDataEao.addNewSensordataDevice(sensordataDevice);
//		} catch (Exception exception) {
//			return false;
//		}
//		return true;
//	}
//
//	@Override
//	public boolean addDeviceUsageSample(boolean powerstatus, Date time,
//			int deviceId, String roomId) {
//		SensordataRoom sensordataRoom;
//		try {
//			sensordataRoom = sensorDataEao.getSensordataRoom(roomId);
//		} catch (Exception exception) {
//			return false;
//		}
//		//Search for corresponding device
//		SensordataDevice roomDevice = null;
//		for (SensordataDevice device : sensordataRoom.getSensordataDevices()) {
//			if (device.getId() == deviceId) {
//				roomDevice = device;
//				break;
//			}
//		}
//		//If we didn't find a corresponding device then return false
//		if (roomDevice == null) {
//			return false;
//		}
//		//Add new usage sample
//		SensordataDeviceusage sensordataDeviceusage = new SensordataDeviceusage();
//		sensordataDeviceusage.setPowerstatus(powerstatus);
//		sensordataDeviceusage.setTime(time);
//		sensordataDeviceusage.setSensordataDevice(roomDevice);
//		try {
//			sensorDataEao.addNewSensordataDeviceusage(sensordataDeviceusage);
//		} catch (Exception exception) {
//			return false;
//		}
//		
//		return true;
//	}
//
//	@Override
//	public boolean addNewDeviceParameter(String name, double value, Date time,
//			int deviceId, String roomId) {
//		SensordataRoom sensordataRoom;
//		try {
//			sensordataRoom = sensorDataEao.getSensordataRoom(roomId);
//		} catch (Exception exception) {
//			return false;
//		}
//		//Search for corresponding device
//		SensordataDevice roomDevice = null;
//		for (SensordataDevice device : sensordataRoom.getSensordataDevices()) {
//			if (device.getId() == deviceId) {
//				roomDevice = device;
//				break;
//			}
//		}
//		//If we didn't find a corresponding device then return false
//		if (roomDevice == null) {
//			return false;
//		}
//		//Add new parameter
//		SensordataParameter sensordataParameter = new SensordataParameter();
//		sensordataParameter.setName(name);
//		sensordataParameter.setValue(value);
//		sensordataParameter.setTime(time);
//		sensordataParameter.setSensordataDevice(roomDevice);
//		try {
//			sensorDataEao.addNewSensordataParameter(sensordataParameter);
//		} catch (Exception exception) {
//			return false;
//		}
//		return true;
//	}
//	
//	@Override
//	public boolean updateRoom(String roomId, String name, boolean connected,
//			boolean powerstatus, String establishmentId) {
//		SensordataRoom sensordataRoom;
//		try {
//			sensordataRoom = sensorDataEao.getSensordataRoom(roomId);
//		} catch (Exception exception) {
//			return false;
//		}
//		SensordataAddress sensordataAddress = null;
//		if (sensordataRoom.getSensordataAddress() != null) {
//			try {
//				sensordataAddress = sensorDataEao.getSensordataAddress(establishmentId);
//			} catch (Exception exception) {
//				return false;
//			}
//		}
//		
//		sensordataRoom.setId(roomId);
//		sensordataRoom.setName(name);
//		sensordataRoom.setConnected(connected);
//		sensordataRoom.setPowerstatus(powerstatus);
//		sensordataRoom.setSensordataAddress(sensordataAddress);
//		try {
//			sensorDataEao.updateSensordataRoom(sensordataRoom);
//		} catch (Exception exception) {
//			return false;
//		}
//		return true;
//	}
//
//	@Override
//	public boolean updateAddress(String establishmentId, String streetNumber,
//			String streetName, String suburb, String state, String building,
//			String level) {
//		SensordataAddress sensordataAddress;
//		try {
//			sensordataAddress = sensorDataEao.getSensordataAddress(establishmentId);
//		} catch (Exception exception) {
//			return false;
//		}
//		sensordataAddress.setEstablishmentid(establishmentId);
//		sensordataAddress.setStreetnumber(streetNumber);
//		sensordataAddress.setStreetname(streetName);
//		sensordataAddress.setSuburb(suburb);
//		sensordataAddress.setState(state);
//		sensordataAddress.setBuilding(building);
//		sensordataAddress.setLevel(level);
//		try {
//			sensorDataEao.updateSensordataAddress(sensordataAddress);
//		} catch (Exception exception) {
//			return false;
//		}
//		return true;
//	}
//
//	@Override
//	public boolean updateDevice(String roomId, int deviceId, String type,
//			String brand, String model, String serial, String macaddress,
//			boolean connected) {
//		SensordataRoom sensordataRoom;
//		try {
//			sensordataRoom = sensorDataEao.getSensordataRoom(roomId);
//		} catch (Exception exception) {
//			return false;
//		}
//		//Search for corresponding device
//		SensordataDevice roomDevice = null;
//		for (SensordataDevice device : sensordataRoom.getSensordataDevices()) {
//			if (device.getId() == deviceId) {
//				roomDevice = device;
//				break;
//			}
//		}
//		// If we didn't find a corresponding device then return false
//		if (roomDevice == null) {
//			return false;
//		}
//		roomDevice.setId(deviceId);
//		roomDevice.setType(type);
//		roomDevice.setBrand(brand);
//		roomDevice.setModel(model);
//		roomDevice.setSerial(serial);
//		roomDevice.setMacaddress(macaddress);
//		roomDevice.setConnected(connected);
//		roomDevice.setSensordataRoom(sensordataRoom);
//		try {
//			sensorDataEao.updateSensordataDevice(roomDevice);
//		} catch (Exception exception) {
//			return false;
//		}
//		return true;
//	}
//
//	@Override
//	public boolean updateDeviceParameter(String roomId, int deviceId,
//			String name, double value, Date time) {
//		SensordataRoom sensordataRoom;
//		try {
//			sensordataRoom = sensorDataEao.getSensordataRoom(roomId);
//		} catch (Exception exception) {
//			return false;
//		}
//		//Search for corresponding device
//		SensordataDevice roomDevice = null;
//		for (SensordataDevice device : sensordataRoom.getSensordataDevices()) {
//			if (device.getId() == deviceId) {
//				roomDevice = device;
//				break;
//			}
//		}
//		// If we didn't find a corresponding device then return false
//		if (roomDevice == null) {
//			return false;
//		}
//		
//		//Search for corresponding device
//		SensordataParameter sensordataParameter = null;
//		for (SensordataParameter parameter : roomDevice.getSensordataParameters()) {
//			if (parameter.getName() == name) {
//				sensordataParameter = parameter;
//				break;
//			}
//		}
//		sensordataParameter.setValue(value);
//		sensordataParameter.setTime(time);
//		try {
//			sensorDataEao.updateSensordataParameter(sensordataParameter);
//		} catch (Exception exception) {
//			return false;
//		}
//		return true;		
//	}
//
//
//	@Override
//	public List<String> getEstablishmentIdList() {
//		List<String> establishmentIdList = new ArrayList<String>();
//		try {
//			for (SensordataAddress address : sensorDataEao
//					.getSensordataAddresses()) {
//				establishmentIdList.add(address.getEstablishmentid());
//			}
//		} catch (Exception exception) {
//			return null;
//		}
//		return establishmentIdList;
//	}
 * */
}
