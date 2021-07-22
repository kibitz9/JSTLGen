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
public  class Buffer2D{
    public Buffer1D[] buffers1d;
    public Buffer2D(Buffer1D[] buffers1d){
        this.buffers1d=buffers1d;
    }
    
    
    public Buffer2D fft(){
        //first, fft all the individual rows...
        Buffer2D temp = this.onedfft();
        temp=temp.SwapAxis();
        temp = temp.onedfft();
        temp=temp.SwapAxis();
        return temp;
    }
    public Buffer2D onedfft(){
        //fft only in one dimension
        Buffer1D[] temp = new Buffer1D[buffers1d.length];
        for (int a=0;a<buffers1d.length;a++){
            temp[a] = buffers1d[a].fft();
        }
        return new Buffer2D(temp);
    }
    
    
    public Buffer2D SwapAxis(){
        if (buffers1d.length==0){
            return this;
        }
        if (buffers1d.length!=buffers1d[0].reals.length){
            throw new java.lang.RuntimeException("Buffer axis swaps require row count and row length to be the same!");
        }
        int size = buffers1d.length;
        Buffer1D[] returnBuffers = new Buffer1D[size];
        for (int a=0;a<size;a++){
            double[] newRowReals = new double[size];
            double[] newRowImaginaries = new double[size];
            for (int b=0;b<size;b++){
                newRowReals[b]=buffers1d[b].reals[a];
                newRowImaginaries[b] = buffers1d[b].imaginaries[a];
            }
            returnBuffers[a]=new Buffer1D(newRowReals,newRowImaginaries);
        }
        return new Buffer2D(returnBuffers);
    }

    @Override
    public String toString(){
        String returnString = "";
        for(int a=0;a<buffers1d.length;a++){
            returnString+=buffers1d[a].toString()+"\r\n";
        }
        return returnString;
    }
}