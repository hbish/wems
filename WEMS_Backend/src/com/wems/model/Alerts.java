package com.wems.model;

import java.util.*;

public class Alerts extends Viewable
{
    private LinkedList<Alert> alerts = new LinkedList<Alert>();
    
    public Alert alert(int index)
    {
        if (index > alerts.size()-1)
            return null;
        else
            return alerts.get(index);
    }
    
    public int size()
    {   return alerts.size();  }
    
    public void add(Alert alert) 
    {
        alerts.add(alert);
        update();
    }
}