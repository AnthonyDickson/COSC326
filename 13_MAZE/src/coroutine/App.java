package coroutine;

/**
 * App
 * 
 * @author Anthony Dickson
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Solving puzzle 1...");
        Solver s1 = new Solver(new Puzzle1());
        s1.solve();
        s1.printSolution();
        
        System.out.println("\nSolving puzzle 2...");
        Solver s2 = new Solver(new Puzzle2());
        s2.solve();
        s2.printSolution();
        
        System.out.println("\nSolving puzzle 3...");
        Solver s3 = new Solver(new Puzzle3());
        s3.solve();
        s3.printSolution();
        
        System.out.println("\nSolving puzzle 4...");
        Solver s4 = new Solver(new Puzzle4());
        s4.solve();
        s4.printSolution();
    }    
}