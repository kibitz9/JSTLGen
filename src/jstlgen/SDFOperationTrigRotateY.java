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
public class SDFOperationTrigRotateY extends SDFOperationTrigRotate {
    @Override
    public double GetRawDistance(Vector3d translatedp)
    {


        double newX = translatedp.x * cosTheta + translatedp.z * sinTheta;
        double newZ = translatedp.z * cosTheta + translatedp.x * negativeSinTheta;

        //a standard rotation (not inverted) would look like this...
        //double newY = translatedp.Y * cosTheta - translatedp.Z * sinTheta;
        //double newZ = translatedp.Z * cosTheta + translatedp.Y * sinTheta;

        Vector3d temp = new Vector3d(newX,translatedp.y, newZ);
        return toRotate.GetDistance(temp);
        //BiComplex temp = new BiComplex(translatedp);
        //temp = temp.Multiply(this.amountInverse);
        //Vector3d newVector = new Vector3d(temp);
        //return toRotate.GetDistance(newVector);
    }

    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDFOperationTrigRotateY(toRotate.Clone(), super.radians);
    }
    
    public SDFOperationTrigRotateY(SignedDistanceField3d toRotate, double radians)
    {
        super(toRotate,radians);
    }
    
    public SDFOperationTrigRotateY(SignedDistanceField3d toRotate, String rotationExpression)
    {
        super(toRotate,rotationExpression);
    }
    
     @Override
    public ShaderString toShaderString(String parm){
        String cosThetaVar = ShaderString.nextVariableName("cosTheta");
        String sinThetaVar = ShaderString.nextVariableName("sinTheta");
        String vectorVar = ShaderString.nextVariableName("rot");
        String comment = ShaderString.nextVariableName("\r\n\t//Rotate y axis");
        String defines = comment;
        defines+= "\r\n\tfloat "+cosThetaVar+"="+super.GetCosRotationExpression()+";";
        defines+= "\r\n\tfloat "+sinThetaVar+"="+super.GetSinRotationExpression()+";";
        defines+= "\r\n\tvec3 "+vectorVar+"=vec3(<parm>.x*"+cosThetaVar+"+<parm>.z*+"+sinThetaVar+",<parm>.y,<parm>.z*"+cosThetaVar+"+<parm>.x*-"+sinThetaVar+");";
        defines=defines.replace("<parm>", parm);
        ShaderString c = toRotate.toShaderString(vectorVar);
        
        return new ShaderString(defines+c.defines,c.code,c.constantsAndFunctions);
        
        
                
    }
    
}
