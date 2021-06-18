/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shadergen;

/**
 *
 * @author cmiller
 */
public abstract class ShaderComponent {
    public abstract jstlgen.ShaderString toShaderString(String parm);
}
