package com.wems;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.wems.config.*;
import com.wems.model.*;

public class AlertSettingDetailPanel extends JPanel
{
    private JLabel alertId,
                   alertUserGroup,
                   score,
                   dataParameterId,
                   alertType,
                   alertPriority,
                   timestamp,
                   minThresholdValue,
                   maxThresholdValue,
                   exactThresholdValue,
                   minThresholdEnabled,
                   maxThresholdEnabled,
                   exactThresholdEnabled,
                   scoreEnabled;
    
    private AlertSettings alertSettings;
    private int index = 0;
    private Config config;
    private JButton nextButton = new JButton(), smsButton = new JButton();
    private JFrame parentWindow;
    private SMSDialog smsDialog;
    private Box controlBox;
    
    public AlertSettingDetailPanel(Config config, JFrame parentWindow, AlertSettings alertSettings, int index)
    {
        smsDialog = new SMSDialog(config);
        this.parentWindow = parentWindow;
        this.config = config;
        this.alertSettings = alertSettings;
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
        showAlertSetting(index);
    }
    
    private Box buttons()
    {
        Box hBox = Box.createHorizontalBox();
        hBox.add(button(nextButton, "Next Setting", "images/icon-right.png", new ButtonListener()));
        hBox.add(Box.createHorizontalStrut(10));
        hBox.add(button(smsButton, "Open SMS", "images/icon-mail.png", new ButtonListener()));
        return hBox;
    }
    
    private Box infoBox()
    {
        Box box = Box.createVerticalBox();
        alertId = new JLabel("");
        alertUserGroup = new JLabel("");
        score = new JLabel("");
        dataParameterId = new JLabel("");
        alertType = new JLabel("");
        alertPriority = new JLabel("");
        timestamp = new JLabel("");
        minThresholdValue = new JLabel("");
        maxThresholdValue = new JLabel("");
        exactThresholdValue = new JLabel("");
        minThresholdEnabled = new JLabel("");
        maxThresholdEnabled = new JLabel("");
        exactThresholdEnabled = new JLabel("");
        scoreEnabled = new JLabel("");
        
        box.add(alertId);
        box.add(alertUserGroup);
        box.add(score);
        box.add(dataParameterId);
        box.add(alertType);
        box.add(alertPriority);
        box.add(timestamp);
        box.add(minThresholdValue);
        box.add(maxThresholdValue);
        box.add(exactThresholdValue);
        box.add(minThresholdEnabled);
        box.add(maxThresholdEnabled);
        box.add(exactThresholdEnabled);
        box.add(scoreEnabled);
        
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
    
    private void showAlertSetting(int index)
    {
        if (alertSettings.alertSetting(index) != null) {
            updateBox(alertSettings.alertSetting(index));
        }
        else {
            nextButton.setText("End Record");
            nextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/icon-cross.png")));
            clearBox();
            smsDialog.setSMSMsg("");
        }
    }
    
    private void updateBox(AlertSetting alertSetting)
    {        
        alertId.setText("Alert Setting ID: " + String.valueOf(alertSetting.alertId()));
        alertUserGroup.setText("Alert User Group ID: " + String.valueOf(alertSetting.alertUserGroup()));
        score.setText("Alert Score: " + String.valueOf(alertSetting.score()));
        dataParameterId.setText("Sensor Data ID: " + String.valueOf(alertSetting.dataParameterId()));
        alertType.setText("Alert Type: " + alertSetting.alertType());
        alertPriority.setText("Alert Priority: " + alertSetting.alertPriority());
        timestamp.setText("Alert Timestamp: " + alertSetting.timestamp().toString());
        minThresholdValue.setText("Alert Min Thresh Val: " + String.valueOf(alertSetting.minThresholdValue()));
        maxThresholdValue.setText("Alert Max Thresh Val: " + String.valueOf(alertSetting.maxThresholdValue()));
        exactThresholdValue.setText("Alert Exact Thresh Val: " + String.valueOf(alertSetting.exactThresholdValue()));
        minThresholdEnabled.setText("Alert Min Thresh Bool: " + String.valueOf(alertSetting.minThresholdEnabled()));
        maxThresholdEnabled.setText("Alert Max Thresh Bool: " + String.valueOf(alertSetting.maxThresholdEnabled()));
        exactThresholdEnabled.setText("Alert Exact Thresh Bool: " + String.valueOf(alertSetting.exactThresholdEnabled()));
        scoreEnabled.setText("Alert Score Enabled: " + String.valueOf(alertSetting.scoreEnabled()));
        smsDialog.setSMSMsg("This is an automated WEMS message warning:\nA WEMS Alert Setting is set for " + alertId.getText());
    }
    
    private void clearBox()
    {
        alertId.setText("");
        alertUserGroup.setText("");
        score.setText("");
        dataParameterId.setText("");
        alertType.setText("");
        alertPriority.setText("");
        timestamp.setText("");
        minThresholdValue.setText("");
        maxThresholdValue.setText("");
        exactThresholdValue.setText("");
        minThresholdEnabled.setText("");
        maxThresholdEnabled.setText("");
        exactThresholdEnabled.setText("");
        scoreEnabled.setText("");
    }
    
    private class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) 
        {
            String actionSelected = e.getActionCommand();
            if(actionSelected == "Next Setting")
                showAlertSetting(++index);
            else if(actionSelected == "Open SMS") {
                controlBox.add(smsDialog);
                revalidate();
                smsButton.setText("Close SMS");
                smsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/icon-cross.png")));
                parentWindow.setSize(424, 620);
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