package com.wems;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import com.wems.business.*;
import com.wems.config.*;
import com.wems.model.*;

public class UserTable extends JPanel implements View
{   
    private JTable table;
    private TableModel model;
    private JLabel userText = new JLabel();
    private Users users;
    private UserAccountTopPane userAccountTopPane;
    private static UserDetailWindow detailWindow;
    private boolean showHint = false;
    private Config config;
    
    public UserTable(Config config)
    {
        this.config = config;
        updateUsersData();
        users.attach(this);
        
        model = new TableModel();
        table = new JTable(model); 
       
        setupLayout();
        setupControls();
    }
    
    public void setUserAccountTopPane(UserAccountTopPane userAccountTopPane)
    {
        this.userAccountTopPane = userAccountTopPane;
    }

    public void showEditDelHint(boolean showHint)
    {
        this.showHint = showHint;
    }
    
    private void setupLayout()
    {
        setLayout(new FlowLayout());
    }
    
    private void updateUsersData()
    {
        try
        {
            BusinessLogic bl = new BusinessLogic(config);
            this.users = bl.updateUsers();
        }
        catch(Exception e){}
    }
    
    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        box.add(setupLabel(users.size()));
        box.add(setupTable());
        add(box);
    }
    
    public void update() 
    {
        updateUsersData();
        setupLabel(users.size());
        model.fillData();
    }
    
    public void disposeDetailWindow()
    {
        if(detailWindow != null)
            detailWindow.setVisible(false);
    }

    private JLabel setupLabel(int no_users)
    {
        if (showHint)
            userText.setText("Note: To edit/delete user, first select the row of the user.");
        else
            userText.setText("Note: There are " + String.valueOf(no_users) + " User(s) in Record.");
        userText.setAlignmentX(CENTER_ALIGNMENT);
        return userText;
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
        scrollPane.setMinimumSize(new Dimension  (700,275));
        scrollPane.setMaximumSize(new Dimension  (700,275));
        scrollPane.setPreferredSize(new Dimension(700,275));

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
            if (userAccountTopPane.isAdding)
            {
                detailWindow = new UserDetailWindow(users, selectedId);
            }
            else if(userAccountTopPane.isEditDeleting)
            {
                int userId = users.user(selectedId).userId();
                userAccountTopPane.populateFields(userId);
            }
        }
    }
    
    private class TableModel extends AbstractTableModel 
    {
        private int ROWS = 1;
        private int COLUMNS = 7;
        private String[] columnNames = {
            "First Name",
            "Last Name",
            "User Name",
            "Email",
            "Phone No",
            "User Type ID",
            "User Group"};
        private User[] userText = new User[ROWS];
        
        public TableModel() 
        {
            fillData();
        }
        
        public void fillData() 
        {
            if (users.size()>ROWS || users.size()<ROWS)
            {
                ROWS = users.size();
                userText = new User[ROWS];
            }
            
            for (int i=0; i<ROWS; i++) 
            {
                User user = users.user(i);
                setValue(i, user);
            }
            fireTableDataChanged();
        }
        
        public Object getValueAt(int row) 
        {
            return userText[row];
        }
        
        public String getValueAt(int row, int col) 
        {
            User user = userText[row];
            if (user != null) 
            {
                switch (col)
                {
                    case 0: return user.firstName();
                    case 1: return user.lastName();
                    case 2: return user.username();
                    case 3: return user.email();
                    case 4: return user.phNumber();
                    case 5: return String.valueOf(user.userTypeId());
                    case 6: return String.valueOf(user.userGroup());
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
            User value = (User)o;
            userText[row] = value;
        }
    }
}