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
public class ImageThreeChannelPolar {
    public Buffer2DPolar red;
    public Buffer2DPolar green;
    public Buffer2DPolar blue;
    
    public ImageThreeChannelPolar(Buffer2DPolar red, Buffer2DPolar green, Buffer2DPolar blue){
        this.red=red;
        this.green=green;
        this.blue=blue;
    }
    
    public ImageThreeChannelPolar fft(){
        Buffer2DPolar newRed = red.fft();
        Buffer2DPolar newGreen = green.fft();
        Buffer2DPolar newBlue = blue.fft();
        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
    }
    
//    public ImageThreeChannelPolar toMonochrome(){
//        Buffer2D newRed = red.avg(green,blue);
//        return new ImageThreeChannelPolar(newRed,newRed,newRed);
//    }
    
    public ImageThreeChannelPolar ifft(){
        Buffer2DPolar newRed = red.ifft();
        Buffer2DPolar newGreen = green.ifft();
        Buffer2DPolar newBlue = blue.ifft();
        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
    }
    
//    public ImageThreeChannelPolar showBrightestRealValue(){
//        Buffer2D newRed = red.showBrightestRealValue();
//        Buffer2D newGreen = green.showBrightestRealValue();
//        Buffer2D newBlue = blue.showBrightestRealValue();
//        return new ImageThreeChannelPolar(newRed, newGreen, newBlue);
//    }
    
//    private void loadImage(String filePath){
//        ImageThreeChannel cartesian = new ImageThreeChannel(2);
//        cartesian.lo
        
        
//        try{
//            java.io.File f = new java.io.File(filePath);
//            java.awt.image.BufferedImage bi = ImageIO.read(f);
//            int width = bi.getWidth();
//            int height = bi.getHeight();
//            
//            System.out.println(width);
//            System.out.println(height);
//            int powerOf2 = 1;
//            while (powerOf2<width){
//                powerOf2*=2;
//            }
//            while (powerOf2<height){
//                powerOf2*=2;
//            }
//            Buffer1D[] redRows = new Buffer1D[powerOf2];
//            Buffer1D[] greenRows = new Buffer1D[powerOf2];
//            Buffer1D[] blueRows = new Buffer1D[powerOf2];
//            //allocate our buffers
//            for (int y=0;y<powerOf2;y++){
//                double[] redReals = new double[powerOf2];
//                double[] redImaginaries = new double[powerOf2];
//                
//                double[] greenReals = new double[powerOf2];
//                double[] greenImaginaries = new double[powerOf2];
//                
//                double[] blueReals = new double[powerOf2];
//                double[] blueImaginaries = new double[powerOf2];
//                
//                for(int x=0;x<powerOf2;x++){
//                    if (y<height&&x<width){
//                        int p = bi.getRGB(x,y);
//                    
//                        java.awt.Color temp = new java.awt.Color(p);
//                        redReals[x]=temp.getRed();
//                        //System.out.println(redReals[x]);
//                        greenReals[x]=temp.getGreen();
//                        blueReals[x]=temp.getBlue();
//                    }
//                    //int alphaInt = (p>>24)&0xff;
//                }
//                redRows[y] = new Buffer1D(redReals,redImaginaries);
//                greenRows[y] = new Buffer1D(greenReals,greenImaginaries);
//                blueRows[y] = new Buffer1D(blueReals,blueImaginaries);
//                //System.out.println(bi.getType());
//            }
//            
//            Buffer2D redBufferCart = new Buff
//            
//            red = new Buffer2DPolar(new Buffer2D(redRows));
//            green = new Buffer2DPolar(new Buffer2D(greenRows));
//            blue = new Buffer2DPolar(new Buffer2D(blueRows));
//        }
//        catch(java.io.IOException e){
//            System.out.println(e.getMessage());
//        }
        
//    }
    
