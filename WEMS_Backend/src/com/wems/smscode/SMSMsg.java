package com.wems.smscode;

import java.net.*;
import java.io.*;
import javax.swing.*;

import com.wems.config.*;


public class SMSMsg
{
  public SMSMsg()
  {
  }

  public void sendSMS(Config config, String mob_no, String sMsg) {
      String sURL = "";
      HttpURLConnection conn = null;
      try  {
    	  sURL = config.SMS_GATEWAY_URL_1 + config.SMS_HOST_USERNAME +
    			 config.SMS_GATEWAY_URL_2 + config.SMS_HOST_PASSWORD +
    			 config.SMS_GATEWAY_URL_3 + mob_no +
    			 config.SMS_GATEWAY_URL_4 + URLEncoder.encode(sMsg, "UTF-8");
          URL url = new URL(sURL);
          conn = (HttpURLConnection)url.openConnection();          
          conn.setDoOutput(false);
          conn.setRequestMethod("GET");
          conn.connect();
          int iResponseCode = conn.getResponseCode();
          if ( iResponseCode == 200 ) {
            BufferedReader oIn = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
            String sInputLine = "";
            String sResult = "";
            while ((sInputLine = oIn.readLine()) != null) {
              sResult = sResult + sInputLine;
            }
            if (Long.parseLong(sResult) > 0) 
            {
              printMessage("Success - MT ID : " + sResult);       
            }
            else 
            {
              printMessage("Failure - Error code : " + sResult);       
            }
          }
          else {
            printMessage("Failure - Unspecified Error");        
          }
      }
      catch (Exception e){
        printMessage(e.getMessage());
      }
      finally {
        if (conn != null) {
          conn.disconnect();
        }
      }  
    }
    
    private void printMessage(String warningString)
    {
        System.out.println(warningString);
        JOptionPane.showMessageDialog(null, warningString, "SMS Message Information", JOptionPane.INFORMATION_MESSAGE);
    }
}