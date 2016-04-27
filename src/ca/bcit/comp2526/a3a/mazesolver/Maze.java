package ca.bcit.comp2526.a3a.mazesolver;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Maze.
 *
 * @author BCIT
 * @version 2016
 */
public class Maze {
    private final MazeSection[][] mazeSections;
    private final Random random;
    private Scanner scan;

    /**
     * Constructor for objects of type Maze.
     * 
     * @param rows
     * @param columns
     */
    public Maze(int rows, int columns) {
        mazeSections = new MazeSection[rows][columns];
        random = new Random();
        scan = new Scanner(System.in);
    }

    /**
     * Initializes a new empty maze.
     */
    public void init() {
        for(int i=0;i < getRows() ;i++){
            for(int j=0; j < getColumns() ;j++){
                mazeSections[i][j] = new MazeSection(i, j, false);
            }
        }
        for(int i=0;i < getRows() ;i++){
            for(int j=0; j < getColumns() ;j++){
                mazeSections[i][j].init();
            }
        }
    }

    /**
     * Eliminates all walls from the maze and 'unvisits' each maze section,
     * effectively wiping it clean.
     */
    public void clear() {
        for(int i=0;i < getRows() ;i++){
            for(int j=0; j < getColumns() ;j++){
                mazeSections[i][j].setSolid(false);
                makeNavigable(mazeSections[i][j]);
            }
        }
    }
    
    
    

    /**
     * Makes the specified MazeSection solid.
     * 
     * @param section
     *            a maze
     */
    private void makeSolid(MazeSection section) {
        section.setSolid(true);
        section.setColour(Color.BLACK);
    }

    /**
     * Makes the specified MazeSection navigable.
     * 
     * @param section
     *            a maze
     */
    private void makeNavigable(MazeSection section) {
        section.unvisit();
        section.setColour(Color.WHITE);
    }

    /**
     * @return the number of rows
     */
    public int getRows() {
        return mazeSections.length;
    }

    /**
     * @return the number of columns
     */
    public int getColumns() {
        return mazeSections[0].length;
    }

    /**
     * Returns a reference to the MazeSection at the specified coordinates.
     * 
     * @param row
     * @param column
     * @return location in maze as a MazeSection
     */
    public MazeSection getMazeSectionAt(int row, int column) {
        return mazeSections[row][column];
    }

    /**
     * Generates a (terrible) random maze.
     */
    public void generateRandomMaze() {
        clear();
        
        
        ArrayList<MazeSection> midPoints = new ArrayList<MazeSection>();
        ArrayList<MazeSection> path = new ArrayList<MazeSection>();
        
        int chance = 20;
        
        
        addPoints(midPoints, chance);//mid points
        midPoints.add(getMazeSectionAt(24, 24));//exit
        
        
        MazeSection previous = getMazeSectionAt(0, 1);//entrance
        for(MazeSection section: midPoints){
            connectPoints(path, previous, section);
            previous = section;
        }
        
        
        for(int i=0;i < getRows() ;i++){
            for(int j=0; j < getColumns() ;j++){
                makeSolid(getMazeSectionAt(i, j));
            }
        }
        
        for(MazeSection section: path){
            section.setSolid(false);
            makeNavigable(section);
        }

    }
    
    
    
    private void addPoints(ArrayList<MazeSection> points, int chance){

        int lastRow = -2;
        
        for(int i=0;i < getRows() ;i++){
            for(int j=0; j < getColumns()-1 ;j++){
                if(i==0 && j==0){
                    j +=2;
                }
                if(random.nextInt(chance) == 1 && i > lastRow + 1){
                    chance = 20;
                    points.add(getMazeSectionAt(i, j));
                    lastRow = i;
                }
                else {
                    if(chance > 1)
                        chance--;
                }
            }
        }
    }
    
    
    private void connectPoints(ArrayList<MazeSection> mainPath, MazeSection start, MazeSection end){
        
        MazeSection current = start;
        
        boolean correctRow = false;
        boolean correctCol = false;
        
        int currentRow = current.getLocation().x;
        int currentCol = current.getLocation().y;
        int endRow     = end.getLocation().x;
        int endCol     = end.getLocation().y;
        
        mainPath.add(current);
        
        while(current.getLocation().y != end.getLocation().y 
                && current.getLocation().x != end.getLocation().x){

            if(currentRow == endRow){
                correctRow = true;
            }
            
            if(currentCol == endCol){
                correctCol = true;
            }
            
            while(!correctRow || !correctCol){
                int choose = random.nextInt(2);
                
                if(correctRow){
                    choose = 0;
                }
                if(correctCol){
                    choose = 1;
                }
                
                if(choose == 0) {
                    if(currentRow < endRow){
                        currentRow++;
                    }
                    if(currentRow > endRow){
                        currentRow--;
                    }
                }
                else {
                    if(currentCol < endCol){
                        currentCol++;
                    }
                    if(currentCol > endCol){
                        currentCol--;
                    }
                }
            }

            current = getMazeSectionAt(currentRow, currentCol);
            mainPath.add(current);
            
        }
    }

    
    
    
    

    /**
     * Generates a maze from the specified file. The file must be a .txt file
     * which contains MAZE_DIMENSION lines of MAZE_DIMENSION characters each.
     * There are two characters: * delineates a solid section, and anything else
     * delineates a navigable section. The entry to the maze must be at location
     * [0][1] and the (possibly multiple) exit(s) to the maze must be on the
     * right edge (rightmost column) of the maze [0...24][24]
     * 
     * @param file
     *            that contains the maze
     */
    public void generateMazeFromFile(File file) throws IOException {
        clear();
        scan = new Scanner(file);
        scan.useDelimiter("");
        char input;
        for(int i=0;i < getRows() ;i++){
            for(int j=0; j < getColumns() ;j++){
                input = scan.next().charAt(0);
                while(input == '\n'){
                    input = scan.next().charAt(0);
                }
                if(scan.hasNext() && input == '*'){
                    makeSolid(getMazeSectionAt(i, j));
                }
            }
        }
    }

    /**
     * Resets the maze by 'unvisiting' all visited maze sections
     * and resetting their colour to white.
     */
    public void reset() {
        MazeSection section;
        for(int i=0;i < getRows() ;i++){
            for(int j=0; j < getColumns() ;j++){
                section = getMazeSectionAt(i, j);
                if(section.hasBeenVisited() || section.getBackground().equals(Color.LIGHT_GRAY)){
                    makeNavigable(section);
                    section.revalidate();
                }
            }
        }
    }
}
