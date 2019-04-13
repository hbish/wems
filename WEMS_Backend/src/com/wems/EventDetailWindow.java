package com.wems;

import javax.swing.*;
import com.wems.config.*;
import com.wems.model.*;

public class EventDetailWindow extends JFrame
{
    public EventDetailWindow(Config config, Events events, int index)
    {
        setupLayout();
        setupControls(config, events, index);
        build();
    }

    private void setupLayout()
    {
        setTitle("Event Details");
        setLocation(450, 500);
    }

    private void setupControls(Config config, Events events, int index)
    {
        EventDetailPanel detailPanel = new EventDetailPanel(config, this, events, index);
        add(detailPanel);
    }

    private void build()
    {
        pack();
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}