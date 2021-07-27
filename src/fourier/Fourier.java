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
        /*
        
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
        
        ImageThreeChannel i = new ImageThreeChannel("c:\\data\\imageone.png");
        
        double filterScale = 8192;
        
        ImageThreeChannel f = new ImageThreeChannel("c:\\data\\imagetwo.png");
        f=f.scale(1.0/filterScale);
        i=i.fft();
        f=f.fft();
        i=i.multiply(f);
        //i=i.highPass(.995);
        i=i.ifft();
        
       
        i.WriteRealsToImageFile("c:\\data\\convolutionResult.png");
        
        //now attempt deconvolution...
        ImageThreeChannel original = new ImageThreeChannel("c:\\data\\imageone.png");
        
        
        original=original.fft();
        i=i.fft();//convert back to f domain again.
        ImageThreeChannel convolution = i.divide(original);
        convolution=convolution.ifft();
        //multiply so we can see the results in the image...
        convolution=convolution.scale(filterScale);
        convolution.WriteRealsToImageFile("c:\\data\\extractedConvolution.png");
        convolution=convolution.scale(1./filterScale);
        
        convolution = convolution.fft();
        
        i=i.divide(convolution);
        //attempt to restore original image from reconstructed convolution...
        
        
        
        //i=i.divide(f);
        
        i=i.ifft();
        i.WriteRealsToImageFile("c:\\data\\extractedOriginal.png");
        
        /////////////////////////
        
        
        ImageThreeChannel result = new ImageThreeChannel("c:\\data\\result.png");
        ImageThreeChannel testone = new ImageThreeChannel("c:\\data\\test1.png");
        ImageThreeChannel testtwo = new ImageThreeChannel("c:\\data\\test2.png");
        
        result=result.fft();
        testone=testone.fft();
        testtwo=testtwo.fft();
        ImageThreeChannel c = result.divide(testone);
        
        ImageThreeChannel result2 = testtwo.multiply(c);
        result2=result2.ifft();
        result2.WriteRealsToImageFile("c:\\data\\crazyexperiment.png");
        
        
        //ImageThreeChannel gr = new ImageThreeChannel("c:\\data\\gr.jpg");
        
        //ImageThreeChannel grfft = gr.fft();
        
        ImageThreeChannel ac = new ImageThreeChannel("c:\\data\\ac.png");
        
        ImageThreeChannel acfft = ac.fft();
        
        ImageThreeChannel merged = grfft.merge(acfft,8);
        //ImageThreeChannel merged2 = ac.blend(gr,.5);
        
        merged=merged.ifft();
        merged.WriteRealsToImageFile("c:\\data\\merged.jpg");
        //merged2.WriteRealsToImageFile("c:\\data\\merged2.jpg");
        //catfft=catfft.lowPass(64);
        //cat = catfft.ifft();
        
        //cat.WriteRealsToImageFile("c:\\data\\catfilteredlow.png");
        */
        
        /*
        ImageThreeChannel ac = new ImageThreeChannel("c:\\data\\ac512.png");
        ac=ac.swapRealsAndImaginaries();
        ImageThreeChannel gr = new ImageThreeChannel("c:\\data\\gr.jpg");
        ac=ac.add(gr);
        ac = ac.fft();
        //ac = ac.threshold(256000);
        ac = ac.highPass(.1);
        ac=ac.ifft();
        ac=ac.scale(20);
        ac.WriteRealsToImageFile("c:\\data\\grthreshold.png");
        ac=ac.swapRealsAndImaginaries();
        ac.WriteRealsToImageFile("c:\\data\\acthreshold.png");
        
        ac.WriteMagnitudesToImageFile("c:\\data\\acgrmags.png");
        */
        
        
        //ImageThreeChannel filter = ImageThreeChannel.EDGEDETECT9X9.resize(1024).toMonochrome();
        //filter = filter.fft();
        //ImageThreeChannel filter2 = ImageThreeChannel.EDGEDETECT9X9INVERTED.resize(1024).toMonochrome();
        //filter2 = filter2.fft();
        
        //ImageThreeChannel ac = new ImageThreeChannel("d:\\data\\girl.png");//.toMonochrome();
       // ImageThreeChannel gr = new ImageThreeChannel("d:\\data\\translated.png");//.toMonochrome();
        
        //ac=ac.fft();
        //ImageThreeChannel ed1 = ac.multiply(filter);//edge detect one
        //ImageThreeChannel ed2 = ac.multiply(filter2);
    
        //ed1=ed1.ifft();
        //ed2=ed2.ifft();
        
        //ac = ed1.subtract(ed2).abs();
        //ac=ac.fft();
        
        
        //gr=gr.fft();
        //ImageThreeChannel gr1 = gr.multiply(filter);
        //ImageThreeChannel gr2 = gr.multiply(filter2);
        //gr1=gr1.ifft();
        //gr2=gr2.ifft();
       // gr=gr1.subtract(gr2).abs();
       // gr=gr.fft();
        
        //gr=gr.multiply(filter);
        //ImageThreeChannel extractedFilter=ac.divide(gr);
        
        //ImageThreeChannel ac2 = new ImageThreeChannel("d:\\data\\ac.png");
        
       // ac2=ac2.fft();
        //ac2=ac2.multiply(extractedFilter);
                
        
        //ac=gr.multiply(extractedFilter);
        //ac=ac.highPass(.75);
        //ac2=ac2.ifft();
        //ac=ac.showBrightestRealValue();
        //ac=ac.scale(.0008);
       // ac2=ac2.scale(1.);
       // ac2.WriteRealsToImageFile("d:\\data\\translationtest.png");
        //ac.WriteImaginariesToImageFile("c:\\data\\convolved2.png");
        
       // ac.toUnitVectors().scale(128).WriteRealsToImageFile("c:\\data\\unitvectors.png");
       ImageThreeChannel c1 = new ImageThreeChannel("d:\\data\\c3Original.png");
       System.out.println("Image one loaded");
       ImageThreeChannel c2 = new ImageThreeChannel("d:\\data\\c3AfterSystemRotationCorrection.png");
       System.out.println("Image two loaded");
       
       c1=c1.fft();
       System.out.println("Image one fft done");
       
       //ImageThreeChannel c1Cached = c1;
       c2=c2.fft();
       System.out.println("Image two fft done");
       
       ImageThreeChannel c1Cached=c1;//ImageThreeChannel("d:\\data\\c3Original.png");;
       //c1Cached=c1Cached.resize(2048);
       //c1Cached = c1Cached.fft();
       //c1=c1.lowPass(.99);
      // c2=c2.lowPass(.99);
       
       ImageThreeChannel extractedFilter=c2.divide(c1);
       System.out.println("Filter extracted");
       
       c1=c1.divide(extractedFilter);
       System.out.println("multiplying original by filter.");
       
       //so far, c1 has our first attempt at a fix. now bring in what it actually does after another round through the system...
       ImageThreeChannel c3 = new ImageThreeChannel("d:\\data\\afterprint.png");
       c3=c3.fft();
       
       ImageThreeChannel filter2 = c3.divide(c1Cached);
       
       c1=c1.divide(filter2);//reverse this over/undershoot.
       
       
       
       c1=c1.ifft();
       System.out.println("ifft done");
       c1.WriteRealsToImageFile("d:\\data\\claudiaout.png");
       System.out.println("file written");
       
    }
    
    
    


    
    
    
   
    
}
