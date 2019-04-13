package com.wems.service;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import javax.ejb.Remote;

import com.wems.data.SensordataDeviceDataClass;
import com.wems.data.SensordataRoomDataClass;
import java.util.Set;

@Remote
public interface LocationBeanRemote {

	public List<SensordataDeviceDataClass> getDevices(int userGroupID);
	public List<String> getBuildings();
	public List<String> getLevels(String building);
	public List<String> getRooms(String building, String level);
	public List<String> getDeviceNames(String room);
	public SensordataDeviceDataClass getDevice(int deviceuid);
	public double getBuildingPowerUsage(String building);
	public double getLevelPowerUsage(String building, String level);
	public double getRoomPowerUsage(String roomid);
	public double getAllPowerUsage();
	public List<String> getRoomsDC(String building, String level);
	public List<Integer> getRoomsAlerts(String building, String level);
	public List<SensordataRoomDataClass> getRoomDetails(String building, String level);
	public List<SensordataDeviceDataClass> getDeviceDetails(String room);
	public List<String> getPList(int deviceuid);
	public List<Integer> getDevicesIDs(int userGroup);
	List<String> getDeviceNames(int userGroupID);
	void removeSensordataDevice(int datadeviceuid);
	
}
