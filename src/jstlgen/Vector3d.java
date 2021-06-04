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
public class Vector3d {
     public Vector3d(double x, double y, double z)
        {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        public double x;
        public double y;
        public double z;


        public void InPlaceUpdate(double x, double y, double z)
        {
             this.x = x;
            this.y = y;
            this.z = z;
            magsquaredrecip = null;
            RelatedVector = null;
            unitVector = null;
            magnitudeCalulated = false;
            magnitudesquaredcalculated = false;
        }

        public Vector3d RelatedVector;

       
       

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
        public Vector3d Clamp(Vector3d low,  Vector3d high)
        {
            double newx = clamp(this.x, low.x,high.x);
            double newy = clamp(this.y, low.y, high.y);
            double newz = clamp(this.z, low.z, high.z);

            return new Vector3d(newx, newy, newz);
        }
        
       
        public Vector3d GetComponentwiseRecipricol()
        {
                return new Vector3d(1.0 / this.x, 1.0 / this.y, 1.0 / this.z);
        }

        Vector3d magsquaredrecip = null;
        public Vector3d GetMagnitudeSquaredRecipricol()
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

        public Vector3d GetComponentwiseMax(double max)
        {
            return new Vector3d(Math.max(this.x, max), Math.max(this.y, max), Math.max(this.z, max));
        }

        public Vector3d Max(double max)
        {
            return GetComponentwiseMax(max);
        }
        
        public Vector3d Min(double min)
        {
            return ComponentwiseMin(min);
        }
        public double MaximumComponent()
        {
            return Math.max(this.x,Math.max(this.y,this.z));
        }
        public Vector3d ComponentwiseMin(double max)
        {
            return new Vector3d(Math.min(this.x, max), Math.min(this.y, max), Math.min(this.z, max));
        }
        public Vector2d GetXY()
        {
            
            return new Vector2d(x, y);
            
        }
        public Vector2d GetXZ()
        {
           
            return new Vector2d(x, z);
       
        }

        public Vector2d GetYZ()
        {
            return new Vector2d(y, z);
        }

        public Vector3d ShrinkWrap(SignedDistanceField3d sdf)
        {
            //move towards the origin the distance specified by sdf.
            double dist = sdf.GetDistance(this);
            dist *= .9999999;//avoid going inside.
            if (dist <= 0)
            {
                return this;
            }
            //Vector3d towardsOrigin = this.UnitVector.Negate().Scale(dist);

            //return this.Add(towardsOrigin);

            Vector3d towardsOrigin = this.GetUnitVector().Scale(dist);

            return this.Subtract(towardsOrigin);
        }

        public Vector3d Midpoint(Vector3d otherVector)
        {
            return Midpoint(this, otherVector);
        }
        public static Vector3d Midpoint(Vector3d one, Vector3d two)
        {
            //return two.Subtract(one).Scale(.5).Add(one);//these are the same thing
            return one.Add(two).Scale(.5);
        }

        public Vector3d Clone()
        {
            return new Vector3d(this.x, this.y, this.z);
        }
        public Vector3d ShrinkTowardsSlope(SignedDistanceField3d sdf, double epsilon)
        {
            Vector3d slope= sdf.GetSlope(this, epsilon);
            Vector3d normal = slope.GetUnitVector();
            double dist = sdf.GetDistance(this)*(1-epsilon);
            //bool inside = dist < 0;
            //if (inside)
            //{
            //    string brk = "";
            //}
            //dist = Math.Abs(dist);//since the normal changes direction too, we don't want to double negate.
            //doesn't seem to work inside at all - at least not for a torus test.
            Vector3d scaledMove = normal.Scale(dist).Negate();//we want to move in the opposite direction of the normal.
            return this.Add(scaledMove);
        }

        public void ShrinkTowardsSlopeNoBurn(SignedDistanceField3d sdf, double epsilon)
        {
            Vector3d slope = sdf.GetSlope(this, epsilon);
            Vector3d normal = slope.GetUnitVector();
            double dist = sdf.GetDistance(this) * (1 - epsilon);
            normal.ScaleNoBurn(dist);
            normal.NegateNoBurn();
            AddNoBurn(normal);
            magnitudeCalulated = false;
            magnitudesquaredcalculated = false;
        }
        public int InsideOrOutside(SignedDistanceField3d sdf)
        {
            //returns 1 for outside and -1 for inside;

            double dist = sdf.GetDistance(this);
            if (dist < 0)
            {
                return -1;
            }
            return 1;
        }

        private static double insideoutsideEpsilon = 0;
        public int InsideOrOutside(SignedDistanceField3d sdf, double closeDist)
        {
            //returns 2 for completely outside and 1 for outside but close,
            //// -1 for inside but close, and -2 for completely inside;

            double dist = sdf.GetDistance(this)+ insideoutsideEpsilon;
            if (dist < 0)
            {
                if (dist < -closeDist)
                {
                    return -1; 
                }
                return -2;

            }
            if (dist < closeDist) { 
                return 1;
            }
            return 2;
        }
        public static double GetDistance(Vector3d first, Vector3d second)
        {
            return second.Subtract(first).GetMagnitude();
        }
        public double GetDistance(Vector3d other)
        {
            return GetDistance(this, other);
        }

        

        public static Vector3d GetGravitationalAttraction(Vector3d location, List<Vector3d> points)
        {
            Vector3d runningTotal = ORIGIN;
            for (Vector3d point : points)
            {
                Vector3d offset = point.Subtract(location);
                Vector3d x = offset.GetMagnitudeSquaredRecipricol();
                runningTotal = runningTotal.Add(x);
            }
            return runningTotal;
        }
        public Vector3d GravityShrinkWrap(SignedDistanceField3d sdf, List<Vector3d> gravityPoints, double ratioOfTotalDist)
        {
            //move towards the origin the distance specified by sdf.
            double dist = sdf.GetDistance(this);
            dist *= .9999999;//avoid going inside.
            if (dist <= 0)
            {
                return this;
            }

            Vector3d direction = GetGravitationalAttraction(this, gravityPoints).GetUnitVector();

            dist *= ratioOfTotalDist;

            return this.Add(direction.Scale(dist));
            //Vector3d towardsOrigin = this.UnitVector.Negate().Scale(dist);

            ////return this.Add(towardsOrigin);

            //Vector3d towardsOrigin = this.UnitVector.Scale(dist);

            //return this.Subtract(towardsOrigin);
        }
    
        public static Vector3d TryDirections(SignedDistanceField3d sdf, Vector3d startingPoint, double maxTravel, List<Vector3d> directions)
        {

            double dist = sdf.GetDistance(startingPoint);

            if (dist <= 0)
            {
                return startingPoint;
            }
            double bestDist = dist;
            Vector3d bestLocation = startingPoint;

            double step = Math.min(maxTravel, dist);//the smaller of the two. min and max confuse me.

            for(Vector3d v : directions)
            {
                Vector3d newVector = startingPoint.Add(v.Scale(step));

                double newDist = sdf.GetDistance(newVector);
                if (newDist >= 0 && newDist < bestDist)
                {
                    bestDist = newDist;
                    bestLocation = newVector;
                }
            }

            //finally, try directory towards object with slight favoring...
            Vector3d zz = startingPoint.Subtract(startingPoint.GetUnitVector().Scale(step));
            double zzDist = sdf.GetDistance(zz);
            zzDist *= .9;//the favoring
            if (zzDist < bestDist)
            {
                bestLocation = zz;
            }

            return bestLocation;
        }

        public Vector3d ShrinkWrap2(SignedDistanceField3d sdf, double maxTravel)
        {

            return TryDirections(sdf, this, maxTravel, Vector3d.MANYDIRECTIONS);
        }
        public Vector3d GridSnap(double gridSize)
        {
            
            double tempx = x;
            double tempy = y;
            double tempz = z;
            tempx /= gridSize;
            tempy /= gridSize;
            tempz /= gridSize;
            tempx = Math.floor(tempx);
            tempy = Math.floor(tempy);
            tempz = Math.floor(tempz);
            return new Vector3d(tempx, tempy, tempz);

        }
        public Vector3d PrecisionSnap(int zed)
        {
            int zed2 = zed;
            double xtemp = x;
            double ytemp = y;
            double ztemp = z;
            while (zed2-- > 0)
            {
                xtemp /= 2;
                ytemp /= 2;
                ztemp /= 2;
            }
            //xtemp = Math.Floor(xtemp);
           // ytemp = Math.Floor(ytemp);
           // ztemp = Math.Floor(ztemp);
            while (zed-- > 0)
            {
                xtemp *= 2;
                ytemp *= 2;
                ztemp *= 2;
            }
            return new Vector3d(xtemp, ytemp, ztemp);
        }
        public Vector3d Subtract(Vector3d toSubtract)
        {
            return new Vector3d(x - toSubtract.x,y - toSubtract.y, z - toSubtract.z);
        }
        public Vector3d Negate()
        {
            return new Vector3d(-x,-y, -z);
        }
        public void NegateNoBurn()
        {
            this.x = -x;
            this.y = -y;
            this.z = -z;
            RelatedVector = null;
            if (unitVector != null) {
                unitVector.NegateNoBurn();
            }
        }
        public Vector3d Add(Vector3d toAdd)
        {
            return this.Subtract(toAdd.Negate());
        }
        public void AddNoBurn(Vector3d toAdd)
        {
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


      
       
      

        private Vector3d unitVector = null;
        public Vector3d GetUnitVector()
        {
         
            if (this.unitVector == null)
            {
                double m = GetMagnitude();
                if (m == 0)
                {
                    return new Vector3d(1, 0, 0);//a kludge
                }
                unitVector= new Vector3d(this.x / m, this.y / m, this.z / m);
            }
            return unitVector;

        }


        private boolean magnitudesquaredcalculated = false;
        double magnitudeSquared = 0;
        
        public double GetMagnitudeSquared(){
                if (!magnitudesquaredcalculated)
                {
                    magnitudeSquared = x * x + y * y + z * z;
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
        public Vector3d (Vector3d toCopy)
        {
            this.x = toCopy.x;
            this.y = toCopy.y;
            this.z = toCopy.z;
        }
       
        
   
        public Vector3d Abs()
        {
            if (x >= 0)
            {
                if (y >= 0)
                {
                    if (z >= 0)
                    {
                        return this;
                    }
                    return new Vector3d(x, y, -z);
                }
                if (z > 0)
                {
                    return new Vector3d(x, -y, z);
                }
                return new Vector3d(x, -y, -z);
            }
            if (y >= 0)
            {
                if (z >= 0)
                {
                    return new Vector3d(-x,y,z);
                }
                return new Vector3d(-x, y, -z);
            }
            if (z > 0)
            {
                return new Vector3d(-x, -y, z);
            }
            return new Vector3d(-x, -y, -z);
           
        }

        
       
//        public String ToSTLString()
//        {
//            String.format
//            return String.format(x), os) .toString("e6") + " " + y.ToString("e6") + " " + z.ToString("e6");
//        }

        public byte[] ToBinary()
        {

            byte[] returnByte = new byte[12];
            int temp = Float.floatToIntBits((float)this.x);
            returnByte[0] = (byte)(temp&0xff);
            returnByte[1] = (byte)((temp>>8)&0xff);
            returnByte[2] = (byte)((temp>>16)&0xff);
            returnByte[3] = (byte)((temp>>24)&0xff);
            temp = Float.floatToIntBits((float)this.y);
            returnByte[4] = (byte)(temp&0xff);
            returnByte[5] = (byte)((temp>>8)&0xff);
            returnByte[6] = (byte)((temp>>16)&0xff);
            returnByte[7] = (byte)((temp>>24)&0xff);
            temp = Float.floatToIntBits((float)this.z);
            returnByte[8] = (byte)(temp&0xff);
            returnByte[9] = (byte)((temp>>8)&0xff);
            returnByte[10] = (byte)((temp>>16)&0xff);
            returnByte[11] = (byte)((temp>>24)&0xff);
            return returnByte;
        }

        public static Vector3d CrossProduct(Vector3d one, Vector3d two)
        {
            return new Vector3d(
                one.y*two.z - one.z*two.y,
                one.z*two.x - one.x*two.z,
                one.x*two.y - one.y*two.x

                );
        }

        public static boolean IntersectsSDF(SignedDistanceField3d sdf, Vector3d start, Vector3d end, double epsilon)
        {
            if (start.InsideOrOutside(sdf) < 0)
            {
                return true;
            }
            if (end.InsideOrOutside(sdf) < 0)
            {
                return true;
            }
            double pointDist = Math.abs(sdf.GetDistance(start));
            Vector3d startEndVector = end.Subtract(start);
           
            if (startEndVector.GetMagnitude() < pointDist)
            {
                return false;
            }

            startEndVector = startEndVector.GetUnitVector();
            while (pointDist > epsilon)
            {
                start = start.Add(startEndVector.Scale(pointDist));//raymarch along...
                pointDist = Math.abs(sdf.GetDistance(start));//get new distance
                Vector3d remaining = end.Subtract(start);//get new line length (remaining)...
                if (remaining.GetMagnitude() < pointDist)
                {
                    return false;
                }

            }
            return true;


        }

        public static double DotProduct(Vector3d one, Vector3d two)
        {
            return one.x * two.x + one.y * two.y + one.z * two.z;
        }

        public static Vector3d Scale(Vector3d one, double two)
        {
            return new Vector3d(one.x * two, one.y * two, one.z * two);
        }

      
        public Vector3d CrossProduct(Vector3d secondVector)
        {
            return CrossProduct(this, secondVector);
        }
        public double DotProduct(Vector3d secondVector)
        {
            return DotProduct(this, secondVector);
        }
        public Vector3d Scale(double scalar)
        {
            return Scale(this, scalar);
        }
        public void ScaleNoBurn(double scalar)
        {
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
        public Vector3d Translate(Vector3d t)
        {
            return this.Add(t);
        }

        public Vector3d Interpolate(Vector3d a, double t)
        {
            return new Vector3d(this.Add(a.Subtract(this).Scale(t)));
        }

        private static final double HalfSQRT2 = Math.sqrt(2) / 2;//did you know 1/sqrt(2) = sqrt(2)/2?
        private static final double OneOverSQRT3 = 1 / Math.sqrt(3);//did you know (1/sqrt(3))^3 = 1? 

        public static final Vector3d ORIGIN = new Vector3d(0, 0, 0);

        public static final Vector3d TOX = new Vector3d(1, 0, 0);
        public static final Vector3d FROMX = new Vector3d(-1, 0, 0);
        public static final Vector3d TOY = new Vector3d(0, 1, 0);
        public static final Vector3d FROMY = new Vector3d(0, -1, 0);
        public static final Vector3d TOZ = new Vector3d(0, 0, 1);
        public static final Vector3d FROMZ = new Vector3d(0, 0, -1);

        private static final Vector3d a = new Vector3d(HalfSQRT2, HalfSQRT2, 0);
        private static final Vector3d b = new Vector3d(-HalfSQRT2, HalfSQRT2, 0);
        private static final Vector3d c = new Vector3d(HalfSQRT2, -HalfSQRT2, 0);
        private static final Vector3d d = new Vector3d(-HalfSQRT2, -HalfSQRT2, 0);

        private static final Vector3d e = new Vector3d(0,HalfSQRT2, HalfSQRT2);
        private static final Vector3d f = new Vector3d(0,-HalfSQRT2, HalfSQRT2);
        private static final Vector3d g = new Vector3d(0,HalfSQRT2, -HalfSQRT2);
        private static final Vector3d h = new Vector3d(0,-HalfSQRT2, -HalfSQRT2);

        private static final Vector3d i = new Vector3d(HalfSQRT2, 0,HalfSQRT2);
        private static final Vector3d j = new Vector3d(-HalfSQRT2, 0,HalfSQRT2);
        private static final Vector3d k = new Vector3d(HalfSQRT2, 0,-HalfSQRT2);
        private static final Vector3d l = new Vector3d(-HalfSQRT2, 0,-HalfSQRT2);

        private static final Vector3d m = new Vector3d(OneOverSQRT3, OneOverSQRT3, OneOverSQRT3);
        private static final Vector3d n = new Vector3d(OneOverSQRT3, OneOverSQRT3, -OneOverSQRT3);
        private static final Vector3d o = new Vector3d(OneOverSQRT3, -OneOverSQRT3, OneOverSQRT3);
        private static final Vector3d p = new Vector3d(OneOverSQRT3, -OneOverSQRT3, -OneOverSQRT3);

        private static final Vector3d q = new Vector3d(-OneOverSQRT3, OneOverSQRT3, OneOverSQRT3);
        private static final Vector3d r = new Vector3d(-OneOverSQRT3, OneOverSQRT3, -OneOverSQRT3);
        private static final Vector3d s = new Vector3d(-OneOverSQRT3, -OneOverSQRT3, OneOverSQRT3);
        private static final Vector3d t = new Vector3d(-OneOverSQRT3, -OneOverSQRT3, -OneOverSQRT3);


       



        public static List<Vector3d> CARDINALDIRECTIONS = Arrays.asList(
            TOX,FROMX,TOY,FROMY,TOZ,FROMZ);
     
        
        public static List<Vector3d> MANYDIRECTIONS = Arrays.asList(
            TOX,FROMX,TOY,FROMY,TOZ,FROMZ
            ,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t
        );
        private static final double BIGSIZE = 10000000;

        public static Vector3d BIG_X = new Vector3d(BIGSIZE, 0, 0);
        public static Vector3d BIG_NEG_X = new Vector3d(-BIGSIZE, 0, 0);
        public static Vector3d BIG_Y = new Vector3d(0, BIGSIZE, 0);
        public static Vector3d BIG_NEG_Y = new Vector3d(0, -BIGSIZE, 0);
        public static Vector3d BIG_Z = new Vector3d(0, 0, BIGSIZE);
        public static Vector3d BIG_NEG_Z = new Vector3d(0, 0, -BIGSIZE);
}
