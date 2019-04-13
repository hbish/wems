package com.wems.model;

public class Device
{
    private int     deviceuid, // PK
                    id,
                    roomuid,   // FK: sensordata_room
                    userGroup; // FK: user_group
    private String  type,
                    brand,
                    model,
                    serial,
                    macaddress;
    private boolean connected;

    public Device(  int deviceuid, // PK
                    int id,
                    int roomuid,   // FK: sensordata_room
                    int userGroup, // FK: user_group
                    String type,
                    String brand,
                    String model,
                    String serial,
                    String macaddress,
                    boolean connected)
    {
        this.deviceuid  = deviceuid;
        this.id         = id;
        this.roomuid    = roomuid;
        this.userGroup  = userGroup;
        this.type       = type;
        this.brand      = brand;
        this.model      = model;
        this.serial     = serial;
        this.macaddress = macaddress;
        this.connected  = connected;
    }
    
    public Device(  int id,
                    int roomuid,   // FK: sensordata_room
                    int userGroup, // FK: user_group
                    String type,
                    String brand,
                    String model,
                    String serial,
                    String macaddress,
                    boolean connected)
    {
        this.id         = id;
        this.roomuid    = roomuid;
        this.userGroup  = userGroup;
        this.type       = type;
        this.brand      = brand;
        this.model      = model;
        this.serial     = serial;
        this.macaddress = macaddress;
        this.connected  = connected;
    }
    
    public int     deviceuid() {return deviceuid;}
    public int     id()        {return id;}
    public int     roomuid()   {return roomuid;}
    public int     userGroup() {return userGroup;}
    public String  type()      {return type;}
    public String  brand()     {return brand;}
    public String  model()     {return model;}
    public String  serial()    {return serial;}
    public String  macaddress(){return macaddress;}
    public boolean connected() {return connected;}
}