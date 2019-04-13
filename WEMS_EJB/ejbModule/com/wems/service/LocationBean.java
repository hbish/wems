package com.wems.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.SortedSet;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.wems.data.SensordataDeviceDataClass;
import com.wems.data.SensordataRoomDataClass;
import com.wems.eao.EventsLogEao;
import com.wems.eao.SensorDataEao;
import com.wems.entity.*;

import javax.jws.WebService;

/**
 * Session Bean implementation class LocationBean
 */
@Stateless
@LocalBean
@WebService
public class LocationBean implements LocationBeanRemote {
	@EJB SensorDataEao sensorEao;
	@EJB AlarmsBean alarmBean;
	@EJB EventsLogEao eventsEao;
    /**
     * Default constructor. 
     */
    public LocationBean() {
        // TODO Auto-generated constructor stub
    }

    //Get a List of devices from a UserGroup
    @Override
    public List<SensordataDeviceDataClass> getDevices(int userGroupID){
    	List<SensordataDevice> sddList = sensorEao.getDevicesByUserGroup(userGroupID);
    	List<SensordataDeviceDataClass> dataClassList = new ArrayList<SensordataDeviceDataClass>();
    	for(SensordataDevice sd : sddList ) {
    		   SensordataDeviceDataClass sddata = new SensordataDeviceDataClass();

    		   sddata.setDeviceuid(sd.getDeviceuid());
    		   sddata.setBrand(sd.getBrand());
    		   sddata.setType(sd.getType());
    		   sddata.setModel(sd.getModel());
    		   sddata.setSerial(sd.getSerial());
    		   sddata.setMacaddress(sd.getMacaddress());

    		   dataClassList.add(sddata);   
    		}

    		return dataClassList;
    }
    
  //Get List of device IDs from UserGroup
  	@Override
  	public List<Integer> getDevicesIDs(int userGroup){
  		List<SensordataDevice> allDevices = sensorEao.getDevicesByUserGroup(userGroup);
  		List<Integer> devicesIDs = new ArrayList<Integer>();
  		for (SensordataDevice device: allDevices){
  			devicesIDs.add(device.getDeviceuid());
  		}
  		return devicesIDs;
  	}
    
    //Get a List of building names
    @Override
	public List<String> getBuildings(){
		List<String> buildings = sensorEao.getAllBuildingNames();
		return buildings;
	}
	
    //Get a List of level names given building name
    @Override
    public List<String> getLevels(String building){
		List<String> levels = sensorEao.getLevelsByBuildingName(building);
		return levels;
	}
	
    //Get List of Room Names related to a selected building and level
  	@Override
	public List<String> getRooms(String building, String level){
		List<String> rooms = new LinkedList<String>();
		Set<SensordataRoom> roomData = new HashSet<SensordataRoom>();
		List<SensordataAddress> addresses = new LinkedList<SensordataAddress>();
		try{		
			addresses = sensorEao.getAddressbyBuildingandLevel(building, level);
		} catch (Exception exception)
		{
			return rooms;
		}
		Iterator<SensordataAddress> itr = addresses.iterator();
	   	while(itr.hasNext()){
	   		roomData.addAll(itr.next().getSensordataRooms());
	   	}
	   	Iterator<SensordataRoom> itr2 = roomData.iterator();
	   	while(itr2.hasNext()){
	   		rooms.add(itr2.next().getId());
	   	}
		return rooms;
	}
  	
  	//Get List of all room details
  	@Override
  	public List<SensordataRoomDataClass> getRoomDetails(String building, String level){
  		List<SensordataRoomDataClass> rooms = new LinkedList<SensordataRoomDataClass>();
  		Set<SensordataRoom> roomData = new HashSet<SensordataRoom>();
		List<SensordataAddress> addresses = new LinkedList<SensordataAddress>();
		try{		
			addresses = sensorEao.getAddressbyBuildingandLevel(building, level);
		} catch (Exception exception)
		{
			return rooms;
		}
		Iterator<SensordataAddress> itr = addresses.iterator();
	   	while(itr.hasNext()){
	   		roomData.addAll(itr.next().getSensordataRooms());
	   	}
	   	Iterator<SensordataRoom> itr2 = roomData.iterator();
	   	while(itr2.hasNext()){
	   		SensordataRoom currentRoom = itr2.next();
	   		SensordataRoomDataClass currentRoomData = new SensordataRoomDataClass();
	   		currentRoomData.setConnected(currentRoom.getConnected());
	   		currentRoomData.setId(currentRoom.getId());
	   		currentRoomData.setName(currentRoom.getName());
	   		currentRoomData.setPowerUsage(getRoomPowerUsage(currentRoom.getId()));
	   		currentRoomData.setRoomuid(currentRoom.getRoomuid());
	   		currentRoomData.setNumAlerts(alarmBean.findNumAlerts(currentRoom));
	   		rooms.add(currentRoomData);
	   	}
	   	return rooms;
  	}
  	
  	
  
