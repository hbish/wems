package com.wems.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;

import com.wems.eao.EventsLogEao;
import com.wems.eao.SensorDataEao;
import com.wems.entity.AlertLog;
import com.wems.entity.AlertSetting;
import com.wems.entity.EventsLog;
import com.wems.entity.SensordataAddress;
import com.wems.entity.SensordataDevice;
import com.wems.entity.SensordataDeviceusage;
import com.wems.entity.SensordataParameter;
import com.wems.entity.SensordataRoom;
import com.wems.entity.User;
import com.wems.entity.UserGroup;
import com.wems.data.AlertLogDataClass;
import com.wems.data.AlertSettingDataClass;
import com.wems.data.SensordataDeviceDataClass;
import com.wems.data.UserDataClass;
import com.wems.data.UserGroupDataClass;

public class DataConverter {
	@EJB SensorDataEao sensorEao;
	@EJB EventsLogEao eventsEao;
	public DataConverter(){
		
	}
	public SensordataDeviceDataClass convertDevice(SensordataDevice device){
		SensordataDeviceDataClass deviceData = new SensordataDeviceDataClass();
		Set<SensordataParameter> pList = new HashSet<SensordataParameter>();
		StringBuffer temp = new StringBuffer();
		pList = device.getSensordataParameters();
		Iterator<SensordataParameter> itr = pList.iterator();
		while (itr.hasNext()){
			SensordataParameter p = itr.next();
			temp.append(p.getName()+": "+p.getValue()+"<br />");
		}
		deviceData.setDeviceuid(device.getDeviceuid());
		deviceData.setId(device.getId());
		deviceData.setBrand(device.getBrand());
		deviceData.setConnected(device.getConnected());
		deviceData.setMacaddress(device.getMacaddress());
		deviceData.setModel(device.getModel());
		deviceData.setSerial(device.getSerial());
		deviceData.setType(device.getType());
        System.out.print(temp.toString());
		String parameters = temp.toString();
		deviceData.setParameters(parameters);
		return deviceData;
	}
	
	public AlertLogDataClass convertAlertLog(AlertLog alertLog) {
		AlertLogDataClass alertData = new AlertLogDataClass();
		alertData.setId(alertLog.getAlertsId());
		alertData.setImportance(alertLog.getAlertPriority());
		alertData.setTime(alertLog.getTimestamp());
		AlertSetting alertSetting = alertLog.getAlertSetting();
		if (alertSetting != null) {
			if (alertSetting.getMinThresholdEnabled()) {
				alertData.setTrigger("Less Than"
						+ alertSetting.getMinThresholdValue());
			} else if (alertSetting.getMaxThresholdEnabled()) {
				alertData.setTrigger("Greater Than"
						+ alertSetting.getMaxThresholdValue());
			} else {
				alertData.setTrigger("Equals"
						+ alertSetting.getExactThresholdValue());
			}
			SensordataParameter parameter = alertSetting
					.getSensordataParameter();
			if (parameter != null) {
				alertData.setParameterName(parameter.getName());
				SensordataDevice device = parameter.getSensordataDevice();
				if (device != null) {
					alertData
							.setDevice(device.getType() + " " + device.getId());
					SensordataRoom room = device.getSensordataRoom();
					if (room != null) {
						alertData.setRoom(room.getName());
						SensordataAddress address = room.getSensordataAddress();
						if (address != null) {
							alertData.setBuilding(address.getBuilding());
							alertData.setFloor(address.getLevel());
						}
					}
				}
			}
		} else {
			Set<EventsLog> eventSet = alertLog.getEventsLogs();
			Iterator<EventsLog> eItr = eventSet.iterator();
			alertData.setParameterName("Connection Status");
			while (eItr.hasNext()) {
				EventsLog event = eItr.next();
				SensordataDevice device2 = event.getSensordataDevice();
				SensordataRoom room2 = event.getSensordataRoom();
				if (device2 != null) {
					alertData.setTrigger("Device Disconnected");
					alertData.setDevice(device2.getType() + " "
							+ device2.getId());
					SensordataRoom room = device2.getSensordataRoom();
					if (room != null) {
						alertData.setRoom(room.getName());
						SensordataAddress address = room.getSensordataAddress();
						if (address != null) {
							alertData.setBuilding(address.getBuilding());
							alertData.setFloor(address.getLevel());
						}
					}
				}else if (room2!=null){
					alertData.setTrigger("Room Disconnected");
					alertData.setDevice("N/A");
						alertData.setRoom(room2.getName());
						SensordataAddress address = room2.getSensordataAddress();
						if (address != null) {
							alertData.setBuilding(address.getBuilding());
							alertData.setFloor(address.getLevel());
						}
				}
			}
		}
		return alertData;
	}
	
