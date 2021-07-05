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
public class SDFOperationBoundingSphere extends SignedDistanceField3d{

    SignedDistanceField3d toContain;
    double radius;
    double radiusSquared;
    double padding;
    double radiusSquaredPlusPadding;
    
    public SDFOperationBoundingSphere(SignedDistanceField3d toContain, double radius, double padding){
        this.toContain=toContain;
        this.radius=radius;
        this.radiusSquared=radius*radius;
        this.padding=padding;
        this.radiusSquaredPlusPadding =(radius+padding)*(radius+padding);
    }
  
    
    @Override
    public double GetRawDistance(Vector3d p) {
        //Vector3d q = new Vector3d(p);
      
        if (p.GetMagnitudeSquared()>radiusSquaredPlusPadding){
            return p.GetMagnitude()-radius;
        }
        
        return toContain.GetDistance(p);
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDFOperationBoundingSphere(toContain,radius,padding); 
    }
    

    
    @Override
    public ShaderString toShaderString(String parm){
        
        String radiusSquaredPlusPaddingS = ShaderString.nextVariableName("rp");
        String radiusS = ShaderString.nextVariableName("r");
        String dist = ShaderString.nextVariableName("d");
        String sdfS = ShaderString.nextVariableName("sdf");
        
        String f0 = "\r\nfloat "+sdfS+"(vec3 p){";
        f0+="\r\n\tfloat "+radiusSquaredPlusPaddingS+"="+radiusSquaredPlusPadding+";";
        f0+="\r\n\tfloat "+radiusS+"="+radius+";";
        f0+="\r\n\tfloat "+dist+";";
        f0+="\r\n\tfloat lp=length(p);";
        
        f0+="\r\n\tif (lp>"+radiusSquaredPlusPaddingS+"+.2){";
        f0+="\r\n\t\t"+dist+"=lp-("+radiusS+"+.1);";
        f0+="\r\n\t}";
        f0+="\r\n\telse{";
        ShaderString ss = toContain.toShaderString("p");
        f0+=ss.defines.replace("\r\n\t","\r\n\t\t");
        f0+="\r\n\t\t"+dist+"="+ss.code+";";
        
        f0+="\r\n\t}";
        f0+="\r\nreturn "+dist+";";
        f0+="\r\n}";

        
     
        String c = sdfS+"(<parm>)";
        c=c.replace("<parm>", parm);
        
 
        String color = ss.color;   
        
        return new ShaderString("",c,ss.constantsAndFunctions+"\r\n"+f0,color);
        
    }
    
}
