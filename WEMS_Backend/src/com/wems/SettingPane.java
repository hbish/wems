/**
 * Write a description of class here
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package com.wems;

import java.awt.*;
import javax.swing.*;
import com.wems.config.*;

public class SettingPane extends JPanel
{
    private SettingTopPane settingTopPane;
    private SettingBottomPane settingBottomPane;
   
    public SettingPane(Config config)
    {
        settingTopPane = new SettingTopPane(config);
        settingBottomPane = new SettingBottomPane();
        setupLayout();
        setupControls();
    }

    public void refreshData()
    {
        settingTopPane.updateFields();
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
        setSize(600,800);
    }

    private void setupControls()
    {
        add(settingTopPane);
        add(settingBottomPane);
    }
}