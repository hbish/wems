package com.wems.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import java.security.Security;
import java.util.Properties;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.wems.entity.AlertLog;
import com.wems.entity.SensordataDevice;
import com.wems.entity.SensordataRoom;
import com.wems.entity.User;
import com.wems.entity.UserGroup;

/**
 * Session Bean implementation class EmailerBean
 */
@Stateless
@LocalBean
public class EmailerBean {

    /**
     * Default constructor. 
     */
	private String SYS_USER_NAME      = "wems-server";
    private int    SYS_MAX_USERS      = 400;
    private int    SYS_USER_ACCESS_0  = 0; // SuperAdmin Access Rights Type
    private int    SYS_USER_ACCESS_1  = 1; // Admin Access Rights Type
    
    private String DB_JDBC_PROTOCOL   = "jdbc:mysql://";
    private String DB_JDBC_CLASS_NAME = "com.mysql.jdbc.MYSQL"; // Windows driver
    private String DB_HOST            = "localhost:3306";        // Host where the Database resides (Windows)
    private String DB_NAME            = "wemsdb";                // Name of the database
    private String DB_USER            = "root";                  // User who can access the Database
    private String DB_PASSWORD        = "wems123";                  // Password for the User
    
    public String MAIL_SMTP_HOST     = "smtp.gmail.com";
    public int    MAIL_SMTP_PORT     = 465;
    public String MAIL_FROM          = "wems.message@gmail.com";
    public String MAIL_SMTP_USER     = "wems.message@gmail.com";
    public String MAIL_SMTP_PASS     = "wemsdragons";
    private String MAIL_SMTP_AUTH     = "true";
    private String MAIL_DEBUG         = "false";
    private int    MAIL_SMTP_SF_PORT  = 465;
    private String MAIL_SMTP_SF_CLASS = "javax.net.ssl.SSLSocketFactory";
    private String MAIL_SMTP_SF_FBACK = "false";
    private String DISCLAIMER =  "\nKind Regards,\nThe WEMS Administration Team\n\n" +
            "THIS IS AN AUTOMATED EMAIL - PLEASE DO NOT REPLY TO THIS EMAIL!\nIF YOU REPLY, YOU MAY BE OF QUESTIONABLE CONSCIENCE.";
    
    public EmailerBean() {
        // TODO Auto-generated constructor stub
    }
    
    private void sendEmail(
    	    String aFromEmailAddr,
    	    String aToEmailAddr,
    	    String aSubject,
    	    String aBody
    	  ){
    	    //Initialise Configurator and settings  
    	    final Properties fMailServerConfig = System.getProperties();
    	    fMailServerConfig.put("mail.smtp.host",                   MAIL_SMTP_HOST); // Host whose Mail services will be used
    	    fMailServerConfig.put("mail.smtp.port",                   String.valueOf(MAIL_SMTP_PORT));
    	    fMailServerConfig.put("mail.from",                        MAIL_FROM);
    	    fMailServerConfig.put("mail.user",                        MAIL_SMTP_USER);
    	    fMailServerConfig.put("mail.password",                    MAIL_SMTP_PASS);
    	    fMailServerConfig.put("mail.smtp.auth",                   MAIL_SMTP_AUTH); // Other Configuration Details
    	    fMailServerConfig.put("mail.debug",                       MAIL_DEBUG);
    	    fMailServerConfig.put("mail.smtp.socketFactory.port",     String.valueOf(MAIL_SMTP_SF_PORT));
    	    fMailServerConfig.put("mail.smtp.socketFactory.class",    MAIL_SMTP_SF_CLASS);
    	    fMailServerConfig.put("mail.smtp.socketFactory.fallback", MAIL_SMTP_SF_FBACK);
    	      
    	    //Authenticators are used to prompt the user for user name and password.
    	    Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
    	    Session session = Session.getInstance( fMailServerConfig, 
    	        new javax.mail.Authenticator() {
    	            protected PasswordAuthentication getPasswordAuthentication() {
    	                return new PasswordAuthentication(fMailServerConfig.getProperty("mail.user"),
    	                                                  fMailServerConfig.getProperty("mail.password"));
    	            }
    	        });
    	    
    	    MimeMessage message = new MimeMessage( session );
    	    try {
    	      message.addRecipient(
    	        Message.RecipientType.TO, new InternetAddress(aToEmailAddr)
    	      );
    	      // Setting the Subject and Content Type
    	      message.setSubject( aSubject );
    	      message.setText( aBody );
    	      Transport.send( message );
    	    }
    	    catch (MessagingException ex){
    	      System.err.println("Cannot send email: " + ex);
    	    }
    	  }

