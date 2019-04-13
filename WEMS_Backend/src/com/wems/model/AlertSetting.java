package com.wems.model;

import java.util.Date;

public class AlertSetting
{
    private int alertId,
                alertUserGroup,
                score,
                dataParameterId;
    private String alertType,
                   alertPriority;
    private Date timestamp;
    private double minThresholdValue,
                   maxThresholdValue,
                   exactThresholdValue;
    private boolean minThresholdEnabled,
                    maxThresholdEnabled,
                    exactThresholdEnabled,
                    scoreEnabled;
    
    public AlertSetting(
                int alertId,
                int alertUserGroup,
                int score,
                int dataParameterId,
                String alertType,
                String alertPriority,
                Date timestamp,
                double minThresholdValue,
                double maxThresholdValue,
                double exactThresholdValue,
                boolean minThresholdEnabled,
                boolean maxThresholdEnabled,
                boolean exactThresholdEnabled,
                boolean scoreEnabled)
    {
        this.alertId = alertId;
        this.alertUserGroup = alertUserGroup;
        this.score = score;
        this.dataParameterId = dataParameterId;
        this.alertType = alertType;
        this.alertPriority = alertPriority;
        this.timestamp = timestamp;
        this.minThresholdValue = minThresholdValue;
        this.maxThresholdValue = maxThresholdValue;
        this.exactThresholdValue = exactThresholdValue;
        this.minThresholdEnabled = minThresholdEnabled;
        this.maxThresholdEnabled = maxThresholdEnabled;
        this.exactThresholdEnabled = exactThresholdEnabled;
        this.scoreEnabled = scoreEnabled;
    }
    
    public int alertId()                   {return alertId;}
    public int alertUserGroup()            {return alertUserGroup;}
    public int score()                     {return score;}
    public int dataParameterId()           {return dataParameterId;}
    public String alertType()              {return alertType;}
    public String alertPriority()          {return alertPriority;}
    public Date timestamp()                {return timestamp;}
    public double minThresholdValue()      {return minThresholdValue;}
    public double maxThresholdValue()      {return maxThresholdValue;}
    public double exactThresholdValue()    {return exactThresholdValue;}
    public boolean minThresholdEnabled()   {return minThresholdEnabled;}
    public boolean maxThresholdEnabled()   {return maxThresholdEnabled;}
    public boolean exactThresholdEnabled() {return exactThresholdEnabled;}
    public boolean scoreEnabled()          {return scoreEnabled;}
}