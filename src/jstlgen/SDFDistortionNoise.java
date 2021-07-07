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


public class SDFDistortionNoise extends SignedDistanceField3d {

    SignedDistanceField3d toDistort;
    double mult;
    double noiseScale;
    public SDFDistortionNoise(SignedDistanceField3d toDistort, double mult, double noiseScale){
        this.toDistort = toDistort;
        this.mult=mult;
        this.noiseScale=noiseScale;
    }
    
    @Override
    public double GetRawDistance(Vector3d p) {
        double d = toDistort.GetDistance(p);
        double strength = clamp(mult/d,0.,1.);
        double noise = noise(p.Scale(noiseScale));
        return d+mix(mult,noise,strength);
    }

    @Override
    public SignedDistanceField3d Clone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    double SimpleNoise2(Vector3d p){

        return fract(sin(p.x*101.+p.y*107.+p.z*103.+cos(p.x+p.y+p.z))*1000.);
    }

    double simpleNoise1(Vector3d p){

       Vector3d q=floor(p);
       Vector2d r=new Vector2d(0.0,1.);

       double a = SimpleNoise2(q.Add(r.GetXXX()));
       double b = SimpleNoise2(q.Add(r.GetYXX()));
       double c = SimpleNoise2(q.Add(r.GetXYX()));
       double d = SimpleNoise2(q.Add(r.GetYYX()));

       double e = SimpleNoise2(q.Add(r.GetXXY()));
       double f = SimpleNoise2(q.Add(r.GetYXY()));
       double g = SimpleNoise2(q.Add(r.GetXYY()));
       double h = SimpleNoise2(q.Add(r.GetYYY()));



       double s = mix(a,b,fract(p.x));
       double t = mix(c,d,fract(p.x));
       double u = mix(s,t,fract(p.y));

       double v = mix(e,f,fract(p.x));
       double w = mix(g,h,fract(p.x));
       double x = mix(v,w,fract(p.y));


       double z = mix(u,x,fract(p.z));


       return z;
    }

    double noise(Vector3d p){
        Vector3d q=new Vector3d(p);
        double temp = 0.;
        temp += simpleNoise1(q)/2.;
        q=q.Scale(2);

        temp +=simpleNoise1(q)/4.;
        q=q.Scale(2);

        temp +=simpleNoise1(q)/8.;
        q=q.Scale(2);

        temp +=simpleNoise1(q)/16.;
        q=q.Scale(2);
        temp +=simpleNoise1(q)/32.;
        q=q.Scale(2);
        temp +=simpleNoise1(q)/64.;
        return temp;
    }

    @Override
    public ShaderString toShaderString(String parmValue){
       
        String fn = "\r\nfloat simpleNoise2(vec3 p){";
        fn+="\r\n\treturn fract(sin(p.x*101.+p.y*107.+p.z*103.+cos(p.x+p.y+p.z))*1000.);";
        fn+="}";

        
        ShaderString.AddGlobalFunction("simpleNoise2", fn);
        
        fn="\r\nfloat simpleNoise1(vec3 p){\n" +

        "\r\n\tvec3 q=floor(p);" +
        "\r\n\tvec2 r=vec2(0.0,1.);" +

        "\r\n\tfloat a = simpleNoise2(q+r.xxx);" +
        "\r\n\tfloat b = simpleNoise2(q+r.yxx);" +
        "\r\n\tfloat c = simpleNoise2(q+r.xyx);" +
        "\r\n\tfloat d = simpleNoise2(q+r.yyx);" +
        "\r\n\tfloat e = simpleNoise2(q+r.xxy);" +
        "\r\n\tfloat f = simpleNoise2(q+r.yxy);" +
        "\r\n\tfloat g = simpleNoise2(q+r.xyy);" +
        "\r\n\tfloat h = simpleNoise2(q+r.yyy);" +
        "\r\n\tfloat s = mix(a,b,fract(p.x));" +
        "\r\n\tfloat t = mix(c,d,fract(p.x));" +
        "\r\n\tfloat u = mix(s,t,fract(p.y));" +
        "\r\n\tfloat v = mix(e,f,fract(p.x));" +
        "\r\n\tfloat w = mix(g,h,fract(p.x));" +
        "\r\n\tfloat x = mix(v,w,fract(p.y));" +
        "\r\n\tfloat z = mix(u,x,fract(p.z));" +
      
        "\r\n\treturn z;" +
        "\r\n}";

        ShaderString.AddGlobalFunction("simpleNoise1", fn);
        
        fn = "\r\nfloat simpleNoise(vec3 p){" +
        "\r\n\tvec3 q=p;" +
        "\r\n\tfloat temp = 0.;" +
        "\r\n\ttemp += simpleNoise1(q)/2.;" +
        "\r\n\tq*=2.;" +

        "\r\n\ttemp +=simpleNoise1(q)/4.;" +
        "\r\n\tq*=2.;\n" +

        "\r\n\ttemp +=simpleNoise1(q)/8.;" +
        "\r\n\tq*=2.;" +

        "\r\n\ttemp +=simpleNoise1(q)/16.;" +
        "\r\n\tq*=2.;" +
        "\r\n\ttemp +=simpleNoise1(q)/32.;" +
        "\r\n\tq*=2.;" +
        "\r\n\ttemp +=simpleNoise1(q)/64.;" +
        "\r\n\treturn temp;" +
        "}";
        
        ShaderString.AddGlobalFunction("simpleNoise", fn);
        
        String vMult = ShaderString.nextVariableName("mult");
        String vStrength = ShaderString.nextVariableName("strength");
        String vNoise = ShaderString.nextVariableName("noise");
        String vNoiseScale = ShaderString.nextVariableName("noisescale");
        String vDist = ShaderString.nextVariableName("dist");
                
     
       
        
        ShaderString sd = toDistort.toShaderString(parmValue);
        
        String d = "\r\n\tfloat "+vMult+"="+mult+";";
        d+="\r\n\tfloat "+vNoiseScale+"="+noiseScale+";";
        d+="\r\n\tfloat "+vDist+"="+sd.code+";";
        d+="\r\n\tfloat "+vStrength+"=clamp("+vMult+"/"+vDist+",0.,1.);";
        d+="\r\n\tfloat "+vNoise+"=simpleNoise(<parm>*"+vNoiseScale+");";
        String c = "mix("+vDist+"-"+vMult+",("+vDist+"-("+vMult+"/2.)+"+vNoise+"),"+vStrength+")";

        d=d.replace("<parm>",parmValue);
        String color = sd.color;   
        return new ShaderString(sd.defines+d,c,sd.constantsAndFunctions,color);
    
    }
    
    
   
}
