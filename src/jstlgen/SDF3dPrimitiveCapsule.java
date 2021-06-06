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
public class SDF3dPrimitiveCapsule extends SignedDistanceField3d {
     private double height = 0;
        private double radius = 0;
        
        public SDF3dPrimitiveCapsule(double height, double radius)
        {
            this.height = height;
            this.radius = radius;
        }
        @Override
        public double GetRawDistance(Vector3d p)
        {
            return sdVerticalCapsule(p, height, radius);   
        }
        public double sdVerticalCapsule(Vector3d p, double h, double r)
        {
            Vector3d p2 = new Vector3d(p.x, p.y - clamp(p.y, 0.0, h), p.z);
            
            return length(p2) - r;
        }
        @Override
        public SignedDistanceField3d Clone()
        {
            return new SDF3dPrimitiveCapsule(height, radius);
        }
        //float sdVerticalCapsule(vec3 p, float h, float r)
        //{
        //    p.y -= clamp(p.y, 0.0, h);
        //    return length(p) - r;
        //}

}
