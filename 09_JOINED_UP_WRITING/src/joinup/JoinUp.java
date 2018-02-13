package joinup;

import java.util.*;
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
    Set<Node> explored = new HashSet<>();
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
        System.out.println(path.size() + " " + String.join(" ", path));
        search(start, end, LinkType.DOUBLE);
        System.out.println(path.size() + " " + String.join(" ", path));
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
        Queue<Node> q = new ArrayDeque<>();        
        Node source = new Node(start);
        Node goal = new Node(end);
        explored.clear();
        path.clear();
        q.add(source);
        int i = 0;
        
        while (!q.isEmpty()) {
            i++;
            Node current = q.poll();
            explored.add(current);

            System.err.println("\nIteration #: " + i);
            System.err.println("Queue size: " + q.size());
            System.err.println("Explored count: " + explored.size());
            System.err.println("Queue size + explored count = " + (q.size() + explored.size()));
            System.err.println("Queue size + explored count <= dictionary size: " + (q.size() + explored.size() <= dict.size()));
            //if (q.size() > dict.size()) return false;

            if(current.equals(goal)) {
                path = path(current);
                return true;
            }
            

            long startTime = System.currentTimeMillis();
            
            for(Node child: current.getEdges(type)) {                
                if(!explored.contains(child) && !q.contains(child)){
                    child.parent = current;
                    q.add(child);
                }
            }

            long endTime = System.currentTimeMillis();
            System.err.println("Iterations took " + (endTime - startTime) + "ms.");
        }

        return false;
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
        List<Node> adj = new ArrayList<>();

        public Node(String value) {
            this.value = value;
            this.parent = null;
        }

        public List<Node> getEdges(LinkType type) {
            adj.clear();
            

            for (String word : findLinked(value, type)) {
                adj.add(new Node(word));
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

    public static void main(String[] args) {
        try {
            JoinUp jup = new JoinUp();
            jup.run(args[0], args[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java JoinUp <start word> <end word>");
        }
    }
}
