package com.wems.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class TrendBean
 */
@Stateless
@LocalBean
public class TrendBean implements TrendBeanRemote {

    /**
     * Default constructor. 
     */
    public TrendBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void getfilteredData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getSensorTrend() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getDeviceTrend() {
		// TODO Auto-generated method stub
		
	}

}
