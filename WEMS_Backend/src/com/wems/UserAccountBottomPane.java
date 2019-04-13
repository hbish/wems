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
import com.wems.config.*;

public class UserAccountBottomPane extends JPanel
{
  private UserTable table;
  private ButtonListener listener;
  private String actionSelected;
  private UserAccountTopPane userAccountTopPane;
  private Config config;
  
    public UserAccountBottomPane(Config config)
    {
        this.config = config;
        table = new UserTable(config);
        setupLayout();
        setupControls();
    }
    
    public void setUserAccountTopPane(UserAccountTopPane userAccountTopPane)
    {
        this.userAccountTopPane = userAccountTopPane;
        table.setUserAccountTopPane(userAccountTopPane);
    }

    public void updateTable()
    {
        table.update();
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }

    private void setupControls()
    {
        listener = new ButtonListener();
        Box box = Box.createVerticalBox();
        box.add(table);
        box.add(Box.createVerticalStrut(20));
        box.add(buttonBox());
        add(box);
    }
    
    private Box buttonBox()
    {
        Box box = Box.createHorizontalBox();
        box.add(button("REFRESH", "images/icon-goto.png", listener));
        box.add(Box.createHorizontalStrut(10));
        box.add(button("TOGGLE ADD/MOD/DEL MODE", "images/icon-edit.png", listener));
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        //box.add(Box.createGlue());
        return box;
    }
    
    private JButton button(String label, String iconPath, ButtonListener listener)
    {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        button.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));
        if(config.WIN7_UI_LOOK)
        	button.setBackground(Color.ORANGE);
        return button;
    }
    
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            actionSelected = e.getActionCommand();
            if(actionSelected == "REFRESH")
            {
                if(userAccountTopPane.isEditDeleting)
                    userAccountTopPane.clearFields();
                updateTable();
            }
            else if(actionSelected == "TOGGLE ADD/MOD/DEL MODE")
            {
                userAccountTopPane.buttonToggle();
                table.disposeDetailWindow();
                if (userAccountTopPane.isAdding)
                    table.showEditDelHint(false);
                else
                    table.showEditDelHint(true);
                updateTable();
            }
        }
    }
}