package numbers;

import java.util.*;

/**
 * This is a Numbers class which contains methods and variable to
 * observe how computer stores and handle different numbers.
 */
public class HarmonicNumbers {
	private static int nTests = 0;
	private static float spHN, spHNReverse;
	private static double dpHN, dpHNReverse;

	public static void runTest(int n) {
		System.out.println("################################################################################");
		System.out.println("Test #" + ++nTests); 

		System.out.println("\nComparison of Single and Double Precision");
		harmonicNumber(n);
		System.out.println("");
		harmonicNumberReverse(n);

		double spDiff = Percentages.difference(spHN, spHNReverse);
		double dpDiff = Percentages.difference(dpHN, dpHNReverse);

		System.out.println("\nComparison of Computation Orders");
		System.out.println("Single Precision");
		System.out.println("H(" + n + "): " + spHN);
		System.out.println("H(" + n + ") (Reverse Order): " + spHNReverse);
		System.out.println("Percent Difference: " + Percentages.getString(spDiff));
		System.out.println("agree: " + Percentages.isAcceptable(spDiff));
		
		System.out.println("\nDouble Precision");
		System.out.println("H(" + n + "): " + dpHN);
		System.out.println("H(" + n + ") (Reverse Order): " + dpHNReverse);
		System.out.println("Percent Difference: " + Percentages.getString(dpDiff));
		System.out.println("agree: " + Percentages.isAcceptable(dpDiff));
	}

  	public static void harmonicNumber(int n) {
		float spSum = 0;
		double dpSum = 0;

		for (int i = 1; i <= n; i++){
			spSum += 1 / (float) i;
			dpSum += 1 / (double) i;
		}

		spHN = spSum;
		dpHN = dpSum;
		
		double diff = Percentages.difference(spSum, dpSum);
		
		System.out.println("H(" + n + ")");
		System.out.println("Single Precision: " + spSum);
		System.out.println("Double Precision: " + dpSum);
		System.out.println("Percent Difference: " + Percentages.getString(diff));
		System.out.println("agree: " + Percentages.isAcceptable(diff));
	}
	
	public static void harmonicNumberReverse(int n) {
		float spSum = 0;
		double dpSum = 0;
		
		for(int i = n; i >= 1; i--){
			spSum += 1 / (float) i;
			dpSum += 1 / (double) i;
		}
		
		spHNReverse = spSum;
		dpHNReverse = dpSum;

		double diff = Percentages.difference(spSum, dpSum);
	
		System.out.println("H(" + n + ") (Reverse Order)");
		System.out.println("Single Precision: " + spSum);
		System.out.println("Double Precision: " + dpSum);
		System.out.println("Percent Difference: " + Percentages.getString(diff));
		System.out.println("agree: " + Percentages.isAcceptable(diff));
	}

  	public static void main(String args[]) {		
		Scanner scanner = new Scanner(System.in);
	  
		while (scanner.hasNextInt()) {
			try {   
				runTest(scanner.nextInt());
			} catch (NumberFormatException e) {
				System.out.println("n must be an integer.");
			} 
		}   
		
		scanner.close();
	}
}
