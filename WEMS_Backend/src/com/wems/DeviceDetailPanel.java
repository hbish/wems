package com.wems;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.wems.model.*;

public class DeviceDetailPanel extends JPanel
{
    private JLabel deviceuid, // PK
                   id,
                   roomuid,   // FK: sensordata_room
                   userGroup, // FK: user_group
                   type,
                   brand,
                   model,
                   serial,
                   macaddress,
                   status,
                   connected;
    private Devices devices; 
    private int index = 0;
    private JButton button = new JButton();
    private JPanel detailPanel;
    
    public DeviceDetailPanel(Devices devices, int index)
    {
        this.devices = devices;
        this.index = index;
        this.detailPanel = this;
        setupLayout();
        setupControls();
    }

    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }

    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        box.add(infoBox());
        box.add(button("Next Device", "images/icon-right.png", new ButtonListener()));
        add(box);
        showDevice(index);
    }
    
    private Box infoBox()
    {
        Box box = Box.createVerticalBox();
        deviceuid = new JLabel("");
        id = new JLabel("");
        roomuid = new JLabel("");
        userGroup = new JLabel("");
        type = new JLabel("");
        brand = new JLabel("");
        model = new JLabel("");
        serial = new JLabel("");
        macaddress = new JLabel("");
        status = new JLabel("");
        connected = new JLabel("");
        
        box.add(deviceuid);
        box.add(id);
        box.add(roomuid);
        box.add(userGroup);
        box.add(type);
        box.add(brand);
        box.add(model);
        box.add(serial);
        box.add(macaddress);
        box.add(status);
        box.add(connected);
        
        box.setMinimumSize(new Dimension(180, 180));
        box.setMaximumSize(new Dimension(180, 180));
        box.setPreferredSize(new Dimension(180, 180));
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.setBackground(Color.WHITE);
        return box;
    }
    
    private JButton button(String label, String iconPath, ButtonListener listener)
    {
        button.setText(label);
        button.setMinimumSize(new Dimension(110, 20));
        button.setMaximumSize(new Dimension(110, 20));
        button.setPreferredSize(new Dimension(110, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));
        button.addActionListener(listener);
        return button;
    }
    
    private void showDevice(int index)
    {
        if (devices.device(index) != null) {
            updateBox(devices.device(index));
        }
        else {
            button.setText("End Record");
            button.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/icon-cross.png")));
            clearBox();
        }
    }
    
    private void updateBox(Device device)
    {
        deviceuid.setText   ("Device UID: "     + String.valueOf(device.deviceuid()));
        id.setText          ("Device ID: "      + String.valueOf(device.id()));
        roomuid.setText     ("Room UID: "       + String.valueOf(device.roomuid()));
        userGroup.setText   ("Room User Group: "+ String.valueOf(device.userGroup()));  
        type.setText        ("Type: "           + device.type());
        brand.setText       ("Brand: "          + device.brand());
        model.setText       ("Model: "          + device.model());
        serial.setText      ("Serial: "         + device.serial());
        macaddress.setText  ("MAC Address: "    + device.macaddress());
        connected.setText   ("Is Connected?: "  + String.valueOf(device.connected()));
    }
    
    private void clearBox()
    {
        deviceuid.setText("");
        id.setText("");
        roomuid.setText("");
        userGroup.setText("");
        type.setText("");
        brand.setText("");
        model.setText("");
        serial.setText("");
        macaddress.setText("");
        status.setText("");
        connected.setText("");
    }
    
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            String actionSelected = e.getActionCommand();
            if(actionSelected == "Next Device")
                showDevice(++index);
        }
    }
}