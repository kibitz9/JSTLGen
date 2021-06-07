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
    public int alpha;
    public int red;
    public int green;
    public int blue;
    private int ff = 255;
    private double colorScale = 255;
    public ShaderColor(int alpha, int red, int green, int blue){
        this.red=red;
        this.green=green;
        this.blue=blue;
        this.alpha=alpha;
    }
    public int ToInt(){
        int color = (alpha<<24)&0xff000000;
        color +=(red<<16)&0x00ff0000;
        color +=(green<<8)&0x0000ff00;
        color +=(blue)&0x000000ff;
        return color;
    }
}
