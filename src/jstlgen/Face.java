/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen;
import java.util.*;
/**
 *
 * @author Christopher.Miller
 */
public class Face {
    private static double minimumAreaSquared = .00000000001;
    public Vector3d one;
    public Vector3d two;
    public Vector3d three;
    private Vector3d normal=null;
    private double areaSquared;
    private boolean areaCalulated = false;
    private boolean hasNoArea = false;
    //
    public Face(Vector3d one, Vector3d two, Vector3d three)
    {
         this(one, two, three, null);
        //this.normal = surfaceNormal();//let's lazy load this.
    }
    
    
    public Face(Face toClone){
        this.one = toClone.one.Clone();
        this.two=toClone.two.Clone();
        this.three=toClone.three.Clone();
    }
    public double Min(double one, double two)
    {
        if (one < two)
        {
            return one;
        }
        return two;
    }


    public double Max(double one, double two)
    {
        if (one > two)
        {
            return one;
        }
        return two;
    }

    public Line GetBoundingBox()
    {
        double minx = Double.MAX_VALUE;
        double miny = Double.MAX_VALUE;
        double minz = Double.MAX_VALUE;

        double maxx = Double.MIN_VALUE;
        double maxy = Double.MIN_VALUE;
        double maxz = Double.MIN_VALUE;
        minx = Min(minx, one.x);
        minx = Min(minx, two.x);
        minx = Min(minx, three.x);

        miny = Min(miny, one.y);
        miny = Min(miny, two.y);
        miny = Min(miny, three.y);

        minz = Min(minz, one.z);
        minz = Min(minz, two.z);
        minz = Min(minz, three.z);

        maxx = Max(maxx, one.x);
        maxx = Max(maxx, two.x);
        maxx = Max(maxx, three.x);

        maxy = Max(maxy, one.y);
        maxy = Max(maxy, two.y);
        maxy = Max(maxy, three.y);

        maxz = Max(maxz, one.z);
        maxz = Max(maxz, two.z);
        maxz = Max(maxz, three.z);

        return new Line(new Vector3d(minx, miny, minz), new Vector3d(maxx, maxy, maxz));


    }
        private void calculateAreaSquared()
        {
            double a = one.GetMagnitude();
            double b = two.GetMagnitude();
            double c = three.GetMagnitude();
            double s = (a + b + c) / 2;//semiperimiter
            double aa = s - a;
            double bb = s - b;
            double cc = s - c;
            double d = aa * bb * cc;
            this.areaSquared = s * d;

            if (one == two || one == three || two == three)
            {
                hasNoArea = true;
                //throw new Exception("Zero Area Triangle");

            }
            else
            {
                calculateAreaSquared();
                if (this.areaSquared < minimumAreaSquared)
                {
                    this.areaSquared = 0;
                    this.hasNoArea = true;
                }
            }

        }


        private Face(Vector3d one, Vector3d two, Vector3d three, Vector3d normal)
        {
            this.one = one;
            this.two = two;
            this.three = three;
            this.normal = normal;
          

        }

        public Face Rotate()
        {
            return new Face(two, three, one, normal);
        }


      
        public Face Scale(Vector3d origin, double scale)
        {
            if (origin == Vector3d.ORIGIN)
            {
                return simpleScale(scale);
            }
            else
            {
                Face temp = this.Translate(origin.Negate());
                temp = temp.simpleScale(scale);
                temp = temp.Translate(origin);
                return temp;
            }
        }
//        public boolean HasNoArea
//        {
//            get
//            {
//                if (!this.areaCalulated)
//                {
//                    calculateAreaSquared();
//                    this.areaCalulated = true;
//                }
//                return hasNoArea;
//            }
//        }
        private Face simpleScale(double scale)
        {
            return new Face(one.Scale(scale), two.Scale(scale), three.Scale(scale));
        }
        public Face Flip()
        {
            return new Face(this.one, this.three, this.two);
        }
        public Face Translate(Vector3d v)
        {
            //return new Face(this.One.Add(v), this.Two.Add(v), this.Three.Add(v));
            return new Face(this.one.Translate(v), this.two.Translate(v), this.three.Translate(v));
        }

