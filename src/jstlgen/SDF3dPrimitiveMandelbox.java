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
public class SDF3dPrimitiveMandelbox extends SignedDistanceField3d{

    int itr;
    double s;
    double r;
    double f;
    public SDF3dPrimitiveMandelbox(int itr, double s, double r, double f){
        this.itr=itr;
        this.s=s;
        this.r=r;
        this.f=f;
    }
    
    
    @Override
    public double GetRawDistance(Vector3d p) {
        
        //a bounding sphere...
        if (p.GetMagnitude()>10.01){
            return p.GetMagnitude()-10;
        }
        
        
        Vector4d q = new Vector4d(p,1.0);
        
        
        Vector4d c = new Vector4d(p,1.0);
       
        for (int a=0;a<itr;a++){
            Vector3d qxyz = q.GetXYZ();
            qxyz = (clamp(qxyz,-1,1).Scale(2).Subtract(qxyz));
            qxyz=qxyz.Scale(f);
            
            q=new Vector4d(qxyz,q.w);

            q=(q.Scale(s)).Divide(clamp(qxyz.GetMagnitudeSquared(),r,1.0));
           
            q=q.Add(c);
            
        }
        double dist = q.GetXYZ().GetMagnitude()/abs(q.w);
       
        return dist/3.;
        //return q.GetXYZ().GetMagnitude()/abs(q.w);
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDF3dPrimitiveMandelbox(itr,s,r,f);
    }
    @Override
    public ShaderString toShaderString(String parm){
        String qS = ShaderString.nextVariableName("q");
        String cS = ShaderString.nextVariableName("c");
        String sS = ShaderString.nextVariableName("S");
        String rS = ShaderString.nextVariableName("R");
        String fS = ShaderString.nextVariableName("F");
        String itrS = ShaderString.nextVariableName("ITR");
        String aS = ShaderString.nextVariableName("a");
        String comment = ShaderString.nextVariableName("\r\n//Mandelbox");
        String d = "";//comment;
        String f = comment;
        d+="\r\n\tvec4 "+qS+"=vec4(<parm>,1.0);";
        d+="\r\n\tvec4 "+cS+"=vec4(<parm>,1.0);";
        f+="\r\nconst float "+sS+"="+s+";";
        f+="\r\nconst float "+rS+"="+r+";";
        f+="\r\nconst int "+itrS+"="+itr+";";
        f+="\r\nconst float "+fS+"="+this.f+";";
        d+="\r\n\tfor (int "+aS+"=0;"+aS+"<"+itrS+";"+aS+"++){";
        d+="\r\n\t\t"+qS+".xyz="+fS+"*(clamp("+qS+".xyz,-1.0,1.0)*2.0-"+qS+".xyz);";
        d+="\r\n\t\t"+qS+" *="+sS+"/clamp(dot("+qS+".xyz,"+qS+".xyz),"+rS+",1.0);";
        d+="\r\n\t\t"+qS+" +="+cS+";";
        d+="\r\n\t}";
        d=d.replace("<parm>",parm);
        String c=".333*length("+qS+".xyz)/abs("+qS+".w)";
        String color = "vec3(1,1,1)";
        return new ShaderString(d,c,f,color);
    }
}
