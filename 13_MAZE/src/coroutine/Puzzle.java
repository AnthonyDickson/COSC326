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
    PuzzleSquare[][] board = new PuzzleSquare[3][3];
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
     * Copy constructor.
     * Note: <code>squares</code> is not cloned, changing <code>squares</code>
     * will change <code>squares</code> for both the original and the copy. 
     */
    public Puzzle(Puzzle other) {
        this.c1 = new Point(other.c1);
        this.c2 = new Point(other.c2);
        this.isC1Turn = other.isC1Turn;
        this.movesToSolve = other.movesToSolve;
        this.board = other.board;
    }

    /**
     * Get the position of the coin to move this turn.
     * 
     * @return the position of the coin to move this turn.
     */
    public Point getCurrentCoin() {
        return (isC1Turn) ? c1 : c2;
    }

    /**
     * Get the position of the coin that isn't moving this turn.
     * 
     * @return the position of the coin that isn't moving this turn.
     */
    public Point getOtherCoin() {
        return (isC1Turn) ? c2 : c1;
    }

    /** 
     * Gets all the directions a coin can move in.
     * 
     * @param from The position of the coin we want to move.
     * @return All of the directions a coin can move.
     */
    public ArrayList<Direction> getMoves(Point from) {
        ArrayList<Direction> moves = new ArrayList<>();
        Point other = new Point();

        if (from.equals(c1)) {
            other = c2;
        } else if (from.equals(c2)) {
            other = c1;
        }

        // Get moves for c1.
        for (Direction d : board[other.y][other.x].validDirections) {
            if (isValid(from, d)) moves.add(d);
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
    public boolean move(Point from, Direction dir) {
        if (!isValid(from, dir)) return false;
        
        Point p = new Point(from);
        p.translate(dir.asPoint.x, dir.asPoint.y);

        if (from.equals(c1)) {
            c1 = p;
        } else {
            c2 = p;
        }

        isC1Turn = !isC1Turn;

        return true;
    }

    /** 
     * Pass the current coin's turn.
     */
    public void pass() {
        isC1Turn = !isC1Turn;
    }

    /**
     * Checks if a move is valid.
     */
    public boolean isValid(Point from, Direction dir) {
        Point dest = new Point(from);
        dest.translate(dir.asPoint.x, dir.asPoint.y);
        // A coin may not share the same square.
        if (dest.equals(c1) || dest.equals(c2)) return false;
        // A coin may not move off the board.
        if ((dest.x < 0 || dest.x > 2) || (dest.y < 0 || dest.y > 2)) return false;
        
        return true;
    }

    /**
     * Checks if the puzzle has been solved.
     * 
     * @return true if either of the coins are on the 'goal' square, 
     * otherwise false.
     */
    public boolean isSolved() {
        return c1.equals(GOAL) || c2.equals(GOAL);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (c1.equals(new Point(col, row))) {
                    sb.append("C1 ");   // One Space.
                } else if (c2.equals(new Point(col, row))) {
                    sb.append("C2 ");   // One space.
                } else if (GOAL.equals(new Point(col, row))) {                    
                    sb.append("*  ");   // Two spaces.
                } else {
                    sb.append("   "); // Three spaces.
                }

                sb.append(String.format("%-17s", board[row][col].validDirections.toString()));
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}