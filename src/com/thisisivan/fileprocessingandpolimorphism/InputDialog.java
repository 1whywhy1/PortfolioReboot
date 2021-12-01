package com.thisisivan.fileprocessingandpolimorphism;

import javax.swing.*;
import java.awt.*;

public class InputDialog extends JDialog {
    private JTextField nameText;
    private JTextField priceText;
    private final JPanel pane;

    public InputDialog()
    {
        super();
        pane = new JPanel();
        pane.add(new JLabel("Enter Item name:"));
        this.setLocationRelativeTo(null);
        this.getContentPane().add(pane);

    }
    public InputDialog(JFrame mf,String title,boolean modal, int number){
        super(mf,title,modal);
        this.setMinimumSize(new Dimension(600,200));
        this.setLayout(new GridBagLayout());
        nameText = new JTextField();
        priceText = new JTextField();
        nameText.setPreferredSize(new Dimension(150,25));
        priceText.setPreferredSize(new Dimension(150,25));


        // Created a widget for name and price user input, need to figure out how to pass it back
        // to WriteOutput class for further calculations

        pane = new JPanel();
        pane.add(new JLabel("Enter Item " + number + " name:"));
        pane.add(nameText);
        pane.add(new JLabel("Enter Item " + number + " price:"));
        pane.add(priceText);
        this.setLocationRelativeTo(null);
        this.getContentPane().add(pane);
        this.setVisible(true);
    }
}
