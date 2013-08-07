
/*
 * File: NameSurfer.java
 * ---------------------
 * This file lets you graph the popularity of different baby names over the last century or so!
 */
 
import acm.program.*;
import java.awt.event.*;
import javax.swing.*;
 
public class NameSurfer extends Program implements NameSurferConstants {
 
    /* Method: init() */
    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */
    public void init() {
        //create "global" database
        database = new NameSurferDataBase(NAMES_DATA_FILE);
 
        /**
         *Create the name input field.
         */
        //create label for name input field
        JLabel nameInputLabel = new JLabel("Name: ");
        add(nameInputLabel, NORTH);
 
        //create name input field, complete with ability to start graphing when the use hits enter
        nameInputField.addActionListener(this);
        nameInputField.setActionCommand("Graph");
        add(nameInputField, NORTH);
 
 
        graph = new NameSurferGraph();
        add(graph);
 
        /**
         * Add the buttons.
         */
        //add the "Graph" button
        add(graphButton, NORTH);
        //add the "Clear" button
        add(clearButton, NORTH);
 
        addActionListeners();
    }
 
    /* Method: actionPerformed(e) */
    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Graph")){
            graph();
        }else{
            clear();
        }
    }
 
    /**If the entry exists in the database, the entry's data is graphed.*/
    private void graph(){
        name = (nameInputField.getText()).toLowerCase();
        NameSurferEntry nameData = database.findEntry(name);
         
        if(nameData == null){
            println("Sorry, that name doesn't exist!");
        }else{
            graph.addEntry(nameData);
        }
    }
 
    /**Clears the graph of all data lines and labels.*/
    private void clear(){
        graph.clear();
    }
 
     
    /*
     * Instance variable section
     */
    /**The name the user typed in.*/
    private String name;
 
    /**JObjects created to set up the NameSurfer interface.*/
    private JTextField nameInputField = new JTextField(NUMBER_COLUMNS);
    private JButton graphButton = new JButton("Graph");
    private JButton clearButton = new JButton("Clear");
 
    /**Creating instances of classes.*/
    private NameSurferDataBase database;
    private NameSurferGraph graph;
 
}
 