/**
 * Write a description of class InputPanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package com.wems;

import java.awt.*;
import javax.swing.*;
import com.wems.config.*;

public class DevicePane extends JPanel
{
    private DeviceTopPane deviceTopPane;
    private DeviceBottomPane deviceBottomPane;
   
    public DevicePane(Config config)
    {
        deviceTopPane = new DeviceTopPane(config);
        deviceBottomPane = new DeviceBottomPane(config);
        deviceTopPane.setDeviceBottomPane(deviceBottomPane);
        deviceBottomPane.setDeviceTopPane(deviceTopPane);
        setupLayout();
        setupControls();
    }
    
    public void refreshData()
    {
        deviceTopPane.updateFields();
        deviceBottomPane.updateTable();
    }

    private void setupLayout()
    {
        setLayout(new FlowLayout());
        setSize(600,800);
    }

    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        box.add(deviceTopPane);
        box.add(deviceBottomPane);
        add(box);
    }
}