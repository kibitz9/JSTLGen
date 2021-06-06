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
public class SDF2dPrimitiveBox extends SignedDistanceField2d{
    private Vector2d box;

    public SDF2dPrimitiveBox(Vector2d box)
    {
        this.box = box;
    }
    @Override
    public SignedDistanceField2d Clone()
    {
        return new SDF2dPrimitiveBox(new Vector2d(box));
    }
    @Override
    public double GetRawDistance(Vector2d translatedp)
    {
        Vector2d d = translatedp.Abs().Subtract(box);
        Vector2d e = d.ComponentwiseMax(0.0);
        double f = e.GetMagnitude() + min(max(d.x, d.y), 0);
        return f;
    }
    //float sdBox(in vec2 p, in vec2 b)
    //{
    //    vec2 d = abs(p) - b;
    //    return length(max(d, 0.0)) + min(max(d.x, d.y), 0.0);
    //}
}
