package numbers;

import java.util.*;

public class Identity{
  /**
   * \brief   A Constructor to handle initialisation of member variables.
   */
  public Identity(){

  }

  public static float calculate(float x,float y){
    return (( (x/y) - (x*y) )*y + (x*y*y));
  }

  public static double calculate(double x,double y){
    return (( (x/y) - (x*y) )*y + (x*y*y));
  }

  public static void main(String args[]){
    String str = "float";
    float sX=2.54f,sY=3.5f;
    double dX=2.5848000000,dY=3.5234000000;
    if(args.length > 0){
      if(str.equals(args[0])){
        if(args.length > 1){
          sX = Float.parseFloat(args[1]);
        }
        if(args.length > 2){
          sY = Float.parseFloat(args[2]);
        }
      } else {
        if(args.length > 1){
          dX = Double.parseDouble(args[1]);
        }
        if(args.length > 2){
          dY = Double.parseDouble(args[2]);
        }
      }
    }
    Identity id = new Identity();
    System.out.println("Single Precision Input x = "+ sX + "  y = "+ sY);
    System.out.println("Single Precision Left Side: " + sX + "  Right Side: " + id.calculate(sX,sY) + "  Result: " + (sX==id.calculate(sX,sY)));
    System.out.println("Double Precision Input x = "+ dX + "  y = "+ dY);
    System.out.println("Double Precision Left Side: " + dX + "  Right Side: " + id.calculate(dX,dY) + "  Result: " + (dX==id.calculate(dX,dY)));
  }
}
