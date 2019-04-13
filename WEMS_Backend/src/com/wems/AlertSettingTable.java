package com.wems;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import com.wems.business.*;
import com.wems.config.*;
import com.wems.model.*;

public class AlertSettingTable extends JPanel implements View
{   
    private JTable table;
    private TableModel model;
    private JLabel alertSettingText = new JLabel();
    private AlertSettings alertSettings;
    private OverviewTopPane overviewTopPane;
    private static AlertSettingDetailWindow detailWindow;
    private Config config;
    
    public AlertSettingTable(Config config)
    {
        this.config = config;
        updateAlertSettingsData();
        alertSettings.attach(this);
        
        model = new TableModel();
        table = new JTable(model); 
       
        setupLayout();
        setupControls();
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }
    
    private void updateAlertSettingsData()
    {
        try
        {
            BusinessLogic bl = new BusinessLogic(config);
            this.alertSettings = bl.updateAlertSettings();
        }
        catch(Exception e){}
    }
    
    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        box.add(setupLabel(alertSettings.size()));
        box.add(setupTable());
        add(box);
    }
    
    public void update() 
    {
        updateAlertSettingsData();
        setupLabel(alertSettings.size());
        model.fillData();
    }
    
    public void disposeDetailWindow()
    {
        if(detailWindow != null)
            detailWindow.setVisible(false);
    }

    private JLabel setupLabel(int no_alertSettings)
    {
        alertSettingText.setText("Note: There are " + String.valueOf(no_alertSettings) + " System Alert Setting(s) in Record. Select to view & send SMS.");
        alertSettingText.setAlignmentX(CENTER_ALIGNMENT);
        return alertSettingText;
    }
        
    private JScrollPane setupTable()
    {
        JPanel panel = new JPanel();
        table.getSelectionModel().addListSelectionListener(new RowListener());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        table.getTableHeader();
        table.getColumnModel().getColumn(0).setPreferredWidth(55);
        table.getColumnModel().getColumn(1).setPreferredWidth(55);
        table.getColumnModel().getColumn(2).setPreferredWidth(55);
        table.getColumnModel().getColumn(3).setPreferredWidth(55);
        table.getColumnModel().getColumn(4).setPreferredWidth(55);
        table.getColumnModel().getColumn(5).setPreferredWidth(55);
        table.getColumnModel().getColumn(6).setPreferredWidth(55);
        table.getColumnModel().getColumn(7).setPreferredWidth(55);
        table.getColumnModel().getColumn(8).setPreferredWidth(55);
        table.getColumnModel().getColumn(9).setPreferredWidth(55);
        table.getColumnModel().getColumn(10).setPreferredWidth(55);
        table.getColumnModel().getColumn(11).setPreferredWidth(55);
        table.getColumnModel().getColumn(12).setPreferredWidth(55);
        table.getColumnModel().getColumn(13).setPreferredWidth(55);
        panel.add(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setMinimumSize(new Dimension  (700,106));
        scrollPane.setMaximumSize(new Dimension  (700,106));
        scrollPane.setPreferredSize(new Dimension(700,106));

        return scrollPane;
    }

    private class RowListener implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent alertSetting)
        {
            if (alertSetting.getValueIsAdjusting())
                return;
            int selectedId = table.getSelectionModel().getLeadSelectionIndex();
            if (selectedId < 0) return;
            
            if(detailWindow != null)  detailWindow.setVisible(false);
            detailWindow = new AlertSettingDetailWindow(config, alertSettings, selectedId);
        }
    }
    
    private class TableModel extends AbstractTableModel 
    {
        private int ROWS = 1;
        private int COLUMNS = 14;
        private String[] columnNames = {
            "Alert Setting ID",
            "Alert User Group ID",
            "Alert Score",
            "Sensor Data ID",
            "Alert Type",
            "Alert Priority",
            "Alert Timestamp",
            "Alert Min Thresh Val",
            "Alert Max Thresh Val",
            "Alert Exact Thresh Val",
            "Alert Min Thresh Bool",
            "Alert Max Thresh Bool",
            "Alert Exact Thresh Bool",
            "Alert Score Enabled"
        };
        private AlertSetting[] alertSettingText = new AlertSetting[ROWS];
        
        public TableModel() 
        {
            fillData();
        }
        
        public void fillData() 
        {
            if (alertSettings.size()>ROWS || alertSettings.size()<ROWS)
            {
                ROWS = alertSettings.size();
                alertSettingText = new AlertSetting[ROWS];
            }
            
            for (int i=0; i<ROWS; i++) 
            {
                AlertSetting alertSetting = alertSettings.alertSetting(i);
                setValue(i, alertSetting);
            }
            fireTableDataChanged();
        }
        
        public Object getValueAt(int row) 
        {
            return alertSettingText[row];
        }
        
        public String getValueAt(int row, int col) 
        {
            AlertSetting alertSetting = alertSettingText[row];
            if (alertSetting != null) 
            {
                switch (col)
                {
                    case 0:  return String.valueOf(alertSetting.alertId());
                    case 1:  return String.valueOf(alertSetting.alertUserGroup());
                    case 2:  return String.valueOf(alertSetting.score());
                    case 3:  return String.valueOf(alertSetting.dataParameterId());
                    case 4:  return alertSetting.alertType();
                    case 5:  return alertSetting.alertPriority();
                    case 6:  return alertSetting.timestamp().toString();
                    case 7:  return String.valueOf(alertSetting.minThresholdValue());
                    case 8:  return String.valueOf(alertSetting.maxThresholdValue());
                    case 9:  return String.valueOf(alertSetting.exactThresholdValue());
                    case 10: return String.valueOf(alertSetting.minThresholdEnabled());
                    case 11: return String.valueOf(alertSetting.maxThresholdEnabled());
                    case 12: return String.valueOf(alertSetting.exactThresholdEnabled());
                    case 13: return String.valueOf(alertSetting.scoreEnabled());
                }
                return null;
            }
            return null;
        }

        public int getColumnCount() 
        {
            return COLUMNS;
        }
        
        public int getRowCount() 
        {
            return ROWS;
        }
        
        public String getColumnName(int col) 
        {
            return columnNames[col];
        }
        
        public void setValue(int row, Object o) 
        {
            AlertSetting value = (AlertSetting)o;
            alertSettingText[row] = value;
        }
    }
}