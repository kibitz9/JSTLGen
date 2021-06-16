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
public class SDF3dPrimitiveSolidAngle extends SignedDistanceField3d{
    double angleInRadians;
    double radius;
    double sin;
    double cos;
    Vector2d sincos;
    public SDF3dPrimitiveSolidAngle(double angleInRadians, double radiusOfSphericalSection)
    {
        this.angleInRadians = angleInRadians;
        this.radius = radiusOfSphericalSection;
        this.sin = sin(angleInRadians);
        this.cos = cos(angleInRadians);
        this.sincos = new Vector2d(sin, cos);
    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDF3dPrimitiveSolidAngle(angleInRadians,radius);
    }
    @Override
    public double GetRawDistance(Vector3d p)
    {
        Vector2d q = new Vector2d(p.GetXZ().GetMagnitude(), p.y);
        double l = q.GetMagnitude() - radius;
        double m = q.Subtract(sincos.Scale(clamp(q.DotProduct(sincos), 0.0, radius))).GetMagnitude();
        return max(l, m * sign(sincos.y * q.x - sincos.x * q.y));
    }
    @Override
    public ShaderString toShaderString(String parm){
        String q = ShaderString.nextVariableName("q");
        String l = ShaderString.nextVariableName("l");
        String m = ShaderString.nextVariableName("m");
        String sc = ShaderString.nextVariableName("sc");
        String rad = ShaderString.nextVariableName("rad");
        
        String d="\r\n\tvec2 "+q+"=vec2(length(<parm>.xz),<parm>.y);";
        d+="\r\n\tvec2 "+sc+"=vec2("+sincos.x+","+sincos.y+");";
        d+="\r\n\tfloat "+rad+"="+radius+";";        
        d+="\r\n\tfloat "+l+"=length("+q+")-"+rad+";";
        d+="\r\n\tfloat "+m+"=length("+q+"-("+sc+"*clamp(dot("+q+","+sc+"),0.0,"+rad+")));";
        
        String c = "max("+l+","+m+"*sign("+sc+".y*"+q+".x-"+sc+".x*"+q+".y))";
        d=d.replace("<parm>",parm);
        return new ShaderString(d,c);
        
    }
}
