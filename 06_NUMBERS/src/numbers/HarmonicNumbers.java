package numbers;

import java.util.*;

/**
 * This is a Numbers class which contains methods and variable to
 * observe how computer stores and handle different numbers.
 */
public class HarmonicNumbers {
  public static void main(String args[]) {
    int n = 10000;

    if(args.length > 0){
      n = Integer.parseInt(args[0]);
    }

    float sTotal = 0;
    double dTotal = 0;

    for(int i = 1; i <= n; i++){
      sTotal += 1/(float)i;
      dTotal += 1/(double)i;
    }

    System.out.println("Ascending Order Ouput of Harmonic Series for n = " + n);
    System.out.println("Single Precision: " + sTotal);
    System.out.println("Double Precision: " + dTotal);

    float sTotalInv = 0;
    float dTotalInv = 0;

    for(int i = n ; i >= 1; i--){
      sTotalInv +=1/(float)i;
      dTotalInv += 1/(double)i;
    }

    System.out.println("Descending Order Ouput of Harmonic Series for n = " + n);
    System.out.println("Single Precision: " + sTotalInv);
    System.out.println("Double Precision: " + dTotalInv);
  }
}
