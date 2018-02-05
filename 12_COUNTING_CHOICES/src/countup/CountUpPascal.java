package countup;

import java.util.*;

/**
 * Computes and prints C(n, k) up to the max value of a long.
 * Uses pascal's triangle to compute C(n, k).
 * 
 * @author Anthony Dickson
 */
public class CountUpPascal {
    public static void main (String[] args){
		int n;
		int k;

		try {
			n = Integer.parseInt(args[0]);
			k = Integer.parseInt(args[1]);		
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Usage: java CountUp <n> <k>");
			return;
        } 
        
        // Handle speical cases.
        if (n == k || k == 0) {
            System.out.println(1);
            return;
        }
        
        if (k == 1 || n - k == 1) {
            System.out.println(n);
            return;
        }

        // Since C(n, k) is equivalent to C(n, n - k)
        if (k > n - k) k = n - k;

		long[] pascal = new long[k + 1];		
        pascal[0] = 1L;
        
		// Generate pascal's triangle up to row n.
		for (int i = 1; i <= n; i++) {
			for (int j = k; j > 0; j--) {
                try {
                    pascal[j] = Math.addExact(pascal[j], pascal[j - 1]);
                } catch (ArithmeticException e) {
                    System.out.println("> Float.MAX_VALUE");
                    return;
                }
			}
		}		
		
        System.out.println(pascal[k]);
    }
}