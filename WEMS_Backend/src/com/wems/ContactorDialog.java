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

import com.wems.business.*;
import com.wems.config.*;
import com.wems.model.*;

public class ContactorDialog extends JDialog
{
   private JLabel welcome0, welcome1;
   private JTextField username, email; 
   ButtonListener listener;
   String actionSelected;
   Config config;
   
    public ContactorDialog(Config config)
    {
       this.config = config;
       setupLayout();
       setupControls();
    }
    
    private void setupLayout()
    {
       int width = 345;
       int height = 155; 
        
       setLayout(new FlowLayout());  
       setTitle("WEMS Backend Administration Panel: Recovery");
       setSize(width, height);
       setLocationRelativeTo(null);

    }
    
    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        listener = new ButtonListener();

        username = new JTextField(10);
        email = new JTextField(10);
        
        welcome0 = new JLabel("Please Enter your Registered Username and Email below");
        welcome1 = new JLabel("and an Email will be sent with your Account Details.");
        welcome0.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcome1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        box.add(welcome0);
        box.add(welcome1);
        box.add(textfieldBox(username, "Username :"));
        box.add(textfieldBox(email, "Email :         "));
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
    
    private Box buttonBox()
    {
        Box box = Box.createHorizontalBox(); 
        box.add(Box.createHorizontalStrut(85));
        box.add(button("Submit",Color.GREEN, "images/icon-right.png", listener));
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
        email.setText("");
    }

    public String username()
    {
         return username.getText();
    }

    public String email()
    {
        return email.getText();
    }
        
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            // if check the database
            actionSelected = e.getActionCommand();
            
            if(actionSelected == "Submit")
            {
                if (!email().equals("") && !username().equals("")) {
                    
                    if (RegExTest.isEmail(email()))
                    {
                        //the domains of these email addresses should be valid
                        try
                        {
                            BusinessLogic _bl = new BusinessLogic(config);
                            if(_bl.validateUser(username(), email()))
                            {
                                User user = _bl.getUserAccess(username());
                                String generatedKey = RecoveryStringID.nextStringID();
                                String fullName     = user.firstName() + " " + user.lastName();
                                String header       = "Your password has been temporarily reset";
                                String message      =
                                    "Hello " + fullName + ",\n\nYou have requested a recovery of your WEMS Console password.\n" +
                                    " - Your login is: " + username() + "\n" +
                                    " - Your password has been reset to:\t " + generatedKey + "\n" +
                                    "You will be required to create a new password when you login next to the console.\n\n" +
                                    "Tip: In case you are wondering why your password looks so strange, it's because for additional security.\n" +
                                    "We store your password in the database using a high security one-way hash.\n\n" + 
                                    "Kind Regards,\nThe WEMS Administration Team\n\n" +
                                    "THIS IS AN AUTOMATED EMAIL - PLEASE DO NOT REPLY TO THIS EMAIL!\nIF YOU REPLY, YOU MAY BE OF QUESTIONABLE CONSCIENCE.";
                                _bl.assignTempPassword(username(), generatedKey);
                                
                                Emailer emailer = new Emailer();
                                emailer.sendEmail(
                                    config.SYS_EMAIL_CONTACT,
                                    email(),
                                    header,
                                    message,
                                    config
                                   );
                                setVisible(false);
                            }
                            else
                                JOptionPane.showMessageDialog(null, "Your Username and Email does not exist in the System Records. Please try again.");
                        }
                        catch (Exception ex){}
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Email entered is invalid. Please try again.");
                }
                else
                    JOptionPane.showMessageDialog(null, "Email and/or Username fields are Blank. Please Verify Inputs.");
            }
            else if(actionSelected == "Cancel")
                setVisible(false); // Close Window
        }
    }
}