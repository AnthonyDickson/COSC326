package coroutine;

/**
 * Puzzle2 from http://cs.williams.edu/~bailey/coRoutinePuzzle.pdf (Top Right).
 *  
 * @author Anthony Dickson
 */
public class Puzzle2 extends Puzzle {
    public Puzzle2() {
        super(14);

        this.squares[0][0] = new PuzzleSquare();
        this.squares[0][0].validDirections.add(Direction.N);
        this.squares[0][0].validDirections.add(Direction.S);
        this.squares[0][0].validDirections.add(Direction.SW);

        this.squares[0][1] = new PuzzleSquare();
        this.squares[0][1].validDirections.add(Direction.NE);
        this.squares[0][1].validDirections.add(Direction.E);
        this.squares[0][1].validDirections.add(Direction.S);
        this.squares[0][1].validDirections.add(Direction.SW);
        
        this.squares[0][2] = new PuzzleSquare();
        this.squares[0][2].validDirections.add(Direction.N);
        this.squares[0][2].validDirections.add(Direction.NE);
        this.squares[0][2].validDirections.add(Direction.S);
        this.squares[0][2].validDirections.add(Direction.SW);

        this.squares[1][0] = new PuzzleSquare();
        this.squares[1][0].validDirections.add(Direction.NW);
        this.squares[1][0].validDirections.add(Direction.NE);
        this.squares[1][0].validDirections.add(Direction.S);
        this.squares[1][0].validDirections.add(Direction.SW);

        this.squares[1][1] = new PuzzleSquare(true);

        this.squares[1][2] = new PuzzleSquare();
        this.squares[1][2].validDirections.add(Direction.W);
        this.squares[1][2].validDirections.add(Direction.NE);
        this.squares[1][2].validDirections.add(Direction.SE);

        this.squares[2][0] = new PuzzleSquare();
        this.squares[2][0].validDirections.add(Direction.N);
        this.squares[2][0].validDirections.add(Direction.NW);
        this.squares[2][0].validDirections.add(Direction.W);

        this.squares[2][1] = new PuzzleSquare();
        this.squares[2][1].validDirections.add(Direction.NW);
        this.squares[2][1].validDirections.add(Direction.NE);
        this.squares[2][1].validDirections.add(Direction.SW);

        this.squares[2][2] = new PuzzleSquare();
        this.squares[2][2].validDirections.add(Direction.N);
        this.squares[2][2].validDirections.add(Direction.NW);
        this.squares[2][2].validDirections.add(Direction.E);
    }

    public static void main(String[] args) {
        System.out.println(new Puzzle2());
    }
}