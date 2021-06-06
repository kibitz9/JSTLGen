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
public abstract class SDFOperationRepeat extends SignedDistanceField3d{
    protected double repeatsBefore;
    protected double repeatsAfter;
    protected double width;
    protected double halfWidth;
    protected double boundrymin;
    protected double boundrymax;
    protected double outsideMoveBefore;
    protected double outsideMoveAfter;
    protected SignedDistanceField3d toRepeat;
    
    SDFOperationRepeat(SignedDistanceField3d toRepeat, double repeatsBefore, double repeatsAfter, double width){
        this.repeatsBefore = repeatsBefore;
        this.repeatsAfter = repeatsAfter;
        this.width = width;
        this.halfWidth = width / 2;
        this.toRepeat = toRepeat;
        this.boundrymin = -(width * repeatsBefore + halfWidth);
        this.boundrymax = width * repeatsAfter + halfWidth;
        this.outsideMoveBefore = width * repeatsBefore;
        this.outsideMoveAfter = -(width * repeatsAfter);
    }
    
    
}
