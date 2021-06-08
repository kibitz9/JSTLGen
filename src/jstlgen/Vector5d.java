/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen;

/**
 *
 * @author Christopher.Miller
 */
public class Vector5d {
    private Vector3d one;
    private Vector2d two;
    public Vector3d GetXYZ(){
        return one;
    }
    public Vector2d GetVW(){
        return two;
    }
    public Vector5d(Vector3d one, Vector2d two){
        this.one=one;
        this.two=two;
    }
     public Vector5d(Vector3d one, double v, double w){
        this.one=one;
        this.two=new Vector2d(v,w);
    }
}
