/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourier;

/**
 *
 * @author Christopher.Miller
 */
public class Buffer1DPolar{
    public ComplexPolar[] nodes;

    public Buffer1DPolar(double[] reals, double[] imaginaries){
        nodes = new ComplexPolar[reals.length];
        for(int a=0;a<reals.length;a++){
            nodes[a]=new ComplexPolar(new Complex(reals[a],imaginaries[a]));
        }
        
    }
    
    
//    public Buffer1DPolar avg(Buffer1DPolar... others){
//        Buffer1DPolar[] more = new Buffer1DPolar[others.length+1];
//        more[0]=this;
//        for (int a=0;a<others.length;a++){
//            more[a+1]=others[a];
//        }
//        return average(more);
//    }
//    
//    public static Buffer1DPolar average(Buffer1DPolar... buffers){
//        double[] returnDoubleReal = new double[buffers[0].reals.length];
//        double[] returnDoubleImaginary = new double[buffers[0].imaginaries.length];
//        int bufferCount = buffers.length;
//        
//        if (bufferCount<1){
//            throw new java.lang.RuntimeException("No buffers!");
//                    
//        }
//        double bufferCountD = bufferCount;
//        int buffSize = buffers[0].reals.length;
//        
//        for (int a=0;a<buffSize;a++){
//            for (int b=0;b<bufferCount;b++){
//            
//                returnDoubleReal[a]+=buffers[b].reals[a];
//                returnDoubleImaginary[a]+=buffers[b].imaginaries[a];
//              
//            }
//            returnDoubleReal[a]/=bufferCountD;
//            returnDoubleImaginary[a]/=bufferCountD;
//        }
//        return new Buffer1DPolar(returnDoubleReal,returnDoubleImaginary);
//        
//    }
    
    
//    public Buffer1DPolar clone(boolean sizeOnly){
//        if (sizeOnly){
//            return new Buffer1DPolar(new double[reals.length],new double[imaginaries.length]);
//        }
//        else{
//            return clone();
//        }
//    }
//    @Override
//    public Buffer1DPolar clone(){
//        double[] newReals = new double[this.reals.length];
//        double[] newImaginaries = new double[this.imaginaries.length];
//        for (int a=0;a>newReals.length;a++){
//            newReals[a]=reals[a];
//            newImaginaries[a]=imaginaries[a];
//        }
//        return new Buffer1DPolar(newReals,newImaginaries);
//    }
//    
    
    
    
