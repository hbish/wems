package com.wems;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.wems.model.*;

public class UGDetailPanel extends JPanel
{
    private JLabel userGroupName,
                   userGroupEmail,
                   userGroupDescription;
                
    private UserGroups userGroups; 
    private int index = 0;
    private JButton button = new JButton();
    
    public UGDetailPanel(UserGroups userGroups, int index)
    {
        this.userGroups = userGroups;
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
        Box box = Box.createVerticalBox();
        box.add(infoBox());
        box.add(button("Next User Group", "images/icon-right.png", new ButtonListener()));
        add(box);
        showUserGroup(index);
    }
    
    private Box infoBox()
    {
        Box box = Box.createVerticalBox();
        userGroupName = new JLabel("");
        userGroupEmail = new JLabel("");
        userGroupDescription = new JLabel("");
        
        box.add(userGroupName);
        box.add(userGroupEmail);
        box.add(userGroupDescription);
        
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
    
    private void showUserGroup(int index)
    {
        if (userGroups.userGroup(index) != null) {
            updateBox(userGroups.userGroup(index));
        }
        else {
            button.setText("End Record");
            button.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/icon-cross.png")));
            clearBox();
        }
    }
    
    private void updateBox(UserGroup userGroup)
    {
        userGroupName.setText  ("User Type Name: "    + userGroup.userGroupName());
        userGroupEmail.setText ("Email: "             + userGroup.userGroupEmail());
        userGroupDescription.setText ("Description: " + userGroup.userGroupDescription());    
        
    }
    
    private void clearBox()
    {
        userGroupName.setText("");
        userGroupEmail.setText("");
        userGroupDescription.setText("");
    }
    
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            String actionSelected = e.getActionCommand();
            if(actionSelected == "Next User Group")
                showUserGroup(++index);
        }
    }
}