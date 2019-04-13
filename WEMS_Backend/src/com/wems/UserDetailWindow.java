package com.wems;

import javax.swing.*;
import com.wems.model.*;

public class UserDetailWindow extends JFrame
{
    public UserDetailWindow(Users users, int index)
    {
        setupLayout();
        setupControls(users, index);
        build();
    }

    private void setupLayout()
    {
        setTitle("User Details");
        setLocation(450, 500);
    }

    private void setupControls(Users users, int index)
    {
        UserDetailPanel detailPanel = new UserDetailPanel(users, index);
        add(detailPanel);
    }

    private void build()
    {
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}