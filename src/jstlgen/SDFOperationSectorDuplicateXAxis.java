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
public class SDFOperationSectorDuplicateXAxis extends SignedDistanceField3d {

    
    //private double radius;
    //private double spokeWidth;
    private double sectorCount;
    private SignedDistanceField3d toRotate;
    private double sectorSize;
    public SDFOperationSectorDuplicateXAxis( SignedDistanceField3d toRotate, double sectorCount){
        this.sectorCount=sectorCount;
        this.toRotate = toRotate;
        this.sectorSize = (3.1415926536*2)/sectorCount;
    }
    
    @Override
    public double GetRawDistance(Vector3d p) {
        double sectorNumber = Math.round(Math.atan2(p.z,p.y)/sectorSize);
        double angleOffset =-sectorNumber*sectorSize;
        double cos1 = Math.cos(angleOffset);
        double sin1 = Math.sin(angleOffset);
        Vector3d q = new Vector3d(p.x, p.y*cos1-p.z*sin1,p.z*cos1+p.y*sin1);
        return toRotate.GetDistance(q);
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDFOperationSectorDuplicateXAxis(  toRotate, sectorCount);
    }
    
    @Override
    public ShaderString toShaderString(String parm){
        String sectorSizeS = ShaderString.nextVariableName("SECTORSIZE");
        //String spokeWidthS = ShaderString.nextVariableName("spokewidth");
        String sectorNumberS = ShaderString.nextVariableName("sectorNumber");
        String angleOffsetS = ShaderString.nextVariableName("angleOffset");
        String q = ShaderString.nextVariableName("q");
        String cos = ShaderString.nextVariableName("cos");
        String sin = ShaderString.nextVariableName("sin");
        //String dist = ShaderString.nextVariableName("dist");
        //String absq = ShaderString.nextVariableName("absq");
        //String radiusS = ShaderString.nextVariableName("radius");
        //String bx1 = ShaderString.nextVariableName("bx1");
       // String qb = ShaderString.nextVariableName("qb");
        String f = "";
        f = "\r\nconst float "+sectorSizeS+"="+(3.1415926536*2)/sectorCount+";";
        String d="";
        //d+="\r\n\tfloat "+sectorNumberS+"=round(atan(<parm>.z,<parm>.y)/"+sectorSizeS+");";
        d+="\r\n\tfloat "+sectorNumberS+"=round(atan(<parm>.z,<parm>.y)/"+sectorSizeS+");";
        d+="\r\n\tfloat "+angleOffsetS+"=-"+sectorNumberS+"*"+sectorSizeS+";";
       
        d+="\r\n\tfloat "+cos+"=cos("+angleOffsetS+");";
        d+="\r\n\tfloat "+sin+"=sin("+angleOffsetS+");";
        
        //d+="\r\n\tfloat "+radiusS+"="+radius+";"; 
        //d+="\r\n\tfloat "+spokeWidthS+"="+spokeWidth+";";
        d+="\r\n\tvec3 "+q+"=vec3(";
        d+="\r\n\t<parm>.x,";
        d+="\r\n\t\t<parm>.y*"+cos+"-<parm>.z*"+sin+",";
        d+="\r\n\t\t<parm>.z*"+cos+"+<parm>.y*"+sin;
      
        //d+="\r\n\t\t<parm>.z";
        d+="\r\n\t);";
        //d+="\r\n\tvec2 "+qb+"="+q+"-vec2("+radiusS+",0.0);";
        //d+="\r\n\tvec2 "+absq+"=abs("+qb+")-vec2("+radiusS+","+spokeWidthS+");";
        //d+="\r\n\tfloat "+bx1+"=min(max("+absq+".x,"+absq+".y),0.0)+length(max("+absq+",0.0));";
        //String c=bx1;
        d=d.replace("<parm>",parm);
        ShaderString tt = toRotate.toShaderString(q);
        
        d+=tt.defines;
        String c = tt.code;
        //String color = tt.color;
        
        
        String functions = "";
            String color;
            String fn = ShaderString.nextVariableName("sector_color");
            
            
            
            functions +="\r\nvec3 "+fn+"(vec3 q){"; 
            
           
            functions+="\r\n\tfloat "+sectorNumberS+"=round(atan(q.z,q.y)/"+sectorSizeS+");";
            functions+="\r\n\tfloat "+angleOffsetS+"=-"+sectorNumberS+"*"+sectorSizeS+";";
            functions+="\r\n\tfloat "+cos+"=cos("+angleOffsetS+");";
            functions+="\r\n\tfloat "+sin+"=sin("+angleOffsetS+");";
        
        
            functions+="\r\n\tvec3 p=vec3(";
            functions+="\r\n\tq.x,";
            functions+="\r\n\t\tq.y*"+cos+"-q.z*"+sin+",";
            functions+="\r\n\t\tq.z*"+cos+"+q.y*"+sin;
            functions+="\r\n\t);";    
            functions +="\r\n\treturn "+tt.color+";";
            functions +="\r\n}";
            color = fn+"(p)";   
        
        return new ShaderString(d,c,f+tt.constantsAndFunctions+functions,color);
        
        
        
        
    }
    
    
}
