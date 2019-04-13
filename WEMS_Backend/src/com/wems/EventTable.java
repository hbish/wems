package com.wems;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import com.wems.business.*;
import com.wems.config.*;
import com.wems.model.*;
import com.wems.model.Event;

public class EventTable extends JPanel implements View
{   
    private JTable table;
    private TableModel model;
    private JLabel eventText = new JLabel();
    private Events events;
    private OverviewTopPane overviewTopPane;
    private static EventDetailWindow detailWindow;
    private Config config;
    
    public EventTable(Config config)
    {
        this.config = config;
        updateEventsData();
        events.attach(this);
        
        model = new TableModel();
        table = new JTable(model); 
       
        setupLayout();
        setupControls();
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }
    
    private void updateEventsData()
    {
        try
        {
            BusinessLogic bl = new BusinessLogic(config);
            this.events = bl.updateEvents();
        }
        catch(Exception e){}
    }
    
    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        box.add(setupLabel(events.size()));
        box.add(setupTable());
        add(box);
    }
    
    public void update() 
    {
        updateEventsData();
        setupLabel(events.size());
        model.fillData();
    }
    
    public void disposeDetailWindow()
    {
        if(detailWindow != null)
            detailWindow.setVisible(false);
    }

    private JLabel setupLabel(int no_events)
    {
        eventText.setText("Note: There are " + String.valueOf(no_events) + " System Event(s) in Record. Select to view & send SMS.");
        eventText.setAlignmentX(CENTER_ALIGNMENT);
        return eventText;
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
        public void valueChanged(ListSelectionEvent event)
        {
            if (event.getValueIsAdjusting())
                return;
            int selectedId = table.getSelectionModel().getLeadSelectionIndex();
            if (selectedId < 0) return;
            
            if(detailWindow != null)  detailWindow.setVisible(false);
            detailWindow = new EventDetailWindow(config, events, selectedId);
        }
    }
    
    private class TableModel extends AbstractTableModel 
    {
        private int ROWS = 1;
        private int COLUMNS = 5;
        private String[] columnNames = {
            "Event ID",
            "Device ID",
            "Alert ID",
            "Event Type",
            "Event Time"};
        private Event[] eventText = new Event[ROWS];
        
        public TableModel() 
        {
            fillData();
        }
        
        public void fillData() 
        {
            if (events.size()>ROWS || events.size()<ROWS)
            {
                ROWS = events.size();
                eventText = new Event[ROWS];
            }
            
            for (int i=0; i<ROWS; i++) 
            {
                Event event = events.event(i);
                setValue(i, event);
            }
            fireTableDataChanged();
        }
        
        public Object getValueAt(int row) 
        {
            return eventText[row];
        }
        
        public String getValueAt(int row, int col) 
        {
            Event event = eventText[row];
            if (event != null) 
            {
                switch (col)
                {
                    case 0: return String.valueOf(event.eventsId());
                    case 1: return String.valueOf(event.deviceId());
                    case 2: return String.valueOf(event.alertId());
                    case 3: return String.valueOf(event.eventType());
                    case 4: return event.timeStamp().toString();
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
            Event value = (Event)o;
            eventText[row] = value;
        }
    }
}