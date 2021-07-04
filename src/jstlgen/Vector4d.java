/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen;
import java.math.*;
import java.util.Arrays;
import java.util.List;
/**
 *
 * @author Christopher.Miller
 */
public class Vector4d {
    public Vector4d(double x, double y, double z,double w)
       {
           this.x = x;
           this.y = y;
           this.z = z;
           this.w = w;
       }
       public double x;
       public double y;
       public double z;
       public double w;

       public void InPlaceUpdate(double x, double y, double z, double w)
       {
           this.x = x;
           this.y = y;
           this.z = z;
           this.w = w;
           magsquaredrecip = null;
           RelatedVector = null;
           unitVector = null;
           magnitudeCalulated = false;
           magnitudesquaredcalculated = false;
       }

       public Vector4d RelatedVector;




       private double clamp(double v, double low, double high)
       {
           if (high < low)
           {
               double temp = low;
               low = high;
               high = temp;
           }
           if (v < low)
           {
               v = low;
           }
           else if (v > high)
           {
               v = high;
           }
           return v;
       }
       public Vector4d Clamp(Vector4d low,  Vector4d high)
       {
           double newx = clamp(this.x, low.x,high.x);
           double newy = clamp(this.y, low.y, high.y);
           double newz = clamp(this.z, low.z, high.z);
           double neww = clamp(this.w, low.w, high.w);

           return new Vector4d(newx, newy, newz, neww);
       }


       public Vector4d GetComponentwiseRecipricol()
       {
               return new Vector4d(1.0 / this.x, 1.0 / this.y, 1.0/this.z, 1.0/w);
       }

       Vector4d magsquaredrecip = null;
       public Vector4d GetMagnitudeSquaredRecipricol()
       {

               if (magsquaredrecip == null)
               {
                   double magsquared = this.GetMagnitudeSquared();
                   double recip = 1 / magsquared;
                   magsquaredrecip = this.GetUnitVector().Scale(recip);
               }

               return magsquaredrecip;


       }
    //        public Vector3d Round()
    //        {
    //            //round the components
    //            double newX = Math.Round(x);
    //            double newY = Math.Round(y);
    //            double newZ = Math.Round(z);
    //            return new Vector3d(newX, newY, newZ);
    //        }
       //public Vector3d Rotate(Vector3d rotation)
       //{
       //    Quaternion q = new Quaternion(this);
       //    return Rotate(q);
       //}

    //        public Vector3d Rotate(BiComplex q)
    //        {
    //            BiComplex r = new BiComplex(this);
    //            return new Vector3d(r.Multiply(q));
    //        }
    //        public Vector3d Rotate(Vector3d rotation)
    //        {
    //            BiComplex q = new BiComplex(this);
    //            return Rotate(q);
    //        }

       public Vector4d ComponentwiseMax(double max)
       {
           return new Vector4d(Math.max(this.x, max), Math.max(this.y, max), Math.max(this.z, max),Math.max(this.w, max));
       }

       public Vector4d Max(double max)
       {
           return ComponentwiseMax(max);
       }

       public Vector4d Min(double min)
       {
           return ComponentwiseMin(min);
       }
       public double MaximumComponent()
       {
           return Math.max(this.w,Math.max(this.x,Math.max(this.y,this.z)));
       }
       public Vector4d ComponentwiseMin(double max)
       {
           return new Vector4d(Math.min(this.x, max), Math.min(this.y, max), Math.min(this.z, max),Math.min(this.w,max));
       }
       public Vector2d GetXY()
       {
           return new Vector2d(x, y); 
       }
        public Vector2d GetYX()
       {
           return new Vector2d(y, x); 
       }
       public Vector2d GetXZ()
       {
           return new Vector2d(x, z);
       }
       public Vector2d GetZX()
       {
           return new Vector2d(z, x);
       }

