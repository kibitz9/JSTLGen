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
        
        ShaderString o = one.toShaderString("p");
        ShaderString t = two.toShaderString("p");
        
       // String left = ShaderString.nextVariableName("unionleft");
       // String right = ShaderString.nextVariableName("unionRight");
        
        String fn1 = ShaderString.nextVariableName("sdf");
        String fn2 = ShaderString.nextVariableName("sdf");
        
        
       // String d = o.defines+t.defines;
        //d+="\r\n\tfloat "+left+"="+o.code+";";
       // d+="\r\n\tfloat "+right+"="+t.code+";";
        
        String functions = "";
        functions+="\r\nfloat "+fn1+"(vec3 p){";
        functions+=o.defines;
        functions+="\r\n\treturn ("+o.code+");";
        functions+="\r\n}";
        
        functions+="\r\nfloat "+fn2+"(vec3 p){";
        functions+=t.defines;
        functions+="\r\n\treturn ("+t.code+");";
        functions+="\r\n}";
        
      
        String color = "";
        
        if (o.color.equals(t.color)){
            color = o.color;
        }
        else{
            String fn = ShaderString.nextVariableName("union_color");
            functions +="\r\nvec3 "+fn+"(vec3 p){"; 
            functions +="\r\n\tfloat d1 = "+fn1+"(p);";
            functions +="\r\n\tfloat d2 = "+fn2+"(p);";
            functions +="\r\n\tif (abs(d1)<abs(d2)){";
            functions +="\r\n\t\treturn "+o.color+";";
            functions +="\r\n\t}";
            functions +="\r\n\telse{";
            functions +="\r\n\t\treturn "+t.color+";";
            functions +="\r\n\t}";
            functions +="\r\n}";
            
            color = fn+"(p)";
            
            
            
        }
        
        
        
        String c = "min("+fn1+"(<parm>),"+fn2+"(<parm>))";
        c=c.replace("<parm>",parmValue);
        return new ShaderString("",c,o.constantsAndFunctions+"\r\n"+t.constantsAndFunctions+functions,color);
    }
}
