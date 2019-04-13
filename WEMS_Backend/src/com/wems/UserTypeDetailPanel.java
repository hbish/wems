package com.wems;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.wems.model.*;

public class UserTypeDetailPanel extends JPanel
{
    private JLabel userTypeName,
                   userTypeId,
                   userTypeDescription;
                
    private UserTypes userTypes; 
    private int index = 0;
    private JButton button = new JButton();
    private JPanel buDetailPanel;
    
    public UserTypeDetailPanel(UserTypes userTypes, int index)
    {
        this.userTypes = userTypes;
        this.index = index;
        this.buDetailPanel = this;
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
        showUserType(index);
    }
    
    private Box infoBox()
    {
        Box box = Box.createVerticalBox();
        userTypeName = new JLabel("");
        userTypeId = new JLabel("");
        userTypeDescription = new JLabel("");
        
        box.add(userTypeId);
        box.add(userTypeName);
        box.add(userTypeDescription);
        
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
    
    private void showUserType(int index)
    {
        if (userTypes.userType(index) != null) {
            updateBox(userTypes.userType(index));
        }
        else {
            button.setText("End Record");
            button.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/icon-cross.png")));
            clearBox();
        }
    }
    
    private void updateBox(UserType userType)
    {
        userTypeId.setText("User Type ID: "         + userType.userTypeId());
        userTypeName.setText("User Type Name: "     + userType.userTypeName());
        userTypeDescription.setText("Description: " + userType.userTypeDescription());
    }
    
    private void clearBox()
    {
        userTypeName.setText("");
        userTypeId.setText("");
        userTypeDescription.setText("");
    }
    
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            String actionSelected = e.getActionCommand();
            if(actionSelected == "Next User Type")
                showUserType(++index);
        }
    }
}