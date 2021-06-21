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
public class SDF3dPrimitivePlane  extends SignedDistanceField3d{

    private Vector3d angleAsSurfaceNormal;
    private double heightAtOrigin;
    public SDF3dPrimitivePlane(Vector3d angleAsSurfaceNormal, double heightAtOrigin){
        this.angleAsSurfaceNormal=angleAsSurfaceNormal.GetUnitVector();//normalize
        this.heightAtOrigin=heightAtOrigin;
    }
    
    
    @Override  
    public double GetRawDistance(Vector3d p) {
        return p.DotProduct(angleAsSurfaceNormal,p)+heightAtOrigin;
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDF3dPrimitivePlane(angleAsSurfaceNormal,heightAtOrigin);
    }
    @Override
    public ShaderString toShaderString(String parm){
        String planeCalc = ShaderString.nextVariableName("plane");
        String planeAngle= ShaderString.nextVariableName("planeAngleNorm");
        String planeHeight = ShaderString.nextVariableName("planeHeight");
        String d = "\r\n\tvec3 "+planeAngle+"=vec3("+angleAsSurfaceNormal.x+","+angleAsSurfaceNormal.y+","+angleAsSurfaceNormal.z+");";
        d+="\r\n\tfloat "+planeHeight+"="+this.heightAtOrigin+";";
        d+= "\r\n\tfloat "+planeCalc+"=dot(<parm>,"+planeAngle+")+"+planeHeight+";";
        d=d.replace("<parm>",parm);
        String c = planeCalc;
        return new ShaderString(d,c);
    }
    
}
