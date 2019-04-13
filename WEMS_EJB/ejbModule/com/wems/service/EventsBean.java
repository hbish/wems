package com.wems.service;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;

import com.wems.eao.*;
import com.wems.xml.*;
import com.wems.entity.*;

/**
 * Session Bean implementation class EventsBean
 */
@Stateless
@LocalBean
@WebService
public class EventsBean implements EventsBeanRemote {
	@EJB SensorDataEao sensorDataEao;
	@EJB UserEao userEao;
	@EJB AlarmsBean alarmsBean;
	@EJB EmailerBean emailer;
    /**
     * Default constructor. 
     */
    public EventsBean() {
    }
    
    public void processRoomEvent(SensorXMLData roomData){
  		String roomId = roomData.getID();
  		boolean roomExist = checkRoomExist(roomId);
  		if (!roomExist){
  			addRoom(roomData);
  	 		SensordataRoom room = sensorDataEao.getSensordataRoom(roomId);
  			addAllDevice(room, roomData.getDeviceList());
  			//addevent(room addd)
  			//addalert(new room add)
  			//send message new room is discovered
  		}else{
  	 		SensordataRoom room = sensorDataEao.getSensordataRoom(roomId);
  			updateRoom(room, roomData);
  			addRoomUsageLog(room, roomData);
  			updateAllDevice(room, roomData.getDeviceList());
  		}
  		return;
  	}
    