    public void sendEmailforDeviceFault(UserGroup usergroup, SensordataDevice device, SensordataRoom room, AlertLog newAlarm){
    	String userEmail  = usergroup.getUserGroupEmail();
        String header       = "Device Fault";
        String message      =
             "Hello " + usergroup.getUserGroupName()+ ",\n\nThe device" + device.getType() + " "+ device.getId() + 
             " located at " + room.getName() +
             " (Building " +room.getSensordataAddress().getBuilding() + ", Level " +room.getSensordataAddress().getLevel() + ", Room " + room.getId()+")"+
             " has caused an alert.\n\n" +
         	 "Fault Description: "+
         	 newAlarm.getAlertType()+
             ".\n\n"+
             DISCLAIMER;
        sendEmail(MAIL_FROM, userEmail, header, message);
    }
    
    public void sendEmailforDeviceDC(UserGroup usergroup, SensordataDevice device, SensordataRoom room, AlertLog newAlarm){
    	String userEmail  = usergroup.getUserGroupEmail();
        String header       = "Device Disconnected";
        String message      =
             "Hello " + usergroup.getUserGroupName()+ ",\n\nThe device" + device.getType() + " "+ device.getId() + 
             " located at " + room.getName() +
             " (Building " +room.getSensordataAddress().getBuilding() + ", Level " +room.getSensordataAddress().getLevel() + ", Room " + room.getId()+")"+
             " has been disconnected.\n\n"+
             DISCLAIMER;
        System.out.print("EMAIL" + userEmail);
        System.out.print("Message" + message);
        sendEmail(MAIL_FROM, userEmail, header, message);
        return;
    }
    
    public void sendEmailForRoomDC(UserGroup usergroup, SensordataRoom room, AlertLog newAlarm){
    	String userEmail  = usergroup.getUserGroupEmail();
        String header       = "Device Fault";
        String message      =
             "Hello " + usergroup.getUserGroupName()+ ",\n\nThe room " + room.getName() + " located at" +
             " Building " +room.getSensordataAddress().getBuilding() + ", Level " +room.getSensordataAddress().getLevel() + ", Room " + room.getId()+
             "is not responding.\n\n"+
             DISCLAIMER;
        sendEmail(MAIL_FROM, userEmail, header, message);
        System.out.print("DEBUG - SENT EMAIL");
        return;
    }
    
    public void sendEmailForDeleteUser(User user){
    	String userEmail  = user.getEmail();
    	String userGroupEmail = user.getUserGroupBean().getUserGroupEmail();
        String header       = "Deleted User Account";
        String message1      =
             "Hello " + user.getFirstName() + " " + user.getLastName() + ",\n\nYour User Account has been deleted from the WEMS System.\n\n" +           
             "\nKind Regards,\nThe WEMS Administration Team\n\n" +
             "THIS IS AN AUTOMATED EMAIL - PLEASE DO NOT REPLY TO THIS EMAIL!\nIF YOU REPLY, YOU MAY BE OF QUESTIONABLE CONSCIENCE.";
             
        String message2      =
             "Hello User Group" + ",\n\nThe User Account for "+ user.getFirstName() + " " + user.getLastName() +" has been deleted from the WEMS System.\n\n" +           
             DISCLAIMER;
        String[] reciever = new String[]{userEmail,userGroupEmail};
        String[] message = new String[]{message1,message2};        
         for(int i = 0 ; i<2 ; i++)
         {
             sendEmail(
            	MAIL_FROM,
                  reciever[i],
                  header,
                  message[i]
            );
         }
    }
}