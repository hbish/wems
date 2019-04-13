package com.wems.model;

import java.util.Date;

public class Alert
{
    private int      alertsId;
    private String   alertType,
                     alertPriority;
    private Date     timeStamp;
    private byte[]   sensorDataDump;
    
    public Alert(
                int    alertsId,
                String alertType,
                String alertPriority,
                Date   timeStamp,
                byte[] sensorDataDump)
    {
        this.alertsId       = alertsId;
        this.alertType      = alertType;
        this.alertPriority  = alertPriority;
        this.timeStamp      = timeStamp;
        this.sensorDataDump = sensorDataDump;
    }
    
    public int alertsId()         {return alertsId;}
    public String alertType()     {return alertType;}
    public String alertPriority() {return alertPriority;}
    public Date timeStamp()       {return timeStamp;}
    public byte[] sensorDataDump(){return sensorDataDump;}
}