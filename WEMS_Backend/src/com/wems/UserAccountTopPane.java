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
import java.util.*;

public class UserAccountTopPane extends JPanel
{
    private JLabel label, descriptionLabel;
    private JTextField firstName, lastName, username, email, phNumber, loginAttempts;
    private JPasswordField password;
    private JComboBox userTypeId, userGroup, isLockedOut;
    private ButtonListener listener;
    private ComboListener comboListener;
    private String actionSelected;
    private UserAccountBottomPane userAccountBottomPane;
    private JButton but_add, but_edit, but_del, but_clear;
    private String[] booleanValues = new String[]{"True", "False"};
    private int userId = -1;
    private Config config;
    private User userAccess;
    private ArrayList<GenericValue> userTypeValues = new ArrayList<GenericValue>();
    private ArrayList<GenericValue> userGroupValues = new ArrayList<GenericValue>();
    
    public boolean isAdding = true;
    public boolean isEditDeleting = false;
    
    public UserAccountTopPane(Config config, User userAccess)
    {
        this.config = config;
        this.userAccess = userAccess;
        setupLayout();
        setupControls();
    }
    
    public void setUserAccountBottomPane(UserAccountBottomPane userAccountBottomPane)
    {
        this.userAccountBottomPane = userAccountBottomPane;
    }
    
    public void updateFields()
    {
        ArrayList<String> userTypeString = new ArrayList<String>();
        ArrayList<String> userGroupString = new ArrayList<String>();
        
        try {
            BusinessLogic bl = new BusinessLogic(config);
            userTypeValues = bl.userTypeValues();
            userGroupValues = bl.userGroupValues();
        }
        catch(Exception e) {}
        
        for (GenericValue u: userTypeValues)
        {
            userTypeString.add(u.value());
        }
        for (GenericValue u: userGroupValues)
        {
            userGroupString.add(u.value());
        }
        userTypeId.setModel(new javax.swing.DefaultComboBoxModel(userTypeString.toArray()));
        userGroup.setModel(new javax.swing.DefaultComboBoxModel(userGroupString.toArray()));
    }
    
    public void buttonToggle()
    {
        if (isAdding)
        {
            isAdding = false;
            isEditDeleting = true;
            but_add.setEnabled(false);
            but_edit.setEnabled(true);
            but_del.setEnabled(true);
        }
        else
        {
            isAdding = true;
            isEditDeleting = false;
            but_add.setEnabled(true);
            but_edit.setEnabled(false);
            but_del.setEnabled(false);
        }
        clearFields();
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }

    private void setupControls()
    {
        listener = new ButtonListener();
        comboListener = new ComboListener();
        add(mainGrid());
    }
    
