/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourier;

import javax.imageio.ImageIO;

/**
 *
 * @author Christopher.Miller
 */
public class ImageThreeChannel {
    public Buffer2D red;
    public Buffer2D green;
    public Buffer2D blue;
    
    public ImageThreeChannel(Buffer2D red, Buffer2D green, Buffer2D blue){
        this.red=red;
        this.green=green;
        this.blue=blue;
    }
    
    public ImageThreeChannel fft(){
        Buffer2D newRed = red.fft();
        Buffer2D newGreen = green.fft();
        Buffer2D newBlue = blue.fft();
        return new ImageThreeChannel(newRed,newGreen,newBlue);
    }
    
    public ImageThreeChannel ifft(){
        Buffer2D newRed = red.ifft();
        Buffer2D newGreen = green.ifft();
        Buffer2D newBlue = blue.ifft();
        return new ImageThreeChannel(newRed,newGreen,newBlue);
    }
    
    public ImageThreeChannel(String filePath){
        
        try{
            java.io.File f = new java.io.File(filePath);
            java.awt.image.BufferedImage bi = ImageIO.read(f);
            int width = bi.getWidth();
            int height = bi.getHeight();
            
            System.out.println(width);
            System.out.println(height);
            int powerOf2 = 1;
            while (powerOf2<width){
                powerOf2*=2;
            }
            while (powerOf2<height){
                powerOf2*=2;
            }
            Buffer1D[] redRows = new Buffer1D[powerOf2];
            Buffer1D[] greenRows = new Buffer1D[powerOf2];
            Buffer1D[] blueRows = new Buffer1D[powerOf2];
            //allocate our buffers
            for (int y=0;y<powerOf2;y++){
                double[] redReals = new double[powerOf2];
                double[] redImaginaries = new double[powerOf2];
                
                double[] greenReals = new double[powerOf2];
                double[] greenImaginaries = new double[powerOf2];
                
                double[] blueReals = new double[powerOf2];
                double[] blueImaginaries = new double[powerOf2];
                
                for(int x=0;x<powerOf2;x++){
                    if (y<height&&x<width){
                        int p = bi.getRGB(x,y);
                    
                        java.awt.Color temp = new java.awt.Color(p);
                        redReals[x]=temp.getRed();
                        //System.out.println(redReals[x]);
                        greenReals[x]=temp.getGreen();
                        blueReals[x]=temp.getBlue();
                    }
                    //int alphaInt = (p>>24)&0xff;
                }
                redRows[y] = new Buffer1D(redReals,redImaginaries);
                greenRows[y] = new Buffer1D(greenReals,greenImaginaries);
                blueRows[y] = new Buffer1D(blueReals,blueImaginaries);
                //System.out.println(bi.getType());
            }
            
            red = new Buffer2D(redRows);
            green = new Buffer2D(greenRows);
            blue = new Buffer2D(blueRows);
        }
        catch(java.io.IOException e){
            System.out.println(e.getMessage());
        }
        
    }
    
    public void writeNonZeros(){
        for (int a=0;a<this.red.buffers1d.length;a++){
            for (int b=0;b<this.red.buffers1d[a].reals.length;b++){
                if (this.red.buffers1d[a].reals[b]>0){
                    System.out.println(this.red.buffers1d[a].reals[b]);
                }
            }
        }
    }
    private int clamp(int n, int low, int high){
        if (n<low){
            return low;
        }
        if (n>high){
            return high;
        }
        return n;
    }
   
    
    public ImageThreeChannel lowPass(double amt){
        Buffer2D newRed = this.red.lowPass(amt);
        Buffer2D newGreen = this.green.lowPass(amt);
        Buffer2D newBlue = this.blue.lowPass(amt);
        return new ImageThreeChannel(newRed,newGreen,newBlue);
    }
    
     public ImageThreeChannel highPass(double amt){
        Buffer2D newRed = this.red.highPass(amt);
        Buffer2D newGreen = this.green.highPass(amt);
        Buffer2D newBlue = this.blue.highPass(amt);
        return new ImageThreeChannel(newRed,newGreen,newBlue);
    }
    
    
    public ImageThreeChannel scale(double scalar){
        Buffer2D newRed = this.red.scale(scalar);
        Buffer2D newGreen = this.green.scale(scalar);
        Buffer2D newBlue = this.blue.scale(scalar);
        return new ImageThreeChannel(newRed,newGreen,newBlue);
    }
    
    public ImageThreeChannel multiply(ImageThreeChannel other){
        Buffer2D newRed = this.red.multiply(other.red);
        Buffer2D newGreen = this.green.multiply(other.green);
        Buffer2D newBlue = this.blue.multiply(other.blue);
        return new ImageThreeChannel(newRed,newGreen,newBlue);
    }
    public ImageThreeChannel divide(ImageThreeChannel other){
        Buffer2D newRed = this.red.divide(other.red);
        Buffer2D newGreen = this.green.divide(other.green);
        Buffer2D newBlue = this.blue.divide(other.blue);
        return new ImageThreeChannel(newRed,newGreen,newBlue);
    }
    
    public void WriteRealsToImageFile(String path){
        int widthHeight = red.buffers1d.length;
        java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(widthHeight,widthHeight,2);
        for (int y=0;y<widthHeight;y++){
            for(int x=0;x<widthHeight;x++){
                int redInt = clamp((int)red.buffers1d[y].reals[x],0,255);
                int greenInt = clamp((int)green.buffers1d[y].reals[x],0,255);
                int blueInt = clamp((int)blue.buffers1d[y].reals[x],0,255);
                
                java.awt.Color temp = new java.awt.Color(redInt,greenInt,blueInt);
                //System.out.println(temp.getRGB());
//                if (temp.getRed()>0){
//                    System.out.println(temp.getRed());
//                }
                bi.setRGB(x,y,temp.getRGB());
                //bi.setRGB(x,y,0xff0000ff);
            }
        }
        try{
            ImageIO.write(bi,"png", new java.io.File(path));
        }
        catch(java.lang.Exception err){
            System.out.println(err.getMessage());
        }
        
    }
}