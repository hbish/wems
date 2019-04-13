package com.wems;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import com.wems.business.*;
import com.wems.config.*;
import com.wems.model.*;

public class UserTypeTable extends JPanel implements View
{   
    private JTable table;
    private TableModel model;
    private JLabel userType = new JLabel();
    private UserTypes userTypes;
    private UserTypePane userTypePane;
    private static UserTypeDetailWindow detailWindow;
    private Config config;
    private int userTypeId = -1;
    
    public UserTypeTable(Config config)
    {
        this.config = config;
        updateUserTypesData();
        userTypes.attach(this);
        
        model = new TableModel();
        table = new JTable(model); 
       
        setupLayout();
        setupControls();
    }
    
    public void setUserTypePane(UserTypePane userTypePane)
    {
        this.userTypePane = userTypePane;
    }
    
    public int getSelectedUserTypeId()
    {
        return userTypeId;
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }
    
    private void updateUserTypesData()
    {
        try
        {
            BusinessLogic bl = new BusinessLogic(config);
            this.userTypes = bl.updateUserTypes();
            userTypeId = -1; // Default ID Selected (i.e. none)
        }
        catch(Exception e){}
    }
    
    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        box.add(setupLabel(false, userTypes.size()));
        box.add(setupTable());
        add(box);
    }
    
    public void update() 
    {
        updateUserTypesData();
        setupLabel(userTypePane.isToEdit, userTypes.size());
        model.fillData();
    }
    
    public void disposeDetailWindow()
    {
        if(detailWindow != null)
            detailWindow.setVisible(false);
    }
    
    private JLabel setupLabel(boolean isToEdit, int no_types)
    {
        if (isToEdit)
            userType.setText("Note: To edit/delete user type, first select the row of the type.");
        else
            userType.setText("Note: There are " + String.valueOf(no_types) + " User Type(s) in Record.");
        userType.setAlignmentX(CENTER_ALIGNMENT);
        return userType;
    }
        
    private JScrollPane setupTable()
    {
        JPanel panel = new JPanel();
        table.getSelectionModel().addListSelectionListener(new RowListener());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        table.getTableHeader();
        table.getColumnModel().getColumn(0).setPreferredWidth(233);
        table.getColumnModel().getColumn(1).setPreferredWidth(233);
        table.getColumnModel().getColumn(2).setPreferredWidth(233);
        panel.add(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setMinimumSize(new Dimension  (700,395));
        scrollPane.setMaximumSize(new Dimension  (700,395));
        scrollPane.setPreferredSize(new Dimension(700,395));

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
            userTypeId = userTypes.userType(selectedId).userTypeId();
            if (userTypePane.isToEdit)
                userTypePane.updateFields(userTypes.userType(selectedId));
            else
                detailWindow = new UserTypeDetailWindow(userTypes, selectedId);
        }
    }
    
    private class TableModel extends AbstractTableModel 
    {
        private int ROWS = 1;
        private int COLUMNS = 3;
        private String[] columnNames = {
            "User Type ID",
            "User Type Name",
            "User Type Description"};
        private UserType[] userTypeText = new UserType[ROWS];
        
        public TableModel() 
        {
            fillData();
        }
        
        public void fillData() 
        {
            if (userTypes.size()>ROWS || userTypes.size()<ROWS)
            {
                ROWS = userTypes.size();
                userTypeText = new UserType[ROWS];
            }
            
            for (int i=0; i<ROWS; i++) 
            {
                UserType userType = userTypes.userType(i);
                setValue(i, userType);
            }
            fireTableDataChanged();
        }
        
        public Object getValueAt(int row) 
        {
            return userTypeText[row];
        }
        
        public String getValueAt(int row, int col) 
        {
            UserType userType = userTypeText[row];
            if (userType != null) 
            {
                switch (col)
                {
                    case 0: return String.valueOf(userType.userTypeId());
                    case 1: return userType.userTypeName();
                    case 2: return userType.userTypeDescription();
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
            UserType value = (UserType)o;
            userTypeText[row] = value;
        }
    }
}