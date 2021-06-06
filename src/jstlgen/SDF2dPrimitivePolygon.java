/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen;
import java.util.*;
/**
 *
 * @author cmiller
 */
public class SDF2dPrimitivePolygon extends SignedDistanceField2d{
    
    private Vector2d[] points;


    public SDF2dPrimitivePolygon(List<Vector2d> points)
    {
        this.points = new Vector2d[points.size()];
        for (int a=0;a<points.size();a++){
            this.points[a]=points.get(a);
        }
        prep();
    }

    public SDF2dPrimitivePolygon(Vector2d[] points){
        this.points = new Vector2d[points.length];
        for (int a=0;a<points.length;a++){
            this.points[a]=points[a];
        }
        prep();
        
    }
    
    public SDF2dPrimitivePolygon InvertX()
    {
        Vector2d[] newList = new Vector2d[points.length];
        for(int a=0;a<points.length;a++)
        {
            Vector2d v = points[a];
            
            newList[a]=new Vector2d(-v.x, v.y);

        }
        return new SDF2dPrimitivePolygon(newList);
    }
    public SDF2dPrimitivePolygon AlignFirstPointAtOrigin()
    {
        Vector2d[] newList = new Vector2d[points.length] ;
        double min = Double.MIN_VALUE;
        for (int a=0;a<points.length;a++){
            Vector2d v = points[a];
            if (v.x < min)
            {
                min = v.x;
            }
        }
        for (int a=0;a<points.length;a++){
            Vector2d v = points[a];
            newList[a]=new Vector2d(v.x-min, v.y);
        }

        return new SDF2dPrimitivePolygon(newList);

    }
    @Override
    public double GetRawDistance(Vector2d translatedp)
    {
        Vector2d pMinusv0 = translatedp.Subtract(points[0]);//
        //double d = pMinusv0.DotProduct(pMinusv0);//
        double d = pMinusv0.GetMagnitudeSquared();
        double s = 1.0;
        int n = points.length;
        for(int i = 0, j = n - 1; i < n; j = i, i++)
        {
            Vector2d vi = points[i];
            Vector2d vj = points[j];
            Vector2d e = es[i];
            Vector2d w = translatedp.Subtract(vi);
            double cl = clamp(w.DotProduct(e) / dotProducts[i], 0.0, 1.0);
            Vector2d b = w.Subtract(e.Scale(cl));
            //d= Min(d, b.DotProduct(b));
            d = min(d, b.GetMagnitudeSquared());
            BinaryVector3d c = new BinaryVector3d(
                translatedp.y >= vi.y,
                translatedp.y < vj.y,
                e.x * w.y > e.y * w.x

                );
            if (c.All() || c.Not().All())
            {
                s =-s;
            }
        }
        return s * sqrt(d);
    }

//    public double GetRawDistanceGood(Vector2d translatedp)
//    {
//        Vector2d pMinusv0 = translatedp.Subtract(points[0]);
//        double d = pMinusv0.DotProduct(pMinusv0);
//        double s = 1.0;
//        int n = points.length;
//        for (int i = 0, j = n - 1; i < n; j = i, i++)
//        {
//            Vector2d vi = points[i];
//            Vector2d vj = points[j];
//            Vector2d e = vj.Subtract(vi);
//            Vector2d w = translatedp.Subtract(vi);
//            double cl = Clamp(w.DotProduct(e) / e.DotProduct(e), 0.0, 1.0);
//            Vector2d b = w.Subtract(e.Scale(cl));
//            d = Min(d, b.DotProduct(b));
//            BinaryVector3d c = new BinaryVector3d(
//                translatedp.Y >= vi.Y,
//                translatedp.Y < vj.Y,
//                e.X * w.Y > e.Y * w.X
//
//                );
//            if (c.All() || c.Not().All())
//            {
//                s = -s;
//            }
//        }
//        return s * Math.Sqrt(d);
//    }

    private Vector2d[] es = null;
    private double[] dotProducts = null;
    private void prep()
    {
        es = new Vector2d[points.length];
        dotProducts = new double[points.length];
        //Vector2d pMinusv0 = translatedp.Subtract(points[0]);
        //double d = pMinusv0.DotProduct(pMinusv0);
        //double s = 1.0;
        int n = points.length;
        for (int i = 0, j = n - 1; i < n; j = i, i++)
        {
            Vector2d vi = points[i];
            Vector2d vj = points[j];
            es[i] = vj.Subtract(vi);
            dotProducts [i]= es[i].DotProduct(es[i]);

            ////Vector2d e = vj.Subtract(vi);
            ////Vector2d w = translatedp.Subtract(vi);
            //double cl = Clamp(w.DotProduct(es[i]) / dotProducts[i], 0.0, 1.0);
            //Vector2d b = w.Subtract(e.Scale(cl));
            //d = Min(d, b.DotProduct(b));
            //BinaryVector3d c = new BinaryVector3d(
            //    translatedp.Y >= vi.Y,
            //    translatedp.Y < vj.Y,
            //    e.X * w.Y > e.Y * w.X

            //    );
            //if (c.All() || c.Not().All())
            //{
            //    s = -s;
            //}
        }
        //return s * Math.Sqrt(d);
    }
    @Override
    public SignedDistanceField2d Clone()
    {
        Vector2d[] newPoints = new Vector2d[points.length];
        for (int a=0;a<points.length;a++){
            newPoints[a] = new Vector2d(points[a]);
        }
       
        return new SDF2dPrimitivePolygon(newPoints);
    }

    /*

    const int num = v.length();
    float d = dot(p-v[0],p-v[0]);
    float s = 1.0;
    for( int i=0, j=num-1; i<num; j=i, i++ )
    {
        // distance
        vec2 e = v[j] - v[i];
        vec2 w =    p - v[i];
        vec2 b = w - e*clamp( dot(w,e)/dot(e,e), 0.0, 1.0 );
        d = min( d, dot(b,b) );

        // winding number from http://geomalgorithms.com/a03-_inclusion.html
        bvec3 cond = bvec3( p.y>=v[i].y, 
                            p.y <v[j].y, 
                            e.x*w.y>e.y*w.x );
        if( all(cond) || all(not(cond)) ) s=-s;  
    }

    return s*sqrt(d);

     * */


    //float sdPolygon(in vec2[N] v, in vec2 p)
    //{
    //    float d = dot(p - v[0], p - v[0]);
    //    float s = 1.0;
    //    for (int i = 0, j = N - 1; i < N; j = i, i++)
    //    {
    //        vec2 e = v[j] - v[i];
    //        vec2 w = p - v[i];
    //        vec2 b = w - e * clamp(dot(w, e) / dot(e, e), 0.0, 1.0);
    //        d = min(d, dot(b, b));
    //        bvec3 c = bvec3(p.y >= v[i].y, p.y < v[j].y, e.x * w.y > e.y * w.x);
    //        if (all(c) || all(not(c))) s *= -1.0;
    //    }
    //    return s * sqrt(d);
    //}
}