	public AlertSettingDataClass convertAlertSetting(AlertSetting alertsetting){
		AlertSettingDataClass alertData = new AlertSettingDataClass();
		alertData.setUG(alertsetting.getUserGroup().getUserGroupName());
		alertData.setPriority(alertsetting.getAlertPriority());
		alertData.setAlertId(alertsetting.getAlertId());
		SensordataParameter parameter = alertsetting.getSensordataParameter();
		SensordataDevice device = new SensordataDevice();
		SensordataRoom room = new SensordataRoom();
		SensordataAddress address = new SensordataAddress();
		if (parameter!=null){
			alertData.setParameterName(parameter.getName());
			device = parameter.getSensordataDevice();
			if (device!=null){
				alertData.setDeviceId(Integer.toString(device.getId()));
				alertData.setDeviceType(device.getType());
				alertData.setDeviceuid(device.getDeviceuid());
				room = device.getSensordataRoom();
				if (room!=null){
					alertData.setRoom(room.getId());
					address = room.getSensordataAddress();
					if (address!=null){
						alertData.setBuilding(address.getBuilding());
						alertData.setLevel(address.getLevel());
					}
				}
			}
		}
		if (alertsetting.getMinThresholdEnabled()&&(alertsetting.getMaxThresholdEnabled())){
			alertData.setTrigger("Outside the range of " +alertsetting.getMinThresholdValue() + " to "
					+alertsetting.getMaxThresholdValue() );
		}else if (alertsetting.getMinThresholdEnabled()){
			alertData.setTrigger("Less Than " +alertsetting.getMinThresholdValue());
		}else if (alertsetting.getMaxThresholdEnabled()){
			alertData.setTrigger("Greater Than " +alertsetting.getMaxThresholdValue());
		}else{
			alertData.setTrigger("Equals " +alertsetting.getExactThresholdValue());
		}
		return alertData;
	}
	
	public UserGroupDataClass convertUserGroup(UserGroup ug){
		UserGroupDataClass ugData = new UserGroupDataClass();
		ugData.setUserGroupId(ug.getUserGroupId());
		ugData.setName(ug.getUserGroupName());
		ugData.setDesc(ug.getUserGroupDescription());
		ugData.setEmail(ug.getUserGroupEmail());
		return ugData;
	}
	
	public UserDataClass convertUser(User u){
		UserDataClass uData = new UserDataClass();
		uData.setContactNumber(u.getContactNumber());
		uData.setEmail(u.getEmail());
		uData.setFirstName(u.getFirstName());
		uData.setId(u.getUserId());
		uData.setIsTempPass(u.getIsTempPass());
		uData.setLastName(u.getLastName());
		uData.setLockedOut(u.getLockedOut());
		uData.setLoginAttempts(u.getLoginAttempts());
		uData.setPassHash(u.getPassHash());
		uData.setUserName(u.getUsername());				
		uData.setUserGroup(u.getUserGroupBean().getUserGroupName());
		uData.setUserGroupID(u.getUserGroupBean().getUserGroupId());
		
		uData.setUserGroup(u.getUserGroupBean().getUserGroupName());
		uData.setUserGroup(u.getUserGroupBean().getUserGroupName());
		uData.setUserType(u.getUserType().getUserTypeName());
		return uData;
	}
}