  	 //Get List of Room Names related to a selected building and level
  	@Override
	public List<Integer> getRoomsAlerts(String building, String level){
		List<Integer> numAlerts = new LinkedList<Integer>();
		Set<SensordataRoom> roomData = new HashSet<SensordataRoom>();
		List<SensordataAddress> addresses = new LinkedList<SensordataAddress>();
		try{		
			addresses = sensorEao.getAddressbyBuildingandLevel(building, level);
		} catch (Exception exception)
		{
			return numAlerts;
		}
		Iterator<SensordataAddress> itr = addresses.iterator();
	   	while(itr.hasNext()){
	   		roomData.addAll(itr.next().getSensordataRooms());
	   	}
	   	Iterator<SensordataRoom> itr2 = roomData.iterator();
	   	while(itr2.hasNext()){
	   		numAlerts.add(alarmBean.findNumAlerts(itr2.next()));
	   	}
		return numAlerts;
	}
  	
  //Get List of Room Names related to a selected building and level
  	@Override
	public List<String> getRoomsDC(String building, String level){
		List<String> rooms = new LinkedList<String>();
		Set<SensordataRoom> roomData = new HashSet<SensordataRoom>();
		List<SensordataAddress> addresses = new LinkedList<SensordataAddress>();
		try{		
			addresses = sensorEao.getAddressbyBuildingandLevel(building, level);
		} catch (Exception exception)
		{
			return rooms;
		}
		Iterator<SensordataAddress> itr = addresses.iterator();
	   	while(itr.hasNext()){
	   		roomData.addAll(itr.next().getSensordataRooms());
	   	}
	   	Iterator<SensordataRoom> itr2 = roomData.iterator();
	   	while(itr2.hasNext()){
	   		if(itr2.next().getConnected())
	   		{
	   			rooms.add("YES");
	   		} else {
	   			rooms.add("NO");
	   		}
	   	}
		return rooms;
	}
	
	//Get List of Devices Names related to a selected room
	@Override
	public List<String> getDeviceNames(String room){
		List<String> devices = new LinkedList<String>();
		Set<SensordataDevice> deviceData = new HashSet<SensordataDevice>();
		SensordataRoom roomData = new SensordataRoom();
		try{		
			roomData = sensorEao.getSensordataRoom(room);
		} catch (Exception exception)
		{
			return devices;
		}
	   	deviceData = roomData.getSensordataDevices();
	   	Iterator<SensordataDevice> itr = deviceData.iterator();
	   	while(itr.hasNext()){
	   		devices.add(itr.next().getType());
	   	}
		return devices;
	}
	
	//Get List of Devices Names related to a selected room
	@Override
	public List<SensordataDeviceDataClass> getDeviceDetails(String room){
		List<SensordataDeviceDataClass> devices = new LinkedList<SensordataDeviceDataClass>();
		Set<SensordataDevice> deviceData = new HashSet<SensordataDevice>();
		SensordataRoom roomData = new SensordataRoom();
		try{		
			roomData = sensorEao.getSensordataRoomByName(room);
		} catch (Exception exception)
		{
			return devices;
		}
	   	deviceData = roomData.getSensordataDevices();
	   	Iterator<SensordataDevice> itr = deviceData.iterator();
	   	while(itr.hasNext()){
	   		DataConverter converter = new DataConverter();
	   		SensordataDevice device = itr.next();
	   		SensordataDeviceDataClass newData = converter.convertDevice(device);
	   		List<EventsLog> list = eventsEao.getEventByDeviceId(device);
			newData.setNumAlerts(list.size());
			List<SensordataDeviceusage> powerList = null;
			int i = 0;
			try{
				i = sensorEao.checkDevicehasPowerUsage(device);
			}catch(Exception e){
				System.out.print("DEBUG - EXCEPTION check device power");
				return devices;
			}
			if (i>0){
				try{
					powerList = sensorEao.getLatestPowerStatusByDeviceId(device);
				}catch (Exception e){
					System.out.print("DEBUG - EXCEPTION powerlist");
					return devices;
				}
				if(powerList.get(0).getPowerstatus()){
					newData.setPowerStatus("ON");
				}else{
					newData.setPowerStatus("OFF");
				}
			}
	   		devices.add(newData);
	   	}
		return devices;
	}
	
