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

    public Buffer1D(double[] reals, double[] imaginaries){
        this.reals=reals;
        this.imaginaries=imaginaries;
        if (this.reals.length!=this.imaginaries.length){
            throw new java.lang.RuntimeException("Real and imaginary coefficient lengths must match!");
        }
    }
    
    
    
    public Buffer1D GetConjugates(){
        double[] newImaginaries = new double[imaginaries.length];
        for(int a=0;a<imaginaries.length;a++){
            newImaginaries[a] = -imaginaries[a];
        }
        return new Buffer1D(reals, newImaginaries);
    }

    Buffer1D scale(double scalar){
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
   
    Buffer1D merge(Buffer1D other, int cutoff){
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
    
    Buffer1D blend(Buffer1D other, double amount){
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        for (int a=0;a<reals.length;a++){
            newReals[a]=reals[a]*(1-amount)+other.reals[a]*amount;
            newImaginaries[a]=imaginaries[a]*(1-amount)+other.imaginaries[a]*amount;
        }
        return new Buffer1D(newReals,newImaginaries);
    }
    
    Buffer1D threshold(double amt){
        double amts = amt*amt;
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        for (int a=0;a<reals.length;a++){
            if (Math.abs(reals[a]*imaginaries[a])>=amts){
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
    
    Buffer1D inversethreshold(double amt){
        double amts = amt*amt;
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        for (int a=0;a<reals.length;a++){
            if (Math.abs(reals[a]*imaginaries[a])<=amts){
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
    
    Buffer1D divide(Buffer1D other){
        double[] newReals = new double[reals.length];
        double[] newImaginaries = new double[imaginaries.length];
        
        for (int a=0;a<this.reals.length;a++){
            double sharedDivisor = other.reals[a]*other.reals[a]+other.imaginaries[a]*other.imaginaries[a];
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

