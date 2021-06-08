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
public class SDFDistortionSin extends SignedDistanceField3d{


    public SignedDistanceField3d toDistort;
    double locationMult;
    double scale;
    public SDFDistortionSin(SignedDistanceField3d toDistort, double locationMult, double scale){
        this.toDistort=toDistort;
        this.locationMult=locationMult;
        this.scale = scale;
    }
    @Override
    public double GetRawDistance(Vector3d p) {
        p=p.Add(sin(p.Scale(locationMult)).Scale(scale));
        double dist = toDistort.GetDistance(p);
        return dist;
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDFDistortionSin(toDistort.Clone(),locationMult,scale);
    }
    
}
