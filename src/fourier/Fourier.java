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
public class Fourier {
    
    private static double ks[] = new double[0];
    
    private static void populateks(int count){
        if (ks.length>=count){
            return;
        }
        else{
            ks = new double[count];
            for(int a=0;a<count;a++){
                ks[a]=-2*a*Math.PI;
            }
        }
    }
    
    public static Buffer1D ifft(Buffer1D toTransform){
        double n = toTransform.reals.length;
        Buffer1D temp = toTransform.GetConjugates();
        temp = fft(temp);
        temp = temp.GetConjugates();
        temp = temp.Scale(1.0/n);
        return temp;
    }
    
    public static Buffer1D fft(Buffer1D toTransform){
        populateks(toTransform.reals.length);
        return _fft(toTransform);
    }
    
    private static Buffer1D _fft(Buffer1D toTransform){
        
       
        
        
        int n = toTransform.reals.length;
        if (n==1){
            return toTransform;
        }
        
        //The following works even though it is only
        //an even odd check because this routine
        //is recursive. So eventually if it's not a power
        //of two then it will be odd but not 1...
        if (n%2!=0){
            throw new java.lang.IllegalArgumentException("this fft routine only works on buffer sizes that are a power of 2.");
        }
        
        
//        double[] evenReals = new double[n/2];
//        double[] evenImaginaries = new double[n/2];
//        for (int a=0;a<n/2;a++){
//            evenReals[a]=toTransform.reals[2*a];
//            evenImaginaries[a]=toTransform.imaginaries[2*a];
//        }
        Buffer1D evens = toTransform.GetEvens();
        Buffer1D evenFFT = _fft(evens);
        
//        double[] oddReals = evenReals;//don't be too confused. Just reusing the arrays
//        double[] oddImaginaries = evenImaginaries;
//        for (int a=0;a<n/2;a++){
//            oddReals[a]=toTransform.reals[2*a+1];
//            oddImaginaries[a]=toTransform.imaginaries[2*a+1];
//        }

        Buffer1D odds = toTransform.GetOdds();
        Buffer1D oddFFT = _fft(odds);
        
        double[] finalReals = new double[n];
        double[] finalImaginaries = new double[n];
        
        double[] evenFFTReals = evenFFT.reals;
        double[] oddFFTReals = oddFFT.reals;
        double[] evenFFTImaginaries = evenFFT.imaginaries;
        double[] oddFFTImaginaries = oddFFT.imaginaries;
        
        for (int k=0;k<n/2;k++){
            
            double kth = ks[k]/n;
            double wkReal = Math.cos(kth);
            double wkImaginary = Math.sin(kth);
            double wkTimesOddReal = wkReal*oddFFTReals[k]-wkImaginary*oddFFTImaginaries[k];
            double wkTimesOddImaginary = wkReal*oddFFTImaginaries[k]+wkImaginary*oddFFTReals[k];
            double evenFFTRealsK = evenFFTReals[k];
            double evenFFTImaginariesK = evenFFTImaginaries[k];
            int kPlusND2 = k+n/2;
            finalReals[k] = evenFFTRealsK+wkTimesOddReal;
            finalImaginaries[k] = evenFFTImaginariesK+wkTimesOddImaginary;
            
            finalReals[kPlusND2] = evenFFTRealsK-wkTimesOddReal;
            finalImaginaries[kPlusND2] = evenFFTImaginariesK-wkTimesOddImaginary;
        }
        return new Buffer1D(finalReals, finalImaginaries);
    }
    
    
    public static void main(String[] args){
        
        
        Buffer1D one = new Buffer1D(
        new double[]{
            1,2,3
        }
        , new double[]{
            11,22,33
        } 
        
        );
    
        Buffer1D two = new Buffer1D(
        new double[]{
            0,0,0
        }
        , new double[]{
            0,0,0
        } 
        
        );
        
        Buffer1D three = new Buffer1D(
        new double[]{
            0,0,0
        }
        , new double[]{
            0,0,0
        } 
        
        );
    
        
        Buffer1D[] array = new Buffer1D[]{
            one,two,three
        };
        Buffer2D td = new Buffer2D(array);
        System.out.println(td);
        System.out.println(td.SwapAxis());
        System.out.println(td.SwapAxis().SwapAxis());
        System.out.println(td);
        
        
        
        Buffer1D aBuffer = new Buffer1D(
                new Complex(1,1),
                new Complex(2,2),
                new Complex(3,3),
                new Complex(4,4),
                new Complex(5,5),
                new Complex(6,6),
                new Complex(7,7),
                new Complex(8,8)
        );
        
        aBuffer = new Buffer1D(
                new Complex(1,1),
                new Complex(1,1),
                new Complex(1,1),
                new Complex(1,1),
                new Complex(1,1),
                new Complex(1,1),
                new Complex(1,1),
                new Complex(1,1)
        );
       
        System.out.println("***************");
        System.out.println(aBuffer);
        System.out.println("***************");
        aBuffer = Fourier.fft(aBuffer);
        System.out.println("***************");
        System.out.println(aBuffer);
        System.out.println("***************");
        aBuffer = Fourier.fft(aBuffer);
        System.out.println("***************");
        System.out.println(aBuffer);
        System.out.println("***************");
        aBuffer = Fourier.ifft(aBuffer);
        System.out.println("***************");
        System.out.println(aBuffer);
        System.out.println("***************");
        aBuffer = Fourier.ifft(aBuffer);
        System.out.println("***************");
        System.out.println(aBuffer);
        System.out.println("***************");
        
    }
    
    
    


    
    
    
   
    
}
