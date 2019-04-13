package com.wems.model;
import java.util.*;

public class Devices extends Viewable
{
    private LinkedList<Device> devices = new LinkedList<Device>();
    
    public Device device(int index)
    {
        if (index > devices.size()-1)
            return null;
        else
            return devices.get(index);
    }
    
    public int size()
    {   return devices.size();  }
    
    public void add(Device device) 
    {
        devices.add(device);
        update();
    }
}