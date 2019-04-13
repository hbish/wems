package com.wems.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class DataBean
 */
@Stateless
@LocalBean
public class DataBean implements DataBeanRemote {

    /**
     * Default constructor. 
     */
    public DataBean() {
    }

	@Override
	public void addSensorData() {
		/* add sensor/device to device table
		 * add to event log [EventsBean.addEvent()]
		 * 
		 */
		
	}

	@Override
	public void getSensorData() {
		/*get all devices from device table
		 * 
		 */
		
	}

}
