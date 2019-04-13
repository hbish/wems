package com.wems.service;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.eclipse.persistence.annotations.Convert;

import com.wems.data.AlertLogDataClass;
import com.wems.data.AlertSettingDataClass;
import com.wems.data.EventsLogDataClass;
import com.wems.data.SensordataDeviceDataClass;
import com.wems.eao.AlertConfigEao;
import com.wems.eao.EventsLogEao;
import com.wems.eao.SensorDataEao;
import com.wems.eao.UserEao;
import com.wems.entity.AlertLog;
import com.wems.entity.AlertSetting;
import com.wems.entity.EventsLog;
import com.wems.entity.SensordataDevice;
import com.wems.entity.SensordataParameter;
import com.wems.entity.SensordataPowerusage;
import com.wems.entity.SensordataRoom;
import com.wems.entity.User;
import com.wems.entity.UserGroup;
import com.wems.xml.*;

/**
 * Session Bean implementation class AlarmsBean
 */
@Stateless
@LocalBean
@WebService
public class AlarmsBean implements AlarmsBeanRemote {
	@EJB SensorDataEao sensorDataEao;
	@EJB EventsLogEao eventEao;
	@EJB AlertConfigEao alertEao;
	@EJB EmailerBean emailer;
	@EJB UserEao userEao;
    /**
     * Default constructor. 
     */
    public AlarmsBean() {
        // TODO Auto-generated constructor stub
    }

    //Get User's Alert Settings ID
    @Override
    public List<Integer> getUsersAlarmsID(String username){
    	List<Integer> idList = new LinkedList<Integer>();
    	User user = userEao.getUserByUsername(username);
    	if (user!=null){
    		UserGroup usergroup = user.getUserGroupBean();
	  		if (usergroup!=null){
	  		}
	  		 idList = alertEao.getAllAlertSettingsIDByUserGroup(usergroup);
    	}
    	return idList;
    	
    }
    
    public int findNumAlerts(SensordataRoom room){
    	int numAlerts = 0;
    	List<EventsLog> list = null;
    	Set<SensordataDevice> deviceList = room.getSensordataDevices();
    	Iterator<SensordataDevice> itr = deviceList.iterator();
    	while(itr.hasNext()){
    		 list = eventEao.getEventByDeviceId(itr.next());
    		 numAlerts+=list.size();
    	}
    	return numAlerts;
    }
    
    public void checkRoomsDC(){
    	// get list of all rooms
    	List<SensordataRoom> roomList = sensorDataEao.getRooms();
    	// loop thru rooms
    	for(int i = 0; i < roomList.size(); i++ ){
    		SensordataRoom currentRoom = roomList.get(i);
    		if (currentRoom.getConnected()){
    			List<Date> latestTime = sensorDataEao.getLatestPowerTimeByRoomId(currentRoom);
    			Date currentTime = new Date();
    			long diff = currentTime.getTime() - latestTime.get(latestTime.size()-1).getTime();
    			long secDiff = diff / (1000);
    			if(secDiff > 20){
    				setAlarmForRoomDC(currentRoom, "Room Disconnected", "HIGH");
    				currentRoom.setConnected(false);
    				try {
						sensorDataEao.updateSensordataRoom(currentRoom);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						return;
					}
    			}
    		}
    	}
    	return;
    }
    
    //Get Alert Setting given its ID
    @Override
    public AlertSettingDataClass getAlarmByID(int id){
    	AlertSettingDataClass alarmData = new AlertSettingDataClass();
    	DataConverter converter = new DataConverter();
    	AlertSetting alertsetting = alertEao.getAllAlertSettingsById(id);
    	if (alertsetting!=null){
    		alarmData = converter.convertAlertSetting(alertsetting);
    	}
    	return alarmData;
    }
    
    //Get User's Alert Settings
    @Override
    public List<AlertSettingDataClass> getUsersAlarms(String username){
    	List<AlertSettingDataClass> alarmList = new LinkedList<AlertSettingDataClass>();
    	DataConverter converter = new DataConverter();
    	User user = userEao.getUserByUsername(username);
    	if (user!=null){
    		UserGroup usergroup = user.getUserGroupBean();
	  		if (usergroup!=null){
	  			Set<AlertSetting> alertSettingList = usergroup.getAlertSettings();
	  			Iterator<AlertSetting> itr = alertSettingList.iterator();
		  		while(itr.hasNext()){
		  			alarmList.add(converter.convertAlertSetting(itr.next()));
		  		}
	  		}
    	}
    	return alarmList;
    }
    
