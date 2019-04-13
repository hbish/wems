package com.wems;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import com.wems.business.*;
import com.wems.config.*;
import com.wems.model.*;

public class AlertTable extends JPanel implements View
{   
    private JTable table;
    private TableModel model;
    private JLabel alertText = new JLabel();
    private Alerts alerts;
    private OverviewTopPane overviewTopPane;
    private static AlertDetailWindow detailWindow;
    private Config config;
    
    public AlertTable(Config config)
    {
        this.config = config;
        updateAlertsData();
        alerts.attach(this);
        
        model = new TableModel();
        table = new JTable(model); 
       
        setupLayout();
        setupControls();
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }
    
    private void updateAlertsData()
    {
        try
        {
            BusinessLogic bl = new BusinessLogic(config);
            this.alerts = bl.updateAlerts();
        }
        catch(Exception e){}
    }
    
    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        box.add(setupLabel(alerts.size()));
        box.add(setupTable());
        add(box);
    }
    
    public void update() 
    {
        updateAlertsData();
        setupLabel(alerts.size());
        model.fillData();
    }
    
    public void disposeDetailWindow()
    {
        if(detailWindow != null)
            detailWindow.setVisible(false);
    }

    private JLabel setupLabel(int no_alerts)
    {
        alertText.setText("Note: There are " + String.valueOf(no_alerts) + " System Alert(s) in Record. Select to view & send SMS.");
        alertText.setAlignmentX(CENTER_ALIGNMENT);
        return alertText;
    }
        
    private JScrollPane setupTable()
    {
        JPanel panel = new JPanel();
        table.getSelectionModel().addListSelectionListener(new RowListener());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        table.getTableHeader();
        table.getColumnModel().getColumn(0).setPreferredWidth(136);
        table.getColumnModel().getColumn(1).setPreferredWidth(136);
        table.getColumnModel().getColumn(2).setPreferredWidth(136);
        table.getColumnModel().getColumn(3).setPreferredWidth(136);
        table.getColumnModel().getColumn(4).setPreferredWidth(136);
        panel.add(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setMinimumSize(new Dimension  (700,108));
        scrollPane.setMaximumSize(new Dimension  (700,108));
        scrollPane.setPreferredSize(new Dimension(700,108));

        return scrollPane;
    }

    private class RowListener implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent alert)
        {
            if (alert.getValueIsAdjusting())
                return;
            int selectedId = table.getSelectionModel().getLeadSelectionIndex();
            if (selectedId < 0) return;
            
            if(detailWindow != null)  detailWindow.setVisible(false);
            detailWindow = new AlertDetailWindow(config, alerts, selectedId);
        }
    }
    
    private class TableModel extends AbstractTableModel 
    {
        private int ROWS = 1;
        private int COLUMNS = 5;
        private String[] columnNames = {
            "Alert ID",
            "Alert Type",
            "Alert Priority",
            "Alert Time",
            "Alert Data Dump"};
        private Alert[] alertText = new Alert[ROWS];
        
        public TableModel() 
        {
            fillData();
        }
        
        public void fillData() 
        {
            if (alerts.size()>ROWS || alerts.size()<ROWS)
            {
                ROWS = alerts.size();
                alertText = new Alert[ROWS];
            }
            
            for (int i=0; i<ROWS; i++) 
            {
                Alert alert = alerts.alert(i);
                setValue(i, alert);
            }
            fireTableDataChanged();
        }
        
        public Object getValueAt(int row) 
        {
            return alertText[row];
        }
        
        public String getValueAt(int row, int col) 
        {
            Alert alert = alertText[row];
            if (alert != null) 
            {
                switch (col)
                {
                    case 0: return String.valueOf(alert.alertsId());
                    case 1: return alert.alertType();
                    case 2: return alert.alertPriority();
                    case 3: return alert.timeStamp().toString();
                    case 4: return String.valueOf(alert.sensorDataDump());
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
            Alert value = (Alert)o;
            alertText[row] = value;
        }
    }
}