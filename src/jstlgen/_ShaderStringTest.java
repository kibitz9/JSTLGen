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
        
        /* box and sphere
        SDF3dPrimitiveBox box = new SDF3dPrimitiveBox(new Vector3d(1000,10,1000));
        SDFOperationTranslate t = new SDFOperationTranslate(box,new Vector3d(0,-20,0));
        
        SDF3dPrimitiveBox box2 = new SDF3dPrimitiveBox(new Vector3d(5,5,5));
        SDFOperationTranslate t2 = new SDFOperationTranslate(box2,new Vector3d(0,-4,0));

        SDF3dPrimitiveSphere s = new SDF3dPrimitiveSphere(3.);
        SDFOperationTranslate t3 = new SDFOperationTranslate(s,new Vector3d(0,4.,0));
        
        
        SDFOperationRoundEdges round = new SDFOperationRoundEdges(t2,1);
        
        SDFOperationCSGUnion u = new SDFOperationCSGUnion(t,round);
        SDFOperationCSGUnion u2 = new SDFOperationCSGUnion(u,t3);
        */
        
        
        //SDFOperationTrigRotateX rotx = new SDFOperationTrigRotateX(u,-.2);  
        
        /*capsule
        SignedDistanceField3d capsule = new SDF3dPrimitiveCapsule(10,3);

        */
        
        SignedDistanceField3d cone = new SDF3dPrimitiveCone(3.14159264/4,3);
        SignedDistanceField3d rot = new SDFOperationTrigRotateX(cone,15.0);

        SignedDistanceField3d sa = new SDF3dPrimitiveSolidAngle(3.14159264/4,3);
        sa = new SDFOperationTranslate(sa,new Vector3d(3,3,3));
        SignedDistanceField3d uu = new SDFOperationCSGUnion(sa,rot);
        
        uu = new SDFOperationTrigRotateY(uu,1.0);
        uu = new SDFOperationTrigRotateZ(uu,3.0);
        SignedDistanceField3d final1 = uu;
        ShaderString ss = final1.toShaderString("p");
        //test = new SDFDistortionSin(test,4,.25);
        System.out.println(ss.generateString());
        
    }
}
