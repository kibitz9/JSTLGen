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
public class SDFOperationSmoothDifference extends SignedDistanceField3d{
    private final SignedDistanceField3d one;
    private final SignedDistanceField3d two;
    private final double k;
    private final double oneSixthOverK;
    public SDFOperationSmoothDifference(SignedDistanceField3d one, SignedDistanceField3d two,double k)
    {
        this.one = one;
        this.two = two;
        this.k = k;
        this.oneSixthOverK = (1.0 / 6.0) / (k*k);
    }
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {
        double a = -two.GetDistance(translatedp);//negative here.
        double b = one.GetDistance(translatedp);

        double h = max(k - abs(a - b), 0.0);
        return max(a, b) + h * h * h * oneSixthOverK;


    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationSmoothDifference(one.Clone(), two.Clone(), k);
    }
    @Override
    public ShaderString toShaderString(String parmValue){
        String k = ShaderString.nextVariableName("k");
         String a = ShaderString.nextVariableName("a");
         String b = ShaderString.nextVariableName("b");
         String h = ShaderString.nextVariableName("h");
         String zz = ShaderString.nextVariableName("zz");// 
         
         
         //String oneSixthOverk = ShaderString.nextVariableName("z");
         
         
                 
         ShaderString oneSS = one.toShaderString(parmValue);
         ShaderString twoSS = two.toShaderString(parmValue);
         
         
         String defines ="\r\n\tfloat "+a+"=-("+twoSS.code+");";
         defines +="\r\n\tfloat "+b+"="+oneSS.code+";";
         defines += "\r\n\tfloat "+k+"="+this.k+";";
         defines += "\r\n\tfloat "+h+"=max("+k+"-abs("+a+"-"+b+"),0.0);";
         defines +="\r\n\tfloat "+zz+"= (1.0 / 6.0) / ("+k+"*"+k+");";
         String c = "max("+a+","+b+")+"+h+"*"+h+"*"+h+"*"+zz;
         

         defines = twoSS.defines+oneSS.defines+defines;
         return new ShaderString(defines,c,oneSS.functions+"\r\n"+twoSS.functions);
    }
}
