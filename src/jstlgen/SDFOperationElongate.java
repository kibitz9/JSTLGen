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
public class SDFOperationElongate extends SignedDistanceField3d{
     private SignedDistanceField3d toElongate;
        private Vector3d elongationAmountPerDimension;
        public SDFOperationElongate(SignedDistanceField3d toElongate, Vector3d elongationAmountPerDimension)
        {
            this.toElongate = toElongate;
            this.elongationAmountPerDimension = elongationAmountPerDimension;//elongationAmountPerDimension.Max(0.00000000000001);
        }
        @Override
        public double GetRawDistance(Vector3d p)
        {

            Vector3d q = p.Abs().Subtract(elongationAmountPerDimension);
            return toElongate.GetDistance(q.Max(0.0)) + min(max(q.x, max(q.y, q.z)), 0.0);

        }
        @Override
        public SignedDistanceField3d Clone()
        {
            return new SDFOperationElongate(toElongate.Clone(), elongationAmountPerDimension.Clone()) ;
        }
        //float opElongate(in sdf3d primitive, in vec3 p, in vec3 h)
        //{
        //    vec3 q = abs(p) - h;
        //    return primitive(max(q, 0.0)) + min(max(q.x, max(q.y, q.z)), 0.0);
        //}
}
