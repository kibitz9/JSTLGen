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
        Vector2d q = new Vector2d(sin,cos).Scale(1/cos);
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