package com.wems;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.wems.business.*;
import com.wems.config.*;
import com.wems.model.*;
import com.wems.model.Event;

public class EventDetailPanel extends JPanel
{
    private JLabel eventId,
                   deviceId,
                   alertId,
                   event,
                   timeStamp,
                   alertValues0,
                   alertValues1,
                   alertValues2,
                   alertValues3,
                   alertValues4;
    
    private Events events; 
    private int index = 0;
    private Config config;
    private JButton nextButton = new JButton(), smsButton = new JButton();
    private JFrame parentWindow;
    private SMSDialog smsDialog;
    private Box controlBox;
    
    public EventDetailPanel(Config config, JFrame parentWindow, Events events, int index)
    {
        smsDialog = new SMSDialog(config);
        this.parentWindow = parentWindow;
        this.config = config;
        this.events = events;
        this.index = index;
        setupLayout();
        setupControls();
    }

    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }

    private void setupControls()
    {
        controlBox = Box.createVerticalBox();
        controlBox.add(infoBox());
        controlBox.add(buttons());
        add(controlBox);
        showEvent(index);
    }
    
    private Box buttons()
    {
        Box hBox = Box.createHorizontalBox();
        hBox.add(button(nextButton, "Next Alert", "images/icon-right.png", new ButtonListener()));
        hBox.add(Box.createHorizontalStrut(10));
        hBox.add(button(smsButton, "Open SMS", "images/icon-mail.png", new ButtonListener()));
        return hBox;
    }
    
    private Box infoBox()
    {
        Box box = Box.createVerticalBox();
        eventId = new JLabel("");
        deviceId = new JLabel("");
        alertId = new JLabel("");
        event = new JLabel("");
        timeStamp = new JLabel("");
        alertValues0 = new JLabel("");
        alertValues1 = new JLabel("");
        alertValues2 = new JLabel("");
        alertValues3 = new JLabel("");
        alertValues4 = new JLabel("");
        
        box.add(eventId);
        box.add(deviceId);
        box.add(alertId);
        box.add(event);
        box.add(timeStamp);
        box.add(alertValues0);
        box.add(alertValues1);
        box.add(alertValues2);
        box.add(alertValues3);
        box.add(alertValues4);
        
        box.setMinimumSize(new Dimension(200, 180));
        box.setMaximumSize(new Dimension(200, 180));
        box.setPreferredSize(new Dimension(200, 180));
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.setBackground(Color.WHITE);
        return box;
    }
    
    private JButton button(JButton button, String label, String iconPath, ButtonListener listener)
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
    
    private void showEvent(int index)
    {
        if (events.event(index) != null) {
            updateBox(events.event(index));
        }
        else {
            nextButton.setText("End Record");
            nextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/icon-cross.png")));
            clearBox();
            smsDialog.setSMSMsg("");
        }
    }
    
    private void updateBox(Event event)
    {        
        eventId.setText   ("Event ID: "     + String.valueOf(event.eventsId()));
        deviceId.setText  ("Device ID: "    + String.valueOf(event.deviceId()));
        alertId.setText   ("Alert ID: "     + String.valueOf(event.alertId()));
        this.event.setText("Event Type: "   + String.valueOf(event.eventType()));
        timeStamp.setText ("Event Time: "   + event.timeStamp().toString());
        smsDialog.setSMSMsg("This is an automated WEMS message warning:\nA WEMS Event is notified for " + eventId.getText());
        try
        {
            BusinessLogic bl = new BusinessLogic(config);
            Alert alert = bl.getAlert(event.alertId());
            alertValues0.setText("=== Detail for Alert ID: " + String.valueOf(alert.alertsId()) + " ===");
            alertValues1.setText("Alert Type: "              + String.valueOf(alert.alertType()));
            alertValues2.setText("Alert Priority: "          + String.valueOf(alert.alertPriority()));
            alertValues3.setText("Alert Time: "              + alert.timeStamp().toString());
            alertValues4.setText("Sensor Data Dump:"         + String.valueOf(alert.sensorDataDump()));
        }
        catch (Exception e)
        {
            alertValues0.setText("=== No Alert Details ===");
            alertValues1.setText("-/-");
            alertValues2.setText("-/-");
            alertValues3.setText("-/-");
            alertValues4.setText("-/-");
        }
    }
    
    private void clearBox()
    {
        eventId.setText("");
        deviceId.setText("");
        alertId.setText("");
        event.setText("");
        timeStamp.setText("");
        alertValues0.setText("");
        alertValues1.setText("");
        alertValues2.setText("");
        alertValues3.setText("");
        alertValues4.setText("");
    }
    
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            String actionSelected = e.getActionCommand();
            if(actionSelected == "Next Alert")
                showEvent(++index);
            else if(actionSelected == "Open SMS") {
                controlBox.add(smsDialog);
                revalidate();
                smsButton.setText("Close SMS");
                smsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/icon-cross.png")));
                parentWindow.setSize(404, 615);
            }
            else if(actionSelected == "Close SMS") {
                controlBox.remove(smsDialog);
                revalidate();
                smsButton.setText("Open SMS");
                smsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/icon-mail.png")));
                parentWindow.setSize(255, 245);
            }
        }
    }
}