    //Get user's alarms given username
  	@Override
  	public List<AlertLogDataClass> getUserNotifications(String username) {
  		List<AlertLogDataClass> alertDataList = new LinkedList<AlertLogDataClass>();
  		List<AlertLog> alertLogList = new LinkedList<AlertLog>();
  		DataConverter converter = new DataConverter();
  		User user = userEao.getUserByUsername(username);
  		if(user!=null){
	  		UserGroup usergroup = user.getUserGroupBean();
	  		if (usergroup!=null){
		  		Set<AlertSetting> alertSettingList = usergroup.getAlertSettings();
		  		Iterator<AlertSetting> itr = alertSettingList.iterator();
		  		while(itr.hasNext()){
		  			alertLogList.addAll(eventEao.getAlertsByAlertSetting(itr.next()));
		  		}
		  		Iterator<AlertLog> itr2 = alertLogList.iterator();
		  		while(itr2.hasNext()){
		  			alertDataList.add(converter.convertAlertLog(itr2.next()));
		  		}
	  		}
  		}
  		return alertDataList;
  	}
  	
    @Override
    public List<AlertLogDataClass> getUserEvents(String username) {
        List<AlertLogDataClass> alertDataList = new LinkedList<AlertLogDataClass>();
        List<AlertLog> alertsList = new LinkedList<AlertLog>();
        List<EventsLog> eventsList = new LinkedList<EventsLog>();
        Set<SensordataRoom> roomsList = null;
        Set<SensordataDevice> deviceList = null;
        DataConverter converter = new DataConverter();
        User user = userEao.getUserByUsername(username);
        System.out.print("DEBUG: " + username);
        if(user!=null){
            UserGroup usergroup = user.getUserGroupBean();
            System.out.print("DEBUG: " + usergroup.getUserGroupName().toString());
            if (usergroup!=null){
              roomsList = usergroup.getSensordataRooms();
              Iterator<SensordataRoom> itr = roomsList.iterator();
              while(itr.hasNext()){
                SensordataRoom room = itr.next();
                eventsList.addAll(eventEao.getEventByRoomId(room));
                System.out.print("DEBUG: " + room.getId().toString());
              }
              deviceList = usergroup.getSensordataDevices();
              Iterator<SensordataDevice> itr4 = deviceList.iterator();
              while(itr4.hasNext()){
            	  SensordataDevice device = itr4.next();
                eventsList.addAll(eventEao.getEventByDeviceId(device));
                System.out.print("DEBUG: " + device.getType().toString());
              }
              Iterator<EventsLog> itr2 = eventsList.iterator();
              while(itr2.hasNext()) {
                EventsLog events = itr2.next();
                alertsList.addAll(eventEao.getAlertsByEvents(events));
                System.out.print("DEBUG: " + events.getEventsId());
              }
              Iterator<AlertLog> itr3 = alertsList.iterator();
              while(itr3.hasNext()) {
                AlertLog alert = itr3.next();
                alertDataList.add(converter.convertAlertLog(alert));
                System.out.print("DEBUG: " + alert.getAlertsId());
              }
            }
        }
        return alertDataList;
    }
    
  	//Modify/Update Alert Setting
  	@Override
  	public boolean updateAlarmSetting(int alarmid, int usergroupid, String priority, 
  			String thresholdtype, String thresholdvalueMin, String thresholdvalueExact, String thresholdvalueMax){
  		AlertSetting alarm = new AlertSetting();
  		UserGroup ug = new UserGroup();
  		try{
  	  		ug = userEao.getUserGroupById(usergroupid);
  		} catch (Exception e){
  			return false;
  		}
  		try{
  	  		alarm = alertEao.getAllAlertSettingsById(alarmid);
  		} catch (Exception e){
  			return false;
  		}
  		alarm.setAlertPriority(priority);
  		if(thresholdtype.equals("BELOW")){
  			alarm.setMinThresholdEnabled(true);
  			alarm.setMaxThresholdEnabled(false);
  			alarm.setExactThresholdEnabled(false);
  			alarm.setMinThresholdValue(Double.parseDouble(thresholdvalueMin));
  		} else if(thresholdtype.equals("EXACT")){
  			alarm.setMinThresholdEnabled(false);
  			alarm.setMaxThresholdEnabled(false);
  			alarm.setExactThresholdEnabled(true);
  			alarm.setExactThresholdValue(Double.parseDouble(thresholdvalueExact));
  		} else if(thresholdtype.equals("ABOVE")){
  			alarm.setMinThresholdEnabled(false);
  			alarm.setMaxThresholdEnabled(true);
  			alarm.setExactThresholdEnabled(false);
  			alarm.setMaxThresholdValue(Double.parseDouble(thresholdvalueMax));
  		} else if(thresholdtype.equals("RANGE")){
  			alarm.setMinThresholdEnabled(true);
  			alarm.setMaxThresholdEnabled(true);
  			alarm.setExactThresholdEnabled(false);
  			alarm.setMinThresholdValue(Double.parseDouble(thresholdvalueMin));
  			alarm.setMaxThresholdValue(Double.parseDouble(thresholdvalueMax));
  		}
  		alarm.setUserGroup(ug);
  		try{
  			alertEao.updateAlertSetting(alarm);
  		} catch (Exception e){
  			return false;
  		}
  		return true;
  	}
    
