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
public class SDF3dPrimitiveSphere extends SignedDistanceField3d {

    private final double radius;
    public SDF3dPrimitiveSphere(double radius)
    {
        this.radius = radius;
    }
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {
        return translatedp.GetMagnitude() - radius;
    }
     @Override
    public SignedDistanceField3d Clone()
    {
        return new SDF3dPrimitiveSphere(radius);
    }

    @Override
    public ShaderString toShaderString(String parmValue){
       
        String varName2 = ShaderString.nextVariableName("radius");
        String comment = ShaderString.nextVariableName("\r\n\t//Sphere");
        String d = comment;
        d += "\r\n\tfloat "+varName2+" ="+radius+";";
        String c = "length(<parm>)-"+varName2;
        c=c.replace("<parm>", parmValue);
        
        return new ShaderString(d,c);
        
    }
  
    
}
