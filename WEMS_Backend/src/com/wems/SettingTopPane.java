/**
 * Write a description of class SettingTopPane here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package com.wems;

import java.awt.*;
import javax.swing.*;
import com.wems.config.*;

public class SettingTopPane extends JPanel
{
    private ConfigPane configPane;
    private DatabasePane databasePane;
    private EmailPane emailPane;
    private SMSConfPane smsPane;
    
    public SettingTopPane(Config config)
    {
        setupLayout();
        setupControls(config);
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }
    
    public void updateFields()
    {
        configPane.updateFields();
        databasePane.updateFields();
        emailPane.updateFields();
        smsPane.updateFields();
    }

    private void setupControls(Config config)
    {
        JTabbedPane tabbedPane = new JTabbedPane();
        configPane = new ConfigPane(config);
        databasePane = new DatabasePane(config);
        emailPane = new EmailPane(config);
        smsPane = new SMSConfPane(config);
        
        tabbedPane.addTab("System Configuration", configPane);
        tabbedPane.addTab("Database Configuration", databasePane);
        tabbedPane.addTab("Email Configuration", emailPane);
        tabbedPane.addTab("SMS Configuration", smsPane);
        add(tabbedPane);
    }  
}