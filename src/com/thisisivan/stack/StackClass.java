package com.thisisivan.stack;

import javax.swing.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StackClass {

    //region Variables
    static Stack st;
    static int itemsQuantity;

    private static List<IStackPrinter> printers = new ArrayList<IStackPrinter>();
    //endregion

    //region PushPop Methods
    static void showpush(Stack<Integer> st, int value) {
        st.push(value);
    }

    static int showpop(Stack<Integer> st) {
        return st.pop();
    }
    //endregion

    //region PlayDemo
    public static void PlayDemo(){
        PushAndDisplay();
        PopAndDisplay();
    }
    //endregion

    // Take input from user and push it into stack
    //region PushAndDisplay
    public static void PushAndDisplay()
    {
        itemsQuantity = 0;
        try {
            itemsQuantity = Integer.parseInt(JOptionPane.showInputDialog(null, "How many items would you like to push to stack?", "Stack Item number", JOptionPane.QUESTION_MESSAGE));

            st = new Stack();

            // Declaring Integer to be able to set null into it.
            Integer item = null;

            for(int i = 0; i < itemsQuantity; ++i)
            {
                while(true) {
                    try {
                        item = Integer.parseInt(JOptionPane.showInputDialog
                                (null, "What is Integer No " + (i + 1) + " ?",
                                        "Item Name", JOptionPane.QUESTION_MESSAGE));
                    }
                    catch (Exception e){}

                    if(!item.equals(null))
                    {
                        break;
                    }
                }
                showpush(st, item);
                SendPrintStackString("Stack after inserting item No " + (i + 1) + ": ");
            }
        }
        catch (Exception e)
        {

        }
    }
    //endregion

    // Takes top item off the stack and sends message to print
    //region PopAndDisplay
    private static void PopAndDisplay()
    {
        try {
            for (int i = 0; i < itemsQuantity; ++i) {
                SendPrintStackString("After popping item with value of  - " + showpop(st));
            }
        }
        catch (Exception e)
        {

        }
    }
    //endregion

    // Sends a message to observer to print Stack string
    //region SendMessage - PrintStack
    private static void SendPrintStackString(String description)
    {
        for(IStackPrinter printer : printers)
        {
            printer.printStack(description + st.toString() + "\n");
        }
    }
    //endregion

    //region Add Event Listeners
    public static void AddListener(IStackPrinter listener)
    {
        printers.add(listener);
    }
    //endregion
}
