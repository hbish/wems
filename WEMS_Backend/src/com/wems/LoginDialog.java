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
import com.wems.model.*;

public class LoginDialog extends JDialog
{
    private JLabel creditLabel;
    private JTextArea welcomeLabel;
    private JTextField usernameField; 
    private JPasswordField passwordField; 
    private AddImage addImage;
    private ButtonListener listener;
    private String actionSelected;
    private ContactorDialog contactorDialog;
    private ChangePassDialog changePassDialog;
    private Config config = new Config();
    
    public LoginDialog()
    {      
        setupLayout();
        setupControls();
    }
    
    private void setupLayout()
    {
       int width  = 415;
       int height = 330;
        
       setLayout(new FlowLayout());
       getContentPane().setBackground(Color.WHITE);
       setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
       setTitle("WEMS Backend Administration Panel: Login");
       setSize(width, height);
       setLocationRelativeTo(null);
       setIconImage(
            new ImageIcon(
            LoginDialog.class.getResource("images/icon-wems.png")
            ).getImage()
       );
    }
    
    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        listener = new ButtonListener();
        addImage = new AddImage();
        
        usernameField = new JTextField(10);
        usernameField.setToolTipText("Please enter your registered WEMS Username here");
        usernameField.requestFocusInWindow(); // Set Focus in Field
        passwordField = new JPasswordField(10);
        passwordField.setToolTipText("Please enter your registered WEMS Password here");
        
        creditLabel = new JLabel("Dragon Developments 2011 v1.0");
        creditLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        welcomeLabel = new JTextArea("             Welcome to the WEMS Backend Panel. Please login \n              below using a registered username and password.");
        welcomeLabel.setEditable(false);
        welcomeLabel.setFont(new Font("Sans Serif", Font.PLAIN, 12));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        box.add(addImage.addLogo("images/wems-logo.png"));
        box.add(Box.createVerticalStrut(25));
        box.add(welcomeLabel);
        box.add(Box.createVerticalStrut(10));
        box.add(textfieldBox(usernameField, " Username : "));
        box.add(Box.createVerticalStrut(10));
        box.add(textfieldBox(passwordField, " Password : "));
        box.add(Box.createVerticalStrut(20));
        box.add(buttonBox());
        box.add(Box.createVerticalStrut(10));
        box.add(creditLabel);
        add(box);
        setVisible(true);
        
        // http://www.java.happycodings.com/Java_Swing/code36.html
        // Listen for windowOpened event to set focus to actual field
        this.addWindowListener(new WindowAdapter(){ 
           public void windowOpened( WindowEvent e ) 
           { 
               usernameField.requestFocus(); 
           } 
        });
    }
    
    private Box textfieldBox(JTextField field, String label)
    {
        Box box = Box.createHorizontalBox();   
        box.add(new JLabel(label));
        box.add(Box.createHorizontalStrut(10));
        box.add(field);
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(Box.createGlue());
        return box;
    }
    private Box buttonBox()
    {
        Box box = Box.createHorizontalBox();
        box.add(button("Login", Color.GREEN, "images/icon-goto.png", listener));
        box.add(Box.createHorizontalStrut(5));
        box.add(button("Clear", Color.ORANGE, "images/icon-edit.png", listener));
        box.add(Box.createHorizontalStrut(5));
        box.add(button("Exit", Color.RED, "images/icon-quit.png", listener));
        box.add(Box.createHorizontalStrut(5));
        box.add(button("Recover Password", Color.CYAN, "images/icon-load.png", listener));
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        //box.add(Box.createGlue());
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
        usernameField.setText("");
        passwordField.setText("");
    }

    public String username()
    {
         return usernameField.getText();
    }

    public String password()
    {
        return String.valueOf(passwordField.getPassword());
    }
    
    public void forgotUsernameOrPassword()
    {
        contactorDialog = new ContactorDialog(config);
    }
        
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            // if check the database
            actionSelected = e.getActionCommand();
            
            if(actionSelected == "Login")
            {
                BusinessLogic bl = new BusinessLogic(config);
                try
                {
                    if (!username().equals("") && !password().equals(""))
                    {
                        boolean valid = bl.validateLogin(username(), password());
                        boolean isTempPass = bl.isUserExistWithTempPass(username());
                        if (valid)
                        {
                            if (!isTempPass)
                            {
                                User userAccess = bl.getUserAccess(username());
                                new Window(config, userAccess);
                                dispose();
                            }
                            else
                            {
                                changePassDialog = new ChangePassDialog(config);
                                clear(); 
                            }
                        }
                        else
                        {
                            if (bl.isValidUser(username()))
                            {
                                int loginAttempts  = bl.currentLoginAttempts(username()) + 1;
                                boolean isLockedOut= bl.setFailedLoginAttempts(username(), loginAttempts, config.SYS_MAX_LOG_ATTEMPT);
                                if (!isLockedOut)
                                    JOptionPane.showMessageDialog(null, "Your account password is invalid, or you have insufficient privileges to use this console.\nThis is login failure attempt no. #" + String.valueOf(loginAttempts) + ". Please try again.");
                                else
                                    JOptionPane.showMessageDialog(null, "Your account has been locked out due to multiple login failures or unauthorized use.\nPlease contact your administrator to re-activate your account.");
                                passwordField.setText("");
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Your account username is invalid. Please try again.");
                                clear();
                            }
                        }
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Username and/or Password is Blank. Please Verify Inputs.");
                }   
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(null, "WEMS Console Issue: " + ex.getMessage() + "\nPlease Contact Your System Adminstrator to report this issue.");
                    ex.printStackTrace();
                }
            }
            else if(actionSelected == "Clear")
            {
                clear();     
            }     
            else if(actionSelected == "Recover Password")
            {
                clear();
                forgotUsernameOrPassword();
            }
            else if(actionSelected == "Exit")
            {
                dispose();
            }
        }
    }
    
    public static void main(String[] args)
    {
        try {
           for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
           {
               if ("Windows".equals(info.getName())) 
               {
                   UIManager.setLookAndFeel(info.getClassName());
                   break;
               }
           }
        }
        catch (Exception e) {}
        // If Nimbus/Windows/etc is not available, you can set the GUI to another look and feel.
        new LoginDialog();
    }
}