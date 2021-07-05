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
public class SDFMaterial extends SignedDistanceField3d {

    SignedDistanceField3d obj;
    MaterialFunction function;
    
    public SDFMaterial (SignedDistanceField3d obj, MaterialFunction function){
        this.obj=obj;
        this.function=function;
    }
    
    @Override
    public double GetRawDistance(Vector3d p) {
        return obj.GetRawDistance(p);
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDFMaterial(obj, function);
    }
    
    @Override
    public ShaderString toShaderString(String parm){
        ShaderString ss = obj.toShaderString("p");
        String fnName = ShaderString.nextVariableName("sdf");
        //String mfnName = ShaderString.nextVariableName("msdf");
        String 
          f0 ="\r\nfloat "+fnName+"(vec3 p){";
          f0+=ss.defines;
          f0+="\r\n\treturn "+ss.code+";";
          f0+="\r\n}";
          
        //String 
        //  f1 ="\r\nvec3 "+fnName+"_color(vec3 p){";
        //  f1+="\r\n\treturn "+function.toShaderString()+";";
        //  f1+="\r\n}";  
           
        String color = function.getColorString();
        
        String code = fnName+"(<parm>)";
        code=code.replace("<parm>",parm);
        return new ShaderString("",code,ss.constantsAndFunctions+f0,color);//+f1);
    }
   
    
    
}
