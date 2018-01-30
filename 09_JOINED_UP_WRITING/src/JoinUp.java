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
    BST dict = new BST();
    String start = "";
    String end = "";

    public JoinUp(String start, String end) {
        this.start = start;
        this.end = end;
    }

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
     * Get the shortest sequence of joined up words that link a beginning word 
     * to an end word, where the common part is at least half as long as one of 
     * the two words.
     * 
     * @return The sequence of joined up words.
     */
    public ArrayList<String> singlyJoined() {
        return new ArrayList<String>();
    }

    /**
     * Get the shortest sequence of joined up words that link a beginning word 
     * to an end word, where the common part is at least half as long as both 
     * words.
     * 
     * @return The sequence of joined up words.
     */
    public ArrayList<String> doublyJoined() {
        return new ArrayList<String>();
    }
    
    private class BST {
        String root;
        BST left;
        BST right;
        
        public BST() {
            this.root = "";
            this.left = null;
            this.right = null;
        }
        
        public BST(String key) {
            this.root = key;
            this.left = null;
            this.right = null;
        }
        
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
            JoinUp jup = new JoinUp(args[0], args[1]);
            jup.processInput(System.in);  
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java JoinUp <start word> <end word> < <dictionary file>");
        }
    }
}