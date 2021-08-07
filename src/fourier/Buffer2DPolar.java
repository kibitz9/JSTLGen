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
public  class Buffer2DPolar{
    public Buffer1DPolar[] buffers1d;
    public Buffer2DPolar(Buffer1DPolar[] buffers1d){
        this.buffers1d=buffers1d;
    }
  
    public Buffer2DPolar(Buffer2D bufferCartesian){
        buffers1d = new Buffer1DPolar[bufferCartesian.buffers1d.length];
        for (int a=0;a<buffers1d.length;a++){
            buffers1d[a]=new Buffer1DPolar(bufferCartesian.buffers1d[a]);
        }
    }
    
    public Buffer2DPolar fft(){
        //first, fft all the individual rows...
        Buffer2DPolar temp = this.onedfft();
        temp=temp.SwapAxis();
        temp = temp.onedfft();
        temp=temp.SwapAxis();
        return temp;
    }
    
    
    
    public Buffer2DPolar ifft(){
        Buffer2DPolar temp = this.onedifft();
        temp=temp.SwapAxis();
        temp = temp.onedifft();
        temp=temp.SwapAxis();
        return temp;
    }
    
    public Buffer2DPolar scale(double scalar){
        Buffer1DPolar[] newBuffers = new Buffer1DPolar[buffers1d.length];
        for(int a=0;a<newBuffers.length;a++){
            newBuffers[a]=buffers1d[a].scale(scalar);
        }
        return new Buffer2DPolar(newBuffers);
    }
    
//    public Buffer2DPolar reduceToFloatPrecision(){
//        Buffer1DPolar[] newBuffers = new Buffer1DPolar[buffers1d.length];
//        for(int a=0;a<newBuffers.length;a++){
//            newBuffers[a]=buffers1d[a].reduceToFloatPrecision();
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
//    public Buffer2DPolar reduceToHalfPrecision(){
//        Buffer1DPolar[] newBuffers = new Buffer1DPolar[buffers1d.length];
//        for(int a=0;a<newBuffers.length;a++){
//            newBuffers[a]=buffers1d[a].reduceToHalfPrecision();
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
    
    private double clamp(double n, double low, double high){
        if (n<low){
            return low;
        }
        if (n>high){
            return high;
        }
        return n;
    }
   
    public Buffer2DPolar lowPass(double amt){
        amt = clamp(amt,0,1);
        int cutoff = (int)(((double)buffers1d.length)*amt)/2;
        
        return lowPass(cutoff);
    }
    
