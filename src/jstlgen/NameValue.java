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
public class NameValue {
    private String name;
    private String value;
    
    public NameValue(String name, String value){
        this.name=name;
        this.value=value;
        
    }
    public String getName(){
        return name;
    }
    public String getValue(){
        return value;
    }
    public void setValue(String value){
        this.value = value;
    }
    public void setValue(int value){
        this.value = String.valueOf(value);
    }
    public int getIntValue(){
        return Integer.parseInt(value);
    }
    
}
