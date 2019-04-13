package com.wems;

import javax.swing.*;

import com.wems.config.*;
import com.wems.model.*;

public class AlertDetailWindow extends JFrame
{
    public AlertDetailWindow(Config config, Alerts alerts, int index)
    {
        setupLayout();
        setupControls(config, alerts, index);
        build();
    }

    private void setupLayout()
    {
        setTitle("Alert Details");
        setLocation(450, 500);
    }

    private void setupControls(Config config, Alerts alerts, int index)
    {
        AlertDetailPanel detailPanel = new AlertDetailPanel(config, this, alerts, index);
        add(detailPanel);
    }

    private void build()
    {
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}