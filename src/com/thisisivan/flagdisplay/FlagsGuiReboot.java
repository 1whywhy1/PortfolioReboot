package com.thisisivan.flagdisplay;

import com.thisisivan.WelcomePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.net.URLDecoder;

public class FlagsGuiReboot extends JFrame implements ActionListener {
    //region Variables
    private JPanel pnlMain;
    private JPanel headerPanel;
    private JRadioButton rbtIndia;
    private JRadioButton rbtAustralia;
    private JRadioButton rbtChina;
    private JRadioButton rbtNZ;
    private JRadioButton rbtRussia;
    private JButton backButton;
    private JLabel lblPicture;
    private JLabel countyName;

    private ImageIcon india, australia, china, nz, russia;
    WelcomePage welcomePage;
    //endregion

    //region Constructors
    public FlagsGuiReboot(){}
    public FlagsGuiReboot(WelcomePage welcomePage) {

        this.welcomePage = welcomePage;

        india = new ImageIcon(getClass().getResource("Images/india.png"));
        australia = new ImageIcon(getClass().getResource("Images/australia.png"));
        china = new ImageIcon(getClass().getResource("Images/china.png"));
        nz = new ImageIcon(getClass().getResource("Images/newZealand.png"));
        russia = new ImageIcon(getClass().getResource("Images/russia.png"));

        //region CloseOperation

        // Set Closing Operation and add listener to open a confirmation window.
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are you sure to close Flags of the World?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    Close();
                }
            }
        };
        this.addWindowListener(exitListener);
        //endregion

        this.setLocationRelativeTo(null);
        this.setContentPane(pnlMain);
        this.pack();
        this.setSize(500,500);
        lblPicture.setIcon(india);

        this.setVisible(true);
        rbtAustralia.addActionListener(this);
        rbtIndia.addActionListener(this);
        rbtChina.addActionListener(this);
        rbtNZ.addActionListener(this);
        rbtRussia.addActionListener(this);
        backButton.addActionListener(this);
    }
    //endregion


    //region ActiveListener
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rbtIndia) {
            SwapLabelIcon(lblPicture, india, "India");
        }
        else if ((e.getSource() == rbtAustralia)){
            SwapLabelIcon(lblPicture, australia, "Australia");
        }
        else if ((e.getSource() == rbtNZ)){
            SwapLabelIcon(lblPicture, nz, "New Zealand");
        }
        else if ((e.getSource() == rbtChina)){
            SwapLabelIcon(lblPicture, china, "China");
        }
        else if ((e.getSource() == rbtRussia)){
            SwapLabelIcon(lblPicture, russia, "Russia");
        }
        else if ((e.getSource() == backButton))
        {
            Close();
        }
    }
//endregion

//region SwapLabel
private void SwapLabelIcon(JLabel label, ImageIcon newIcon, String countryText)
{
    // Resize image to fit label
    Image image = newIcon.getImage().getScaledInstance(label.getWidth(),label.getHeight(), Image.SCALE_SMOOTH);
    // Set label image
    label.setIcon(new ImageIcon(image));
    countyName.setText(countryText);
}
    //endregion

    //region Utils
    private void Close()
    {
        this.welcomePage.setVisible(true);
        this.dispose();
    }
    //endregion

}