    private JPanel mainGrid()
    {
        JPanel panel = new JPanel();
        Box vBox = Box.createVerticalBox();
        Box vhBox = Box.createHorizontalBox();
        Box vBox1 = Box.createVerticalBox();
        Box vBox2 = Box.createVerticalBox();
        
        firstName= new JTextField(32);
        firstName.setToolTipText("Insert First Name");
        lastName = new JTextField(24);
        lastName.setToolTipText("Insert Last Name");
        username = new JTextField(24);
        username.setToolTipText("Insert Username Login");
        password = new JPasswordField(24);
        password.setToolTipText("Insert Password Value");
        phNumber = new JTextField(24);
        phNumber.setToolTipText("Insert Phone No. (Inc. Area Code)");
        email = new JTextField(22);
        email.setToolTipText("Insert Email Address");
        loginAttempts = new JTextField(32);
        loginAttempts.setToolTipText("Current Login Attempts");
        
        userTypeId = new JComboBox();
        userTypeId.setToolTipText("Select User Type");
        userGroup = new JComboBox();
        userGroup.setToolTipText("Select User Group");
        isLockedOut = new JComboBox(booleanValues);
        isLockedOut.setToolTipText("Select User Locked Out");
        isLockedOut.addActionListener(comboListener);
        updateFields();
        userTypeId.setSelectedIndex(-1);
        userGroup.setSelectedIndex(-1);
        isLockedOut.setSelectedIndex(-1);
        
        vBox1.add(textfieldBox(firstName, "First Name:"));
        vBox1.add(Box.createVerticalStrut(10));
        vBox1.add(textfieldBox(lastName, "Last Name:"));
        vBox1.add(Box.createVerticalStrut(10));
        vBox1.add(textfieldBox(username, "Username:"));
        vBox1.add(Box.createVerticalStrut(10));
        vBox1.add(textfieldBox(password, "Password:"));
        vBox1.add(Box.createVerticalStrut(10));
        vBox1.add(textfieldBox(phNumber, "Phone No.:"));
        
        vBox2.add(textfieldBox(email, "Email:"));
        vBox2.add(Box.createVerticalStrut(10));
        vBox2.add(comboBox(userTypeId, "User Type:"));
        vBox2.add(Box.createVerticalStrut(10));
        vBox2.add(comboBox(userGroup, "User Group:"));
        vBox2.add(Box.createVerticalStrut(10));
        vBox2.add(textfieldBox(loginAttempts, "Login Attempts:"));
        vBox2.add(Box.createVerticalStrut(10));
        vBox2.add(comboBox(isLockedOut, "User Locked Out:"));
        vBox2.add(Box.createVerticalStrut(10));
        
        vhBox.add(vBox1);
        vhBox.add(Box.createHorizontalStrut(10));
        vhBox.add(vBox2);
        vhBox.setBorder(titleBorder("User Account Details"));
        
        vBox.add(vhBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(buttonBox());
        
        panel.add(vBox);
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
    
    private Box decriptionBox(String label)
    {
        Box box = Box.createHorizontalBox();   
        descriptionLabel = new JLabel(label);
        box.add(descriptionLabel);
        box.add(Box.createGlue());
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        return box;
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
    
    public void clearFields()
    {
        userId = -1; // Reset to Default User ID
        firstName.setText(""); 
        lastName.setText("");
        username.setText("");
        email.setText("");
        password.setText("");
        phNumber.setText("");
        loginAttempts.setText("");
        
        userTypeId.setSelectedIndex(-1);
        userGroup.setSelectedIndex(-1);
        isLockedOut.setSelectedIndex(-1);
    }
    
    public void populateFields(int userId)
    {
        try
        {
            this.userId = userId;
            BusinessLogic bl = new BusinessLogic(config);
            User user = bl.updateUserDetails(userId);
            int isLockedOutSelectedIndex = -1;
            firstName.setText(user.firstName()); 
            lastName.setText(user.lastName());
            username.setText(user.username());
            email.setText(user.email());
            password.setText("");
            phNumber.setText(user.phNumber());
            loginAttempts.setText(String.valueOf(user.loginAttempts()));
        
            userTypeId.setSelectedIndex    (impl_getSelectedIndex(userTypeValues,     user.userTypeId()));
            userGroup.setSelectedIndex     (impl_getSelectedIndex(userGroupValues,    user.userGroup()));
            
            if (user.isLockedOut())
                isLockedOutSelectedIndex = 0; // True: Check booleanValues
            else
                isLockedOutSelectedIndex = 1; // False: Check booleanValues
            isLockedOut.setSelectedIndex(isLockedOutSelectedIndex);
        }
        catch (Exception e)
        {
            clearFields();
        }
    }
    
    private int impl_getSelectedIndex(ArrayList<GenericValue> genericValues, int comparisonId)
    {
        int i = 0;
        for (GenericValue g: genericValues) {
            if (g.valueId() == comparisonId) {
                return i;
            }
            i++;
        }
        return -1;
    }
    
    private int impl_getSelectedId(ArrayList<GenericValue> genericValues, int comparisonId)
    {
        int i = 1;
        for (GenericValue g: genericValues) {
            if (i == comparisonId+1) {
                return g.valueId();
            }
            i++;
        }
        return -1;
    }
    
    private void warningDialog(String warningString)
    {
        JOptionPane.showMessageDialog(this, warningString, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private class ComboListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            try {
                JComboBox cb = (JComboBox)e.getSource();
                String value = (String)cb.getSelectedItem();
                if(value.equals("False"))
                {
                    loginAttempts.setText("0");
                }
            }
            catch (Exception ex){}
        }
    }
    
    private void addUserEmail(String s_firstName,
                              String s_lastName,
                              String s_username,
                              String s_email,
                              String s_phNumber,
                              int i_userType,
                              int i_userGroup)
    {
        try
        {
            BusinessLogic _bl = new BusinessLogic(config);
            Object[] obj      = _bl.getUserType(i_userType, i_userGroup);
            
            UserType userType   = (UserType) obj[0];
            UserGroup userGroup = (UserGroup) obj[1];
            
            String userGroupName   = userGroup.userGroupName();
            String userGroupEmail  = userGroup.userGroupEmail();
            String userTypeName    = userType.userTypeName();
            
            String header       = "New User Account has been Added in the WEMS System";
            String userMessage  =
                "Hello " + s_firstName + ",\n\nYour New User Account has been Added in the WEMS System. Details are:\n\n" +
                " - User : " + s_firstName + " " + s_lastName + "\n" +
                " - Username : " + s_username + "\n" +
                " - Email : " + s_email + "\n" +
                " - Phone Number : " + s_phNumber + "\n" +
                 " - User Account Type : " + userTypeName + "\n" +
                " - User Group : " + userGroupName + "\n" +
                "\nKind Regards,\nThe WEMS Administration Team\n\n" +
                "THIS IS AN AUTOMATED EMAIL - PLEASE DO NOT REPLY TO THIS EMAIL!\nIF YOU REPLY, YOU MAY BE OF QUESTIONABLE CONSCIENCE.";
            String adminMessage =
                "Hello " + userGroupName + ",\n\nA New User Account has been Added in the WEMS System Under your User Group. Details are:\n\n" +
                " - User : " + s_firstName + " " + s_lastName + "\n" +
                " - Username : " + s_username + "\n" +
                " - Email : " + s_email + "\n" +
                " - Phone Number : " + s_phNumber + "\n" +
                 " - User Account Type : " + userTypeName + "\n" +
                " - User Group : " + userGroupName + "\n" +
                "\nKind Regards,\nThe WEMS Administration Team\n\n" +
                "THIS IS AN AUTOMATED EMAIL - PLEASE DO NOT REPLY TO THIS EMAIL!\nIF YOU REPLY, YOU MAY BE OF QUESTIONABLE CONSCIENCE.";    
                
            Emailer emailer = new Emailer();
            String[] reciever = new String[]{s_email, userGroupEmail};
            String[] message = new String[]{userMessage, adminMessage};
            for(int i = 0; i<reciever.length; i++)
            {
                emailer.sendEmail(
                    config.SYS_EMAIL_CONTACT,
                    reciever[i],
                    header,
                    message[i],
                    config
               );
            }
        }
        catch(Exception e)
        {
            // e.printStackTrace();                  
            // JOptionPane.showMessageDialog(null, "Your Username and Email does not exist in the System Records. Please try again.");
        }
    }
    
    private void deleteUserEmail(int userId)
    {
        try
        {
           BusinessLogic _bl = new BusinessLogic(config);
           Object[] obj      = _bl.getUser(userId);
            
           User user             = (User) obj[0];
           UserGroup userGroup   = (UserGroup) obj[1];
           String userGroupName  = userGroup.userGroupName();
           String userGroupEmail = userGroup.userGroupEmail();
           String userEmail      = user.email();
           
           String header       = "Deleted User Account";
           String userMessage  =
                "Hello " + user.firstName() + " " + user.lastName() + ",\n\nYour User Account has been deleted from the WEMS System.\n\n" +           
                "\nKind Regards,\nThe WEMS Administration Team\n\n" +
                "THIS IS AN AUTOMATED EMAIL - PLEASE DO NOT REPLY TO THIS EMAIL!\nIF YOU REPLY, YOU MAY BE OF QUESTIONABLE CONSCIENCE.";
           String adminMessage =
                "Hello " + userGroupName + ",\n\nThe following User Account has been deleted from the WEMS System.\n\n" +           
                " - User : "+ user.username() + " - " + user.firstName() + " " + user.lastName() + "\n" +
                "\nKind Regards,\nThe WEMS Administration Team\n\n" +
                "THIS IS AN AUTOMATED EMAIL - PLEASE DO NOT REPLY TO THIS EMAIL!\nIF YOU REPLY, YOU MAY BE OF QUESTIONABLE CONSCIENCE.";
             
           Emailer emailer = new Emailer();
           String[] reciever = new String[]{userEmail,userGroupEmail};
           String[] message = new String[]{userMessage ,adminMessage};
           
           for(int i = 0 ; i<reciever.length ; i++)
           {
               emailer.sendEmail(
                        config.SYS_EMAIL_CONTACT,
                        reciever[i],
                        header,
                        message[i],
                        config
                   );
           }
        }
        catch(Exception e)
        {
           //System.out.print(e);               
        }
    }
    
    private class ButtonListener implements ActionListener 
    {
        String warningString = ""; 
        String s_username, s_firstName, s_lastName, s_phNumber, s_email;
        String s_password = "";
        int i_userType, i_userGroup, i_loginAttempts;
        boolean b_isLockedOut;
        
        public void actionPerformed(ActionEvent e) 
        {
            actionSelected = e.getActionCommand();
            if(actionSelected == "ADD")
            {
                if(!username.getText().equals(""))
                {
                    s_username = username.getText();
                }
                else 
                {
                    warningString = "- No Username\n";  
                }
                
                if(!firstName.getText().equals(""))
                {
                    s_firstName = firstName.getText(); 
                }
                else 
                {
                    warningString += "- No First Name\n";  
                }
                
                if(!lastName.getText().equals(""))
                {
                    s_lastName = lastName.getText();
                }
                else 
                {
                    warningString += "- No Last Name\n";  
                }
                
                if(!password.getPassword().toString().equals(""))
                {
                    s_password = String.valueOf(password.getPassword());
                }
                else
                {
                    warningString += "- No Password\n";  
                }
                
                if(!phNumber.getText().equals(""))
                {
                    s_phNumber = phNumber.getText();
                }
                else
                {
                    warningString += "- No Phone Number\n";  
                }
                
                if(!email.getText().equals(""))
                {
                    s_email = email.getText();
                }
                else
                {
                    warningString += "- No Email\n";  
                }
                
                if(!loginAttempts.getText().equals(""))
                {
                    try
                    {
                        i_loginAttempts = Integer.valueOf(loginAttempts.getText());
                    }
                    catch (NumberFormatException nfe)
                    {
                        warningString += "- Login Attempts Value must be numeric\n";
                    }
                }
                else
                {
                    warningString += "- No Login Attempts Number\n";
                }
                
                if(userTypeId.getSelectedIndex() != -1)
                {
                    i_userType = impl_getSelectedId(userTypeValues, userTypeId.getSelectedIndex());
                }
                else
                {
                    warningString += "- No User Type Selected\n";  
                }
                
                if(userGroup.getSelectedIndex() != -1)
                {
                    i_userGroup = impl_getSelectedId(userGroupValues, userGroup.getSelectedIndex());
                }
                else
                {
                    warningString += "- No User Group Selected\n";  
                }
                
                if(isLockedOut.getSelectedIndex() != -1)
                {
                    b_isLockedOut = Boolean.parseBoolean((String)isLockedOut.getSelectedItem());
                }
                else
                {
                    warningString += "- No Locked Out Mode Selected\n";  
                }
                
                if(!warningString.equals(""))
                {
                    warningDialog("Please Verify The Following Inputs Not Entered:\n" + warningString);  
                    warningString = "";
                }
                else
                {
                    try
                    {
                         BusinessLogic bl = new BusinessLogic(config);
                         bl.addUser(
                                    s_firstName,
                                    s_lastName,
                                    s_username,
                                    s_email,
                                    s_password,
                                    s_phNumber,
                                    i_userType,
                                    i_userGroup,
                                    i_loginAttempts,
                                    b_isLockedOut);
                         userAccountBottomPane.updateTable();
                         addUserEmail(s_firstName,s_lastName,s_username,s_email,s_phNumber,i_userType,i_userGroup);
                         clearFields();
                    }
                    catch(Exception ex)
                    {
                        System.out.println(ex);
                    }
                }
            }
            else if(actionSelected == "EDIT")
            {
                if(userAccess.userId() == userId) {
                    warningDialog("Error: You cannot edit an account with the same account that you logged into.\nLogin as a different user to edit this account.");
                    return;
                }
                if (userId < 0) {
                    warningDialog("You have not selected a valid User ID to edit.");
                    return;
                }
                
                if(!username.getText().equals(""))
                {
                    s_username = username.getText();
                }
                else 
                {
                    warningString = "- No Username\n";  
                }
                
                if(!firstName.getText().equals(""))
                {
                    s_firstName = firstName.getText(); 
                }
                else 
                {
                    warningString += "- No First Name\n";  
                }
                
                if(!lastName.getText().equals(""))
                {
                    s_lastName = lastName.getText();
                }
                else 
                {
                    warningString += "- No Last Name\n";  
                }
                
                if(!password.getPassword().toString().equals(""))
                {
                    s_password = String.valueOf(password.getPassword());
                }
                
                if(!phNumber.getText().equals(""))
                {
                    s_phNumber = phNumber.getText();
                }
                else
                {
                    warningString += "- No Phone Number\n";  
                }
                
                if(!email.getText().equals(""))
                {
                    s_email = email.getText();
                }
                else
                {
                    warningString += "- No Email\n";  
                }
                
                if(!loginAttempts.getText().equals(""))
                {
                    try
                    {
                        i_loginAttempts = Integer.valueOf(loginAttempts.getText());
                    }
                    catch (NumberFormatException nfe)
                    {
                        warningString += "- Login Attempts Value must be numeric\n";
                    }
                }
                else
                {
                    warningString += "- No Login Attempts Number\n";
                }
                
                if(userTypeId.getSelectedIndex() != -1)
                {
                    i_userType = impl_getSelectedId(userTypeValues, userTypeId.getSelectedIndex());
                }
                else
                {
                    warningString += "- No User Type Selected\n";  
                }
                
                if(userGroup.getSelectedIndex() != -1)
                {
                    i_userGroup = impl_getSelectedId(userGroupValues, userGroup.getSelectedIndex());
                }
                else
                {
                    warningString += "- No User Group Selected\n";  
                }
                
                if(isLockedOut.getSelectedIndex() != -1)
                {
                    b_isLockedOut = Boolean.parseBoolean((String)isLockedOut.getSelectedItem());
                }
                else
                {
                    warningString += "- No Locked Out Mode Selected\n";  
                }
                
                if(!warningString.equals(""))
                {
                    warningDialog("Please Verify The Following Inputs Not Entered:\n" + warningString);  
                    warningString = "";
                }
                else
                {
                    try
                    {
                         BusinessLogic bl = new BusinessLogic(config);
                         bl.editUser(
                                    userId,
                                    s_firstName,
                                    s_lastName,
                                    s_username,
                                    s_email,
                                    s_password,
                                    s_phNumber,
                                    i_userType,
                                    i_userGroup,
                                    i_loginAttempts,
                                    b_isLockedOut);
                         userAccountBottomPane.updateTable();
                         clearFields();
                    }
                    catch(Exception ex)
                    {
                        System.out.println(ex);
                    }
                }
            }
            else if(actionSelected == "DELETE")
            {
                if(userAccess.userId() == userId) {
                    warningDialog("Error: You cannot delete an account with the same account that you logged into.\nLogin as a different user to delete this account.");
                    return;
                }
                if (userId < 0) {
                    warningDialog("You have not selected a valid User ID to delete.");
                    return;
                }
                
                try
                {
                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete this user?\nPlease note, you will not be able to recover this account.", "Confirm User Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        BusinessLogic bl = new BusinessLogic(config);
                        deleteUserEmail(userId);
                        bl.deleteUser(userId);
                        userAccountBottomPane.updateTable();
                        clearFields();
                    }
                }
                catch(Exception ex)
                {
                    System.out.println(ex);
                }
            }
            else if(actionSelected == "CLEAR")
            {
                clearFields();
            }
        }
    }
}