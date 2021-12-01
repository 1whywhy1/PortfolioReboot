package com.thisisivan.fileprocessingandpolimorphism.MyUtils;

import com.thisisivan.fileprocessingandpolimorphism.MyUtils.MyUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/*
 * Process sample.txt using Scannner
 * and Read from file to a JTextArea
 */

/**
 * @author srao3
 */
public class ReadTextFile {
    public String ReadReportFile(){
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir")+ System.getProperty("file.separator") + "\\Files");
        String fileName;
        String currentRecord = "";
        String output = "";
        String [] fields;

        try
        {
            output +=String.format("%30s\n","Report");
            output += MyUtils.printLine('x',140);
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();
            fileName = f.getAbsolutePath();

            try
            {
//                FileReader reader = new FileReader(filename);
//                BufferedReader br = new BufferedReader(reader);

                Scanner inFile =  new Scanner(new File(fileName));
                currentRecord = inFile.nextLine();
                fields = currentRecord.split(";");

                for(String item:fields)
                    output +=String.format("%-20s",item);
                output +=("\n");
                output += MyUtils.printLine(100);

                while(inFile.hasNext())
                {
                    currentRecord = inFile.nextLine();
                    fields = currentRecord.split(";");
                    for(String item:fields)
                        output +=String.format("%-20s",item);
                    output +=("\n");
                }


            }
            catch (FileNotFoundException fe)
            {
                JOptionPane.showMessageDialog(null, "File not found!", "InfoBox: Failed to find the file", JOptionPane.INFORMATION_MESSAGE);

            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, e, "InfoBox: File Read", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (NullPointerException npe)
        {
            return " ";
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "InfoBox", JOptionPane.INFORMATION_MESSAGE);
        }
        return output;
    }

    public String ReadFile(){
        JFileChooser chooser = new JFileChooser(System.getProperty("user.dir")+ System.getProperty("file.separator") + "\\Files");
        String fileName;
        String currentRecord = "";
        String output = "";
        String [] fields;

        try
        {
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();
            fileName = f.getAbsolutePath();

            try
            {
//                FileReader reader = new FileReader(filename);
//                BufferedReader br = new BufferedReader(reader);
//                textArea.read(br,null);

                Scanner inFile =  new Scanner(new File(fileName));
                //currentRecord = inFile.nextLine();

                while(inFile.hasNext())
                {
                    output += inFile.nextLine() + "\n";
                }
                return output;


            }
            catch (FileNotFoundException fe)
            {
                JOptionPane.showMessageDialog(null, "File not found!", "InfoBox: Failed to find the file", JOptionPane.INFORMATION_MESSAGE);

            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, e, "InfoBox: File Read", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (NullPointerException npe)
        {
            return " ";
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, e, "InfoBox", JOptionPane.INFORMATION_MESSAGE);
        }
        return output;
    }







}
