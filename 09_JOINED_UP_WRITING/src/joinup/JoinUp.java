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

        List<Integer> indexes = findSinglyLinked(start);
        
        System.out.println("Singly linked words for " + start);
        for (int index : indexes) {
            System.out.print(dict.get(index) + " ");
        }
        System.out.println();

        indexes = findDoublyLinked(start);

        System.out.println("Doubly linked words for " +start);
        for (int index : indexes) {
            System.out.print(dict.get(index) + " ");
        }

        System.out.println();
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

            int length = Math.max(left.length(), right.length()) / 2;
            if (right.startsWith(left.substring(length))) words.add(index);
        }

        return words;
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