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
public class SDFOperationSmoothIntersection extends SignedDistanceField3d {
    private SignedDistanceField3d one;
    private SignedDistanceField3d two;
    private double k;
    private double oneSixthOverK;
    public SDFOperationSmoothIntersection(SignedDistanceField3d one, SignedDistanceField3d two,double k)
    {
        this.one = one;
        this.two = two;
        this.k = k;
        this.oneSixthOverK = (1.0 / 6.0) / (k*k);
    }
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {
        double a = two.GetDistance(translatedp);
        double b = one.GetDistance(translatedp);

        double h = max(k - abs(a - b), 0.0);
        return max(a, b) + h * h * h * oneSixthOverK;


    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationSmoothIntersection(one.Clone(), two.Clone(), k);
    }

    @Override
    public ShaderString toShaderString(String parm){
         String k = ShaderString.nextVariableName("k");
         String a = ShaderString.nextVariableName("a");
         String b = ShaderString.nextVariableName("b");
         String h = ShaderString.nextVariableName("h");
         String zz = ShaderString.nextVariableName("zz");// 
                 
         ShaderString oneSS = one.toShaderString(parm);
         ShaderString twoSS = two.toShaderString(parm);
         
         
         String defines ="\r\n\tfloat "+a+"=("+twoSS.code+");";
         defines +="\r\n\tfloat "+b+"="+oneSS.code+";";
         defines += "\r\n\tfloat "+k+"="+this.k+";";
         defines += "\r\n\tfloat "+h+"=max("+k+"-abs("+a+"-"+b+"),0.0);";
         defines +="\r\n\tfloat "+zz+"= (1.0 / 6.0) / ("+k+"*"+k+");";
         String c = "max("+a+","+b+")+"+h+"*"+h+"*"+h+"*"+zz;
         

         defines = twoSS.defines+oneSS.defines+defines;
         return new ShaderString(defines,c,oneSS.constantsAndFunctions+"\r\n"+twoSS.constantsAndFunctions);
    }
    
    
}
