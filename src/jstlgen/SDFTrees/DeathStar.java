/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen.SDFTrees;

import jstlgen.MaterialFunction;
import jstlgen.SDF3dPrimitiveBox;
import jstlgen.SDF3dPrimitiveMandelbox;
import jstlgen.SDF3dPrimitivePlane;
import jstlgen.SDF3dPrimitiveSphere;
import jstlgen.SDF3dPrimitiveTorus;
import jstlgen.SDFMaterial;
import jstlgen.SDFOperationBoundingSphere;
import jstlgen.SDFOperationCSGUnion;
import jstlgen.SDFOperationSectorDuplicateYAxis;
import jstlgen.SDFOperationSectorDuplicateZAxis;
import jstlgen.SDFOperationSurfaceDetailOnion;
import jstlgen.SDFOperationTranslate;
import jstlgen.SDFOperationTrigRotateX;
import jstlgen.SDFOperationTrigRotateY;
import jstlgen.SDFOperationUniformScale;
import jstlgen.ShaderString;
import jstlgen.SignedDistanceField3d;
import jstlgen.Vector3d;

/**
 *
 * @author cmiller
 */
public class DeathStar {
    public static void main(String[] args){
        
      
        SignedDistanceField3d mb = new SDF3dPrimitiveMandelbox(10,-2.5,.5,1);
        //SignedDistanceField3d mb2 = new SDF3dPrimitiveMandelbox(10,-2,.5,1);
        
        //mb2=new SDFOperationUniformScale(mb2,3.1);
        mb=new SDFOperationUniformScale(mb,.75);
        
        mb=new SDFOperationTrigRotateX(mb,3.14159265/4.);
        
        double divs = 20;
        mb = new SDFOperationSectorDuplicateZAxis(mb,divs);
        mb = new SDFOperationSectorDuplicateYAxis(mb,divs);
        mb = new SDFOperationSectorDuplicateZAxis(mb,divs);
        //mb = new SDFOperationSectorDuplicateZAxis(mb,divs);
       
        //mb = new SDFOperationTrigRotateX(mb,3.14159265/4);
        //mb2=new SDF3dPrimitiveSphere(3);
        
        //mb = new SDFOperationBoundingBox(mb,4);
        mb=new SDFOperationBoundingSphere(mb,2.3,.0001);
        
        SignedDistanceField3d sphere2 = new SDF3dPrimitiveSphere(1.9);
        sphere2 = new SDFMaterial(sphere2,new MaterialFunction(new Vector3d(1,0,0)));
        mb=new SDFOperationCSGUnion(mb,sphere2);
        
        mb=new SDFOperationUniformScale(mb,.3);
        
        mb= new SDFOperationTrigRotateY(mb,"iTime/5.");
        //SignedDistanceField3d sph = new SDF3dPrimitiveSphere(3);
        //sph=new SDFOperationTranslate(sph,new Vector3d(0,0,.1)); 
        //mb = new SDFOperationCSGDifference(mb,sph);
        //SDFOperationCSGIntersection i = new SDFOperationCSGIntersection(mb,mb2);
        
        mb = new SDFOperationTranslate(mb,new Vector3d(-1.5,0,0));
      
      
        SignedDistanceField3d mb2 = new SDF3dPrimitiveMandelbox(10,-2.5,.5,1);
        //mb2=new SDFOperationRoundEdges(mb2,.01);
        
        
        SignedDistanceField3d box = new SDF3dPrimitiveBox(new Vector3d(.95,.95,.95));
        
        box = new SDFMaterial(box,new MaterialFunction(new Vector3d(1,1,2)));
        
        SignedDistanceField3d surface = new SDFOperationSurfaceDetailOnion(box,mb2,.1);
        
        
        
        
        surface = new SDFOperationUniformScale(surface,.5);
        surface= new SDFOperationTrigRotateY(surface,"-iTime/5.");
        
       
        
        SignedDistanceField3d ts = new SDF3dPrimitiveTorus(.55,.2);
        
        ts = new SDFMaterial(ts,new MaterialFunction(new Vector3d(2,1,0)));
        
        SignedDistanceField3d mb3 = new SDF3dPrimitiveMandelbox(10,-2.9,.5,1);
        
        mb3 = new SDFOperationUniformScale(mb3,.7);
        
        
        
        ts = new SDFOperationSurfaceDetailOnion(ts,mb3,.05);
        //ts = new SDFOperationCSGIntersection(mb3,ts);
        
        
        ts = new SDFOperationSectorDuplicateYAxis(ts,12);//here!!
        
        ts=new SDFOperationTrigRotateX(ts,3.14159265/4.);
        
        ts=new SDFOperationTrigRotateY(ts,"iTime/5.");
        ts = new SDFOperationTranslate(ts,new Vector3d(1.5,0,0));
        
        SignedDistanceField3d u1 = new SDFOperationCSGUnion(ts,surface);
        
        SignedDistanceField3d env = new SDF3dPrimitivePlane(new Vector3d(0,1,0),1.1);
        env = new SDFMaterial(env,new MaterialFunction(new Vector3d(1,.9,.9)));
        
        SignedDistanceField3d env2 = new SDF3dPrimitiveBox(new Vector3d(100,10,1));
        env2 = new SDFMaterial(env2,new MaterialFunction(new Vector3d(1.9,1.9,2)));
        
        env2 = new SDFOperationTranslate(env2,new Vector3d(0,0,3));
        
        u1 = new SDFOperationCSGUnion(u1,env2);
        u1 = new SDFOperationCSGUnion(u1,env);
        SignedDistanceField3d final1 = new SDFOperationCSGUnion(u1,mb);
       
        
        
        //final1 = new SDFMaterial(final1,new MaterialFunction(new Vector3d(1,1,1)));
        
        
        //***********************//
        
        
        /*the following is a good unit test for color lookup */
        /*
        SignedDistanceField3d box11 = new SDF3dPrimitiveBox(new Vector3d(1,1,1));
        SignedDistanceField3d sphere11 = new SDF3dPrimitiveSphere(1.1);
        SDFMaterial matbox11 = new SDFMaterial(box11,new MaterialFunction(new Vector3d(1,0,0)));
        SDFMaterial matsphere11 = new SDFMaterial(sphere11,new MaterialFunction(new Vector3d(0,0,1)));
        SignedDistanceField3d xxxx = new SDFOperationCSGIntersection(matbox11,matsphere11);
        
        
        xxxx = new SDFOperationTrigRotateX(xxxx,"iTime/5.");
        
        xxxx = new SDFOperationTrigRotateY(xxxx,"iTime/7.");
        
        
        xxxx = new SDFOperationTrigRotateZ(xxxx,"iTime/11.");
        
        
        xxxx = new SDFOperationTranslate(xxxx,new Vector3d(.1,.1,.1));
        
        xxxx = new SDFOperationUniformScale(xxxx,.5);
        
        
        final1=xxxx;
        */
        
        
        
        
        
        
        
        //SignedDistanceField3d final1 = mb2;
        String initialVarName = "p";
        ShaderString ss = final1.toShaderString(initialVarName);
        
        //test = new SDFDistortionSin(test,4,.25);
        System.out.println(ss.generateString("map7",initialVarName));
    }
}
