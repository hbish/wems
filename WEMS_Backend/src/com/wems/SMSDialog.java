/**
 * Write a description of class here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
package com.wems;

import java.util.Iterator;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.wems.business.*;
import com.wems.config.*;
import com.wems.smscode.*;

public class SMSDialog extends JPanel
{
   private JTextField receiver; 
   private JTextArea message;
   private Config config;
   private SMSListBox dualList;
   private ButtonListener listener;
   private String actionSelected;
   private String smsMessage;
   
    public SMSDialog(Config config)
    {
       this.config = config;
       setupLayout();
       setupControls();
    }
    
    public void setSMSMsg(String smsMessage)
    {
       this.smsMessage = smsMessage;
       message.setText(smsMessage);
    }
    
    private void setupLayout()
    {
       int width = 345;
       int height = 235;
       setLayout(new FlowLayout());
       setSize(width, height);
    }
    
    private void setupControls()
    {
        Box box = Box.createVerticalBox();
        listener = new ButtonListener();

        receiver = new JTextField(10);
        receiver.setEditable(false);
        message = new JTextArea(5,5);
        message.setEditable(false);
        
        dualList = new SMSListBox(config, this);
        dualList.addSourceElements(getUserNumbers());
        dualList.addDestinationElements(new String[]{""});
        
        box.add(dualList);
        box.add(textBox(receiver, "Mobile Number : "));
        box.add(Box.createVerticalStrut(10));
        box.add(textBox(message, "SMS Message : "));
        box.add(Box.createVerticalStrut(10));
        box.add(buttonBox());
        box.setBorder(titleBorder("SMS Mobile Messaging"));
        add(box);
        setVisible(true);
    }
    
    private Object[] getUserNumbers()
    {
        try
        {
            BusinessLogic bl = new BusinessLogic(config);
            return bl.getUserNumbers();
        }
        catch(Exception e){}
        return new String[]{""};
    }

    private Box textBox(JTextField field, String label)
    {
        Box box = Box.createHorizontalBox();
        box.add(new JLabel(label));
        box.add(Box.createVerticalStrut(10));
        box.add(field);
        box.add(Box.createGlue());
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        return box;
    }
    
    private Box textBox(JTextArea field, String label)
    {
        Box box = Box.createVerticalBox();
        box.add(new JLabel(label));
        box.add(Box.createVerticalStrut(10));
        box.add(field);
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(Box.createGlue());
        return box;
    }
    
    private Box buttonBox()
    {
        Box box = Box.createHorizontalBox(); 
        box.add(Box.createHorizontalStrut(10));
        box.add(button("Send SMS Message", Color.GREEN, "images/icon-mail.png", listener));
        box.add(Box.createHorizontalStrut(10));
        box.setAlignmentX(Component.CENTER_ALIGNMENT);
        //box.add(Box.createGlue());
        return box;
    }

    private JButton button(String label, Color color, String iconPath, ButtonListener listener)
    {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        button.setIcon(new javax.swing.ImageIcon(getClass().getResource(iconPath)));
        if(config.WIN7_UI_LOOK)
        	button.setBackground(color);
        return button;
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
        JOptionPane.showMessageDialog(this, warningString, "Data Entry Warning", JOptionPane.WARNING_MESSAGE);
    }
    
    private String removeCharAt(String s, int pos)
    {
        return s.substring(0,pos)+s.substring(pos+1);
    }
    
    public void clear()
    {
        receiver.setText("");
        message.setText("");
    }

    public String receiver()
    {
         return receiver.getText();
    }

    public String message()
    {
        return message.getText();
    }
    
    public void populateMobileNo()
    {
        // http://www.vogella.de/articles/JavaRegularExpressions/article.html
        String numbers = "", value = "";
        Iterator dest = dualList.destinationIterator();
        while (dest.hasNext())
        {
            value = ((String)dest.next()).replaceAll("[^0-9]", "");
            numbers = numbers + value + ",";
        }
        try
        {
            if (!numbers.equals("")) {
                numbers = removeCharAt(numbers, 0); // remove first comma
                numbers = removeCharAt(numbers, numbers.length()-1); // remove last comma
            }
        }
        catch (Exception e){}
        receiver.setText(numbers);
    }
        
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e) 
        {
            actionSelected = e.getActionCommand();
            
            if(actionSelected == "Send SMS Message")
            {
                if(smsMessage.equals(""))
                    warningDialog("There is no message to send to SMS!");
                else if(receiver.getText().equals(""))
                    warningDialog("Please select at least one number to send an SMS Message!");
                else
                {
                    SMSMsg smsMsg = new SMSMsg();
                    smsMsg.sendSMS(config, receiver.getText(), smsMessage);
                }
            }
        }
    }
}