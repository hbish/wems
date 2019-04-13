package com.wems.model;

public class UserGroup
{
    private String  userGroupName,
                    userGroupEmail,
                    userGroupDescription;
    private int     userGroupId;

    public UserGroup(
                String userGroupName,
                String userGroupEmail,
                String userGroupDescription)
    {
        this.userGroupName        = userGroupName;
        this.userGroupEmail       = userGroupEmail;
        this.userGroupDescription = userGroupDescription;
    }
    
    public UserGroup(
                String userGroupName,
                String userGroupEmail)
    {
        this.userGroupName        = userGroupName;
        this.userGroupEmail       = userGroupEmail;
    }
    
    public UserGroup(
                int userGroupId,
                String userGroupName,
                String userGroupEmail,
                String userGroupDescription)
    {
        this.userGroupId          = userGroupId;
        this.userGroupName        = userGroupName;
        this.userGroupEmail       = userGroupEmail;
        this.userGroupDescription = userGroupDescription;
    }
    
    public String userGroupName()       {return userGroupName;}
    public String userGroupEmail()      {return userGroupEmail;}
    public String userGroupDescription(){return userGroupDescription;}
    public int userGroupId()            {return userGroupId;}
}