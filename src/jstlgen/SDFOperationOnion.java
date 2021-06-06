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
public class SDFOperationOnion extends SignedDistanceField3d{
    private SignedDistanceField3d toOnion;
    double thickness;
    
    public SDFOperationOnion (SignedDistanceField3d toOnion, double thickness)
    {
        this.toOnion = toOnion;
        this.thickness = thickness;

    }
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {
        return abs(toOnion.GetDistance(translatedp)) - thickness;
    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationOnion(toOnion.Clone(), thickness);
    }

}
