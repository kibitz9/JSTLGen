/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fourier;

/**
 *
 * @author cmiller
 */
public class Tests {
    
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
       /*
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
      // c1=c1.divide(extractedFilter);
      // System.out.println("multiplying original by filter again.");
       */
       /*
       //so far, c1 has our first attempt at a fix. now bring in what it actually does after another round through the system...
       ImageThreeChannel c3 = new ImageThreeChannel("d:\\data\\afterprint.png");
       c3=c3.fft();
       
       ImageThreeChannel filter2 = c3.divide(c1Cached);
       
       c1=c1.divide(filter2);//reverse this over/undershoot.
       */
       /*
       int cutoff = 15;
       ImageThreeChannel c4=c1.lowPass(cutoff);
       c1=c4.merge(c1Cached,cutoff);
       
       c1=c1.ifft();
       System.out.println("ifft done");
       c1.WriteRealsToImageFile("d:\\data\\claudiaout.png");
       System.out.println("file written");

*/
       
       
       long startTime = java.lang.System.currentTimeMillis();
       
       //ImageThreeChannel c4= new ImageThreeChannel("c:\\data\\cat.png");
       
       //add a little something to the imaginary channel to avoid certain issues...
      // c4=c4.add(new Complex(.000000001,1));
       // ImageThreeChannel c4Temp = c4;
       //c4=c4.fft();
       
      
       
       //ImageThreeChannel cache = c4;
       //c4=c4.fft();
       //c4=c4.lowPass(64);
       //c4=c4.topNWaves(64);
       //c4=c4.zeroOutNegativeFrequencies();
       //c4=c4.populateNegativeFromPositiveFrequencies();
       
       //ImageThreeChannel gb = ImageThreeChannel.SHARPEN3X3;
       //gb=gb.resize(128);
       
       
       //gb=gb.add(.01);
       //gb=gb.scale(10);
       //gb=gb.add(new Complex(0,1));
       //gb=gb.fft();
       
       
       
       //c4Temp=c4Temp.multiply(gb);
       //c4Temp=c4Temp.divide(c4);
       //c4=c4.multiply(gb);
       //c4=c4.divide(gb);
       //c4=c4.multiply(gb);
       //c4=c4.multiply(gb);
       //c4=c4.multiply(gb);
       //c4=c4.add(.0000001);
//       boolean test = false;
//       int amount = 15;
//       int amount2 = 35;
//       if (test){
//            c4=c4.topNExperimental(amount);
//            c4=c4.swapAxis();
//            c4=c4.topNExperimental(amount);
//            c4=c4.swapAxis();
//       }
//       else{
           
//            c4=c4.highPass(amount);
//            c4=c4.swapAxis();
//            c4=c4.highPass(amount);
//            c4=c4.swapAxis();
//            
//            
//            c4=c4.lowPass(amount2);
//            c4=c4.swapAxis();
//            c4=c4.lowPass(amount2);
//            c4=c4.swapAxis();
            
//            c4=c4.ifft();
//            c4=c4.fft();
//            c4=c4.ifft();
//            c4=c4.fft();
//             c4=c4.ifft();
//            c4=c4.fft();
//            c4=c4.ifft();
//            c4=c4.fft();
            //c4=c4.scale(4);
//       }
      //c4=c4.topNMagnitudes(2);
       //c4=c4.swapAxis();
       //TODO make a version of topNWaves that does the swap axis for us,.
       
       //cache = cache.subtract(c4);
       
//       double nonZero = c4.countNonZeroComponents();
//       double zero = c4.countZeroComponents();
//       System.out.println(nonZero);
//       System.out.println(zero);
//       
//       System.out.println((nonZero/(nonZero+zero))*100.+"%");
//       
       
       
       //double scale = .00001;
      // c4=c4.scale(scale);
       
      // c4=c4.reduceToHalfPrecision();
       
      // c4=c4.scale(1./scale);
       //c4=c4.scale(.000001);
       //c4=c4.ifft();
       //c4=c4.scale(.0000025);
      // c4.WriteRealsToImageFile("d:\\data\\girlextremecompressionreals.png");
      // c4.WriteImaginariesToImageFile("d:\\data\\girlextremecompressionimaginaries.png");
     //c4.writeNonZeros();;
     //c4=c4.lowPass(50);
     
     // c4=c4.lowPass(400);
      //c4=c4.highPass(399);
      
      //c4 = c4.topNMagnitudes(100);
      //c4=c4.ifft();
     
       
      //c4Temp=c4Temp.scale(.75);
      //c4=c4Temp.subtract(c4);
      
       //c4=c4.scale(19);
      //c4=c4.add(c4Temp);
       //c4.writeNonZeros();;
        //c4.writeNonZeros();;
//       if (test){
//            //c4=c4.scale(3);
//            c4.WriteRealsToImageFile("d:\\data\\acExperimental.png");
//       }
//       else{
       // c4=c4.add(256);
       //c4=c4.scale(50);
       
        //c4=c4.add(50.);
      //c4.WriteRealsToImageFile("c:\\data\\catout.png");
       
//       }
      Buffer3D test = Buffer3D.generateTestSphere(32,256);
      test=test.fft();
      
      test=test.highPass(32);
      //test=test.hiPass(20);
      
      int frame = 123;
      test=test.centerShift();
      test=test.centerShift();
      test=test.ifft();
      
      ImageThreeChannel i = new ImageThreeChannel(test.buffers2d[frame],test.buffers2d[frame],test.buffers2d[frame]);
      
      
      i=i.scale(1000);
      
      
      
      i.WriteRealsToImageFile("c:\\data\\circletest.png");
      System.out.println(java.lang.System.currentTimeMillis()-startTime);
       //cache=cache.ifft();
       
       //cache.WriteRealsToImageFile("d:\\data\\girldiff.png");
       
      // ImageThreeChannel reals = new ImageThreeChannel("d:\\data\\girlextremecompressionreals.png");
      // ImageThreeChannel imaginaries = new ImageThreeChannel("d:\\data\\girlextremecompressionimaginaries.png");
       
       //reals=reals.add(imaginaries);
       //reals=reals.scale(1./.0000025);
       //reals=reals.ifft();
       //reals.WriteRealsToImageFile("d:\\data\\expectingbadresults.png");
       
    }
    
}
