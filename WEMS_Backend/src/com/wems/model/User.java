package com.wems.model;

public class User
{
    private String  firstName,
                    lastName,
                    username,
                    email,
                    password,
                    phNumber;
    private int     userId,
                    userTypeId,
                    userGroup,
                    loginAttempts;
    private boolean isTempPass,
                    isLockedOut;

    public User(String firstName,
                String lastName,
                String username,
                String email, 
                String password, 
                String phNumber, 
                int userTypeId,
                int userGroup,
                int loginAttempts,
                boolean isLockedOut)
    {
        this.firstName      = firstName;
        this.lastName       = lastName;
        this.username       = username;
        this.email          = email;
        this.password       = password;
        this.phNumber       = phNumber;
        this.userTypeId     = userTypeId;
        this.userGroup      = userGroup;
        this.loginAttempts  = loginAttempts;
        this.isLockedOut    = isLockedOut;
    }
    
    public User(String firstName,
                String lastName,
                String username,
                String email, 
                int userGroup)
    {
        this.firstName      = firstName;
        this.lastName       = lastName;
        this.username       = username;
        this.email          = email;
        this.userGroup      = userGroup;
    }
    
    public User(int userId,
                String firstName,
                String lastName,
                String username,
                String email, 
                String password, 
                String phNumber, 
                int userTypeId,
                int userGroup,
                int loginAttempts,
                boolean isLockedOut)
    {
        this.userId         = userId;
        this.firstName      = firstName;
        this.lastName       = lastName;
        this.username       = username;
        this.email          = email;
        this.password       = password;
        this.phNumber       = phNumber;
        this.userTypeId     = userTypeId;
        this.userGroup      = userGroup;
        this.loginAttempts  = loginAttempts;
        this.isLockedOut    = isLockedOut;
    }
    
    public User(int userId, String firstName, String lastName)
    {
        this.userId    = userId;
        this.firstName = firstName;
        this.lastName  = lastName;
    }
    
    public User(String username)
    {
        this.username   = username;
    }
        
    public User(String username, String email)
    {
        this.username   = username;
        this.email      = email;
    }
    
    public User(String username, String password, boolean isTempPass)
    {
        this.username   = username;
        this.password   = password;
        this.isTempPass = isTempPass;
    }
    
    public User(String username, int loginAttempts, boolean isLockedOut)
    {
        this.username     = username;
        this.loginAttempts= loginAttempts;
        this.isLockedOut  = isLockedOut;
    }
    
    public String firstName()  {return firstName;}
    public String lastName()   {return lastName;}
    public String username()   {return username;}
    public String email()      {return email;}
    public String password()   {return password;}
    public String phNumber()   {return phNumber;}
    
    public int userId()         {return userId;}
    public int userTypeId()     {return userTypeId;}
    public int userGroup()      {return userGroup;}
    public boolean isTempPass() {return isTempPass;}
    public boolean isLockedOut(){return isLockedOut;}
    public int loginAttempts()  {return loginAttempts;}
}
