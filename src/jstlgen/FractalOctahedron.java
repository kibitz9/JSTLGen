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
public class FractalOctahedron extends FractalHedron {
      private Vector3d frontLeft;
        private Vector3d frontRight;
        private Vector3d backRight;
        private Vector3d backLeft;
        private Vector3d top;
        private Vector3d bottom;

        public FractalOctahedron (double scale)
        {
            this();
            Scale(scale);
           
        }
       
        public FractalOctahedron(
            Vector3d frontleft,
            Vector3d frontRight,
            Vector3d backRight,
            Vector3d backLeft,
            Vector3d top,
            Vector3d bottom
            )
        {
            this.frontLeft = frontleft;
            this.frontRight = frontRight;
            this.backRight = backRight;
            this.backLeft = backLeft;
            this.top = top;
            this.bottom = bottom;
        }
        public FractalOctahedron()
        {
            
            frontLeft = new Vector3d(-1, -1, 0);
            frontRight = new Vector3d(1, -1, 0);
            backRight = new Vector3d(1, 1, 0);
            backLeft = new Vector3d(-1, 1, 0);
            top = new Vector3d(0, 0, sqrt2);
            bottom = new Vector3d(0, 0, -sqrt2);

        }
        
       

       
        @Override
        public double GetLineLength()
        {
            return frontLeft.Subtract(frontRight).GetMagnitude();
        }
        
