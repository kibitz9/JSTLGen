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
public class SDFOperationRoundEdges extends SignedDistanceField3d{
    SignedDistanceField3d toRound;
    private double amount;
    public SDFOperationRoundEdges(SignedDistanceField3d toRound,double additionalDistance)
    {
        this.amount= additionalDistance;
        this.toRound = toRound;
    }
    @Override
    public double GetRawDistance(Vector3d p)
    {
        return toRound.GetDistance(p) - amount;
    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationRoundEdges(toRound.Clone(), amount);
    }
    
    @Override 
    public ShaderString toShaderString(String parmName){
        String ra = ShaderString.nextVariableName("roundAmnt");
        String d = "\r\n\tfloat "+ra+"="+this.amount+";";
        
        ShaderString xx = toRound.toShaderString(parmName);
        
        String c = "("+xx.code+")-"+ra;
        
        return new ShaderString(xx.defines+d,c,xx.functions);
    }
    
}
