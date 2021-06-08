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

//uniform vec3      iResolution;           // viewport resolution (in pixels)
//uniform float     iTime;                 // shader playback time (in seconds)
//uniform float     iTimeDelta;            // render time (in seconds)
//uniform int       iFrame;                // shader playback frame
//uniform float     iChannelTime[4];       // channel playback time (in seconds)
//uniform vec3      iChannelResolution[4]; // channel resolution (in pixels)
//uniform vec4      iMouse;                // mouse pixel coords. xy: current (if MLB down), zw: click
//uniform samplerXX iChannel0..3;          // input channel. XX = 2D/Cube
//uniform vec4      iDate;                 // (year, month, day, time in seconds)
//uniform float     iSampleRate;           // sound sample rate (i.e., 44100)

public abstract class SoftwareShader {
    
    protected Vector3d iResolution;
    protected Vector4d iMouse;
    
    
    javax.swing.JPanel target;
    protected int softwareThreads;
    boolean running = false;
    protected java.awt.image.BufferedImage b;
    protected int width=0;
    protected int height=0;
    protected double widthd;
    protected double heightd;
    
    protected double halfWidthd;
    protected double halfHeightd;
    
    protected java.awt.Graphics2D g;
    protected int[] array;
    protected int scansize;
    protected java.lang.Thread t;
    
    protected double iTime;
    
    
    public Vector2d vec2(double x,double y){
        return new Vector2d(x,y);
    }
    
    public Vector3d vec3(double x, double y, double z){
        return new Vector3d(x,y,z);
    }
    public Vector4d vec4(double x, double y, double z, double w){
        return new Vector4d(x,y,z,w);
    }
    
    public Vector4d vec4(Vector3d t, double w){
        return new Vector4d(t,w);
    }
    
    
    public Vector5d rayMarch(SignedDistanceField3d sdf, Vector3d point, Vector3d ray, double epsilon, double iters, double maxDist, double moveRatio){
        double distance = sdf.GetDistance(point);
        int steps = 0;
        while (
                distance>epsilon
                &&steps++<iters
                &&distance<maxDist){
            
           
            point = point.Add(ray.Scale(distance*moveRatio));
            distance = sdf.GetDistance(point);
            
        }
        return new Vector5d(point,distance,(double)steps);
    }
    
    public SoftwareShader(javax.swing.JPanel target, int softwareThreads){
        this.target=target;
        this.softwareThreads=softwareThreads;
        
    }
    public void start(){
        if (!this.running){
            this.running = true;
            this.g=(java.awt.Graphics2D)target.getGraphics();
            this.t = new th();
            t.start();
        }
        
    }
    private void updateBufferedImageSize(){
        if (width!=target.getWidth()||height!=target.getHeight()){
            this.width = target.getWidth();
            this.height = target.getHeight();
            this.b = new java.awt.image.BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_4BYTE_ABGR);
            this.array = new int[width*height];
            this.scansize = this.width;
            this.g = (java.awt.Graphics2D) target.getGraphics();
            this.widthd = (double)width;
            this.heightd = (double)height;
            this.halfWidthd = widthd/2.0;
            this.halfHeightd = heightd/2.0;
            this.iResolution = new Vector3d(widthd,heightd,32);
        }
    }
    public void pause(){
        this.running = false;
    }
    
    public class th extends java.lang.Thread{
        @Override 
        public void run(){
            
            long millisPerFrame = 1000/60;//30 frames a second
            long startTimeInNanos = System.nanoTime();
            double startTimeInMicros = startTimeInNanos/1000;
            double startTimeInSeconds = startTimeInMicros/1000000d;
            ParallelThread[] ts = new ParallelThread[softwareThreads];
            while(running){
                long frameStartTime=System.nanoTime();
                iTime = (frameStartTime-startTimeInNanos)/1000;
                iTime/=1000000d;
                
                updateBufferedImageSize();
                
                for (int a=0;a<softwareThreads;a++){
                    ts[a]=new ParallelThread(a,softwareThreads);
                    ts[a].start();
                }
                
                for (int a=0;a<softwareThreads;a++){
                    boolean done = false;
                    while (true){
                       
                        try{
                            ts[a].join();
                            if (ts[a].done){
                                break;
                            }
                        }
                        catch(java.lang.InterruptedException err){
                            //nop
                        }
                    }
                        
                }
                b.setRGB(0,0,width,height,array,0,width);
                g.drawImage(b,0,0,null);
                
                //process();
                long elapsedMillis = (System.nanoTime()-frameStartTime)/1000000;
                long additional = millisPerFrame-elapsedMillis;
                if (additional>0){
                    try{
                        Thread.sleep(additional);
                    }
                    catch(java.lang.InterruptedException err){
                        String x = "";
                    }
                }
            }
        }
    }
   
    
    public class ParallelThread extends java.lang.Thread{
        public int threadNumber;
        public int threadCount;
        public boolean done = false;
        public ParallelThread(int threadNumber, int threadCount){
            this.threadCount = threadCount;
            this.threadNumber = threadNumber;
        }
        @Override 
        public void run(){
            
            for (int x=threadNumber;x<width;x+=threadCount){
                for(int y=0;y<height;y++){
                    Vector4d pixel = mainImage(new Vector2d(x,y));
                    array[y*scansize + x]=pixel.ToARGBInt();
                }
            }
            done = true;
            //b.setRGB(0,0,width,height,array,0,width);
            //g.drawImage(b,0,0,null);
        }

       
    }
        
        
    
