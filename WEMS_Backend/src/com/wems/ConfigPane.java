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

public class ConfigPane extends JPanel
{
    private JTextField systemName, maxUser, systemDesc, systemOwner, systemSerial, webUrl, emailContact;
    private ButtonListener listener;
    private String actionSelected;
    private JButton but_save, but_refresh, but_clear;
    private Config config;

    /**
     * Constructor for objects of class
     */
    public ConfigPane(Config config)
    {
        this.config = config;
        setupLayout();
        setupControls();
    }
    
    public void updateFields()
    {
        systemName.setText(config.SYS_USER_NAME);
        systemOwner.setText(config.SYS_USER_OWNER);
        systemDesc.setText(config.SYS_USER_DESC);
        systemSerial.setText(config.SYS_USER_SERIAL);
        maxUser.setText(String.valueOf(config.SYS_MAX_USERS));
        webUrl.setText(config.SYS_WEB_CONSOLE);
        emailContact.setText(config.SYS_EMAIL_CONTACT);
    }
    
    private void clearText()
    {
        systemName.setText("");
        systemOwner.setText("");
        systemDesc.setText("");
        systemSerial.setText("");
        maxUser.setText("");
        webUrl.setText("");
        emailContact.setText("");
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
        setSize(900,400);
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
        
        systemName = new JTextField(config.SYS_USER_NAME, 72);
        systemName.setToolTipText("System Name");
        systemOwner = new JTextField(String.valueOf(config.SYS_USER_OWNER), 8);
        systemOwner.setToolTipText("System Owner");
        systemDesc = new JTextField(String.valueOf(config.SYS_USER_DESC), 8);
        systemDesc.setToolTipText("System Description");
        systemSerial = new JTextField(String.valueOf(config.SYS_USER_SERIAL), 8);
        systemSerial.setToolTipText("System Serial");
        maxUser = new JTextField(String.valueOf(config.SYS_MAX_USERS), 8);
        maxUser.setToolTipText("Maximum Users");
        webUrl = new JTextField(config.SYS_WEB_CONSOLE, 8);
        webUrl.setToolTipText("Web Console URL Site");
        emailContact = new JTextField(config.SYS_EMAIL_CONTACT, 8);
        emailContact.setToolTipText("Admin Email Contact");
        
        vBox.add(textfieldBox(systemName, "System Name: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(systemOwner, "System Owner: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(systemDesc, "System Description: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(systemSerial, "System Serial No.: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(maxUser, "Max Users: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(webUrl, "Web Console URL: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(emailContact, "Admin Email Contact: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(buttonBox());
        
        panel.add(vBox);
        return panel;
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
        //box.add(Box.createGlue());
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
                    if (systemName.getText().equals(""))                warningString += " - System Name Invalid\n";
                    if (systemDesc.getText().equals(""))                warningString += " - System Description Invalid\n";
                    if (systemSerial.getText().equals(""))              warningString += " - Serial Number Invalid\n";
                    if (systemOwner.getText().equals(""))               warningString += " - System Owner Invalid\n";
                    if (maxUser.getText().equals(""))                   warningString += " - Max Users Invalid\n";
                    if (webUrl.getText().equals(""))                    warningString += " - Web URL Invalid\n";
                    if (emailContact.getText().equals(""))              warningString += " - Email Contact Invalid\n";
                    try {if (Integer.valueOf(maxUser.getText()) < 0)    warningString += " - Max Users Must be greater than 0\n";}
                    catch (Exception ex)                               {warningString += " - Max Users Must be a number\n";}
                    if (!warningString.equals("")) {
                        warningDialog(warningString);
                        return;
                    }
                    config.SYS_USER_NAME = systemName.getText();
                    config.SYS_USER_OWNER = systemOwner.getText();
                    config.SYS_USER_DESC = systemDesc.getText();
                    config.SYS_USER_SERIAL = systemSerial.getText();
                    config.SYS_MAX_USERS = Integer.valueOf(maxUser.getText());
                    config.SYS_WEB_CONSOLE = webUrl.getText();
                    config.SYS_EMAIL_CONTACT = emailContact.getText();
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