package joinup;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.InputStream;

/**
 * Etude 9, Joined Up Writing.
 * 
 * @author Anthony Dickson
 */
public class JoinUp {
    static enum LinkType { SINGLE, DOUBLE }
    Dictionary dict;
    List<String> path = new ArrayList<>();
    long updated = 0;

    /**
     * Construct JoinUp, creating a dictionary using input from stdin.
     */
    public JoinUp() {
        dict = new Dictionary(System.in);
    }

    /**
     * Find the shortest singly and doubly linked sequences starting from 
     * <code>start</code> and ending with <code>end</code>, and prints the
     * result to stdout.
     * 
     * @param start The string to start linking from.
     * @param end The string that should be at the end of the sequence.
     */
    public void run(String start, String end) {
        search(start, end, LinkType.SINGLE);
        search(start, end, LinkType.DOUBLE);
    }
    
    /**
     * Find the shortest linked sequence.
     * 
     * @param start The first word in the sequence.
     * @param end The last word in the sequence.
     * @param type The type of linking to use (single/double).
     * @return returns <code>true</code> if a path was found, 
     * <code>false</code> otherwise. 
     */
    private boolean search(String start, String end, LinkType type) {
        Queue<Node> q = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return n1.cost - n2.cost;
            }
        });
        
        long startTime = System.currentTimeMillis();
        Node source = new Node(start);
        Node goal = new Node(end);
        Set<Node> explored = new HashSet<>();
        source.cost = 0;
        q.add(source);

        while (!q.isEmpty()) {
            // printProgress(startTime, explored);

            Node current = q.poll();
            explored.add(current);

            if(current.equals(goal)) {
                path = path(current);
                System.out.println(path.size() + " " + String.join(" ", path));
                return true;
            }

            for(Edge e: current.getEdges(type)) {
                Node child = e.target;
                int cost = e.cost;
                child.cost = current.cost + cost;

                if(!explored.contains(child) && !q.contains(child)){
                    child.parent = current;
                    q.add(child);
                } 
            }
        }

        return false;
    }

	private void printProgress(long startTime, Set<Node> explored) {
		if (System.currentTimeMillis() - updated > 100) {
            double percentDone = 100.0 *  explored.size() / dict.size();
            long eta = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTime);
            eta = (long) ((eta / percentDone) * (100 - percentDone));
            long seconds = eta % 60;
            long minutes = (long) (eta / 60.0);
            System.err.format("\rSearched %.2f%% of the dictionary. Approx. time remaining: %2dm %2ds.", percentDone, minutes, seconds);
            updated = System.currentTimeMillis();
        }
	}
    
    /**
     * Gets the words in the solution path.
     * 
     * @param from The node at the end of the path.
     * @return list of the node values that leads from the start of the path to
     * the node <code>from</code>.
     */
    private List<String> path(Node from) {
        List<String> path = new ArrayList<>();

        for (Node n = from; n != null; n = n.parent) path.add(n.value);
        
        Collections.reverse(path);
        return path;
    }

    /**
     * Returns the dictionary indicies of words that are linked with 
     * <code>word</code>, i.e. words where the common part between the end
     * of <code>word</code> and the start of the other word is at least half as
     * long as one or both of the words.
     * 
     * @param word The word to link with.
     * @param type The type of link to use (single/double).
     * @return The dictionary indicies of the words that are linked with
     * <code>word</code>.
     */
    private List<String> findLinked(String word, LinkType type) {
        List<String> words = new ArrayList<>();
        
        for (int length = 0; length < word.length(); length++) {
            String prefix = word.substring(length);
            int i = dict.findPrefix(prefix);
            
            while (i > -1) {
                String other = dict.get(i);
                
                if (type == LinkType.SINGLE && isSinglyLinked(word, other, prefix)) {
                    words.add(other);
                } 
                else if (type == LinkType.DOUBLE && isDoublyLinked(word, other, prefix)) {
                    words.add(other);
                }
                
                i = dict.findPrefix(prefix, i + 1);
            }
        }
        
        return words;
    }

    /**
     * Check whether or not <code>a</code> and <code>b</code>
     * are singly linked by <code>commonPart</code>.
     * 
     * @param a The first string to check.
     * @param b The other string to check.
     * @param commonPart The substring that is common between <code>a</code>
     * and <code>b</code>.
     * @return <code>true</code> if <code>a</code> and <code>b</code> are 
     * singly linked, <code>false</code> otherwise.
     */
    private boolean isSinglyLinked(String a, String b, String commonPart) {
        if (commonPart.length() * 2 >= a.length() || commonPart.length() * 2 >= b.length()) return true;
        
        return false;
    }

    /**
     * Check whether or not <code>a</code> and <code>b</code>
     * are doubly linked by <code>commonPart</code>.
     * 
     * @param a The first string to check.
     * @param b The other string to check.
     * @param commonPart The substring that is common between <code>a</code>
     * and <code>b</code>.
     * @return <code>true</code> if <code>a</code> and <code>b</code> are 
     * doubly linked, <code>false</code> otherwise.
     */
    private boolean isDoublyLinked(String a, String b, String commonPart) {
        if (commonPart.length() * 2 < a.length()) return false;
        if (commonPart.length() * 2 < b.length()) return false;

        return true;
    }
     
    private class Node {
        final String value;
        Node parent;
        int cost;
        List<Edge> adj = new ArrayList<>();

        public Node(String value) {
            this.value = value;
            this.parent = null;
            this.cost = Integer.MAX_VALUE;
        }

        public List<Edge> getEdges(LinkType type) {
            adj.clear();

            for (String word : findLinked(value, type)) {
                Edge e = new Edge(new Node(word), 1);
                adj.add(e);
            }

            return adj;
        } 

        @Override
        public int hashCode() {
            int hash = 7;
            
            for (int i = 0; i < value.length(); i++) {
                hash = hash * 11 + value.charAt(i);
            }

            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            return value.equals(((Node) obj).value);
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private class Edge {
        final int cost;
        final Node target;

        public Edge(Node target, int cost) {
            this.target = target;
            this.cost = cost;
        }
    }

    public static void main(String[] args) {
        try {
            JoinUp jup = new JoinUp();
            jup.run(args[0], args[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java JoinUp <start word> <end word>");
        }
    }
}