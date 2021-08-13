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
public class Buffer1D{
    public double[] reals;
    public double[] imaginaries;

    
    public Buffer1D(int size){
        this.reals=new double[size];
        this.imaginaries = new double[size];
    }
    
    public Buffer1D(double[] reals, double[] imaginaries){
        this.reals=reals;
        this.imaginaries=imaginaries;
        if (this.reals.length!=this.imaginaries.length){
            throw new java.lang.RuntimeException("Real and imaginary coefficient lengths must match!");
        }
    }
    
    public Buffer1D(Buffer1DPolar polarVersion){
        this.reals = new double[polarVersion.nodes.length];
        this.imaginaries = new double[polarVersion.nodes.length];
        for (int a=0;a<reals.length;a++){
            Complex c = new Complex(polarVersion.nodes[a]);
            reals[a]=c.real;
            imaginaries[a]=c.imaginary;
        }
    }
    
    
    public Buffer1D avg(Buffer1D... others){
        Buffer1D[] more = new Buffer1D[others.length+1];
        more[0]=this;
        for (int a=0;a<others.length;a++){
            more[a+1]=others[a];
        }
        return average(more);
    }
    
    public Buffer1D centerShift(){
        int size = this.reals.length;
        int sizeD2 = size/2;
        double[] returnReals = new double[size];
        double[] returnImaginaries = new double[size];
        for (int a=0;a<size;a++){
            int offsetIndex = (a+sizeD2)%size;
            returnReals[a]=reals[offsetIndex];
            returnImaginaries[a]=imaginaries[offsetIndex];
        }
        return new Buffer1D(returnReals, returnImaginaries);
    }
    
    public static Buffer1D average(Buffer1D... buffers){
        double[] returnDoubleReal = new double[buffers[0].reals.length];
        double[] returnDoubleImaginary = new double[buffers[0].imaginaries.length];
        int bufferCount = buffers.length;
        
        if (bufferCount<1){
            throw new java.lang.RuntimeException("No buffers!");
                    
        }
        double bufferCountD = bufferCount;
        int buffSize = buffers[0].reals.length;
        
        for (int a=0;a<buffSize;a++){
            for (int b=0;b<bufferCount;b++){
            
                returnDoubleReal[a]+=buffers[b].reals[a];
                returnDoubleImaginary[a]+=buffers[b].imaginaries[a];
              
            }
            returnDoubleReal[a]/=bufferCountD;
            returnDoubleImaginary[a]/=bufferCountD;
        }
        return new Buffer1D(returnDoubleReal,returnDoubleImaginary);
        
    }
    
    
    public Buffer1D clone(boolean sizeOnly){
        if (sizeOnly){
            return new Buffer1D(new double[reals.length],new double[imaginaries.length]);
        }
        else{
            return clone();
        }
    }
    @Override
    public Buffer1D clone(){
        double[] newReals = new double[this.reals.length];
        double[] newImaginaries = new double[this.imaginaries.length];
        for (int a=0;a>newReals.length;a++){
            newReals[a]=reals[a];
            newImaginaries[a]=imaginaries[a];
        }
        return new Buffer1D(newReals,newImaginaries);
    }
    
    
    
    public Buffer1D GetConjugates(){
        double[] newImaginaries = new double[imaginaries.length];
        double[] newReals = new double[reals.length];
        for(int a=0;a<imaginaries.length;a++){
            newReals[a]=reals[a];
            newImaginaries[a] = -imaginaries[a];
        }
        return new Buffer1D(newReals, newImaginaries);
    }

    public Buffer1D reduceToFloatPrecision(){
        double[] newImaginaries = new double[imaginaries.length];
        double[] newReals = new double[reals.length];
        for(int a=0;a<imaginaries.length;a++){
            newReals[a]=(float)(reals[a]);
            newImaginaries[a] = (float)(imaginaries[a]);
        }
        return new Buffer1D(newReals, newImaginaries);
    }
    
    public Buffer1D reduceToHalfPrecision(){
        double[] newImaginaries = new double[imaginaries.length];
        double[] newReals = new double[reals.length];
        for(int a=0;a<imaginaries.length;a++){
            int real = util.HalfPrecision.fromFloat((float)reals[a]);
            int imaginary = util.HalfPrecision.fromFloat((float)imaginaries[a]);
            newReals[a]=util.HalfPrecision.toFloat(real);
            newImaginaries[a] = util.HalfPrecision.toFloat(imaginary);
        }
        return new Buffer1D(newReals, newImaginaries);
    }
    
