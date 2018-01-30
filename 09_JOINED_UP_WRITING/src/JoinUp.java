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
    ArrayList<String> dict = new ArrayList<>();

    /**
     * Process input from stdin, and add the words in the input into the 
     * dictionary.
     * 
     * @param input The input stream to read the input from.
     */
    public void processInput(InputStream input) {
        Scanner in = new Scanner(input);

        while (in.hasNext()) {
            dict.add(in.next());
        }

        in.close();
    }

    /**
     * Find the shortest sequences of joined up words that link 
     * <code>start</code> and <code>end</code>, and output the results to 
     * stdout.
     * 
     * @param start The word to start joining up from.
     * @param end The last word to join up to.
     */
    public void run(String start, String end) {
        String[] singly = singlyJoined(start, end);
        StringBuilder singlyOutput = new StringBuilder();
        singlyOutput.append(singly.length);
        
        for (String s : singly) {
            singlyOutput.append(" " + s);
        }
        
        String[] doubly = doublyJoined(start, end);
        StringBuilder doublyOutput = new StringBuilder();
        doublyOutput.append(doubly.length);

        for (String s : doubly) {
            doublyOutput.append(" " + s);
        }

        System.out.println(singlyOutput.toString());
        System.out.println(doublyOutput.toString());
    }

    /**
     * Get the shortest sequence of joined up words that link a beginning word 
     * to an end word, where the common part is at least half as long as one of 
     * the two words.
     * 
     * @return The sequence of joined up words.
     */
    public String[] singlyJoined(String start, String end) {
        ArrayDeque<String> seq = new ArrayDeque<>();

        seq.add(start);

        while (true) {
            String curr = seq.peekLast();
            String suffix = curr.substring(curr.length() / 2);

            for (String other : dict) {
                String prefix = other.substring(0, other.length() / 2);

                if (other.startsWith(suffix) || curr.endsWith(prefix)) {
                    seq.add(other);
                }
            }

            if (curr.equals(seq.peekLast())) {
                break;
            }
        }
        
        return seq.toArray(new String[0]);
    }

    /**
     * Get the shortest sequence of joined up words that link a beginning word 
     * to an end word, where the common part is at least half as long as both 
     * words.
     * 
     * @return The sequence of joined up words.
     */
    public String[] doublyJoined(String start, String end) {
        ArrayDeque<String> seq = new ArrayDeque<>();
        
        return seq.toArray(new String[0]);
    }
    
    private class BST {
        String root;
        BST left;
        BST right;
        
        /** Create an empty BST. */
        public BST() {
            this.root = "";
            this.left = null;
            this.right = null;
        }
        
        /** 
         * Create a new BST with the root set to <code>key</code>.
         * 
         * @param key The value of root.
         */
        public BST(String key) {
            this.root = key;
            this.left = null;
            this.right = null;
        }
        
        /**
         * Add a key to the BST.
         * 
         * @param key The key to add.
         */
        void add(String key) {
            if (isEmpty()) {
                root = key;
            } else if (key.compareTo(root) < 0) {
                if (left != null) {
                    left.add(key);
                } else {
                    left = new BST(key);
                }
            } else if (key.compareTo(root) > 0) {
                if (right != null) {
                    right.add(key);
                } else {
                    right = new BST(key);
                }
            }
        }

        /**
         * Search the BST for a key that starts with <code>prefix</code>.
         * 
         * @param prefix The prefix to search for.
         * @return The key that starts with <code>prefix</code>. 
         * Returns <code>null</code> if not found.
         */
        String findPrefix(String prefix) {
            if (isEmpty()) return null;

            if (root.startsWith(prefix)) {
                return root;
            } else if (prefix.compareTo(root) < 0) {
                return (left == null) ? null : left.findPrefix(prefix);
            } else if (prefix.compareTo(root) > 0) {
                return (right == null) ? null : right.findPrefix(prefix);
            }

            return null;
        }
        
        /**
         * Check if this BST is empty.
         * 
         * @return true if empty, false otherwise.
         */
        boolean isEmpty() {
            return this.root == "";
        }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            
            if (left != null) {
                sb.append(left.toString());
            }
            
            sb.append(root + " ");
            
            if (right != null) {
                sb.append(right.toString());
            }
            
            return sb.toString();
        }
    }
    
    public static void main(String[] args) {
        try {
            JoinUp jup = new JoinUp();
            jup.processInput(System.in);  
            jup.run(args[0], args[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java JoinUp <start word> <end word>");
        }
    }
}