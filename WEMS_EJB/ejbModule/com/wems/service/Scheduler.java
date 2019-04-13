package com.wems.service;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Session Bean implementation class Scheduler
 * 
 * It is vital that the following command be added to beans which depends on the primary bean
 * @DependsOn("Scheduler")
 */
@Startup
@Singleton
@LocalBean
public class Scheduler implements Runnable {
	private Thread localThread;
	private volatile boolean halted;
	private volatile long intervalMS;
	@EJB AlarmsBean alarmsBean;
	
    /**
     * Default constructor. 
     */
    public Scheduler() {
        
    }
    
    @PostConstruct
    void init() {
    	halted = false;
    	intervalMS = 1000;
    	
    	//Create and start the thread
    	this.localThread = new Thread(this);
    	this.localThread.start();
    }

    /**
     * Set the amount of time to yield before running the next cycle
     */
    @Lock(LockType.WRITE)
    public void setTimeInterval(long milliseconds) {
    	intervalMS = milliseconds;
    }
    
    /**
     * Get the current time interval
     * @return Time interval in milliseconds
     */
    @Lock(LockType.READ)
    public long getTimeInterval() {
    	return intervalMS;
    }
    
    /**
     * Halts the thread. Should only be used in extreme cases!
     */
    public void halt() {
    	halted = true;
    }

    /**
     * The function which will be executed at every time duration of 'intervalMS'
     */
	@Override
	public void run() {
		while (!halted) {
			/*
			 * ### DO STUFF HERE ###
			 */
			
			alarmsBean.checkRoomsDC();
			
			/*
			 * ### STOP DOING STUFF HERE ###
			 */
			
			//Yield thread momentarily
			try {
				Thread.sleep(intervalMS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
    
    
}
