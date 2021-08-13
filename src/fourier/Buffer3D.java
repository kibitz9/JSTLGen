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
public class Buffer3D {
    public Buffer2D[] buffers2d;
    public Buffer3D(Buffer2D[] buffers2d){
        this.buffers2d=buffers2d;
    }
    
    
     
    public Buffer3D fft(){
        //first, do x and y...
        Buffer3D returnBuffers = _fftIndividualBuffers();//x and y axis
        returnBuffers=returnBuffers.SwapAxis();
        //returnBuffers=returnBuffers._fftIndividualBuffers();
        returnBuffers=returnBuffers._fftIndividualRows();//only one axis remains.
        
        
        returnBuffers=returnBuffers.SwapAxis();
        return returnBuffers;
    }
    
    public Buffer3D ifft(){
        //first, do x and y...
        Buffer3D returnBuffers = _ifftIndividualBuffers();
        returnBuffers=returnBuffers.SwapAxis();
        returnBuffers=returnBuffers._ifftIndividualRows();//only one axis remains
        returnBuffers=returnBuffers.SwapAxis();
        return returnBuffers;
    }
    
    private Buffer3D _fftIndividualBuffers(){
        Buffer2D[] returnBuffers = new Buffer2D[buffers2d.length];
        for (int a=0;a<returnBuffers.length;a++){
            returnBuffers[a]=buffers2d[a].fft();
        }
        return new Buffer3D(returnBuffers);
    }
    private Buffer3D _fftIndividualRows(){
        Buffer2D[] returnBuffers = new Buffer2D[buffers2d.length];
        for (int a=0;a<returnBuffers.length;a++){
            returnBuffers[a]=buffers2d[a].onedfft();
        }
        return new Buffer3D(returnBuffers);
    }
    
    private Buffer3D _ifftIndividualRows(){
        Buffer2D[] returnBuffers = new Buffer2D[buffers2d.length];
        for (int a=0;a<returnBuffers.length;a++){
            returnBuffers[a]=buffers2d[a].onedifft();
        }
        return new Buffer3D(returnBuffers);
    }
    
    public Buffer3D centerShift(){
        int size = this.buffers2d.length;
        int sizeD2 = size/2;
        Buffer2D[] newBuffers = new Buffer2D[size];
        for (int a=0;a<buffers2d.length;a++){
            int offsetIndex = (a+sizeD2)%size;
            newBuffers[a]=buffers2d[offsetIndex].centerShift();
            
        }
        return new Buffer3D(newBuffers);
    }
    
    private Buffer3D _ifftIndividualBuffers(){
        Buffer2D[] returnBuffers = new Buffer2D[buffers2d.length];
        for (int a=0;a<returnBuffers.length;a++){
            returnBuffers[a]=buffers2d[a].ifft();
        }
        return new Buffer3D(returnBuffers);
    }
    
    public static Buffer3D generateTestSphere(double sphereRadius, int bufferSize){
        Buffer2D[] returnBuffers = new Buffer2D[bufferSize];
        for (int a=0;a<bufferSize;a++){
            returnBuffers[a]=new Buffer2D(bufferSize);
        }
        
        double bufferSizeDouble = bufferSize;
        double halfBufferSize = bufferSizeDouble/2.;
        
        for (double x=0;x<bufferSizeDouble;x++){
            for (double y=0;y<bufferSizeDouble;y++){
                for(double z=0;z<bufferSizeDouble;z++){
                    double distx = (halfBufferSize-x);
                    double disty = (halfBufferSize-y);
                    double distz = (halfBufferSize-z);
                    
                    double distxSquared = distx*distx;
                    double distySquared = disty*disty;
                    double distzSquared = distz*distz;
                    
                    double actualDist = Math.sqrt(distxSquared+distySquared+distzSquared);
                    
                    
                    double diff = actualDist-sphereRadius;
                    
                    if (diff>1){
                        returnBuffers[(int)z].buffers1d[(int)y].reals[(int)x]=0.;//this really does nothing but for understanding.
                    }
                    else if (diff<=0){
                        returnBuffers[(int)z].buffers1d[(int)y].reals[(int)x]=1.;
                    }
                    else{
                        //dist less than or equal to 1 but greater than zero
                        //interpolate close 3d pixel
                        //not super acurate but better than no anti-aliasing at all
                        returnBuffers[(int)z].buffers1d[(int)y].reals[(int)x]=1-diff;
                    }
                    
                    
                    
                }
            }
        }
        
        return new Buffer3D(returnBuffers);
        
        
        
    }
    
    
    public Buffer3D SwapAxis(){
        if (buffers2d.length==0){
            return this;
        }
        
        if (buffers2d.length!=buffers2d[0].buffers1d.length){
            throw new java.lang.RuntimeException("All dimension sizes must match");
        }
        if (buffers2d[0].buffers1d[0].reals.length==0){
            throw new java.lang.RuntimeException("All dimension sizes must match");
        }
        if (buffers2d.length!=buffers2d[0].buffers1d[0].reals.length){
            throw new java.lang.RuntimeException("All dimension sizes must match");
        }
        
        int size = buffers2d.length;
        
        //to make things easy, allocate the entire result first. This allows us
        //to simply swap axes without worrying too much about allocating as we go.
        
        Buffer2D[] returnBuffers = new Buffer2D[size];
        for (int z=0;z<size;z++){
            Buffer1D[] temp = new Buffer1D[size];
            for (int y=0;y<size;y++){
                temp[y]=new Buffer1D(size);
            }
            returnBuffers[z]=new Buffer2D(temp);
        }
        
        
        for (int y=0;y<size;y++){

            for (int x=0;x<size;x++){
               
                
                for (int z=0;z<size;z++){
                    //since we pre-allocated the buffer, it's just a matter of switching
                    //x and z between the read and write.
                    //why x and z and not y? Assuming fft has already occurred
                    //for non-z axises, then y has already been processed
                    //x is always the axis where the individual fft occurs.
                    //and has already been processed too.
                    //z had not been processed, so we move z into place along the x
                    //axis.
                    returnBuffers[z].buffers1d[y].reals[x]=buffers2d[x].buffers1d[y].reals[z];
                    returnBuffers[z].buffers1d[y].imaginaries[x]=buffers2d[x].buffers1d[y].imaginaries[z];
                    
                }
            }
            
        }
        return new Buffer3D(returnBuffers);
        
        
//        if (buffers2d.length!=buffers2d[0].reals.length){
//            throw new java.lang.RuntimeException("Buffer axis swaps require row count and row length to be the same!");
//        }
//        int size = buffers1d.length;
//        Buffer1D[] returnBuffers = new Buffer1D[size];
//        for (int a=0;a<size;a++){
//            double[] newRowReals = new double[size];
//            double[] newRowImaginaries = new double[size];
//            for (int b=0;b<size;b++){
//                newRowReals[b]=buffers1d[b].reals[a];
//                newRowImaginaries[b] = buffers1d[b].imaginaries[a];
//            }
//            returnBuffers[a]=new Buffer1D(newRowReals,newRowImaginaries);
//        }
//        return new Buffer2D(returnBuffers);
    }
    
}
