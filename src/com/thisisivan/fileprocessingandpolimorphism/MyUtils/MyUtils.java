
/*
 * static functions in MyUtils can be called used via 
 * the class name
 */
package com.thisisivan.fileprocessingandpolimorphism.MyUtils;
/**
 *
 * @author srao3
 */
public class MyUtils {
    public static String printLine()
    {
        String str = "";
        for(int i=0;i<30; i++)
            str +="=";
        str += "\n";
        return str;
    }    
    public static String printLine(char c)
    {
        String str = "";
        for(int i=0;i<30; i++)
            str += c;
        str += "\n";
        return str;
    }
    public static String printLine(char c, int x)
    {
        String str = "";
        for(int i=0;i<x; i++)
            str += c;
        str += "\n";
        return str;
    }
    public static String printLine(int x)
    {
        String str = "";
        for(int i=0;i<x; i++)
            str +=("=");
        str += "\n";
        return str;
    }
}
