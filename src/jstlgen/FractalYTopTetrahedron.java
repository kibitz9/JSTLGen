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
public class FractalYTopTetrahedron extends FractalHedron{
   
        private Vector3d topBack;
        private Vector3d topFront;
        private Vector3d bottomLeft;
        private Vector3d bottomRight;
        public FractalYTopTetrahedron(double scale)
        {
            this();
            Scale(scale);
           
        }
       
        public FractalYTopTetrahedron(
            Vector3d topBack,
            Vector3d topFront,
            Vector3d bottomLeft,
            Vector3d bottomRight

            )
        {
            this.topBack = topBack;
            this.topFront = topFront;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
          
        }
        public FractalYTopTetrahedron()
        {

            topBack = new Vector3d(0,1, OneHalfsqrt2);
            topFront = new Vector3d(0, -1, OneHalfsqrt2);
            bottomLeft = new Vector3d(-1, 0, -OneHalfsqrt2);
            bottomRight = new Vector3d(1, 0, -OneHalfsqrt2);
           

        }

        @Override
        protected Vector3d GetTranslation()
        {
            Vector3d temp1 = topBack.Midpoint(topFront);
            Vector3d temp2 = bottomLeft.Midpoint(bottomRight);
            return temp1.Midpoint(temp2);
        }

        @Override
        public double GetLineLength()
        {
            return topBack.Subtract(topFront).GetMagnitude();
        }
        @Override
        public List<Face> GetFaces()
        {
            List<Face> faces = new ArrayList<Face>();
            faces.add(new Face(topFront, bottomRight, topBack));
            faces.add(new Face(topFront, topBack, bottomLeft));

            faces.add(new Face(bottomLeft, bottomRight, topFront));
            faces.add(new Face(bottomLeft, topBack, bottomRight));

           
            return faces;
        }
        @Override
        protected void ScaleVertices(double scale)
        {
            topFront = topFront.Scale(scale);
            topBack = topBack.Scale(scale);
            bottomLeft = bottomLeft.Scale(scale);
            bottomRight = bottomRight.Scale(scale);
         
        }
        @Override
        protected void TranslateVertices(Vector3d translation)
        {
            topFront = topFront.Add(translation);
            topBack = topBack.Add(translation);
            bottomLeft = bottomLeft.Add(translation);
            bottomRight = bottomRight.Add(translation);
           
        }
        @Override
        protected List<FractalHedron> SubdivideAll()
        {
            List<FractalHedron> returnList = new ArrayList<FractalHedron>();

            //first, the center octahedron

            Vector3d newTop = topFront.Midpoint(topBack);
            Vector3d newBottom = bottomLeft.Midpoint(bottomRight);
            Vector3d newFrontLeft = topFront.Midpoint(bottomLeft);
            Vector3d newFrontRight = topFront.Midpoint(bottomRight);
            Vector3d newBackLeft = topBack.Midpoint(bottomLeft);
            Vector3d newBackRight = topBack.Midpoint(bottomRight);

            returnList.add(new FractalOctahedron(
                newFrontLeft,
                newFrontRight,
                newBackRight,
                newBackLeft,
                newTop,
                newBottom
                ));


            //now the other y tops...
            Vector3d newTopFront = topFront;
            Vector3d newTopBack = topFront.Midpoint(topBack);
            Vector3d newBottomLeft = topFront.Midpoint(bottomLeft);
            Vector3d newBottomRight = topFront.Midpoint(bottomRight);

            returnList.add(new FractalYTopTetrahedron(newTopBack,newTopFront,newBottomLeft,newBottomRight));

            newTopFront = topBack.Midpoint(topFront);
            newTopBack = topBack;
            newBottomLeft = topBack.Midpoint(bottomLeft);
            newBottomRight = topBack.Midpoint(bottomRight);

            returnList.add(new FractalYTopTetrahedron(newTopBack, newTopFront, newBottomLeft, newBottomRight));


            newTopFront = bottomRight.Midpoint(topFront);
            newTopBack = bottomRight.Midpoint(topBack);
            newBottomLeft = bottomRight.Midpoint(bottomLeft);
            newBottomRight = bottomRight;

            returnList.add(new FractalYTopTetrahedron(newTopBack, newTopFront, newBottomLeft, newBottomRight));



            newTopFront = bottomLeft.Midpoint(topFront);
            newTopBack = bottomLeft.Midpoint(topBack);
            newBottomLeft = bottomLeft;
            newBottomRight = bottomLeft.Midpoint(bottomRight); ;

            returnList.add(new FractalYTopTetrahedron(newTopBack, newTopFront, newBottomLeft, newBottomRight));

            return returnList;



        }
        @Override
        protected int GetSDFState(SignedDistanceField3d sdf, double marginalDist, double epsilon)
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


