/**
 * Write a description of class jLoginDialog here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package com.wems;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.wems.business.*;
import com.wems.config.*;

public class ChangePassDialog extends JDialog
{
   private JLabel welcome0;
   private JTextField username; 
   private JPasswordField pass, vpass;
   private Config config;
   ButtonListener listener;
   String actionSelected;
   
    public ChangePassDialog(Config config)
    {
       this.config = config;
       setupLayout();
       setupControls();
    }
    
    private void setupLayout()
    {
       int width = 470;
       int height = 218; 
        
       setLayout(new FlowLayout());  
       setTitle("WEMS Backend Administration Panel: Change Password");
       setSize(width, height);
       setLocationRelativeTo(null);
    }
    
    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        listener = new ButtonListener();

        username = new JTextField(10);
        pass = new JPasswordField(10);
        vpass = new JPasswordField(10);
        
        welcome0 = new JLabel("Please Enter your Registered Username and NEW Password below");
        welcome0.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        box.add(welcome0);
        box.add(Box.createVerticalStrut(10));
        box.add(textfieldBox(username, "Username :"));
        box.add(Box.createVerticalStrut(10));
        box.add(passfieldBox(pass, "Enter New Password : "));
        box.add(Box.createVerticalStrut(10));
        box.add(passfieldBox(vpass, "Retype New Password : "));
        box.add(Box.createVerticalStrut(20));
        box.add(buttonBox());
        add(box);
        
        setVisible(true);
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
    
    private Box passfieldBox(JPasswordField field, String label)
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
        box.add(Box.createHorizontalStrut(85));
        box.add(button("Change", Color.GREEN, "images/icon-modify.png", listener));
        box.add(Box.createHorizontalStrut(10));
        box.add(button("Cancel", Color.RED, "images/icon-cross.png", listener));
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(Box.createGlue());
        return box;
    }

    private JButton button(String label, Color color, String iconPath, ButtonListener listener)
    {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        button.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));
        if(config.WIN7_UI_LOOK)
       		button.setBackground(color);
        return button;
    }
    
    public void clear()
    {
        username.setText("");
        pass.setText("");
        vpass.setText("");
    }

    public String username()
    {
         return username.getText();
    }

    public String password()
    {
        return String.valueOf(pass.getPassword());
    }
        
    public String validatePassword()
    {
        return String.valueOf(vpass.getPassword());
    }    
    
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            // if check the database
            actionSelected = e.getActionCommand();
            
            if(actionSelected == "Change")
            {
                if (!username().equals("") && !password().equals("") && !validatePassword().equals("")) 
                {
                    if (password().equals(validatePassword()))
                    {
                        try
                        {
                            BusinessLogic _bl = new BusinessLogic(config);
                            if (_bl.isUserExistWithTempPass(username()))
                            {
                                _bl.assignNewPassword(username(), password());
                                setVisible(false);
                            }
                            else
                                JOptionPane.showMessageDialog(null, "Your username entered is invalid. Please try again.");
                        }
                        catch (Exception ex){}
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Your passwords entered do not match. Please try again.");
                }
                else
                    JOptionPane.showMessageDialog(null, "Username and/or Password fields are Blank. Please Verify Inputs.");
            }
            else if(actionSelected == "Cancel")
                setVisible(false); // Close Window
        }
    }
}