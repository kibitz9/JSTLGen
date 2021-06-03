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
public class SDFSphere extends SignedDistanceField3d {

    private final double radius;
    public SDFSphere(double radius)
    {
        this.radius = radius;
    }
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {
        return translatedp.GetMagnitude() - radius;
    }
     @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFSphere(radius);
    }

  
    
}
