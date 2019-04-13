package com.wems;

import java.security.Security;
import java.util.Properties;
import java.io.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.wems.config.*;

/**
  * Using the javax.mail API.
  */
public final class Emailer {
  /**
  * Send a single email.
  */
  public void sendEmail(
    String aFromEmailAddr,
    String aToEmailAddr,
    String aSubject,
    String aBody,
    Config config
  ){
    //Initialise Configurator and settings  
    final Properties fMailServerConfig = System.getProperties();
    fMailServerConfig.put("mail.smtp.host",                   config.MAIL_SMTP_HOST); // Host whose Mail services will be used
    fMailServerConfig.put("mail.smtp.port",                   String.valueOf(config.MAIL_SMTP_PORT));
    fMailServerConfig.put("mail.from",                        config.MAIL_FROM);
    fMailServerConfig.put("mail.user",                        config.MAIL_SMTP_USER);
    fMailServerConfig.put("mail.password",                    config.MAIL_SMTP_PASS);
    fMailServerConfig.put("mail.smtp.auth",                   config.MAIL_SMTP_AUTH); // Other Configuration Details
    fMailServerConfig.put("mail.debug",                       config.MAIL_DEBUG);
    fMailServerConfig.put("mail.smtp.socketFactory.port",     String.valueOf(config.MAIL_SMTP_SF_PORT));
    fMailServerConfig.put("mail.smtp.socketFactory.class",    config.MAIL_SMTP_SF_CLASS);
    fMailServerConfig.put("mail.smtp.socketFactory.fallback", config.MAIL_SMTP_SF_FBACK);
      
    //Authenticators are used to prompt the user for user name and password.
    //Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
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
  
  //   /**
  //   * Allows the config to be refreshed at runtime, instead of
  //   * requiring a restart.
  //   */
  //   public static void refreshConfig() {
  //     fMailServerConfig.clear();
  //     fetchConfig();
  //   }
  // 
  //   /** PRIVATE */
  //   private static Properties fMailServerConfig = new Properties();
  // 
  //   static {
  //     fetchConfig();
  //   }
  //   
  //   /**
  //   * Open a specific text file containing mail server
  //   * parameters, and populate a corresponding Properties object.
  //   */
  //   private static void fetchConfig() {
  //     InputStream input = null;
  //     try {
  //       //If possible, one should try to avoid hard-coding a path in this
  //       //manner; in a web application, one should place such a file in
  //       //WEB-INF, and access it using ServletContext.getResourceAsStream.
  //       //Another alternative is Class.getResourceAsStream.
  //       //This file contains the javax.mail config properties mentioned above.
  //       input = new FileInputStream( "./_EmailConfig_.txt" );
  //       fMailServerConfig.load( input );
  //     }
  //     catch ( IOException ex ){
  //       System.err.println("Cannot open and load mail server properties file.");
  //     }
  //     finally {
  //       try {
  //         if ( input != null ) input.close();
  //       }
  //       catch ( IOException ex ){
  //         System.err.println( "Cannot close mail server properties file." );
  //       }
  //     }
  //   }
} 