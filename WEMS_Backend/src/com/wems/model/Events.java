package com.wems.model;
import java.util.*;

public class Events extends Viewable
{
    private LinkedList<Event> events = new LinkedList<Event>();
    
    public Event event(int index)
    {
        if (index > events.size()-1)
            return null;
        else
            return events.get(index);
    }
    
    public int size()
    {   return events.size();  }
    
    public void add(Event event) 
    {
        events.add(event);
        update();
    }
}