    public ImageThreeChannelPolar(String filePath){
        
        ImageThreeChannel temp = new ImageThreeChannel(filePath);
        this.red = new Buffer2DPolar(temp.red);
        this.green = new Buffer2DPolar(temp.green);
        this.blue = new Buffer2DPolar(temp.blue);
    }
    
    
    
//    
//    public void writeNonZeros(){
//        for (int a=0;a<this.red.buffers1d.length;a++){
//            for (int b=0;b<this.red.buffers1d[a].reals.length;b++){
//                if (this.red.buffers1d[a].reals[b]>0){
//                    System.out.println(this.red.buffers1d[a].reals[b]);
//                }
//            }
//        }
//    }
    private int clamp(int n, int low, int high){
        if (n<low){
            return low;
        }
        if (n>high){
            return high;
        }
        return n;
    }
   
    
    public ImageThreeChannelPolar lowPass(double amt){
        Buffer2DPolar newRed = this.red.lowPass(amt);
        Buffer2DPolar newGreen = this.green.lowPass(amt);
        Buffer2DPolar newBlue = this.blue.lowPass(amt);
        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
    }
    
    public ImageThreeChannelPolar lowPass(int cutoff){
        Buffer2DPolar newRed = this.red.lowPass(cutoff);
        Buffer2DPolar newGreen = this.green.lowPass(cutoff);
        Buffer2DPolar newBlue = this.blue.lowPass(cutoff);
        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
    }
    
     public ImageThreeChannelPolar highPass(double amt){
        Buffer2DPolar newRed = this.red.highPass(amt);
        Buffer2DPolar newGreen = this.green.highPass(amt);
        Buffer2DPolar newBlue = this.blue.highPass(amt);
        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
    }
    public ImageThreeChannelPolar highPass(int cutoff){
        Buffer2DPolar newRed = this.red.highPass(cutoff);
        Buffer2DPolar newGreen = this.green.highPass(cutoff);
        Buffer2DPolar newBlue = this.blue.highPass(cutoff);
        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
    }
//    public ImageThreeChannelPolar merge(ImageThreeChannelPolar other, int cutoff){
//        Buffer2D newRed = this.red.merge(other.red, cutoff);
//        Buffer2D newGreen = this.green.merge(other.green,cutoff);
//        Buffer2D newBlue = this.blue.merge(other.blue,cutoff);
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
//    public ImageThreeChannelPolar blend(ImageThreeChannelPolar other, double amount){
//        Buffer2D newRed = this.red.blend(other.red, amount);
//        Buffer2D newGreen = this.green.blend(other.green,amount);
//        Buffer2D newBlue = this.blue.blend(other.blue,amount);
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
    
//    public ImageThreeChannelPolar threshold(double amt){
//        Buffer2D newRed = this.red.threshold(amt);
//        Buffer2D newGreen = this.green.threshold(amt);
//        Buffer2D newBlue = this.blue.threshold(amt);
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
    
//    public int countNonZeroComponents(){
//        int count = 0;
//        count +=red.countNonZeroComponents();
//        count +=green.countNonZeroComponents();
//        count +=blue.countNonZeroComponents();
//        return count;
//    }
    
//    public ImageThreeChannel zeroOutNegativeFrequencies(){
//        Buffer2D newRed = this.red.zeroOutNegativeFrequencies();
//        Buffer2D newGreen = this.green.zeroOutNegativeFrequencies();
//        Buffer2D newBlue = this.blue.zeroOutNegativeFrequencies();
//        return new ImageThreeChannel(newRed,newGreen,newBlue);
//    }
//    
//    public ImageThreeChannel populateNegativeFromPositiveFrequencies(){
//        Buffer2D newRed = this.red.populateNegativeFromPositiveFrequencies();
//        Buffer2D newGreen = this.green.populateNegativeFromPositiveFrequencies();
//        Buffer2D newBlue = this.blue.populateNegativeFromPositiveFrequencies();
//        return new ImageThreeChannel(newRed,newGreen,newBlue);
//    }
//    public int countZeroComponents(){
//        int count = 0;
//        count +=red.countZeroComponents();
//        count +=green.countZeroComponents();
//        count +=blue.countZeroComponents();
//        return count;
//    }
//    
//    public ImageThreeChannelPolar topNMagnitudes(int waveCount){
//        Buffer2D newRed = this.red.topNMagnitudes(waveCount);
//        Buffer2D newGreen = this.green.topNMagnitudes(waveCount);
//        Buffer2D newBlue = this.blue.topNMagnitudes(waveCount);
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
//    
//    public ImageThreeChannelPolar topNExperimental(int waveCount){
//        Buffer2D newRed = this.red.topNExperimental(waveCount);
//        Buffer2D newGreen = this.green.topNExperimental(waveCount);
//        Buffer2D newBlue = this.blue.topNExperimental(waveCount);
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
//    
//    public ImageThreeChannelPolar topNReals(int waveCount){
//        Buffer2D newRed = this.red.topNReals(waveCount);
//        Buffer2D newGreen = this.green.topNReals(waveCount);
//        Buffer2D newBlue = this.blue.topNReals(waveCount);
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
//    
//    public ImageThreeChannelPolar topNImaginaries(int waveCount){
//        Buffer2D newRed = this.red.topNReals(waveCount);
//        Buffer2D newGreen = this.green.topNReals(waveCount);
//        Buffer2D newBlue = this.blue.topNReals(waveCount);
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
//    
//    public ImageThreeChannelPolar inversethreshold(double amt){
//        Buffer2D newRed = this.red.inversethreshold(amt);
//        Buffer2D newGreen = this.green.inversethreshold(amt);
//        Buffer2D newBlue = this.blue.inversethreshold(amt);
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
//    
//    public ImageThreeChannelPolar reduceToFloatPrecision(){
//        Buffer2D newRed = this.red.reduceToFloatPrecision();
//        Buffer2D newGreen = this.green.reduceToFloatPrecision();
//        Buffer2D newBlue = this.blue.reduceToFloatPrecision();
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
//    
//    public ImageThreeChannelPolar reduceToHalfPrecision(){
//        Buffer2D newRed = this.red.reduceToHalfPrecision();
//        Buffer2D newGreen = this.green.reduceToHalfPrecision();
//        Buffer2D newBlue = this.blue.reduceToHalfPrecision();
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
//    
    public ImageThreeChannelPolar swapAxis(){
        Buffer2DPolar newRed = this.red.SwapAxis();
        Buffer2DPolar newGreen = this.green.SwapAxis();
        Buffer2DPolar newBlue = this.blue.SwapAxis();
        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
    }
    public ImageThreeChannelPolar scale(double scalar){
        Buffer2DPolar newRed = this.red.scale(scalar);
        Buffer2DPolar newGreen = this.green.scale(scalar);
        Buffer2DPolar newBlue = this.blue.scale(scalar);
        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
    }
    
