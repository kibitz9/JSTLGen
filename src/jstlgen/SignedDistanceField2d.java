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
public abstract class SignedDistanceField2d extends SignedDistanceField{

  

    public abstract SignedDistanceField2d Clone();
    
    
  
    public abstract double GetRawDistance(Vector2d translatedp);
    public double GetDistance(Vector2d p)
    {
        return GetRawDistance(p);
    }

//    public static Vector2d ComponentMax(Vector2d v, double z)
//    {
//        return new Vector2d(Max(v.X, z),Max(v.Y, z));
//    }
//    public static double Max(double one, double two)
//    {
//        return Math.Max(one, two);
//    }
//    public static double Min(double one, double two)
//    {
//        return Math.Min(one, two);
//    }
//    public static double Clamp(double value, double low, double high)
//    {
//        //return Math.Clamp(value, low, high);
//        if (value < low)
//        {
//            return low;
//        }
//        if (value > high)
//        {
//            return high;
//        }
//        return value;
//    }
    
    
//    protected static double clamp(double value, double min, double max){
//        if (value<min){
//            return min;
//        }
//        if (value>max){
//            return max;
//        }
//        return value;
//    }
//    protected static double min(double val1, double val2){
//        if (val1<val2){
//            return val1;
//        }
//        return val2;
//    }
//    protected static double max(double val1, double val2){
//        if (val1>val2){
//            return val1;
//        }
//        return val2;
//    }
   
    
   
    
    
    

}
