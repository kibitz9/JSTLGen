/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen.shader;

/**
 *
 * @author Christopher.Miller
 */
public class ShaderColor {
    public double alpha;
    public double red;
    public double green;
    public double blue;
    //private int ff = 255;
    private double colorScale = 255;
    public ShaderColor(double alpha, double red, double green, double blue){
        this.red=red;
        this.green=green;
        this.blue=blue;
        this.alpha=alpha;
    }
    private int min(int one, int two){
        if (one<two){
            return one;
        }
        return two;
    }
    
}