    public ImageThreeChannelPolar multiply(ImageThreeChannelPolar other){
        Buffer2DPolar newRed = this.red.multiply(other.red);
        Buffer2DPolar newGreen = this.green.multiply(other.green);
        Buffer2DPolar newBlue = this.blue.multiply(other.blue);
        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
    }
    public ImageThreeChannelPolar divide(ImageThreeChannelPolar other){
        Buffer2DPolar newRed = this.red.divide(other.red);
        Buffer2DPolar newGreen = this.green.divide(other.green);
        Buffer2DPolar newBlue = this.blue.divide(other.blue);
        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
    }
    
//    public ImageThreeChannelPolar swapRealsAndImaginaries(){
//        Buffer2DPolar newRed = this.red.swapRealsAndImaginaries();
//        Buffer2DPolar newGreen = this.green.swapRealsAndImaginaries();
//        Buffer2DPolar newBlue = this.blue.swapRealsAndImaginaries();
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
    
//    public ImageThreeChannelPolar clearReals(){
//        Buffer2D newRed = this.red.clearReals();
//        Buffer2D newGreen = this.green.clearReals();
//        Buffer2D newBlue = this.blue.clearReals();
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
//    public ImageThreeChannelPolar clearImaginaries(){
//        Buffer2D newRed = this.red.clearImaginaries();
//        Buffer2D newGreen = this.green.clearImaginaries();
//        Buffer2D newBlue = this.blue.clearImaginaries();
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
    
    public ImageThreeChannelPolar add(ImageThreeChannelPolar other){
        Buffer2DPolar newRed = this.red.add(other.red);
        Buffer2DPolar newGreen = this.green.add(other.green);
        Buffer2DPolar newBlue = this.blue.add(other.blue);
        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
    }
    
