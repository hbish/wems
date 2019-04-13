package com.wems.service;
import javax.ejb.Remote;

@Remote
public interface ReportBeanRemote {
//	WEMS-REP-F-01
//	WEMS-REP-F-07
	public void getOverallSystemStatus();
//	WEMS-REP-F-02
//	WEMS-REP-F-07
	public void getOverallEnergyConsumption();
//	WEMS-REP-F-03
//	WEMS-REP-F-07
	public void getOverallRoomEnergyConsumption();
//	WEMS-REP-F-04
//	WEMS-REP-F-07
	public void getOverallBuildingEnergyConsumption();
	
//	WEMS-REP-F-05 (HMI related)
//	WEMS-REP-F-08 (HMI related)
	
//	WEMS-REP-F-06
	public void generateDOCReport();
	public void generatePDFReport();
	
}
