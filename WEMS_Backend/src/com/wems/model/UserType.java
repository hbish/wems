package com.wems.model;

public class UserType
{
    private int     userTypeId;
    private String  userTypeName,
                    userTypeDescription;
    
    public UserType(String userTypeName)
    {
        this.userTypeName = userTypeName;
    }
                   
    public UserType(
                String userTypeName, 
                String userTypeDescription)
    {
        this.userTypeName        = userTypeName;
        this.userTypeDescription = userTypeDescription;
    }
    
    public UserType(
                int userTypeId, 
                String userTypeName, 
                String userTypeDescription)
    {
        this.userTypeId          = userTypeId;
        this.userTypeName        = userTypeName;
        this.userTypeDescription = userTypeDescription;
    }
    
    public int userTypeId()             {return userTypeId;}
    public String userTypeName()        {return userTypeName;}
    public String userTypeDescription() {return userTypeDescription;}
}