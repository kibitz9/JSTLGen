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
public class SDFOperationUniformScale extends SignedDistanceField3d{
    private double scale;
    private double oneOverScale;
    private SignedDistanceField3d toScale;
    public SDFOperationUniformScale(SignedDistanceField3d toScale, double scale)
    {
        this.toScale = toScale;
        this.oneOverScale = 1/scale;

        this.scale = scale;
    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationUniformScale(toScale.Clone(), scale);
    }
    @Override
    public double GetRawDistance(Vector3d p)
    {
        return this.toScale.GetDistance(p.Scale(oneOverScale))*scale;
    }
    
    @Override
    public ShaderString toShaderString(String parmValue){
        String scaleV = ShaderString.nextVariableName("SCALE");
        String newVar = ShaderString.nextVariableName("sp");
        String f="\r\nconst float "+scaleV+"="+scale+";";
        String d = "";//\r\n\tfloat "+scaleV+"="+scale+";";
        d += "\r\n\tvec3 "+newVar+"=<parm>/"+scaleV+";";
       
        d=d.replace("<parm>", parmValue);
        ShaderString x = toScale.toShaderString(newVar);
        
        
        String functions = "";
            String color;
            String fn = ShaderString.nextVariableName("scale_color");
            functions +="\r\nvec3 "+fn+"(vec3 q){"; 
            functions +="\r\n\tvec3 p=q/"+scaleV+";";
            functions +="\r\n\treturn "+x.color+";";
            functions +="\r\n}";
            color = fn+"(p)";    
        
        
        return new ShaderString(d+x.defines,"("+x.code+")*"+scaleV,f+x.constantsAndFunctions+functions,color);
    }
    //float opScale(in vec3 p, in float s, in sdf3d primitive)
    //{
    //    return primitive(p / s) * s;
    //}

}
