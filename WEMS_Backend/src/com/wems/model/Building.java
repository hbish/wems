package com.wems.model;

public class Building
{
    private String[] buildingList = {
         "Building 1",
         "Building 2",
         "Building 3",
         "Building 4"};
    private String[] levelList = {
         "Level 1",
         "Level 2",
         "Level 3",
         "Level 4"};
    private String[] roomList = {
         "Room 1",
         "Room 2",
         "Room 3",
         "Room 4"};
    
    public String[] buildingList(){return buildingList;}
    public String[] levelList()   {return levelList;}
    public String[] roomList()    {return roomList;}
}