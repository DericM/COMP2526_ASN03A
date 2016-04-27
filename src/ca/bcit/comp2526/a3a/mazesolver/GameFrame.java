package ca.bcit.comp2526.a3a.mazesolver;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * GameFrame.
 *
 * @author BCIT
 * @version 2016
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame implements ActionListener {

    public static final int DELAY = 25;

    private final Maze maze;
    private final MazeSolver mazeSolver;
    private JMenuBar menuBar;
    private JMenu fileMenu, mazeMenu;
    private JMenuItem menuItemOpenFile, menuItemGenerateRandom, menuItemSolveMaze;

    /**
     * Constructor for objects of type GameFrame.
     * 
     * @param maze
     * @param mazeSolver
     */
    public GameFrame(final Maze maze, final MazeSolver mazeSolver) {
        this.maze = maze;
        this.mazeSolver = mazeSolver;
    }

    /**
     * Initializes this GameFrame. Sets the title, constructs and sets the menu
     * bar, constructs a new empty maze.
     */
    public void init() {
        setTitle("Assignment 3a");
        setJMenuBar(constructMenuBar());
        setLayout(new GridLayout(this.maze.getRows(), this.maze.getColumns()));
        for (int i = 0; i < this.maze.getRows(); i++) {
            for (int j = 0; j < this.maze.getColumns(); j++) {
                add(this.maze.getMazeSectionAt(i, j));
            }
        }
    }

    /**
     * Constructs and returns the JMenu Bar.
     * 
     * @return menuBar a JMenuBar
     */
    private JMenuBar constructMenuBar() {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        menuItemOpenFile = new JMenuItem("Open file...");
        menuItemOpenFile.addActionListener(this);
        fileMenu.add(menuItemOpenFile);
        menuItemGenerateRandom = new JMenuItem("Generate random maze");
        menuItemGenerateRandom.addActionListener(this);
        fileMenu.add(menuItemGenerateRandom);
        menuBar.add(fileMenu);

        mazeMenu = new JMenu("Maze");
        menuItemSolveMaze = new JMenuItem("Solve Maze");
        menuItemSolveMaze.addActionListener(this);
        mazeMenu.add(menuItemSolveMaze);
        menuBar.add(mazeMenu);
        return menuBar;
    }

    /**
     * Responds to the user interface.
     * 
     * @param e
     *            an ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(menuItemGenerateRandom)) {
            generateRandomMaze();
        } else if (e.getSource().equals(menuItemOpenFile)) {
            generateMazeFromFile();
        } else if (e.getSource().equals(menuItemSolveMaze)) {
            generateMazeSolutions();
        }
        repaint();
    }

    /**
     * Generates a (terrible) random maze.
     */
    public void generateRandomMaze() {
        maze.generateRandomMaze();
    }

    /**
     * Generates a maze based on a maze file selected by the user using a
     * file chooser dialog.
     */
    public void generateMazeFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                maze.clear();
                maze.generateMazeFromFile(file);
                mazeSolver.setMaze(maze);
            } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(this, "That file cannot be opened.");
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(this, "That file is not a correctly-formatted maze.");
                maze.clear();
            } finally {
                repaint();
            }
        }
    }

    /**
     * Generates solutions to the maze.
     */
    public void generateMazeSolutions() {
        try {
            maze.reset();
            repaint();
            ArrayList<ArrayList<MazeSection>> solutions = mazeSolver.solveMaze();
            displayMazeSolutions(solutions);
        } catch (MazeEntryPointException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    /**
     * Displays the maze solutions. All solutions are displayed in grey. The
     * shortest solution is displayed in dark grey.
     * 
     * @param solutions
     */
    public void displayMazeSolutions(final ArrayList<ArrayList<MazeSection>> solutions) {
        int numberOfSolutions = solutions.size();
        JOptionPane.showMessageDialog(this,
                "This maze has " + numberOfSolutions + (numberOfSolutions != 1 ? " solutions" : " solution"));

        if (numberOfSolutions > 0) {
            int indexOfShortestSolution = mazeSolver.findShortestPath();
            Timer myTimer = new Timer(DELAY, new ActionListener() {
                int solution = 0;
                int step = 0;
                int shortestSolutionStep = 0;

                public void actionPerformed(ActionEvent e) {
                    if (solution < numberOfSolutions) {
                        ArrayList<MazeSection> aSolution = solutions.get(solution);
                        if (step < aSolution.size()) {
                            aSolution.get(step++).setColour(Color.LIGHT_GRAY);
                        } else {
                            maze.reset();
                            step = 0;
                            solution++;
                        }
                    } else {
                        ArrayList<MazeSection> bestSolution = solutions.get(indexOfShortestSolution);
                        if (shortestSolutionStep < bestSolution.size()) {
                            bestSolution.get(shortestSolutionStep++).setColour(Color.GRAY);
                        } else {
                            ((Timer) e.getSource()).stop();
                        }
                    }
                    repaint();
                }
            });
            myTimer.start();
        }
    }
}
