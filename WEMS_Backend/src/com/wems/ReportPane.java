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

public class ReportPane extends JPanel
{
    private ReportTopPane reportTopPane;
    private ReportBottomPane reportBottomPane;
   
    public ReportPane(Config config)
    {
        reportTopPane = new ReportTopPane(config);
        reportBottomPane = new ReportBottomPane(config);
        reportTopPane.setReportBottomPane(reportBottomPane);
        reportBottomPane.setReportTopPane(reportTopPane);
        setupLayout();
        setupControls();
    }

    public void refreshData()
    {
        reportBottomPane.refreshData();
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }

    private void setupControls()
    {
        Box vBox = Box.createVerticalBox();
        vBox.add(reportTopPane);
        vBox.add(reportBottomPane);
        add(vBox);
    }
}