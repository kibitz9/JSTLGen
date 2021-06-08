/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen.shader;
import jstlgen.*;

/**
 *
 * @author Christopher.Miller
 */
public class TestShader extends SoftwareShader {

    private int offset =0;
    @Override
    public Vector4d mainImage(Vector2d fragCoord) {
        // Normalized pixel coordinates (from 0 to 1)
        Vector2d uv = fragCoord.Divide(iResolution.GetXY());
        Vector3d temp = uv.GetXYX().Add(vec3(0,2,4).Add(iTime));
        Vector3d col = cos(temp).Scale(.5).Add(.5);
        return vec4(col,1.0);
        
//      // Normalized pixel coordinates (from 0 to 1)
//    vec2 uv = fragCoord/iResolution.xy;
//
//    // Time varying pixel color
//    vec3 col = 0.5 + 0.5*cos(iTime+uv.xyx+vec3(0,2,4));
//
//    // Output to screen
//    fragColor = vec4(col,1.0);
       
    }

    
    public TestShader(javax.swing.JPanel target, int softwareThreads){
        super(target,softwareThreads,1);
    }
    
    
}
