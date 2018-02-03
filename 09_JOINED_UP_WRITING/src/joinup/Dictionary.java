package joinup;

import java.io.InputStream;
import java.util.*;

/**
 * A sorted dictionary of words.
 */
public class Dictionary {
    private List<String> entries = new ArrayList<>();

    public Dictionary(InputStream in) {
        super();

        Set<String> words = new TreeSet<>();
        Scanner input = new Scanner(in);

        while (input.hasNext()) {
            words.add(input.next());
        }

        input.close();
        entries.addAll(words);
    }

    /** 
     * Search the entire dictionary for a word starting with 
     * <code>prefix</code>.
     * 
     * @param prefix The prefix to match.
     * @return the index of the word that starts with <code>prefix</code>.
     * Returns -1 if no such word was found.
     */
    public int findPrefix(String prefix) {
        int i = 0;
        int lower = 0;
        int upper = entries.size() - 1;

        // System.out.println("Looking for word that starts with " + prefix);

        // Find a word that starts with the same letter as the prefix.
        while (lower <= upper) {
            i = lower + (upper - lower) / 2;
            
            if (prefix.charAt(0) < entries.get(i).charAt(0)) upper = i - 1;
            else if (prefix.charAt(0) > entries.get(i).charAt(0)) lower = i + 1;
            else break;
        }

        if (lower > upper) return -1;

        // System.out.println("Found word that starts with " + prefix.charAt(0) + ": " + entries.get(i));
        
        // Scan dictionary until we find the first word that starts with 
        // the same letter as the prefix.
        while (entries.get(i).compareTo(prefix) > 0 && i > 0) i--;
        
        if (i < 0) return -1;

        // System.out.println("Found first word that comes before " + prefix.charAt(0) + ": " + entries.get(i));
        
        return findPrefix(prefix, i);
    } 

    /**
     * Search the dictionary for a word starting with 
     * <code>prefix</code> starting from index <code>i</code>.
     * 
     * @param prefix The prefix to match.
     * @param i Where to start looking from.
     * @return the index of the word that starts with <code>prefix</code>.
     * Returns -1 if no such word was found.
     */
    public int findPrefix(String prefix, int i) {
        while (entries.get(i).charAt(0) == prefix.charAt(0) && i < entries.size()) {
            if (entries.get(i).startsWith(prefix)) return i;
            i++;
        }

        return -1;
    }
    
    public boolean contains(String key) {
        return indexOf(key) > -1;
    } 
    
    public int indexOf(String key) {
        int lower = 0;
        int upper = entries.size();
        
        while (lower <= upper) {
            int mid = lower + (upper - lower) / 2;
            
            if (key.compareTo(entries.get(mid)) < 0) upper = mid - 1;
            else if (key.compareTo(entries.get(mid)) > 0) lower = mid + 1;
            else if (key.equals(entries.get(mid))) return mid;
        }

        return -1;
    } 

    public String get(int index) {
        return entries.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String s : entries) {
            sb.append(s);
            sb.append(" ");    
        }

        return sb.toString();
    }
    
}
