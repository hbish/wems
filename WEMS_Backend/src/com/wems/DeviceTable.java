package com.wems;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import com.wems.business.*;
import com.wems.config.*;
import com.wems.model.*;

public class DeviceTable extends JPanel implements View
{   
    private JTable table;
    private TableModel model;
    private JLabel deviceText = new JLabel();
    private Devices devices;
    private DeviceTopPane deviceTopPane;
    private static DeviceDetailWindow detailWindow;
    private boolean showHint = false;
    private Config config;
    
    public DeviceTable(Config config)
    {
        this.config = config;
        updateDevicesData();
        devices.attach(this);
        
        model = new TableModel();
        table = new JTable(model); 
       
        setupLayout();
        setupControls();
    }
    
    public void setDeviceTopPane(DeviceTopPane deviceTopPane)
    {
        this.deviceTopPane = deviceTopPane;
    }

    public void showEditDelHint(boolean showHint)
    {
        this.showHint = showHint;
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }
    
    private void updateDevicesData()
    {
        try
        {
            BusinessLogic bl = new BusinessLogic(config);
            this.devices = bl.updateDevices();
        }
        catch(Exception e){}
    }
    
    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        box.add(setupLabel(devices.size()));
        box.add(setupTable());
        add(box);
    }
    
    public void update() 
    {
        updateDevicesData();
        setupLabel(devices.size());
        model.fillData();
    }
    
    public void disposeDetailWindow()
    {
        if(detailWindow != null)
            detailWindow.setVisible(false);
    }

    private JLabel setupLabel(int no_devices)
    {
        if (showHint)
            deviceText.setText("Note: To edit/delete device, first select the row of the device.");
        else
            deviceText.setText("Note: There are " + String.valueOf(no_devices) + " Device(s) in Record.");
        deviceText.setAlignmentX(CENTER_ALIGNMENT);
        return deviceText;
    }
        
    private JScrollPane setupTable()
    {
        JPanel panel = new JPanel();
        table.getSelectionModel().addListSelectionListener(new RowListener());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        table.getTableHeader();
        table.getColumnModel().getColumn(0).setPreferredWidth(115);
        table.getColumnModel().getColumn(1).setPreferredWidth(115);
        table.getColumnModel().getColumn(2).setPreferredWidth(115);
        table.getColumnModel().getColumn(3).setPreferredWidth(115);
        table.getColumnModel().getColumn(4).setPreferredWidth(115);
        table.getColumnModel().getColumn(5).setPreferredWidth(115);
        panel.add(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setMinimumSize(new Dimension  (700,245));
        scrollPane.setMaximumSize(new Dimension  (700,245));
        scrollPane.setPreferredSize(new Dimension(700,245));

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
            if (deviceTopPane.isAdding)
            {
                detailWindow = new DeviceDetailWindow(devices, selectedId);
            }
            else if(deviceTopPane.isEditDeleting)
            {
                deviceTopPane.populateFields(devices.device(selectedId));
            }
        }
    }
    
    private class TableModel extends AbstractTableModel 
    {
        private int ROWS = 1;
        private int COLUMNS = 10;
        private String[] columnNames = {
            "Device UID",
            "Device ID",
            "Room UID",
            "User Group",
            "Type",
            "Brand",
            "Model",
            "Serial No.",
            "MAC Address",
            "Is Connected"};
        private Device[] deviceText = new Device[ROWS];
        
        public TableModel() 
        {
            fillData();
        }
        
        public void fillData() 
        {
            if (devices.size()>ROWS || devices.size()<ROWS)
            {
                ROWS = devices.size();
                deviceText = new Device[ROWS];
            }
            
            for (int i=0; i<ROWS; i++) 
            {
                Device device = devices.device(i);
                setValue(i, device);
            }
            fireTableDataChanged();
        }
        
        public Object getValueAt(int row) 
        {
            return deviceText[row];
        }
        
        public String getValueAt(int row, int col) 
        {
            Device device = deviceText[row];
            if (device != null) 
            {
                switch (col)
                {
                    case 0: return String.valueOf(device.deviceuid());
                    case 1: return String.valueOf(device.id());
                    case 2: return String.valueOf(device.roomuid());
                    case 3: return String.valueOf(device.userGroup());
                    case 4: return device.type();
                    case 5: return device.brand();
                    case 6: return device.model();
                    case 7: return device.serial();
                    case 8: return device.macaddress();
                    case 9: return String.valueOf(device.connected());
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
            Device value = (Device)o;
            deviceText[row] = value;
        }
    }
}