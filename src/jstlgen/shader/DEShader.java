/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen.shader;
import jstlgen.*;

/**
 *
 * @author Christopher.Miller
 */
public class DEShader extends SoftwareShader{

    private Vector3d eye = new Vector3d(0,0,-40);
    private Vector3d lense = new Vector3d(0,0,-39);
    double distanceMax = 100;
    SignedDistanceField3d test;
    int iters = 64;
    
    public DEShader(javax.swing.JPanel target, int softwareThreads){
        super(target,softwareThreads);
        test = new SDF3dPrimitiveSphere(10);
        test = new SDFOperationOnion(test,4);
        test = new SDFOperationOnion(test,1);
        test = new SDFOperationOnion(test,.25);
        test = new SDFOperationAxisCut(test,SDFOperationAxisCut.Axis.X,0,true);
        
        test = new SDFOperationAxisCut(test,SDFOperationAxisCut.Axis.X,-5,false);
        
    }
    
    
    @Override
    public Vector4d mainImage(Vector2d fragCoord) {
        double x1 = fragCoord.x;
        double y1 = fragCoord.y;
        x1-=halfWidthd;
        y1-=halfHeightd;
        x1/=widthd;
        y1/=widthd;//we scale according to one axis only
        
        Vector3d ray = new Vector3d(x1,y1,lense.z).Subtract(eye).GetUnitVector();
        Vector3d lightSource = new Vector3d(100,100,100);
        Vector3d lightColor = new Vector3d(1,0,1);
        
        Vector3d lightSource2 = new Vector3d(-100,100,100);
        Vector3d lightColor2 = new Vector3d(1,1,0);
        
        double epsilon = .000001;
        Vector3d point = eye.Clone();
        int steps = 0;
        
        SignedDistanceField3d test2 = new SDFOperationTrigRotateY(test,fract(iTime/10)*3.14159265*2);
        SignedDistanceField3d test1 = new SDFOperationTrigRotateZ(test2,fract(iTime/14)*3.14159265*2);
        
        double distance = test1.GetDistance(point);
        
        while (
                distance>epsilon
                &&steps++<iters
                &&distance<distanceMax){
            point = point.Add(ray.Scale(distance));
            distance = test1.GetDistance(point);
            
        }
        
        Vector3d normal = test1.GetSlope(point, epsilon/10d).GetUnitVector().Negate();
        Vector3d lightRay = lightSource.Subtract(point).GetUnitVector();
        
        Vector3d lightRay2 = lightSource2.Subtract(point).GetUnitVector();
        
        
        double diffuse = max(normal.DotProduct(lightRay),0) ;
        double diffuse2 = max(normal.DotProduct(lightRay2),0);
        
        Vector3d diffuseColor = lightColor.Scale(diffuse);
        Vector3d diffuseColor2 = lightColor2.Scale(diffuse2);
        
        double si = (double)steps/(double)iters;
        
        double occlusion = 1.0-si;
        
        Vector3d combinedColor = diffuseColor.Add(diffuseColor2);
        
        combinedColor=combinedColor.Multiply(occlusion);
        //double brightness = occlusion*diffuse;
        
        if (distance<epsilon){
            //steps ;
            return vec4(combinedColor,1.0);
        }
        if (distance>distanceMax){
            //steps *=4;
            return vec4(si,0,0,1.0);
        }
        return vec4(0,0,0,1);
    }
    
}
