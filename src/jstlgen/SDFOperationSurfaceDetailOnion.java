


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
public class SDFOperationSurfaceDetailOnion extends SignedDistanceField3d {

    SignedDistanceField3d surface;
    SignedDistanceField3d detail;
    SignedDistanceField3d onioned;
    SignedDistanceField3d intersection;
    SignedDistanceField3d finalUnion;
    double thickness;
    public SDFOperationSurfaceDetailOnion (SignedDistanceField3d surface, SignedDistanceField3d detail, double thickness){
        this.surface=surface;
        this.detail=detail;
        this.thickness=thickness;//the amount to shrink the surface to expose the new surface detail.
        this.onioned = new SDFOperationOnion(surface,thickness);
        this.intersection = new SDFOperationCSGIntersection(onioned,detail);
        this.finalUnion = new SDFOperationCSGUnion(this.surface,this.intersection);
    }
    
    @Override
    public double GetRawDistance(Vector3d p) {
        double outerDist = this.onioned.GetDistance(p);
        if (outerDist>0.01){
            return outerDist;
        }
        double innerDist = this.surface.GetDistance(p);
        if (innerDist<-.01){
            return innerDist;
        }
        return this.finalUnion.GetDistance(p);
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDFOperationSurfaceDetailOnion(surface, detail, thickness);
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
            f2+="\r\n\tfloat thickness="+this.thickness+";";
            f2+="\r\n\tfloat surface="+fn0Name+"(p);";
            f2+="\r\n\tfloat onioned=abs(surface)-thickness;";
            f2+="\r\n\tif (onioned>thickness){";
            f2+="\r\n\t\treturn onioned;";
            f2+="\r\n\t}";
            f2+="\r\n\telse if (surface<-thickness){";
            f2+="\r\n\t\treturn surface;";
            f2+="\r\n\t}";
            f2+="\r\n\telse{";
            f2+="\r\n\t\tfloat detail="+fn1Name+"(p);";
            f2+="\r\n\t\tfloat dist = max(onioned,detail);";
            f2+="\r\n\t\treturn min(dist,surface);";
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
