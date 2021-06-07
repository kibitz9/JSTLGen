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
public class TestShader extends SoftwareShader {

    private int offset =0;
    @Override
    public ShaderColor GetPixel(int x, int y) {
        offset++;
        return new ShaderColor(255,(x+offset/100000)&0xff,(y+offset/300000)&0xff,0);
    }

    
    public TestShader(javax.swing.JPanel target, int softwareThreads){
        super(target,softwareThreads);
    }
    
    
}
