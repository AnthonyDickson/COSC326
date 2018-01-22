package numbers;

import java.util.*;

public class StandardDeviation {
  static double number;
  static double fixedValue;

  public static double sumOfValue() {
    double total = 0.0;

    for(double i = 1.0; i <= number; i++) {
      total += i;
    }

    return total;
  }

  public static double sumOfSquare() {
    double total = 0.0;
    for (double i = 1.0; i <= number; i++) {
      total += Math.pow(i, 2);
    }

    return total;
  }

  public static double sovFixed() {
    double total = 0.0;

    for(double i = 1.0; i <= number; i++){
      total += i;
      total += fixedValue;
    }

    return total;
  }

  public static double sosFixed() {
    double total = 0.0;

    for(double i = 1.0; i <= number; i++) {
      total += Math.pow((i + fixedValue), 2);
    }

    return total;
  }

  public static void method1() {
    float sMean = (float) sumOfValue() / (float) number;
    float sTotal = 0.0f;
    float sSd = 0.0f;

    double dMean = sumOfValue() / number;
    double dTotal = 0.0;
    double dSd = 0.0;

    for (double n = 1.0f;n <= number; n++) {
      sTotal += Math.pow((n - sMean),2);
      dTotal += Math.pow((n - dMean),2);
    }

    sSd = (float)Math.sqrt(sTotal / number);
    dSd = Math.sqrt(dTotal / number);
    System.out.println("Method-1 Output Single Precision: " + sSd + "  Double Precision: " + dSd);

    float sMeanFixed = (float) sovFixed() / (float) number;
    float sTotalFixed = 0.0f;
    float sSdFixed = 0.0f;

    double dMeanFixed = sovFixed() / number;
    double dTotalFixed = 0.0;
    double dSdFixed = 0.0;

    for (double n = 1.0f; n <= number; n++){
      sTotalFixed += Math.pow((n - sMeanFixed),2);
      dTotalFixed += Math.pow((n - dMeanFixed),2);
    }

    sSdFixed = (float) Math.sqrt(sTotalFixed / number);
    dSdFixed = Math.sqrt(dTotalFixed / number);
    System.out.println("Method-1 Output with added Fixed Value [ " + fixedValue + " ] Single Precision: " + sSdFixed + "  Double Precision: " + dSdFixed);
  }

  public static void method2() {
    double squaresSum = sumOfSquare();
    double sumsSquare = Math.pow(sumOfValue(), 2);
    float sSd = 0.0f;
    double dSd = 0.0;
    sSd = (float)Math.sqrt(((float) squaresSum - ((float) sumsSquare / (float) number)) / (float) number);
    dSd = Math.sqrt((squaresSum - (sumsSquare/number)) / number);
    System.out.println("Method-2 Output Single Precision: " + sSd + "  Double Precision:" + dSd);

    double squaresSumFixed = sosFixed();
    double sumsSquareFixed = Math.pow(sovFixed(),2);
    float sSdFixed = 0.0f;
    double dSdFixed = 0.0;
    sSdFixed = (float) Math.sqrt(((float) squaresSumFixed - ((float) sumsSquareFixed / (float) number)) / (float) number);
    dSdFixed = Math.sqrt((squaresSumFixed - (sumsSquareFixed/number)) / number);
    System.out.println("Method-2 Output with added Fixed Value [ " + fixedValue + " ] Single Precision: " + sSdFixed + "  Double Precision:" + dSdFixed);

  }

  public static void main(String args[]) {
    StandardDeviation.number = 10000;
    StandardDeviation.fixedValue = 1;

    if(args.length > 0) {
      StandardDeviation.number = Double.parseDouble(args[0]);
    }

    if(args.length > 1) {
      StandardDeviation.fixedValue = Double.parseDouble(args[1]);
    }

    StandardDeviation.method1();
    StandardDeviation.method2();
  }
}
