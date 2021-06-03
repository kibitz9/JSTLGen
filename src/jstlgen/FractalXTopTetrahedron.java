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
public class FractalXTopTetrahedron extends FractalHedron{
    
        private Vector3d topLeft;
        private Vector3d topRight;
        private Vector3d bottomFront;
        private Vector3d bottomBack;

        public FractalXTopTetrahedron(double scale)
        {
            this();
            Scale(scale);
           
        }
       
        public FractalXTopTetrahedron(
            Vector3d topLeft,
            Vector3d topRight,
            Vector3d bottomFront,
            Vector3d bottomBack
            
            )
        {
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomFront = bottomFront;
            this.bottomBack = bottomBack;
          
        }
        public FractalXTopTetrahedron()
        {
           
            topLeft = new Vector3d(-1,0, OneHalfsqrt2);
            topRight = new Vector3d(1, 0, OneHalfsqrt2);
            bottomFront = new Vector3d(0, -1, -OneHalfsqrt2);
            bottomBack = new Vector3d(0, 1, -OneHalfsqrt2);
           

        }

        @Override
        protected Vector3d GetTranslation()
        {
            Vector3d temp1 = topLeft.Midpoint(topRight);
            Vector3d temp2 = bottomFront.Midpoint(bottomBack);
            return temp1.Midpoint(temp2);
        }


