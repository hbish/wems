package com.wems.data;

import java.util.Date;

public class UserSessionDataClass {

		private int sessionId;

		private String ipAddress;
		
		private Date loggedInTime;

		private int sessionStatus;
		
		//Constructor
		
		public UserSessionDataClass(){	
			
		}
		
		//Getters
		
		public int getSessionId(){
			return sessionId;
		}
		
		public String getIpAddress(){
			return ipAddress;
		}
		
		public Date getLoggedInTime(){
			return loggedInTime;
		}
		
		public int getSessionStatus(){
			return sessionStatus;
		}

	

}