    public ImageThreeChannelPolar toUnitVectors(){
        Buffer2DPolar newRed = this.red.toUnitVectors();
        Buffer2DPolar newGreen = this.green.toUnitVectors();
        Buffer2DPolar newBlue = this.blue.toUnitVectors();
        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
    }
    
//    public ImageThreeChannelPolar abs(){
//        Buffer2DPolar newRed = this.red.abs();
//        Buffer2DPolar newGreen = this.green.abs();
//        Buffer2DPolar newBlue = this.blue.abs();
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
    public ImageThreeChannelPolar subtract(ImageThreeChannelPolar other){
        Buffer2DPolar newRed = this.red.subtract(other.red);
        Buffer2DPolar newGreen = this.green.subtract(other.green);
        Buffer2DPolar newBlue = this.blue.subtract(other.blue);
        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
    }
    public int getSize(){
        return this.red.buffers1d.length;
    }
    
//    public ImageThreeChannelPolar resize(int newSize){
//        Buffer2D newRed = this.red.resize(newSize);
//        Buffer2D newGreen = this.green.resize(newSize);
//        Buffer2D newBlue = this.blue.resize(newSize);
//        return new ImageThreeChannelPolar(newRed,newGreen,newBlue);
//    }
    
    public void WriteRealsToImageFile(String path){
        
        ImageThreeChannel c = new ImageThreeChannel(this);
        c.WriteRealsToImageFile(path);
        
  
    }
    
//    public void WriteMagnitudesToImageFile(String path){
//         ImageThreeChannel c = new ImageThreeChannel(this);
//        int widthHeight = red.buffers1d.length;
//        java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(widthHeight,widthHeight,2);
//        for (int y=0;y<widthHeight;y++){
//            for(int x=0;x<widthHeight;x++){
//                double r = Math.sqrt(
//                        red.buffers1d[y].reals[x]*red.buffers1d[y].reals[x]
//                        +red.buffers1d[y].imaginaries[x]*red.buffers1d[y].imaginaries[x]);
//                double g = Math.sqrt(
//                        green.buffers1d[y].reals[x]*green.buffers1d[y].reals[x]
//                        +green.buffers1d[y].imaginaries[x]*green.buffers1d[y].imaginaries[x]);
//                double b = Math.sqrt(
//                        blue.buffers1d[y].reals[x]*blue.buffers1d[y].reals[x]
//                        +blue.buffers1d[y].imaginaries[x]*blue.buffers1d[y].imaginaries[x]);
//                
//                int redInt = clamp((int)r,0,255);
//                int greenInt = clamp((int)g,0,255);
//                int blueInt = clamp((int)b,0,255);
//                
//                java.awt.Color temp = new java.awt.Color(redInt,greenInt,blueInt);
//                //System.out.println(temp.getRGB());
////                if (temp.getRed()>0){
////                    System.out.println(temp.getRed());
////                }
//                bi.setRGB(x,y,temp.getRGB());
//                //bi.setRGB(x,y,0xff0000ff);
//            }
//        }
//        try{
//            ImageIO.write(bi,"png", new java.io.File(path));
//        }
//        catch(java.lang.Exception err){
//            System.out.println(err.getMessage());
//        }
//        
//    }
    
//    
//    public static ImageThreeChannelPolar EDGEDETECT3X3 = new ImageThreeChannelPolar(
//            Buffer2D.EDGEDETECT3X3,
//            Buffer2D.EDGEDETECT3X3,
//            Buffer2D.EDGEDETECT3X3
//    );
//    
//    public static ImageThreeChannelPolar EDGEDETECT6X6 = new ImageThreeChannelPolar(
//            Buffer2D.EDGEDETECT6X6,
//            Buffer2D.EDGEDETECT6X6,
//            Buffer2D.EDGEDETECT6X6
//    );
//    
//    public static ImageThreeChannelPolar EDGEDETECT9X9 = new ImageThreeChannelPolar(
//            Buffer2D.EDGEDETECT9X9,
//            Buffer2D.EDGEDETECT9X9,
//            Buffer2D.EDGEDETECT9X9
//    );
//    
//     public static ImageThreeChannelPolar EDGEDETECT9X9INVERTED = new ImageThreeChannelPolar(
//            Buffer2D.EDGEDETECT9X9INVERTED,
//            Buffer2D.EDGEDETECT9X9INVERTED,
//            Buffer2D.EDGEDETECT9X9INVERTED
//    );
//    
   
    
    
    public void WriteImaginariesToImageFile(String path){
        ImageThreeChannel c = new ImageThreeChannel(this);
        c.WriteImaginariesToImageFile(path);
        
    }
}