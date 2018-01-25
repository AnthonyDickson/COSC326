package numbers;

import java.util.*;

public class StandardDeviation {
	private static final Random rand = new Random(2018);
	private static int nTests = 0;
	private static float spMethod1, spMethod2;
	private static double dpMethod1, dpMethod2;
	
	public static void runTest(double[] a, int n, int c) {		
		for (int i = 0; i < a.length; i++) {
			a[i] += c;
		}
		
		System.out.println("################################################################################");
		System.out.println("Test #" + ++nTests);
		System.out.println("a=" + Arrays.toString(a).substring(0, 70).split("[\\s-]*\\d*\\.*\\d*$")[0] + " ...]");
		System.out.println("n=" + n);
		System.out.println("c=" + c);
		System.out.println("\nComparison of Single and Double Precision");
		System.out.println("Method (1)");
		method1(a, n, c);
		System.out.println("\nMethod (2)");
		method2(a, n, c);

		double spDiff = Percentages.difference(spMethod1, spMethod2);
		double dpDiff = Percentages.difference(dpMethod1, dpMethod2);

		System.out.println("\nComparison of Methods");
		System.out.println("Single Precision");
		System.out.println("σ (Method One): " + spMethod1);
		System.out.println("σ (Method Two): " + spMethod2);
		System.out.println("Percent Difference: " + Percentages.getString(spDiff));
		System.out.println("agree: " + Percentages.isAcceptable(spDiff));
		
		System.out.println("\nDouble Precision");
		System.out.println("σ (Method One): " + dpMethod1);
		System.out.println("σ (Method Two): " + dpMethod2);
		System.out.println("Percent Difference: " + Percentages.getString(dpDiff));
		System.out.println("agree: " + Percentages.isAcceptable(dpDiff));
	}
	  
	public static void method1(double[] a, int n, int c) {
		// Single Precision
		float avg = (float) sum(a) / n;
		float squareDiffSum = 0;
		
		for (int i = 0; i < n; i++) {
			squareDiffSum += (float) Math.pow((float) a[i] - avg, 2);
		}
		
		float spStdDev = (float) Math.sqrt(squareDiffSum / n);
		spMethod1 = spStdDev;
		System.out.println("σ (Single Precision): " + spStdDev); 
		
		// Double Precision
		double avg2 = sum(a) / n;
		double squareDiffSum2 = 0;
		
		for (int i = 0; i < n; i++) {
			squareDiffSum2 += Math.pow(a[i] - avg2, 2);
		}
		
		double dpStdDev = Math.sqrt(squareDiffSum2 / n);
		dpMethod1 = dpStdDev;
		System.out.println("σ (Double Precision): " + dpStdDev); 
		
		double diff = Percentages.difference(spStdDev, dpStdDev);
		System.out.println("Percent Difference: " + Percentages.getString(diff));
		System.out.println("Agree: " + Percentages.isAcceptable(diff));
	}
	
	public static void method2(double[] a, int n, int c) {
		// Single Precision
		float squareSum = 0;
		
		for (int i = 0; i < n; i++) {
			squareSum += (float) Math.pow((float) a[i], 2);
		}
		
		float sumSquared = (float) Math.pow(sum(a), 2);
		
		float spStdDev = (float) Math.sqrt((squareSum - sumSquared / n) / n);
		spMethod2 = spStdDev;
		System.out.println("σ (Single Precision): " + spStdDev); 
		
		// Double Precision
		double squareSum2 = 0;
		
		for (int i = 0; i < n; i++) {
			squareSum2 += Math.pow(a[i], 2);
		}
		
		double sumSquared2 = Math.pow(sum(a), 2);
		
		double dpStdDev = Math.sqrt((squareSum2 - sumSquared2 / n) / n);
		dpMethod2 = dpStdDev;
		System.out.println("σ (Double Precision): " + dpStdDev); 
		
		double diff = Percentages.difference(spStdDev, dpStdDev);
		System.out.println("Percent Difference: " + Percentages.getString(diff));
		System.out.println("Agree: " + Percentages.isAcceptable(diff));
	}

	public static int sum(int[] a) {
		int sum = 0;

		for (int i = 0; i < a.length; i++) {
			sum += a[i];
		}

		return sum;
	}

	public static double sum(double[] a) {
		double sum = 0;

		for (int i = 0; i < a.length; i++) {
			sum += a[i];
		}

		return sum;
	}

	public static int[] arrayOfInts(int n) {
		int[] a = new int[n];

		for (int i = 0; i < n; i++) {
			a[i] = i;
		}

		return a;
	}

	public static int[] arrayOfRandInts(int n) {
		int[] a = new int[n];

		for (int i = 0; i < n; i++) {
			a[i] = rand.nextInt() % 100;
		}

		return a;
	}

	public static double[] arrayOfDoubles(int n) {
		double[] a = new double[n];
		int max = 100;

		for (int i = 0; i < n; i++) {
			a[i] = (double) i;
		}

		return a;
	}

	public static double[] arrayOfRandDoubles(int n) {
		double[] a = new double[n];
		int max = rand.nextInt(1000);

		for (int i = 0; i < n; i++) {
			a[i] = rand.nextDouble() * 2 * max - max;
		}

		return a;
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int i = 0;
		
		while (scanner.hasNextInt()) {
			try {   
				int n = scanner.nextInt();
				int c = scanner.nextInt();
				
				runTest(arrayOfDoubles(n), n, c);
				runTest(arrayOfRandDoubles(n), n, c);
			} catch (NumberFormatException e) {
				System.out.println("n and c must be integers.");
			} 
		}

		scanner.close();		
	}
}