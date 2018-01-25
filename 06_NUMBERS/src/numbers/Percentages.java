package numbers;

public class Percentages {
    public static final double MAX_PERCENT_DIFF = 5.0;
    
    /**
     * Computes the percent differnce.
     * 
     * @param a The first number to compare.
     * @param b The other number to compare.
     * @return the difference as a percentage.
     */
    public static double difference(double a, double b) {
        if (a == 0 && b == 0) {
            return 0.0;
        }

        return 100 * Math.abs(a - b) / Math.abs((a + b) / 2);
    }

    /**
     * Computes the percent error.
     * 
     * @param t Theoretical value, the expected result.
     * @param e Experimental value, the observed result.
     * @return the error as a percentage.
     */
    public static double error(double t, double e) {
        if (t == 0 && e == 0) {
            return 0.0;
        }

        return 100 * Math.abs((t - e) / t);
    }

    /**
     * Checks if the value is within the predifined acceptable range.
     * 
     * @param p The percentage to check.
     * @return true if the value is within the acceptable range, false otherwise.
     */
    public static boolean isAcceptable(double p) {
        return p < MAX_PERCENT_DIFF;
    }

    /**
     * Returns a formatted string of the percentage.
     * Percentages less than 0.01 are displayed as '< 0.01 %'.
     * 
     * @param p The percentage to format.
     * @return the formatted string of the percentage.
     */
    public static String getString(double p) {
        if (p >= 0.01) {
            return String.format("%.2f%%", p);
        } else if (p > 0) {
            return "< 0.01%";
        }

        return "0.00%";
    }

}