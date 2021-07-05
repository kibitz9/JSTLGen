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
public class SDF3dPrimitiveCone extends SignedDistanceField3d {
    double angleInRadians;
    double height;
    double sin;
    double cos;
    //Vector2d sincos;
    public SDF3dPrimitiveCone(double angleInRadians, double height){
        this.angleInRadians = angleInRadians;
        this.height = height;
        this.sin = sin(angleInRadians);
        this.cos = cos(angleInRadians);
        //this.sincos = new Vector2d(sin, cos);
    }
    @Override
    public double GetRawDistance(Vector3d p){
        //Vector2d q = new Vector2d(sin/cos,-1.0).Scale(height);
        Vector2d q = new Vector2d(sin,cos).Scale(1/cos).Scale(height);
        Vector2d w = new Vector2d(p.GetXZ().GetMagnitude(),p.y);
        double dotwq = w.DotProduct(q);
        double dotqq = q.GetMagnitudeSquared();//same as dot(q,q);
        double dd = dotwq/dotqq;
        
        Vector2d a = w.Subtract(q.Scale(clamp(dd,0.0,1.0)));
        double temp = clamp(w.x/q.x,0.0,1.0);
        Vector2d tempV = new Vector2d(temp,1.0);
        Vector2d b = w.Subtract(q.ComponentwiseMultiply(tempV));
        double k = sign(q.y);
        double d = min(a.GetMagnitudeSquared(),b.GetMagnitudeSquared());
        //double s = max(k*(w.x*q.y-w.y*q.x),k*(w.y-q.y));
        double s = max( k*(w.x*q.y-w.y*q.x),k*(w.y-q.y)  );
        return sqrt(d)*sign(s);
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDF3dPrimitiveCone(angleInRadians, height);
    }
    
    @Override
    public ShaderString toShaderString(String parm){
        String h = ShaderString.nextVariableName("h");
        String sc =ShaderString.nextVariableName("sc");
        String q = ShaderString.nextVariableName("q");
        String w = ShaderString.nextVariableName("w");
        String dd = ShaderString.nextVariableName("dd");
        String a = ShaderString.nextVariableName("a");
        String b = ShaderString.nextVariableName("b");
        String k = ShaderString.nextVariableName("k");
        String d = ShaderString.nextVariableName("d");
        String s = ShaderString.nextVariableName("s");
        
        String comment = ShaderString.nextVariableName("\r\n\t//Cone");
        String ddd = comment;
        
        ddd += "\r\n\tfloat "+h+"="+height+";";
        ddd +="\r\n\tfloat "+sc+"="+sin/cos+";";
        ddd += "\r\n\tvec2 "+q+"="+h+"*vec2("+sc+",-1.0);";//)*1.0/"+cos+";";
        ddd+="\r\n\tvec2 "+w+"=vec2(length(<parm>.xz),<parm>.y);";
        ddd+="\r\n\tfloat "+dd+"=dot("+w+","+q+")/dot("+q+","+q+");";
        ddd+="\r\n\tvec2 "+a+"="+w+"-"+q+"*clamp("+dd+",0.0,1.0);";
        ddd+="\r\n\tvec2 "+b+"="+w+"-"+q+"*vec2(clamp("+w+".x/"+q+".x,0.0,1.0),1.0);";     
        ddd+="\r\n\tfloat "+k+"=sign("+q+".y);";
        ddd+="\r\n\tfloat "+d+"=min(dot("+a+","+a+"),dot("+b+","+b+"));";
        ddd+="\r\n\tfloat "+s+"=max("+k+"*("+w+".x*"+q+".y-"+w+".y*"+q+".x),"+k+"*("+w+".y-"+q+".y));";
        String c = "sqrt("+d+")*sign("+s+")";
        ddd=ddd.replace("<parm>", parm);
        
        String color = "vec3(1,1,1)";
        return new ShaderString(ddd,c,"",color);
    }
    
}

//Cone - exact
//
//float sdCone( in vec3 p, in vec2 c, float h )
//{
//  // c is the sin/cos of the angle, h is height
//  // Alternatively pass q instead of (c,h),
//  // which is the point at the base in 2D
//  vec2 q = h*vec2(c.x/c.y,-1.0);
//    
//  vec2 w = vec2( length(p.xz), p.y );
//  vec2 a = w - q*clamp( dot(w,q)/dot(q,q), 0.0, 1.0 );
//  vec2 b = w - q*vec2( clamp( w.x/q.x, 0.0, 1.0 ), 1.0 );
//  float k = sign( q.y );
//  float d = min(dot( a, a ),dot(b, b));
//  float s = max( k*(w.x*q.y-w.y*q.x),k*(w.y-q.y)  );
//  return sqrt(d)*sign(s);
//}