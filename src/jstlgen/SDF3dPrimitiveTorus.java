/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen;

/**
 *
 * @author cmiller
 */
public class SDF3dPrimitiveTorus extends SignedDistanceField3d {
    double primaryRadius;
    double secondaryRadius;

    public SDF3dPrimitiveTorus(double primaryRadius, double secondaryRadius)
    {
        this.primaryRadius = primaryRadius;
        this.secondaryRadius = secondaryRadius;
    }
    @Override
    public double GetRawDistance(Vector3d p)
    {
        Vector2d temp = new Vector2d(p.GetXZ().GetMagnitude() - primaryRadius, p.y);
        return temp.GetMagnitude() - secondaryRadius;
    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDF3dPrimitiveTorus(primaryRadius, secondaryRadius);
    }

}