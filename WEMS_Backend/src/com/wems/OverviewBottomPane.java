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
import javax.swing.border.*;
import com.wems.config.*;

public class OverviewBottomPane extends JPanel
{
    private AlertSettingTable table;
    private ButtonListener listener;
    private String actionSelected;
    private Config config;
    OverviewTopPane overviewTopPane;
    OverviewMiddlePane overviewMiddlePane;
    
    public OverviewBottomPane(Config config)
    {
        this.config = config;
        table = new AlertSettingTable(config);
        setupLayout();
        setupControls();
    }

    public void setOverviewTopPane(OverviewTopPane overviewTopPane)
    {
        this.overviewTopPane = overviewTopPane;
    }
    
    public void setOverviewMiddlePane(OverviewMiddlePane overviewMiddlePane)
    {
        this.overviewMiddlePane = overviewMiddlePane;
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
        box.add(Box.createVerticalStrut(0));
        box.add(buttonBox());
        box.setBorder(titleBorder("System Alert Setting Details"));
        add(box);
    }
    
    private TitledBorder titleBorder(String label)
    {
        TitledBorder topBorder = BorderFactory.createTitledBorder(label);
        topBorder.setTitlePosition(TitledBorder.TOP);
        topBorder.setTitleFont(new Font("Sans Serif", Font.BOLD, 12));
        return topBorder;
    }
    
    private Box buttonBox()
    {
        Box box = Box.createHorizontalBox();
        box.add(button("REFRESH ALERT SETTINGS", "images/icon-goto.png", listener));
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
            if(actionSelected == "REFRESH ALERT SETTINGS")
            {
                updateTable();
            }
        }
    }
}