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
public class SDFOperationTrigRotateX extends SDFOperationTrigRotate {
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {


        double newY = translatedp.y * cosTheta + translatedp.z * sinTheta;
        double newZ = translatedp.z * cosTheta + translatedp.y * negativeSinTheta;

        //a standard rotation (not inverted) would look like this...
        //double newY = translatedp.Y * cosTheta - translatedp.Z * sinTheta;
        //double newZ = translatedp.Z * cosTheta + translatedp.Y * sinTheta;

        Vector3d temp = new Vector3d(translatedp.x, newY, newZ);
        return toRotate.GetDistance(temp);
        //BiComplex temp = new BiComplex(translatedp);
        //temp = temp.Multiply(this.amountInverse);
        //Vector3d newVector = new Vector3d(temp);
        //return toRotate.GetDistance(newVector);
    }

    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationTrigRotateX(toRotate.Clone(), super.radians);
    }
    
    public SDFOperationTrigRotateX(SignedDistanceField3d toRotate, double radians)
    {
        super(toRotate,radians);
    }
    
}