package com.wems.service;
import javax.ejb.Remote;

import com.wems.data.AlertLogDataClass;
import com.wems.data.AlertSettingDataClass;
import com.wems.data.EventsLogDataClass;

import java.util.List;
@Remote
public interface AlarmsBeanRemote {
	
//	WEMS-ALA-F-01 (DB specific)
//	WEMS-ALA-F-06 (Need clarification)
	
//	WEMS-ALA-F-02
//	WEMS-ALA-F-04
	public List<AlertLogDataClass> getUserNotifications(String username);
	public AlertLogDataClass getAlertLogByID(int id);
	public List<AlertSettingDataClass> getUsersAlarms(String username);
	public List<Integer> getUsersAlarmsID(String username);
	public List<AlertLogDataClass> getUserEvents(String username);
	public AlertSettingDataClass getAlarmByID(int id);
	public boolean updateAlarmSetting(int alarmid, int usergroupid, String priority, String thresholdtype, String thresholdvalueMin, String thresholdvalueExact, String thresholdvalueMax);
	//	WEMS-ALA-F-03
	public void setSystemError();
//	WEMS-CON-F-01
//	WEMS-CON-F-02	
//	WEMS-ALA-F-05
	boolean deleteAlarmSetting(int alarmid);	
	public void addAlarmSetting(String room,String deviceid, String pName, 
			String lessThan, String greaterThan, String ug, String priority);
	public void modifyAlarmSetting(String alertid, String roomid, String deviceuid, String pname,
			String lessThan, String greaterThan, String ug, String priority);
	String getSensorDataDump(int alertId);
}
