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
public abstract class SoftwareShader {
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
        }
    }
    public void pause(){
        this.running = false;
    }
    
    public class th extends java.lang.Thread{
        @Override 
        public void run(){
            
            long millisPerFrame = 1000/60;//30 frames a second
            double startTimeInSeconds = System.nanoTime()/1000;
            startTimeInSeconds/=1000000d;
            
            while(running){
                long startTime=System.nanoTime();
                iTime = startTime/1000;
                iTime/=1000000d;
                
                process();
                long elapsedMillis = (System.nanoTime()-startTime)/1000000;
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
   
//    startX - the starting X coordinate
//startY - the starting Y coordinate
//w - width of the region
//h - height of the region
//rgbArray - the rgb pixels
//offset - offset into the rgbArray
//scansize - scanline stride for the rgbArray
    public void process(){
        
        updateBufferedImageSize();
        for (int x=0;x<width;x++){
            for(int y=0;y<height;y++){
                ShaderColor pixel = GetPixel(x,y);
                array[y*scansize + x]=pixel.ToInt();
            }
        }
        b.setRGB(0,0,width,height,array,0,width);
        g.drawImage(b,0,0,null);
    }
    public abstract ShaderColor GetPixel(int x, int y); 
    
    
    
    protected static double fract(double value){
        return value - (Math.floor(value));
    }
    
}
