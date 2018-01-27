package numbers;

import java.util.*;

public class Identity{
    private static Random rand = new Random(2018);

    public static void runTest(double x, double y) {
        System.out.println(x + "\t" + y + "\t" + compute((float) x, (float) y) + "\t" + compute(x, y));
    }

    public static float compute(float x, float y) {
        return (((x / y) - (x * y)) * y + (x * y * y));
    }

    public static double compute(double x, double y) {
        return (((x / y) - (x * y)) * y + (x * y * y));
    }

    public static double randomX(int max) {
        return rand.nextDouble() * max;
    }

    public static double randomY(int max) {
        double y = randomX(max);

        while (y == 0.0) {
            y = randomX(max);
        }

        return y;
    }

    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Identity");
        System.out.println("x\ty\tSingle Precision RHS\tDouble Precision RHS");
        
        while (scanner.hasNextDouble()) {
            try {   
                runTest(scanner.nextDouble(), scanner.nextDouble());
            } catch (NumberFormatException e) {
                System.out.println("x and y must be floating point values.");
            } 
        } 

        for (int i = 1; i <= 10000000; i *= 10) {
            for (int j = 1; j <= 10000000; j *= 10) {
                runTest(randomX(i), randomY(j));
            }
        }
        
        scanner.close();
    }
}