	//Get Device Object given the device id
	@Override
	public SensordataDeviceDataClass getDevice(int deviceuid){
		SensordataDeviceDataClass device = new SensordataDeviceDataClass();
		SensordataDevice deviceEntity = sensorEao.getDeviceById(deviceuid);
		DataConverter converter = new DataConverter();
		device = (converter.convertDevice(deviceEntity));
		List<EventsLog> list = eventsEao.getEventByDeviceId(deviceEntity);
		device.setNumAlerts(list.size());
		List<SensordataDeviceusage> powerList = null;
		try{
			powerList = sensorEao.getLatestPowerStatusByDeviceId(deviceEntity);
		}catch (Exception e){
			device.setPowerStatus("N/A");
		}
		if(powerList!=null){
			if(powerList.get(0).getPowerstatus()){
				device.setPowerStatus("ON");
			}else{
				device.setPowerStatus("OFF");
			}
		}
		return device;
	}

	//Get all Power Usage for all buildings
	@Override
	public double getAllPowerUsage(){
		List<SensordataRoom> allRooms = sensorEao.getRooms();
		double powerUsage = 0;
		for (int i=0; i<allRooms.size(); i++){
				List<Double> roomPowerUsage = sensorEao.getLatestPowerUsageByRoomId(allRooms.get(i));
				if (roomPowerUsage==null){
					return -1;
				}
				powerUsage += roomPowerUsage.get(0);
		}
		return powerUsage;
		
	}

	//Get Power Usage for all rooms in a usergroup/
//	@Override
//	public double getUserGroupPowerUsage(int usergroupid){
//		List<SensordataRoom> allRooms = sensorEao.getRoomsByUserGroup();
//		double powerUsage = 0;
//		for (int i=0; i<allRooms.size(); i++){
//				List<Double> roomPowerUsage = sensorEao.getLatestPowerUsageByRoomId(allRooms.get(i));
//				if (roomPowerUsage==null){
//					return -1;
//				}
//				powerUsage += roomPowerUsage.get(0);
//		}
//		return powerUsage;
//	}
	
	
	//Get Power Usage for a given building
    @Override
	public double getBuildingPowerUsage(String building){
		double powerUsage = 0;
		List<SensordataAddress> addressList = sensorEao.getAddressbyBuilding(building);
		for (int i=0; i<addressList.size(); i++){
			Set<SensordataRoom> roomList= addressList.get(i).getSensordataRooms();
			Iterator<SensordataRoom> itr = roomList.iterator();
			while (itr.hasNext()){
				List<Double> roomPowerUsage = sensorEao.getLatestPowerUsageByRoomId(itr.next());
				if (roomPowerUsage.size()!=0){
				powerUsage += roomPowerUsage.get(0);
				}
			}

		}
		return powerUsage;
	}
	
	//Get Power Usage for a given building and level
    @Override
	public double getLevelPowerUsage(String building, String level){
		double powerUsage = 0;
		//get list of addressuid given buildingName
		List<SensordataAddress> addressList = sensorEao.getAddressbyBuildingandLevel(building, level);
		if (addressList.size()==0){
			return -1;
		}
		for (int i=0; i<addressList.size(); i++){
			//get room uid
			Set<SensordataRoom> roomList= addressList.get(i).getSensordataRooms();
			Iterator<SensordataRoom> itr = roomList.iterator();
			while (itr.hasNext()){
				List<Double> roomPowerUsage = sensorEao.getLatestPowerUsageByRoomId(itr.next());
				if (roomPowerUsage.size()!=0){
					powerUsage += roomPowerUsage.get(0);
				}
			}
		}
		return powerUsage;
	}
    
	//Get Power Usage for a given room
    @Override
    public double getRoomPowerUsage(String roomid){
    	SensordataRoom room = sensorEao.getSensordataRoom(roomid);
    	List<Double> roomPowerUsage = sensorEao.getLatestPowerUsageByRoomId(room);
    	if (roomPowerUsage.size()!=0){
    		return roomPowerUsage.get(0);
		}
		return 0;
	}
    
    @Override
    public List<String> getPList(int deviceuid){
    	List<String> plist = new LinkedList<String>();
    	SensordataDevice deviceEntity = sensorEao.getDeviceById(deviceuid);
    	Set<SensordataParameter> p = deviceEntity.getSensordataParameters();
    	Iterator<SensordataParameter> itr = p.iterator();
    	while (itr.hasNext()){
    		plist.add(itr.next().getName());
    	}
    	return plist;
    	
    }

	@Override
	public List<String> getDeviceNames(int userGroupID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void removeSensordataDevice(int deviceuid){
		try {
			SensordataDevice device = sensorEao.getDeviceById(deviceuid);
			sensorEao.removeSensordataDevice(device);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
