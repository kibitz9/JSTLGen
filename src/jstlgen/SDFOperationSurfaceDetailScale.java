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
public class SDFOperationSurfaceDetailScale extends SignedDistanceField3d {

    SignedDistanceField3d surface;
    SignedDistanceField3d detail;
    SignedDistanceField3d scaled;
    SignedDistanceField3d intersection;
    SignedDistanceField3d finalUnion;
    double scale;
    public SDFOperationSurfaceDetailScale (SignedDistanceField3d surface, SignedDistanceField3d detail, double scale){
        this.surface=surface;
        this.detail=detail;
        this.scale=scale;//the amount to shrink the surface to expose the new surface detail.
        this.scaled = new SDFOperationUniformScale(surface,scale);
        this.intersection = new SDFOperationCSGIntersection(surface,detail);
        this.finalUnion = new SDFOperationCSGUnion(scaled,this.intersection);
    }
    
    @Override
    public double GetRawDistance(Vector3d p) {
        double outerDist = this.surface.GetDistance(p);
        if (outerDist>0.01){
            return outerDist;
        }
        return this.finalUnion.GetDistance(p);
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDFOperationSurfaceDetailScale(surface, detail, scale);
    }
    
    @Override
    public ShaderString toShaderString(String parm){
        String fn0Name = ShaderString.nextVariableName("sdf");
        String fn1Name = ShaderString.nextVariableName("sdf");
        String fn2Name = ShaderString.nextVariableName("sdf");
        
        ShaderString sss0 = surface.toShaderString("p");
        ShaderString sss1 = detail.toShaderString("p");
        
        String 
            f0 ="\r\nfloat "+fn0Name+"(vec3 p){";
            f0+=sss0.defines;
            f0+="\r\n\treturn "+sss0.code+";";
            f0+="\r\n}";
        
        String 
            f1 ="\r\nfloat "+fn1Name+"(vec3 p){";
            f1+=sss1.defines;
            f1+="\r\n\treturn "+sss1.code+";";
            f1+="\r\n}";
            
            
        String
            f2 ="\r\nfloat "+fn2Name+"(vec3 p){";
            f2+="\r\n\tfloat scale="+this.scale+";";
            f2+="\r\n\tfloat temp="+fn0Name+"(p);";
            f2+="\r\n\tif (temp>.01){";
            f2+="\r\n\t\treturn temp;";
            f2+="\r\n\t}";
            f2+="\r\n\telse{";
            f2+="\r\n\t\tvec3 q = p/scale;";
            f2+="\r\n\t\tfloat dist2 = "+fn0Name+"(q);";
            f2+="\r\n\t\tif(dist2<0.){";
            f2+="\r\n\t\t\treturn dist2;";
            f2+="\r\n\t\t}";
            f2+="\r\n\t\telse{";
            f2+="\r\n\t\t\tfloat temp2="+fn1Name+"(p);";
            f2+="\r\n\t\t\tfloat dist = max(temp,temp2);";
            f2+="\r\n\t\t\treturn min(dist,dist2);";
            f2+="\r\n\t\t}";
            f2+="\r\n\t}";
            f2+="\r\n}";
            
        String c = fn2Name+"(<parm>)";
        c=c.replace("<parm>",parm);
        
        ShaderString xx = new ShaderString(
                ""
                ,c
                ,sss0.constantsAndFunctions+"\r\n"+sss1.constantsAndFunctions+f0+f1+f2
        );
        return xx;
        
    }
    
    
    
}