	public void checkAllThresholds(SensordataParameter parameter) {
		Set<AlertSetting> alertList = parameter.getAlertSettings();
		Iterator<AlertSetting> itr = alertList.iterator();
		while (itr.hasNext()){
			AlertSetting alertSetting = itr.next();
			checkThreshold(parameter, alertSetting);
		}
	}

	private void checkThreshold(SensordataParameter parameter, AlertSetting alertSetting){
		if(alertSetting.getExactThresholdEnabled()){
			if(parameter.getValue()==Double.toString(alertSetting.getExactThresholdValue())){
				setAlarmForDevice(alertSetting, parameter.getValue(), parameter.getName() + " is EXACT VALUE", "MED");
			}
		}else {
			if (alertSetting.getMinThresholdEnabled()){
				if(Double.parseDouble(parameter.getValue()) < alertSetting.getMinThresholdValue()){
					setAlarmForDevice(alertSetting, parameter.getValue(), parameter.getName() + " under MIN VALUE","LOW");
				}
			}
			if (alertSetting.getMaxThresholdEnabled()){
				if(Double.parseDouble(parameter.getValue()) > alertSetting.getMaxThresholdValue()){
					setAlarmForDevice(alertSetting,  parameter.getValue(), parameter.getName() + " over MAX VALUE", "HIGH");
				}
			}
		}
		return;
	}
	
