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

