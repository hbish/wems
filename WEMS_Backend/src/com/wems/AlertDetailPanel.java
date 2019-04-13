package com.wems;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.wems.config.*;
import com.wems.model.*;

public class AlertDetailPanel extends JPanel
{
    private JLabel alertId,
                   alertType,
                   alertPriority,
                   timeStamp,
                   sensorDataDump;
    
    private Alerts alerts; 
    private int index = 0;
    private JButton nextButton = new JButton();
    private JButton smsButton = new JButton();
    private JFrame parentWindow;
    private SMSDialog smsDialog;
    private Box controlBox;
    
    public AlertDetailPanel(Config config, JFrame parentWindow, Alerts alerts, int index)
    {
        smsDialog = new SMSDialog(config);
        this.parentWindow = parentWindow;
        this.alerts = alerts;
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
        showAlert(index);
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
        alertId = new JLabel("");
        alertType = new JLabel("");
        alertPriority = new JLabel("");
        timeStamp = new JLabel("");
        sensorDataDump = new JLabel("");
        
        box.add(alertId);
        box.add(alertType);
        box.add(alertPriority);
        box.add(timeStamp);
        box.add(sensorDataDump);
        
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
    
    private void showAlert(int index)
    {
        if (alerts.alert(index) != null) {
            updateBox(alerts.alert(index));
        }
        else {
            nextButton.setText("End Record");
            nextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/icon-cross.png")));
            clearBox();
            smsDialog.setSMSMsg("");
        }
    }
    
    private void updateBox(Alert alert)
    {        
        alertId.setText          ("Alert ID: "        + String.valueOf(alert.alertsId()));
        alertType.setText        ("Alert Type: "      + alert.alertType());
        alertPriority.setText    ("Alert Priority: "  + alert.alertPriority());
        timeStamp.setText        ("Alert Time: "      + alert.timeStamp().toString());
        sensorDataDump.setText   ("Sensor Data Dump: "+ String.valueOf(alert.sensorDataDump()));
        smsDialog.setSMSMsg("This is an automated WEMS message warning:\nA WEMS Alert is triggered for " + alertId.getText());
    }
    
    private void clearBox()
    {
        alertId.setText("");
        alertType.setText("");
        alertPriority.setText("");
        timeStamp.setText("");
        sensorDataDump.setText("");
    }
    
    private class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            String actionSelected = e.getActionCommand();
            if(actionSelected == "Next Alert")
                showAlert(++index);
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