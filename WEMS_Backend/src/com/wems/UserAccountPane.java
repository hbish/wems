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
import com.wems.model.*;

public class UserAccountPane extends JPanel
{
    private UserAccountTopPane userAccountTopPane;
    private UserAccountBottomPane userAccountBottomPane;
   
    public UserAccountPane(Config config, User userAccess)
    {
        userAccountTopPane = new UserAccountTopPane(config, userAccess);
        userAccountBottomPane = new UserAccountBottomPane(config);
        userAccountTopPane.setUserAccountBottomPane(userAccountBottomPane);
        userAccountBottomPane.setUserAccountTopPane(userAccountTopPane);
        setupLayout();
        setupControls();
    }
    
    public void refreshData()
    {
        userAccountTopPane.updateFields();
        userAccountBottomPane.updateTable();
    }

    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }

    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        box.add(userAccountTopPane);
        box.add(userAccountBottomPane);
        add(box);
    }
}