package ca.bcit.comp2526.a3a.mazesolver;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;


import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * MazeSection.
 *
 * @author BCIT
 * @version 2016
 */
@SuppressWarnings("serial")
public class MazeSection extends JPanel {
    private final Point location;
    private boolean isSolid, visited;
    
    /**
     * Constructor for objects of type MazeSection.
     * 
     * @param maze
     * @param row
     * @param column
     * @param isSolid
     */
    public MazeSection(int row, int column, boolean isSolid) {
        location = new Point(row, column);
        this.isSolid = isSolid;
        unvisit();
    }

    /**
     * Initializes this maze section.
     */
    public void init() {
        setLayout(new GridLayout(1, 1));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setColour(Color.WHITE);
    }
    
    
    /**
     * @return true if this maze section is solid, else false
     */
    public boolean isSolid() {
        return isSolid;
    }

    /**
     * Sets whether this maze section is solid.
     * 
     * @param isSolid
     *            a boolean
     */
    public void setSolid(boolean isSolid) {
        this.isSolid = isSolid;
    }

    /**
     * Visits this maze section.
     */
    public void visit() {
        visited = true;
    }

    /**
     * "Unvisits" this maze section.
     */
    public void unvisit() {
        visited = false;
    }

    /**
     * @return true if this maze has been visited, else false
     */
    public boolean hasBeenVisited() {
        return visited;
    }

    /**
     * @return the location of this MazeSection as a Point
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Sets the colour of this maze section.
     * 
     * @param color
     */
    public void setColour(Color color) {
        setBackground(color);
    }
}
