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
public class SDFDifference extends SignedDistanceField3d{
    private SignedDistanceField3d one;
    private SignedDistanceField3d two;
    public SDFDifference(SignedDistanceField3d one, SignedDistanceField3d two)
    {
        this.one = one;
        this.two = two;
    }
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {
        return max(one.GetDistance(translatedp), -two.GetDistance(translatedp));
    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFDifference(one.Clone(), two.Clone());
    }
}
