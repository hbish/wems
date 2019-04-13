package com.wems;

import javax.swing.*;
import java.awt.*;
import com.wems.config.*;
import com.wems.model.*;

public class Window extends JFrame
{
	//http://download.oracle.com/javase/tutorial/uiswing/examples/components/index.html
    TabbedPane tabbedPane;
    MenuBar menuBar;
    AddImage addImage;
    String fullName;
    Config config;

    public Window(Config config, User userAccess)
    {
        fullName = "Welcome " + userAccess.firstName() + " " + userAccess.lastName();
        tabbedPane = new TabbedPane(config, userAccess);
        addImage = new AddImage();
        menuBar = new MenuBar(config, this);
        setupLayout();
        setupControls();
        build();
    }

    private void setupLayout()
    {
        int width = 785;
        int height = 770;
        setTitle("WEMS Back-end Administration Console");
        setSize(width, height);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        setIconImage(
            new ImageIcon(
            LoginDialog.class.getResource("images/icon-wems.png")
            ).getImage()
        );
    }

    private void setupControls()
    {
        Box vBox = Box.createVerticalBox();
        Box hBox = Box.createHorizontalBox();
        hBox.add(addImage.addLogo("images/wems-logobar.png"));
        hBox.add(setupLabel());
        vBox.add(hBox);
        vBox.add(tabbedPane);
        add(menuBar);
        add(vBox);
    }
    
    private JLabel setupLabel()
    {
        JLabel label = new JLabel(fullName);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }
    
    private void build()
    {
        //pack();
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}