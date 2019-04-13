/**
 * Write a description of class TabbedPane here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package com.wems;

import javax.swing.*;
import javax.swing.event.*;
import com.wems.config.*;
import com.wems.model.*;

public class TabbedPane extends JTabbedPane
{
    OverviewPane overviewPane;
    DevicePane devicePane;
    UserAccountPane userAccountPane;
    UserGroupPane userGroupPane;
    UserTypePane userTypePane;
    ReportPane reportPane;
    SettingPane settingPane;
    ChangeListenerImpl changeListenerImpl;
    
    public TabbedPane(Config config, User userAccess)
    {
        setupControls(config, userAccess);
        this.addChangeListener(changeListenerImpl);
    }

    private void setupControls(Config config, User userAccess)
    {
        overviewPane = new OverviewPane(config);
        devicePane = new DevicePane(config);
        userAccountPane = new UserAccountPane(config, userAccess);
        userGroupPane = new UserGroupPane(config);
        userTypePane = new UserTypePane(config);
        reportPane = new ReportPane(config);
        settingPane = new SettingPane(config);
        changeListenerImpl = new ChangeListenerImpl();
        
        addTab("Overview", overviewPane);
        addTab("Device", devicePane);
        addTab("User Account", userAccountPane);
        addTab("User Group", userGroupPane);
        addTab("User Type", userTypePane);
        addTab("Reports", reportPane);
        addTab("System Settings", settingPane);
    }
    
    private class ChangeListenerImpl implements ChangeListener 
    {
        // This method is called whenever the selected tab changes
        public void stateChanged(ChangeEvent evt)
        {
            JTabbedPane pane = (JTabbedPane)evt.getSource();
            // Get current tab
            switch (pane.getSelectedIndex())
            {
                case 0:
                {
                    overviewPane.refreshData();
                    break;
                }
                case 1:
                {
                    devicePane.refreshData();
                    break;
                }
                case 2:
                {
                    userAccountPane.refreshData();
                    break;
                }
                case 3:
                {
                    userGroupPane.refreshData();
                    break;
                }
                case 4:
                {
                    userTypePane.refreshData();
                    break;
                }
                case 5:
                {
                    reportPane.refreshData();
                    break;
                }
                case 6:
                {
                    settingPane.refreshData();
                    break;
                }
            }
        }
    }
}