        public Face(double onex, double oney, double onez, double twox, double twoy, double twoz, double threex, double threey, double threez)
        {
            this.one = new Vector3d(onex, oney, onez);
            this.two = new Vector3d(twox, twoy, twoz);
            this.three = new Vector3d(threex, threey, threez);
            //this.normal = surfaceNormal();.et's lazy load this...

            //if (one == two || one == three || two == three)
            //{
            //    hasNoArea = true;
            //    throw new Exception("Zero Area Triangle");
            //}

        }

        public static boolean IsSameFace(Face one, Face two)
        {
            if (one.one == two.one && one.two == two.two && one.three == two.three)
            {
                return true;
            }
            if (one.two == two.one && one.three == two.two && one.one == two.three)
            {
                return true;
            }
            if (one.three == two.one && one.one == two.two && one.two == two.three)
            {
                return true;
            }
            return false;
        }
        public boolean IsSameFace(Face other)
        {
            return IsSameFace(this, other);
        }
        public Face Join2OneTwo(Vector3d newPoint)
        {

            Face returnFace = new Face(newPoint, this.two, this.one);
            return returnFace;
        }

        public Face Join2TwoThree(Vector3d newPoint)
        {

            Face returnFace = new Face(newPoint, this.three, this.two);
            return returnFace;
        }

        public Face Join2ThreeOne(Vector3d newPoint)
        {

            Face returnFace = new Face(newPoint, this.one, this.three);
            return returnFace;
        }


        public Vector3d GetNormal()
        {
           
            if (normal == null)
            {
                normal = surfaceNormal();
            }
            return normal;

        }


        private Vector3d surfaceNormal()
        {
            Vector3d V = two.Subtract(one);
            Vector3d W = three.Subtract(one);
            return Vector3d.CrossProduct(V, W).GetUnitVector();
            //double newX = (V.Y * W.Z) - (V.Z * W.Y);
            //double newY = (V.Z * W.X) - (V.X * W.Z);
            //double newZ = (V.X * W.Y) - (V.Y * W.X);
            //return new Vector(newX, newY, newZ);
        }

        public double GetSDFTension(SignedDistanceField3d d, double epsilon)
        {
            Vector3d s1 = d.GetSlope(this.one, epsilon).GetUnitVector();
            Vector3d s2 = d.GetSlope(this.two, epsilon).GetUnitVector();
            Vector3d s3 = d.GetSlope(this.three, epsilon).GetUnitVector();
            double d12 = s1.Subtract(s2).GetMagnitude();
            double d23 = s2.Subtract(s3).GetMagnitude();
            double d31 = s3.Subtract(s1).GetMagnitude();
            return Math.max(d12, Math.max(d23, d31));

        }
        private static boolean advanced = true;
        private static double midPointDistanceSplitDist=.001;
        private static boolean centerDistanceOnly=true;
        private static boolean advanced2 = false;
        
        private double GetSDFTension (SignedDistanceField3d sdf, Vector3d a, Vector3d b, double epsilon)
        {
            
            if (advanced2){
                Vector3d sa = sdf.GetSlope(a, epsilon).GetUnitVector();
                Vector3d sb = sdf.GetSlope(b, epsilon).GetUnitVector();
                Vector3d sc = sdf.GetSlope(a.Midpoint(b), epsilon).GetUnitVector();
                
                double returnValue1 = Math.abs(sa.Subtract(sb).GetMagnitude());//original return value.
                double returnValue2 = Math.abs(sb.Subtract(sc).GetMagnitude());
                double returnValue3 = Math.abs(sc.Subtract(sa).GetMagnitude());
                return returnValue1+returnValue2+returnValue3;
                
                
            }      
            
            if (advanced){
                
                Vector3d midpoint = a.Midpoint(b);
//               
//                double dist3 = Math.abs(sdf.GetDistance(midpoint));
//                if (dist3>midPointDistanceSplitDist){
//                    return 10;
//                }
                //return dist3;
//                double returnValue4=0;
//                if (dist3>midPointDistanceSplitRatio*dist1||dist3>midPointDistanceSplitRatio*dist2){
//                    returnValue4=2;
//                }
//                if (centerDistanceOnly){
//                    return returnValue4;
//                }

                Vector3d sa = sdf.GetSlope(a, epsilon).GetUnitVector();
                Vector3d sb = sdf.GetSlope(b, epsilon).GetUnitVector();
                Vector3d smid = sdf.GetSlope(b.Midpoint(a),epsilon).GetUnitVector();

                double returnValue1 = Math.abs(sa.Subtract(sb).GetMagnitude());//original return value.
                double returnValue2 = Math.abs(sa.Subtract(smid).GetMagnitude());
                double returnValue3 = Math.abs(sb.Subtract(smid).GetMagnitude());
                return returnValue1+returnValue2+returnValue3;
            }
            
            Vector3d sa = sdf.GetSlope(a, epsilon).GetUnitVector();
            Vector3d sb = sdf.GetSlope(b, epsilon).GetUnitVector();
            return Math.abs(sa.Subtract(sb).GetMagnitude());
            
        }

        
        
