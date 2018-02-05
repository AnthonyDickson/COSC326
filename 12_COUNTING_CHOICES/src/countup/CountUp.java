package countup;

import java.util.*;

/**
 * Computes C(n, k) and prints the result if it fits within a 64-bit integer.
 * 
 * @author Anthony Dickson, Alexis Barltrop
 */
public class CountUp {

	/**
	 * Get the numerator terms after cancelling out with denominator terms.
	 * 
	 * @param n The value of n in the formula for n choose k.
	 * @param n The value of k in the formula for n choose k.
	 * @return The simplified numerator terms.
	 */
	public static List<Long> numeratorTerms(long n, long k) {
		List<Long> nums = new ArrayList<>();
		long bound;
		
		if (k > n - k) {
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
	public static List<Long> denominatorTerms(long n, long k) {
		List<Long> nums = new ArrayList<>();
		long bound;
		
		if (k > n - k) {
			bound = n - k;
		} else {
			bound = k;
		}

		for (long i = bound; i > 0; i--) {
			nums.add(i);
		}
		
		return nums;
	}

	public static List<Long> primeFactors(List<Long> nums) { 
		List<Long> result = new ArrayList<>();

		for (long n : nums) {
			result.addAll(primeFactors(n));
		}

		return result;
	}

	public static List<Long> primeFactors(long n) {
		List<Long> factors = new ArrayList<Long>();

        for (long i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.add(i);
                n /= i;
            }
		}
		
        return factors;
	}

	/**
	 * Factor out/cancel terms in the given fraction.
	 * Modifies parameters directly.
	 * 
	 * @param numerator The terms in the numerator.
	 * @param denominator The terms in the denominator.
	 */
	public static void factorTerms(List<Long> numerator, List<Long> denominator) {
		for (int i = 0; i < numerator.size(); i++) {
			for (int j = 0; j < denominator.size(); j++) {
				if (numerator.get(i) % denominator.get(j) == 0) {
					numerator.set(i, numerator.get(i) / denominator.get(j));
					denominator.set(j, 1L);
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
	public static long getProduct(List<Long> nums) {
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
		List<Long> numerator = numeratorTerms(n, k);
		List<Long> denominator = denominatorTerms(n, k);
		denominator = primeFactors(denominator);
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