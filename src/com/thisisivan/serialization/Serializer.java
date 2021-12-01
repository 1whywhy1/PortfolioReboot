package com.thisisivan.serialization;

import com.thisisivan.User;
import com.thisisivan.UserType;
import com.thisisivan.fileprocessingandpolimorphism.MyUtils.WriteAs;
import com.thisisivan.fileprocessingandpolimorphism.MyUtils.WriteToFile;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Serializer {

    private User user;

    public Serializer(){};

    // Serializes User object and saves in. Throws
    public void SerializeThisUser(String username, String password, String firstName, String lastName, UserType userType, String email){

        try {
            user = new User(username, password, firstName, lastName, userType, email);
            String exactFilePath = WriteToFile.GetPathToFile(WriteAs.ser);

            if (exactFilePath != null) {
                try {
                    //Saving of object in a file
                    FileOutputStream file = new FileOutputStream(exactFilePath);
                    ObjectOutputStream out = new ObjectOutputStream(file);

                    // Method for serialization of object
                    out.writeObject(user);

                    out.close();
                    file.close();

                    JOptionPane.showMessageDialog(null, "User has been serialized", "InfoBox: Serialization complete", JOptionPane.INFORMATION_MESSAGE);

                } catch (IOException ex) {
                    System.out.println("IOException is caught");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Serialization failed", JOptionPane.INFORMATION_MESSAGE);

        }

    }
}
