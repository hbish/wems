package com.wems.model;

import java.util.*;

public class UserGroups extends Viewable
{
    private LinkedList<UserGroup> userGroups = new LinkedList<UserGroup>();
    
    public UserGroup userGroup(int index)
    {
        if (index > userGroups.size()-1)
            return null;
        else
            return userGroups.get(index);
    }
    
    public int size()
    {   return userGroups.size();  }
    
    public void add(UserGroup userGroup) 
    {
        userGroups.add(userGroup);
        update();
    }
}