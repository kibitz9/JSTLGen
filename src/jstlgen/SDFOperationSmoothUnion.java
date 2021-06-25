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
public class SDFOperationSmoothUnion extends SignedDistanceField3d{
    private SignedDistanceField3d one;
    private SignedDistanceField3d two;
    private double k;
    private double oneSixthOverK;
    public SDFOperationSmoothUnion(SignedDistanceField3d one, SignedDistanceField3d two,double k)
    {
        this.one = one;
        this.two = two;
        this.k = k;
        this.oneSixthOverK = (1.0 / 6.0) / (k*k);
    }
    
    public SDFOperationSmoothUnion(SignedDistanceField3d one, SignedDistanceField3d two, SignedDistanceField3d three,double k){
        this.one = one;
        this.two = new SDFOperationSmoothUnion(two,three,k);
        this.k = k;
        this.oneSixthOverK = (1.0 / 6.0) / (k*k);
    }
    public SDFOperationSmoothUnion(SignedDistanceField3d one, SignedDistanceField3d two, SignedDistanceField3d three,SignedDistanceField3d four,double k){
        this.one = new SDFOperationSmoothUnion(one,two,k);
        this.two = new SDFOperationSmoothUnion(three,four,k);
        this.k = k;
        this.oneSixthOverK = (1.0 / 6.0) / (k*k);
    }
    public SDFOperationSmoothUnion(SignedDistanceField3d one, SignedDistanceField3d two, SignedDistanceField3d three,SignedDistanceField3d four
        ,SignedDistanceField3d five,double k){
        this.one = new SDFOperationSmoothUnion(one,two,k);
        this.two = new SDFOperationSmoothUnion(three,new SDFOperationSmoothUnion(four,five,k),k);
        this.k = k;
        this.oneSixthOverK = (1.0 / 6.0) / (k*k);
    }
    public SDFOperationSmoothUnion(
            SignedDistanceField3d one, SignedDistanceField3d two
            ,SignedDistanceField3d three,SignedDistanceField3d four
            ,SignedDistanceField3d five, SignedDistanceField3d six,double k){
        
        this.one = new SDFOperationSmoothUnion(one,new SDFOperationSmoothUnion(two,three,k),k);
        this.two = new SDFOperationSmoothUnion(four,new SDFOperationSmoothUnion(five,six,k),k);
        this.k = k;
        this.oneSixthOverK = (1.0 / 6.0) / (k*k);
    }
    public SDFOperationSmoothUnion(
            SignedDistanceField3d one, SignedDistanceField3d two
            ,SignedDistanceField3d three,SignedDistanceField3d four
            ,SignedDistanceField3d five, SignedDistanceField3d six
            ,SignedDistanceField3d seven,double k){
        this.one = new SDFOperationSmoothUnion(new SDFOperationSmoothUnion(one,two,k),new SDFOperationSmoothUnion(three,four,k),k);
        this.two = new SDFOperationSmoothUnion(new SDFOperationSmoothUnion(five,six,k),seven,k);
        this.k = k;
        this.oneSixthOverK = (1.0 / 6.0) / (k*k);
    }
    public SDFOperationSmoothUnion(
            SignedDistanceField3d one, SignedDistanceField3d two
            ,SignedDistanceField3d three,SignedDistanceField3d four
            ,SignedDistanceField3d five, SignedDistanceField3d six
            ,SignedDistanceField3d seven, SignedDistanceField3d eight,double k){
        this.one = new SDFOperationSmoothUnion(new SDFOperationSmoothUnion(one,two,k),new SDFOperationSmoothUnion(three,four,k),k);
        this.two = new SDFOperationSmoothUnion(new SDFOperationSmoothUnion(five,six,k),new SDFOperationSmoothUnion(seven,eight,k),k);
        this.k = k;
        this.oneSixthOverK = (1.0 / 6.0) / (k*k);
    }
    
    
    
    
    
    
    
    
    
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {
        double a = two.GetDistance(translatedp);
        double b = one.GetDistance(translatedp);

        double h = max(k - abs(a - b), 0.0);
        return min(a, b) - h * h * h * oneSixthOverK;


    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationSmoothUnion(one.Clone(), two.Clone(), k);
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
         String c = "min("+a+","+b+")-"+h+"*"+h+"*"+h+"*"+zz;
         

         defines = twoSS.defines+oneSS.defines+defines;
         return new ShaderString(defines,c,oneSS.functions+"\r\n"+twoSS.functions);
    }
}
