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
public class SDFDistortionGyroid extends SignedDistanceField3d {

    
    double scale1;
    double scale2;
    double offset1;
    double offset2;
    double inverseScale1;
    double inverseScale2;
    public SDFDistortionGyroid(double scale1, double scale2, double offset1, double offset2) {
        this.scale1=scale1;
        this.offset1=offset1;
        this.scale2=scale2;
        this.offset2=offset2;
        this.inverseScale1 = 1/scale1;
        this.inverseScale2 = 1/scale2;
    }
    @Override
    public double GetRawDistance(Vector3d p) {
        Vector3d q = sin(p).Scale(inverseScale1).Add(offset1);
        Vector3d r = cos(p.GetYZX().Scale(inverseScale2).Add(offset2));
        return q.DotProduct(r);
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDFDistortionGyroid(scale1,scale2,offset1,offset2);
    }
    
    @Override
    public ShaderString toShaderString(String parmValue){
        
        String s1 = ShaderString.nextVariableName("scaleA");
        String s2 = ShaderString.nextVariableName("scaleB");
        String o1 = ShaderString.nextVariableName("offsetA");
        String o2 = ShaderString.nextVariableName("offsetB");
        String d = "\r\n\tfloat "+s1+"="+inverseScale1+";";
        d+="\r\n\tfloat "+s2+"="+inverseScale2+";";
        d+="\r\n\tfloat "+o1+"="+offset1+";";
        d+="\r\n\tfloat "+o2+"="+offset2+";";
        
        String returnString = "dot(sin("+parmValue+".xyz*"+s1+"+"+o1+"),cos("+parmValue+".yzx*"+s2+"+"+o2+"))";
        
        String color = "vec3(1,1,1)";
        return new ShaderString(d,returnString,"",color);
    }
    
}
