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
public class SDFOperationLathe extends SignedDistanceField3d{
    SignedDistanceField2d twoDElement;
    private double o;
    
    public SDFOperationLathe(SignedDistanceField2d twoDElement, double o)
    {
        this.twoDElement = twoDElement;
        this.o = o;
    }
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {
        Vector2d q = new Vector2d(translatedp.GetXZ().GetMagnitude() - o, translatedp.y);
        return twoDElement.GetDistance(q);
    }
    
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationLathe(twoDElement.Clone(), o);
    }

    //float opRevolution(in vec3 p, in sdf2d primitive, float o)
    //{
    //    vec2 q = vec2(length(p.xz) - o, p.y);
    //    return primitive(q)
    //}

}
