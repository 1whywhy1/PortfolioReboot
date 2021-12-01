package com.thisisivan.serialization;

import com.thisisivan.User;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserializer {
    //region Variables
    private User user;
    //endregion

    //region Constructors
    public Deserializer(){}
    //endregion

    //region DeserializeUser
    public User DeserializeUser() throws IOException, ClassNotFoundException {
        user = null;

        try
        {
            // Open FileChooser to select file
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir")+ System.getProperty("file.separator") + "\\Files");
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.CANCEL_OPTION) {
                throw new Error("Nothing Selected");
            }
            File f = chooser.getSelectedFile();
            String fileName = f.getAbsolutePath();

            // Reading the object from a file
            FileInputStream file = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            user = (User)in.readObject();

            // Close objects
            in.close();
            file.close();

            JOptionPane.showMessageDialog(null, "User has been deserialized. ", "InfoBox: Deserialization complete",  JOptionPane.INFORMATION_MESSAGE);

        }
        catch(IOException ex)
        {
            throw ex;
        }
        catch(Error er)
        {
            // Do nothing if cancel is pressed in FileChooser
        }
        catch(ClassNotFoundException ex)
        {

        }
        return user;
    }
    //endregion
}
