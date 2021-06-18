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
public class SDF3dPrimitiveTorus extends SignedDistanceField3d {
    double primaryRadius;
    double secondaryRadius;

    public SDF3dPrimitiveTorus(double primaryRadius, double secondaryRadius)
    {
        this.primaryRadius = primaryRadius;
        this.secondaryRadius = secondaryRadius;
    }
    @Override
    public double GetRawDistance(Vector3d p)
    {
        Vector2d temp = new Vector2d(p.GetXZ().GetMagnitude() - primaryRadius, p.y);
        return temp.GetMagnitude() - secondaryRadius;
    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDF3dPrimitiveTorus(primaryRadius, secondaryRadius);
    }

    @Override
    public ShaderString toShaderString(String parmValue){
        String primaryRadiusV = ShaderString.nextVariableName("primaryRadius");
        String secondaryRadiusV =ShaderString.nextVariableName("secondaryRadius");
        String comment = ShaderString.nextVariableName("\r\n\t//Torus");
        
        String d = comment;
        d+="\r\n\tfloat "+primaryRadiusV+"="+this.primaryRadius+";";
        d+="\r\n\tfloat "+secondaryRadiusV+"="+this.secondaryRadius+";";
        String v = "length(vec2(length(<parm>.xz)-"+primaryRadiusV+",<parm>.y))-"+secondaryRadiusV;
        v=v.replace("<parm>", parmValue);
        return new ShaderString(d,v);
    }
    
}
