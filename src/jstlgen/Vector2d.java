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
public class Vector2d {
     public Vector2d(double x, double y)
        {
            this.x = x;
            this.y = y;
           
        }
        public double x;
        public double y;




        public Vector2d GridSnap(double gridSize)
        {
            
            double tempx = x;
            double tempy = y;
       
            tempx /= gridSize;
            tempy /= gridSize;

            tempx = Math.floor(tempx);
            tempy = Math.floor(tempy);

            return new Vector2d(tempx, tempy);

        }
       
        public Vector2d Subtract(Vector2d toSubtract)
        {
            return new Vector2d(x - toSubtract.x, y - toSubtract.y);
        }
        public Vector2d Negate()
        {
            return new Vector2d(-x, -y);
        }
        public Vector2d Add(Vector2d toAdd)
        {
            return new Vector2d(this.x + toAdd.x, this.y + toAdd.y);
            //return this.Subtract(toAdd.Negate());
        }
        public Vector2d Midpoint(Vector2d otherSide)
        {
            //return the midpoint between two vectors - the average of two vectors.
            return new Vector2d(
               (this.x + otherSide.x) / 2,
                (this.y + otherSide.y) / 2
             
            );


        }
       
        public Vector2d Divide(Vector2d other){
            return new Vector2d(this.x/other.x, this.y/other.y);
        }
      

        private Vector2d unitVector = null;
        public Vector2d GetUnitVector()
        {
           
            if (this.unitVector == null)
            {
                double m = GetMagnitude();
                if (m == 0)
                {
                    return new Vector2d(1, 0);//a kludge
                }
                unitVector= new Vector2d(this.x / m, this.y / m);
            }
            return unitVector;
            
        }
      
        public double GetMagnitudeSquared(){
           
            return x * x + y * y;

        }
        
        public double GetMagnitude() {
           
            return Math.sqrt(GetMagnitudeSquared());

        }
        public Vector2d (Vector2d toCopy)
        {
            this.x = toCopy.x;
            this.y = toCopy.y;
        }
      
        
        public Vector3d GetXYX(){
            return new Vector3d(x,y,x);
        }
       

     

        public Vector2d Abs()
        {
            if (x >= 0)
            {
                if (y >= 0)
                {
                    
                    return this;
                    
                }
               
                return new Vector2d(x, -y);
            }
            if (y >= 0)
            {
                
                return new Vector2d(-x,y);
               
            }
            
            return new Vector2d(-x, -y);
           
        }

        private int hashCode = 0;
       
        public double MaximumComponent()
        {
            return Math.max(this.x,this.y);
        }
       

        public Vector2d ComponentwiseMax(double max)
        {
            return new Vector2d(Math.max(this.x, max), Math.max(this.y, max));
        }

        public static Vector3d CrossProduct(Vector2d one, Vector2d two)
        {
            return new Vector3d(
                0 ,
                0,
                one.x*two.y - one.y*two.x
                );
        }
        public static Vector2d ComponentwiseMultiply(Vector2d one, Vector2d two)
        {
            return new Vector2d(one.x * two.x, one.y * two.y);
        }

        public Vector2d ComponentwiseMultiply(Vector2d other)
        {
            return ComponentwiseMultiply(this, other);
        }
        public static double DotProduct(Vector2d one, Vector2d two)
        {
            return one.x * two.x + one.y * two.y;
        }

        public static Vector2d Scale(Vector2d one, double two)
        {
            return new Vector2d(one.x * two, one.y * two);
        }

        public Vector3d CrossProduct(Vector2d secondVector)
        {
            return CrossProduct(this, secondVector);
        }
        public double DotProduct(Vector2d secondVector)
        {
            return DotProduct(this, secondVector);
        }
        public Vector2d Scale(double scalar)
        {
            return Scale(this, scalar);
        }

        public Vector2d Translate(Vector2d t)
        {
            return this.Add(t);
        }

        public Vector2d Interpolate(Vector2d a, double t)
        {
            return new Vector2d(this.Add(a.Subtract(this).Scale(t)));
        }

        public final static Vector2d ORIGIN = new Vector2d(0, 0);
}
