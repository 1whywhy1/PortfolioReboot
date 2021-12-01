package com.thisisivan;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
    private static Logger logger;

    //region Fetch, Setup and Return Logger
    public static Logger SetupLogger(String loggerName, String loggerPath){
        // Create FileHandler, fetch a logger with name
        FileHandler fh;
        Logger logger = Logger.getLogger(loggerName);
        try{
            // Set file handler to the path in Out folder to create logger. True to append the file

            // The following line finds a path on Windows, but it contains extra / before hard disk name. This might not work if it is Linux or Mac. Required further testing
            //fh = new FileHandler((User.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "MyLogger.log").substring(1),true);

            // This finds the path to the project files and then append to set the path to Logs folder
            fh = new FileHandler(loggerPath, true);

           // System.getProperty("user.dir") + System.getProperty("file.separator") + "\\src\\com\\thisisivan\\Logs\\dblogfile + loggerName +".log"
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();

            fh.setFormatter(formatter);
            return logger;

        } catch (Exception e)
        {
            logger.log(Level.WARNING,"Exception ::", e);
        }
        return null;
    }
    //endregion
}
