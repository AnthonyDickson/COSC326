package coroutine;

import java.awt.Point;
import java.util.ArrayList;

/**
 * PuzzleSquare
 *  
 * @author Anthony Dickson
 */
public class PuzzleSquare {
    ArrayList<Direction> validDirections = new ArrayList<>();
    
    public PuzzleSquare() {}

    public PuzzleSquare(ArrayList<Direction> validDirections) {
        this.validDirections = validDirections;
    }
}