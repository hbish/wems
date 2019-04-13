package com.wems.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class ReportBean
 */
@Stateless
@LocalBean
public class ReportBean implements ReportBeanRemote {

    /**
     * Default constructor. 
     */
    public ReportBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void getOverallSystemStatus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getOverallEnergyConsumption() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getOverallRoomEnergyConsumption() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getOverallBuildingEnergyConsumption() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateDOCReport() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generatePDFReport() {
		// TODO Auto-generated method stub
		
	}

}
