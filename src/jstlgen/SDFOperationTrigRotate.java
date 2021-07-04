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
public abstract class SDFOperationTrigRotate extends SignedDistanceField3d {
    protected SignedDistanceField3d toRotate;
        protected double radians;
        protected double negativeSinTheta;
        protected double sinTheta;
        protected double cosTheta;
        //BiComplex amountInverse;//we store the inverse since we always apply the oopposite to the incoming point
        public SDFOperationTrigRotate(SignedDistanceField3d toRotate, double radians)
        {
            this.toRotate = toRotate;
            this.radians = radians;
            this.sinTheta = sin(radians);
            this.negativeSinTheta = -this.sinTheta;//store negative so we don't always have to invert
            this.cosTheta = cos(radians);
        }
        
        public SDFOperationTrigRotate(SignedDistanceField3d toRotate, String rotationExpression)
        {
            this.toRotate = toRotate;
            this.radians = 0.;
            this.sinTheta = sin(radians);
            this.negativeSinTheta = -this.sinTheta;//store negative so we don't always have to invert
            this.cosTheta = cos(radians);
            this.overrideExpression = rotationExpression;
        }

        protected String overrideExpression = null;
        public String GetCosRotationExpression(){
            if (overrideExpression==null){
                return ""+((float)(this.cosTheta));
            }
            else{
                return "cos("+overrideExpression+")";
            }
        }
        
        protected String overrideSinExpression = null;
        public String GetSinRotationExpression(){
            if (overrideExpression==null){
                return ""+((float)(this.sinTheta));
            }
            else{
                return "sin("+overrideExpression+")";
            }
        }
        
        @Override
        public abstract double GetRawDistance(Vector3d translatedp);
        //{


        //    double newY = translatedp.Y * cosTheta - translatedp.Z* sinTheta;
        //    double newZ = translatedp.Z * cosTheta + translatedp.Y * sinTheta;

        //    Vector3d temp = new Vector3d(translatedp.X, newY, newZ);
        //    return toRotate.GetDistance(temp);
        //    //BiComplex temp = new BiComplex(translatedp);
        //    //temp = temp.Multiply(this.amountInverse);
        //    //Vector3d newVector = new Vector3d(temp);
        //    //return toRotate.GetDistance(newVector);
        //}


}
