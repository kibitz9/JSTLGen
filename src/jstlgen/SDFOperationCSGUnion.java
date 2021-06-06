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
public class SDFOperationCSGUnion extends SignedDistanceField3d{
    private SignedDistanceField3d one;
    private SignedDistanceField3d two;
    
    public SDFOperationCSGUnion(SignedDistanceField3d one, SignedDistanceField3d two)
    {
        this.one = one;
        this.two = two;
    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationCSGUnion(one.Clone(), two.Clone());
    }
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {
        return min(one.GetDistance(translatedp), two.GetDistance(translatedp));
    } 
}
