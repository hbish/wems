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

public class EmailPane extends JPanel
{
    private JTextField SMTP_Host, SMTP_Port, Mail_From, Mail_Username, Mail_Password;
    private JComboBox Mail_SMTP_Auth, Mail_Debug;
    String[] booleanStrings = {"true", "false"};
    private ButtonListener listener;
    private String actionSelected;
    private JButton but_save, but_refresh, but_clear;
    private Config config;

    /**
     * Constructor for objects of class
     */
    public EmailPane(Config config)
    {
        this.config = config;
        setupLayout();
        setupControls();
    }
    
    public void updateFields()
    {
        SMTP_Host.setText(config.MAIL_SMTP_HOST);
        SMTP_Port.setText(String.valueOf(config.MAIL_SMTP_PORT));
        Mail_From.setText(config.MAIL_FROM);
        Mail_Username.setText(config.MAIL_SMTP_USER);
        Mail_Password.setText(config.MAIL_SMTP_PASS);
        Mail_SMTP_Auth.setSelectedItem(config.MAIL_SMTP_AUTH);
        Mail_Debug.setSelectedItem(config.MAIL_DEBUG);
    }
    
    private void clearText()
    {
        SMTP_Host.setText("");
        SMTP_Port.setText("");
        Mail_From.setText("");
        Mail_Username.setText("");
        Mail_Password.setText("");
        Mail_SMTP_Auth.setSelectedIndex(-1);
        Mail_Debug.setSelectedIndex(-1);
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
        
        Mail_SMTP_Auth = new JComboBox(booleanStrings);
        Mail_SMTP_Auth.setToolTipText("SMTP Authentication");
        Mail_SMTP_Auth.setSelectedItem(config.MAIL_SMTP_AUTH);
        Mail_Debug = new JComboBox(booleanStrings);
        Mail_Debug.setToolTipText("Mail Debug Flag");
        Mail_Debug.setSelectedItem(config.MAIL_DEBUG);
        
        SMTP_Host= new JTextField(config.MAIL_SMTP_HOST, 72);
        SMTP_Host.setToolTipText("SMTP Host");
        SMTP_Port = new JTextField(String.valueOf(config.MAIL_SMTP_PORT), 10);
        SMTP_Port.setToolTipText("SMTP Port");
        Mail_From = new JTextField(config.MAIL_FROM, 10);
        Mail_From.setToolTipText("Mail From");
        Mail_Username = new JTextField(config.MAIL_SMTP_USER, 10);
        Mail_Username.setToolTipText("Username");
        Mail_Password = new JTextField(config.MAIL_SMTP_PASS, 10);
        Mail_Password.setToolTipText("Password");     
        
        vBox.add(textfieldBox(SMTP_Host, "SMTP Host: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(SMTP_Port, "SMTP Port: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(Mail_From, "Mail From: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(Mail_Username, "Mail Username: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(Mail_Password, "Mail Password: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(comboBox(Mail_SMTP_Auth, "Mail SMTP Auth: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(comboBox(Mail_Debug, "Mail Debug: "));
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
                    if (SMTP_Host.getText().equals(""))      warningString += " - SMTP Mail Host Invalid\n";
                    if (SMTP_Port.getText().equals(""))      warningString += " - SMTP Port Invalid\n";
                    if (Mail_From.getText().equals(""))      warningString += " - Mail From Address Invalid\n";
                    if (Mail_Username.getText().equals(""))  warningString += " - Mail Username Invalid\n";
                    if (Mail_Password.getText().equals(""))  warningString += " - Mail Password Invalid\n";
                    try {if (Integer.valueOf(SMTP_Port.getText()) < 0) warningString += " - SMTP Port Must be greater than 0\n";}
                    catch (Exception ex)                              {warningString += " - SMTP Port Must be a number\n";}
                    if (Mail_SMTP_Auth.getSelectedIndex() < 0)  warningString += " - SMTP Auth Selection Invalid\n";
                    if (Mail_Debug.getSelectedIndex() < 0)      warningString += " - Mail Debug Selection Invalid\n";
                    if (!warningString.equals("")) {
                        warningDialog(warningString);
                        return;
                    }
                    config.MAIL_SMTP_HOST = SMTP_Host.getText();
                    config.MAIL_SMTP_PORT = Integer.valueOf(SMTP_Port.getText());
                    config.MAIL_FROM      = Mail_From.getText();
                    config.MAIL_SMTP_USER = Mail_Username.getText();
                    config.MAIL_SMTP_PASS = Mail_Password.getText();
                    config.MAIL_SMTP_AUTH = (String)Mail_SMTP_Auth.getSelectedItem();
                    config.MAIL_DEBUG     = (String)Mail_Debug.getSelectedItem();
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