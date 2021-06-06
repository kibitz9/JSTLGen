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
public class SDF3dOperationRoundEdges extends SignedDistanceField3d{
    SignedDistanceField3d toRound;
    private double amount;
    public SDF3dOperationRoundEdges(SignedDistanceField3d toRound,double additionalDistance)
    {
        this.amount= additionalDistance;
        this.toRound = toRound;
    }
    @Override
    public double GetRawDistance(Vector3d p)
    {
        return toRound.GetDistance(p) - amount;
    }
    @Override
    public SignedDistanceField3d Clone()
    {
        return new SDF3dOperationRoundEdges(toRound.Clone(), amount);
    }
}
