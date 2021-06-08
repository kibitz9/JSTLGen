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
    int iters = 128;
    
    
    
    public DEShader(javax.swing.JPanel target, int softwareThreads){
        super(target,softwareThreads);
        test = new SDF3dPrimitiveSphere(10);
        test = new SDFOperationOnion(test,4);
        test = new SDFOperationOnion(test,1);
        test = new SDFOperationOnion(test,.25);
        test = new SDFOperationSmoothAxisCut(test,SDFOperationSmoothAxisCut.Axis.X,0,true,2.0);
        
        test = new SDFOperationSmoothAxisCut(test,SDFOperationSmoothAxisCut.Axis.X,-5,false,2.0);
        //test = new SDFDistortionSin(test,4,.25);
        
        test = new SDFOperationRepeatX(test,1,1,9);
        //test = new SDFOperationRoundEdges(test,1);
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
        Vector3d lightColor = new Vector3d(.75,.5,1);
        
        Vector3d lightSource2 = new Vector3d(-100,100,100);
        Vector3d lightColor2 = new Vector3d(1,.9,.8);
        //lightColor2 = new Vector3d(0,0,0);
        double epsilon = .0000001;
        Vector3d point = eye.Clone();
        
        
        SignedDistanceField3d test2 = new SDFOperationTrigRotateY(test,fract(iTime/10)*3.14159265*2);
        SignedDistanceField3d test1 = new SDFOperationTrigRotateZ(test2,fract(iTime/51)*3.14159265*2);
        
        //double distance = test1.GetDistance(point);
        double moveRatio = 1.0;
        
        
        Vector5d marchResult = rayMarch(test1, point, ray, epsilon, iters, distanceMax, moveRatio);
//        while (
//                distance>epsilon
//                &&steps++<iters
//                &&distance<distanceMax){
//            
//           
//            point = point.Add(ray.Scale(distance*moveRatio));
//            distance = test1.GetDistance(point);
//            
//        }
       
        point = marchResult.GetXYZ();
        Vector2d otherData = marchResult.GetVW();
        double steps = otherData.y;
        double distance = otherData.x; 

        
       
        
        Vector3d backOffABit = point.Add(ray.Negate().Scale(epsilon*10));
        
        Vector3d shadowRay = lightSource.Subtract(backOffABit).GetUnitVector().Negate();
        Vector3d shadowRay2 = lightSource2.Subtract(backOffABit).GetUnitVector().Negate();
        
        int shadowIttrs = 100;
        Vector5d marchResultShadow = rayMarch(test1,backOffABit,shadowRay,epsilon,shadowIttrs,distanceMax,1);
        Vector5d marchResultShadow2 = rayMarch(test1,backOffABit,shadowRay2,epsilon,shadowIttrs,distanceMax,1);
        
        double shadow = 1;
        double shadow2 = 1;
        
        double shadowDistance = marchResultShadow.GetVW().x;
        double shadowDistance2 = marchResultShadow2.GetVW().x;
        double shadowCount = marchResultShadow.GetVW().y;
        double shadowCount2 = marchResultShadow2.GetVW().y;
        
        if (shadowDistance<=epsilon||shadowCount>=shadowIttrs){
            shadow=0;
        }
        if (shadowDistance2<=epsilon||shadowCount2>=shadowIttrs){
            shadow2=0;
        }
        
        
        Vector3d normal = test1.GetSlope(point, epsilon/10d).GetUnitVector().Negate();
        Vector3d lightRay = lightSource.Subtract(point).GetUnitVector();
        Vector3d lightRay2 = lightSource2.Subtract(point).GetUnitVector();
        
        
        
        
        
        
        double diffuse = max(normal.DotProduct(lightRay),0) ;
        double diffuse2 = max(normal.DotProduct(lightRay2),0);
        
        Vector3d diffuseColor = lightColor.Scale(diffuse*shadow);
        Vector3d diffuseColor2 = lightColor2.Scale(diffuse2*shadow2);
        
        double si = steps/(double)iters;
        
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
            return vec4(0,si,0,1.0);
        }
        return vec4(0,0,0,1);
    }
    
}
