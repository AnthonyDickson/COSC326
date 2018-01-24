package numbers;

import java.util.*;

public class Identity{
    public static Random rand = new Random();
    public static double MAX_ERROR = 0.5;
    
    public static float calculate(float x,float y){
        return (((x / y) - (x * y)) * y + (x * y * y));
    }

    public static double calculate(double x,double y){
        return (((x / y) - (x * y)) * y + (x * y * y));
    }

    public static void testSP(float x, float y, int range) {
        System.out.println("\nSingle Precision:");
        System.out.println("x: " + x);
        System.out.println("y: " + y);
               
        float LHS = x;
        float RHS = calculate(x, y);
        double percentError = 100 * Math.abs(LHS - RHS) / ((Math.abs(LHS) + Math.abs(RHS)) / 2);
        
        System.out.println("LHS: " + LHS);
        System.out.println("RHS: " + RHS);
        System.out.println(String.format("Percent Error: %.2f%%", percentError));
        System.out.println("LHS == RHS: " + (percentError < MAX_ERROR));
    }
    
    public static void testDP(double x, double y, int range) {       
        System.out.println("\nDouble Precision:");
        System.out.println("x: " + x);
        System.out.println("y: " + y);

               
        double LHS = x;
        double RHS = calculate(x, y);
        double percentError = 100 * Math.abs(LHS - RHS) / (Math.abs(LHS) + Math.abs(RHS) / 2);
        
        System.out.println("LHS: " + LHS);
        System.out.println("RHS: " + RHS);
        System.out.println(String.format("Percent Error: %.2f%%", percentError));
        System.out.println("LHS == RHS: " + (percentError < MAX_ERROR));
    }

    public static void main(String args[]){
        try {
            int nTests = Integer.parseInt(args[0]);
            int range = (args.length > 1) ? Integer.parseInt(args[1]) : 100;
            double x = 0;
            double y = 1;
            int i = 0;

            if (args.length == 4) {
                x = Double.parseDouble(args[2]);
                y = Double.parseDouble(args[3]);
                
                testSP((float) x, (float) y, range);
                testDP(x, y, range);
            }
            
            while (i++ < nTests) {
                System.out.println("################################################################################");
                System.out.println("Test #" + i);
                
                x = rand.nextDouble() * 2 * range - range;
                y = rand.nextDouble() * 2 * range - range;

                while (y == 0) {
                    y = rand.nextDouble() * 2 * range - range;
                }
                
                testSP((float) x, (float) y, range);
                testDP(x, y, range);                
            }
        } catch (NumberFormatException e) {
            System.out.println("Usage: java numbers.Identity <# tests> [<range> [<x> <y>]]");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java numbers.Identity <# tests> [<range> [<x> <y>]]");
        }
    }
}
