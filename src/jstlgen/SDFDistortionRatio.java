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
public class SDFDistortionRatio extends SignedDistanceField3d{
    private final double ratio;
    SignedDistanceField3d sdf;
    public SDFDistortionRatio(SignedDistanceField3d sdf, double ratio){
        this.ratio=ratio;
        this.sdf=sdf;
    }

    @Override
    public double GetRawDistance(Vector3d p) {
        return sdf.GetDistance(p)*ratio;
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDFDistortionRatio(this.sdf,this.ratio);
    }
    
}
