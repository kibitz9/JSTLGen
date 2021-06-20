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
public class SDFOperationRepeatY extends SDFOperationRepeat{

    @Override
    public double GetRawDistance(Vector3d p) {
        double py = p.y;
        boolean dontrepeatleft=false;
        boolean dontrepeatright = false; ;
        if (py <= boundrymin)
        {
            return toRepeat.GetDistance(new Vector3d(p.x, py + outsideMoveBefore, p.z));
        }
        else if (py >= boundrymax)
        {
            return toRepeat.GetDistance(new Vector3d(p.x, py+ outsideMoveAfter, p.z));
        }
        else if (py < boundrymin + width)
        {
            dontrepeatleft = true;
        }
        if (py > boundrymax - width)
        {
            dontrepeatright = true;
        }

        double py2 =GetScaledFrac(py, width);

        double dist1 = toRepeat.GetDistance(new Vector3d(p.x, py2, p.z)); 
        if (!calcNeighbors){
            return dist1;
        }
        
        double dist2 = dontrepeatleft ? Double.MAX_VALUE : toRepeat.GetDistance(new Vector3d(p.x,py2 + width,  p.z));
        double dist3 = dontrepeatright ? Double.MAX_VALUE : toRepeat.GetDistance(new Vector3d(p.x, py2 - width,  p.z));
        // return dist3;
        return min(dist1, min(dist2, dist3));
    }
    
    public SDFOperationRepeatY(SignedDistanceField3d toRepeat, double repeatsBefore, double repeatsAfter, double width, boolean calcNeighbors){
        super(toRepeat,repeatsBefore,repeatsAfter,width,calcNeighbors);
    }
    @Override
    public SignedDistanceField3d Clone() {
        return new SDFOperationRepeatY(toRepeat,repeatsBefore,repeatsAfter,width, calcNeighbors);
    }
    
   
    @Override
    public ShaderString toShaderString(String parm){
        String pV = ShaderString.nextVariableName("py");
        String boundryMin = ShaderString.nextVariableName("boundrymin");
        String boundryMax = ShaderString.nextVariableName("boundrymax");
        
        //String dontrepeatleftV = ShaderString.nextVariableName("dontrepeatleft");
        //String dontrepeatrightV = ShaderString.nextVariableName("dontrepeatright");
        String widthV = ShaderString.nextVariableName("width");
        
        String calcbefore = ShaderString.nextVariableName("calcbefore");
        String calcafter = ShaderString.nextVariableName("calcafter");
        String calcinside = ShaderString.nextVariableName("calcinside");
        String calcoutside = ShaderString.nextVariableName("calcoutside");
        
        String calcleftrep = ShaderString.nextVariableName("calcleftrep");
        String calcrightrep = ShaderString.nextVariableName("calcrightrep");
        String pV2 = ShaderString.nextVariableName("py2");
        String fn1 = ShaderString.nextVariableName("df");
        
        String outsidemovebefore = ShaderString.nextVariableName("outsidemovebefore");
        String outsidemoveafter = ShaderString.nextVariableName("outsidemoveafter");
        
        //String outside = ShaderString.nextVariableName("outside");
        String result = ShaderString.nextVariableName("result");
        
        String distA = ShaderString.nextVariableName("distA");
        String distB = ShaderString.nextVariableName("distB");
        String distC = ShaderString.nextVariableName("distC");
        
        String function1 = "float "+fn1+"(in vec3 p){\r\n";
        ShaderString ss = toRepeat.toShaderString("p");
        function1+=ss.defines+"\r\n";
        function1+="\r\n\treturn "+ss.code+";";
        function1+="\r\n}";
        
        String f = function1;
        
     
        
        String d="\r\n\tfloat "+boundryMin+"="+boundrymin+";";
        d+="\r\n\tfloat "+boundryMax+"="+boundrymax+";";
        d+="\r\n\tfloat "+widthV+"="+width+";";
        d+="\r\n\tfloat "+pV+"=<parm>.y;";
        
        d+="\r\n\tfloat "+outsidemovebefore+"="+outsideMoveBefore+";";
        d+="\r\n\tfloat "+outsidemoveafter+"="+outsideMoveAfter+";";
        
        d+="\r\n\tfloat "+calcbefore+"=1.0-max(sign("+pV+"-"+boundryMin+"),0.0);";//px<=boundrymin
        d+="\r\n\tfloat "+calcafter+"=1.0-max(sign("+boundryMax+"-"+pV+"),0.0);";//px>=boundrymax
        d+="\r\n\tfloat "+calcoutside+"="+calcbefore+"+"+calcafter+";";
        d+="\r\n\tfloat "+calcinside+"=1.0-"+calcoutside+";";
  
            
        d+="\r\n\tfloat "+calcleftrep+"=max(sign(("+boundryMin+"+"+widthV+")-"+pV+"),0.0);";  //px < boundrymin + width
        d+="\r\n\tfloat "+calcrightrep+"=max(sign("+pV+"-("+boundryMax+"-"+widthV+")),0.0);";  //px > boundrymax - widt
        
        //d+="\r\n\tfloat "+outside+"="+calcoutside+"==0.0?0.0:"+fn1+"(vec3(<parm>.x,<parm>.y,"+pzV+"+"+outsidemovebefore+"*"+calcbefore+"+"+outsidemoveafter+"*"+calcafter+"));";
        
        d+="\r\n\tfloat "+pV2+"="+calcoutside+"*"+pV+"+"+calcinside+"*("+pV+"-round("+pV+"/"+widthV+")*"+widthV+");";
        
        if (!calcNeighbors){
            d+="\r\n\tfloat "+result+"="+fn1+"(vec3(<parm>.x,"+pV2+"+"+outsidemovebefore+"*"+calcbefore+"+"+outsidemoveafter+"*"+calcafter+",<parm>.z));";
        }
        else{
            d+="\r\n\tfloat "+distA+"="+fn1+"(vec3(<parm>.x,"+pV2+"+"+outsidemovebefore+"*"+calcbefore+"+"+outsidemoveafter+"*"+calcafter+",<parm>.z));";
            d+="\r\n\tfloat "+distB+"="+calcleftrep+"*999999.0+"+fn1+"(vec3(<parm>.x,"+pV2+"+"+widthV+",<parm>.z));";
            d+="\r\n\tfloat "+distC+"="+calcrightrep+"*999999.0+"+fn1+"(vec3(<parm>.x,"+pV2+"-"+widthV+",<parm>.z));";

            d+="\r\n\tfloat "+result+"=min("+distA+",min("+distB+","+distC+"));";
        }
       
        
        String c = result;
        
        
       
       
        //d = d+ distA1.defines+distB1.defines+distC1.defines;
        d=d.replace("<parm>", parm);
        return new ShaderString(d,c,ss.functions+"\r\n"+f);
//       double dist1 = toRepeat.GetDistance(new Vector3d(px2, p.y, p.z)); 
//            double dist2 = dontrepeatleft ? Double.MAX_VALUE : toRepeat.GetDistance(new Vector3d(px2 + width, p.y, p.z));
//            double dist3 = dontrepeatright ? Double.MAX_VALUE : toRepeat.GetDistance(new Vector3d(px2 - width, p.y, p.z));
//           // return dist3;
//            return min(dist1, min(dist2, dist3));
    }
   
}
