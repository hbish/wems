package com.wems;

import javax.swing.*;
import com.wems.model.*;

public class DeviceDetailWindow extends JFrame
{
    public DeviceDetailWindow(Devices devices, int index)
    {
        setupLayout();
        setupControls(devices, index);
        build();
    }

    private void setupLayout()
    {
        setTitle("Device Details");
        setLocation(450, 500);
    }

    private void setupControls(Devices devices, int index)
    {
        DeviceDetailPanel detailPanel = new DeviceDetailPanel(devices, index);
        add(detailPanel);
    }

    private void build()
    {
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}