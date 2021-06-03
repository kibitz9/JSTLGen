/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen;

/**
 *
 * @author Christopher.Miller
 */
public class Line {
    public Vector3d start;
    public Vector3d end;
    public Line(Vector3d start, Vector3d end)
    {
        this.start = start;
        this.end = end;
    }
       

    public double GetCrudeDistance(Line other)
    {
        double dist = this.start.Subtract(other.end).GetMagnitude();
        dist += this.end.Subtract(other.start).GetMagnitude();
        return dist;
    }

    public static Vector3d GetClosestLinePoint(Vector3d lineStart, Vector3d lineEnd, Vector3d location)
    {
        Vector3d d = lineEnd.Subtract(lineStart).GetUnitVector();
        Vector3d v = location.Subtract(lineStart);
        double t = v.DotProduct(d);
        Vector3d P = lineStart.Add(d.Scale(t));
        return P;
    }
    public static double GetDistance(Vector3d lineStart, Vector3d lineEnd, Vector3d location)
    {
        return GetClosestLinePoint(lineStart, lineEnd, location).Subtract(location).GetMagnitude();
    }


   

       
}
