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
    
    //private static double ksdn[][] = new double[0][0];
    
    
    //private static double[] kthsin = new double[0];
   // private static double[] kthcos = new double[0];
    
//    
//    private static void populateksdn(int n, int size){
//        if (ksdn.length>=n){
//            return;
//        }
//        ksdn=new double[n][size];
//        for (int n)
//    }
    
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
        int n = toTransform.reals.length;
        populateks(n);
        return _fft(toTransform,n);
    }
    
//    public static native double sin(double a);
//    public static native double cos(double a);
    
    private static Buffer1D _fft(Buffer1D toTransform, int n){
        
       
        
        
        //int n = toTransform.reals.length;
        if (n==1){
            //throw new java.lang.RuntimeException("dfsdfsd");
            return toTransform;
        }
        
        //The following works even though it is only
        //an even odd check because this routine
        //is recursive. So eventually if it's not a power
        //of two then it will be odd but not 1...
        if (n%2!=0){
            throw new java.lang.IllegalArgumentException("this fft routine only works on buffer sizes that are a power of 2.");
        }
        int nd2 = n/2;
   
        
        
        double[] evenFFTReals;// = evenFFT.reals;
        double[] oddFFTReals;// = oddFFT.reals;
        double[] evenFFTImaginaries;// = evenFFT.imaginaries;
        double[] oddFFTImaginaries;// = oddFFT.imaginaries;
        
        
        if (nd2==1){
            //Buffer1D evens =
            //evenFFT =  toTransform.GetEvens();
            
            //evenFFT = new Buffer1D(new double[]{toTransform.reals[0]},new double[]{toTransform.imaginaries[0]});
            
            evenFFTReals = new double[]{toTransform.reals[0]};
            oddFFTReals = new double[]{toTransform.reals[1]};
            evenFFTImaginaries = new double[]{toTransform.imaginaries[0]};
            oddFFTImaginaries = new double[]{toTransform.imaginaries[1]};
            
            //Buffer1D odds = 
            //oddFFT = toTransform.GetOdds();
            //oddFFT = new Buffer1D(new double[]{toTransform.reals[1]},new double[]{toTransform.imaginaries[1]});
        }
        else{
            
            
            Buffer1D evens = toTransform.GetEvens();
            Buffer1D evenFFT = _fft(evens,nd2);
            Buffer1D odds = toTransform.GetOdds();
            Buffer1D oddFFT = _fft(odds,nd2);
            
            evenFFTReals = evenFFT.reals;
            oddFFTReals = oddFFT.reals;
            evenFFTImaginaries = evenFFT.imaginaries;
            oddFFTImaginaries = oddFFT.imaginaries;
            
        }


       
        
        double[] finalReals = new double[n];
        double[] finalImaginaries = new double[n];
        
        //double[] evenFFTReals = evenFFT.reals;
        //double[] oddFFTReals = oddFFT.reals;
        //double[] evenFFTImaginaries = evenFFT.imaginaries;
        //double[] oddFFTImaginaries = oddFFT.imaginaries;
        
        
        for (int k=0;k<nd2;k++){
            
            double kth = ks[k]/n;
            double wkReal = StrictMath.cos(kth);
            double wkImaginary = StrictMath.sin(kth);
            
            double oddFFTRealsK = oddFFTReals[k];
            double oddFFTImaginariesK = oddFFTImaginaries[k];
            
            double wkTimesOddReal = wkReal*oddFFTRealsK-wkImaginary*oddFFTImaginariesK;
            double wkTimesOddImaginary = wkReal*oddFFTImaginariesK+wkImaginary*oddFFTRealsK;
            
            double evenFFTRealsK = evenFFTReals[k];
            double evenFFTImaginariesK = evenFFTImaginaries[k];
            
            int kPlusND2 = k+nd2;
            finalReals[k] = evenFFTRealsK+wkTimesOddReal;
            finalImaginaries[k] = evenFFTImaginariesK+wkTimesOddImaginary;
            
            finalReals[kPlusND2] = evenFFTRealsK-wkTimesOddReal;
            finalImaginaries[kPlusND2] = evenFFTImaginariesK-wkTimesOddImaginary;
        }
        return new Buffer1D(finalReals, finalImaginaries);
    }
    
    
    
    


    
    
    
   
    
}
