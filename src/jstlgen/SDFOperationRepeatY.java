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
public class SDFOperationRepeatY extends SDFOperationRepeat{

    @Override
    public double GetRawDistance(Vector3d p) {
        double py = p.y;
        boolean dontrepeatleft=false;
        boolean dontrepeatright = false; ;
        if (py <= boundrymin)
        {
            return toRepeat.GetDistance(new Vector3d(p.x, py + outsideMoveBefore, p.z));
        }
        else if (py >= boundrymax)
        {
            return toRepeat.GetDistance(new Vector3d(p.x, py+ outsideMoveAfter, p.z));
        }
        else if (py < boundrymin + width)
        {
            dontrepeatleft = true;
        }
        if (py > boundrymax - width)
        {
            dontrepeatright = true;
        }

        double py2 =GetScaledFrac(py, width);

        double dist1 = toRepeat.GetDistance(new Vector3d(p.x, py2, p.z)); 
        double dist2 = dontrepeatleft ? Double.MAX_VALUE : toRepeat.GetDistance(new Vector3d(p.x,py2 + width,  p.z));
        double dist3 = dontrepeatright ? Double.MAX_VALUE : toRepeat.GetDistance(new Vector3d(p.x, py2 - width,  p.z));
        // return dist3;
        return min(dist1, min(dist2, dist3));
    }
    
    public SDFOperationRepeatY(SignedDistanceField3d toRepeat, double repeatsBefore, double repeatsAfter, double width){
        super(toRepeat,repeatsBefore,repeatsAfter,width);
    }
    @Override
    public SignedDistanceField3d Clone() {
        return new SDFOperationRepeatY(toRepeat,repeatsBefore,repeatsAfter,width);
    }
    
}
