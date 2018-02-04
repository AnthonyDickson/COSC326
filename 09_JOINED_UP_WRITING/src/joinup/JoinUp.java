package joinup;

import java.util.*;
import java.io.InputStream;

/**
 * JoinUp
 * Say that two words join up if a proper suffix of one is a proper prefix of 
 * the next. For instance the words “suffix” and “fixture” join up. For a pair 
 * of joined up words, the suffix of one which is a prefix of the other will be
 * called the common part. We specify two slight variations:
 * singly joined the common part is at least half as long as one of the two 
 * words, and doubly joined the common part is at least half as long as both words.
 * 
 * The basic problem will be to find, for a given “dictionary” a shortest sequence of joined
 * up words that link a beginning word to an end word. For instance:
 * bard ardent entire
 * is a sequence linking “bard” to “entire” in which each pair is doubly joined. On the
 * other hand,
 * suffix fixture read
 * is a singly joined sequence.
 * 
 * @author Anthony Dickson
 */
public class JoinUp {
    // BST dict = new BST();
    Dictionary dict;

    /**
     * Construct JoinUp, creating a dictionary using input from stdin.
     */
    public JoinUp() {
        dict = new Dictionary(System.in);
    }

    public void run(String start, String end) {
        System.out.println(start + " " + end);

        // List<Integer> indexes = findSinglyLinked(start);
        
        // System.out.println("Singly linked words for " + start);
        // for (int index : indexes) {
        //     System.out.print(dict.get(index) + " ");
        // }
        // System.out.println();

        // indexes = findDoublyLinked(start);

        // System.out.println("Doubly linked words for " +start);
        // for (int index : indexes) {
        //     System.out.print(dict.get(index) + " ");
        // }

        // System.out.println();
        
        // List<Integer> indexes = findDoublyLinked("agent");
        // for (int index : indexes) {
        //     System.out.print(dict.get(index) + " ");
        // }
        // System.out.println();

        System.out.println("Finding singly linked sequence...");
        List<String> sl = shortestPath(start, end, true);
        System.out.println("\nFinding doubly linked sequence...");
        List<String> dl = shortestPath(start, end, false);
        System.out.println();

        System.out.println(sl.size() + " " + String.join(" ", sl));
        System.out.println(dl.size() + " " + String.join(" ", dl));
    }
    
    /**
     * Find the shortest linked sequence.
     * 
     * @param start The first word in the sequence.
     * @param end The last word in the sequence.
     * @param singlyLinked Set to true if you want to search for singly linked
     * words or false if you want to search for doubly linked words.
     * @return The path that gives the shortest linked word sequence.
     */
    private List<String> shortestPath(String start, String end, boolean singlyLinked) {
        Queue<Node> q = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return n1.cost - n2.cost;
            }
        });
        
        Set<Node> explored = new HashSet<>();
        Node source = new Node(start);
        Node goal = new Node(end);
        source.cost = 0;
        q.add(source);

        while (!q.isEmpty()) {
            if (explored.size() % 100 == 0) System.out.println((100 * explored.size() / dict.size()) + "%");
            if (explored.size() == dict.size()) return new ArrayList<String>();

            Node current = q.poll();
            explored.add(current);

            if(current.value.equals(goal.value)) {
                System.out.println("Found solution path: " + path(current));
                return path(current);
            }

            current.adj = getEdges(current, singlyLinked);

            for(Edge e: current.adj) {
                Node child = e.target;
                int cost = e.cost;
                child.cost = current.cost + cost;

                if(!explored.contains(child) && !q.contains(child)){
                    child.parent = current;
                    q.add(child);

                    // System.out.println(path(current));
                    // System.out.println(child);
                    // System.out.println(q);
                    // System.out.println();
                } else if((q.contains(child)) && (child.cost > current.cost + cost)){
                    child.parent = current;

                    // the next two calls decrease the key of the node in the queue
                    q.remove(child);
                    q.add(child);
                }
            }
        }

        return new ArrayList<String>();
    }

    private List<Edge> getEdges(Node n, boolean singlyLinked) {
        List<Edge> adj = new ArrayList<>();
        List<Integer> indicies;

        if (singlyLinked) {
            indicies = findSinglyLinked(n.value);
        } else {
            indicies = findDoublyLinked(n.value);
        }

        for (int i : indicies) {
            Edge e = new Edge(new Node(dict.get(i)), 1);
            adj.add(e);
        }

        return adj;
    } 

    /**
     * Returns the dictionary indicies of words that are singly linked with 
     * <code>word</code>, i.e. words where the common part between the end
     * of <code>word</code> and the start of the other word is at least half as
     * long as one of the words.
     * 
     * @param word The word to link with.
     * @return The dictionary indicies of the words that are singly linked with
     * <code>word</code>.
     */
    private List<Integer> findSinglyLinked(String word) {
        List<Integer> words = new ArrayList<>();

        int length = 1;

        while (length < word.length()) {
            String prefix = word.substring(word.length() - length);
            int i = dict.findPrefix(prefix);
            
            while (i > -1) {
                if (length >= dict.get(i).length() / 2 || length >= word.length() / 2) {
                    words.add(i);
                }

                i = dict.findPrefix(prefix, i + 1);
            }

            length++;
        }

        return words;
    }

    /**
     * Returns the dictionary indicies of words that are doubly linked with 
     * <code>word</code>, i.e. words where the common part between the end
     * of <code>word</code> and the start of the other word is at least half as
     * long as both words.
     * 
     * @param word The word to link with.
     * @return The dictionary indicies of the words that are doubly linked with
     * <code>word</code>.
     */
    private List<Integer> findDoublyLinked(String word) {
        List<Integer> words = new ArrayList<>();

        for (int index : findSinglyLinked(word)) {
            String left = word;
            String right = dict.get(index);

            if (left.length() > 2 * right.length() || right.length() > 2 * left.length()) continue;

            int length = Math.max(left.length(), right.length()) / 2;
            if (right.startsWith(left.substring(left.length() - length))) words.add(index);
        }

        return words;
    }

    /**
     * Gets the words in the solution path.
     */
    private List<String> path(Node from) {
        List<String> path = new ArrayList<>();

        for (Node n = from; n != null; n = n.parent) path.add(n.value);
        
        Collections.reverse(path);
        return path;
    }
     
    private class Node {
        final String value;
        Node parent;
        int cost;
        List<Edge> adj = new ArrayList<>();

        public Node(String value) {
            this.value = value;
            this.parent = null;
            int cost = Integer.MAX_VALUE;
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
        public String toString() {
            return value;
        }
    }

    public class Edge {
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