package com.wems;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import com.wems.config.*;

public class ReportBottomPane extends JPanel
{
    private JPanel panel;
    private Config config;
    private ReportTopPane reportTopPane;
  
    public ReportBottomPane(Config config)
    {
        this.config = config;
        setupLayout();
        setupControls();
    }
    
    public void refreshData()
    {
    }
    
    public void setReportTopPane(ReportTopPane reportTopPane)
    {
        this.reportTopPane = reportTopPane;
    }
    
    public void displayReport(String reportType, String reportExport, String reportFormat)
    {
        ReportViewer report = new ReportViewer(config);
        try
        {
            remove(panel);
            panel = report.displayReport(reportType, reportExport, reportFormat);
            add(panel);
            revalidate(); // Re-update Panel and Display
            //repaint();
        }
        catch (Exception e)
        {
            warningDialog("The requested report cannot be generated at this time.\nError Notice: " + e.getMessage());
        }
    }
       
    private TitledBorder titleBorder(String label)
    {
        TitledBorder topBorder = BorderFactory.createTitledBorder(label);
        topBorder.setTitlePosition(TitledBorder.TOP);
        topBorder.setTitleFont(new Font("Sans Serif", Font.BOLD, 12));
        return topBorder;
    }
    
    private void warningDialog(String warningString)
    {
        JOptionPane.showMessageDialog(this, warningString, "Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
        setBorder(titleBorder("Report Panel"));
        setPreferredSize(new Dimension(700,520));
    }

    private void setupControls()
    {
        panel = new JPanel();
        add(panel);
        setVisible(true);
    }
}