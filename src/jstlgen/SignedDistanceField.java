/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen;

/**
 *
 * @author cmiller
 */
public class SignedDistanceField {
    
    
    protected static final double RAD360 = Math.PI*2.0;
    protected static final double RAD180 = Math.PI;
    protected static final double RAD90 = RAD180/2.0;
    protected static final double RAD45 = RAD180/4.0;
    protected static final double RAD1 = RAD360/360;
    protected static final double RAD10 = RAD360/36;
    
    
    public double GetScaledFrac(double n, double width)
    {
        double m = n / width;
        m = Math.round(m)*width;
        double o = n - m;

        return o;

    }
    
    
    protected static double sin(double angle){
        return Math.sin(angle);
    }
    protected static double cos(double angle){
        return Math.cos(angle);
    }
    
    protected static double sign(double number){
        return Math.signum(number);
    }
    //3d helpers
    protected static double clamp(double value, double min, double max){
        if (value<min){
            return min;
        }
        if (value>max){
            return max;
        }
        return value;
    }
    protected static double min(double val1, double val2){
        if (val1<val2){
            return val1;
        }
        return val2;
    }
    protected static double max(double val1, double val2){
        if (val1>val2){
            return val1;
        }
        return val2;
    }
    protected static Vector3d max(Vector3d vect, double val){
        return vect.ComponentwiseMax(val);
    }
    protected static double max(Vector3d vect){
        return vect.MaximumComponent();
    }
    protected static double length(Vector3d vect){
        return vect.GetMagnitude();
    }
    protected static Vector3d abs(Vector3d vect){
        return vect.Abs();
    }
    protected static double abs(double val){
        return Math.abs(val);
    }
    protected static double sqrt(double val){
        return Math.sqrt(val);
    }
    
    //2d helpers
      
   
    protected static Vector2d max(Vector2d vect, double val){
        return vect.ComponentwiseMax(val);
    }
    protected static double max(Vector2d vect){
        return vect.MaximumComponent();
    }
    protected static double length(Vector2d vect){
        return vect.GetMagnitude();
    }
    protected static Vector2d abs(Vector2d vect){
        return vect.Abs();
    }
   
}