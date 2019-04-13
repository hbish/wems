/**
 * Write a description of class here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package com.wems;

import java.awt.*;
import javax.swing.*;
import com.wems.config.*;

public class OverviewPane extends JPanel
{
    private OverviewTopPane    overviewTopPane;
    private OverviewMiddlePane overviewMiddlePane;
    private OverviewBottomPane overviewBottomPane;
   
    public OverviewPane(Config config)
    {
        overviewTopPane = new OverviewTopPane(config);
        overviewMiddlePane = new OverviewMiddlePane(config);
        overviewBottomPane = new OverviewBottomPane(config);
        
        overviewTopPane.setOverviewMiddlePane(overviewMiddlePane);
        overviewTopPane.setOverviewBottomPane(overviewBottomPane);
        overviewMiddlePane.setOverviewTopPane(overviewTopPane);
        overviewMiddlePane.setOverviewBottomPane(overviewBottomPane);
        overviewBottomPane.setOverviewTopPane(overviewTopPane);
        overviewBottomPane.setOverviewMiddlePane(overviewMiddlePane);
        
        setupLayout();
        setupControls();
    }
    
    public void refreshData()
    {
        overviewTopPane.updateTable();
        overviewMiddlePane.updateTable();
        overviewBottomPane.updateTable();
    }

    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }

    private void setupControls()
    {
        add(overviewTopPane);
        add(overviewMiddlePane);
        add(overviewBottomPane);
    }
}