    public Buffer2DPolar lowPass(int cutoff){
       
        
        Buffer1DPolar[] newBuffers = new Buffer1DPolar[buffers1d.length];
        for(int a=0;a<newBuffers.length;a++){
            if (a<=cutoff||a>=(buffers1d.length-(cutoff+1))){
                newBuffers[a]=buffers1d[a].lowPass(cutoff);
            }
            else{
                newBuffers[a]=new Buffer1DPolar(buffers1d.length);
            }
        }
        return new Buffer2DPolar(newBuffers);
    }
    
//    public Buffer2D zeroOutNegativeFrequencies(){
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        for(int a=0;a<newBuffers.length;a++){
//            if (a<=buffers1d.length/2){
//                newBuffers[a]=buffers1d[a].zeroOutNegativeFrequencies();
//                
//            }
//            else{
//               // newBuffers[a]=buffers1d[a].zeroOutNegativeFrequencies();
//                newBuffers[a]=new Buffer1D(new double[buffers1d.length]);
//            }
//        }
//        return new Buffer2D(newBuffers);
//    }
//    
//    public Buffer2D populateNegativeFromPositiveFrequencies(){
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        int length = buffers1d.length;
//        for(int a=0;a<newBuffers.length;a++){
//            if (a<=buffers1d.length/2){
//                newBuffers[a]=buffers1d[a].populateNegativeFromPositiveFrequencies();
//                
//            }
//            else{
//                //newBuffers[a]=buffers1d[a].populateNegativeFromPositiveFrequencies();
//                newBuffers[a]=buffers1d[length-a].populateNegativeFromPositiveFrequencies().GetConjugates();
//                
//            }
//        }
//        return new Buffer2D(newBuffers);
//    }
//   
    
//    public Buffer2DPolar clone(boolean sizeOnly){
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        for (int a=0;a<newBuffers.length;a++){
//            newBuffers[a]=this.buffers1d[a].clone(sizeOnly);
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
//    public Buffer2DPolar clone(){
//        return clone(false);
//    }
//    
    
    public Buffer2DPolar highPass(double amt){
        amt = clamp(amt,0,1);
        int cutoff = (int)(((double)buffers1d.length)*amt)/2;
        
        return highPass(cutoff);
    }
    
    public Buffer2DPolar highPass(int cutoff){
        
        Buffer1DPolar[] newBuffers = new Buffer1DPolar[buffers1d.length];
        for(int a=0;a<newBuffers.length;a++){
            if (a>=cutoff&&a<=(buffers1d.length-(cutoff+1))){
                newBuffers[a]=buffers1d[a].highPass(cutoff);
            }
            else{
                newBuffers[a]=new Buffer1DPolar(buffers1d.length);
            }
        }
        return new Buffer2DPolar(newBuffers);
    }
    
    
    
//    public Buffer2DPolar merge(Buffer2DPolar other, int cutoff){
//        
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        for(int a=0;a<newBuffers.length;a++){
//            if (a<=cutoff||a>=(buffers1d.length-(cutoff+1))){
//                newBuffers[a]=buffers1d[a].merge(other.buffers1d[a],cutoff);
//            }
//            else{
//                newBuffers[a]=other.buffers1d[a];//I have to think about this. Is this right?
//            }
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
    
//    public Buffer2DPolar blend(Buffer2DPolar other, double amount){
//        
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        for(int a=0;a<newBuffers.length;a++){
//            
//                newBuffers[a]=buffers1d[a].blend(other.buffers1d[a],amount);
//            
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
    
//    public Buffer2DPolar threshold(double amount){
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        for (int a=0;a<buffers1d.length;a++){
//            newBuffers[a]=buffers1d[a].threshold(amount);
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
    
//    public Buffer2DPolar topNMagnitudes(int waveCount){
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        for(int a=0;a<newBuffers.length;a++){
//            newBuffers[a]=buffers1d[a].topNMagnitudes(waveCount);
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
//    
//    public Buffer2DPolar topNExperimental(int waveCount){
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        for(int a=0;a<newBuffers.length;a++){
//            newBuffers[a]=buffers1d[a].topNExperimental(waveCount);
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
//    
//    public Buffer2DPolar topNReals(int waveCount){
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        for(int a=0;a<newBuffers.length;a++){
//            newBuffers[a]=buffers1d[a].topNReals(waveCount);
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
//    public Buffer2DPolar topNImaginaries(int waveCount){
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        for(int a=0;a<newBuffers.length;a++){
//            newBuffers[a]=buffers1d[a].topNReals(waveCount);
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
//    
//    public int countNonZeroComponents(){
//        int count = 0;
//        for(int a=0;a<buffers1d.length;a++){
//            count+=buffers1d[a].countNonZeroComponents();
//        }
//        return count;
//    }
//    public int countZeroComponents(){
//        int count = 0;
//        for(int a=0;a<buffers1d.length;a++){
//            count+=buffers1d[a].countZeroComponents();
//        }
//        return count;
//    }
    
//    public Buffer2DPolar clearReals(){
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        for (int a=0;a<buffers1d.length;a++){
//            newBuffers[a]=buffers1d[a].clearReals();
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
//    public Buffer2DPolar clearImaginaries(){
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        for (int a=0;a<buffers1d.length;a++){
//            newBuffers[a]=buffers1d[a].clearImaginaries();
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
    
//    public Buffer2DPolar swapRealsAndImaginaries(){
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        for (int a=0;a<buffers1d.length;a++){
//            newBuffers[a]=buffers1d[a].swapRealsAndImaginaries();
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
//    
//    public Buffer2DPolar inversethreshold(double amount){
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        for (int a=0;a<buffers1d.length;a++){
//            newBuffers[a]=buffers1d[a].inversethreshold(amount);
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
    
//    private static Buffer2DPolar getEdgeDetect3x3(){
//       
//        double[] x = new double[3];
//        x[0]=-1./8.;
//        x[1]=-1./8.;
//        x[2]=-1./8.;
//      
//        double[] y = new double[3];
//        y[0]=-1./8.;
//        y[1]=1.;
//        y[2]=-1./8.;
//      
//     
//       
//        
//        Buffer1D[] buffers = new Buffer1D[]{
//            new Buffer1D(x),
//            new Buffer1D(y),
//            new Buffer1D(x)
//        };
//        return new Buffer2DPolar(buffers);
//    }
//    
    
//    private static Buffer2DPolar getEdgeDetect6x6(){
//       
//        double[] x = new double[6];
//        x[0]=-1./32.;
//        x[1]=-1./32.;
//        x[2]=-1./32.;
//        x[3]=-1./32.;
//        x[4]=-1./32.;
//        x[5]=-1./32.;
//      
//        double[] y = new double[6];
//        y[0]=-1./32.;
//        y[1]=-1./32.;
//        y[2]=.25;
//        y[3]=.25;
//        y[4]=-1./32.;
//        y[5]=-1./32.;
//      
//
//        Buffer1D[] buffers = new Buffer1D[]{
//            new Buffer1D(x),
//            new Buffer1D(x),
//            new Buffer1D(y),
//            new Buffer1D(y),
//            new Buffer1D(x),
//            new Buffer1D(x),
//        };
//        return new Buffer2DPolar(buffers);
//    }
//    
//    private static Buffer2DPolar getEdgeDetect9x9(){
//       
//        double[] x = new double[9];
//        x[0]=-1./72.;
//        x[1]=-1./72.;
//        x[2]=-1./72.;
//        x[3]=-1./72.;
//        x[4]=-1./72.;
//        x[5]=-1./72.;
//        x[6]=-1./72.;
//        x[7]=-1./72.;
//        x[8]=-1./72.;
//      
//        double[] y = new double[9];
//        y[0]=-1./72.;
//        y[1]=-1./72.;
//        y[2]=-1./72.;
//        y[3]= 1./9.;
//        y[4]= 1./9.;
//        y[5]= 1./9.;
//        y[6]=-1./72.;
//        y[7]=-1./72.;
//        y[8]=-1./72.;
//      
//
//        Buffer1D[] buffers = new Buffer1D[]{
//            new Buffer1D(x),
//            new Buffer1D(x),
//            new Buffer1D(x),
//            new Buffer1D(y),
//            new Buffer1D(y),
//            new Buffer1D(y),
//            new Buffer1D(x),
//            new Buffer1D(x),
//            new Buffer1D(x),
//        };
//        return new Buffer2DPolar(buffers);
//    }
//    
//    private static Buffer2DPolar getEdgeDetect9x9Inverted(){
//       
//        double[] x = new double[9];
//        x[0]=1./72.;
//        x[1]=1./72.;
//        x[2]=1./72.;
//        x[3]=1./72.;
//        x[4]=1./72.;
//        x[5]=1./72.;
//        x[6]=1./72.;
//        x[7]=1./72.;
//        x[8]=1./72.;
//      
//        double[] y = new double[9];
//        y[0]=1./72.;
//        y[1]=1./72.;
//        y[2]=1./72.;
//        y[3]=- 1./9.;
//        y[4]=- 1./9.;
//        y[5]=- 1./9.;
//        y[6]=1./72.;
//        y[7]=1./72.;
//        y[8]=1./72.;
//      
//
//        Buffer1D[] buffers = new Buffer1D[]{
//            new Buffer1D(x),
//            new Buffer1D(x),
//            new Buffer1D(x),
//            new Buffer1D(y),
//            new Buffer1D(y),
//            new Buffer1D(y),
//            new Buffer1D(x),
//            new Buffer1D(x),
//            new Buffer1D(x),
//        };
//        return new Buffer2DPolar(buffers);
//    }
//    
    
//    public static Buffer2DPolar EDGEDETECT3X3 = getEdgeDetect3x3();
//    public static Buffer2DPolar EDGEDETECT6X6 = getEdgeDetect6x6();
//    public static Buffer2DPolar EDGEDETECT9X9 = getEdgeDetect9x9();
//    public static Buffer2DPolar EDGEDETECT9X9INVERTED = getEdgeDetect9x9Inverted();
//    
    public Buffer2DPolar multiply(Buffer2DPolar other){
        Buffer1DPolar[] newBuffers = new Buffer1DPolar[buffers1d.length];
        if (newBuffers.length<other.buffers1d.length){
            throw new java.lang.RuntimeException("Other buffer must currently be same size or smaller.");
            
        }
        for (int a=0;a<newBuffers.length;a++){
            if (a<other.buffers1d.length){
                newBuffers[a]=buffers1d[a].multiply(other.buffers1d[a]);
            }
            else{
                newBuffers[a]=new Buffer1DPolar(buffers1d.length);
            }
        }
        return new Buffer2DPolar(newBuffers);
    }
    
    public Buffer2DPolar toUnitVectors(){
        Buffer1DPolar[] newBuffers = new Buffer1DPolar[buffers1d.length];
        for (int a=0;a<buffers1d.length;a++){
            newBuffers[a]=buffers1d[a].toUnitVectors();
        }
        return new Buffer2DPolar(newBuffers);
    }
    
//    public Buffer2DPolar abs(){
//        Buffer1D[] newBuffers = new Buffer1D[buffers1d.length];
//        for (int a=0;a<buffers1d.length;a++){
//            newBuffers[a]=buffers1d[a].abs();
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
//    
//    public Buffer2DPolar resize(int newSize){
//        Buffer1D[] newBuffers = new Buffer1D[newSize];
//        for (int a=0;a<newSize;a++){
//            if (a<buffers1d.length){
//                newBuffers[a]=buffers1d[a].resize(newSize);
//            }
//            else{
//                newBuffers[a]=new Buffer1D(new double[newSize]);
//            }
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
    
    public Buffer2DPolar add(Buffer2DPolar other){
        Buffer1DPolar[] newBuffers = new Buffer1DPolar[buffers1d.length];
        if (newBuffers.length!=other.buffers1d.length){
            throw new java.lang.RuntimeException("Buffer sizes must match");
            
        }
        for (int a=0;a<newBuffers.length;a++){
            newBuffers[a]=buffers1d[a].add(other.buffers1d[a]);
        }
        return new Buffer2DPolar(newBuffers);
    }
    
     public Buffer2DPolar subtract(Buffer2DPolar other){
        Buffer1DPolar[] newBuffers = new Buffer1DPolar[buffers1d.length];
        if (newBuffers.length!=other.buffers1d.length){
            throw new java.lang.RuntimeException("Buffer sizes must match");
            
        }
        for (int a=0;a<newBuffers.length;a++){
            newBuffers[a]=buffers1d[a].subtract(other.buffers1d[a]);
        }
        return new Buffer2DPolar(newBuffers);
    }
    
     
    
    
    public Buffer2DPolar divide(Buffer2DPolar other){
        Buffer1DPolar[] newBuffers = new Buffer1DPolar[buffers1d.length];
        if (newBuffers.length!=other.buffers1d.length){
            throw new java.lang.RuntimeException("Buffer sizes must match");
            
        }
        for (int a=0;a<newBuffers.length;a++){
            newBuffers[a]=buffers1d[a].divide(other.buffers1d[a]);
        }
        return new Buffer2DPolar(newBuffers);
    }
    
    
    
   
    public Buffer2DPolar onedfft(){
        //fft only in one dimension
        Buffer1DPolar[] temp = new Buffer1DPolar[buffers1d.length];
        for (int a=0;a<buffers1d.length;a++){
            temp[a] = buffers1d[a].fft();
        }
        return new Buffer2DPolar(temp);
    }
    public Buffer2DPolar onedifft(){
        //fft only in one dimension
        Buffer1DPolar[] temp = new Buffer1DPolar[buffers1d.length];
        for (int a=0;a<buffers1d.length;a++){
            temp[a] = buffers1d[a].ifft();
        }
        return new Buffer2DPolar(temp);
    }
    
   
    
    public Buffer2DPolar SwapAxis(){
        if (buffers1d.length==0){
            return this;
        }
        if (buffers1d.length!=buffers1d[0].nodes.length){
            throw new java.lang.RuntimeException("Buffer axis swaps require row count and row length to be the same!");
        }
        int size = buffers1d.length;
        Buffer1DPolar[] returnBuffers = new Buffer1DPolar[size];
        for (int a=0;a<size;a++){
            ComplexPolar[] newComplexes = new ComplexPolar[size];
            for (int b=0;b<size;b++){
                newComplexes[b]=buffers1d[b].nodes[a];
               
            }
            returnBuffers[a]=new Buffer1DPolar(newComplexes);
        }
        return new Buffer2DPolar(returnBuffers);
    }

    @Override
    public String toString(){
        String returnString = "";
        for(int a=0;a<buffers1d.length;a++){
            returnString+=buffers1d[a].toString()+"\r\n";
        }
        return returnString;
    }
    
//    public Buffer2DPolar avg(Buffer2DPolar... otherBuffers){
//        Buffer2DPolar[] toParse = new Buffer2DPolar[otherBuffers.length+1];
//        toParse[0]=this;
//        for (int a=0;a<otherBuffers.length;a++){
//            toParse[a+1]=otherBuffers[a];
//        }
//        return average(toParse);
//       
//    }
    
//    public static Buffer1D[] extract1DBufferArrayAtIndex( int buffer1Dindex, Buffer2DPolar... buffersCollection){
//        Buffer1D[] returnBufferArray = new Buffer1D[buffersCollection.length];
//        for (int a=0;a<buffersCollection.length;a++){
//            returnBufferArray[a]=buffersCollection[a].buffers1d[buffer1Dindex];
//        }
//        return returnBufferArray;
//    }
//    public static Buffer2DPolar average(Buffer2DPolar... buffers){
//        Buffer1D[] newBuffers = new Buffer1D[buffers[0].buffers1d.length];
//        
//        for (int a=0;a<newBuffers.length;a++){
//            Buffer1D[] crossBuffers = extract1DBufferArrayAtIndex(a,buffers);
//            newBuffers[a]=Buffer1D.average(crossBuffers);
//        }
//        return new Buffer2DPolar(newBuffers);
//    }
    
    public static double max(double one, double two){
        if (one>two){
            return one;
        }
        return two;
    }
    
//    public Buffer2DPolar showBrightestRealValue(){
//        Buffer1D[] newBuffers = new Buffer1D[this.buffers1d.length];
//        double maxReal = Double.MIN_VALUE;
//        for (int a=0;a<this.buffers1d.length;a++){
//            for (int b=0;b<this.buffers1d[a].reals.length;b++){
//                maxReal=max(maxReal,this.buffers1d[a].reals[b]);
//            }
//        }
//        //System.out.println(maxReal);
//        for (int a=0;a<this.buffers1d.length;a++){
//            double[] newReals = new double[this.buffers1d[a].reals.length];
//            double[] newImaginaries = new double[newReals.length];
//            for (int b=0;b<this.buffers1d[a].reals.length;b++){
//                if (this.buffers1d[a].reals[b]==maxReal){
//                    newReals[b]=maxReal;
//                    //System.out.println(maxReal);
//                }
//                
//            }
//            newBuffers[a]=new Buffer1D(newReals,newImaginaries);
//        }
//        return new Buffer2DPolar(newBuffers);
//        
//        
//    }
    
}