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
public class SDFOperationTrigRotateZ extends SDFOperationTrigRotate {
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {


        double newY = translatedp.y * cosTheta + translatedp.x * sinTheta;
        double newX = translatedp.x * cosTheta + translatedp.y * negativeSinTheta;

        //a standard rotation (not inverted) would look like this...
        //double newY = translatedp.Y * cosTheta - translatedp.Z * sinTheta;
        //double newZ = translatedp.Z * cosTheta + translatedp.Y * sinTheta;

        Vector3d temp = new Vector3d(newX,newY,translatedp.z);
        return toRotate.GetDistance(temp);
        //BiComplex temp = new BiComplex(translatedp);
        //temp = temp.Multiply(this.amountInverse);
        //Vector3d newVector = new Vector3d(temp);
        //return toRotate.GetDistance(newVector);
    }

    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationTrigRotateZ(toRotate.Clone(), super.radians);
    }
    
    public SDFOperationTrigRotateZ(SignedDistanceField3d toRotate, double radians)
    {
        super(toRotate,radians);
    }
    
}
