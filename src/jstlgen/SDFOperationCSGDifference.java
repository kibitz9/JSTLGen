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
public class SDFOperationCSGDifference extends SignedDistanceField3d{
    private SignedDistanceField3d one;
    private SignedDistanceField3d two;
    public SDFOperationCSGDifference(SignedDistanceField3d one, SignedDistanceField3d two)
    {
        this.one = one;
        this.two = two;
    }
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {
        return max(one.GetDistance(translatedp), -two.GetDistance(translatedp));
    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationCSGDifference(one.Clone(), two.Clone());
    }
    @Override
    public ShaderString toShaderString(String parmValue){
        ShaderString ss1 = one.toShaderString(parmValue);
        ShaderString ss2 = two.toShaderString(parmValue);
        String defines = ss1.defines+ss2.defines;
        String code = "max("+ss1.code+",-("+ss2.code+"))";
        return new ShaderString(defines,code,ss1.functions+"\r\n"+ss2.functions);
    }
}
