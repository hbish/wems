package com.wems.service;
import javax.ejb.Remote;

@Remote
public interface TrendBeanRemote {
//	WEMS-TRE-F-01 What are we filtering?
//	WEMS-TRE-F-03
	public void getfilteredData();
//	WEMS-TRE-F-02 (HMI Related)
	
//	WEMS-TRE-F-04
	public void getSensorTrend();
	public void getDeviceTrend();
}
