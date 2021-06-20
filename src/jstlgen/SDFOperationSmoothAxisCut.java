/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen;

/**
 *
 * @author cmiller
 */
public class SDFOperationSmoothAxisCut extends SignedDistanceField3d{
     SignedDistanceField3d toCut;
     Axis axis;
     double offset;
     boolean clipPositiveSide;
     double k;
     double oneSixthOverK;
     public SDFOperationSmoothAxisCut (SignedDistanceField3d toCut, Axis axis, double offset, boolean clipPositiveSide, double k)
     {
         this.toCut = toCut;
         this.axis = axis;
         this.offset = offset;
         this.clipPositiveSide = clipPositiveSide;
         this.k = k;
         this.oneSixthOverK = (1.0 / 6.0) / (k*k);
     }
     @Override
     public double GetRawDistance(Vector3d translatedp)
     {
         double a=translatedp.x;
         if (axis == Axis.Y)
         {
             a = translatedp.y;
         }
         else if (axis == Axis.Z)
         {
             a = translatedp.z;
         }

         if (!clipPositiveSide)
         {
             a*= -1;
         }
         a += offset;
         
         double b=toCut.GetDistance(translatedp);
         
         
         double h = max(k - abs(a - b), 0.0);
         return max(a, b) + h * h * h * oneSixthOverK;
         
         

     }
     public enum Axis
     {
         X,
         Y,
         Z,

     }
     @Override
     public SignedDistanceField3d Clone()
     {
         return new SDFOperationSmoothAxisCut(toCut.Clone(), axis, offset, clipPositiveSide,k);
     }
     @Override 
     public ShaderString toShaderString(String parmValue){
         String k = ShaderString.nextVariableName("k");
         String a = ShaderString.nextVariableName("a");
         String b = ShaderString.nextVariableName("b");
         String h = ShaderString.nextVariableName("h");
         String zz = ShaderString.nextVariableName("zz");// 
         
         
         String oneSixthOverk = ShaderString.nextVariableName("z");
         
         String temp = parmValue+".x";
         if (axis == Axis.Y)
         {
             temp = parmValue+".y";
         }
         else if (axis == Axis.Z)
         {
             temp = parmValue+".z";
         }

         if (!clipPositiveSide)
         {
             temp="-"+temp;
         }
         temp=temp+"+"+this.offset;
                 
         ShaderString toCt = toCut.toShaderString(parmValue);
         
         String defines ="\r\n\tfloat "+a+"="+temp+";";
         defines +="\r\n\tfloat "+b+"="+toCt.code+";";
         defines += "\r\n\tfloat "+k+"="+this.k+";";
         defines += "\r\n\tfloat "+h+"=max("+k+"-abs("+a+"-"+b+"),0.0);";
         defines +="\r\n\tfloat "+zz+"= (1.0 / 6.0) / ("+k+"*"+k+");";
         String c = "max("+a+","+b+")+"+h+"*"+h+"*"+h+"*"+zz;
         
        
         defines = toCt.defines+defines;
         return new ShaderString(defines,c,toCt.functions);
         
         
         
     }
}
