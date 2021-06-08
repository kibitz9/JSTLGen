/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen.shader.shaderAliases;

import jstlgen.Vector4d;

/**
 *
 * @author Christopher.Miller
 */
public class vec4 extends jstlgen.Vector4d{

    public vec4(double x, double y, double z, double w) {
        super(x, y, z, w);
    }

    public vec4(Vector4d toCopy) {
        super(toCopy);
    }
    
}
