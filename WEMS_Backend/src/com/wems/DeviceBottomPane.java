/**
 * Write a description of class InputPanel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package com.wems;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.wems.config.*;

public class DeviceBottomPane extends JPanel
{
  private DeviceTable table;
  private ButtonListener listener;
  private String actionSelected;
  private DeviceTopPane deviceTopPane;
  private Config config;
  
    public DeviceBottomPane(Config config)
    {
        this.config = config;
        table = new DeviceTable(config);
        setupLayout();
        setupControls();
    }
    
    public void setDeviceTopPane(DeviceTopPane deviceTopPane)
    {
        this.deviceTopPane = deviceTopPane;
        table.setDeviceTopPane(deviceTopPane);
    }

    public void updateTable()
    {
        table.update();
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }

    private void setupControls()
    {
        listener = new ButtonListener();
        Box box = Box.createVerticalBox();
        box.add(table);
        box.add(Box.createVerticalStrut(20));
        box.add(buttonBox());
        add(box);
    }
    
    private Box buttonBox()
    {
        Box box = Box.createHorizontalBox();
        box.add(button("REFRESH", "images/icon-goto.png", listener));
        box.add(Box.createHorizontalStrut(10));
        box.add(button("TOGGLE ADD/MOD/DEL MODE", "images/icon-edit.png", listener));
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        //box.add(Box.createGlue());
        return box;
    }
    
    private JButton button(String label, String iconPath, ButtonListener listener)
    {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        button.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));
        if(config.WIN7_UI_LOOK)
        	button.setBackground(Color.ORANGE);
        return button;
    }
    
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            actionSelected = e.getActionCommand();
            if(actionSelected == "REFRESH")
            {
                if(deviceTopPane.isEditDeleting)
                    deviceTopPane.clearFields();
                updateTable();
            }
            else if(actionSelected == "TOGGLE ADD/MOD/DEL MODE")
            {
                deviceTopPane.buttonToggle();
                table.disposeDetailWindow();
                if (deviceTopPane.isAdding)
                    table.showEditDelHint(false);
                else
                    table.showEditDelHint(true);
                updateTable();
            }
        }
    }
}