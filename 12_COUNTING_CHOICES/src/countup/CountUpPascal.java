package countup;

import java.util.*;

/**
 * Computes and prints n choose k up to the max value of a long.
 * Uses pascal's triangle to compute n choose k.
 * 
 * @author Anthony Dickson
 */
public class CountUpPascal {
	private static long MISSING_VALUE = -1L;

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

		// Stores the previous row and the current row of pascals triangle.
		long[][] pascal = new long[2][n / 2 + 1];		
		pascal[0][0] = 1L;

		// Generate pascal's triangle up to row n, using symmetry and reusing array rows.
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= i / 2; j++) {
				long left, right;
				if (j - 1 >= 0) {
					left = pascal[(i + 1) % 2][j - 1];
				} else {
					left = 0;
				}

				if (i % 2 == 0 && pascal[(i + 1) % 2][j] == 0) {
					right = left;
				} else {
					right = pascal[(i + 1) % 2][j];
				}

				// Mark values that can't be computed due to missing value(s).
				if (left == MISSING_VALUE || right == MISSING_VALUE) {
					pascal[i % 2][j] = MISSING_VALUE;
					break;
				}

				try {
					pascal[i % 2][j] = Math.addExact(left, right);
				} catch (ArithmeticException e) {
					pascal[i % 2][j] = MISSING_VALUE;
				}	
			}
		}
		
		if (k > n / 2) k = n - k;

		if (pascal[n % 2][k] == MISSING_VALUE) {
			System.out.println("> Float.MAX_VALUE");
		} else {
			System.out.println(pascal[n % 2][k]);	
		}
	}    
}