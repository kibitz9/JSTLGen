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
public class SDF3dPrimitiveBox extends SignedDistanceField3d{
     private Vector3d box;
        public SDF3dPrimitiveBox(Vector3d positiveHalfDiagonal)
        {
            this.box = positiveHalfDiagonal;
        }
        public SDF3dPrimitiveBox(double radiusx, double radiusy, double radiusz)
        {
            this.box = new Vector3d(radiusx, radiusy, radiusz);
        }
        @Override
        public double GetRawDistance(Vector3d p)
        {
            return getDist(p, box);
        }
//        private static double getDistx(Vector3d p, Vector3d box)
//        {
//            Vector3d q = p.Abs().Subtract(box);
//            double temp2 = Math.max(q.GetMagnitude(), 0);
//            temp2 += Math.min(Math.max(q.x, Math.max(q.y, q.z)), 0.0);
//            return temp2;
//        }

        private static double getDist(Vector3d p, Vector3d box)
        {
            
            Vector3d q = abs(p).Subtract(box);
            Vector3d r = max(q,0);
            double o = length(r);//outside distance

            double i = min(max(q),0);//inside distance
            return o + i;
        }
        @Override
        public SignedDistanceField3d Clone()
        {
            return new SDF3dPrimitiveBox(this.box);
        }
        
        @Override
        public ShaderString toShaderString(String parmValue){
            String varName = ShaderString.nextVariableName("box");
            String varName2 = ShaderString.nextVariableName("q");
            String comment = ShaderString.nextVariableName("\r\n\t//Box");
            String d = comment;
            d+="\r\n\tvec3 "+varName+"="+box.toShaderString()+";";
            d+="\r\n\tvec3 "+varName2+"=abs(<parm>)-"+varName+";";
            
            
            String c = "length(max("+varName2+",0.0))+min(max("+varName2+".x,max("+varName2+".y,"+varName2+".z)),0.0)";
            
            d=d.replace("<parm>", parmValue);
            return new ShaderString(d,c);
        }

        //float sdBox(vec3 p, vec3 b)
        //{
        //    vec3 q = abs(p) - b;
        //    return length(max(q, 0.0)) + min(max(q.x, max(q.y, q.z)), 0.0);
        //}
}
