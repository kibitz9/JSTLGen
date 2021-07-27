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
public class _ShaderStringTest {
    public static void main(String[] args){
//        SDF3dPrimitiveSphere sphere = new SDF3dPrimitiveSphere(1.0);
//        //SDF3dPrimitiveSphere sphere2 = new SDF3dPrimitiveSphere(1.1);
//        //SDFOperationTranslate t = new SDFOperationTranslate(sphere,new Vector3d(1,.5,.25));
//        
//        
//        SDF3dPrimitiveBox box = new SDF3dPrimitiveBox(new Vector3d(.5,2,3));
//        //t = new SDFOperationTranslate(box,new Vector3d(1,.5,.25));
//        
//        
//        //SDFOperationCSGUnion union = new SDFOperationCSGUnion(t,sphere2);
//        
//        //SDFOperationUniformScale scale = new SDFOperationUniformScale(t,.9);
//        
//        SDFOperationOnion o = new SDFOperationOnion(sphere,.2);
//        
//        SignedDistanceField3d d = new SDFOperationCSGDifference(o,box);
//        
//        
//        
//        SDFOperationTranslate t2 = new SDFOperationTranslate(d,new Vector3d(0,-.25,0));
//        
//        ShaderString ss = t2.toShaderString("p");
        
        /*
        SignedDistanceField3d test = new SDF3dPrimitiveSphere(10);
        test = new SDFOperationOnion(test,4);
        test = new SDFOperationOnion(test,1);
        test = new SDFOperationOnion(test,.25);
        test = new SDFOperationSmoothAxisCut(test,SDFOperationSmoothAxisCut.Axis.X,0,true,2.0);
        
        test = new SDFOperationSmoothAxisCut(test,SDFOperationSmoothAxisCut.Axis.X,-5,false,2.0);
        test = new SDFOperationUniformScale(test,.5);
        SignedDistanceField3d test2 = new SDF3dPrimitiveTorus(10,1);
        SignedDistanceField3d test3 = new SDF3dPrimitiveBox(100,.5,100);
        SignedDistanceField3d test4 = new SDFOperationSmoothDifference(test2,test3,.1);
        SignedDistanceField3d test5 = new SDFOperationTrigRotateX(test4,.24);
        
        SignedDistanceField3d test6 = new SDF3dPrimitiveTorus(8,.5);
        SignedDistanceField3d test7 = new SDFOperationTrigRotateZ(test6,.24);
        
        SignedDistanceField3d test8 = new SDF3dPrimitiveTorus(4,.25);
        SignedDistanceField3d test8a = new SDF3dPrimitiveBox(100,100,1);
        SignedDistanceField3d test8b = new SDFOperationSmoothDifference(test8,test8a,.1);
        SignedDistanceField3d test9 = new SDFOperationTrigRotateY(test8b,.24);
        
        test = new SDFOperationSmoothUnion(test,test5,.1);
        test = new SDFOperationSmoothUnion(test,test7,.1);
        test =  new SDFOperationSmoothUnion(test,test9,.1);
        
        SignedDistanceField3d bx1 = new SDF3dPrimitiveBox(new Vector3d(10,1,1));
        
        test = new SDFOperationSmoothIntersection(bx1,test,1.4);
        SignedDistanceField3d final1=test;
        */
        /*brainthing
        SignedDistanceField3d gyr = new SDFDistortionGyroid(.5,.5,0,0);
        
        SignedDistanceField3d gyr2 = new SDFDistortionGyroid(.133,.133,0,0);
        
        SignedDistanceField3d o1 = new SDFOperationOnion(gyr2,.01);
        
        SignedDistanceField3d diff1 = new SDFOperationSmoothDifference(gyr,o1,1);
        
       
        
        SignedDistanceField3d xx = new SDF3dPrimitiveSphere(5);
        SignedDistanceField3d xxx = new SDFOperationSmoothIntersection(xx,diff1,1);
        
       SignedDistanceField3d xxxx = new SDFOperationUniformScale(xxx,2);
*/
        
        /* box and sphere */
        
        /*
        SDF3dPrimitiveBox box2 = new SDF3dPrimitiveBox(new Vector3d(5,5,5));
        SDFOperationTranslate t2 = new SDFOperationTranslate(box2,new Vector3d(0,-4,0));

        SDF3dPrimitiveSphere s = new SDF3dPrimitiveSphere(3.);
        SDFOperationTranslate t3 = new SDFOperationTranslate(s,new Vector3d(0,4.,0));
        
        
        SDFOperationRoundEdges round = new SDFOperationRoundEdges(t2,1);
        
        //SDFOperationCSGUnion u = new SDFOperationCSGUnion(t,round);
        SDFOperationCSGUnion u2 = new SDFOperationCSGUnion(round,t3);
        
        SDF3dPrimitiveTorus torus = new SDF3dPrimitiveTorus(7,1);
        
        SDF3dPrimitiveTorus torus3 = new SDF3dPrimitiveTorus(7,1);
        SDFOperationTrigRotateX rotx = new SDFOperationTrigRotateX(torus3,Math.PI/2);
        
        
        
        SDFOperationCSGUnion u3 = new SDFOperationCSGUnion(torus,rotx);
        SDFOperationCSGUnion u4 = new SDFOperationCSGUnion(u3,u2);
        
        
        SDFOperationRepeatZ repeatZ = new SDFOperationRepeatZ(u4,5,5,25,false);
        SDFOperationRepeatY repeatY = new SDFOperationRepeatY(repeatZ,5,5,25,false);
        SDFOperationRepeatX repeatX = new SDFOperationRepeatX(repeatY,5,5,25,false);
        
        SDF3dPrimitiveSphere b = new SDF3dPrimitiveSphere(90);
        SDFOperationCSGUnion u = new SDFOperationCSGUnion(b,repeatX);
        
        */
        
        
        //SDFOperationRepeatY repeatY = new SDFOperationRepeatY(repeatX,2,2,20);
        //SDFOperationRepeatZ repeatZ = new SDFOperationRepeatZ(repeatY,2,2,20);
        
        //SDF3dPrimitiveBox box = new SDF3dPrimitiveBox(new Vector3d(1000,10,1000));
        //SDFOperationTranslate t = new SDFOperationTranslate(box,new Vector3d(0,-20,0));
        //SDFOperationCSGUnion u = new SDFOperationCSGUnion(t,repeatX);
        //SignedDistanceField3d u = repeatX;
        /**/
        
        
        //SDFOperationTrigRotateX rotx = new SDFOperationTrigRotateX(u,-.2);  
        
        /*capsule
        SignedDistanceField3d capsule = new SDF3dPrimitiveCapsule(10,3);

        */
        
//        SignedDistanceField3d cone = new SDF3dPrimitiveCone(3.14159264/4,3);
//        SignedDistanceField3d rot = new SDFOperationTrigRotateX(cone,15.0);
//
//        SignedDistanceField3d sa = new SDF3dPrimitiveSolidAngle(3.14159264/4,3);
//        sa = new SDFOperationTranslate(sa,new Vector3d(3,3,3));
//        SignedDistanceField3d uu = new SDFOperationCSGUnion(sa,rot);
//        
//        uu = new SDFOperationTrigRotateY(uu,1.0);
//        uu = new SDFOperationTrigRotateZ(uu,3.0);
        

/*
        SDF3dPrimitiveSolidAngle a = new SDF3dPrimitiveSolidAngle(3.14159/4.0,40);
        SDF3dPrimitivePlane p = new SDF3dPrimitivePlane(new Vector3d(0.,1.0,0.0),0);
        SDF3dPrimitivePlane p2 = new SDF3dPrimitivePlane(new Vector3d(1.0,0,0.0),0);
        SDF3dPrimitivePlane p3 = new SDF3dPrimitivePlane(new Vector3d(0.0,0,1.0),0);
        SDFOperationSmoothUnion u1 = new SDFOperationSmoothUnion(p,p2,10.);
        SDFOperationSmoothUnion u2 = new SDFOperationSmoothUnion(u1,p3,10.);
  
        //SDFOperationCSGDifference diff = new SDFOperationCSGDifference(p,a);
        //SignedDistanceField3d uu = new SDFOperationTrigRotateZ(diff,3.14159265/2.0);
    
        SDF3dPrimitiveSphere s = new SDF3dPrimitiveSphere(10);
        SDFOperationSmoothIntersection i = new SDFOperationSmoothIntersection(u2,s,1.0);
        double smooth = 5.;
        
        SDF3dPrimitiveInfiniteSpokesZAxis is = new SDF3dPrimitiveInfiniteSpokesZAxis(70,5.,31);
        SDF3dPrimitiveInfiniteSpokesZAxis is2 = new SDF3dPrimitiveInfiniteSpokesZAxis(74,2.5,49);
        SDF3dPrimitiveInfiniteSpokesZAxis is3 = new SDF3dPrimitiveInfiniteSpokesZAxis(78,1.25,67);
        SDF3dPrimitiveInfiniteSpokesZAxis is4 = new SDF3dPrimitiveInfiniteSpokesZAxis(80,.625,101);
        SDFOperationSmoothUnion u7 = new SDFOperationSmoothUnion(is,is2,is3,is4,smooth);
        
        
        SDF3dPrimitiveSphere sss = new SDF3dPrimitiveSphere(180);
        SDFOperationSmoothIntersection iii = new SDFOperationSmoothIntersection(u7,sss,smooth);
        SDFOperationSmoothDifference uuu = new SDFOperationSmoothDifference(iii,new SDF3dPrimitiveSphere(175),smooth);
      */
      /* -- Good test item... */
      
      
      /*
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
        mb=new SDFOperationBoundingSphere(mb,2.3,.01);
        
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
        
        //surface = new SDFDistortionNoise(surface,.125,10);
        
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
        env = new SDFDistortionNoise(env,.1,20);
        
        
        SignedDistanceField3d env2 = new SDF3dPrimitiveBox(new Vector3d(100,10,1));
        env2 = new SDFMaterial(env2,new MaterialFunction(new Vector3d(1.9,1.9,2)));
        env2 = new SDFDistortionNoise(env2,.1,20);
        env2 = new SDFOperationTranslate(env2,new Vector3d(0,0,3));
        
        
        
        u1 = new SDFOperationCSGUnion(u1,env2);
        u1 = new SDFOperationCSGUnion(u1,env);
        SignedDistanceField3d final1 = new SDFOperationCSGUnion(u1,mb);
       
        
        
        
        //final1 = new SDFMaterial(final1,new MaterialFunction(new Vector3d(1,1,1)));
        
        */
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
        
        /*
        SignedDistanceField3d spokes = new SDF3dPrimitiveInfiniteSpokesZAxis(2.,.01,500.);
        
        SignedDistanceField3d sphere = new SDF3dPrimitiveSphere(1.);
        SignedDistanceField3d i = new SDFOperationCSGIntersection(spokes,sphere);
        sphere = new SDF3dPrimitiveSphere(.95);
        i=new SDFOperationCSGUnion(i,sphere);
        i = new SDFOperationTrigRotateY(i,Math.PI/2.);
        SignedDistanceField3d final1 = sphere;
        
        
        */
        /*
        SignedDistanceField3d sphere = new SDF3dPrimitiveSphere(1.);
        SignedDistanceField3d gyroid = new SDFDistortionGyroid(.5,.5,0,0);
        SignedDistanceField3d scale = new SDFOperationUniformScale(gyroid,.1);
        SignedDistanceField3d onion = new SDFOperationOnion(scale,.09);
        onion = new SDFOperationOnion(onion,.03);
        
        SignedDistanceField3d mb2 = new SDF3dPrimitiveMandelbox(10,-2.5,.5,1);
        mb2 = new SDFOperationUniformScale(mb2,.33);
        onion = new SDFOperationCSGIntersection(mb2,onion);
        SDFMaterial mat1 = new SDFMaterial(onion,new MaterialFunction(new Vector3d(1,0,0)));
        onion = new SDFOperationOnion(mat1,.01);
        SignedDistanceField3d surfaceDetail = new SDFOperationSurfaceDetailOnion(sphere,onion,.1);
        */
        
        double ss = -1.5;
        double rr = .15;
        int itr = 9;
        
        SignedDistanceField3d mb2 = new SDF3dPrimitiveMandelbox(itr,ss,rr,1);
        mb2 = new SDFOperationTrigRotateX(mb2,3.14159265/4.);
        SignedDistanceField3d mb3 = new SDF3dPrimitiveMandelbox(itr,ss,rr,1);
        mb3 = new SDFOperationCSGUnion(mb3,mb2);
        
        SignedDistanceField3d mb4 = new SDFOperationTrigRotateY(mb3,3.14159265/4.);
        mb3 = new SDFOperationCSGUnion(mb3,mb4);
        
        
        mb3 = new SDFOperationUniformScale(mb3,.5);
        
        SignedDistanceField3d sphere = new SDF3dPrimitiveSphere(1.);
        mb3 = new SDFOperationSurfaceDetailOnion(sphere,mb3,.1);
        
        mb3 = new SDFOperationSectorDuplicateYAxis(mb3,12);
        
        SignedDistanceField3d final1 = mb3;
        
        final1 = sphere;
        
        String initialVarName = "p";
        ShaderString ss2 = final1.toShaderString(initialVarName);
        
        //test = new SDFDistortionSin(test,4,.25);
        System.out.println(ss2.generateString("map7",initialVarName));
        //SignedDistanceField3d mb2 = new SDF3dPrimitiveMandelbox(10,-2.5,.5,1);
    }
}
