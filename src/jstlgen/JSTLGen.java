/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen;
import jstlgen.ComplexFields.*;
/**
 *
 * @author Christopher.Miller
 */
public class JSTLGen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int threadCount =12;
        System.out.println(threadCount);
        long start = System.currentTimeMillis();
        double epsilon = .005;
        //double epsilon = .00001;
        //SignedDistanceField3d c = new SDFSphere(1.0);
        
        //SignedDistanceField3d phull = new SDFEnterprisePrimaryHull();
        
        
        
//        SignedDistanceField3d c = new SDFBox(new Vector3d(1.0,1.0,1.0));
//        SignedDistanceField3d c2 = new SDFSphere(1.2);
        
        //c=new SDFSmoothDifference(c,c2,.2);
        
//        phull = new SDFOperationAxisCut(phull,SDFOperationAxisCut.Axis.Z,0,true);
//        
//        SignedDistanceField3d capsule = new SDF3dPrimitiveCapsule(SignedDistanceField.RAD45,20);
//        phull=capsule;
//        
//        SignedDistanceField3d cone = new SDF3dPrimitiveCone(SignedDistanceField.RAD45,20);
//        SignedDistanceField3d smooth = new SDF3dOperationRoundEdges(cone,.1);
//        
//        SignedDistanceField3d repeat = new SDFOperationRepeatX(smooth,3,3,2);
//        SignedDistanceField3d rotate = new SDFOperationTrigRotateZ(repeat,.1);
//        SignedDistanceField3d repeat2 = new SDFOperationRepeatY(smooth,3,3,2);
//        
//        
//        SignedDistanceField3d translate = new SDFOperationTranslate(repeat2,0,-.5,0);
//        SignedDistanceField3d elongate = new SDFOperationElongate(translate, new Vector3d(0.0,1.0,0.0));
//        
//        
        //SignedDistanceField3d round = new SDFOperationRoundEdges(phull,10);
        
        //phull = new SDF3dPrimitiveTorus(50,10);
        //interesting and pretty good...
        
//        SDF3dPrimitivePoint p1 = new SDF3dPrimitivePoint(new Vector3d(1,0,1));
//        SDF3dPrimitivePoint p2 = new SDF3dPrimitivePoint(new Vector3d(-1,0,1));
//        SDF3dPrimitivePoint p3 = new SDF3dPrimitivePoint(new Vector3d(1,0,-1));
//        SDF3dPrimitivePoint p4 = new SDF3dPrimitivePoint(new Vector3d(-1,0,-1));
//        
//
//        SignedDistanceField3d r1 = new SDFOperationRoundEdges(p1,.5);
//        SignedDistanceField3d r2 = new SDFOperationRoundEdges(p2,.5);
//        SignedDistanceField3d r3 = new SDFOperationRoundEdges(p3,.5);
//        SignedDistanceField3d r4 = new SDFOperationRoundEdges(p4,.5);
//        
//        SignedDistanceField3d u1 = new SDFOperationSmoothUnion(r1,r2,3);
//        SignedDistanceField3d u2 = new SDFOperationSmoothUnion(r3,r4,3);
//        SignedDistanceField3d u3 = new SDFOperationSmoothUnion(u1,u2,3);
//        
        
//        SignedDistanceField3d final1 = u3;
//        final1 = new SDFOperationOnion(final1,.01);
//        final1 = new SDFOperationAxisCut(final1,SDFOperationAxisCut.Axis.Z,0,false);
        double splitThreshold = .0125;

        //Solid s = Solid.Triangulate(final1, 1, epsilon, threadCount,.5,2);
        
        //final1 = new SDF3dPrimitiveSphere(160);
        //Solid s = Solid.Triangulate(final1, 40, 40, .00000001, 12, 1,.001);
        
        //SDF3dPrimitiveMandelbox mb = new SDF3dPrimitiveMandelbox(12,-2.7,.666666,1.05);
        SDF3dPrimitiveMandelbox mb = new SDF3dPrimitiveMandelbox(12,2,.5,1.05);
        SignedDistanceField3d final1 = mb;
        Solid s = Solid.HedronTriangulate(final1,1.,1., epsilon, threadCount);
        
        
        
        
        
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        s=s.SideSplitStressedFaces(final1, epsilon, threadCount, splitThreshold);
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        s=s.SideSplitStressedFaces(final1, epsilon, threadCount, splitThreshold);
////        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
////         s=s.SideSplitStressedFaces(final1, epsilon, threadCount, splitThreshold);
////        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
////         s=s.SideSplitStressedFaces(final1, epsilon, threadCount, splitThreshold);
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        //s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        //s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        //s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        
//        s=s.SideSplitStressedFaces(final1, epsilon, threadCount, splitThreshold);
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        
//        s=s.SideSplitStressedFaces(final1, epsilon, threadCount, splitThreshold);
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
        System.out.println("here");
        for (int a=0;a<5;a++){
            //s=s.SideSplitStressedFaces(final1, epsilon, threadCount, splitThreshold);
            //s=s.ShrinkWrap(final1);
            s=s.Subdivide();
           
            s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
            s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
            s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
            s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
            s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
            s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
            s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
            s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
        }
        
        
      //  s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        s=s.SideSplitStressedFaces(final1, epsilon, threadCount, splitThreshold);
//        s=s.SideSplitStressedFaces(final1, epsilon, threadCount, splitThreshold);
//        s=s.SideSplitStressedFaces(final1, epsilon, threadCount, splitThreshold);
//        s=s.SideSplitStressedFaces(final1, epsilon, threadCount, splitThreshold);
//        s=s.SideSplitStressedFaces(final1, epsilon, threadCount, splitThreshold);
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        //s=s.SideSplitStressedFaces(final1, epsilon, threadCount, .125);
//        //s=s.Subdivide();
//        s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
        
//        
//        
//        double threshold = .5;
//        int refinements = 10;
//        for (int a=0;a<refinements;a++){
//            //s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//            s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//            //s=s.SideSplitStressedFaces(phull, epsilon, threadCount, threshold);
//            s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//            //s=s.ShrinkTowardsSlope(final1, epsilon, threadCount, true);
//        }
        
        
        long end = System.currentTimeMillis();
        System.out.println((end-start));
        
        byte[] b = s.toBinary();
        try{
            java.io.FileOutputStream ff = new java.io.FileOutputStream("c:\\data\\test4.stl");
            java.io.BufferedOutputStream bo = new java.io.BufferedOutputStream(ff);
            bo.write(b);
            bo.close();
            ff.close();
        }
        catch(java.lang.Exception any){
            System.out.println(any.getMessage());
        }
        
        
        
        
    }
    
}