	@Override
	public void addAlarmSetting(String roomid,String deviceuid, String pname, 
			String lessThan, String greaterThan, String ug, String priority){
		AlertSetting newAlarm = new AlertSetting();
		System.out.print(deviceuid+ pname + lessThan + greaterThan + ug + priority);
		UserGroup usergroup = userEao.getUserGroupByName(ug);
		SensordataDevice device = sensorDataEao.getDeviceById(Integer.parseInt(deviceuid));
		SensordataParameter para = sensorDataEao.getSensorDataParametersByDeviceByName(device, pname);
		newAlarm.setAlertPriority(priority);
		newAlarm.setTimestamp(new Date());
		if (!lessThan.equals("")){
			newAlarm.setMinThresholdEnabled(true);
			newAlarm.setMinThresholdValue(Double.parseDouble(lessThan));
		}
		if (!greaterThan.equals("")){
			newAlarm.setMaxThresholdEnabled(true);
			newAlarm.setMaxThresholdValue(Double.parseDouble(greaterThan));			
		}
		newAlarm.setSensordataParameter(para);
		newAlarm.setUserGroup(usergroup);
		try {
			alertEao.addNewAlertSetting(newAlarm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	
	@Override
	public void modifyAlarmSetting(String alertid, String roomid, String deviceuid, String pname,
			String lessThan, String greaterThan, String ug, String priority) {
		AlertSetting newAlarm = new AlertSetting();
		UserGroup userg = new UserGroup();
		try {
			userg = userEao.getUserGroupByName(ug);
		} catch (Exception e) {
			return;
		}
		try {
			newAlarm = alertEao.getAllAlertSettingsById(Integer.parseInt(alertid));
		} catch (Exception e) {
			return;
		}

		SensordataDevice device = sensorDataEao.getDeviceById(Integer
				.parseInt(deviceuid));
		SensordataParameter para = sensorDataEao
				.getSensorDataParametersByDeviceByName(device, pname);
		newAlarm.setAlertPriority(priority);
		newAlarm.setTimestamp(new Date());
		if (!lessThan.equals("")) {
			newAlarm.setMinThresholdEnabled(true);
			newAlarm.setMinThresholdValue(Double.parseDouble(lessThan));
		}
		if (!greaterThan.equals("")) {
			newAlarm.setMaxThresholdEnabled(true);
			newAlarm.setMaxThresholdValue(Double.parseDouble(greaterThan));
		}
		newAlarm.setSensordataParameter(para);
		try {
			alertEao.updateAlertSetting(newAlarm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	
	//Removes Alarm Setting from database
	@Override
	public boolean deleteAlarmSetting(int alarmid) {
		AlertSetting alarm = new AlertSetting();
		try{
  	  		alarm = alertEao.getAllAlertSettingsById(alarmid);
  		} catch (Exception e){
  			return false;
  		}
		try{
				alertEao.removeAlertSetting(alarm);
		} catch (Exception e){
  			return false;
  		}
		return true;
	}
	
	@Override
	public void setSystemError() {
		/* add error to event log to alert log
		 * add to event log [EventsBean.addEvent()]
		 */
		
	}

	public void setAlarmForDevice(AlertSetting alertsetting, String value,  String type, String priority) {
		AlertLog newAlarm = new AlertLog();
		newAlarm.setAlertPriority(priority);
		if (alertsetting!=null){
			newAlarm.setAlertSetting(alertsetting);
		}
		newAlarm.setAlertType(type);
		newAlarm.setSensorDatadump(value);
		newAlarm.setTimestamp(new Date());
		try {
			eventEao.addNewAlertLog(newAlarm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		List<AlertLog> alarmList = eventEao.getAllAlerts();
		AlertLog alarm = alarmList.get(alarmList.size() - 1);
		EventsLog newEvent = new EventsLog();
		newEvent.setEventType(1);
		newEvent.setSensordataDevice(alertsetting.getSensordataParameter().getSensordataDevice());
		newEvent.setTimestamp(new Date());
		SensordataDevice device = new SensordataDevice();
		if (alertsetting!=null){
			SensordataParameter parameter = alertsetting.getSensordataParameter();
			device = parameter.getSensordataDevice();
			newEvent.setSensordataDevice(device);
		}
		newEvent.setAlertLog(alarm);
		try {
			eventEao.addNewEventLog(newEvent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		UserGroup usergroup = alertsetting.getUserGroup();
		if (usergroup!=null){
			SensordataRoom room = device.getSensordataRoom();
			//smser.sendSMSforDeviceFault(usergroup, device, room, newAlarm);
			emailer.sendEmailforDeviceFault(usergroup, device, room, alarm);
		}
		return;
	}
	
	public void setAlarmForDeviceDC(SensordataDevice device, String type, String priority) {
		AlertLog newAlarm = new AlertLog();
		newAlarm.setAlertPriority(priority);
		newAlarm.setAlertType(type);
		newAlarm.setTimestamp(new Date());
		try {
			eventEao.addNewAlertLog(newAlarm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		List<AlertLog> alarmList = eventEao.getAllAlerts();
		AlertLog alarm = alarmList.get(alarmList.size() - 1);
		EventsLog newEvent = new EventsLog();
		newEvent.setEventType(1);
		newEvent.setTimestamp(new Date());
		newEvent.setSensordataDevice(device);
		newEvent.setAlertLog(alarm);
		try {
			eventEao.addNewEventLog(newEvent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		UserGroup usergroup = device.getUserGroupBean();
		System.out.print("EMAIL" + usergroup.getUserGroupEmail());
		if (usergroup!=null){
			SensordataRoom room = device.getSensordataRoom();
			//smser.sendSMSforDeviceDC(usergroup, device, room, newAlarm);
			emailer.sendEmailforDeviceDC(usergroup, device, room, newAlarm);
		}
		return;
	}
	
	public void setAlarmForRoomDC(SensordataRoom room, String type, String priority) {
		AlertLog newAlarm = new AlertLog();
		newAlarm.setAlertPriority(priority);
		newAlarm.setAlertType(type);
		newAlarm.setTimestamp(new Date());
		try {
			eventEao.addNewAlertLog(newAlarm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		List<AlertLog> alarmList = eventEao.getAllAlerts();
		AlertLog alarm = alarmList.get(alarmList.size() - 1);
		EventsLog newEvent = new EventsLog();
		newEvent.setEventType(1);
		newEvent.setSensordataRoom(room);
		newEvent.setTimestamp(new Date());
		SensordataDevice device = new SensordataDevice();
		newEvent.setAlertLog(alarm);
		try {
			eventEao.addNewEventLog(newEvent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		UserGroup usergroup = room.getUserGroupBean();
		if (usergroup!=null){
			//smser.sendSMSForRoomDC(usergroup, room, newAlarm);
			emailer.sendEmailForRoomDC(usergroup, room, alarm);
		}
		return;
	}

	@Override
	public AlertLogDataClass getAlertLogByID(int id) {
		AlertLogDataClass alertLogData = new AlertLogDataClass();
		AlertLog alertLog;
		try {
			alertLog = eventEao.getAlertById(id);
		} catch (Exception e) {
			return alertLogData;
		}
		DataConverter converter = new DataConverter();
		alertLogData = converter.convertAlertLog(alertLog);
		return alertLogData;
	}

	@Override
	public String getSensorDataDump(int alertId) {
		AlertLog alertLog;
		try {
			alertLog = eventEao.getAlertById(alertId);
		} catch (Exception e) {
			return "";
		}
		return alertLog.getSensorDatadump();
	}


	/* Missing Functions
	 * 1. get all from alert log
	 * 
	 */

}
