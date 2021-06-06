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
public class SDFOperationRepeatX extends SDFOperationRepeat{

    @Override
    public SignedDistanceField3d Clone() {
        return new SDFOperationRepeatX(toRepeat, repeatsBefore, repeatsAfter, width);
    }
    
    public SDFOperationRepeatX(SignedDistanceField3d toRepeat, double repeatsBefore, double repeatsAfter, double width){
        super(toRepeat,repeatsBefore,repeatsAfter,width);
    }
    
    
    @Override
    public double GetRawDistance(Vector3d p)
        {
            double px = p.x;
            boolean dontrepeatleft=false;
            boolean dontrepeatright = false; ;
            if (px <= boundrymin)
            {
                return toRepeat.GetDistance(new Vector3d(px + outsideMoveBefore, p.y, p.z));
            }
            else if (px >= boundrymax)
            {
                return toRepeat.GetDistance(new Vector3d(px + outsideMoveAfter, p.y, p.z));
            }
            else if (px < boundrymin + width)
            {
                dontrepeatleft = true;
            }
            if (px > boundrymax - width)
            {
                dontrepeatright = true;
            }

            double px2 =GetScaledFrac(px, width);


           
            double dist1 = toRepeat.GetDistance(new Vector3d(px2, p.y, p.z)); 
            double dist2 = dontrepeatleft ? Double.MAX_VALUE : toRepeat.GetDistance(new Vector3d(px2 + width, p.y, p.z));
            double dist3 = dontrepeatright ? Double.MAX_VALUE : toRepeat.GetDistance(new Vector3d(px2 - width, p.y, p.z));
           // return dist3;
            return min(dist1, min(dist2, dist3));
            
        }
}
