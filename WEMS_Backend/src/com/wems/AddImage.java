/**
 * Write a description of class addImage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

package com.wems;

import java.awt.*;
import javax.swing.*;

public class AddImage
{
    // instance variables - replace the example below with your own
    private JLabel imageBox;
    private ImageIcon image;
    
    public JLabel addLogo(String fileImg)
    {
        image = new ImageIcon(getClass().getResource(fileImg));
        imageBox = new JLabel(image);
        imageBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        return imageBox;
    }
}