       public Vector2d GetYZ()
       {
           return new Vector2d(y, z);
       }
       public Vector2d GetZY()
       {
           return new Vector2d(z, y);
       }
       public Vector2d GetWX()
       {
           return new Vector2d(w, x);
       }
       public Vector2d GetXW()
       {
           return new Vector2d(x, w);
       }
       public Vector2d GetWY()
       {
           return new Vector2d(w, y);
       }
       public Vector2d GetYW()
       {
           return new Vector2d(y, w);
       }
       public Vector2d GetWZ()
       {
           return new Vector2d(w, z);
       }
       public Vector2d GetZW()
       {
           return new Vector2d(z, w);
       }

       public Vector3d GetWXY(){
           return new Vector3d(w,x,y);
       }
       public Vector3d GetWYX(){
           return new Vector3d(w,y,x);
       }
       public Vector3d GetYWX(){
           return new Vector3d(y,w,x);
       }
       public Vector3d GetYXW(){
           return new Vector3d(y,x,w);
       }
       public Vector3d GetXYW(){
           return new Vector3d(x,y,w);
       }
       public Vector3d GetXWY(){
           return new Vector3d(x,w,y);
       }
       //more to do here.

       public Vector3d GetWYZ(){
           return new Vector3d(w,y,z);
       }
       public Vector3d GetWZX(){
           return new Vector3d(w,z,x);
       }
       public Vector3d GetXYZ(){
           return new Vector3d(x,y,z);
       }

       public Vector3d GETWWW(){
           return new Vector3d(w,w,w);
       }



       public Vector4d Midpoint(Vector4d otherVector)
       {
           return Midpoint(this, otherVector);
       }
       public static Vector4d Midpoint(Vector4d one, Vector4d two)
       {
           //return two.Subtract(one).Scale(.5).Add(one);//these are the same thing
           return one.Add(two).Scale(.5);
       }

       public Vector4d Clone()
       {
           return new Vector4d(this.x, this.y, this.z, this.w);
       }



       public static double GetDistance(Vector4d first, Vector4d second)
       {
           return second.Subtract(first).GetMagnitude();
       }
       public double GetDistance(Vector4d other)
       {
           return GetDistance(this, other);
       }




       public Vector4d Subtract(Vector4d toSubtract)
       {
           return new Vector4d(x - toSubtract.x,y - toSubtract.y, z - toSubtract.z,w-toSubtract.w);
       }
       public Vector4d Negate()
       {
           return new Vector4d(-x,-y, -z,-w);
       }
       public void NegateNoBurn()
       {
           this.w = -w;
           this.x = -x;
           this.y = -y;
           this.z = -z;
           RelatedVector = null;
           if (unitVector != null) {
               unitVector.NegateNoBurn();
           }
       }
       public Vector4d Add(Vector4d toAdd)
       {
           return this.Subtract(toAdd.Negate());
       }
       public void AddNoBurn(Vector4d toAdd)
       {
           this.w += toAdd.w;
           this.x += toAdd.x;
           this.y += toAdd.y;
           this.z += toAdd.z;
           magsquaredrecip = null;
           RelatedVector = null;
           unitVector = null;
       }
       //public Vector3d Midpoint(Vector3d otherSide)
       //{
       //    //return the midpoint between two vectors - the average of two vectors.
       //    return new Vector3d(
       //        Util.Round(this.x + otherSide.x) / 2,
       //        Util.Round(this.y + otherSide.y) / 2,
       //        Util.Round(this.z + otherSide.z) / 2
       //    );






       private Vector4d unitVector = null;
       public Vector4d GetUnitVector()
       {

           if (this.unitVector == null)
           {
               double m = GetMagnitude();
               if (m == 0)
               {
                   return new Vector4d(1, 0, 0,0);//a kludge
               }
               unitVector= new Vector4d(this.x / m, this.y / m, this.z / m,this.w/m);
           }
           return unitVector;

       }


       private boolean magnitudesquaredcalculated = false;
       double magnitudeSquared = 0;

