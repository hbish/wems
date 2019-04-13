package com.wems;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import com.wems.business.*;
import com.wems.config.*;
import com.wems.model.*;

public class UGTable extends JPanel implements View
{   
    private JTable table;
    private TableModel model;
    private JLabel userText = new JLabel();
    private UserGroups userGroups;
    private UserGroupTopPane userGroupTopPane;
    private static UGDetailWindow detailWindow;
    private boolean showHint = false;
    private Config config;
    
    public UGTable(Config config)
    {
        this.config = config;
        updateUserGroupsData();
        userGroups.attach(this);
        
        model = new TableModel();
        table = new JTable(model); 
       
        setupLayout();
        setupControls();
    }
    
    public void setUserGroupTopPane(UserGroupTopPane userGroupTopPane)
    {
        this.userGroupTopPane = userGroupTopPane;
    }

    public void showEditDelHint(boolean showHint)
    {
        this.showHint = showHint;
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }
    
    private void updateUserGroupsData()
    {
        try
        {
            BusinessLogic bl = new BusinessLogic(config);
            this.userGroups = bl.updateUserGroups();
        }
        catch(Exception e){}
    }
    
    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        box.add(setupLabel(userGroups.size()));
        box.add(setupTable());
        add(box);
    }
    
    public void update() 
    {
        updateUserGroupsData();
        setupLabel(userGroups.size());
        model.fillData();
    }

    public void disposeDetailWindow()
    {
        if(detailWindow != null)
            detailWindow.setVisible(false);
    }
    
    private JLabel setupLabel(int no_bUnits)
    {
        if (showHint)
            userText.setText("Note: To edit/delete group, first select the row of the group.");
        else
            userText.setText("Note: There are " + String.valueOf(no_bUnits) + " User Group(s) in Record.");
        userText.setAlignmentX(CENTER_ALIGNMENT);
        return userText;
    }
        
    private JScrollPane setupTable()
    {
        JPanel panel = new JPanel();
        table.getSelectionModel().addListSelectionListener(new RowListener());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        table.getTableHeader();
        table.getColumnModel().getColumn(0).setPreferredWidth(233);
        table.getColumnModel().getColumn(1).setPreferredWidth(233);
        table.getColumnModel().getColumn(2).setPreferredWidth(240);
        panel.add(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setMinimumSize(new Dimension (700,175));
        scrollPane.setMaximumSize(new Dimension (700,175));
        scrollPane.setPreferredSize(new Dimension (700,175));

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
            if (userGroupTopPane.isAdding)
            {
                detailWindow = new UGDetailWindow(userGroups, selectedId);
            }
            else if(userGroupTopPane.isEditDeleting)
            {
                int userGroupId = userGroups.userGroup(selectedId).userGroupId();
                userGroupTopPane.populateFields(userGroupId);
            }
        }
    }
    
    private class TableModel extends AbstractTableModel 
    {
        private int ROWS = 1;
        private int COLUMNS = 3;
        private String[] columnNames = {
            "User Group Name",
            "User Group Email",
            "User Group Description"};
        private UserGroup[] userGroupText = new UserGroup[ROWS];
        
        public TableModel() 
        {
            fillData();
        }
        
        public void fillData() 
        {
            if (userGroups.size()>ROWS || userGroups.size()<ROWS)
            {
                ROWS = userGroups.size();
                userGroupText = new UserGroup[ROWS];
            }
            
            for (int i=0; i<ROWS; i++) 
            {
                UserGroup userGroup = userGroups.userGroup(i);
                setValue(i, userGroup);
            }
            fireTableDataChanged();
        }
        
        public Object getValueAt(int row) 
        {
            return userGroupText[row];
        }
        
        public String getValueAt(int row, int col) 
        {
            UserGroup userGroup = userGroupText[row];
            if (userGroup != null) 
            {
                switch (col)
                {
                    case 0: return userGroup.userGroupName();
                    case 1: return userGroup.userGroupEmail();
                    case 2: return userGroup.userGroupDescription();
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
            UserGroup value = (UserGroup)o;
            userGroupText[row] = value;
        }
    }
}