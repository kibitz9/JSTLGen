/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shadergen;

import jstlgen.ShaderString;

/**
 *
 * @author cmiller
 */
public class ShaderComponentSurfaceNormal extends ShaderComponent{

    
    
    @Override
    public ShaderString toShaderString(String parm) {
        return new ShaderString("",defaultMethod);
        //hmm.

    }
    
    String defaultMethod = "vec3 getSurfaceNormal(vec3 p, float epsilon){\n" +
"    float epsilon2 = epsilon/10.0;\n" +
"    vec3 xPlus = vec3(p.x+epsilon2,p.y,p.z);\n" +
"    vec3 xMinus = vec3(p.x-epsilon2,p.y,p.z);\n" +
"    vec3 yPlus = vec3(p.x,p.y+epsilon2,p.z);\n" +
"    vec3 yMinus = vec3(p.x,p.y-epsilon2,p.z);\n" +
"    vec3 zPlus = vec3(p.x,p.y,p.z+epsilon2);\n" +
"    vec3 zMinus = vec3(p.x,p.y,p.z-epsilon2);\n" +
"    \n" +
"    float slopex = getDist(xPlus)-getDist(xMinus);\n" +
"    float slopey = getDist(yPlus)-getDist(yMinus);\n" +
"    float slopez = getDist(zPlus)-getDist(zMinus);\n" +
"    return normalize(vec3(slopex,slopey,slopez));\n" +
"}";
    
    String tetraHedronMethod = "vec3 getSurfaceNormal( in vec3 p, float epsilon )\n" +
"{\n" +
"     // replace by an appropriate value\n" +
"    const vec2 k = vec2(1,-1);\n" +
"    return normalize( k.xyy*f( p + k.xyy*epsilon ) + \n" +
"                      k.yyx*f( p + k.yyx*epsilon ) + \n" +
"                      k.yxy*f( p + k.yxy*epsilon ) + \n" +
"                      k.xxx*f( p + k.xxx*epsilon ) );\n" +
"}";
    
}
