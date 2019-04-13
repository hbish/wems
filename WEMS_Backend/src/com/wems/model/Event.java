package com.wems.model;

import java.util.Date;

public class Event
{
    private int      eventsId,
                     deviceId,
                     alertId,
                     eventType;
    private Date     timeStamp;
    
    public Event(
                int eventsId,
                int deviceId,
                int alertId,
                int eventType,
                Date timeStamp)
    {
        this.eventsId  = eventsId;
        this.deviceId  = deviceId;
        this.alertId   = alertId;
        this.eventType = eventType;
        this.timeStamp = timeStamp;
    }
    
    public int eventsId() {return eventsId;}
    public int deviceId() {return deviceId;}
    public int alertId()  {return alertId;}
    public int eventType(){return eventType;}
    public Date timeStamp()  {return timeStamp;}
}