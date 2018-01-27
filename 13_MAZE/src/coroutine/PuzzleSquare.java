package coroutine;

import java.awt.Point;
import java.util.Set;
import java.util.HashSet;

/**
 * PuzzleSquare
 *  
 * @author Anthony Dickson
 */
public class PuzzleSquare {
    Set<Direction> validDirections = new HashSet<>();
    final boolean isGoal;
    
    public PuzzleSquare() {
        this(false);
    }

    public PuzzleSquare(boolean isGoal) {
        this.isGoal = isGoal;
    }
}