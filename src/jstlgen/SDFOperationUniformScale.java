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
public class SDFOperationUniformScale extends SignedDistanceField3d{
    private double scale;
    private double oneOverScale;
    private SignedDistanceField3d toScale;
    public SDFOperationUniformScale(SignedDistanceField3d toScale, double scale)
    {
        this.toScale = toScale;
        this.oneOverScale = 1/scale;

        this.scale = scale;
    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationUniformScale(toScale.Clone(), scale);
    }
    @Override
    public double GetRawDistance(Vector3d p)
    {
        return this.toScale.GetDistance(p.Scale(oneOverScale))*scale;
    }
    //float opScale(in vec3 p, in float s, in sdf3d primitive)
    //{
    //    return primitive(p / s) * s;
    //}

}