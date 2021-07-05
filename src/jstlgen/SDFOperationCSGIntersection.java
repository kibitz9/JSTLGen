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
public class SDFOperationCSGIntersection extends SignedDistanceField3d{
    private SignedDistanceField3d one;
    private SignedDistanceField3d two;
    public SDFOperationCSGIntersection(SignedDistanceField3d one, SignedDistanceField3d two)
    {
        this.one = one;
        this.two = two;
    }
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {
        return max(one.GetDistance(translatedp), two.GetDistance(translatedp));
    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationCSGIntersection(one.Clone(), two.Clone());
    }
    @Override
    public ShaderString toShaderString(String parm){
        ShaderString ss1 = one.toShaderString("p");
        ShaderString ss2 = two.toShaderString("p");
        
        String fn1 = ShaderString.nextVariableName("sdf");
        String fn2 = ShaderString.nextVariableName("sdf");
        
        
        String functions = "";
        functions+="\r\nfloat "+fn1+"(vec3 p){";
        functions+=ss1.defines;
        functions+="\r\n\treturn ("+ss1.code+");";
        functions+="\r\n}";
        
        functions+="\r\nfloat "+fn2+"(vec3 p){";
        functions+=ss2.defines;
        functions+="\r\n\treturn ("+ss2.code+");";
        functions+="\r\n}";

        
        //String defines = ss1.defines+ss2.defines;
        String code = "max("+fn1+"(<parm>),"+fn2+"(<parm>))";
        
        code=code.replace("<parm>",parm);
        
        String color = "";
        
        if (ss1.color.equals(ss2.color)){
            color = ss1.color;
        }
        else{
            String fn = ShaderString.nextVariableName("intersection_color");
            functions +="\r\nvec3 "+fn+"(vec3 p){"; 
            functions +="\r\n\tfloat d1 = "+fn1+"(p);";
            functions +="\r\n\tfloat d2 = "+fn2+"(p);";
            functions +="\r\n\tif (abs(d1)<abs(d2)){";
            functions +="\r\n\t\treturn "+ss1.color+";";
            functions +="\r\n\t}";
            functions +="\r\n\telse{";
            functions +="\r\n\t\treturn "+ss2.color+";";
            functions +="\r\n\t}";
            functions +="\r\n}";
            
            color = fn+"(p)";
            
            
            
        }
        
        //String colorFunction = ss.color;  
        
        
        return new ShaderString("",code,ss1.constantsAndFunctions+"\r\n"+ss2.constantsAndFunctions+functions,color);
    }
    
}
