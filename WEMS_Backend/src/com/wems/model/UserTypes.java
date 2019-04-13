package com.wems.model;
import java.util.*;

public class UserTypes extends Viewable
{
    private LinkedList<UserType> userTypes = new LinkedList<UserType>();
    
    public UserType userType(int index)
    {
        if (index > userTypes.size()-1)
            return null;
        else
            return userTypes.get(index);
    }
    
    public int size()
    {   return userTypes.size();  }
    
    public void add(UserType userType) 
    {
        userTypes.add(userType);
        update();
    }
}