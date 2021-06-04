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
public abstract class FractalHedron {
     public static double debugScale = 1.0;
        ////octahedron points
        //private Vector3d frontLeft;
        //private Vector3d frontRight;
        //private Vector3d backRight;
        //private Vector3d backLeft;
        //private Vector3d top;
        //private Vector3d bottom;


        ////XTop tetrahedron points
        //private Vector3d topLeft;
        //private Vector3d topRight;
        //private Vector3d bottomFront;
        //private Vector3d bottomBack;

        ////yTop tetrahedron//
        //private Vector3d topBack;
        //private Vector3d topFront;
        //private Vector3d bottomLeft;
        //private Vector3d bottomRight;

        protected static final double sqrt2 = Math.sqrt(2);
        protected static final double OneHalfsqrt2 = sqrt2 / 2d;
        public enum ShapeType {
            Octahedron,
            XTopTetrahedron,
            YTopTetrahedron,
        }

        //public ShapeType Shape { get; set; } = ShapeType.Octahedron;

        //private ShapeType shapeType = ShapeType.Octahedron;

      
        public abstract double GetLineLength();

        public abstract List<Face> GetFaces();
       
        public List<Face> Faces()
        {
            return GetFaces();
        }
        protected abstract void ScaleVertices(double scale);
        public void Scale(double scale)
        {
            ScaleVertices(scale);
        }

        public Vector3d GetCenter()
        {
            return GetTranslation();
        }
        protected abstract Vector3d GetTranslation();
        protected abstract void TranslateVertices(Vector3d translation);
        public void Translate(Vector3d translation)
        {
            TranslateVertices(translation);
        }
        protected abstract List<FractalHedron> SubdivideAll();
        public List<FractalHedron> Subdivide()
        {
            List<FractalHedron> returnList = SubdivideAll();

            if (debugScale != 1)
            {
                for (FractalHedron f : returnList)
                {
                    Vector3d center = f.GetCenter();
                    f.Translate(center.Negate());
                    f.Scale(debugScale);
                    f.Translate(center);
                }
            }

            return returnList;
        }


        public double GetSurfaceNormalVariance(SignedDistanceField3d sdf, double epsilon)
        {
            double lineSize = GetLineLength();
            Vector3d x = new Vector3d(lineSize, 0, 0);
            Vector3d y = new Vector3d(0, lineSize, 0);
            Vector3d z = new Vector3d(0, 0, lineSize);

            Vector3d xplus = GetCenter().Add(x);
            Vector3d xminus = GetCenter().Subtract(x);

            Vector3d yplus = GetCenter().Add(y);
            Vector3d yminus = GetCenter().Subtract(y);

            Vector3d zplus = GetCenter().Add(z);
            Vector3d zminus = GetCenter().Subtract(z);

            Vector3d xplusNormal = sdf.GetSlope(xplus, epsilon).GetUnitVector();
            Vector3d xminusNormal = sdf.GetSlope(xminus, epsilon).GetUnitVector();

            Vector3d yplusNormal = sdf.GetSlope(yplus, epsilon).GetUnitVector();
            Vector3d yminusNormal = sdf.GetSlope(yminus, epsilon).GetUnitVector();

            Vector3d zplusNormal = sdf.GetSlope(zplus, epsilon).GetUnitVector();
            Vector3d zminusNormal = sdf.GetSlope(zminus, epsilon).GetUnitVector();

            double difx = xplusNormal.Subtract(xminusNormal).GetMagnitude();
            double dify = yplusNormal.Subtract(yminusNormal).GetMagnitude();
            double difz = zplusNormal.Subtract(zminusNormal).GetMagnitude();

            return Math.max(difx, Math.max(dify, difz));


        }
        protected static boolean CHECKLINES = false;//.here
        protected abstract int GetSDFState(SignedDistanceField3d sdf, double marginalDist, double epsilon);
        public int GetSpanningState(SignedDistanceField3d sdf, double marginalDist, double epsilon)
        {
            //returns 2 for completely outside
            //returns 1 for outside but close
            //returns 0 for some vertices inside some outside (spanning)
            //returns -1 for inside but close 
            //returns -2 for all vertices inside
           
            return GetSDFState(sdf, marginalDist, epsilon);
        }

        protected abstract List<Face> GetOutsideFaces(SignedDistanceField3d sdf, double epsilon);
        protected abstract List<Face> GetInsideOrOutsideFaces(SignedDistanceField3d sdf, boolean inside, double epsilon);
        public List<Face> GetOutsideFacesOnly(SignedDistanceField3d sdf, double epsilon)
        {
            return GetOutsideFaces(sdf, epsilon);   
        }

        protected static List<Face> CullFacesWhereLinesIntersect(SignedDistanceField3d sdf, List<Face> faces, double epsilon)
        {
            if (CHECKLINES)
            {
                List<Face> returnList = new ArrayList<Face>();
                for (Face f : faces)
                {
                    if (!f.LinesIntersectSDF(sdf, epsilon))
                    {
                        returnList.add(f);
                    }
                }
                return returnList;
            }
            else
            {
                return faces;
            }
        }

        public List<Face> GetInsideFacesOnly(SignedDistanceField3d sdf, double epsilon)
        {
            return GetInsideOrOutsideFaces(sdf, true,epsilon);
        }
}
