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
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import com.wems.business.*;
import com.wems.config.*;
import com.wems.model.*;

public class UserGroupTopPane extends JPanel
{
    private JLabel label;
    private JTextField userGroupName, userGroupDescription, userGroupEmail;
    private DefaultListModel listModel = new DefaultListModel();
    private JScrollPane listScroll;
    private Building building = new Building();
    private JList list;
    private UserGroupBottomPane userGroupBottomPane;
    private JButton but_add, but_edit, but_del, but_clear;
    private String[] booleanValues = new String[]{"True", "False"};
    private ButtonListener listener;
    private JComboBox userGroup, permissionLevel, buildingList, levelList, roomList;
    public boolean isAdding = true;
    public boolean isEditDeleting = false;
    private int userGroupId = -1;
    private Config config;
    
    public UserGroupTopPane(Config config)
    {
       this.config = config;
       setupLayout();
       setupControls();
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }
    
    public void setUserGroupBottomPane(UserGroupBottomPane userGroupBottomPane)
    {
        this.userGroupBottomPane = userGroupBottomPane;
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

    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        listener = new ButtonListener();    
               
        userGroupName = new JTextField(54);
        userGroupName.setToolTipText("User Group Name");
        userGroupEmail = new JTextField(40);
        userGroupEmail.setToolTipText("User Group Email Contact (Full Address)");
        userGroupDescription = new JTextField(40);
        userGroupDescription.setToolTipText("User Group Description"); 
        
        Border lineBorder = new LineBorder(Color.white, 12);        
        box.add(textfieldBox(userGroupName, "User Group :"));
        box.add(Box.createVerticalStrut(10));
        box.add(textfieldBox(userGroupDescription, "User Description :"));
        box.add(Box.createVerticalStrut(10));
        box.add(textfieldBox(userGroupEmail, "Email :"));
        box.add(Box.createVerticalStrut(10));
        box.setBorder(titleBorder("User Group Details"));
        //box.setBackground(Color.DARK_GRAY);
        
        Box mainBox = Box.createVerticalBox();
        mainBox.add(box);
        mainBox.add(mainGrid());
        mainBox.add(buttonBox());
        add(mainBox);
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
    
    private JPanel mainGrid()
    {
        JPanel right = new JPanel();
        list = new JList();
        list.setVisibleRowCount(3);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        listScroll = new JScrollPane(list);
        listScroll.setPreferredSize(new Dimension(542, 80));
        listScroll.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        
        Box hBox = Box.createHorizontalBox();
        Box bBox = Box.createHorizontalBox();
        Box vBox = Box.createVerticalBox();
        buildingList = new JComboBox(building.buildingList());
        levelList = new JComboBox(building.levelList());
        roomList = new JComboBox(building.roomList());
        
        hBox.add(comboBox(buildingList, "   Building :  "));
        hBox.add(Box.createHorizontalStrut(5));
        hBox.add(comboBox(levelList, "   Level :  "));
        hBox.add(Box.createHorizontalStrut(5));
        hBox.add(comboBox(roomList, "   Room :  "));
        
        bBox.add(button("Commit to Description", true, Color.GREEN, "images/icon-plus.png", listener));
        bBox.add(Box.createHorizontalStrut(10));
        bBox.add(button("Add to List", true, Color.GREEN, "images/icon-modify.png", listener));
        bBox.add(Box.createHorizontalStrut(10));
        bBox.add(button("Clear Selection", true, Color.ORANGE, "images/icon-clear.png", listener));
        bBox.add(Box.createHorizontalStrut(10));
        bBox.add(button("Clear All", true, Color.RED, "images/icon-cross.png", listener));
        bBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        vBox.add(hBox);
        vBox.add(list(list,"    Selected Establishment :"));
        vBox.add(Box.createVerticalStrut(5));
        vBox.add(bBox);
        vBox.add(Box.createVerticalStrut(5));
        vBox.setBorder(titleBorder("User Group Location"));
        vBox.add(Box.createVerticalStrut(5));
        right.add(vBox);
        return right;   
    }
    
    private Box list(JList list, String label)
    {
        Box box = Box.createHorizontalBox();   
        box.add(new JLabel(label));
        box.add(Box.createHorizontalStrut(10));
        box.add(listScroll);
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
    
    private Box comboBox(JComboBox field, String label)
    {
        Box box = Box.createHorizontalBox();   
        box.add(new JLabel(label));
        box.add(Box.createHorizontalStrut(5));
        box.add(field);
        box.add(Box.createGlue());
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
    
    private DefaultListModel listModel()
    {
       listModel.addElement((String)buildingList.getSelectedItem() + ", " +
                            (String)levelList.getSelectedItem() + ", " +
                            (String)roomList.getSelectedItem());
       return listModel;
    }
    
    private DefaultListModel clearSelectedListModel()
    {
       int[] selectedIndices = list.getSelectedIndices();
       for (int i=selectedIndices.length-1; i>-1; i--)
       {
           listModel.removeElementAt(selectedIndices[i]);
       }
       return listModel;
    }
    
    private DefaultListModel clearListModel()
    {
       listModel.removeAllElements();
       return listModel;
    }
    
    public void clearFields()
    {
        userGroupId = -1; // Reset to Default User ID
        userGroupName.setText(""); 
        userGroupEmail.setText("");
        userGroupDescription.setText("");
    }
    
    public void populateFields(int userGroupId)
    {
        try
        {
            this.userGroupId = userGroupId;
            BusinessLogic bl = new BusinessLogic(config);
            UserGroup userGroup = bl.updateUserGroupDetails(userGroupId);
            userGroupName.setText(userGroup.userGroupName()); 
            userGroupEmail.setText(userGroup.userGroupEmail());
            userGroupDescription.setText(userGroup.userGroupDescription());
        }
        catch (Exception e)
        {
            clearFields();
        }
    }
    
    private void commitModel()
    {
        String committedModel = "";
        for (int i=0; i<listModel.size(); i++)
        {
            committedModel += listModel.get(i);
            if (i>0)
                committedModel += ", ";
            else
                committedModel += " ";
        }
        userGroupDescription.setText(committedModel);
    }
    
    private void warningDialog(String warningString)
    {
        JOptionPane.showMessageDialog(this, warningString, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
     private class ButtonListener implements ActionListener 
    {
        String actionSelected = ""; 
        String warningString = ""; 
        String s_userGroupName, s_userGroupDescription, s_userGroupEmail; 
        
        public void actionPerformed(ActionEvent e) 
        {
            actionSelected = e.getActionCommand();
            if(actionSelected == "ADD")
            {              
                if(!userGroupName.getText().equals(""))
                {
                    s_userGroupName = userGroupName.getText(); 
                }
                else 
                {
                    warningString += "- No User Group Name\n";  
                }
                
                if(!userGroupDescription.getText().equals(""))
                {
                    s_userGroupDescription = userGroupDescription.getText();
                }
                else
                {
                    warningString += "- No Description\n";  
                }
                
                if(!userGroupEmail.getText().equals(""))
                {
                    s_userGroupEmail = userGroupEmail.getText();
                }
                else
                {
                    warningString += "- No Email\n";  
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
                         bl.addUserGroup(
                                    s_userGroupName,
                                    s_userGroupEmail,
                                    s_userGroupDescription);
                         userGroupBottomPane.updateTable();
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
                if (userGroupId < 0) {
                    warningDialog("You have not selected a valid User Group ID to edit.");
                    return;
                }
                
                if(!userGroupName.getText().equals(""))
                {
                    s_userGroupName = userGroupName.getText(); 
                }
                else 
                {
                    warningString += "- No User Group Name\n";  
                }
                
                if(!userGroupDescription.getText().equals(""))
                {
                    s_userGroupDescription = userGroupDescription.getText();
                }
                else
                {
                    warningString += "- No Description\n";  
                }
                
                if(!userGroupEmail.getText().equals(""))
                {
                    s_userGroupEmail = userGroupEmail.getText();
                }
                else
                {
                    warningString += "- No Email\n";  
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
                         bl.editUserGroup(
                                    userGroupId,
                                    s_userGroupName,
                                    s_userGroupEmail,
                                    s_userGroupDescription);
                         userGroupBottomPane.updateTable();
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
                if (userGroupId < 0) {
                    warningDialog("You have not selected a valid User Group ID to delete.");
                    return;
                }
                
                try
                {
                    int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete this group?\nPlease note, you will not be able to recover this group.", "Confirm Group Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (response == JOptionPane.YES_OPTION) {
                        BusinessLogic bl = new BusinessLogic(config);
                        bl.deleteUserGroup(userGroupId);
                        userGroupBottomPane.updateTable();
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
            else if(actionSelected == "Add to List")
            {
                list.setModel(listModel());
            }
            else if(actionSelected == "Clear Selection")
            {
                list.setModel(clearSelectedListModel());
            }
            else if(actionSelected == "Clear All List")
            {
                list.setModel(clearListModel());
            }
            else if(actionSelected == "Commit to Description")
            {
                commitModel();
            }
        }
    }
}