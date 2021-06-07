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
    double distanceMax = 1000;
    SignedDistanceField3d test;
    int iters = 128;
    
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
    public ShaderColor GetPixel(int x, int y) {
        double x1 = x;
        double y1 = y;
        x1-=halfWidthd;
        y1-=halfHeightd;
        x1/=widthd;
        y1/=widthd;//we scale according to one axis only
        
        Vector3d ray = new Vector3d(x1,y1,lense.z).Subtract(eye).GetUnitVector();

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
        if (distance<epsilon){
            steps *=2;
            return new ShaderColor(255,255-steps,255-steps,255-steps);
        }
        if (distance>distanceMax){
            steps *=4;
            return new ShaderColor(255,steps,0,0);
        }
        return new ShaderColor(255,0,0,0);
    }
    
}
