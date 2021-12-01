package com.thisisivan.fileprocessingandpolimorphism.MyUtils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class WriteToFile
{

    // Writes to a file from File choose and returns the path to file.
    public static String WriteToAs(String textToSave, WriteAs ext)
    {
        try
        {
            String fileChooserPath = GetPathToFile(ext);
                try (FileWriter fw = new FileWriter(fileChooserPath)) {
                    fw.write(textToSave);
                }
                catch (Exception e){
                    JOptionPane.showMessageDialog(null,e);
                }
//            FileWriter writer = new FileWriter("C:\\Users\\karya\\Documents\\TAFEJavaFiles\\WriteToFile.txt");
//            BufferedWriter bw = new BufferedWriter(writer);
//            textArea.write(bw);
//            bw.close();
            return fileChooserPath;
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e);

        }
        return null;
    }

    public static String GetPathToFile(WriteAs ext)
    {
        try
        {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir")+ System.getProperty("file.separator") + "\\Files");
            int retrieval = chooser.showSaveDialog(null);
            if(retrieval==JFileChooser.APPROVE_OPTION) {
                String fileChooserPath = chooser.getSelectedFile() + "." + ext.name();
                return fileChooserPath;
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e);

        }
        return null;
    }

    public static void WriteTo(JTextArea textArea, String path)
    {
        try
        {

            FileWriter writer = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(writer);
            textArea.write(bw);
            bw.close();
            //textArea.setText("");
            textArea.requestFocus();

        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }

    public static void WriteTo(JEditorPane textArea, String path)
    {
        try
        {

            FileWriter writer = new FileWriter(path);
            BufferedWriter bw = new BufferedWriter(writer);
            textArea.write(bw);
            bw.close();
            //textArea.setText("");
            textArea.requestFocus();

        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }
    }
}
