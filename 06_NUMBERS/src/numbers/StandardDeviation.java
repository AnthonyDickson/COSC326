package numbers;

import java.util.*;

public class StandardDeviation {
	private static final Random rand = new Random();
	private static float spMethod1, spMethod2;
	private static double dpMethod1, dpMethod2;
	
	public static void runTest(double[] a, int n, int c) {		
		for (int i = 0; i < a.length; i++) {
			a[i] += c;
		}
		
		method1(a, n, c);
		method2(a, n, c);
		
		System.out.println(n + "\t" + c + "\t" + spMethod1 + "\t" + spMethod2 + "\t" + dpMethod1 + "\t" + dpMethod2);
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
		
		// Double Precision
		double avg2 = sum(a) / n;
		double squareDiffSum2 = 0;
		
		for (int i = 0; i < n; i++) {
			squareDiffSum2 += Math.pow(a[i] - avg2, 2);
		}
		
		double dpStdDev = Math.sqrt(squareDiffSum2 / n);
		dpMethod1 = dpStdDev;
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
		
		// Double Precision
		double squareSum2 = 0;
		
		for (int i = 0; i < n; i++) {
			squareSum2 += Math.pow(a[i], 2);
		}
		
		double sumSquared2 = Math.pow(sum(a), 2);
		
		double dpStdDev = Math.sqrt((squareSum2 - sumSquared2 / n) / n);
		dpMethod2 = dpStdDev;
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
		
		System.out.println("Standard Deviation");
		System.out.println("\t\tSingle Precision\t\tDouble Precision\t");
		System.out.println("n\tc\tMethod One\tMethod Two\tMethod One\tMethod Two");

		ArrayList<Integer> n = new ArrayList<>();
		ArrayList<Integer> c = new ArrayList<>();

		while (scanner.hasNextInt()) {
			try {   
				n.add(scanner.nextInt());
				c.add(scanner.nextInt());
				
			} catch (NumberFormatException e) {
				System.out.println("n and c must be integers.");
			} 
		}

		for (int i = 0; i < n.size(); i++) {
			runTest(arrayOfDoubles(n.get(i)), n.get(i), c.get(i));
		}

		for (int i = 0; i < n.size(); i++) {
			runTest(arrayOfRandDoubles(n.get(i)), n.get(i), c.get(i));
		}

		scanner.close();		
	}
}