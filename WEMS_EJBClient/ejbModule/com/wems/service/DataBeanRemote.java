package com.wems.service;
import javax.ejb.Remote;

@Remote
public interface DataBeanRemote {
//	WEMS-DAT-F-01 Should happen when we store the sensor data
//	WEMS-DAT-F-05
//	WEMS-DAT-F-02
//	WEMS-DAT-F-06
//	WEMS-IOM-F-07
	public void addSensorData(/*XML String*/);
	
//	WEMS-DAT-F-03 Implicitly done through EAO
//	WEMS-DAT-F-04 Done through alarms module
	
//	WEMS-IOM-F-08
	public void getSensorData();
}
