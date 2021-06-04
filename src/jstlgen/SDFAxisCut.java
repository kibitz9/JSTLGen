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
public class SDFAxisCut extends SignedDistanceField3d{
    SignedDistanceField3d toCut;
    Axis axis;
    double offset;
    boolean clipPositiveSide;
    
    public SDFAxisCut (SignedDistanceField3d toCut, Axis axis, double offset, boolean clipPositiveSide)
    {
        this.toCut = toCut;
        this.axis = axis;
        this.offset = offset;
        this.clipPositiveSide = clipPositiveSide;
    }
    @Override
    public  double GetRawDistance(Vector3d translatedp)
    {
        double temp=translatedp.x;
        if (axis == Axis.Y)
        {
            temp = translatedp.y;
        }
        else if (axis == Axis.Z)
        {
            temp = translatedp.z;
        }

        if (!clipPositiveSide)
        {
            temp*= -1;
        }
        temp += offset;
        return max(temp, toCut.GetDistance(translatedp));

    }
    public enum Axis
    {
        X,
        Y,
        Z,

    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFAxisCut(toCut.Clone(), axis, offset, clipPositiveSide);
    }
}
