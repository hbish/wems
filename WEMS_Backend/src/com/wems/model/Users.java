package com.wems.model;
import java.util.*;

public class Users extends Viewable
{
    private LinkedList<User> users = new LinkedList<User>();
    
    public User user(int index)
    {
        if (index > users.size()-1)
            return null;
        else
            return users.get(index);
    }
    
    public int size()
    {   return users.size();  }
    
    public void add(User user) 
    {
        users.add(user);
        update();
    }
}