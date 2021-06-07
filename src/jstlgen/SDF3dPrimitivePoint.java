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
public class SDF3dPrimitivePoint extends SignedDistanceField3d{
    //no, a 3d point isn't quite an oxy... it has a 3d coordinate.
    private Vector3d location;
    public SDF3dPrimitivePoint(Vector3d location){
        this.location = location;
        
    
    }

    @Override
    public double GetRawDistance(Vector3d p) {
        return p.Subtract(location).GetMagnitude();
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDF3dPrimitivePoint(new Vector3d(location));
    }
}
