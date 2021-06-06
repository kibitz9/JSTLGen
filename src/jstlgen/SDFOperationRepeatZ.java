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
public class SDFOperationRepeatZ extends SDFOperationRepeat {

    @Override
    public double GetRawDistance(Vector3d p) {
        double pz = p.z;
        boolean dontrepeatleft=false;
        boolean dontrepeatright = false; ;
        if (pz <= boundrymin)
        {
            return toRepeat.GetDistance(new Vector3d(p.x, p.y, pz + outsideMoveBefore));
        }
        else if (pz >= boundrymax)
        {
            return toRepeat.GetDistance(new Vector3d(p.x, p.y, pz+ outsideMoveAfter));
        }
        else if (pz < boundrymin + width)
        {
            dontrepeatleft = true;
        }
        if (pz > boundrymax - width)
        {
            dontrepeatright = true;
        }

        double pz2 =GetScaledFrac(pz, width);

        double dist1 = toRepeat.GetDistance(new Vector3d(p.x, p.y, pz2)); 
        double dist2 = dontrepeatleft ? Double.MAX_VALUE : toRepeat.GetDistance(new Vector3d(p.x, p.y, pz2 + width));
        double dist3 = dontrepeatright ? Double.MAX_VALUE : toRepeat.GetDistance(new Vector3d(p.x, p.y, pz2 - width));
        // return dist3;
        return min(dist1, min(dist2, dist3));
    }

    public SDFOperationRepeatZ(SignedDistanceField3d toRepeat, double repeatsBefore, double repeatsAfter, double width){
        super(toRepeat,repeatsBefore,repeatsAfter,width);
    }
    
    @Override
    public SignedDistanceField3d Clone() {
        return new SDFOperationRepeatZ(toRepeat,repeatsBefore,repeatsAfter,width);
    }
    
}
