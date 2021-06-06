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
public class SDFOperationTranslate extends SignedDistanceField3d{
    private Vector3d negativeTranslation;
        private SignedDistanceField3d toTranslate;
        public SDFOperationTranslate(SignedDistanceField3d toTranslate, Vector3d translation)
        {
            this.toTranslate = toTranslate;
            this.negativeTranslation = translation.Negate();//store the negative
        }
        public SDFOperationTranslate(SignedDistanceField3d toTranslate, double x, double y, double z)
        {
            this.toTranslate = toTranslate;
            this.negativeTranslation = new Vector3d(-x, -y, -z);
        }
        @Override
        public  double GetRawDistance(Vector3d translatedp)
        {
            Vector3d n = translatedp.Add(negativeTranslation);
            return toTranslate.GetDistance(n);

        }
        @Override
        public SignedDistanceField3d Clone()
        {
            return new SDFOperationTranslate(toTranslate.Clone(), negativeTranslation.Negate());
        }
}
