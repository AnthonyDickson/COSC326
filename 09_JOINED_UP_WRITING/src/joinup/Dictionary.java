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
            words.add(input.next().toLowerCase()
                                  .replaceAll("[^a-z]", ""));
            // words.add(input.next());
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
        int lower = 0;
        int upper = entries.size() - 1;
        int i = -1;

        while (lower <= upper) {
            int mid = (lower + upper) / 2;
            String entry = get(mid);
            String target = prefix;

            if (entry.length() > target.length()) {
                entry = entry.substring(0, target.length());
            } else if (target.length() > entry.length()) {
                target = target.substring(0, entry.length());
            }

            if (entry.compareTo(target) < 0) {
                lower = mid + 1;
            } else if (entry.compareTo(target) > 0) {
                upper = mid - 1;
            } else {
                i = mid;
                break;
            }
        }

        if (i < 0) return -1;
        
        // Scan dictionary until we find the first word that starts with 
        // the same letter as the prefix.
        while (entries.get(i).compareTo(prefix) > 0 && i > 0) i--;
        // Just incase we went too far.
        while (entries.get(i).compareTo(prefix) < 0 && i < size() - 1) i++;
        
        if (i < 0) return -1;
        
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
        if (i < 0 || i >= entries.size()) return -1;

        if (entries.get(i).startsWith(prefix)) {
            return i;
        }

        return -1;
    }
    
    public boolean contains(String key) {
        return indexOf(key) > -1;
    } 
    
    public int indexOf(String key) {
        return Collections.binarySearch(entries, key);
    } 

    public String get(int index) {
        return entries.get(index);
    }

    public int size() {
        return entries.size();
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
