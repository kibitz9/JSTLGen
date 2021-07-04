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
        
        
        String d="\r\n\tfloat "+radiusSquaredPlusPaddingS+"="+radiusSquaredPlusPadding+";";
        d+="\r\n\tfloat "+radiusS+"="+radius+";";
        d+="\r\n\tfloat "+dist+";";
        
        d+="\r\n\tif (length(<parm>)>"+radiusSquaredPlusPaddingS+"){";
        d+="\r\n\t\t"+dist+"=length(<parm>)-"+radiusS+";";
        d+="\r\n\t}";
        d+="\r\n\telse{";
        ShaderString ss = toContain.toShaderString(parm);
        d+=ss.defines.replace("\r\n\t","\r\n\t\t");
        d+="\r\n\t\t"+dist+"="+ss.code+";";
        d+="\r\n\t}";

        d=d.replace("<parm>",parm);
     
        String c = dist;
        
 
        
        return new ShaderString(d,c,ss.constantsAndFunctions+"\r\n");
        
    }
    
}
