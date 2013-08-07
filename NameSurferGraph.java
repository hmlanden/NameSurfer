
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */
 
import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
 
public class NameSurferGraph extends GCanvas
implements NameSurferConstants, ComponentListener {
 
    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        addComponentListener(this);
        createGraph();
    }
 
    /**Set up the basic lines of the graph.*/
    private void createGraph(){
        //Add vertical lines.
        for(int i = 0; i < NDECADES; i++){
            GLine line = createLine(i);
            add(line);
        }
        //Add labels for the decades
        for(int i = 0; i < NDECADES; i ++){
            GLabel decadeLabel = createDecadeLabel(i);
            add(decadeLabel);
        }
        //Add the bottom margin line.
        GLine bottomLine = createBottomLine();
        add(bottomLine);
         
        //Add the top margin line.
        GLine topLine = createTopLine();
        add(topLine);
    }
 
    /**Create a vertical decade line for the decade based on what decade you're on in the line loop.*/
    private GLine createLine(int i){
        int x = GRAPH_SIDE_MARGIN + (getWidth()/12 * i);
        int y = getHeight();
        int x1= x;
        int y1= 0;
         
        GLine line = new GLine(x, y, x1, y1);
        return line;
    }
 
    /**Create a label for the decade based on what decade you're on in the label loop.*/
    private GLabel createDecadeLabel(int i){
        int x = GRAPH_SIDE_MARGIN + (getWidth()/12 * i);
        int y = getHeight();
 
        //figure out what decade we're at and create a number for it.
        int decade = START_DECADE + (10 * i);
 
        //Parse the integer and create the label.
        GLabel label = new GLabel(Integer.toString(decade),x, y);
        label.setFont("CALIBRI-BOLD-24");
        return label;
    }
 
    /**Create the top margin.*/
    private GLine createTopLine(){
        int x = GRAPH_SIDE_MARGIN;
        int y = (getHeight() - GRAPH_MARGIN_SIZE);
        int x1= getWidth();
        int y1= y;
         
        //Create the margin line.
        GLine line = new GLine(x, y, x1, y1);
        return line;
    }
 
    /**Create the bottom margin line.*/
    private GLine createBottomLine(){
        int x = GRAPH_SIDE_MARGIN;
        int y = GRAPH_MARGIN_SIZE;
        int x1= getWidth();
        int y1= y;
         
        //Create the margin line.
        GLine line = new GLine(x, y, x1, y1);
        return line;
    }
 
    /**
     * Clears the list of name surfer entries stored inside this class,
     * then calls update.
     */
    public void clear() {
        entries.clear();
        update();
    }
 
 
    /* Method: addEntry(entry) */
    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        entries.add(entry);
        update();
    }
 
 
    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        removeAll();
        createGraph();
        addGraph();
    }
 
    /**Create line graph for all the name entries in the name entry ArrayList.*/
    private void addGraph(){
        for(int i = 0; i < entries.size(); i++){
            //Get the current entry.
            NameSurferEntry currentEntry = entries.get(i);
             
            //Create the lines and labels as appropriate.
            for(int j = 0; j < (NDECADES - 1); j++){
                addLineAndLabel(i, j, currentEntry);
            }
        }
    }
 
    /**Create the lines and labels for each entry.*/
    private void addLineAndLabel(int i, int j, NameSurferEntry currentEntry){
        //Get the name.
        String currentName = currentEntry.getName();
         
        //Get the ranks.
        int currentRank = currentEntry.getRank(j);
        int nextRank = currentEntry.getRank(j + 1);
         
        //Bottom of the graph: y axis.
        int rankZero = getHeight()-GRAPH_MARGIN_SIZE;
 
        //Calculation of the x and y coordinates.
        int x = GRAPH_SIDE_MARGIN + ((getWidth()/12) * j);
        int x1= GRAPH_SIDE_MARGIN + ((getWidth()/12) * (j + 1));
        int y = (rankZero - ((MAX_RANK - currentRank) * ((getHeight()- (2 * GRAPH_MARGIN_SIZE) ))/MAX_RANK));
        int y1 = (rankZero - ((MAX_RANK - nextRank) * ((getHeight()- (2 * GRAPH_MARGIN_SIZE)))/MAX_RANK));
 
         
        //Create a line between the current rank and the next rank.
        GLine line = createLine(x, y, x1, y1, rankZero, currentRank, nextRank);
        line.setColor(setLineColor(i));
        line.setVisible(true);
        add(line);
 
        //Create a label for the current name.
        GLabel label = createLabel(currentName, x, y, currentRank, rankZero);
        add(label);
 
        //Special case: add the last label at the end of the last run-through, because at the end, only a label for
        //the next rank gets added.
        if(j == 10){
            GLabel lastLabel = createLabel(currentName, x1, y1, nextRank, rankZero);
            add(lastLabel);
        }
    }
 
    /**Set the color for the line depending on what entry it is in the ArrayList. Loops every four colors.*/
    private Color setLineColor(int i){
        listIndex = i % 4;
         
        switch(listIndex){
            case 0: return Color.GREEN;
            case 1: return Color.RED;
            case 2: return Color.CYAN;
            case 3: return Color.PINK;
        }
         
        return null;
    }
 
    /**Very stylistically bad method for creating lines in a variety of situations.*/
    private GLine createLine(int x, int y, int x1, int y1, int rankZero, int currentRank, int nextRank){
        //If neither rank values are 0, add a line as normal
        if(currentRank != 0 && nextRank != 0){
            GLine line = new GLine(x, y, x1, y1);
            return line;
             
        }else if(currentRank == 0 && nextRank != 0){
            //Add line in the case where the current rank is 0, but the next isn't.
            GLine line = new GLine(x, rankZero, x1, y1);
            return line;
             
        }else if(currentRank != 0 && nextRank == 0){
            //Add line in the case where the next rank is 0
            GLine line = new GLine(x, y, x1, rankZero);
            return line;    
        }
        //Add line where both the current rank and the next rank are 0
        GLine line = new GLine(x, rankZero, x1, rankZero);
        return line;
    }
 
    /**Create a label based on the current rank.*/
    private GLabel createLabel(String currentName, int x, int y, int currentRank, int rankZero){
        if(currentRank != 0){
            String nameLabel = currentName + ": " + currentRank;
            GLabel label = new GLabel(nameLabel, x, y);
            return label;
        }
        String nameLabel = currentName + " *";
        GLabel label = new GLabel(nameLabel, x, y);
        label.setLocation(x, rankZero);
        return label;
    }
 
 
    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) { }
    public void componentMoved(ComponentEvent e) { }
    public void componentResized(ComponentEvent e) { update(); }
    public void componentShown(ComponentEvent e) { }
 
    /*Instance variables*/
    private ArrayList<NameSurferEntry> entries = new ArrayList<NameSurferEntry>();
    private int listIndex;
 
}