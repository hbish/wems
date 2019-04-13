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

public class DatabasePane extends JPanel
{
    private JTextField dbName, dbUser, dbPassword, dbHost, dbClassName, dbProtocol;
    private ButtonListener listener;
    private String actionSelected;
    private JButton but_save, but_refresh, but_clear;
    private Config config;

    /**
     * Constructor for objects of class
     */
    public DatabasePane(Config config)
    {
        this.config = config;
        setupLayout();
        setupControls();
    }
    
    public void updateFields()
    {
        dbName.setText(config.DB_NAME);
        dbUser.setText(config.DB_USER);
        dbPassword.setText(config.DB_PASSWORD);
        dbHost.setText(config.DB_HOST);
        dbClassName.setText(config.DB_JDBC_CLASS_NAME);
        dbProtocol.setText(config.DB_JDBC_PROTOCOL);
    }
    
    private void clearText()
    {
        dbName.setText("");
        dbUser.setText("");
        dbPassword.setText("");
        dbHost.setText("");
        dbClassName.setText("");
        dbProtocol.setText("");
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
        
        dbName= new JTextField(config.DB_NAME, 70);
        dbName.setToolTipText("DB Name");
        dbUser = new JTextField(config.DB_USER, 10);
        dbUser.setToolTipText("DB User");
        dbPassword = new JTextField(config.DB_PASSWORD, 10);
        dbPassword.setToolTipText("DB Password");
        dbHost = new JTextField(config.DB_HOST, 10);
        dbHost.setToolTipText("DB Host");
        dbClassName = new JTextField(config.DB_JDBC_CLASS_NAME, 10);
        dbClassName.setToolTipText("DB Classname");
        dbProtocol = new JTextField(config.DB_JDBC_PROTOCOL, 10);
        dbProtocol.setToolTipText("DB Protocol");
        
        vBox.add(textfieldBox(dbName, "Database Name: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(dbUser, "Database User: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(dbPassword, "Database Password: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(dbHost, "Database Host: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(dbClassName, "Database Class Name: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(dbProtocol, "Database Protocol: "));
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
                    if (dbName.getText().equals(""))      warningString += " - DB Name Invalid\n";
                    if (dbUser.getText().equals(""))      warningString += " - DB Username Invalid\n";
                    if (dbPassword.getText().equals(""))  warningString += " - DB Password Invalid\n";
                    if (dbHost.getText().equals(""))      warningString += " - DB Host Invalid\n";
                    if (dbClassName.getText().equals("")) warningString += " - DB Classname Invalid\n";
                    if (dbProtocol.getText().equals(""))  warningString += " - DB Protocol Invalid\n";
                    if (!warningString.equals("")) {
                        warningDialog(warningString);
                        return;
                    }
                    config.DB_NAME            = dbName.getText();
                    config.DB_USER            = dbUser.getText();
                    config.DB_PASSWORD        = dbPassword.getText();
                    config.DB_HOST            = dbHost.getText();
                    config.DB_JDBC_CLASS_NAME = dbClassName.getText();
                    config.DB_JDBC_PROTOCOL   = dbProtocol.getText();
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