        public Face[] SideSplitOnTension(SignedDistanceField3d sdf, double epsilon, double threshold)
        {
            boolean splitOneTwo = GetSDFTension(sdf, one, two, epsilon) > threshold;
            boolean splitTwoThree = GetSDFTension(sdf, two, three, epsilon) > threshold;
            boolean splitThreeOne = GetSDFTension(sdf, three, one, epsilon) > threshold;

            if (!splitOneTwo && !splitTwoThree && !splitThreeOne)
            {
                return new Face[] { this };
            }
            if (splitOneTwo && splitTwoThree && splitThreeOne)
            {
                Vector3d m = one.Midpoint(two);
                Vector3d n = two.Midpoint(three);
                Vector3d o = three.Midpoint(one);

                return new Face[]
                {
                    new Face(one,m,o),
                    new Face(m,two,n),
                    new Face(m,n,o),
                    new Face(o,n,three),
                };
            }
            if ((splitOneTwo && splitTwoThree) || (splitTwoThree && splitThreeOne) || (splitThreeOne && splitOneTwo))
            {
                Vector3d t1 = one;
                Vector3d t2 = two;
                Vector3d t3 = three;
                //align unbroken line to between t2 and t3.
                if (!splitOneTwo)
                {
                    Vector3d temp = t1;
                    t1 = t3;
                    t3 = t2;
                    t2 = temp;
                }
                else if (!splitThreeOne)
                {
                    Vector3d temp = t1;
                    t1 = t2;
                    t2 = t3;
                    t3 = temp;
                }

                //now split...
                Vector3d m = t1.Midpoint(t2);
                Vector3d n = t1.Midpoint(t3);
                double m1 = n.Subtract(t2).GetMagnitudeSquared();
                double m2 = m.Subtract(t3).GetMagnitudeSquared();
                if (m1>m2){
                     return new Face[]
                    {
                        new Face(t1,m,n),
                        new Face(m,t3,n),
                        new Face(m,t2,t3),
                    };
                }
                else{
                    return new Face[]
                    {
                        new Face(t1,m,n),
                        new Face(m,t2,n),
                        new Face(n,t2,t3),
                    };
                }


            }
            //align broken line between tt1 and tt2...
            Vector3d tt1 = one;
            Vector3d tt2 = two;
            Vector3d tt3 = three;

            if (splitTwoThree)
            {
                Vector3d temp = tt1;
                tt1 = tt2;
                tt2 = tt3;
                tt3 = temp;
            }
            if (splitThreeOne)
            {
                Vector3d temp = tt1;
                tt1 = tt3;
                tt3 = tt2;
                tt2 = temp;
            }

            Vector3d m7 = tt1.Midpoint(tt2);
            return new Face[]
            {
                new Face(tt1,m7,tt3),
                new Face(m7,tt2,tt3)
            };
        }

        
       
        public List<Face> CenterSplitOnTension(SignedDistanceField3d sdf, double epsilon, double threshold)
        {
            double tension = GetSDFTension(sdf, epsilon);
            if (tension > threshold)
            {
                return CenterSplit();
            }
            return Arrays.asList(
                this
            );
        }

        public List<Face> CenterSplit()
        {
            Vector3d center = this.GetCenter();
            return Arrays.asList(
            
                new Face(center,one,two),
                new Face(center,two,three),
                new Face(center,three,one)


            );
            
        }


