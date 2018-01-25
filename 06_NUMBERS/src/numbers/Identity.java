package numbers;

import java.util.*;

public class Identity{
    private static int nTests = 0;

    public static void runTest(double x, double y) {
        System.out.println("################################################################################");
        System.out.println("Test #" + ++nTests);  
        System.out.println("x: " + x);
        System.out.println("y: " + y); 
        
        float sp = compute((float) x, (float) y);
        double dp = compute(x, y);
        double diff = Percentages.difference(sp, dp);
        
        System.out.println("\nComparison of Single and Double Precision");
        System.out.println("Single Precision: " + sp);
        System.out.println("Double Precision: " + dp);
        System.out.println("Percent Difference: " + Percentages.getString(diff));
        System.out.println("agree: " + Percentages.isAcceptable(diff));
        
        System.out.println("\nComparison of LHS and RHS");
        testSP((float) x, (float) y);
		System.out.println("");
        testDP(x, y);         
    }

    public static float compute(float x, float y) {
        return (((x / y) - (x * y)) * y + (x * y * y));
    }

    public static double compute(double x, double y) {
        return (((x / y) - (x * y)) * y + (x * y * y));
    }

    public static <T> void testSP(float x, float y) {        
        float LHS = x;
        float RHS = compute(x, y);
        double error = Percentages.error(LHS, RHS);
        
        System.out.println("Single Precision");
        System.out.println("LHS: " + LHS);
        System.out.println("RHS: " + RHS);
        System.out.println("Percent Error: " + Percentages.getString(error));
        System.out.println("agree: " + Percentages.isAcceptable(error));
    }
    
    public static void testDP(double x, double y) { 
        double LHS = x;
        double RHS = (((x / y) - (x * y)) * y + (x * y * y));
        double error = Percentages.error(LHS, RHS);
        
        System.out.println("Double Precision");
        System.out.println("LHS: " + LHS);
        System.out.println("RHS: " + RHS);
        System.out.println("Percent Error: " + Percentages.getString(error));
        System.out.println("agree: " + Percentages.isAcceptable(error));
    }

    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        
        while (scanner.hasNextDouble()) {
            try {   
                runTest(scanner.nextDouble(), scanner.nextDouble());
            } catch (NumberFormatException e) {
                System.out.println("x and y must be floating point values.");
            } 
        }    
        
        scanner.close();
    }
}
