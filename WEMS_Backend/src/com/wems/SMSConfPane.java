/**
 * Write a description of class here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package com.wems;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.wems.config.*;

public class SMSConfPane extends JPanel
{
    private JTextField SMS_Host_path, SMS_Host_username, SMS_Host_password, SMS_Valid_digits, SMS_Max_numbers;
    private ButtonListener listener;
    private String actionSelected;
    private JButton but_save, but_refresh, but_clear;
    private Config config;

    /**
     * Constructor for objects of class
     */
    public SMSConfPane(Config config)
    {
        this.config = config;
        setupLayout();
        setupControls();
    }
    
    public void updateFields()
    {
        SMS_Host_username.setText(config.SMS_HOST_USERNAME);
        SMS_Host_password.setText(config.SMS_HOST_PASSWORD);
        SMS_Valid_digits.setText(String.valueOf(config.SMS_VALIDNUMBER));
        SMS_Max_numbers.setText(String.valueOf(config.SMS_MAXNUMBERS));
    }
    
    private void clearText()
    {
        SMS_Host_username.setText("");
        SMS_Host_password.setText("");
        SMS_Valid_digits.setText("");
        SMS_Max_numbers.setText("");
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }

    private void setupControls()
    {
        listener = new ButtonListener();
        add(mainGrid());
    }
    
    private JPanel mainGrid()
    {
        JPanel panel = new JPanel();
        Box vBox = Box.createVerticalBox();
        
        SMS_Host_path = new JTextField(config.SMS_GATEWAY_URL_1 + config.SMS_GATEWAY_URL_2 + config.SMS_GATEWAY_URL_3 + config.SMS_GATEWAY_URL_4, 70);
        SMS_Host_path.setToolTipText("SMS API Gateway Path (Non-editable Field)");
        SMS_Host_path.setEditable(false);
        SMS_Host_username = new JTextField(config.SMS_HOST_USERNAME, 70);
        SMS_Host_username.setToolTipText("SMS API Username");
        SMS_Host_password = new JTextField(config.SMS_HOST_PASSWORD, 70);
        SMS_Host_password.setToolTipText("SMS API Password");
        SMS_Valid_digits = new JTextField(String.valueOf(config.SMS_VALIDNUMBER), 10);
        SMS_Valid_digits.setToolTipText("SMS Valid Number in Digits i.e. XX XXX XXX XXX");
        SMS_Max_numbers = new JTextField(String.valueOf(config.SMS_MAXNUMBERS), 10);
        SMS_Max_numbers.setToolTipText("SMS API Max Numbers to Message");
        
        vBox.add(textfieldBox(SMS_Host_path, "SMS API Path: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(SMS_Host_username, "SMS API Username: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(SMS_Host_password, "SMS API Password: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(SMS_Max_numbers, "SMS API Max Numbers: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(SMS_Valid_digits, "Mob. Number Digits: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(buttonBox());
        
        panel.add(vBox);
        return panel;
    }
    
    private Box comboBox(JComboBox field, String label)
    {
        Box box = Box.createHorizontalBox();   
        box.add(new JLabel(label));
        box.add(Box.createHorizontalStrut(10));
        box.add(field);
        box.add(Box.createGlue());
        return box;
    }
    
    private Box textfieldBox(JTextField field, String label)
    {
        Box box = Box.createHorizontalBox();   
        box.add(new JLabel(label));
        box.add(Box.createHorizontalStrut(10));
        box.add(field);
        box.add(Box.createGlue());
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        return box;
    }
    
    private Box buttonBox()
    {
        Box box = Box.createHorizontalBox(); 
        but_save = button("SAVE", true, Color.GREEN, "images/icon-save.png", listener);
        but_refresh = button("REFRESH", true, Color.ORANGE, "images/icon-refresh.png", listener);
        but_clear = button("CLEAR", true, Color.GRAY, "images/icon-clear.png", listener);
        box.add(but_save);
        box.add(Box.createHorizontalStrut(10));
        box.add(but_refresh);
        box.add(Box.createHorizontalStrut(10));
        box.add(but_clear);
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        return box;
    }
    
    private JButton button(String label, boolean isEnabled, Color color, String iconPath, ButtonListener listener)
    {
        JButton button = new JButton(label);
        button.setEnabled(isEnabled);
        button.addActionListener(listener);
        button.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));
        if(config.WIN7_UI_LOOK)
        	button.setBackground(color);
        return button;
    }
    
    private void warningDialog(String warningString)
    {
        JOptionPane.showMessageDialog(this, warningString, "Data Entry Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            actionSelected = e.getActionCommand();
            switch (actionSelected)
            {
                case "SAVE":
                {
                    String warningString = "";
                    if (SMS_Host_username.getText().equals(""))      warningString += " - SMS API Username Invalid\n";
                    if (SMS_Host_password.getText().equals(""))      warningString += " - SMS API Password Invalid\n";
                    if (SMS_Max_numbers.getText().equals(""))        warningString += " - SMS API Max Nos Invalid\n";
                    if (SMS_Valid_digits.getText().equals(""))       warningString += " - SMS API Valid No Invalid\n";
                    
                    try {if (Integer.valueOf(SMS_Max_numbers.getText()) < 0)   warningString += " - SMS Max Nos Must be greater than 0\n";}
                    catch (Exception ex)                                      {warningString += " - SMS Max Nos Must be a number\n";}
                    try {if (Integer.valueOf(SMS_Valid_digits.getText()) < 0)  warningString += " - SMS API Valid No Must be greater than 0\n";}
                    catch (Exception ex)                                      {warningString += " - SMS API Valid No Must be a number\n";}
                    
                    if (!warningString.equals("")) {
                        warningDialog(warningString);
                        return;
                    }
                    config.SMS_HOST_USERNAME = SMS_Host_username.getText();
                    config.SMS_HOST_PASSWORD = SMS_Host_password.getText();
                    config.SMS_VALIDNUMBER   = Integer.valueOf(SMS_Valid_digits.getText());
                    config.SMS_MAXNUMBERS    = Integer.valueOf(SMS_Max_numbers.getText());
                    break;
                }
                case "REFRESH":
                {
                    updateFields();
                    break;
                }
                case "CLEAR":
                {
                    clearText();
                    break;
                }
            }
        }
    }
}