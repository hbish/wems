/**
 * Write a description of class MenuBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package com.wems;

import javax.swing.*;
import com.wems.config.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Desktop;

public class MenuBar extends JMenuBar
{
    // instance variables
    JMenuBar menuBar;
    Listener listener = new Listener();
    String actionSelected;
    Config config;
    Frame frame;

    /**
     * Constructor for objects of class MenuBar
     */
    public MenuBar(Config config, JFrame frame)
    {
        // Create the menu bar
        this.config = config;
        this.frame = frame;
        setupControls(frame);
    }
    
    private JMenu jmenu(String menuName, String[] menuItem, String[] iconPath)
    {
        JMenu menu = new JMenu(menuName);
        menu.setMnemonic(KeyEvent.VK_F);
        
        for( int i = 0; i < menuItem.length; i++)
        {
            JMenuItem jmenuItem = new JMenuItem(menuItem[i]);
            jmenuItem.addActionListener(listener);
            jmenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath[i])));
            menu.add(jmenuItem);
        }
        return menu;
    }

    public void setupControls(JFrame frame)
    {
        menuBar = new JMenuBar();
        
        // Create a fileMenu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(jmenu("File", new String[]{"Open Web Console", "Sign Out", "Close"}, new String[]{"images/icon-load.png",  "images/icon-goto.png", "images/icon-quit.png"}));
        menuBar.add(jmenu("Help", new String[]{"Get Help", "About WEMS Backend Panel"},  new String[]{"images/icon-about.png", "images/icon-wems.png"}));
        frame.setJMenuBar(menuBar);
    }
    
    private class Listener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            actionSelected = e.getActionCommand();
            if (actionSelected.equals("Open Web Console"))
            {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    java.net.URI uri = new java.net.URI( config.SYS_WEB_CONSOLE );
                    desktop.browse( uri );
                }
                catch ( Exception ex ) {
                    JOptionPane.showMessageDialog(null, ex.getMessage() );
                }
            }
            else if (actionSelected.equals("Sign Out"))
            {
                frame.setVisible(false);
                new LoginDialog();
            }
            else if (actionSelected.equals("Close"))
            {
                frame.dispose();
            }
            else if (actionSelected.equals("Get Help"))
            {
                String message = "WEMS© Back-end Console Instructions:\n\n" +
                                 "To use this console, your system must be configured for your user authentication as Administrator.\n" +
                                 "You will need to have the WEMD Database installed and setup on your server to operate this application.\n" +
                                 "If you have any questions, need additional help, or wish to request feedback on improvements or updates,\n" +
                                 "please contact the WEMS administrator at " + config.SYS_EMAIL_CONTACT;
                JOptionPane.showMessageDialog(frame,
                                              message,
                                              "Console Instructions",
                                              JOptionPane.INFORMATION_MESSAGE,
                                              new ImageIcon(MenuBar.class.getResource("images/wems-help.png")));
            }
            else if (actionSelected.equals("About WEMS Backend Panel"))
            {
                String message = "About WEMS Back-end Panel:\n\n" +
                                 "WEMS© 2011 Developed by Dragon Developments for the University of Technology, Sydney.\n" +
                                 "All Rights Reserved. For more information, please contact your local service provider.\n\n" +
                                 "Licensed to: " + config.SYS_USER_OWNER + "\n" +
                                 "Serial Number: " + config.SYS_USER_SERIAL;
                JOptionPane.showMessageDialog(frame,
                                              message,
                                              "About WEMS Back-end Panel",
                                              JOptionPane.INFORMATION_MESSAGE,
                                              new ImageIcon(MenuBar.class.getResource("images/wems-icon.png")));
            }
        }
    }
}