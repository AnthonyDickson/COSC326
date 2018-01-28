package coroutine;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.awt.Point;
import java.util.ArrayDeque;

/**
 * Solves coroutine puzzles.
 * 
 * @author Anthony Dickson
 */
public class Solver {
    List<State> stateGraph = new ArrayList<>();
    Map<State, ArrayList<Edge>> adjacencyList = new HashMap<>();

    /**
     * @param p The coroutine puzzle to solve.
     */
    public Solver(Puzzle p) {
        initGraph(p);
        addEdges();
    }

    /**
     * Create all possible board states.
     */
    private void initGraph(Puzzle p) {
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
     * @return The found state.
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
        boolean isC1;
        State from, to;
        Direction dir;

        public Edge(boolean isC1, State from, State to, Direction dir) {
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
        System.out.println(new Solver(new Puzzle1()));
    }
}