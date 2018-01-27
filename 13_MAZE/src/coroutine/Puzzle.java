package coroutine;

import java.util.ArrayList;
import java.awt.Point;


/**
 * Puzzle
 *  
 * @author Anthony Dickson
 */
public abstract class Puzzle {
    /** The indicies to the two starting squares and the end (goal) square.  */
    static final Point START_ONE = new Point(0, 0);
    static final Point START_TWO = new Point(2, 2);
    static final Point GOAL = new Point(1, 1);

    /** How many moves it takes to solve the puzzle. */
    final int movesToSolve;
    /** A 3x3 board. */
    PuzzleSquare[][] squares = new PuzzleSquare[3][3];
    /** The indicies of where the two coins are. */
    Point c1, c2;
    /** Indicates which coin should be moved. */
    boolean isC1Turn;

    public Puzzle() {
        this(Integer.MAX_VALUE);
    }

    public Puzzle(int movesToSolve) {
        this.c1 = new Point(START_ONE);
        this.c2 = new Point(START_TWO);
        this.isC1Turn = true;
        this.movesToSolve = movesToSolve;
    }

    /** 
     * Gets all the directions a coin can move in.
     * 
     * @param from The position of the coin we want to move.
     * @return All of the directions a coin can move.
     */
    public ArrayList<Direction> getMoves(Point from) {
        ArrayList<Direction> moves = new ArrayList<>();

        if (from.equals(c1)) {
            from = c2;
        } else if (from.equals(c2)) {
            from = c1;
        }

        // Get moves for c1.
        for (Direction d : squares[from.y][from.x].validDirections) {
            if (isValid(d.asPoint)) moves.add(d);
        }

        return moves;
    }

    /**
     * Move a coin from one square to another.
     * 
     * @param from The position of the coin to move.
     * @param dir The direction to move the coin in.
     * @return Whether or not the coin was moved successfully.
     */
    public boolean move(Point from, Point dir) {
        Point p = new Point(from);
        p.translate(dir.x, dir.y);

        if (!isValid(p)) return false;

        if (from.equals(c1)) {
            c1 = p;
        } else {
            c2 = p;
        }

        return true;
    }

    public boolean isValid(Point p) {
        // A coin may not share the same square.
        if (p.equals(c1) || p.equals(c2)) return false;
        // A coin may not move off the board.
        if ((p.x < 0 || p.x > 2) || (p.y < 0 || p.y > 2)) return false;

        return true;
    }
}