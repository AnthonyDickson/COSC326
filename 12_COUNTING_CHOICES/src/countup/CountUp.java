package countup;

import java.util.*;

public class CountUp {
	/**
	 * Get the numerator terms after cancelling out with denominator terms.
	 * 
	 * @param n The value of n in the formula for n choose k.
	 * @param n The value of k in the formula for n choose k.
	 * @return The simplified numerator terms.
	 */
	public static ArrayList<Long> numeratorTerms(long n, long k) {
		ArrayList<Long> nums = new ArrayList<>();
		long bound;
		
		if (k > n / 2) {
			bound = k;
		} else {
			bound = n - k;
		}
	
		for (long i = n; i > bound; i--) {
			nums.add(i);
		}
		
		return nums;
	}
	
	/**
	 * Get the denominator terms after cancelling out with numerator terms.
	 * 
	 * @param n The value of n in the formula for n choose k.
	 * @param n The value of k in the formula for n choose k.
	 * @return The simplified denominator terms.
	 */
	public static ArrayList<Long> denominatorTerms(long n, long k) {
		ArrayList<Long> nums = new ArrayList<>();
		long bound;
		
		if (k > n / 2) {
			bound = n - k;
		} else {
			bound = k;
		}

		for (long i = bound; i > 0; i--) {
			nums.add(i);
		}
		
		return nums;
	}

	/**
	 * Factor out/cancel terms in the given fraction.
	 * Modifies parameters directly.
	 * 
	 * @param numerator The terms in the numerator.
	 * @param denominator The terms in the denominator.
	 */
	public static void factorTerms(ArrayList<Long> numerator, ArrayList<Long> denominator) {
		for (int i = 0; i < numerator.size(); i++) {
			for (int j = 0; j < denominator.size(); j++) {
				if (numerator.get(i) % denominator.get(j) == 0) {
					numerator.set(i, numerator.get(i) / denominator.get(j));
					denominator.set(j, 1L);
					break;
				}
			}
		}
	}

	/**
	 * Compute the product of multiplying each term in <code>nums</code>.
	 * 
	 * @param nums The terms to multiply together.
	 * @return The product/result of multiplying all the terms in 
	 * <code>nums</code> together.
	 */
	public static long getProduct(ArrayList<Long> nums) {
		long total = 1;

		for (Long n : nums) {
			total = Math.multiplyExact(total, n);
		}

		return total;
	}

    public static void main (String[] args){
        long n = Long.parseLong(args[0]);
        long k = Long.parseLong(args[1]);

		// Get simplified numerator and denominator terms.
		ArrayList<Long> numerator = numeratorTerms(n, k);
		ArrayList<Long> denominator = denominatorTerms(n, k);

		// Further simplify the numerator and denominator terms.
		factorTerms(numerator, denominator);

		// Calculate numerator/denominator
		try {
			long numeratorTotal = getProduct(numerator);		
			long denominatorTotal = getProduct(denominator);
			
			// Calculate final answer.
			long result = numeratorTotal / denominatorTotal;
			System.out.println(result);
		} catch (ArithmeticException e) {
			System.out.println("Error: Long Overflow");
			return;
		}
	}    
}