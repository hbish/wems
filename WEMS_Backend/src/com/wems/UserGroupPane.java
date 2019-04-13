/**
 * Write a description of class here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package com.wems;

import java.awt.*;
import javax.swing.*;
import com.wems.config.*;

public class UserGroupPane extends JPanel
{
    private UserGroupTopPane userGroupTopPane;
    private UserGroupBottomPane userGroupBottomPane;
   
    public UserGroupPane(Config config)
    {
        userGroupTopPane = new UserGroupTopPane(config);
        userGroupBottomPane = new UserGroupBottomPane(config);
        userGroupTopPane.setUserGroupBottomPane(userGroupBottomPane);
        userGroupBottomPane.setUserGroupTopPane(userGroupTopPane);
        
        setupLayout();
        setupControls();
    }

    public void refreshData()
    {
        userGroupBottomPane.updateTable();
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }

    private void setupControls()
    {
        add(userGroupTopPane);
        add(userGroupBottomPane);
    }
}