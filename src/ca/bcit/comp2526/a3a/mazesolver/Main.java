package ca.bcit.comp2526.a3a.mazesolver;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * Main.
 * 
 * @author BCIT
 * @version 1.0
 */
public final class Main {

    public static final int MAZE_DIMENSION = 25;
    private static final Toolkit TOOLKIT;

    static {
        TOOLKIT = Toolkit.getDefaultToolkit();
    }

    private Main() {
    }

    /**
     * Drives the program.
     * 
     * @param argv
     */
    public static void main(final String[] argv) {
        final GameFrame frame;
        final Maze maze;
        final MazeSolver mazeSolver;

        maze = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
        maze.init();
        mazeSolver = new MazeSolver(maze);
        frame = new GameFrame(maze, mazeSolver);
        position(frame);
        frame.init();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Positions the frame.
     * 
     * @param frame
     */
    private static void position(final GameFrame frame) {
        final Dimension size;
        size = calculateScreenArea(0.80f, 0.80f);
        frame.setSize(size);
        frame.setLocation(centreOnScreen(size));
    }

    /**
     * Finds and returns the centre of the screen.
     * 
     * @param size
     * @return centre as a Point
     */
    public static Point centreOnScreen(final Dimension size) {
        final Dimension screenSize;
        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }
        screenSize = TOOLKIT.getScreenSize();
        return (new Point((screenSize.width - size.width) / 2, (screenSize.height - size.height) / 2));
    }

    /**
     * Calculates the screen area.
     * 
     * @param widthPercent
     * @param heightPercent
     * @return area as a Dimension
     */
    public static Dimension calculateScreenArea(final float widthPercent, final float heightPercent) {
        final Dimension screenSize;
        final Dimension area;
        final int width;
        final int height;
        final int size;

        if ((widthPercent <= 0.0f) || (widthPercent > 100.0f)) {
            throw new IllegalArgumentException("widthPercent cannot be " + "<= 0 or > 100 - got: " + widthPercent);
        }

        if ((heightPercent <= 0.0f) || (heightPercent > 100.0f)) {
            throw new IllegalArgumentException("heightPercent cannot be " + "<= 0 or > 100 - got: " + heightPercent);
        }

        screenSize = TOOLKIT.getScreenSize();
        width = (int) (screenSize.width * widthPercent);
        height = (int) (screenSize.height * heightPercent);
        size = Math.min(width, height);
        area = new Dimension(size, size);

        return (area);
    }
}
