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
public class SDF3dPrimitiveInfiniteSpokesZAxis extends SignedDistanceField3d {

    
    private double radius;
    private double spokeWidth;
    private double spokeCount;
    public SDF3dPrimitiveInfiniteSpokesZAxis(
        double radius,
        double spokeWidth,
        double spokeCount){
        this.radius=radius;
        this.spokeWidth=spokeWidth;
        this.spokeCount=spokeCount;
        
    }
    
    @Override
    public double GetRawDistance(Vector3d p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDF3dPrimitiveInfiniteSpokesZAxis(radius,spokeWidth,spokeCount);
    }
    
    @Override
    public ShaderString toShaderString(String parm){
        String sectorSizeS = ShaderString.nextVariableName("sectorSize");
        String spokeWidthS = ShaderString.nextVariableName("spokewidth");
        String sectorNumberS = ShaderString.nextVariableName("sectorNumber");
        String angleOffsetS = ShaderString.nextVariableName("angleOffset");
        String q = ShaderString.nextVariableName("q");
        String cos = ShaderString.nextVariableName("cos");
        String sin = ShaderString.nextVariableName("sin");
        //String dist = ShaderString.nextVariableName("dist");
        String absq = ShaderString.nextVariableName("absq");
        String radiusS = ShaderString.nextVariableName("radius");
        String bx1 = ShaderString.nextVariableName("bx1");
        String qb = ShaderString.nextVariableName("qb");
        
        String d="\r\n\tfloat "+sectorSizeS+"="+(3.1415926536*2)/spokeCount+";";
        d+="\r\n\tfloat "+sectorNumberS+"=round(atan(<parm>.y,<parm>.x)/"+sectorSizeS+");";
        d+="\r\n\tfloat "+angleOffsetS+"=-"+sectorNumberS+"*"+sectorSizeS+";";
       
        d+="\r\n\tfloat "+cos+"=cos("+angleOffsetS+");";
        d+="\r\n\tfloat "+sin+"=sin("+angleOffsetS+");";
        
        d+="\r\n\tfloat "+radiusS+"="+radius+";"; 
        d+="\r\n\tfloat "+spokeWidthS+"="+spokeWidth+";";
        d+="\r\n\tvec2 "+q+"=vec2(";
        d+="\r\n\t\t<parm>.x*"+cos+"-<parm>.y*"+sin+",";
        d+="\r\n\t\t<parm>.y*"+cos+"+<parm>.x*"+sin+"";
        //d+="\r\n\t\t<parm>.z";
        d+="\r\n\t);";
        d+="\r\n\tvec2 "+qb+"="+q+"-vec2("+radiusS+",0.0);";
        d+="\r\n\tvec2 "+absq+"=abs("+qb+")-vec2("+radiusS+","+spokeWidthS+");";
        d+="\r\n\tfloat "+bx1+"=min(max("+absq+".x,"+absq+".y),0.0)+length(max("+absq+",0.0));";
        String c=bx1;
        d=d.replace("<parm>", parm);
        return new ShaderString(d,c);
        
        
        
        
    }
    
    
}
