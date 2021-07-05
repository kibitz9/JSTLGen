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
public class MaterialFunction {
    public Vector3d color;
    
    public MaterialFunction(Vector3d color){
        this.color=color;
    }
    public String toShaderString(){
        return color.toShaderString();
    }
    
    public String getColorString(){
        return color.toShaderString();
    }
   
}