       public double GetMagnitudeSquared(){
               if (!magnitudesquaredcalculated)
               {
                   magnitudeSquared = w * w + x * x + y * y + z * z;
                   magnitudesquaredcalculated = true;
               }
               return magnitudeSquared;
               //return x * x + y * y + z * z;

       }
       private boolean magnitudeCalulated = false;
       private double magnitude = 0;
       public double GetMagnitude(){

           if (!magnitudeCalulated)
           {
               magnitude = Math.sqrt(GetMagnitudeSquared());
               magnitudeCalulated = true;
           }
           return magnitude;

       }
       public Vector4d (Vector4d toCopy)
       {
           this.w = toCopy.w;
           this.x = toCopy.x;
           this.y = toCopy.y;
           this.z = toCopy.z;
       }



       public Vector4d Abs()
       {
           return new Vector4d(Math.abs(x),Math.abs(y),Math.abs(z), Math.abs(w));
       }



    //        public String ToSTLString()
    //        {
    //            String.format
    //            return String.format(x), os) .toString("e6") + " " + y.ToString("e6") + " " + z.ToString("e6");
    //        }

    //        public byte[] ToBinary()
    //        {
    //
    //            byte[] returnByte = new byte[12];
    //            int temp = Float.floatToIntBits((float)this.x);
    //            returnByte[0] = (byte)(temp&0xff);
    //            returnByte[1] = (byte)((temp>>8)&0xff);
    //            returnByte[2] = (byte)((temp>>16)&0xff);
    //            returnByte[3] = (byte)((temp>>24)&0xff);
    //            temp = Float.floatToIntBits((float)this.y);
    //            returnByte[4] = (byte)(temp&0xff);
    //            returnByte[5] = (byte)((temp>>8)&0xff);
    //            returnByte[6] = (byte)((temp>>16)&0xff);
    //            returnByte[7] = (byte)((temp>>24)&0xff);
    //            temp = Float.floatToIntBits((float)this.z);
    //            returnByte[8] = (byte)(temp&0xff);
    //            returnByte[9] = (byte)((temp>>8)&0xff);
    //            returnByte[10] = (byte)((temp>>16)&0xff);
    //            returnByte[11] = (byte)((temp>>24)&0xff);
    //            return returnByte;
    //        }



       public static double DotProduct(Vector4d one, Vector4d two)
       {
           return one.w*two.w+one.x * two.x + one.y * two.y + one.z * two.z;
       }

       public static Vector4d Scale(Vector4d one, double two)
       {
           return new Vector4d(one.x * two, one.y * two, one.z * two, one.w*two);
       }



       public Vector4d(Vector3d t, double w){
           this.x=t.x;
           this.y=t.y;
           this.z=t.z;
           this.w=w;
       }
       
       public double DotProduct(Vector4d secondVector)
       {
           return DotProduct(this, secondVector);
       }
       public Vector4d Scale(double scalar)
       {
           return Scale(this, scalar);
       }
       public Vector4d InverseScale(double scalar){
           return Scale(this, 1.0/scalar);
       }
       public Vector4d Multiply(double scalar){
           return Scale(this, scalar);
       }
       public Vector4d Divide(double scalar){
           return Scale(this, 1.0/scalar);
       }
       public void ScaleNoBurn(double scalar)
       {
           this.w *= scalar;
           this.x *= scalar;
           this.y *= scalar;
           this.z *= scalar;
           magsquaredrecip = null;
           RelatedVector = null;
           magnitudeCalulated = false;
           magnitudesquaredcalculated = false;
           //unit vector should still be valid!
           //return this;
       }
       public Vector4d Translate(Vector4d t)
       {
           return this.Add(t);
       }

       public Vector4d Interpolate(Vector4d a, double t)
       {
           return new Vector4d(this.Add(a.Subtract(this).Scale(t)));
       }

       private int min(int one, int two){
           if (one<two){
               return one;
           }
           return two;
       }
       public int ToARGBInt(){

        int r = min((int)(x*255),255);
        int g = min((int)(y*255),255);
        int b = min((int)(z*255),255);
        int a = min((int)(w*255),255);
        int color = (a<<24)&0xff000000;
        color +=(r<<16)&0x00ff0000;
        color +=(g<<8)&0x0000ff00;
        color +=b&0x000000ff;
        return color;
    }
      
}
