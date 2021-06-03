/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen;

/**
 *
 * @author Christopher.Miller
 */
public class Util {
    public static int AppendBytes(byte[] target, byte[] toAppend, int index)
     {

         for(int a = 0; a < toAppend.length; a++)
         {
             target[index++] = toAppend[a];
         }
         return index;
     }

     public static int AppendBytes(byte[] target, int toAppend, int index)
     {
         byte [] b = new byte[4];
            b[0] = (byte)toAppend;
            b[1] = (byte)(toAppend>>8);
            b[2] = (byte)(toAppend>>16);
            b[3] = (byte)(toAppend>>24);
            return AppendBytes(target,b,index);
         //return index;
     }
    

}
