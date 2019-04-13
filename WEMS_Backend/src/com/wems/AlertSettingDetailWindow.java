package com.wems;

import javax.swing.*;
import com.wems.config.*;
import com.wems.model.*;

public class AlertSettingDetailWindow extends JFrame
{
    public AlertSettingDetailWindow(Config config, AlertSettings alertSettings, int index)
    {
        setupLayout();
        setupControls(config, alertSettings, index);
        build();
    }

    private void setupLayout()
    {
        setTitle("Alert Setting Details");
        setLocation(450, 500);
    }

    private void setupControls(Config config, AlertSettings alertSettings, int index)
    {
        AlertSettingDetailPanel detailPanel = new AlertSettingDetailPanel(config, this, alertSettings, index);
        add(detailPanel);
    }

    private void build()
    {
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}