    public Buffer1D scale(double scalar){
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        for(int a=0;a<reals.length;a++){
            newReals[a]=reals[a]*scalar;
            newImaginaries[a]=imaginaries[a]*scalar;
        }
        return new Buffer1D(newReals, newImaginaries);
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
   
    public Buffer1D merge(Buffer1D other, int cutoff){
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        for (int a=0;a<reals.length;a++){
            if (a<=cutoff||a>=(reals.length-(cutoff+1))){
                newReals[a]=reals[a];
                newImaginaries[a]=imaginaries[a];
            }
            else{
                newReals[a]=other.reals[a];
                newImaginaries[a]=other.imaginaries[a];
            }
        }
        return new Buffer1D(newReals,newImaginaries);
    }
    
    public Buffer1D blend(Buffer1D other, double amount){
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        for (int a=0;a<reals.length;a++){
            newReals[a]=reals[a]*(1-amount)+other.reals[a]*amount;
            newImaginaries[a]=imaginaries[a]*(1-amount)+other.imaginaries[a]*amount;
        }
        return new Buffer1D(newReals,newImaginaries);
    }
    
    public Buffer1D threshold(double amt){
        double amts = amt*amt;
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        for (int a=0;a<reals.length;a++){
            if (Math.abs(reals[a]*reals[a]+imaginaries[a]*imaginaries[a])>=amts){
                newReals[a]=reals[a];
                newImaginaries[a]=imaginaries[a];
            }
            else{
                newReals[a]=0;
                newImaginaries[a]=0;
            }
            
        }
        return new Buffer1D(newReals,newImaginaries);
    }
    
    public int countNonZeroComponents(){
        int count = 0;
        for (int a=0;a<reals.length;a++){
            if (reals[a]!=0||imaginaries[a]!=0){
                count++;
            }
        }
        return count;
        
    }
    public int countZeroComponents(){
        int count = 0;
        for (int a=0;a<reals.length;a++){
            if (reals[a]==0&&imaginaries[a]==0){
                count++;
            }
        }
        return count;
    }
    
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
    
    public Buffer1D topNMagnitudes(int count){
        //zeros out all other waves beyond the top n (count) magnitudes.
        //used for possible compression.
        
        if (count>=this.reals.length){
            return this;
        }
        int[] savedIndexes = new int[count];
        double[] savedMagnitudesSquared = new double[count];
        
        //assume the first n items have the greatest magnitude to start...
        for (int a=0;a<count;a++){
            savedIndexes[a]=a;
            savedMagnitudesSquared[a]=reals[a]*reals[a]+imaginaries[a]*imaginaries[a];
        }
        
        //now do the rest...
        for (int a=count;a<reals.length;a++){
            double ms = reals[a]*reals[a]+imaginaries[a]*imaginaries[a];
            int currentIndex=a;
            for (int b=0;b<savedIndexes.length;b++){
                if (ms>savedMagnitudesSquared[b]){
                    double temp = savedMagnitudesSquared[b];
                    savedMagnitudesSquared[b]=ms;
                    ms=temp;//move this one along...
                    
                    int tempi = savedIndexes[b];
                    savedIndexes[b]=currentIndex;
                    currentIndex=tempi;//move this one along...
                }
            }
        }
        
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        
        for (int a=0;a<newReals.length;a++){
            for (int b=0;b<savedIndexes.length;b++){
                if (savedIndexes[b]==a){
                    newReals[a]=reals[a];
                    newImaginaries[a]=imaginaries[a];
                    break;
                }
            }
        }
        return new Buffer1D(newReals,newImaginaries);
        
    }
   
    //why does the following produce better yet dimmer results?
    public Buffer1D topNExperimental2(int count){
        //zeros out all other waves beyond the top n (count) magnitudes.
        //used for possible compression.
        
        if (count>=this.reals.length){
            return this;
        }
        int[] savedIndexes = new int[count];
        double[] savedMagnitudesSquared = new double[count];
        
        //assume the first n items have the greatest magnitude to start...
        for (int a=0;a<count;a++){
            savedIndexes[a]=a;
            savedMagnitudesSquared[a]=Math.abs(reals[a]*imaginaries[a]);
        }
        
        //now do the rest...
        for (int a=count;a<reals.length;a++){
            double ms = Math.abs(reals[a]*imaginaries[a]);
            int currentIndex=a;
            for (int b=0;b<savedIndexes.length;b++){
                if (ms>savedMagnitudesSquared[b]){
                    double temp = savedMagnitudesSquared[b];
                    savedMagnitudesSquared[b]=ms;
                    ms=temp;//move this one along...
                    
                    int tempi = savedIndexes[b];
                    savedIndexes[b]=currentIndex;
                    currentIndex=tempi;//move this one along...
                }
            }
        }
        
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        
        for (int a=0;a<newReals.length;a++){
            for (int b=0;b<savedIndexes.length;b++){
                if (savedIndexes[b]==a){
                    newReals[a]=reals[a];
                    newImaginaries[a]=imaginaries[a];
                    break;
                }
            }
        }
        return new Buffer1D(newReals,newImaginaries);
        
    }
    
    //why does the following produce better yet dimmer results?
    public Buffer1D topNExperimental(int count){
        //zeros out all other waves beyond the top n (count) magnitudes.
        //used for possible compression.
        
        if (count>=this.reals.length){
            return this;
        }
        int[] savedIndexes = new int[count];
        double[] savedMagnitudesSquared = new double[count];
        
        //assume the first n items have the greatest magnitude to start...
        for (int a=0;a<count;a++){
            savedIndexes[a]=a;
            savedMagnitudesSquared[a]=reals[a]*imaginaries[a];
        }
        
        //now do the rest...
        for (int a=count;a<reals.length;a++){
            double ms = reals[a]*imaginaries[a];
            int currentIndex=a;
            for (int b=1;b<savedIndexes.length/2;b++){
                if (ms>savedMagnitudesSquared[b]){
                    double temp = savedMagnitudesSquared[b];
                    savedMagnitudesSquared[b]=ms;
                    ms=temp;//move this one along...
                    
                    int tempi = savedIndexes[b];
                    savedIndexes[b]=currentIndex;
                    currentIndex=tempi;//move this one along...
                }
            }
        }
        
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        
        for (int a=0;a<newReals.length;a++){
            for (int b=0;b<savedIndexes.length;b++){
                if (savedIndexes[b]==a){
                    newReals[a]=reals[a];
                    newImaginaries[a]=imaginaries[a];
                    break;
                }
            }
        }
        return new Buffer1D(newReals,newImaginaries);
        
    }
    
    //why does the following produce better yet dimmer results?
    public Buffer1D topNExperimentalNegative(int count){
        //zeros out all other waves beyond the top n (count) magnitudes.
        //used for possible compression.
        
        if (count>=this.reals.length){
            return this;
        }
        int[] savedIndexes = new int[count];
        double[] savedMagnitudesSquared = new double[count];
        
        //assume the first n items have the greatest magnitude to start...
        for (int a=0;a<count;a++){
            savedIndexes[a]=a;
            savedMagnitudesSquared[a]=reals[a]*imaginaries[a];
        }
        
        //now do the rest...
        for (int a=count;a<reals.length;a++){
            double ms = reals[a]*imaginaries[a];
            int currentIndex=a;
            for (int b=0;b<savedIndexes.length;b++){
                if (ms<savedMagnitudesSquared[b]){
                    double temp = savedMagnitudesSquared[b];
                    savedMagnitudesSquared[b]=ms;
                    ms=temp;//move this one along...
                    
                    int tempi = savedIndexes[b];
                    savedIndexes[b]=currentIndex;
                    currentIndex=tempi;//move this one along...
                }
            }
        }
        
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        
        for (int a=0;a<newReals.length;a++){
            for (int b=0;b<savedIndexes.length;b++){
                if (savedIndexes[b]==a){
                    newReals[a]=reals[a];
                    newImaginaries[a]=imaginaries[a];
                    break;
                }
            }
        }
        return new Buffer1D(newReals,newImaginaries);
        
    }
    
    
    
    public Buffer1D topNReals(int count){
        //zeros out all other waves beyond the top n (count) magnitudes.
        //used for possible compression.
        
        if (count>=this.reals.length){
            return this;
        }
        int[] savedIndexes = new int[count];
        double[] savedReals = new double[count];
        
        //assume the first n items have the greatest magnitude to start...
        for (int a=0;a<count;a++){
            savedIndexes[a]=a;
            savedReals[a]=reals[a];
        }
        
        //now do the rest...
        for (int a=count;a<reals.length;a++){
            double ms = reals[a];
            int currentIndex=a;
            for (int b=0;b<savedIndexes.length;b++){
                if (ms>savedReals[b]){
                    double temp = savedReals[b];
                    savedReals[b]=ms;
                    ms=temp;//move this one along...
                    
                    int tempi = savedIndexes[b];
                    savedIndexes[b]=currentIndex;
                    currentIndex=tempi;//move this one along...
                }
            }
        }
        
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        
        for (int a=0;a<newReals.length;a++){
            for (int b=0;b<savedIndexes.length;b++){
                if (savedIndexes[b]==a){
                    newReals[a]=reals[a];
                    newImaginaries[a]=imaginaries[a];
                    break;
                }
            }
        }
        return new Buffer1D(newReals,newImaginaries);
        
    }
    
    
    public Buffer1D topNImaginaries(int count){
        //zeros out all other waves beyond the top n (count) magnitudes.
        //used for possible compression.
        
        if (count>=this.reals.length){
            return this;
        }
        int[] savedIndexes = new int[count];
        double[] savedImaginaries = new double[count];
        
        //assume the first n items have the greatest magnitude to start...
        for (int a=0;a<count;a++){
            savedIndexes[a]=a;
            savedImaginaries[a]=imaginaries[a];
        }
        
        //now do the rest...
        for (int a=count;a<imaginaries.length;a++){
            double ms = imaginaries[a];
            int currentIndex=a;
            for (int b=0;b<savedIndexes.length;b++){
                if (ms>savedImaginaries[b]){
                    double temp = savedImaginaries[b];
                    savedImaginaries[b]=ms;
                    ms=temp;//move this one along...
                    
                    int tempi = savedIndexes[b];
                    savedIndexes[b]=currentIndex;
                    currentIndex=tempi;//move this one along...
                }
            }
        }
        
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        
        for (int a=0;a<newReals.length;a++){
            for (int b=0;b<savedIndexes.length;b++){
                if (savedIndexes[b]==a){
                    newReals[a]=reals[a];
                    newImaginaries[a]=imaginaries[a];
                    break;
                }
            }
        }
        return new Buffer1D(newReals,newImaginaries);
        
    }
    
    
    Buffer1D inversethreshold(double amt){
        double amts = amt*amt;
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        for (int a=0;a<reals.length;a++){
            if (Math.abs(reals[a]*reals[a]+imaginaries[a]*imaginaries[a])<=amts){
                newReals[a]=reals[a];
                newImaginaries[a]=imaginaries[a];
            }
            else{
                newReals[a]=0;
                newImaginaries[a]=0;
            }
            
        }
        return new Buffer1D(newReals,newImaginaries);
    }
    
    
    
    Buffer1D lowPass(double amt){
        amt = clamp(amt,0,1);
        int cutoff = (int)(((double)reals.length)*amt)/2;
        return lowPass(cutoff);
    }
    
    Buffer1D lowPass(int cutoff){
       
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        for (int a=0;a<reals.length;a++){
            if (a<=cutoff||a>=(reals.length-(cutoff+1))){
                newReals[a]=reals[a];
                newImaginaries[a]=imaginaries[a];
            }
            else{
                newReals[a]=0;
                newImaginaries[a]=0;
            }
        }
        return new Buffer1D(newReals,newImaginaries);
    }
    
    Buffer1D highPass(double amt){
        amt = clamp(amt,0,1);
        int cutoff = (int)(((double)reals.length)*amt)/2;
        return highPass(cutoff);
    }
    
    Buffer1D resize(int newSize){
        double[] newReals = new double[newSize];
        double[] newImaginaries = new double[newSize];
        for (int a=0;a<newReals.length;a++){
            if (a<reals.length){
                newReals[a]=reals[a];
                newImaginaries[a]=imaginaries[a];
            }
            else{
                newReals[a]=0;
                newImaginaries[a]=0;
            }
        }
        return new Buffer1D(newReals,newImaginaries);
    }
    
    
    Buffer1D highPass(int cutoff){
        
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        for (int a=0;a<reals.length;a++){
            if (a>=cutoff&&a<=(reals.length-(cutoff+1))){
                newReals[a]=reals[a];
                newImaginaries[a]=imaginaries[a];
            }
            else{
                newReals[a]=0;
                newImaginaries[a]=0;
            }
        }
        return new Buffer1D(newReals,newImaginaries);
    }
    
    public Buffer1D divideViaPolar(Buffer1D other){
        Buffer1DPolar p = new Buffer1DPolar(this);
        Buffer1DPolar otherp = new Buffer1DPolar(other);
        Buffer1DPolar result = p.divide(otherp);
        return new Buffer1D(result);
       
    }
    
    
    Buffer1D divide(Buffer1D other){
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        
        for (int a=0;a<this.reals.length;a++){
            double sharedDivisor = other.reals[a]*other.reals[a]+other.imaginaries[a]*other.imaginaries[a];
            if (sharedDivisor ==0.){
                sharedDivisor = .0000000000000000000000001;
                //System.out.println("poit");
            }
            double newReal = (this.reals[a]*other.reals[a]+this.imaginaries[a]*other.imaginaries[a])/sharedDivisor;
            double newImaginary = (this.imaginaries[a]*other.reals[a]-this.reals[a]*other.imaginaries[a])/sharedDivisor;
            newReals[a]=newReal;
            newImaginaries[a]=newImaginary;
        }
       
        return new Buffer1D(newReals,newImaginaries);
    }
//    Buffer1D growBuffer(){
//        double[] newReals = new double[reals.length*2];
//        double[] newImaginaries = new double[imaginaries.length*2];
//        for (int a=0;a<reals.length;a++){
//            
//        }
//    }
    Buffer1D multiply(Buffer1D other){
        if (other.reals.length>this.reals.length){
            throw new java.lang.RuntimeException("Other buffer must currently be same size or smaller.");
        }
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        for (int a=0;a<reals.length;a++){
            //complexMultiplication...
            if (a<other.reals.length){
                double nr = reals[a]*other.reals[a]-imaginaries[a]*other.imaginaries[a];
                double ni = reals[a]*other.imaginaries[a]+imaginaries[a]*other.reals[a];
                newReals[a]=nr;
                newImaginaries[a]=ni;
            }
            else{
                newReals[a]=0;
                newImaginaries[a]=0;
            }
        }
        return new Buffer1D(newReals, newImaginaries);
    }
    
    
    public Buffer1D(double... reals){
        this.reals=reals;
        imaginaries = new double[reals.length];
    }
    
    public Buffer1D GetEvens(){
        int lengthD2 = this.reals.length/2;
        double[] evenReals = new double[lengthD2];
        double[] evenImaginaries = new double[lengthD2];
        for (int a=0;a<lengthD2;a++){
            evenReals[a]=reals[2*a];
            evenImaginaries[a]=imaginaries[2*a];
        }
        return new Buffer1D(evenReals, evenImaginaries);
    }
    public Complex[] toComplexes(){
        Complex[] returnComplexes = new Complex[this.reals.length];
        for (int a=0;a<reals.length;a++){
            returnComplexes[a]=new Complex(reals[a],imaginaries[a]);
        }
        return returnComplexes;
    }
    public Buffer1D GetOdds(){
        int lengthD2 = this.reals.length/2;
        double[] oddReals = new double[lengthD2];
        double[] oddImaginaries = new double[lengthD2];
        for (int a=0;a<lengthD2;a++){
            oddReals[a]=reals[2*a+1];
            oddImaginaries[a]=imaginaries[2*a+1];
        }
        return new Buffer1D(oddReals, oddImaginaries);
    }
    
    
    public Buffer1D Scale(double scale){
        double[] returnReals = new double[this.reals.length];
        double[] returnImaginaries = new double[this.imaginaries.length];
        for (int a=0;a<reals.length;a++){
            returnReals[a]=reals[a]*scale;
            returnImaginaries[a]=imaginaries[a]*scale;
        }
        return new Buffer1D(returnReals, returnImaginaries);
    }

    public Buffer1D(Complex... complexes){
        int size = complexes.length;
        reals = new double[size];
        imaginaries = new double[size];
        for(int a=0;a<size;a++){
            reals[a]=complexes[a].real;
            imaginaries[a]=complexes[a].imaginary;
        }
    }
    
    public Buffer1D add(Buffer1D other){
        double[] returnReals = new double[this.reals.length];
        double[] returnImaginaries = new double[this.imaginaries.length];
        for (int a=0;a<reals.length;a++){
            returnReals[a]=reals[a]+other.reals[a];
            returnImaginaries[a]=imaginaries[a]+other.imaginaries[a];
        }
        return new Buffer1D(returnReals, returnImaginaries);
    }
    
    public Buffer1D add(Complex constant){
        double[] returnReals = new double[this.reals.length];
        double[] returnImaginaries = new double[this.imaginaries.length];
        for (int a=0;a<reals.length;a++){
            returnReals[a]=reals[a]+constant.real;
            returnImaginaries[a]=imaginaries[a]+constant.imaginary;
        }
        return new Buffer1D(returnReals, returnImaginaries);
    }
    public Buffer1D add(double constant){
        double[] returnReals = new double[this.reals.length];
        double[] returnImaginaries = new double[this.imaginaries.length];
        for (int a=0;a<reals.length;a++){
            returnReals[a]=reals[a]+constant;
            returnImaginaries[a]=imaginaries[a]+0;
        }
        return new Buffer1D(returnReals, returnImaginaries);
    }
    
    public Buffer1D subtract(Buffer1D other){
        double[] returnReals = new double[this.reals.length];
        double[] returnImaginaries = new double[this.imaginaries.length];
        for (int a=0;a<reals.length;a++){
            returnReals[a]=reals[a]-other.reals[a];
            returnImaginaries[a]=imaginaries[a]-other.imaginaries[a];
        }
        return new Buffer1D(returnReals, returnImaginaries);
    }
    
    public Buffer1D abs(){
        double[] returnReals = new double[this.reals.length];
        double[] returnImaginaries = new double[this.imaginaries.length];
        for (int a=0;a<reals.length;a++){
            returnReals[a]=Math.abs(reals[a]);
            returnImaginaries[a]=Math.abs(imaginaries[a]);
        }
        return new Buffer1D(returnReals, returnImaginaries);
    }
    
    
    
    public Buffer1D swapRealsAndImaginaries(){
        double[] returnReals = new double[this.reals.length];
        double[] returnImaginaries = new double[this.imaginaries.length];
        for (int a=0;a<reals.length;a++){
            returnReals[a]=imaginaries[a];
            returnImaginaries[a]=reals[a];
        }
        return new Buffer1D(returnReals, returnImaginaries);
    }
    public Buffer1D clearReals(){
        double[] returnReals = new double[this.reals.length];
        double[] returnImaginaries = new double[this.imaginaries.length];
        for (int a=0;a<reals.length;a++){
            returnReals[a]=0;
            returnImaginaries[a]=imaginaries[a];
        }
        return new Buffer1D(returnReals, returnImaginaries);
    }
    public Buffer1D clearImaginaries(){
        double[] returnReals = new double[this.reals.length];
        double[] returnImaginaries = new double[this.imaginaries.length];
        for (int a=0;a<reals.length;a++){
            returnReals[a]=reals[a];
            returnImaginaries[a]=0;
            
        }
        return new Buffer1D(returnReals, returnImaginaries);
    }
    
    public Buffer1D toUnitVectors(){
        double[] returnReals = new double[this.reals.length];
        double[] returnImaginaries = new double[this.imaginaries.length];
        for (int a=0;a<reals.length;a++){
            double real = reals[a];
            double imaginary = imaginaries[a];
            
            double mag =Math.sqrt(real*real+imaginary+imaginary);
            if (mag==0){
                mag=.000000001;//kludge.
            }
            real/=mag;
            imaginary/=mag;
            
            returnReals[a]=real;
            returnImaginaries[a]=imaginary;
            
        }
        return new Buffer1D(returnReals, returnImaginaries);
    }
    
    @Override
    public String toString(){
        String returnString = "";
        for(int a=0;a<reals.length;a++){
            returnString+=(new Complex(reals[a],imaginaries[a])).toString();
        }
        return returnString;
    }

   
    
    public Buffer1D fft(){
        return Fourier.fft(this);
    }
    public Buffer1D ifft(){
        return Fourier.ifft(this);
    }
} 

