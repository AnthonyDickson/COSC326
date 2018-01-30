import java.util.Scanner;

/**
 * JoinUp
 * 
 * @author Anthony Dickson
 */
public class JoinUp {
    BST dict = new BST();

    void processLine(String line) {
        dict.add(line);
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
        Scanner in = new Scanner(System.in);

        JoinUp jup = new JoinUp();

        while (in.hasNextLine()) {
            jup.processLine(in.nextLine());
        }

        System.out.println(jup.dict);

        in.close();
    }
}