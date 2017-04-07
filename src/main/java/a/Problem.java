/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a;
import static java.lang.Math.*;
/**
 *
 * @author nikolai
 */
public class Problem {

    public static final double LOC_X_LOW = -2;
    public static final double LOC_X_HIGH = 2;
    public static final double LOC_Y_LOW = -2;
    public static final double LOC_Y_HIGH = 2;
    public static final double VEL_LOW = -1;
    public static final double VEL_HIGH = 1;

    public static final double ERR_TOLERANCE = 1E-20; 

    public static double evaluate(Location location) {
        double result = 0;
        double x = location.getLoca()[0]; 
        double y = location.getLoca()[1];
        
        result = x * exp(-x*x-y*y);
          
        return result;
    }
}