            int topFrontStat = this.topFront.InsideOrOutside(sdf);
            int topBackStat = this.topBack.InsideOrOutside(sdf);
            int bottomLeftStat = this.bottomLeft.InsideOrOutside(sdf);
            int bottomRightStat = this.bottomRight.InsideOrOutside(sdf);

            if (
                topFrontStat > 1
                && topBackStat > 1
                && bottomLeftStat > 1
                && bottomRightStat > 1
)
            {
                if (!someInside)
                {
                    return 2;//all completely outside
                }
            }

            if (
                topFrontStat < -1
                && topBackStat < -1
                && bottomLeftStat < -1
                && bottomRightStat < -1
            )
            {
                if (!someOutside[0])
                {
                    return -2;//all completely inside
                }
            }




            if (
                topFrontStat > 0
                || topBackStat > 0
                || bottomLeftStat > 0
                || bottomRightStat > 0
               
            )
            {
                someOutside[0] = true;
            }

            if (
               topFrontStat < 0
                || topBackStat < 0
                || bottomLeftStat < 0
                || bottomRightStat < 0
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
            boolean topFrontStat = this.topFront.InsideOrOutside(sdf)*mult > 0;
            boolean topBackStat = this.topBack.InsideOrOutside(sdf) * mult > 0;
            boolean bottomLeftStat = this.bottomLeft.InsideOrOutside(sdf) * mult > 0;
            boolean bottomRightStat = this.bottomRight.InsideOrOutside(sdf) * mult > 0;



            List<Face> returnFaces = new ArrayList<Face>();



            if (topFrontStat && topBackStat)
            {
                if (bottomRightStat)
                {
                    returnFaces.add(new Face(topFront, bottomRight, topBack));
                }
                if (bottomLeftStat)
                {
                    returnFaces.add(new Face(topFront, topBack, bottomLeft));
                }
            }

            if (bottomLeftStat && bottomRightStat)
            {
                if (topFrontStat)
                {
                    returnFaces.add(new Face(bottomLeft, bottomRight, topFront));
                }
                if (topBackStat)
                {
                    returnFaces.add(new Face(bottomLeft, topBack, bottomRight));
                }
            }
            return CullFacesWhereLinesIntersect(sdf, returnFaces, epsilon);
        }



        private boolean SomeLinesIntersectSDF(SignedDistanceField3d sdf, double epsilon, boolean[] someDont)
        {

            someDont[0] = false;
            boolean someDo = false;
            if (sdf.GetDistance(topBack) > GetLineLength()*2)
            {
                someDont[0] = true;
                return false;
            }



            if (Vector3d.IntersectsSDF(sdf, topBack, topFront, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (Vector3d.IntersectsSDF(sdf, topBack, bottomLeft, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, topBack, bottomRight, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, topFront, bottomLeft, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, topFront, bottomRight, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }
            if (someDont[0] && someDo)
            {
                return someDo;//save on processing
            }
            if (Vector3d.IntersectsSDF(sdf, bottomLeft, bottomRight, epsilon))
            {
                someDo = true;
            }
            else { someDont[0] = true; }


            return someDo;


        }
        @Override
        protected List<Face> GetOutsideFaces(SignedDistanceField3d sdf,double epsilon)
        {
            return GetInsideOrOutsideFaces(sdf, false, epsilon);
            //bool topFrontStat = this.topFront.InsideOrOutside(sdf)>0;
            //bool topBackStat = this.topBack.InsideOrOutside(sdf) > 0;
            //bool bottomLeftStat = this.bottomLeft.InsideOrOutside(sdf) > 0;
            //bool bottomRightStat = this.bottomRight.InsideOrOutside(sdf) > 0;



            //List<Face> returnFaces = new List<Face>();
            


            //if (topFrontStat && topBackStat)
            //{
            //    if (bottomRightStat)
            //    {
            //        returnFaces.Add(new Face(topFront, bottomRight, topBack));
            //    }
            //    if (bottomLeftStat)
            //    {
            //        returnFaces.Add(new Face(topFront, topBack, bottomLeft));
            //    }
            //}

            //if (bottomLeftStat && bottomRightStat)
            //{
            //    if (topFrontStat)
            //    {
            //        returnFaces.Add(new Face(bottomLeft, bottomRight, topFront));
            //    }
            //    if (topBackStat)
            //    {
            //        returnFaces.Add(new Face(bottomLeft, topBack, bottomRight));
            //    }
            //}
            //return returnFaces;
        } 
}