        public Face ShrinkTowardsSlope(SignedDistanceField3d sdf, double epsilon)
        {
            return new Face(one.ShrinkTowardsSlope(sdf, epsilon), two.ShrinkTowardsSlope(sdf, epsilon), three.ShrinkTowardsSlope(sdf, epsilon));

        }
        public void ShrinkTowardsSlopeNoBurn(SignedDistanceField3d sdf, double epsilon)
        {
            one.ShrinkTowardsSlopeNoBurn(sdf, epsilon);
            two.ShrinkTowardsSlopeNoBurn(sdf, epsilon);
            three.ShrinkTowardsSlopeNoBurn(sdf, epsilon);

            //sss return new Face(One.ShrinkTowardsSlope(sdf, epsilon), Two.ShrinkTowardsSlope(sdf, epsilon), Three.ShrinkTowardsSlope(sdf, epsilon));
        }
        public Face ShrinkWrap(SignedDistanceField3d sdf)
        {
            return new Face(one.ShrinkWrap(sdf), two.ShrinkWrap(sdf), three.ShrinkWrap(sdf));
        }

        public Face GravityShrinkWrap(SignedDistanceField3d sdf, List<Vector3d> gravityPoints, double ratioOfDistanceToTravel)
        {
            return new Face(one.GravityShrinkWrap(sdf, gravityPoints, ratioOfDistanceToTravel), two.GravityShrinkWrap(sdf, gravityPoints, ratioOfDistanceToTravel), three.GravityShrinkWrap(sdf, gravityPoints, ratioOfDistanceToTravel));
        }



        public Face ShrinkWrap2(SignedDistanceField3d sdf, double maxTravel)
        {
            return new Face(one.ShrinkWrap2(sdf, maxTravel), two.ShrinkWrap2(sdf, maxTravel), three.ShrinkWrap2(sdf, maxTravel));
        }



        


        private Vector3d center = null;

        public Vector3d GetCenter()
        {
          
            if (center == null)
            {
                center = new Vector3d((one.x + two.x + three.x) / 3.0d, (one.y + two.y + three.y) / 3.0d, (one.z + two.z + three.z)/ 3.0d);
            }
            return center;
            
        }


//        public string ToSTLString()
//        {
//            string returnString = " facet normal " + this.normal.ToSTLString() + "\r\n";
//            returnString += "  outer loop\r\n";
//            returnString += "   vertex " + one.ToSTLString() + "\r\n";
//            returnString += "   vertex " + two.ToSTLString() + "\r\n";
//            returnString += "   vertex " + three.ToSTLString() + "\r\n";
//            returnString += "  endloop\r\n";
//            returnString += " endfacet\r\n";
//            return returnString;
//        }

        public byte[] ToBinary()
        {
            int counter = 0;
            byte[] returnBytes = new byte[50];
            byte[] normal = this.GetNormal().ToBinary();
            byte[] one = this.one.ToBinary();
            byte[] two = this.two.ToBinary();
            byte[] three = this.three.ToBinary();

            counter = Util.AppendBytes(returnBytes, normal, counter);
            counter = Util.AppendBytes(returnBytes, one, counter);
            counter = Util.AppendBytes(returnBytes, two, counter);
            counter = Util.AppendBytes(returnBytes, three, counter);
            Util.AppendBytes(returnBytes, new byte[] { 0, 0 }, counter);
            return returnBytes;
        }

        public boolean Intersects(Face b)
        {
            //return true if face b intersects this face.
            //todo 
            return true;
        }
        public boolean IsOnSamePlane(Face b)
        {
            return IsOnSamePlane(b, .000000000000001d);
        }
        public boolean IsOnSamePlane(Face b, double tolerance)
        {
            //return true of face b is on same plane as face a (within tolerance).
            //todo
            return true;
        }
      
        public Face SnapToUnitSphere()
        {
            Vector3d newOne = one.GetUnitVector();
            Vector3d newTwo = two.GetUnitVector();
            Vector3d newThree = three.GetUnitVector();
            return new Face(newOne, newTwo, newThree);
        }

       
        public boolean LinesIntersectSDF(SignedDistanceField3d sdf, double epsilon)
        {
            if (Vector3d.IntersectsSDF(sdf,one,two,epsilon)
                || Vector3d.IntersectsSDF(sdf, two, three, epsilon)
                || Vector3d.IntersectsSDF(sdf, three, one, epsilon)
                )
            {
                return true;
            }
            return false;
        }

       

}
