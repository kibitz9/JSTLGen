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
public class SDFOperationBoundingBox extends SignedDistanceField3d{

    SignedDistanceField3d toContain;
    Line box;
    public SDFOperationBoundingBox (SignedDistanceField3d toContain){
        this.toContain = toContain;
        box = toContain.GetBoundingBox(1.);
        
    }
    public SDFOperationBoundingBox(SignedDistanceField3d toContain, Line box){
        this.toContain=toContain;
        this.box = box;
    }
    public SDFOperationBoundingBox(SignedDistanceField3d toContain, double bRadius){
        this.toContain=toContain;
        this.box = new Line(new Vector3d(-bRadius,-bRadius,-bRadius),new Vector3d(bRadius,bRadius,bRadius));
        
        
    }
    
    @Override
    public double GetRawDistance(Vector3d p) {
        Vector3d q = new Vector3d(p);
        double maxDist = 0;
        boolean bound = false;
        if (q.x<box.start.x){
            maxDist = box.start.x-q.x;
            bound = true;
        }
        if (q.y<box.start.y){
            maxDist = max(maxDist,box.start.y-q.y);
            bound = true;
        }
        if (q.z<box.start.z){
            maxDist = max(maxDist,box.start.z-q.z);
            bound = true;
        }
        
        if (q.z>box.end.z){
            maxDist = max(maxDist,q.z-box.end.z);
            bound = true;
        }
        if (q.y>box.end.y){
            maxDist = max(maxDist,q.y-box.end.y);
            bound = true;
        }
        if (q.z>box.end.z){
            maxDist = max(maxDist,q.x-box.end.x);
            bound = true;
        }
        if (bound){
            return maxDist;
        }
        return toContain.GetDistance(p);
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDFOperationBoundingBox(toContain,box); 
    }
    

    
    @Override
    public ShaderString toShaderString(String parm){
        
        String bxs = ShaderString.nextVariableName("bxs");
        String bxe = ShaderString.nextVariableName("bxe");
        String q = ShaderString.nextVariableName("q");
        String bnd = ShaderString.nextVariableName("bound");
        String dst = ShaderString.nextVariableName("dst");
        
        String d="\r\n\tvec3 "+bxs+"=vec3("+box.start.x+","+box.start.y+","+box.start.z+");";
        d+="\r\n\tvec3 "+bxe+"=vec3("+box.end.x+","+box.end.y+","+box.end.z+");";
        d+="\r\n\tvec3 "+q+"=clamp(<parm>,"+bxs+","+bxe+");";
        d+="\r\n\tbool "+bnd+"=<parm>!="+q+";";
        d+="\r\n\tfloat "+dst+";";
        d+="\r\n\tif ("+bnd+"){";
        d+="\r\n\t\t"+dst+"=max_ext(<parm>-"+bxs+");";
        d+="\r\n\t\t"+dst+"=max_ext("+dst+","+bxe+"-<parm>);";
        d+="\r\n\t}";
        d=d.replace("<parm>",parm);
        d+="\r\n\telse{";
        ShaderString sss = toContain.toShaderString(parm);
        d+=sss.defines.replace("\r\n\t","\r\n\t\t");
        d+="\r\n\t\t"+dst+"="+sss.code+";";
        d+="\r\n\t}";
        
        String c = dst;
        
        
        String myFunction = "\r\nfloat max_ext(vec3 a){";
        myFunction+="\r\n\treturn max(a.x,max(a.y,a.z));";
        myFunction+="\r\n}";
        myFunction+= "\r\nfloat max_ext(float a, vec3 b){";
        myFunction+="\r\n\treturn max(a,max_ext(b));";
        myFunction+="\r\n}";
        
        ShaderString.AddGlobalFunction("max_ext", myFunction);
        
       
        
        
        return new ShaderString(d,c,sss.constantsAndFunctions+"\r\n");
        
    }
    
}