//    startX - the starting X coordinate
//startY - the starting Y coordinate
//w - width of the region
//h - height of the region
//rgbArray - the rgb pixels
//offset - offset into the rgbArray
//scansize - scanline stride for the rgbArray
//    public void process(){
//        
//        updateBufferedImageSize();
//        for (int x=0;x<width;x++){
//            for(int y=0;y<height;y++){
//                Vector4d pixel = mainImage(new Vector2d(x,y));
//                array[y*scansize + x]=pixel.ToARGBInt();
//            }
//        }
//        b.setRGB(0,0,width,height,array,0,width);
//        g.drawImage(b,0,0,null);
//    }
    //public abstract Vector4d GetPixel(int x, int y); 
    
    public abstract Vector4d mainImage(Vector2d fragCoord);
    
    public static Vector2d cos(Vector2d one){
        return new Vector2d(Math.cos(one.x), Math.cos(one.y));
    }
    
     public static Vector3d cos(Vector3d one){
        return new Vector3d(Math.cos(one.x), Math.cos(one.y), Math.cos(one.z));
    }
    
    protected static double fract(double value){
        return value - (Math.floor(value));
    }
    
    protected static double sin(double angle){
        return Math.sin(angle);
    }
    protected static double cos(double angle){
        return Math.cos(angle);
    }
    
    protected static double sign(double number){
        return Math.signum(number);
    }
    //3d helpers
    protected static double clamp(double value, double min, double max){
        if (value<min){
            return min;
        }
        if (value>max){
            return max;
        }
        return value;
    }
    protected static double min(double val1, double val2){
        if (val1<val2){
            return val1;
        }
        return val2;
    }
    protected static double max(double val1, double val2){
        if (val1>val2){
            return val1;
        }
        return val2;
    }
    protected static Vector3d max(Vector3d vect, double val){
        return vect.ComponentwiseMax(val);
    }
    protected static double max(Vector3d vect){
        return vect.MaximumComponent();
    }
    protected static double length(Vector3d vect){
        return vect.GetMagnitude();
    }
    protected static Vector3d abs(Vector3d vect){
        return vect.Abs();
    }
    protected static double abs(double val){
        return Math.abs(val);
    }
    protected static double sqrt(double val){
        return Math.sqrt(val);
    }
    
    //2d helpers
      
   
    protected static Vector2d max(Vector2d vect, double val){
        return vect.ComponentwiseMax(val);
    }
    protected static double max(Vector2d vect){
        return vect.MaximumComponent();
    }
    protected static double length(Vector2d vect){
        return vect.GetMagnitude();
    }
    protected static Vector2d abs(Vector2d vect){
        return vect.Abs();
    }
    
    
        
}
