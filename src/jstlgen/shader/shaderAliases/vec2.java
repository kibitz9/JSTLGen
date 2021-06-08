/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen.shader.shaderAliases;

import jstlgen.Vector2d;

/**
 *
 * @author Christopher.Miller
 */
public class vec2 extends jstlgen.Vector2d{

    public vec2(double x, double y) {
        super(x, y);
    }

    public vec2(Vector2d toCopy) {
        super(toCopy);
    }
    
}
