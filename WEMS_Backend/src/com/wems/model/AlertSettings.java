package com.wems.model;

import java.util.*;

public class AlertSettings extends Viewable
{
    private LinkedList<AlertSetting> alertSettings = new LinkedList<AlertSetting>();
    
    public AlertSetting alertSetting(int index)
    {
        if (index > alertSettings.size()-1)
            return null;
        else
            return alertSettings.get(index);
    }
    
    public int size()
    {   return alertSettings.size();  }
    
    public void add(AlertSetting alertSetting) 
    {
        alertSettings.add(alertSetting);
        update();
    }
}