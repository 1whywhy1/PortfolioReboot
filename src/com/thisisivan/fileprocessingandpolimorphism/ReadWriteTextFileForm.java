package com.thisisivan.fileprocessingandpolimorphism;

import com.thisisivan.WelcomePage;
import com.thisisivan.fileprocessingandpolimorphism.MyUtils.ReadTextFile;
import com.thisisivan.fileprocessingandpolimorphism.MyUtils.WriteAs;
import com.thisisivan.fileprocessingandpolimorphism.MyUtils.WriteToFile;
import com.thisisivan.fileprocessingandpolimorphism.MyUtils.WriterType;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintWriter;

public class ReadWriteTextFileForm extends JFrame {

    //region Variables
    private JPanel mainPane;
    private JButton readTextButton;
    private JButton writeTextToFileButton;
    private JTextArea textArea;
    private JButton backButton;
    WelcomePage welcomePage;

    //endregion

    //region Constructors
    public ReadWriteTextFileForm(){

    }
    public ReadWriteTextFileForm(WelcomePage welcomePage){
        this.welcomePage = welcomePage;
        readTextButton.addActionListener(e->ReadReceiptFile());
        writeTextToFileButton.addActionListener(e->WriteFile());
        backButton.addActionListener(e->Close());

        //this.setForeground(Color.white);

        //region CloseOperation
        // Set Closing Operation and add listener to open a confirmation window.
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are You Sure to Close Read Text File App?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    Close();
                }
            }
        };
        this.addWindowListener(exitListener);
        //endregion

        this.setContentPane(mainPane);

        //this.setUndecorated(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    //endregion

    // Read file and display in the TextArea
    //region ReadFile
    public void ReadReceiptFile(){
       ReadTextFile readTextFile = new ReadTextFile();
       String str;
       try
       {
           str = readTextFile.ReadReportFile();
           if (str.equals(""))
           {
               throw new NullPointerException("The file you are accessing might be empty");
           }
           //textArea.read(br,null);
           textArea.setText(str);
       }
       catch (NullPointerException npe)
       {
           JOptionPane.showMessageDialog(null, npe, "InfoBox: Empty file",  JOptionPane.ERROR_MESSAGE);
       }
       catch(Exception e)
       {
           JOptionPane.showMessageDialog(null, "Failed to complete this process!", "InfoBox: Error",  JOptionPane.ERROR_MESSAGE);
       }
       finally
       {
           textArea.requestFocus();
       }
    }
    //endregion

    // Offer file save as format, launch RecordItemInfo method
    //region WriteToFile
    public void WriteFile()
    {
        String[] buttons = { "Cancel", "DOC", "TXT" };
        int confirm = JOptionPane.showOptionDialog(
                null, "How would you like to save this file as?",
                "Output format", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[2]);


        String outputPath = null;

        switch(confirm) {
            case 0:
                break;
            case 1:
                outputPath = WriteToFile.GetPathToFile(WriteAs.doc);
                RecordItemInfo(outputPath);
                //WriteToFile.WriteToAs(output, WriteAs.doc);
                break;
            case 2:
                outputPath = WriteToFile.GetPathToFile(WriteAs.txt);
               RecordItemInfo(outputPath);
                //WriteToFile.WriteToAs(output, WriteAs.txt);
                break;
        }
    }
    //endregion

    // Show a series of InputDialogs to get the input
    // to keep a record of an item list
    // with names and prices. Write to a file of choice.
    //region RecordItemInfo
    private void RecordItemInfo(String path)
    {
        try {
            int itemsQuantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter a number of items", "Items Quantity", JOptionPane.QUESTION_MESSAGE));

            Item[] items = new Item[itemsQuantity];

            PrintWriter output;
            output = new PrintWriter(path);

            // Loop to get input from user and add it to PrintWriter
            for( int i = 0; i < items.length; ++i) {
                try {
                    items[i] = new Item();

                    items[i].setItemName(JOptionPane.showInputDialog(
                            null, "What is Item No " + (i+1) + " name?",
                            "Item Name",
                            JOptionPane.QUESTION_MESSAGE));

                    items[i].setItemCost(Float.parseFloat(JOptionPane.showInputDialog(
                            null, "What is Item No " + (i+1) + " price?",
                            "Item Price",
                            JOptionPane.QUESTION_MESSAGE)));

                    items[i].setProfitMargin();
                    JOptionPane.showMessageDialog(null, "Profit margin set to: "+items[i].getProfitMargin()*100+"%", "Profit Margin",  JOptionPane.INFORMATION_MESSAGE);
                    output.println(items[i].toString());
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(
                            null, "Please, check your input.",
                            "Input error",
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }

            output.close();
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(
                    null, "Please, check your input. It must be a whole number",
                    "Input error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    //endregion

    //region Utils
    // Display a string on the screen

    private void DisplayItems(String diplayString)
    {
        textArea.setText(diplayString);
    }

    // Close Operation
    private void Close()
    {
        this.welcomePage.setVisible(true);
        this.dispose();
    }
    //endregion

}