    private void addRoomUsageLog(SensordataRoom room, SensorXMLData roomData){
    	SensordataPowerusage powerusage = new SensordataPowerusage();
    	powerusage.setWatt(roomData.getWatt());
    	powerusage.setTime(new Date());
    	powerusage.setSensordataRoom(room);
    	try {
			sensorDataEao.addNewSensordataPowerusage(powerusage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
    	return;
    }
  	private boolean checkRoomExist(String roomId){
  		int count = 0;
		try {
			count = sensorDataEao.checkRoomExistById(roomId);
		} catch (Exception e) {
			// TODO Auto-generated catch block			
			e.printStackTrace();
		}
  		if (count==0){
  			return false;
  		}
  		return true;
  	}
  	
  	private boolean checkDeviceExist(SensordataRoom room, String deviceid){
  		int count = 1;
  		try{
  			count = sensorDataEao.checkDeviceExist(room, Integer.parseInt(deviceid));
  		} catch (Exception e) {
			// TODO: handle exception
		}
  		if (count==0){
  			return false;
  		}
  		return true;
  	}
  	private void addAllDevice(SensordataRoom room, LinkedList<SensorXMLDevice> deviceList){
  		int size = deviceList.size();
  		for (int i=0; i<size; i++){
  			addDevice(room, deviceList.get(i));
  			SensordataDevice device = sensorDataEao.getDeviceByRoomByDeviceId(room, Integer.parseInt(deviceList.get(i).getID()));
  			addPowerStatus(deviceList.get(i).getPowerStatus(),device);
  			addAllParameter(device, deviceList.get(i).getParameterList());
  		}
  		return;
  	}
  	
  	private void addDevice(SensordataRoom room, SensorXMLDevice device){
  		SensordataDevice newDevice = new SensordataDevice();
  		UserGroup ug = new UserGroup();
  		newDevice.setId(Integer.parseInt(device.getID()));
  		newDevice.setType(device.getType());
  		newDevice.setBrand(device.getBrand());
  		newDevice.setModel(device.getModel());
  		newDevice.setMacaddress(device.getMac());
  		newDevice.setSerial(device.getSerial());
  		newDevice.setConnected(device.getConnected());
  		newDevice.setSensordataRoom(room);
  		try {
			ug = userEao.getUserGroupById(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print("DEBUG - NO UG");
			e.printStackTrace();
			return;
		}
  		newDevice.setUserGroupBean(ug);
  		try {
			sensorDataEao.addNewSensordataDevice(newDevice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.print("DEBUG - CANNOT ADD DEVICE");
			e.printStackTrace();
			return;
		}
  		//add to event log
  		//add to alert log
  		System.out.print("DEBUG - ADD DEVICE DONE");
  		return;
  	}
  	
  	private void addAllParameter(SensordataDevice device, LinkedList<SensorXMLParameter> paraList){
  		int size = paraList.size();
  		for (int i=0; i<size; i++){
  			addParameter(device, paraList.get(i));
  		}
  		return;
  	}
  	
  	private void addParameter(SensordataDevice device, SensorXMLParameter parameter){
  		SensordataParameter newPara = new SensordataParameter();
  		newPara.setName(parameter.getName());
  		newPara.setValueString(parameter.getValue());
  		newPara.setSensordataDevice(device);
  		try {
			sensorDataEao.addNewSensordataParameter(newPara);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
  		return;
  	}
  	
  	private void addRoom(SensorXMLData roomData){
  		SensordataRoom newRoom = new SensordataRoom();
  		List<SensordataAddress> addressList = new LinkedList<SensordataAddress>();
  		List<SensordataAddress> addressList2 = new LinkedList<SensordataAddress>();
  		SensordataAddress newAddress = new SensordataAddress();
  		newRoom.setId(roomData.getID());
  		newRoom.setName(roomData.getName());
  		newRoom.setConnected(roomData.getConnected());
  		newRoom.setPowerstatus(roomData.getPowerStatus());
  		//check address exist
  		try{
  			System.out.print("DEBUG : check address");
  			addressList = sensorDataEao.getAddressByAddress(roomData.getStNo(), roomData.getStName(), roomData.getSuburb(), roomData.getState(), roomData.getBuilding(), roomData.getLevel());
  		} catch (Exception e){
  			System.out.print("DEBUG : check address EXCEPTION");
  			return;
  		}
  		if (addressList.size()==0){
  			newAddress.setBuilding(roomData.getBuilding());
  			newAddress.setLevel(roomData.getLevel());
  			newAddress.setState(roomData.getState());
  			newAddress.setStreetname(roomData.getStName());
  			newAddress.setStreetnumber(roomData.getStNo());
  			newAddress.setSuburb(roomData.getSuburb());
  			try{
  				sensorDataEao.addNewSensordataAddress(newAddress);
  			}catch(Exception e){
  				return;
  			}
			addressList2 = sensorDataEao.getAddressByAddress(roomData.getStNo(), roomData.getStName(), roomData.getSuburb(), roomData.getState(), roomData.getBuilding(), roomData.getLevel());
			newAddress = addressList2.get(addressList2.size()-1);
  		}else{
  			newAddress = addressList.get(addressList.size()-1);
  		}
  		newRoom.setSensordataAddress(newAddress);
		try{
  			sensorDataEao.addNewSensordataRoom(newRoom);
  		}catch (Exception e){
  			e.printStackTrace();
  			return;
  		}
  		return;
  	}
  	
  	private void updateRoom(SensordataRoom room, SensorXMLData newDataRoom){
  		room.setConnected(newDataRoom.getConnected());
  		room.setPowerstatus(newDataRoom.getPowerStatus());
  		try {
			sensorDataEao.updateSensordataRoom(room);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
  		if(!room.getConnected()){
  			alarmsBean.setAlarmForRoomDC(room, "Room Disconnected", "HIGH");
  		}
  		return;
  	}
  	
  	private void updateAllDevice(SensordataRoom room, LinkedList<SensorXMLDevice> newDataDeviceList){
  		int size = newDataDeviceList.size();
  		for (int i=0; i<size; i++){
  			
  			if (checkDeviceExist(room, newDataDeviceList.get(i).getID())){
  				SensordataDevice device = sensorDataEao.getDeviceByRoomByDeviceId(room, Integer.parseInt(newDataDeviceList.get(i).getID()));
  	  			updateDevice(device, newDataDeviceList.get(i));
  	  			updateAllParameter(device, newDataDeviceList.get(i).getParameterList());
  			} else {
  	  			addDevice(room, newDataDeviceList.get(i));
  	  			SensordataDevice device = sensorDataEao.getDeviceByRoomByDeviceId(room, Integer.parseInt(newDataDeviceList.get(i).getID()));
  	  			addAllParameter(device, newDataDeviceList.get(i).getParameterList());
  			}
  			
  		}
  		return;
  	}
  	
  	private void updateDevice(SensordataDevice device, SensorXMLDevice newDeviceData){
  		boolean temp = device.getConnected();
  		device.setConnected(newDeviceData.getConnected());
  		if (!newDeviceData.getConnected()){
  			//trigger alarm
  		}
  		try {
			sensorDataEao.updateSensordataDevice(device);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
  		updateDevicePowerStatus(newDeviceData.getPowerStatus(), device);
	    System.out.print("ENTERING ALARM SET" + temp);
  		if (temp==true && newDeviceData.getConnected() == false){
  			System.out.print("DEBUG - SET ALARM");
			alarmsBean.setAlarmForDeviceDC(device, "Device has been disconneced", "HIGH");
		} else {
		    System.out.print("EXITING ALARM SET" + temp);
		}
  		return;
  	}
  	
  	private void updateAllParameter(SensordataDevice device, LinkedList<SensorXMLParameter> paraList){
 		int size = paraList.size();
  		for (int i=0; i<size; i++){
  			SensordataParameter parameter = sensorDataEao.getSensorDataParametersByDeviceByName(device,  paraList.get(i).getName());
  			updateParameter(parameter, paraList.get(i));
  		}
  		return;
  	}
  	
  	private void updateParameter(SensordataParameter parameter, SensorXMLParameter newParaData){
  		parameter.setValueString(newParaData.getValue());
  		try {
			sensorDataEao.updateSensordataParameter(parameter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
  		alarmsBean.checkAllThresholds(parameter);
  		return;
  	}

	@Override
	public void addEvent() {
		// TODO Auto-generated method stub
		
	}
	
	private void updateDevicePowerStatus(boolean power, SensordataDevice device){
		List<SensordataDeviceusage> powerList = null;
		int i = 0;
		try{
			i = sensorDataEao.checkDevicehasPowerUsage(device);
		}catch(Exception e){
			System.out.print("DEBUG - EXCEPTION check device power");
			return;
		}
		if (i>0){
			try{
				powerList = sensorDataEao.getLatestPowerStatusByDeviceId(device);
			}catch (Exception e){
				System.out.print("DEBUG - EXCEPTION powerlist");
				return;
			}
			System.out.print("DEBUG : OLD "+powerList.get(0).getPowerstatus());
			System.out.print("DEBUG : NEW "+power);
			if(powerList.get(0).getPowerstatus()!=power){
				System.out.print("DEBUG - EXCEPTION add power status");
				addPowerStatus(power, device);
			}
		}else{
			System.out.print("DEBUG - EXCEPTION add new power status");
			addPowerStatus(power, device);
		}
		return;
	}
	
	private void addPowerStatus(boolean power, SensordataDevice device){
		SensordataDeviceusage usage = new SensordataDeviceusage();
		usage.setPowerstatus(power);
		usage.setSensordataDevice(device);
		usage.setTime(new Date());
		try {
			sensorDataEao.addNewSensordataDeviceusage(usage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
