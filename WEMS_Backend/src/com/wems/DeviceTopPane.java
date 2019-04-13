/**
 * Write a description of class here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package com.wems;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import com.wems.business.*;
import com.wems.config.*;
import com.wems.model.*;

public class DeviceTopPane extends JPanel
{
    // instance variables
    private JTextField id, type, brand, model, serial, macaddress;
    private JComboBox  roomuid, userGroup, connected;
                      
    private JScrollPane scrollpane;
    private ButtonListener listener;
    private String actionSelected;
    private JButton but_add, but_edit, but_del, but_clear;
    private String[] booleanStrings = {"true", "false"};
    private DeviceBottomPane deviceBottomPane;
    private Device device;
    private Config config;
    private ArrayList<GenericValue> roomValues      = new ArrayList<GenericValue>();
    private ArrayList<GenericValue> userGroupValues = new ArrayList<GenericValue>();

    public boolean isAdding = true;
    public boolean isEditDeleting = false;
    
    /**
     * Constructor for objects of class
     */
    public DeviceTopPane(Config config)
    {
        this.config = config;
        setupLayout();
        setupControls();
    }
    
    public void setDeviceBottomPane(DeviceBottomPane deviceBottomPane)
    {
        this.deviceBottomPane = deviceBottomPane;
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
    
    public void updateFields()
    {
        ArrayList<String> roomString = new ArrayList<String>();
        ArrayList<String> userGroupString = new ArrayList<String>();
        
        try {
            BusinessLogic bl = new BusinessLogic(config);
            roomValues = bl.roomValues();
            userGroupValues = bl.userGroupValues();
        }
        catch(Exception e) {}
        
        for (GenericValue u: roomValues)
        {
            roomString.add(u.value());
        }
        for (GenericValue u: userGroupValues)
        {
            userGroupString.add(u.value());
        }
        roomuid.setModel(new javax.swing.DefaultComboBoxModel(roomString.toArray()));
        userGroup.setModel(new javax.swing.DefaultComboBoxModel(userGroupString.toArray()));
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }

    private void setupControls()
    {
        listener = new ButtonListener();
        scrollpane = new JScrollPane(mainGrid());
        scrollpane.setPreferredSize(new Dimension(700, 215));
        
        Box vBox = Box.createVerticalBox();
        vBox.add(scrollpane);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(buttonBox());
        add(vBox);
    }
    
    private JPanel mainGrid()
    {
        JPanel panel = new JPanel();
        Box vBox = Box.createVerticalBox();

        id = new JTextField(70);
        id.setToolTipText("Device ID Value");
        type = new JTextField(8);
        type.setToolTipText("Device Type");
        brand = new JTextField(8);
        brand.setToolTipText("Device Brand");
        model = new JTextField(8);
        model.setToolTipText("Device Model");
        serial = new JTextField(8);
        serial.setToolTipText("Device Serial No.");
        macaddress = new JTextField(8);
        macaddress.setToolTipText("Device MAC Address");
        
        roomuid = new JComboBox();
        roomuid.setToolTipText("Select Room/Office");
        userGroup = new JComboBox();
        userGroup.setToolTipText("Select User Group");
        connected = new JComboBox(booleanStrings);
        connected.setToolTipText("Is it Connected?");
        updateFields();
        roomuid.setSelectedIndex(-1);
        userGroup.setSelectedIndex(-1);
        connected.setSelectedIndex(-1);
        
        JLabel devLabel = new JLabel("Device Settings");
        devLabel.setFont(new Font("Sans Serif", Font.BOLD, 12));
        vBox.add(devLabel);
        vBox.add(new JSeparator(JSeparator.HORIZONTAL));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(id, "Device ID: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(type, "Device Type: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(brand, "Device Brand: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(model, "Device Model: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(serial, "Device Serial No.: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(textfieldBox(macaddress, "Device MAC Address: "));
        
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(comboBox(roomuid, "Select Room/Office: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(comboBox(userGroup, "Select User Group: "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(comboBox(connected, "Is Connected? "));
        vBox.add(Box.createVerticalStrut(10));
        vBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(vBox);
        return panel;
    }
    
    private Box comboBox(JComboBox field, String label)
    {
        Box box = Box.createHorizontalBox();   
        box.add(new JLabel(label));
        box.add(Box.createHorizontalStrut(10));
        box.add(field);
        return box;
    }
    
    private Box textfieldBox(JTextField field, String label)
    {
        Box box = Box.createHorizontalBox();   
        box.add(new JLabel(label));
        box.add(field);
        return box;
    }
    
    private Box buttonBox()
    {
        Box box = Box.createHorizontalBox(); 
        but_add = button("ADD", true, Color.GREEN, "images/icon-plus.png", listener);
        but_edit = button("EDIT", false, Color.ORANGE, "images/icon-modify.png", listener);
        but_del = button("DELETE", false, Color.RED, "images/icon-cross.png", listener);
        but_clear = button("CLEAR", true, Color.GRAY, "images/icon-clear.png", listener);
        box.add(Box.createHorizontalStrut(55));
        box.add(but_add);
        box.add(Box.createHorizontalStrut(10));
        box.add(but_edit);
        box.add(Box.createHorizontalStrut(10));
        box.add(but_del);
        box.add(Box.createHorizontalStrut(10));
        box.add(but_clear);
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
    
    public void clearFields()
    {
        device = null;
        id.setText("");
        type.setText("");
        brand.setText("");
        model.setText("");
        serial.setText("");
        macaddress.setText("");
        roomuid.setSelectedIndex(-1);
        userGroup.setSelectedIndex(-1);
        connected.setSelectedIndex(-1);
    }
    
    public void populateFields(Device device)
    {
        this.device = device;
        int isConnectedSelectedIndex = -1;
        id.setText(String.valueOf(device.id()));
        type.setText(device.type());
        brand.setText(device.brand());
        model.setText(device.model());
        serial.setText(device.serial());
        macaddress.setText(device.macaddress());
        roomuid.setSelectedIndex       (impl_getSelectedIndex(roomValues,      device.roomuid()));
        userGroup.setSelectedIndex     (impl_getSelectedIndex(userGroupValues, device.userGroup()));
        
        if (device.connected())
                isConnectedSelectedIndex = 0; // True: Check booleanValues
            else
                isConnectedSelectedIndex = 1; // False: Check booleanValues
        connected.setSelectedIndex(isConnectedSelectedIndex);
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
    
    private void addDeviceEmail(int i_id, int i_roomuid, int i_userGroup, String s_type, String s_brand, String s_model, String s_serial, String s_macaddress, boolean b_connected)
    {
        String connected = new Boolean(b_connected).toString();
        try
        {
           BusinessLogic _bl = new BusinessLogic(config);
           UserGroup usergroup = _bl.getUserGroup(i_userGroup);
        
           String userGroupName   = usergroup.userGroupName();
           String userGroupEmail  = usergroup.userGroupEmail();
           String header          = "New Device has been Added in the System";
           String message         =
                "Hello " + userGroupName + ",\n\nNew Device has been Added in the System under this User Group.\n\n" +
                " - Device ID \t: " + i_id + "\n" +
                " - Room ID \t: " + i_roomuid + "\n" +
                " - Device Type \t: " + s_type + "\n" +
                " - Device Brand \t: " + s_brand + "\n" +
                " - Device Model # \t: " + s_model + "\n" +
                " - Device Serial # \t: " + s_serial + "\n" +
                " - Device MAC Address \t: " + s_macaddress + "\n" +
                " - Is Device Connected \t: " + connected + "\n" +
                "\nKind Regards,\nThe WEMS Administration Team\n\n" +
                "THIS IS AN AUTOMATED EMAIL - PLEASE DO NOT REPLY TO THIS EMAIL!\nIF YOU REPLY, YOU MAY BE OF QUESTIONABLE CONSCIENCE.";
            
           Emailer emailer = new Emailer();
           emailer.sendEmail(
                config.SYS_EMAIL_CONTACT,
                userGroupEmail,
                header,
                message,
                config
               );
        }
        catch(Exception e)
        {
            // JOptionPane.showMessageDialog(null, "Your Username and Email does not exist in the System Records. Please try again.");
        }
    }
    
    private void deleteDeviceEmail(int i_id, int i_userGroup)
    {
        try
        {
           BusinessLogic _bl = new BusinessLogic(config);
           UserGroup usergroup = _bl.getUserGroup(i_userGroup);
        
           String userGroupName   = usergroup.userGroupName();
           String userGroupEmail  = usergroup.userGroupEmail();
           String header          = "Device has been Deleted in the System";
           String message         =
                "Hello " + userGroupName + ",\n\nDevice has been Deleted in the System under this User Group.\n\n" +
                " - Device ID \t: " + i_id + "\n" +
                "\nKind Regards,\nThe WEMS Administration Team\n\n" +
                "THIS IS AN AUTOMATED EMAIL - PLEASE DO NOT REPLY TO THIS EMAIL!\nIF YOU REPLY, YOU MAY BE OF QUESTIONABLE CONSCIENCE.";
            
           Emailer emailer = new Emailer();
           emailer.sendEmail(
                config.SYS_EMAIL_CONTACT,
                userGroupEmail,
                header,
                message,
                config
               );
        }
        catch(Exception e)
        {
            // JOptionPane.showMessageDialog(null, "Your Username and Email does not exist in the System Records. Please try again.");
        }
    }
    
    private class ButtonListener implements ActionListener 
    {
        String warningString = ""; 
        private int     i_deviceuid, // PK
                        i_id,
                        i_roomuid,   // FK: sensordata_room
                        i_userGroup; // FK: user_group
        private String  s_type,
                        s_brand,
                        s_model,
                        s_serial,
                        s_macaddress;
        private boolean b_connected;
            
        public void actionPerformed(ActionEvent e) 
        {
            actionSelected = e.getActionCommand();
            if(actionSelected == "ADD")
            {
                if(!type.getText().equals(""))
                    s_type = type.getText();
                else
                    warningString = "- No Device Type\n";
                
                if(!brand.getText().equals(""))
                    s_brand = brand.getText();
                else
                    warningString += "- No Device Brand\n";
                
                if(!model.getText().equals(""))
                    s_model = model.getText();
                else
                    warningString += "- No Device Model\n";
                
                if(!serial.getText().equals(""))
                    s_serial = serial.getText();
                else
                    warningString += "- No Device Serial\n";
                
                if(!macaddress.getText().equals(""))
                    s_macaddress = macaddress.getText();
                else
                    warningString += "- No MAC Address\n";
                    
                if(!id.getText().equals(""))
                {
                    try {
                        i_id = Integer.valueOf(id.getText());
                    }
                    catch (NumberFormatException nfe) {
                        warningString += "- Device ID Value must be numeric\n";
                    }
                }
                else
                    warningString += "- No Device ID Number\n";
                
                if(roomuid.getSelectedIndex() != -1)
                    i_roomuid = impl_getSelectedId(roomValues, roomuid.getSelectedIndex());
                else
                    warningString += "- No Room/Office Selected\n";
                
                if(userGroup.getSelectedIndex() != -1)
                    i_userGroup = impl_getSelectedId(userGroupValues, userGroup.getSelectedIndex());
                else
                    warningString += "- No User Group Selected\n";
                
                if(connected.getSelectedIndex() != -1)
                    b_connected = Boolean.parseBoolean((String)connected.getSelectedItem());
                else
                    warningString += "- No 'Is Connected' Selected\n";
                
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
                        bl.addDevice(
                                    i_id,
                                    i_roomuid,
                                    i_userGroup,
                                    s_type,
                                    s_brand,
                                    s_model,
                                    s_serial,
                                    s_macaddress,
                                    b_connected);
                        deviceBottomPane.updateTable();
                        addDeviceEmail(i_id, i_roomuid, i_userGroup, s_type, s_brand, s_model, s_serial, s_macaddress, b_connected);
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
                if (device == null) {
                    warningDialog("You have not selected a valid Device ID to edit.");
                    return;
                }
                
                 if(!type.getText().equals(""))
                    s_type = type.getText();
                else
                    warningString = "- No Device Type\n";
                
                if(!brand.getText().equals(""))
                    s_brand = brand.getText();
                else
                    warningString += "- No Device Brand\n";
                
                if(!model.getText().equals(""))
                    s_model = model.getText();
                else
                    warningString += "- No Device Model\n";
                
                if(!serial.getText().equals(""))
                    s_serial = serial.getText();
                else
                    warningString += "- No Device Serial\n";
                
                if(!macaddress.getText().equals(""))
                    s_macaddress = macaddress.getText();
                else
                    warningString += "- No MAC Address\n";
                    
                if(!id.getText().equals(""))
                {
                    try {
                        i_id = Integer.valueOf(id.getText());
                    }
                    catch (NumberFormatException nfe) {
                        warningString += "- Device ID Value must be numeric\n";
                    }
                }
                else
                    warningString += "- No Device ID Number\n";
                
                if(roomuid.getSelectedIndex() != -1)
                    i_roomuid = impl_getSelectedId(roomValues, roomuid.getSelectedIndex());
                else
                    warningString += "- No Room/Office Selected\n";
                
                if(userGroup.getSelectedIndex() != -1)
                    i_userGroup = impl_getSelectedId(userGroupValues, userGroup.getSelectedIndex());
                else
                    warningString += "- No User Group Selected\n";
                
                if(connected.getSelectedIndex() != -1)
                    b_connected = Boolean.parseBoolean((String)connected.getSelectedItem());
                else
                    warningString += "- No 'Is Connected' Selected\n";
                
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
                         bl.editDevice(
                                    device.deviceuid(),
                                    i_id,
                                    i_roomuid,
                                    i_userGroup,
                                    s_type,
                                    s_brand,
                                    s_model,
                                    s_serial,
                                    s_macaddress,
                                    b_connected);
                         deviceBottomPane.updateTable();
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
                if (device == null) {
                    warningDialog("You have not selected a valid Device ID to delete.");
                    return;
                }
                
                try
                {
                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete this device?\nPlease note, you will not be able to recover this device.", "Confirm Device Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        BusinessLogic bl = new BusinessLogic(config);
                        deleteDeviceEmail(device.deviceuid(), device.userGroup());
                        bl.deleteDevice(device.deviceuid());
                        deviceBottomPane.updateTable();
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