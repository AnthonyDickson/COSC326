package numbers;

import java.util.*;

/**
 * This is a Numbers class which contains methods and variable to
 * observe how computer stores and handle different numbers.
 */
public class HarmonicNumbers {
	private static float spHN, spHNReverse;
	private static double dpHN, dpHNReverse;

	public static void runTest(int n) {
		harmonicNumber(n);
		harmonicNumberReverse(n);
		
		System.out.println(n + "\t" + spHN + "\t" + spHNReverse + "\t" + dpHN + "\t" + dpHNReverse);
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
	}

  	public static void main(String args[]) {		
		Scanner scanner = new Scanner(System.in);
	  
		System.out.println("Harmonic Numbers");
		System.out.println("\tSingle Precision\t\tDouble Precision\t");
		System.out.println("n\tH(n)\tH(n) (Reverse Order)\tH(n)\tH(n) (Reverse Order)");

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
