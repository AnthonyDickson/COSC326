package coroutine;

/**
 * Puzzle1 from http://cs.williams.edu/~bailey/coRoutinePuzzle.pdf (Top left).
 *  
 * @author Anthony Dickson
 */
public class Puzzle1 extends Puzzle {
    public Puzzle1() {
        super(16);

        this.board[0][0] = new PuzzleSquare();
        this.board[0][0].validDirections.add(Direction.N);
        this.board[0][0].validDirections.add(Direction.S);

        this.board[0][1] = new PuzzleSquare();
        this.board[0][1].validDirections.add(Direction.S);
        this.board[0][1].validDirections.add(Direction.SE);
        
        this.board[0][2] = new PuzzleSquare();
        this.board[0][2].validDirections.add(Direction.N);
        this.board[0][2].validDirections.add(Direction.S);
        this.board[0][2].validDirections.add(Direction.SW);

        this.board[1][0] = new PuzzleSquare();
        this.board[1][0].validDirections.add(Direction.NE);
        this.board[1][0].validDirections.add(Direction.E);

        this.board[1][1] = new PuzzleSquare(true);

        this.board[1][2] = new PuzzleSquare();
        this.board[1][2].validDirections.add(Direction.N);
        this.board[1][2].validDirections.add(Direction.SW);

        this.board[2][0] = new PuzzleSquare();
        this.board[2][0].validDirections.add(Direction.W);
        this.board[2][0].validDirections.add(Direction.SE);

        this.board[2][1] = new PuzzleSquare();
        this.board[2][1].validDirections.add(Direction.N);
        this.board[2][1].validDirections.add(Direction.NW);
        this.board[2][1].validDirections.add(Direction.SE);

        this.board[2][2] = new PuzzleSquare();
        this.board[2][2].validDirections.add(Direction.W);
        this.board[2][2].validDirections.add(Direction.SW);
        this.board[2][2].validDirections.add(Direction.S);
    }

    public static void main(String[] args) {
        System.out.println(new Puzzle1());
    }
}