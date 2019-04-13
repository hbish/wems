package com.wems;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.wems.model.*;

public class UserDetailPanel extends JPanel
{
    private JLabel firstName,
                   lastName,
                   username,
                   password,
                   phNumber,
                   email,
                   userTypeId,
                   userGroup,
                   loginAttempts,
                   isLockedOut;
                
    private Users users; 
    private int index = 0;
    private JButton button = new JButton();
    private JPanel detailPanel;
    
    public UserDetailPanel(Users users, int index)
    {
        this.users = users;
        this.index = index;
        this.detailPanel = this;
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
        box.add(button("Next User", "images/icon-right.png", new ButtonListener()));
        add(box);
        showUser(index);
    }
    
    private Box infoBox()
    {
        Box box = Box.createVerticalBox();
        firstName = new JLabel("");
        lastName = new JLabel("");
        username = new JLabel("");
        password = new JLabel("");
        phNumber = new JLabel("");
        email = new JLabel("");
        userTypeId = new JLabel("");
        userGroup = new JLabel("");
        loginAttempts = new JLabel("");
        isLockedOut = new JLabel("");
        
        box.add(username);
        box.add(firstName);
        box.add(lastName);
        box.add(password);
        box.add(phNumber);
        box.add(email);
        box.add(userTypeId);
        box.add(userGroup);
        box.add(loginAttempts);
        box.add(isLockedOut);
        
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
    
    private void showUser(int index)
    {
        if (users.user(index) != null) {
            updateBox(users.user(index));
        }
        else {
            button.setText("End Record");
            button.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/icon-cross.png")));
            clearBox();
        }
    }
    
    private void updateBox(User user)
    {
        firstName.setText       ("First Name: "     + user.firstName());
        lastName.setText        ("Last Name: "      + user.lastName());
        username.setText        ("Username: "       + user.username());
        email.setText           ("Email: "          + user.email());    
        password.setText        ("Password: "       + user.password());
        phNumber.setText        ("Phone No: "       + user.phNumber());
        userTypeId.setText      ("User Type ID: "   + user.userTypeId());
        userGroup.setText       ("User Group: "     + user.userGroup());
        loginAttempts.setText   ("Login Attempts: " + user.loginAttempts());
        isLockedOut.setText     ("Account Locked?: "+ user.isLockedOut());
    }
    
    private void clearBox()
    {
        firstName.setText("");
        lastName.setText("");
        username.setText("");
        email.setText("");
        password.setText("");
        phNumber.setText("");
        userTypeId.setText("");
        userGroup.setText("");
        loginAttempts.setText("");
        isLockedOut.setText("");
    }
    
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            String actionSelected = e.getActionCommand();
            if(actionSelected == "Next User")
                showUser(++index);
        }
    }
}