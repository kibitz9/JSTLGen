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
public class SDFOperationCSGUnion extends SignedDistanceField3d{
    private SignedDistanceField3d one;
    private SignedDistanceField3d two;
    
    
    public SDFOperationCSGUnion(SignedDistanceField3d one, SignedDistanceField3d two, SignedDistanceField3d three){
        this.one = one;
        this.two = new SDFOperationCSGUnion(two,three);
    }
    public SDFOperationCSGUnion(SignedDistanceField3d one, SignedDistanceField3d two, SignedDistanceField3d three,SignedDistanceField3d four){
        this.one = new SDFOperationCSGUnion(one,two);
        this.two = new SDFOperationCSGUnion(three,four);
    }
    public SDFOperationCSGUnion(SignedDistanceField3d one, SignedDistanceField3d two, SignedDistanceField3d three,SignedDistanceField3d four
        ,SignedDistanceField3d five){
        this.one = new SDFOperationCSGUnion(one,two);
        this.two = new SDFOperationCSGUnion(three,new SDFOperationCSGUnion(four,five));
    }
    public SDFOperationCSGUnion(
            SignedDistanceField3d one, SignedDistanceField3d two
            ,SignedDistanceField3d three,SignedDistanceField3d four
            ,SignedDistanceField3d five, SignedDistanceField3d six){
        
        this.one = new SDFOperationCSGUnion(one,new SDFOperationCSGUnion(two,three));
        this.two = new SDFOperationCSGUnion(four,new SDFOperationCSGUnion(five,six));
    }
    public SDFOperationCSGUnion(
            SignedDistanceField3d one, SignedDistanceField3d two
            ,SignedDistanceField3d three,SignedDistanceField3d four
            ,SignedDistanceField3d five, SignedDistanceField3d six
            ,SignedDistanceField3d seven){
        this.one = new SDFOperationCSGUnion(new SDFOperationCSGUnion(one,two),new SDFOperationCSGUnion(three,four));
        this.two = new SDFOperationCSGUnion(new SDFOperationCSGUnion(five,six),seven);
    }
    public SDFOperationCSGUnion(
            SignedDistanceField3d one, SignedDistanceField3d two
            ,SignedDistanceField3d three,SignedDistanceField3d four
            ,SignedDistanceField3d five, SignedDistanceField3d six
            ,SignedDistanceField3d seven, SignedDistanceField3d eight){
        this.one = new SDFOperationCSGUnion(new SDFOperationCSGUnion(one,two),new SDFOperationCSGUnion(three,four));
        this.two = new SDFOperationCSGUnion(new SDFOperationCSGUnion(five,six),new SDFOperationCSGUnion(seven,eight));
    }
    
    
    public SDFOperationCSGUnion(SignedDistanceField3d one, SignedDistanceField3d two)
    {
        this.one = one;
        this.two = two;
    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationCSGUnion(one.Clone(), two.Clone());
    }
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {
        return min(one.GetDistance(translatedp), two.GetDistance(translatedp));
    } 
    @Override
    public ShaderString toShaderString(String parmValue){
        
        ShaderString o = one.toShaderString(parmValue);
        ShaderString t = two.toShaderString(parmValue);
        
        String left = ShaderString.nextVariableName("unionleft");
        String right = ShaderString.nextVariableName("unionRight");
        
        
        String d = o.defines+t.defines;
        d+="\r\n\tfloat "+left+"="+o.code+";";
        d+="\r\n\tfloat "+right+"="+t.code+";";
        
        
        String c = "min("+left+","+right+")";
        
        return new ShaderString(d,c,o.constantsAndFunctions+"\r\n"+t.constantsAndFunctions);
    }
}
