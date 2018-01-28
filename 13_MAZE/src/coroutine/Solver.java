package coroutine;

import java.util.*;
import java.awt.Point;

/**
 * Solves coroutine puzzles.
 * 
 * @author Anthony Dickson
 */
public class Solver {
    /** The puzzle to solve. */
    Puzzle p;
    /** The graph containing all the possible states of the bard. */
    List<State> stateGraph = new ArrayList<>();
    /** The edges describing how states are linked. */
    Map<State, ArrayList<Edge>> adjacencyList = new HashMap<>();
    /** Whether or not this solver has solved the given puzzle. */
    boolean solved;
    /** The edges traversed to get to a solution. */
    ArrayDeque<Edge> solutionPath = new ArrayDeque<>();
    /** The name of which coin was moved. */
    Map<Edge, String> solutionPathNames = new HashMap<>();

    /**
     * @param p The coroutine puzzle to solve.
     */
    public Solver(Puzzle p) {
        this.p = p;
        this.solved = false;
        
        initGraph();
        addEdges();
    }

    /**
     * Attempts to solve the puzzle.
     * If a solution was found, it will be saved in <code>solutionPath</code>.
     * 
     * @return true if the puzzle was solved successfully, otherwise false.
     */
    public boolean solve() {
        Set<State> visited = new HashSet<>();
        ArrayDeque<Point> prevC1 = new ArrayDeque<>();
        ArrayDeque<Point> prevC2 = new ArrayDeque<>();
        
        while (solutionPath.size() <= p.movesToSolve) {
            State curr = find(p.c1, p.c2);
            visited.add(curr);

            System.out.println("Coin turn: " + ((p.isC1Turn) ? "C1" : "C2"));
            System.out.println(curr);
            System.out.println("Coin moves: " + p.getMoves((p.isC1Turn) ? p.c1 : p.c2));
            
            if (curr.isSolved()) {
                solved = true;
                return true;
            }
            
            Point coin = p.getCurrentCoin();
            boolean moved = false;
            boolean found = false;
            
            for (Edge e : adjacencyList.get(curr)) {
                if ((e.isC1 && coin.equals(e.from.c1)) || (!e.isC1 && coin.equals(e.from.c2))) {
                    if (visited.contains(e.to)) {
                        found = true;
                        e.badEdge = true;
                        continue;
                    } 
                    
                    if (!e.badEdge) {
                        moved = true;
                        solutionPath.add(e);
                        solutionPathNames.put(e, (p.isC1Turn) ? "C1" : "C2");

                        prevC1.add(new Point(p.c1));
                        prevC2.add(new Point(p.c2));
                        p.move(coin, e.dir);
                        break;
                    }
                }
            }

            if (found && !moved) {
                System.out.println("Backing up.");
                solutionPath.pollLast();
                p.c1 = prevC1.pollLast();
                p.c2 = prevC2.pollLast();    
            } 

            if (!moved) {                
                p.pass();
            }
        }
        
        return false;
    }

    /**
     * Create all possible board states.
     */
    private void initGraph() {
        int nSquares = p.board.length * p.board.length;

        for (int i = 0; i < nSquares - 1; i++) {
            for (int j = i + 1; j < nSquares; j++) {
                State s = new State(p, stateGraph.size());

                s.c1.move(i % s.board.length, i / s.board.length);
                s.c2.move(j % s.board.length, j / s.board.length);
                stateGraph.add(s);
                adjacencyList.put(s, new ArrayList<Edge>());
            }
        }
    }

    /** 
     * Add edges between the nodes in the graph.
     */
    private void addEdges() {
        ArrayDeque<State> toAdd = new ArrayDeque<>(stateGraph);

        while (!toAdd.isEmpty()) {
            State curr = toAdd.poll();

            // Add edges for coin 1.
            Point from = curr.c1;
            ArrayList<Direction> moves = curr.getMoves(from);

            for (Direction dir : moves) {
                Point dest = new Point(from);
                dest.translate(dir.asPoint.x, dir.asPoint.y);
                adjacencyList.get(curr).add(new Edge(true, curr, find(dest, curr.c2), dir));
            }
            
            // Add edges for coin 2.
            from = curr.c2;
            moves = curr.getMoves(from);
            
            for (Direction dir : moves) {
                Point dest = new Point(from);
                dest.translate(dir.asPoint.x, dir.asPoint.y);
                adjacencyList.get(curr).add(new Edge(false, curr, find(curr.c1, dest), dir));
            }
        }
    }

    /**
     * Find the state where coins are in the same positions.
     * Order the coins are passed in does not matter.
     * 
     * @param c1 The position of one coin.
     * @param c2 The position of the other coin.
     * @return The found state, null if nothing found (shouldn't happen).
     */
    private State find(Point c1, Point c2) {
        for (State s : stateGraph) {
            if ((s.c1.equals(c1) && s.c2.equals(c2)) ||
                (s.c1.equals(c2) && s.c2.equals(c1))) {
                return s;
            }
        }
        
        return null;
    }

    public void printSolution() {
        if (!solved) {
            System.out.println("No solution was found.");
            return;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Solved in " + solutionPath.size() +  " turns!\n");
        sb.append("Solution:\n");        
        sb.append("START => ");

        for (Edge e : solutionPath) {
            sb.append(String.format("(%s %s) => ", solutionPathNames.get(e), e.dir));
        }

        sb.append("FINISH");

        System.out.println(sb.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("State Graph\n");

        for (State s : stateGraph) {
            sb.append(s);
        }

        sb.append("Adjacency List\n"); 

        for (State s : stateGraph) {
            for (Edge e : adjacencyList.get(s)) {
                sb.append(e + "\n");
            }
        }

        return sb.toString();
    }
    
    private class State extends Puzzle {
        int id;

        public State(Puzzle p, int id) {
            super(p);

            this.id = id;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append(String.format("State: %d\n", id));
            sb.append(super.toString());

            return sb.toString();
        }
    }

    private class Edge {
        boolean badEdge;
        boolean isC1;
        State from, to;
        Direction dir;

        public Edge(boolean isC1, State from, State to, Direction dir) {
            this.badEdge = false;
            this.isC1 = isC1;
            this.from = from;
            this.to = to;
            this.dir = dir;
        }

        @Override
        public String toString() {
            return String.format("[%d](%s, %s) => [%d]", from.id, (isC1) ? "C1" : "C2", dir, to.id);
        }
    }

    public static void main(String[] args) {
        Solver s = new Solver(new Puzzle1());
        // System.out.println(s);
        s.solve();
        s.printSolution();
        
        s = new Solver(new Puzzle2());
        // System.out.println(s);
        s.solve();
        s.printSolution();
        
        s = new Solver(new Puzzle3());
        // System.out.println(s);
        s.solve();
        s.printSolution();
        
        // s = new Solver(new Puzzle4());
        // System.out.println(s);
        // s.solve();
        // s.printSolution();
    }
}