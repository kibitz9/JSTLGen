/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen;
import java.util.*;
/**
 *
 * @author Christopher.Miller
 */
public class ShaderString {

    
   
    
    public String defines;
    public String code;
    public String constantsAndFunctions="";
    public String color;
    
    
    public static int varCounter = 0;
    
    public static List<NameValue> globals = new ArrayList<NameValue>();
    public static List<NameValue> globalFunctions = new ArrayList<NameValue>();
    
    public static List<NameValue> oneups = new ArrayList<NameValue>();
    
    
    public static String nextVariableName(String prefix){
        NameValue nv = find(oneups,prefix);
        if (nv==null){
            nv = new NameValue(prefix,"0");
            oneups.add(nv);
        }
        nv.setValue(nv.getIntValue()+1);
        return prefix+nv.getValue();
        
       
    }
    
    public static void ClearGlobals(){
        globals = new ArrayList<NameValue>();
    }
    
    public ShaderString(String defines, String code){
        this.defines=defines;
        this.code=code;
    }
    
     public ShaderString(String defines, String code, String functions){
        this.defines=defines;
        this.code=code;
        this.constantsAndFunctions=functions;
    }
     public ShaderString(String defines, String code, String functions, String color){
        this.defines=defines;
        this.code=code;
        this.constantsAndFunctions=functions;
        this.color=color;
    }
    
    
    private static NameValue find(List<NameValue> list, String name){
        for(int a=0;a<list.size();a++){
             if (list.get(a).getName().equals(name)){
                return list.get(a);
            }
        }
        return null;
    }
    
    public static void AddGlobal(String name, String value){
        boolean found = find(globals,name)!=null;
        if (!found){
            globals.add(new NameValue(name,value));
        }
    }
    public static void AddGlobalFunction(String name, String value){
        boolean found = find(globalFunctions,name)!=null;
        if (!found){
            globalFunctions.add(new NameValue(name,value));
        }
    }
    
    
    public String generateString(){
        return toString();
    }
    
     public String generateString(String functionName, String varName){
        return toString(functionName,varName);
    }
    
    public String toString(String functionName, String varName){
        String returnString = "";
        for (int a=0;a<globals.size();a++){
            NameValue temp = globals.get(a);
            returnString+="\r\n"+temp.getName()+"="+temp.getValue()+";";
        }
        
        for (int a=0;a<globalFunctions.size();a++){
            NameValue temp = globalFunctions.get(a);
            returnString+="\r\n"+temp.getValue();
        }
        
        returnString+="\r\n\t"+constantsAndFunctions+"\r\n";
        returnString+="float "+functionName+"(vec3 p){\r\n";
        returnString+=defines+"\r\n\treturn "+code+";";
        returnString+="\r\n}\r\n";
        
        returnString+="vec3 "+functionName+"_color(vec3 p){\r\n";
        returnString+="\r\n\treturn("+color+");";
        returnString+="\r\n}\r\n";
        
        //returnString+="\r\nfloat "+functionName+"_color(vec3 p){";
       //returnString+=
        
        return returnString;
    }
    
    public String toString(){
        return toString("map","p");
    }
    
}
