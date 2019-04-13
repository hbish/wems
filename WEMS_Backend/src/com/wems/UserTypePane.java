/**
 * Write a description of class here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package com.wems;

import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.*;
import com.wems.business.*;
import com.wems.config.*;
import com.wems.model.*;

public class UserTypePane extends JPanel
{
    private JTextField userTypeName, userTypeDescription;
    private ButtonListener listener;
    private String actionSelected;
    private UserTypeTable table;
    private JButton but_add, but_edit, but_del, but_clear, but_toggle, but_refresh;
    private Config config;
    public boolean isToEdit = false;

    /**
     * Constructor for objects of class
     */
    public UserTypePane(Config config)
    {
        this.config = config;
        table = new UserTypeTable(config);
        table.setUserTypePane(this);
        setupLayout();
        setupControls();
    }
    
    public void refreshData()
    {
        table.update();
    }
    
    public void updateFields(UserType userType)
    {
        userTypeName.setText(userType.userTypeName());
        userTypeDescription.setText(userType.userTypeDescription());
    }
        
    public void clearFields()
    {
        userTypeName.setText("");
        userTypeDescription.setText("");
    }
    
    private void toggleButtons()
    {
        if (isToEdit)
            isToEdit = false;
        else
            isToEdit = true;
        but_add.setEnabled(!isToEdit);
        but_edit.setEnabled(isToEdit);
        but_del.setEnabled(isToEdit);
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
        
        userTypeName = new JTextField(8);
        userTypeName.setToolTipText("User Type Name");
        userTypeDescription = new JTextField(8);
        userTypeDescription.setToolTipText("User Type Description");
        
        vBox.add(Box.createVerticalStrut(5));
        vBox.add(textfieldBox(userTypeName, "User Type Name:  "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(userTypeDescription, "User Type Description:  "));
        vBox.add(Box.createVerticalStrut(5));
        vBox.setBorder(titleBorder("User Type Details"));
        
        Box mainBox = Box.createVerticalBox();
        mainBox.add(vBox);
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(buttonBox());
        mainBox.add(Box.createVerticalStrut(10));
        mainBox.add(table);
        mainBox.add(toggleBox());
        
        panel.add(mainBox);
        return panel;
    }
    
    private TitledBorder titleBorder(String label)
    {
        TitledBorder topBorder = BorderFactory.createTitledBorder(label);
        topBorder.setTitlePosition(TitledBorder.TOP);
        topBorder.setTitleFont(new Font("Sans Serif", Font.BOLD, 12));
        return topBorder;
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
        but_add = button("ADD", true, Color.GREEN, "images/icon-plus.png", listener);
        but_edit = button("EDIT", false, Color.ORANGE, "images/icon-modify.png", listener);
        but_del = button("DELETE", false, Color.RED, "images/icon-cross.png", listener);
        but_clear = button("CLEAR", true, Color.GRAY, "images/icon-clear.png", listener);
        box.add(but_add);
        box.add(Box.createHorizontalStrut(10));
        box.add(but_edit);
        box.add(Box.createHorizontalStrut(10));
        box.add(but_del);
        box.add(Box.createHorizontalStrut(10));
        box.add(but_clear);
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        //box.add(Box.createGlue());
        return box;
    }
    
    private Box toggleBox()
    {
        Box box = Box.createHorizontalBox(); 
        but_refresh = button("REFRESH", true, Color.ORANGE, "images/icon-goto.png", listener);
        but_toggle = button("TOGGLE ADD/MOD/DEL MODE", true, Color.ORANGE, "images/icon-edit.png", listener);
        box.add(but_refresh);
        box.add(Box.createHorizontalStrut(10));
        box.add(but_toggle);
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        //box.add(Box.createGlue());
        return box;
    }
    
    private JButton button(String label, boolean isEnabled, Color color, String iconPath, ButtonListener listener)
    {
        JButton button = new JButton(label);
        button.setEnabled(isEnabled);
        button.addActionListener(listener);
        button.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));
        if (color != null && config.WIN7_UI_LOOK)
            button.setBackground(color);
        return button;
    }
    
    private void warningDialog(String warningString)
    {
        JOptionPane.showMessageDialog(this, warningString, "Data Entry Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    private class ButtonListener implements ActionListener 
    {
        String warningString = ""; 
        
        public void actionPerformed(ActionEvent e) 
        {
            actionSelected = e.getActionCommand();
            switch (actionSelected)
            {
                case "ADD":
                {
                    try
                    {
                        String s_userTypeName = userTypeName.getText();
                        String s_userTypeDescription = userTypeDescription.getText();
                        if (s_userTypeName.equals(""))       warningString += " - User Type Name Invalid\n";
                        if (s_userTypeDescription.equals(""))warningString += " - User Type Description Invalid\n";
                        if (!warningString.equals("")) {
                            warningDialog(warningString);
                            return;
                        }
                        BusinessLogic bl = new BusinessLogic(config);
                        bl.addUserType(s_userTypeName, s_userTypeDescription);
                        refreshData();
                        clearFields();
                    }
                    catch (Exception ex)
                    {
                        warningDialog("Error occurred while adding user type to database.");
                    }
                    break;
                }
                case "EDIT":
                {
                    if (table.getSelectedUserTypeId() < 0) {
                        warningDialog("You have not selected a valid User Type ID to edit.");
                        return;
                    }
                    try
                    {
                        String s_userTypeName = userTypeName.getText();
                        String s_userTypeDescription = userTypeDescription.getText();
                        if (s_userTypeName.equals(""))       warningString += " - User Type Name Invalid\n";
                        if (s_userTypeDescription.equals(""))warningString += " - User Type Description Invalid\n";
                        if (!warningString.equals("")) {
                            warningDialog(warningString);
                            return;
                        }
                        BusinessLogic bl = new BusinessLogic(config);
                        bl.editUserType(table.getSelectedUserTypeId(), s_userTypeName, s_userTypeDescription);
                        refreshData();
                        clearFields();
                    }
                    catch (Exception ex)
                    {
                        warningDialog("You have not selected a valid User Type ID to edit.");
                    }
                    break;
                }
                case "DELETE":
                {
                    if (table.getSelectedUserTypeId() < 0) {
                        warningDialog("You have not selected a valid User Type ID to delete.");
                        return;
                    }
                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete this user type?\nPlease note, you will not be able to recover this details.", "Confirm User Type Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        try
                        {
                            BusinessLogic bl = new BusinessLogic(config);
                            bl.deleteUserType(table.getSelectedUserTypeId());
                            refreshData();
                            clearFields();
                        }
                        catch (Exception ex)
                        {
                            warningDialog("You have not selected a valid User Type ID to delete.");
                        }
                    }
                    break;
                }
                case "REFRESH":
                {
                    refreshData();
                    clearFields();
                    break;
                }
                case "CLEAR":
                {
                    clearFields();
                    break;
                }
                case "TOGGLE ADD/MOD/DEL MODE":
                {
                    toggleButtons();
                    refreshData();
                    clearFields();
                    table.disposeDetailWindow();
                    break;
                }
            }
        }
    }
}