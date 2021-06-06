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
public class BinaryVector3d {
    public BinaryVector3d(boolean x, boolean y, boolean z)
      {
          this.x = x;
          this.y = y;
          this.z = z;
      }
      private boolean x;
      private boolean y;
      private boolean z;



      public BinaryVector3d Not()
      {
          return new BinaryVector3d(!x, !y, !z);
      }

      public boolean All()
      {
          return this.x && this.y && this.z;
      }
      public boolean Any()
      {
          return this.x || this.y || this.z;
      }
      public boolean None()
      {
          return !Any();
      }
      

      public BinaryVector3d(BinaryVector3d toCopy)
      {
          this.x = toCopy.x;
          this.y = toCopy.y;
          this.z = toCopy.z;
      }
  
      
//      public override bool Equals(object obj)
//      {
//          BinaryVector3d v = obj as BinaryVector3d;
//          if (v != null)
//          {
//              if (v.x == this.x && v.y == this.y && v.z == this.z)
//              {
//                  return true;
//              }
//          }
//          return false;
//      }
//      public bool Equals(BinaryVector3d other)
//      {
//          if (other != null)
//          {
//              if (other.x == this.x && other.y == this.y && other.z == this.z)
//              {
//                  return true;
//              }
//          }
//          return false;
//      }


//      private int hashCode = 0;
//      public override int GetHashCode()
//      {
//          if (hashCode != 0)
//          {
//              return hashCode;
//          }
//          hashCode = (x).GetHashCode() + (y).GetHashCode() + (z).GetHashCode();
//          return hashCode;
//      }



}
