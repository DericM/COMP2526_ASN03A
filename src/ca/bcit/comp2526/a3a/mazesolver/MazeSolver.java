package ca.bcit.comp2526.a3a.mazesolver;

import java.util.ArrayList;

/**
 * MazeSolver.
 *
 * @author BCIT
 * @version 2016
 */
public class MazeSolver {

    private Maze maze;
    private ArrayList<ArrayList<MazeSection>> mazeSolutions;

    /**
     * Constructor for objects of type MazeSolver.
     * 
     * @param maze
     * @param frame
     */
    public MazeSolver(Maze maze) {
        this.maze = maze;
        mazeSolutions = new ArrayList<ArrayList<MazeSection>>();
    }

    /**
     * Returns the maze as a Maze.
     * 
     * @return maze as a Maze
     */
    public Maze getMaze() {
        return maze;
    }

    /**
     * Sets the maze.
     * 
     * @param maze
     *            the maze to set
     */
    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Clears the list of solutions.
     */
    public void clear() {
        mazeSolutions = new ArrayList<ArrayList<MazeSection>>();
    }

    /**
     * Solves the maze and returns a List of paths composed of MazeSections.
     * 
     * @param solutions
     *            as an int
     * @return mazeSolutions as an ArrayList<ArrayList<MazeSection>>
     * @throws MazeEntryPointException if the maze does not have an entry point at [0][1]
     */
    public ArrayList<ArrayList<MazeSection>> solveMaze() throws MazeEntryPointException {
        MazeSection entryPoint = maze.getMazeSectionAt(0, 1);
        ArrayList<MazeSection> firstPath = new ArrayList<MazeSection>();
        
        clear();
        
        if(entryPoint.isSolid()){
            throw new MazeEntryPointException("NO ENTRY POINT!");
        }
        
        firstPath.add(entryPoint);
        generatePaths(maze, 0, 1, firstPath);
        
        return mazeSolutions;
    }

    /**
     * Generates paths through the maze recursively and adds all solutions to
     * the collection of solutions.
     * 
     * @param maze
     * @param row
     * @param column
     * @param path
     */
    public void generatePaths(Maze maze, int row, int column, ArrayList<MazeSection> path) {
        
        
        if(!inBounds(row, column)){
            return;
        }
        
        MazeSection section = maze.getMazeSectionAt(row, column);

        if(section.isSolid() || section.hasBeenVisited()){
            return;
        }
        
        for(MazeSection approach:path){
            approach.visit();
        }
        
        path.add(section);
        section.visit();
        
        if(column == maze.getColumns() - 1){
            mazeSolutions.add(path);
            maze.reset();
        }
        else {
            generatePaths(maze, row - 1, column, new ArrayList<MazeSection>(path));
            generatePaths(maze, row + 1, column, new ArrayList<MazeSection>(path));
            generatePaths(maze, row, column - 1, new ArrayList<MazeSection>(path));
            generatePaths(maze, row, column + 1, new ArrayList<MazeSection>(path));
            section.unvisit();
        }
        
    }
    
    
    /*
     * Checks if the row and column are in bounds
     */
    private boolean inBounds(int row, int column){
        if      (row >= 0
                && column >= 0
                && row < maze.getRows()
                && column < maze.getColumns()){
            return true;
        }
        return false;
    }

    
    /**
     * Returns the index of the shortest path in the collection of paths, or
     * -1 if there is no shortest path, i.e., the maze has no solutions.
     * 
     * @param paths
     * @return index as an int
     */
    public int findShortestPath() {
        int shortest = 0, size = mazeSolutions.size();
        if(size < 1){
            return -1;
        }
        for(int i = 0; i < size; i++){
            if(mazeSolutions.get(i).size() < mazeSolutions.get(shortest).size()){
                shortest = i;
            }
        }
        return shortest;
    }
}