        @Override
        public List<Face> GetFaces()
        {
            List<Face> faces = new ArrayList<Face>();
            faces.add(new Face(backRight, backLeft, top));
            faces.add(new Face(backLeft, backRight, bottom));

            faces.add(new Face(frontLeft, frontRight, top));
            faces.add(new Face(frontRight, frontLeft, bottom));

            faces.add(new Face(frontRight, backRight, top));
            faces.add(new Face(backRight, frontRight, bottom));

            faces.add(new Face(backLeft, frontLeft, top));
            faces.add(new Face(frontLeft, backLeft, bottom));
            return faces;
        }
        @Override    
        protected void ScaleVertices(double scale)
        {
            frontLeft = frontLeft.Scale(scale);
            frontRight =frontRight.Scale(scale);
            backRight =backRight.Scale(scale);
            backLeft =backLeft.Scale(scale);
            top = top.Scale(scale);
            bottom = bottom.Scale(scale);
        }
        @Override
        protected void TranslateVertices(Vector3d translation)
        {
            top = top.Add(translation);
            bottom = bottom.Add(translation);
            backLeft = backLeft.Add(translation);
            backRight = backRight.Add(translation);
            frontLeft = frontLeft.Add(translation);
            frontRight = frontRight.Add(translation);
        }
        @Override
        protected List<FractalHedron> SubdivideAll()
        {
            List<FractalHedron> returnList = new ArrayList<FractalHedron>();
            //aliasing these for clarity because this can get confusing...

            Vector3d newTop = top;
            Vector3d newBackLeft = backLeft.Midpoint(top);
            Vector3d newBackRight = backRight.Midpoint(top);
            Vector3d newFrontRight = frontRight.Midpoint(top);
            Vector3d newFrontLeft = frontLeft.Midpoint(top);
            Vector3d newBottom = bottom.Midpoint(top);//many ways to get this one.


            //top
            returnList.add(new FractalOctahedron(
                newFrontLeft
                , newFrontRight
                , newBackRight
                , newBackLeft
                , newTop
                , newBottom
            ));


            newTop = top.Midpoint(bottom);
            newBackLeft = backLeft.Midpoint(bottom);
            newBackRight = backRight.Midpoint(bottom);
            newFrontRight = frontRight.Midpoint(bottom);
            newFrontLeft = frontLeft.Midpoint(bottom);
            newBottom = bottom;

            //bottom
            returnList.add(new FractalOctahedron(
                newFrontLeft
                , newFrontRight
                , newBackRight
                , newBackLeft
                , newTop
                , newBottom
            ));


            //backleft
            newTop = top.Midpoint(backLeft);
            newBackLeft = backLeft;//.Midpoint(backLeft);
            newBackRight = backRight.Midpoint(backLeft);
            newFrontRight = frontRight.Midpoint(backLeft);
            newFrontLeft = frontLeft.Midpoint(backLeft);
            newBottom = bottom.Midpoint(backLeft);

            returnList.add(new FractalOctahedron(
                newFrontLeft
                , newFrontRight
                , newBackRight
                , newBackLeft
                , newTop
                , newBottom
            ));

            //backright
            newTop = top.Midpoint(backRight);
            newBackLeft = backLeft.Midpoint(backRight);
            newBackRight = backRight;//.Midpoint(backRight);
            newFrontRight = frontRight.Midpoint(backRight);
            newFrontLeft = frontLeft.Midpoint(backRight);
            newBottom = bottom.Midpoint(backRight);

            returnList.add(new FractalOctahedron(
               newFrontLeft
               , newFrontRight
               , newBackRight
               , newBackLeft
               , newTop
               , newBottom
           ));

            //frontleft
            newTop = top.Midpoint(frontLeft);
            newBackLeft = backLeft.Midpoint(frontLeft);
            newBackRight = backRight.Midpoint(frontLeft);
            newFrontRight = frontRight.Midpoint(frontLeft);
            newFrontLeft = frontLeft;//.Midpoint(frontLeft);
            newBottom = bottom.Midpoint(frontLeft);

            returnList.add(new FractalOctahedron(
               newFrontLeft
               , newFrontRight
               , newBackRight
               , newBackLeft
               , newTop
               , newBottom
           ));


            //new FrontRight

            newTop = top.Midpoint(frontRight);
            newBackLeft = backLeft.Midpoint(frontRight);
            newBackRight = backRight.Midpoint(frontRight);
            newFrontRight = frontRight;//.Midpoint(frontRight);
            newFrontLeft = frontLeft.Midpoint(frontRight);
            newBottom = bottom.Midpoint(frontRight);

            returnList.add(new FractalOctahedron(
               newFrontLeft
               , newFrontRight
               , newBackRight
               , newBackLeft
               , newTop
               , newBottom
           ));

            
            //we need to add eight tetrahedrons too!!!
            //first the xtop's
            Vector3d newTopLeft = top.Midpoint(frontLeft);
            Vector3d newTopRight = top.Midpoint(frontRight);
            Vector3d newBottomFront = frontLeft.Midpoint(frontRight);
            Vector3d newBottomBack = top.Midpoint(bottom);

            returnList.add(new FractalXTopTetrahedron(
                newTopLeft, newTopRight, newBottomFront, newBottomBack

                ));

           
            newTopLeft = top.Midpoint(backLeft);
            newTopRight = top.Midpoint(backRight);
            newBottomFront = top.Midpoint(bottom);
            newBottomBack = backLeft.Midpoint(backRight);
            

            returnList.add(new FractalXTopTetrahedron(
                newTopLeft, newTopRight, newBottomFront, newBottomBack

                ));

            

           newTopLeft = frontLeft.Midpoint(backLeft);
           newTopRight = top.Midpoint(bottom);
           newBottomFront = frontLeft.Midpoint(bottom);
           newBottomBack = backLeft.Midpoint(bottom);

           returnList.add(new FractalXTopTetrahedron(
               newTopLeft, newTopRight, newBottomFront, newBottomBack

               ));



           newTopLeft = top.Midpoint(bottom);
           newTopRight = frontRight.Midpoint(backRight);
           newBottomFront = frontRight.Midpoint(bottom);
           newBottomBack = backRight.Midpoint(bottom);

           returnList.add(new FractalXTopTetrahedron(
               newTopLeft, newTopRight, newBottomFront, newBottomBack

               ));

           
            //finally the y tops...
            Vector3d newtopBack = top.Midpoint(backLeft);
            Vector3d newtopFront = top.Midpoint(frontLeft);
            Vector3d newbottomLeft = backLeft.Midpoint(frontLeft);
            Vector3d newbottomRight = top.Midpoint(bottom);

            returnList.add(new FractalYTopTetrahedron(
               newtopBack, newtopFront,newbottomLeft, newbottomRight

               ));


            newtopBack = top.Midpoint(backRight);
            newtopFront = top.Midpoint(frontRight);
            newbottomLeft = top.Midpoint(bottom);
            newbottomRight = frontRight.Midpoint(backRight);

            returnList.add(new FractalYTopTetrahedron(
               newtopBack, newtopFront, newbottomLeft, newbottomRight

               ));



            newtopBack = top.Midpoint(bottom);
            newtopFront = frontLeft.Midpoint(frontRight);
            newbottomLeft = frontLeft.Midpoint(bottom);
            newbottomRight = frontRight.Midpoint(bottom);

            returnList.add(new FractalYTopTetrahedron(
               newtopBack, newtopFront, newbottomLeft, newbottomRight

               ));


            newtopBack = backLeft.Midpoint(backRight);
            newtopFront = top.Midpoint(bottom);
            newbottomLeft = backLeft.Midpoint(bottom);
            newbottomRight = backRight.Midpoint(bottom);

            returnList.add(new FractalYTopTetrahedron(
               newtopBack, newtopFront, newbottomLeft, newbottomRight

               ));

         

            return returnList;
        }


        
        @Override    
        protected int GetSDFState(SignedDistanceField3d sdf, double marginalDist, double epsilon)
        {
            //returns 2 for completely outside
            //returns 1 for outside but close
            //returns 0 for some vertices inside some outside (spanning) or line intersections detected
            //returns -1 for inside but close 
            //returns -2 for all vertices inside


           

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

            int topStat = this.top.InsideOrOutside(sdf, marginalDist);
            int bottomStat = this.bottom.InsideOrOutside(sdf, marginalDist);
            int backleftStat = this.backLeft.InsideOrOutside(sdf, marginalDist);
            int backrightStat = this.backRight.InsideOrOutside(sdf, marginalDist);
            int frontLeftStat = this.frontLeft.InsideOrOutside(sdf, marginalDist);
            int frontRightStat = this.frontRight.InsideOrOutside(sdf, marginalDist);
            if (
                    topStat > 1
               && bottomStat > 1
               && backleftStat > 1
               && backrightStat > 1
               && frontLeftStat > 1
               && frontRightStat > 1
             )
            {
                if (!someInside)
                {
                    return 2;//all completely outside
                }
            }

            if (
                    topStat < -1
               && bottomStat < -1
               && backleftStat < -1
               && backrightStat < -1
               && frontLeftStat < -1
               && frontRightStat < -1
             )
            {
                if (!someOutside[0])
                {
                    return -2;//all completely inside
                }
            }



            if (
                topStat > 0
                || bottomStat > 0
                || backleftStat > 0
                || backrightStat > 0
                || frontLeftStat > 0
                || frontRightStat > 0
            )
            {
                someOutside[0] = true;
            }

            if (
                topStat < 0
                || bottomStat < 0
                || backleftStat < 0
                || backrightStat < 0
                || frontLeftStat < 0
                || frontRightStat < 0
            )
            {
                someInside = true;
            }

            



            if (someInside && someOutside[0])
            {
                return 0;
            }
            if (someOutside[0])
            {
                return 1;
            }
//            if (!someInside && !someOutside)
//            {
//                throw new java.lang.Exception("invalid result");
//            }
            return -1;
        }
        private boolean SomeLinesIntersectSDF(SignedDistanceField3d sdf, double epsilon, boolean[] someDont)
        {
            someDont[0] = false;
            boolean someDo = false;
            if (sdf.GetDistance(frontLeft) > GetLineLength() * 2)
            {
                someDont[0] = true;
                return false;
            }
            if (Vector3d.IntersectsSDF(sdf, frontLeft, frontRight, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (Vector3d.IntersectsSDF(sdf, frontRight, backRight, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, backRight, backLeft, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, backLeft, frontLeft, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, frontLeft, top, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, frontRight, top, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, backRight, top, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, backLeft, top, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, frontLeft, bottom, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, frontRight, bottom, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, backRight, bottom, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, backLeft, bottom, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }

            return someDo;


        }
        @Override
        protected List<Face> GetInsideOrOutsideFaces(SignedDistanceField3d sdf, boolean inside, double epsilon)
        {
            int mult = 1;
            if (inside)
            {
                mult = -1;
            }
            boolean topStat = (this.top.InsideOrOutside(sdf)*mult > 0);
            boolean bottomStat = (this.bottom.InsideOrOutside(sdf) * mult > 0);
            boolean backLeftStat = (this.backLeft.InsideOrOutside(sdf) * mult > 0);
            boolean backRightStat = (this.backRight.InsideOrOutside(sdf) * mult > 0);
            boolean frontLeftStat = (this.frontLeft.InsideOrOutside(sdf) * mult > 0);
            boolean frontRightStat = (this.frontRight.InsideOrOutside(sdf) * mult > 0);


            List<Face> returnFaces = new ArrayList<Face>();
            if (topStat)
            {

                if (frontLeftStat)
                {
                    if (frontRightStat)
                    {
                        returnFaces.add(new Face(frontLeft, frontRight, top));
                    }
                    if (backLeftStat)
                    {
                        returnFaces.add(new Face(backLeft, frontLeft, top));
                    }
                }
                if (backRightStat)
                {
                    if (frontRightStat)
                    {
                        returnFaces.add(new Face(frontRight, backRight, top));
                    }
                    if (backLeftStat)
                    {
                        returnFaces.add(new Face(backRight, backLeft, top));
                    }
                }

            }

            if (bottomStat)
            {
                if (frontLeftStat)
                {
                    if (frontRightStat)
                    {
                        returnFaces.add(new Face(frontRight, frontLeft, bottom));
                    }
                    if (backLeftStat)
                    {
                        returnFaces.add(new Face(frontLeft, backLeft, bottom));
                    }

                }

                if (backRightStat)
                {
                    if (backLeftStat)
                    {
                        returnFaces.add(new Face(backLeft, backRight, bottom));
                    }
                    if (frontRightStat)
                    {
                        returnFaces.add(new Face(backRight, frontRight, bottom));
                    }
                }


            }
            return CullFacesWhereLinesIntersect(sdf,returnFaces,epsilon);
        }
        @Override
        protected List<Face> GetOutsideFaces(SignedDistanceField3d sdf, double epsilon)
        {
            return GetInsideOrOutsideFaces(sdf, false, epsilon);
            //bool topStat = this.top.InsideOrOutside(sdf) > 0;
            //bool bottomStat = this.bottom.InsideOrOutside(sdf) > 0;
            //bool backLeftStat = this.backLeft.InsideOrOutside(sdf) > 0;
            //bool backRightStat = this.backRight.InsideOrOutside(sdf) > 0;
            //bool frontLeftStat = this.frontLeft.InsideOrOutside(sdf) > 0;
            //bool frontRightStat = this.frontRight.InsideOrOutside(sdf) > 0;


            //List<Face> returnFaces = new List<Face>();
            //if (topStat)
            //{

            //    if (frontLeftStat)
            //    {
            //        if (frontRightStat)
            //        {
            //            returnFaces.Add(new Face(frontLeft, frontRight, top));
            //        }
            //        if (backLeftStat)
            //        {
            //            returnFaces.Add(new Face(backLeft, frontLeft, top));
            //        }
            //    }
            //    if (backRightStat)
            //    {
            //        if (frontRightStat)
            //        {
            //            returnFaces.Add(new Face(frontRight, backRight, top));
            //        }
            //        if (backLeftStat)
            //        {
            //            returnFaces.Add(new Face(backRight, backLeft, top));
            //        }
            //    }

            //}

            //if (bottomStat)
            //{
            //    if (frontLeftStat)
            //    {
            //        if (frontRightStat)
            //        {
            //            returnFaces.Add(new Face(frontRight, frontLeft, bottom));
            //        }
            //        if (backLeftStat)
            //        {
            //            returnFaces.Add(new Face(frontLeft, backLeft, bottom));
            //        }

            //    }

            //    if (backRightStat)
            //    {
            //        if (backLeftStat)
            //        {
            //            returnFaces.Add(new Face(backLeft, backRight, bottom));
            //        }
            //        if (frontRightStat)
            //        {
            //            returnFaces.Add(new Face(backRight, frontRight, bottom));
            //        }
            //    }


            //}
            //return returnFaces;
        }
        @Override
        protected Vector3d GetTranslation()
        {
            return top.Midpoint(bottom);
        }
}
