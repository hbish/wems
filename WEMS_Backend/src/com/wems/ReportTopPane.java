/**
 * Write a description of class here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 * http://fci-h.blogspot.com/2008/06/java-reporting-jasperreports.html
 * http://java.dzone.com/articles/java-reporting-jasperreports
 * http://java.dzone.com/articles/java-reporting-part-2
 * http://java.dzone.com/articles/java-reporting-%E2%80%93-part-3
 * http://java.dzone.com/articles/java-reporting-%E2%80%93-part-4
 */
package com.wems;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.wems.config.*;

public class ReportTopPane extends JPanel
{
    // instance variables
    private JComboBox comboBox, typeBox;
    private JButton button;
    private ButtonListener listener;
    private String actionSelected;
    private ReportBottomPane reportBottomPane;
    private Config config;
    
    /**
     * Constructor for objects of class
     */
    public ReportTopPane(Config config)
    {
        this.config = config;
        setupLayout();
        setupControls();
    }
    
    public void setReportBottomPane(ReportBottomPane reportBottomPane)
    {
        this.reportBottomPane = reportBottomPane;
    }
    
    public void updateFields()
    {
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }

    private void setupControls()
    {
        listener = new ButtonListener();
        comboBox = new JComboBox(config.REPORT_LIST);
        typeBox  = new JComboBox(config.SAVE_TYPE);
        button   = new JButton("Generate Report");
        button.addActionListener(listener);
        button.setIcon(new javax.swing.ImageIcon(getClass().getResource("images/icon-goto.png")));
        if(config.WIN7_UI_LOOK)
        	button.setBackground(Color.GREEN);
        add(new JLabel("Report Type: "));
        add(comboBox);
        add(typeBox);
        add(button);
    }
    
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            actionSelected = e.getActionCommand();
            if(actionSelected == "Generate Report")
            {
                String reportType   = config.REPORT_PATH[comboBox.getSelectedIndex()];
                String reportExport = config.REPORT_EXPORT[comboBox.getSelectedIndex()];
                String reportFormat = (String)typeBox.getSelectedItem();
                reportBottomPane.displayReport(reportType, reportExport, reportFormat);
            }
        }  
    }   
}