        @Override
        public double GetLineLength()
        {
            return topLeft.Subtract(topRight).GetMagnitude();
        }
        @Override
        public List<Face> GetFaces()
        {
            List<Face> faces = new ArrayList<Face>();
            faces.add(new Face(topLeft, bottomFront, topRight));
            faces.add(new Face(topLeft, topRight, bottomBack));

            faces.add(new Face(bottomFront, bottomBack, topRight));
            faces.add(new Face(bottomFront, topLeft, bottomBack));

           
            return faces;
        }
        @Override
        protected void ScaleVertices(double scale)
        {
            topLeft = topLeft.Scale(scale);
            topRight = topRight.Scale(scale);
            bottomBack = bottomBack.Scale(scale);
            bottomFront = bottomFront.Scale(scale);
         
        }
        @Override
        protected void TranslateVertices(Vector3d translation)
        {
            topLeft = topLeft.Add(translation);
            topRight = topRight.Add(translation);
            bottomBack = bottomBack.Add(translation);
            bottomFront = bottomFront.Add(translation);
           
        }
        @Override
        protected List<FractalHedron> SubdivideAll()
        {


            List<FractalHedron> returnList = new ArrayList<FractalHedron>();

            //first, the center octahedron

            Vector3d newTop = topLeft.Midpoint(topRight);
            Vector3d newBottom = bottomFront.Midpoint(bottomBack);
            Vector3d newFrontLeft = topLeft.Midpoint(bottomFront);
            Vector3d newFrontRight = topRight.Midpoint(bottomFront);
            Vector3d newBackLeft = topLeft.Midpoint(bottomBack);
            Vector3d newBackRight = topRight.Midpoint(bottomBack);
           
            returnList.add(new FractalOctahedron(
                newFrontLeft,
                newFrontRight,
                newBackRight,
                newBackLeft,
                newTop,
                newBottom


                ));


            //now the other x tops...
            Vector3d newTopLeft = topLeft;
            Vector3d newTopRight = topLeft.Midpoint(topRight);
            Vector3d newBottomFront = topLeft.Midpoint(bottomFront);
            Vector3d newBottomBack = topLeft.Midpoint(bottomBack);

            returnList.add(new FractalXTopTetrahedron(newTopLeft, newTopRight, newBottomFront, newBottomBack));

            newTopLeft = topLeft.Midpoint(topRight);
            newTopRight = topRight;
            newBottomFront = topRight.Midpoint(bottomFront);
            newBottomBack = topRight.Midpoint(bottomBack);

            returnList.add(new FractalXTopTetrahedron(newTopLeft, newTopRight, newBottomFront, newBottomBack));


            newTopLeft = topLeft.Midpoint(bottomFront);
            newTopRight = topRight.Midpoint(bottomFront); ;
            newBottomFront = bottomFront;

            newBottomBack = bottomFront.Midpoint(bottomBack);

            returnList.add(new FractalXTopTetrahedron(newTopLeft, newTopRight, newBottomFront, newBottomBack));


            newTopLeft = topLeft.Midpoint(bottomBack);
            newTopRight = topRight.Midpoint(bottomBack); ;
            newBottomFront = bottomBack.Midpoint(bottomFront);
            newBottomBack = bottomBack;

            returnList.add(new FractalXTopTetrahedron(newTopLeft, newTopRight, newBottomFront, newBottomBack));





            // List<FractalSubdivision> returnList = new List<FractalSubdivision>();
            // //aliasing these for clarity because this can get confusing...




            // Vector3d newTop = top;
            // Vector3d newBackLeft = backLeft.Midpoint(top);
            // Vector3d newBackRight = backRight.Midpoint(top);
            // Vector3d newFrontRight = frontRight.Midpoint(top);
            // Vector3d newFrontLeft = frontLeft.Midpoint(top);
            // Vector3d newBottom = bottom.Midpoint(top);//many ways to get this one.


            // //top
            // returnList.Add(new FractalOctahedron(
            //     newFrontLeft
            //     , newFrontRight
            //     , newBackRight
            //     , newBackLeft
            //     , newTop
            //     , newBottom
            // ));


            // newTop = top.Midpoint(bottom);
            // newBackLeft = backLeft.Midpoint(bottom);
            // newBackRight = backRight.Midpoint(bottom);
            // newFrontRight = frontRight.Midpoint(bottom);
            // newFrontLeft = frontLeft.Midpoint(bottom);
            // newBottom = bottom;

            // //bottom
            // returnList.Add(new FractalOctahedron(
            //     newFrontLeft
            //     , newFrontRight
            //     , newBackRight
            //     , newBackLeft
            //     , newTop
            //     , newBottom
            // ));


            // //backleft
            // newTop = top.Midpoint(backLeft);
            // newBackLeft = backLeft;//.Midpoint(backLeft);
            // newBackRight = backRight.Midpoint(backLeft);
            // newFrontRight = frontRight.Midpoint(backLeft);
            // newFrontLeft = frontLeft.Midpoint(backLeft);
            // newBottom = bottom.Midpoint(backLeft);

            // returnList.Add(new FractalOctahedron(
            //     newFrontLeft
            //     , newFrontRight
            //     , newBackRight
            //     , newBackLeft
            //     , newTop
            //     , newBottom
            // ));

            // //backright
            // newTop = top.Midpoint(backRight);
            // newBackLeft = backLeft.Midpoint(backRight);
            // newBackRight = backRight;//.Midpoint(backRight);
            // newFrontRight = frontRight.Midpoint(backRight);
            // newFrontLeft = frontLeft.Midpoint(backRight);
            // newBottom = bottom.Midpoint(backRight);

            // returnList.Add(new FractalOctahedron(
            //    newFrontLeft
            //    , newFrontRight
            //    , newBackRight
            //    , newBackLeft
            //    , newTop
            //    , newBottom
            //));

            // //frontleft
            // newTop = top.Midpoint(frontLeft);
            // newBackLeft = backLeft.Midpoint(frontLeft);
            // newBackRight = backRight.Midpoint(frontLeft);
            // newFrontRight = frontRight.Midpoint(frontLeft);
            // newFrontLeft = frontLeft;//.Midpoint(frontLeft);
            // newBottom = bottom.Midpoint(frontLeft);

            // returnList.Add(new FractalOctahedron(
            //    newFrontLeft
            //    , newFrontRight
            //    , newBackRight
            //    , newBackLeft
            //    , newTop
            //    , newBottom
            //));


            // //new FrontRight

            // newTop = top.Midpoint(frontRight);
            // newBackLeft = backLeft.Midpoint(frontRight);
            // newBackRight = backRight.Midpoint(frontRight);
            // newFrontRight = frontRight;//.Midpoint(frontRight);
            // newFrontLeft = frontLeft.Midpoint(frontRight);
            // newBottom = bottom.Midpoint(frontRight);

            // returnList.Add(new FractalOctahedron(
            //    newFrontLeft
            //    , newFrontRight
            //    , newBackRight
            //    , newBackLeft
            //    , newTop
            //    , newBottom
            //));

            return returnList;

            // return returnList;
        }
        @Override
        protected int GetSDFState(SignedDistanceField3d sdf,double marginalDist, double epsilon)
        {
            //returns 1 for completely outside
            //returns 0 for some vertices inside
            //returns -1 for all vertices inside
            boolean someInside = false;
            boolean[] someOutside = new boolean[1];
            someOutside[0]=false;


            if (CHECKLINES)
            {
                if (SomeLinesIntersectSDF(sdf, epsilon, someOutside))
                {
                    someInside = true;
                }
            }


            int topLeftStat = this.topLeft.InsideOrOutside(sdf);
            int topRightStat = this.topRight.InsideOrOutside(sdf);
            int bottomFrontStat = this.bottomFront.InsideOrOutside(sdf);
            int bottomBackStat = this.bottomBack.InsideOrOutside(sdf);


            if (
                topLeftStat > 1
                && topRightStat > 1
                && bottomFrontStat > 1
                && bottomBackStat > 1
            )
            {
                if (!someInside)
                {
                    return 2;//all completely outside
                }
            }

            if (
                topLeftStat < -1
                && topRightStat < -1
                && bottomFrontStat < -1
                && bottomBackStat <-1
            )
            {
                if (!someOutside[0])
                {
                    return -2;//all completely inside
                }
            }


            if (
                topLeftStat > 0
                || topRightStat > 0
                || bottomFrontStat > 0
                || bottomBackStat > 0
               
            )
            {
                someOutside[0] = true;
            }

            if (
               topLeftStat < 0
                || topRightStat < 0
                || bottomFrontStat < 0
                || bottomBackStat < 0
            )
            {
                someInside = true;
            }



            //if (CHECKLINES)
            //{
            //    if (SomeLinesIntersectSDF(sdf, epsilon))
            //    {
            //        someInside = true;
            //    }
            //}


            if (someInside && someOutside[0])
            {
                return 0;
            }
            if (someOutside[0])
            {
                return 1;
            }

//            if (!someInside && !someOutside[0])
//            {
//                throw new Exception("invalid result");
//            }
            return -1;
        }
        @Override
        protected List<Face> GetInsideOrOutsideFaces(SignedDistanceField3d sdf, boolean inside, double epsilon)
        {
            int mult = 1;
            if (inside)
            {
                mult = -1;
            }
            boolean topLeftStat = this.topLeft.InsideOrOutside(sdf)*mult > 0;
            boolean topRightStat = this.topRight.InsideOrOutside(sdf) * mult > 0;
            boolean bottomFrontStat = this.bottomFront.InsideOrOutside(sdf) * mult > 0;
            boolean bottomBackStat = this.bottomBack.InsideOrOutside(sdf) * mult > 0;



            List<Face> returnFaces = new ArrayList<Face>();
            //List<Face> faces = new List<Face>();
            if (topLeftStat && topRightStat)
            {

                if (bottomFrontStat)
                {
                    returnFaces.add(new Face(topLeft, bottomFront, topRight));
                }
                if (bottomBackStat)
                {
                    returnFaces.add(new Face(topLeft, topRight, bottomBack));
                }

            }

            if (bottomFrontStat && bottomBackStat)
            {
                if (topRightStat)
                {
                    returnFaces.add(new Face(bottomFront, bottomBack, topRight));
                }
                if (topLeftStat)
                {
                    returnFaces.add(new Face(bottomFront, topLeft, bottomBack));
                }

            }
            return CullFacesWhereLinesIntersect(sdf, returnFaces, epsilon);

        }

        
        private boolean SomeLinesIntersectSDF(SignedDistanceField3d sdf, double epsilon, boolean[] someDont)
        {
            someDont[0] = false;
            boolean someDo = false;
            if (sdf.GetDistance(topLeft) > GetLineLength() *2 )
            {
                someDont[0] = true;
                return false;
            }
            


            if (Vector3d.IntersectsSDF(sdf, topLeft, topRight, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (Vector3d.IntersectsSDF(sdf, topLeft, bottomBack, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, topLeft, bottomFront, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, topRight, bottomBack, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, topRight, bottomFront, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, bottomBack, bottomFront, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }


            return someDo;


        }
        @Override
        protected List<Face> GetOutsideFaces(SignedDistanceField3d sdf, double epsilon )
        {
            return GetInsideOrOutsideFaces(sdf, false,epsilon);
            //bool topLeftStat = this.topLeft.InsideOrOutside(sdf) > 0;
            //bool topRightStat = this.topRight.InsideOrOutside(sdf) > 0;
            //bool bottomFrontStat = this.bottomFront.InsideOrOutside(sdf) > 0;
            //bool bottomBackStat = this.bottomBack.InsideOrOutside(sdf) > 0;
           


            //List<Face> returnFaces = new List<Face>();
            ////List<Face> faces = new List<Face>();
            //if (topLeftStat&&topRightStat)
            //{
               
            //    if (bottomFrontStat)
            //    {
            //        returnFaces.Add(new Face(topLeft, bottomFront, topRight));
            //    }
            //    if (bottomBackStat)
            //    {
            //        returnFaces.Add(new Face(topLeft, topRight, bottomBack));
            //    }
                
            //}

            //if (bottomFrontStat && bottomBackStat)
            //{
            //    if (topRightStat)
            //    {
            //        returnFaces.Add(new Face(bottomFront, bottomBack, topRight));
            //    }
            //    if (topLeftStat)
            //    {
            //        returnFaces.Add(new Face(bottomFront, topLeft, bottomBack));
            //    }

            //}
            //return returnFaces;
        }
}
