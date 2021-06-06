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
public class SDF2dPrimitiveCircle extends SignedDistanceField2d{
    private double radius;

    public SDF2dPrimitiveCircle(double radius)
    {
        this.radius = radius;
    }
    @Override
    public SignedDistanceField2d Clone()
    {
        return new SDF2dPrimitiveCircle(radius);
    }
    @Override
    public double GetRawDistance(Vector2d translatedp)
    {
        return translatedp.GetMagnitude() - radius;
    }
    //float sdCircle(vec2 p, float r)
    //{
    //    return length(p) - r;
    //}
}
