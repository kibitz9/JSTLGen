/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen.shader.shaderAliases;

import jstlgen.Vector3d;

/**
 *
 * @author Christopher.Miller
 */
public class vec3 extends jstlgen.Vector3d {

    public vec3(double x, double y, double z) {
        super(x, y, z);
    }

    public vec3(Vector3d toCopy) {
        super(toCopy);
    }
    
}