    public Buffer1DPolar GetConjugates(){
        ComplexPolar[] newComplexPolars = new ComplexPolar[nodes.length];
        
        for(int a=0;a<nodes.length;a++){
            newComplexPolars[a]=new ComplexPolar(nodes[a].modulus, -nodes[a].argument);
        }
        return new Buffer1DPolar(newComplexPolars);
    }

//    public Buffer1DPolar reduceToFloatPrecision(){
//        double[] newImaginaries = new double[imaginaries.length];
//        double[] newReals = new double[reals.length];
//        for(int a=0;a<imaginaries.length;a++){
//            newReals[a]=(float)(reals[a]);
//            newImaginaries[a] = (float)(imaginaries[a]);
//        }
//        return new Buffer1DPolar(newReals, newImaginaries);
//    }
//    
//    public Buffer1DPolar reduceToHalfPrecision(){
//        double[] newImaginaries = new double[imaginaries.length];
//        double[] newReals = new double[reals.length];
//        for(int a=0;a<imaginaries.length;a++){
//            int real = util.HalfPrecision.fromFloat((float)reals[a]);
//            int imaginary = util.HalfPrecision.fromFloat((float)imaginaries[a]);
//            newReals[a]=util.HalfPrecision.toFloat(real);
//            newImaginaries[a] = util.HalfPrecision.toFloat(imaginary);
//        }
//        return new Buffer1DPolar(newReals, newImaginaries);
//    }
    
//    public Buffer1DPolar scale(double scalar){
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        for(int a=0;a<reals.length;a++){
//            newReals[a]=reals[a]*scalar;
//            newImaginaries[a]=imaginaries[a]*scalar;
//        }
//        return new Buffer1DPolar(newReals, newImaginaries);
//    }
//    
    
    public Buffer1DPolar scale(double scalar){
        ComplexPolar[] polars = new ComplexPolar[this.nodes.length];
        for(int a=0;a<nodes.length;a++){
            polars[a]=nodes[a].multiply(scalar);
        }
        return new Buffer1DPolar(polars);
    }
    
    private double clamp(double n, double low, double high){
        if (n<low){
            return low;
        }
        if (n>high){
            return high;
        }
        return n;
    }
//   
//    public Buffer1DPolar merge(Buffer1DPolar other, int cutoff){
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            if (a<=cutoff||a>=(reals.length-(cutoff+1))){
//                newReals[a]=reals[a];
//                newImaginaries[a]=imaginaries[a];
//            }
//            else{
//                newReals[a]=other.reals[a];
//                newImaginaries[a]=other.imaginaries[a];
//            }
//        }
//        return new Buffer1DPolar(newReals,newImaginaries);
//    }
//    
//    public Buffer1DPolar blend(Buffer1DPolar other, double amount){
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            newReals[a]=reals[a]*(1-amount)+other.reals[a]*amount;
//            newImaginaries[a]=imaginaries[a]*(1-amount)+other.imaginaries[a]*amount;
//        }
//        return new Buffer1DPolar(newReals,newImaginaries);
//    }
//    
//    public Buffer1DPolar threshold(double amt){
//        double amts = amt*amt;
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            if (Math.abs(reals[a]*reals[a]+imaginaries[a]*imaginaries[a])>=amts){
//                newReals[a]=reals[a];
//                newImaginaries[a]=imaginaries[a];
//            }
//            else{
//                newReals[a]=0;
//                newImaginaries[a]=0;
//            }
//            
//        }
//        return new Buffer1DPolar(newReals,newImaginaries);
//    }
    
//    public int countNonZeroComponents(){
//        int count = 0;
//        for (int a=0;a<reals.length;a++){
//            if (reals[a]!=0||imaginaries[a]!=0){
//                count++;
//            }
//        }
//        return count;
//        
//    }
//    public int countZeroComponents(){
//        int count = 0;
//        for (int a=0;a<reals.length;a++){
//            if (reals[a]==0&&imaginaries[a]==0){
//                count++;
//            }
//        }
//        return count;
//    }
    
//    public Buffer1D zeroOutNegativeFrequencies(){
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            if (a<=reals.length/2){
//                newReals[a]=reals[a];
//                newImaginaries[a]=imaginaries[a];
//            }
//            else{
//                //leave then as zero.
//            }
//        }
//        return new Buffer1D(newReals, newImaginaries);
//    }
//    public Buffer1D populateNegativeFromPositiveFrequencies(){
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        int length = reals.length;
//        for (int a=0;a<reals.length;a++){
//            if (a<=reals.length/2){
//                newReals[a]=reals[a];
//                newImaginaries[a]=imaginaries[a];
//            }
//            else{
//                newReals[a]=reals[length-a];
//                newImaginaries[a]=-imaginaries[length-a];
//            }
//        }
//        return new Buffer1D(newReals, newImaginaries);
//    }
//    
//    public Buffer1DPolar topNMagnitudes(int count){
//        //zeros out all other waves beyond the top n (count) magnitudes.
//        //used for possible compression.
//        
//        if (count>=this.reals.length){
//            return this;
//        }
//        int[] savedIndexes = new int[count];
//        double[] savedMagnitudesSquared = new double[count];
//        
//        //assume the first n items have the greatest magnitude to start...
//        for (int a=0;a<count;a++){
//            savedIndexes[a]=a;
//            savedMagnitudesSquared[a]=reals[a]*reals[a]+imaginaries[a]*imaginaries[a];
//        }
//        
//        //now do the rest...
//        for (int a=count;a<reals.length;a++){
//            double ms = reals[a]*reals[a]+imaginaries[a]*imaginaries[a];
//            int currentIndex=a;
//            for (int b=0;b<savedIndexes.length;b++){
//                if (ms>savedMagnitudesSquared[b]){
//                    double temp = savedMagnitudesSquared[b];
//                    savedMagnitudesSquared[b]=ms;
//                    ms=temp;//move this one along...
//                    
//                    int tempi = savedIndexes[b];
//                    savedIndexes[b]=currentIndex;
//                    currentIndex=tempi;//move this one along...
//                }
//            }
//        }
//        
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        
//        for (int a=0;a<newReals.length;a++){
//            for (int b=0;b<savedIndexes.length;b++){
//                if (savedIndexes[b]==a){
//                    newReals[a]=reals[a];
//                    newImaginaries[a]=imaginaries[a];
//                    break;
//                }
//            }
//        }
//        return new Buffer1DPolar(newReals,newImaginaries);
//        
//    }
//   
//    //why does the following produce better yet dimmer results?
//    public Buffer1DPolar topNExperimental2(int count){
//        //zeros out all other waves beyond the top n (count) magnitudes.
//        //used for possible compression.
//        
//        if (count>=this.reals.length){
//            return this;
//        }
//        int[] savedIndexes = new int[count];
//        double[] savedMagnitudesSquared = new double[count];
//        
//        //assume the first n items have the greatest magnitude to start...
//        for (int a=0;a<count;a++){
//            savedIndexes[a]=a;
//            savedMagnitudesSquared[a]=Math.abs(reals[a]*imaginaries[a]);
//        }
//        
//        //now do the rest...
//        for (int a=count;a<reals.length;a++){
//            double ms = Math.abs(reals[a]*imaginaries[a]);
//            int currentIndex=a;
//            for (int b=0;b<savedIndexes.length;b++){
//                if (ms>savedMagnitudesSquared[b]){
//                    double temp = savedMagnitudesSquared[b];
//                    savedMagnitudesSquared[b]=ms;
//                    ms=temp;//move this one along...
//                    
//                    int tempi = savedIndexes[b];
//                    savedIndexes[b]=currentIndex;
//                    currentIndex=tempi;//move this one along...
//                }
//            }
//        }
//        
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        
//        for (int a=0;a<newReals.length;a++){
//            for (int b=0;b<savedIndexes.length;b++){
//                if (savedIndexes[b]==a){
//                    newReals[a]=reals[a];
//                    newImaginaries[a]=imaginaries[a];
//                    break;
//                }
//            }
//        }
//        return new Buffer1DPolar(newReals,newImaginaries);
//        
//    }
//    
//    //why does the following produce better yet dimmer results?
//    public Buffer1DPolar topNExperimental(int count){
//        //zeros out all other waves beyond the top n (count) magnitudes.
//        //used for possible compression.
//        
//        if (count>=this.reals.length){
//            return this;
//        }
//        int[] savedIndexes = new int[count];
//        double[] savedMagnitudesSquared = new double[count];
//        
//        //assume the first n items have the greatest magnitude to start...
//        for (int a=0;a<count;a++){
//            savedIndexes[a]=a;
//            savedMagnitudesSquared[a]=reals[a]*imaginaries[a];
//        }
//        
//        //now do the rest...
//        for (int a=count;a<reals.length;a++){
//            double ms = reals[a]*imaginaries[a];
//            int currentIndex=a;
//            for (int b=1;b<savedIndexes.length/2;b++){
//                if (ms>savedMagnitudesSquared[b]){
//                    double temp = savedMagnitudesSquared[b];
//                    savedMagnitudesSquared[b]=ms;
//                    ms=temp;//move this one along...
//                    
//                    int tempi = savedIndexes[b];
//                    savedIndexes[b]=currentIndex;
//                    currentIndex=tempi;//move this one along...
//                }
//            }
//        }
//        
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        
//        for (int a=0;a<newReals.length;a++){
//            for (int b=0;b<savedIndexes.length;b++){
//                if (savedIndexes[b]==a){
//                    newReals[a]=reals[a];
//                    newImaginaries[a]=imaginaries[a];
//                    break;
//                }
//            }
//        }
//        return new Buffer1DPolar(newReals,newImaginaries);
//        
//    }
//    
//    //why does the following produce better yet dimmer results?
//    public Buffer1DPolar topNExperimentalNegative(int count){
//        //zeros out all other waves beyond the top n (count) magnitudes.
//        //used for possible compression.
//        
//        if (count>=this.reals.length){
//            return this;
//        }
//        int[] savedIndexes = new int[count];
//        double[] savedMagnitudesSquared = new double[count];
//        
//        //assume the first n items have the greatest magnitude to start...
//        for (int a=0;a<count;a++){
//            savedIndexes[a]=a;
//            savedMagnitudesSquared[a]=reals[a]*imaginaries[a];
//        }
//        
//        //now do the rest...
//        for (int a=count;a<reals.length;a++){
//            double ms = reals[a]*imaginaries[a];
//            int currentIndex=a;
//            for (int b=0;b<savedIndexes.length;b++){
//                if (ms<savedMagnitudesSquared[b]){
//                    double temp = savedMagnitudesSquared[b];
//                    savedMagnitudesSquared[b]=ms;
//                    ms=temp;//move this one along...
//                    
//                    int tempi = savedIndexes[b];
//                    savedIndexes[b]=currentIndex;
//                    currentIndex=tempi;//move this one along...
//                }
//            }
//        }
//        
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        
//        for (int a=0;a<newReals.length;a++){
//            for (int b=0;b<savedIndexes.length;b++){
//                if (savedIndexes[b]==a){
//                    newReals[a]=reals[a];
//                    newImaginaries[a]=imaginaries[a];
//                    break;
//                }
//            }
//        }
//        return new Buffer1DPolar(newReals,newImaginaries);
//        
//    }
//    
    
//    
//    public Buffer1DPolar topNReals(int count){
//        //zeros out all other waves beyond the top n (count) magnitudes.
//        //used for possible compression.
//        
//        if (count>=this.reals.length){
//            return this;
//        }
//        int[] savedIndexes = new int[count];
//        double[] savedReals = new double[count];
//        
//        //assume the first n items have the greatest magnitude to start...
//        for (int a=0;a<count;a++){
//            savedIndexes[a]=a;
//            savedReals[a]=reals[a];
//        }
//        
//        //now do the rest...
//        for (int a=count;a<reals.length;a++){
//            double ms = reals[a];
//            int currentIndex=a;
//            for (int b=0;b<savedIndexes.length;b++){
//                if (ms>savedReals[b]){
//                    double temp = savedReals[b];
//                    savedReals[b]=ms;
//                    ms=temp;//move this one along...
//                    
//                    int tempi = savedIndexes[b];
//                    savedIndexes[b]=currentIndex;
//                    currentIndex=tempi;//move this one along...
//                }
//            }
//        }
//        
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        
//        for (int a=0;a<newReals.length;a++){
//            for (int b=0;b<savedIndexes.length;b++){
//                if (savedIndexes[b]==a){
//                    newReals[a]=reals[a];
//                    newImaginaries[a]=imaginaries[a];
//                    break;
//                }
//            }
//        }
//        return new Buffer1DPolar(newReals,newImaginaries);
//        
//    }
//    
//    
//    public Buffer1DPolar topNImaginaries(int count){
//        //zeros out all other waves beyond the top n (count) magnitudes.
//        //used for possible compression.
//        
//        if (count>=this.reals.length){
//            return this;
//        }
//        int[] savedIndexes = new int[count];
//        double[] savedImaginaries = new double[count];
//        
//        //assume the first n items have the greatest magnitude to start...
//        for (int a=0;a<count;a++){
//            savedIndexes[a]=a;
//            savedImaginaries[a]=imaginaries[a];
//        }
//        
//        //now do the rest...
//        for (int a=count;a<imaginaries.length;a++){
//            double ms = imaginaries[a];
//            int currentIndex=a;
//            for (int b=0;b<savedIndexes.length;b++){
//                if (ms>savedImaginaries[b]){
//                    double temp = savedImaginaries[b];
//                    savedImaginaries[b]=ms;
//                    ms=temp;//move this one along...
//                    
//                    int tempi = savedIndexes[b];
//                    savedIndexes[b]=currentIndex;
//                    currentIndex=tempi;//move this one along...
//                }
//            }
//        }
//        
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        
//        for (int a=0;a<newReals.length;a++){
//            for (int b=0;b<savedIndexes.length;b++){
//                if (savedIndexes[b]==a){
//                    newReals[a]=reals[a];
//                    newImaginaries[a]=imaginaries[a];
//                    break;
//                }
//            }
//        }
//        return new Buffer1DPolar(newReals,newImaginaries);
//        
//    }
    
    
//    Buffer1DPolar inversethreshold(double amt){
//        double amts = amt*amt;
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            if (Math.abs(reals[a]*reals[a]+imaginaries[a]*imaginaries[a])<=amts){
//                newReals[a]=reals[a];
//                newImaginaries[a]=imaginaries[a];
//            }
//            else{
//                newReals[a]=0;
//                newImaginaries[a]=0;
//            }
//            
//        }
//        return new Buffer1DPolar(newReals,newImaginaries);
//    }
    
    
    
//    Buffer1DPolar lowPass(double amt){
//        amt = clamp(amt,0,1);
//        int cutoff = (int)(((double)reals.length)*amt)/2;
//        return lowPass(cutoff);
//    }
    
//    Buffer1DPolar lowPass(int cutoff){
//       
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            if (a<=cutoff||a>=(reals.length-(cutoff+1))){
//                newReals[a]=reals[a];
//                newImaginaries[a]=imaginaries[a];
//            }
//            else{
//                newReals[a]=0;
//                newImaginaries[a]=0;
//            }
//        }
//        return new Buffer1DPolar(newReals,newImaginaries);
//    }
    
//    Buffer1DPolar highPass(double amt){
//        amt = clamp(amt,0,1);
//        int cutoff = (int)(((double)reals.length)*amt)/2;
//        return highPass(cutoff);
//    }
    
//    Buffer1DPolar resize(int newSize){
//        double[] newReals = new double[newSize];
//        double[] newImaginaries = new double[newSize];
//        for (int a=0;a<newReals.length;a++){
//            if (a<reals.length){
//                newReals[a]=reals[a];
//                newImaginaries[a]=imaginaries[a];
//            }
//            else{
//                newReals[a]=0;
//                newImaginaries[a]=0;
//            }
//        }
//        return new Buffer1DPolar(newReals,newImaginaries);
//    }
//    
    
//    Buffer1DPolar highPass(int cutoff){
//        
//        double[] newReals = new double[reals.length];
//        double[] newImaginaries = new double[imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            if (a>=cutoff&&a<=(reals.length-(cutoff+1))){
//                newReals[a]=reals[a];
//                newImaginaries[a]=imaginaries[a];
//            }
//            else{
//                newReals[a]=0;
//                newImaginaries[a]=0;
//            }
//        }
//        return new Buffer1DPolar(newReals,newImaginaries);
//    }
    
  
//    Buffer1D growBuffer(){
//        double[] newReals = new double[reals.length*2];
//        double[] newImaginaries = new double[imaginaries.length*2];
//        for (int a=0;a<reals.length;a++){
//            
//        }
//    }
    Buffer1DPolar multiply(Buffer1DPolar other){
        if (other.nodes.length>this.nodes.length){
            throw new java.lang.RuntimeException("Other buffer must currently be same size or smaller.");
        }
        ComplexPolar[] polars = new ComplexPolar[this.nodes.length];
     
        for (int a=0;a<nodes.length;a++){
            //complexMultiplication...
            if (a<other.nodes.length){
                polars[a]=this.nodes[a].multiply(other.nodes[a]);
            }
            else{
                polars[a]=new ComplexPolar(0,0);///this might cause a problem...
            }
        }
        return new Buffer1DPolar(polars);
    }
    
    Buffer1DPolar divide(Buffer1DPolar other){
        if (other.nodes.length>this.nodes.length){
            throw new java.lang.RuntimeException("Other buffer must currently be same size or smaller.");
        }
        ComplexPolar[] polars = new ComplexPolar[this.nodes.length];
     
        for (int a=0;a<nodes.length;a++){
            //complexMultiplication...
            if (a<other.nodes.length){
                polars[a]=this.nodes[a].divide(other.nodes[a]);
            }
            else{
                polars[a]=new ComplexPolar(0,0);///this might cause a problem...
            }
        }
        return new Buffer1DPolar(polars);
    }
    
    
//    public Buffer1DPolar(double... reals){
//        this.reals=reals;
//        imaginaries = new double[reals.length];
//    }
    
    public Buffer1DPolar GetEvens(){
        int lengthD2 = this.nodes.length/2;
        ComplexPolar[] evens = new ComplexPolar[lengthD2];
        
        for (int a=0;a<lengthD2;a++){
            evens[a]=nodes[2*a];
            
        }
        return new Buffer1DPolar(evens);
    }
//    public Complex[] toComplexes(){
//        Complex[] returnComplexes = new Complex[this.reals.length];
//        for (int a=0;a<reals.length;a++){
//            returnComplexes[a]=new Complex(reals[a],imaginaries[a]);
//        }
//        return returnComplexes;
//    }
    public Buffer1DPolar GetOdds(){
        int lengthD2 = this.nodes.length/2;
        ComplexPolar[] odds = new ComplexPolar[lengthD2];
       
        for (int a=0;a<lengthD2;a++){
            odds[a]=nodes[2*a+1];
            
        }
        return new Buffer1DPolar(odds);
    }
    
    
//    public Buffer1DPolar Scale(double scale){
//        double[] returnReals = new double[this.reals.length];
//        double[] returnImaginaries = new double[this.imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            returnReals[a]=reals[a]*scale;
//            returnImaginaries[a]=imaginaries[a]*scale;
//        }
//        return new Buffer1DPolar(returnReals, returnImaginaries);
//    }

    public Buffer1DPolar(ComplexPolar... complexes){
        this.nodes=complexes;
    }
//    
//    public Buffer1DPolar add(Buffer1DPolar other){
//        double[] returnReals = new double[this.reals.length];
//        double[] returnImaginaries = new double[this.imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            returnReals[a]=reals[a]+other.reals[a];
//            returnImaginaries[a]=imaginaries[a]+other.imaginaries[a];
//        }
//        return new Buffer1DPolar(returnReals, returnImaginaries);
//    }
//    
//    public Buffer1DPolar subtract(Buffer1DPolar other){
//        double[] returnReals = new double[this.reals.length];
//        double[] returnImaginaries = new double[this.imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            returnReals[a]=reals[a]-other.reals[a];
//            returnImaginaries[a]=imaginaries[a]-other.imaginaries[a];
//        }
//        return new Buffer1DPolar(returnReals, returnImaginaries);
//    }
//    
//    public Buffer1DPolar abs(){
//        double[] returnReals = new double[this.reals.length];
//        double[] returnImaginaries = new double[this.imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            returnReals[a]=Math.abs(reals[a]);
//            returnImaginaries[a]=Math.abs(imaginaries[a]);
//        }
//        return new Buffer1DPolar(returnReals, returnImaginaries);
//    }
//    
//    
//    
//    public Buffer1DPolar swapRealsAndImaginaries(){
//        double[] returnReals = new double[this.reals.length];
//        double[] returnImaginaries = new double[this.imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            returnReals[a]=imaginaries[a];
//            returnImaginaries[a]=reals[a];
//        }
//        return new Buffer1DPolar(returnReals, returnImaginaries);
//    }
//    public Buffer1DPolar clearReals(){
//        double[] returnReals = new double[this.reals.length];
//        double[] returnImaginaries = new double[this.imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            returnReals[a]=0;
//            returnImaginaries[a]=imaginaries[a];
//        }
//        return new Buffer1DPolar(returnReals, returnImaginaries);
//    }
//    public Buffer1DPolar clearImaginaries(){
//        double[] returnReals = new double[this.reals.length];
//        double[] returnImaginaries = new double[this.imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            returnReals[a]=reals[a];
//            returnImaginaries[a]=0;
//            
//        }
//        return new Buffer1DPolar(returnReals, returnImaginaries);
//    }
//    
//    public Buffer1DPolar toUnitVectors(){
//        double[] returnReals = new double[this.reals.length];
//        double[] returnImaginaries = new double[this.imaginaries.length];
//        for (int a=0;a<reals.length;a++){
//            double real = reals[a];
//            double imaginary = imaginaries[a];
//            
//            double mag =Math.sqrt(real*real+imaginary+imaginary);
//            if (mag==0){
//                mag=.000000001;//kludge.
//            }
//            real/=mag;
//            imaginary/=mag;
//            
//            returnReals[a]=real;
//            returnImaginaries[a]=imaginary;
//            
//        }
//        return new Buffer1DPolar(returnReals, returnImaginaries);
//    }
//    
//    @Override
//    public String toString(){
//        String returnString = "";
//        for(int a=0;a<reals.length;a++){
//            returnString+=(new Complex(reals[a],imaginaries[a])).toString();
//        }
//        return returnString;
//    }

   
    
    public Buffer1DPolar fft(){
        return Fourier.fft(this);
    }
    public Buffer1DPolar ifft(){
        return Fourier.ifft(this);
    }
} 

