package com.thisisivan;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CardLayoutDemo extends JFrame /*implements ItemListener*/ {
    private JPanel mainPanel;
    private JPanel panel1;
    private JPanel panel2;

    //Where instance variables are declared:
    JPanel cards;
    final static String BUTTONPANEL = "yellow page";
    final static String TEXTPANEL = "red page";
    private final CardLayout cLayout;
    private final JPanel mainPane;
    boolean isRedVisible;


    CardLayoutDemo() {
//        //Where the components controlled by the CardLayout are initialized:
//        //Create the "cards".
//        JPanel card1 = new JPanel();
//        card1.setPreferredSize(new Dimension(420,420));
//        card1.setBackground(Color.RED);
//        JButton button1 = new JButton();
//        card1.add(button1);
//        card1.setVisible(true);
//
//        JPanel card2 = new JPanel();
//        card2.setPreferredSize(new Dimension(420,420));
//        card2.setBackground(Color.GREEN);
//        TextField text2 = new TextField();
//        card2.add(text2);
//        card2.setVisible(true);
//        card2.setVisible(true);
//
//        //Create the panel that contains the "cards".
//        cards = new JPanel(new CardLayout());
//        cards.add(card1, BUTTONPANEL);
//        cards.add(card2, TEXTPANEL);
//
//        //Where the GUI is assembled:
//        //Put the JComboBox in a JPanel to get a nicer look.
//        JPanel comboBoxPane = new JPanel(); //use FlowLayout
//        String comboBoxItems[] = { BUTTONPANEL, TEXTPANEL };
//        JComboBox cb = new JComboBox(comboBoxItems);
//        cb.setEditable(false);
//        cb.addItemListener(this);
//        comboBoxPane.add(cb);
//
//
//        mainPanel.add(comboBoxPane, BorderLayout.PAGE_START);
//        mainPanel.add(cards, BorderLayout.CENTER);


        //new life
        setTitle("Card Layout Demo");
        this.setForeground(Color.white);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPane = new JPanel();
        mainPane.setPreferredSize(new Dimension(420,420));
        cLayout = new CardLayout();
        mainPane.setLayout(cLayout);

        JPanel yellowPane = new JPanel();
        yellowPane.setBackground(Color.YELLOW);
        JPanel redPane = new JPanel();
        redPane.setBackground(Color.RED);

        mainPane.add(BUTTONPANEL,yellowPane);
        mainPane.add(TEXTPANEL,redPane);
        showRedPane();

        JButton button = new JButton("Switch Panes");
        button.addActionListener(e->switchPanes());

        setLayout(new BorderLayout());
        add(mainPane, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);



        //this.setContentPane(mainPanel);

        this.pack();

        this.setVisible(true);
    }

    // Look here for more detailed example of what you want. You can create these Panels as a separate class and then add them to the menu JFrame. this way will make sure a seamless transition.
    //https://stackoverflow.com/questions/46870004/how-to-switch-jpanels-in-a-jframe-from-within-the-panel

    //Method came from the ItemListener class implementation,
    //contains functionality to process the combo box item selecting
//    @Override
//    public void itemStateChanged(ItemEvent evt) {
//        CardLayout cl = (CardLayout)(cards.getLayout());
//        cl.show(cards, (String)evt.getItem());
//    }

    void switchPanes(){
        System.out.println("Hey");
        if(isRedVisible){
            showYellowPane();
        } else
        {
            showRedPane();
        }
    }

    void showRedPane(){
        cLayout.show(mainPane,TEXTPANEL);
        isRedVisible = true;
    }
    void showYellowPane(){
        cLayout.show(mainPane,BUTTONPANEL);
        isRedVisible = false;
    }

    public static void main(String[] args) {
        CardLayoutDemo cardLayoutDemo = new CardLayoutDemo();
    }
}
