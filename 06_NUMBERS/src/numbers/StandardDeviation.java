package numbers;

import java.util.*;

public class StandardDeviation {
	static final Random rand = new Random();
	static int testCount = 0;

	int n;
	int[] a;
	int c;

	public StandardDeviation() {
		this(100, 0);
	}
	
	public StandardDeviation(int n, int c) {
		this.n = n;
		this.a = arrayOfRandInts(n);
		this.c = c;

		for (int i = 0; i < n; i++) {
			a[i] += + c;
		}

		run();
	}
	
	public StandardDeviation(int n, int c, int[] a) {
		this.n = n;
		this.a = a.clone();
		this.c = c;

		for (int i = 0; i < n; i++) {
			a[i] += + c;
		}

		run();
	}
	
	void run() {
		System.out.println("################################################################################");
		System.out.println("Test #" + ++testCount);
		// System.out.println("a=" + Arrays.toString(a));
		System.out.println("n=" + n);
		System.out.println("c=" + c);
		System.out.println("\nMethod (1):");
		method1();
		System.out.println("\nMethod (2):");
		method2();
	}
	  
	void method1() {
		// Single Precision
		float avg = sum(a) / n;
		float squareDiffSum = 0;
		
		for (int i = 0; i < n; i++) {
			squareDiffSum += (float) Math.pow(a[i] - avg, 2);
		}
		
		float sdFloat = (float) Math.sqrt(squareDiffSum / n);
		System.out.println("Single Precision: ");
		System.out.println("σ=" + sdFloat); 
		
		// Double Precision
		double avg2 = sum(a) / n;
		double squareDiffSum2 = 0;
		
		for (int i = 0; i < n; i++) {
			squareDiffSum2 += Math.pow(a[i] - avg2, 2);
		}

		double sdDouble = Math.sqrt(squareDiffSum2 / n);
		System.out.println("Double Precision: ");
		System.out.println("σ=" + sdDouble); 
	}
	  
	void method2() {
		// Single Precision
		float squareSum = 0;

		for (int i = 0; i < n; i++) {
			squareSum += (float) Math.pow(a[i], 2);
		}

		float sumSquared = (float) Math.pow(sum(a), 2);
		
		float sdFloat = (float) Math.sqrt((squareSum - sumSquared / n) / n);
		System.out.println("Single Precision: ");
		System.out.println("σ=" + sdFloat); 

		// Double Precision
		double squareSum2 = 0;

		for (int i = 0; i < n; i++) {
			squareSum2 += Math.pow(a[i], 2);
		}

		double sumSquared2 = Math.pow(sum(a), 2);
		
		double sdDouble = Math.sqrt((squareSum2 - sumSquared2 / n) / n);
		System.out.println("Double Precision: ");
		System.out.println("σ=" + sdDouble); 
	}

	public static int sum(int[] a) {
		int sum = 0;

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
			a[i] = rand.nextInt(n);
		}

		return a;
	}
	
	public static void main(String[] args) {
		int[] a = arrayOfRandInts(100);
		new StandardDeviation(100, 0, a); 
		new StandardDeviation(100, 0, arrayOfInts(100)); 
		new StandardDeviation(100, 10, a); 
		new StandardDeviation(100, 10000, a); 
		new StandardDeviation(100, 100000, a); 
		new StandardDeviation(100, -10000, a); 
		new StandardDeviation(100, 0); 
		new StandardDeviation(100, 10); 
		new StandardDeviation(100, 10000); 
		new StandardDeviation(100, 1000000); 
		new StandardDeviation(100, -10000); 
		new StandardDeviation(100, -1000000); 
		new StandardDeviation(1000, 0); 
		new StandardDeviation(10000, 0); 
		new StandardDeviation(100000, 0); 
		new StandardDeviation(1000000, 0); 
		new StandardDeviation(rand.nextInt(20), 0); 
		new StandardDeviation(rand.nextInt(1000000), 0); 
		new StandardDeviation(rand.nextInt(1000000), rand.nextInt(10)); 
		new StandardDeviation(rand.nextInt(1000000), rand.nextInt(100)); 
		new StandardDeviation(rand.nextInt(1000000), rand.nextInt(1000)); 
	}
}