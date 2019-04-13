package com.wems;

import javax.swing.*;
import com.wems.model.*;

public class UserTypeDetailWindow extends JFrame
{
    public UserTypeDetailWindow(UserTypes userTypes, int index)
    {
        setupLayout();
        setupControls(userTypes, index);
        build();
    }

    private void setupLayout()
    {
        setTitle("User Type Details");
        //setLocation(450, 500);
    }

    private void setupControls(UserTypes userTypes, int index)
    {
        UserTypeDetailPanel buDetailPanel = new UserTypeDetailPanel(userTypes, index);
        add(buDetailPanel);
    }

    private void build()
    {
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}