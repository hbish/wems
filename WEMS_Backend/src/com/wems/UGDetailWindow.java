package com.wems;

import javax.swing.*;
import com.wems.model.*;

public class UGDetailWindow extends JFrame
{
    public UGDetailWindow(UserGroups userGroups, int index)
    {
        setupLayout();
        setupControls(userGroups, index);
        build();
    }

    private void setupLayout()
    {
        setTitle("User Group Details");
        setLocation(450, 500);
    }

    private void setupControls(UserGroups userGroups, int index)
    {
        UGDetailPanel buDetailPanel = new UGDetailPanel(userGroups, index);
        add(buDetailPanel);
    }

    private void build()
    {
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}