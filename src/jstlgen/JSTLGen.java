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
        int threadCount = 8;
        
        long start = System.currentTimeMillis();
        
        SDFSphere c = new SDFSphere(1.0);
        Solid s = Solid.HedronTriangulate(c, .05, .05, .000001, threadCount);
        
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
