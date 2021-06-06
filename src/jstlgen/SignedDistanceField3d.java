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
public abstract class SignedDistanceField3d  extends SignedDistanceField{
    public abstract double GetRawDistance(Vector3d p);
    public abstract SignedDistanceField3d Clone();
    
    public double GetDistance(Vector3d p){
        return GetRawDistance(p);
    }
    
   
    public Vector3d GetSlope(Vector3d location, double epsilon){
        Vector3d xPlus = new Vector3d(location.x + epsilon, location.y, location.z);
        Vector3d xMinus = new Vector3d(location.x - epsilon, location.y, location.z);
        double xSlope = GetDistance(xPlus) - GetDistance(xMinus);

        Vector3d yPlus = new Vector3d(location.x , location.y + epsilon, location.z);
        Vector3d yMinus = new Vector3d(location.x , location.y - epsilon, location.z);
        double ySlope = GetDistance(yPlus) - GetDistance(yMinus);

        Vector3d zPlus = new Vector3d(location.x, location.y , location.z + epsilon);
        Vector3d zMinus = new Vector3d(location.x, location.y , location.z - epsilon);
        double zSlope = GetDistance(zPlus) - GetDistance(zMinus);

        return new Vector3d(xSlope, ySlope, zSlope).Scale(1d/epsilon);
    }
    private Vector3d GetBound(Vector3d bigaxis, double padding){

        Vector3d returnVector = bigaxis.Add(bigaxis.Negate().GetUnitVector().Scale(this.GetDistance(bigaxis) - padding));
        return returnVector;
    }       
     public Line GetBoundingBox(double padding)
    {


        Vector3d minx = GetBound(Vector3d.BIG_NEG_X, padding);
        Vector3d miny = GetBound(Vector3d.BIG_NEG_Y, padding);
        Vector3d minz= GetBound(Vector3d.BIG_NEG_Z, padding);

        Vector3d maxx = GetBound(Vector3d.BIG_X, padding);
        Vector3d maxy = GetBound(Vector3d.BIG_Y, padding);
        Vector3d maxz = GetBound(Vector3d.BIG_Z, padding);

        return new Line(new Vector3d(minx.x, miny.y, minz.z), new Vector3d(maxx.x, maxy.y, maxz.z));


    }
}
