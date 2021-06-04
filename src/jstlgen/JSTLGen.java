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
public class JSTLGen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int threadCount = 4;
        System.out.println(threadCount);
        long start = System.currentTimeMillis();
        double epsilon = .0000001;
        //SignedDistanceField3d c = new SDFSphere(1.0);
        SignedDistanceField3d c = new SDFBox(new Vector3d(1.0001,1.0001,1.0001));
        SignedDistanceField3d c2 = new SDFSphere(1.20001);
        c2 = new SDFAxisCut(c2,SDFAxisCut.Axis.Z,0,false);
        
        c=new SDFSmoothDifference(c,c2,.2);
        
        //c = new SDFDistortionRatio(c,.9999999);
        Solid s = Solid.HedronTriangulate(c, .08, .08, epsilon, threadCount);
        
        
        double threshold = .05;
        int refinements = 5;
        for (int a=0;a<refinements;a++){
            System.out.println("Refinement "+a);
            s=s.ShrinkTowardsSlope(c, epsilon, threadCount, true);
            s=s.ShrinkTowardsSlope(c, epsilon, threadCount, true);
            s=s.SideSplitStressedFaces(c, epsilon, threadCount, threshold);
            s=s.ShrinkTowardsSlope(c, epsilon, threadCount, true);
            s=s.ShrinkTowardsSlope(c, epsilon, threadCount, true);
        }
        
        
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
