/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen;
import java.util.*;
/**
 *
 * @author Christopher.Miller
 */
public class Solid  {
     //private static boolean noBurnShrink = true;
        private Face[] faces;
        //private List<Face> faces;
        //private List<FaceMeta> faceMeta;
       
        //private static List<System.Threading.Thread> threads; 
        //static Solid()
        //{
        //    for (int a = 0; a < 24; a++)
        //    {
        //        GenericThread t = new GenericThread();
        //        threads.Add(t);
        //    }
        //    //threads = new List<System.Threading.Thread>();
        //    //for (int a = 0; a < 24; a++)
        //    //{
        //    //    threads.Add(new System.Threading.Thread());
        //    //}
        //}

        
        public Solid(){
            //an empty solid.
        }
        //public static final int THREADCOUNT  = 24;

//        public Solid ShrinkTowardsSlope(SignedDistanceField3d sdf, double epsilon)
//        {
//            return ShrinkTowardsSlope(sdf, epsilon, THREADCOUNT);
//            //List<Face> returnFaces = new List<Face>();
//            //foreach (Face f in this.faces)
//            //{
//            //    Face f2 = f.ShrinkTowardsSlope(sdf, epsilon);
//            //    returnFaces.Add(f2);
//            //}
//            //return new Solid(returnFaces);
//        }


        public Solid ShrinkTowardsSlope(SignedDistanceField3d sdf,double epsilon, int threadCount, boolean noBurnShrink)
        {
            
            
            if (noBurnShrink)
            {
                ThreadShrinkTowardsSlope.ShrinkTowardsSlopeNoBurn(sdf, epsilon, threadCount, this.faces);
                return this;
            }
            Solid s = new Solid(this);
            ThreadShrinkTowardsSlope.ShrinkTowardsSlopeNoBurn(sdf, epsilon, threadCount, s.faces);
            return s;
//            List<Face> temp = new ArrayList<Face>();
//
//            activeThreads = new ArrayList<ThreadBase>();
//            toAwaken = java.lang.Thread.currentThread();
//            //List<ThreadShrinkTowardsSlope> threads = new List<ThreadShrinkTowardsSlope>();
//            for (int a = 0; a < threadCount; a++)
//            {
//                activeThreads.Add(new ThreadShrinkTowardsSlope(threadCount, a, this.faces, sdf, epsilon, false, this));
//            }
//
//            try
//            {
//                Thread.currentThread().wait();
//            }
//            catch (java.lang.InterruptedException wake)
//            {
//
//            }
            //bool done = false;
            //while (!done)
            //{

            //    done = true;
            //    foreach (ThreadShrinkTowardsSlope g in threads)
            //    {

            //        // g.t.Join();
            //        if (!g.Done)
            //        {
            //            done = false;
            //            break;
            //        }
            //    }
            //    System.Threading.Thread.Sleep(100);
            //}

//            for (ThreadShrinkTowardsSlope g : activeThreads)
//            {
//                temp.AddRange(g.results);
//            }

            //return new Solid(temp);

        }

        static List<ThreadBase> activeThreads=null;
        static java.lang.Thread toAwaken;
//        public Solid ShrinkTowardsSlopeNoBurn(SignedDistanceField3d sdf, double epsilon, int threadCount)
//        {
//            List<Face> temp = new ArrayList<Face>();
//
//            activeThreads = new ArrayList<ThreadBase>();
//            toAwaken = Thread.currentThread();
//
//            for (int a = 0; a < threadCount; a++)
//            {
//                activeThreads.Add(new ThreadShrinkTowardsSlope(threadCount, a, this.faces, sdf, epsilon, true, this));
//            }
//
//            try
//            {
//                Thread.currentThread().wait();
//            }
//            catch(java.lang.InterruptedException wake)
//            {
//
//            }
//            //bool done = false;
//            //while (!done)
//            //{
//                
//            //    done = true;
//            //    foreach (ThreadShrinkTowardsSlope g in shrinkTowardsSlopThreads)
//            //    {
//
//            //        // g.t.Join();
//            //        if (!g.Done)
//            //        {
//            //            done = false;
//            //            break;
//            //        }
//            //    }
//            //    System.Threading.Thread.Sleep(100);
//            //}
//
//            //foreach (ShrinkTowardsSlopeThread g in threads)
//            //{
//            //    temp.AddRange(g.results);
//            //}
//
//            //return new Solid(temp);
//            return this;
//
//        }
//       


        public Solid ShrinkWrap(SignedDistanceField3d sdf, int iterations)
        {
            Solid returnSolid = this;
            for (int a = 0; a < iterations; a++)
            {
                returnSolid = returnSolid.ShrinkWrap(sdf);
            }
            return returnSolid;
        }
        public Solid ShrinkWrap(SignedDistanceField3d sdf)
        {
            List<Face> returnFaces = new ArrayList<Face>();
            for(Face f : this.faces)
            {
                Face f2 = f.ShrinkWrap(sdf);
                returnFaces.add(f2);
            }
            return new Solid(returnFaces);
        }




//        private Solid GravityShrinkWrap(SignedDistanceField3d sdf, int iterations, List<Vector3d> gravityPoints, double ratioOfDistanceToTravel, int threadCount)
//        {
//            Solid returnSolid = this;
//            for (int a = 0; a < iterations; a++)
//            {
//                returnSolid = returnSolid.GravityShrinkWrap(sdf,gravityPoints, ratioOfDistanceToTravel, threadCount);
//            }
//            return returnSolid;
//        }


//
//        private static List<FractalHedron> HedronSubdivide(SignedDistanceField3d sdf, List<FractalHedron> hedronPool, double epsilon, double lineLength, boolean finalCull)
//        {
//            return HedronSubdivide(sdf, hedronPool, epsilon, THREADCOUNT, lineLength,   finalCull);
//        }

      
//        private static List<FractalHedron> HedronSubdivide(SignedDistanceField3d sdf, List<FractalHedron> hedronPool, double epsilon, int threadCount, double lineLength,  boolean finalCull)
//        {
//            return ThreadSubdivideHedron.HedronSubdivide(sdf, hedronPool, epsilon, threadCount, lineLength, finalCull);
////            List<FractalHedron> temp = new ArrayList<FractalHedron>();
////            activeThreads = new ArrayList<ThreadBase>();
////            toAwaken = Thread.currentThread();
////
////            //List<ThreadSubdivideHedron> threads = new List<ThreadSubdivideHedron>();
////            for (int a = 0; a < threadCount; a++)
////            {
////                activeThreads.Add(new ThreadSubdivideHedron(threadCount, a, hedronPool, sdf, lineLength, epsilon, finalCull, new Solid()));
////            }
////            try
////            {
////                Thread.currentThread().wait();
////            }
////            catch (java.lang.InterruptedException wake)
////            {
////
////            }
////            System.out.println("******************************************");
////            //}
////           
////            for (ThreadSubdivideHedron g : activeThreads)
////            {
////                temp.AddRange(g.results);
////                
////            }
////            
////            return temp;
//
//        }
//
//

    
        public Solid CenterSplitStressedFaces(SignedDistanceField3d sdf, double epsilon, double threshold)
        {
            List<Face> newFaces = new ArrayList<Face>();
            for(Face f : this.faces)
            {
                newFaces.addAll(f.CenterSplitOnTension(sdf, epsilon, threshold));
            }
            return new Solid(newFaces);
        }

/*
        public Solid SideSplitStressedFaces(SignedDistanceField3d sdf, double epsilon, double threshold, int threadCount)
        {
            //List<Face> temp = new List<Face>();
            //Face[] temp = new Face[faces.Length * 4];
            int count2 = 0;
            activeThreads = new ArrayList<ThreadBase>();
            toAwaken = Thread.currentThread();
            
            for (int a = 0; a < threadCount; a++)
            {
                activeThreads.Add(new ThreadSideSplitStressedFaces(threadCount, a, this.faces, sdf, epsilon, true,threshold));
            }
            
            
            try
            {
                Thread.currentThread().wait();
            }
            catch(java.lang.InterruptedException wake)
            {

            }
//            boolean done = false;
//            
//            while (!done)
//            {
//                
//                done = true;
//                for (ThreadSideSplitStressedFaces g : threads)
//                {
//
//                    // g.t.Join();
//                    if (!g.Done)
//                    {
//                        done = false;
//                        break;
//                    }
//                }
//                System.Threading.Thread.Sleep(100);
//            }

            int items = 0;
            for (ThreadSideSplitStressedFaces g : activeThreads)
            {
                
                items += g.results2.Length;
                


            }
            Face[] temp = new Face[items];
            int index = 0;
            for (ThreadSideSplitStressedFaces g : activeThreads)
            {
                for (int zz = 0; zz < g.results2.Length; zz++)
                {
                    temp[index++] = g.results2[zz];
                }


            }


            
            return new Solid(temp);
            
        }
*/

//        public Solid SideSplitStressedFaces(SignedDistanceField3d sdf, double epsilon, double threshold, int threadcount)
//        {
//            int count = 0;
//            Solid x = SideSplitStressedFaces(sdf, epsilon, threshold, threadcount);
//
//            //ssss
//            
//            //List<Face> newFaces = new List<Face>();
//            //for (int a = 0; a < this.faces.Length; a++) {
//            //    //foreach (Face f in this.faces)
//            //    //{
//            //    Face f = this.faces[a];
//            //    List<Face> temp = f.SideSplitOnTension(sdf, epsilon, threshold);
//            //    count += (temp.Count - 1);
//            //    newFaces.AddRange(temp);
//            //}
//            //System.Diagnostics.Debug.WriteLine("Added " + count + " faces.");
//            return x;// new Solid(newFaces);
//        }

       

        public static Solid HedronTriangulate(SignedDistanceField3d sdf, double initialLineLength, double minimumLineLength, double epsilon, int threadCount)
        {
            Line boundingBox = sdf.GetBoundingBox(1);
            double sizex = boundingBox.end.x - boundingBox.start.x;
            double sizey = boundingBox.end.y - boundingBox.start.y;
            double sizez = boundingBox.end.z - boundingBox.start.z;

            double max = Math.max(sizex, Math.max(sizey, sizez));
            double doubleMax = max * 2.0;


            FractalOctahedron f = new FractalOctahedron();
            f.Scale(doubleMax);
            f.Translate(new Vector3d(boundingBox.start.Midpoint(boundingBox.end)));


            List<FractalHedron> octs = new ArrayList<FractalHedron>();
            List<FractalHedron> tempOcts = new ArrayList<FractalHedron>();
            int cullCount = 0;
            octs.add(f);
            double lineLength = octs.get(0).GetLineLength();
            while (lineLength > initialLineLength)
            {
                int tempCullCount = 0;
                
                tempOcts = ThreadSubdivideHedron.HedronSubdivide(sdf, octs, epsilon, threadCount, lineLength, false);
                //tempOcts = HedronSubdivide(sdf, octs, epsilon, lineLength, false);
                //tempOcts = new List<FractalHedron>();
                //foreach(FractalHedron ff in octs)
                //{
                //    int state = ff.GetSpanningState(sdf, lineLength,epsilon);
                //    //don't bother to subdivide if it's far from the object...
                //    if (state * state != 4)
                //    {
                //        List<FractalHedron> candidates = ff.Subdivide();
                //        foreach (FractalHedron fff in candidates)
                //        {
                //            state = fff.GetSpanningState(sdf, lineLength / 2, epsilon);
                //            if (state*state != 4)
                //            {
                //                tempOcts.Add(fff);
                //            }
                //            else
                //            {
                //                cullCount++;
                //            }
                //            //tempOcts.AddRange(ff.Subdivide());
                //        }
                //    }
                //    else
                //    {
                //        cullCount++;
                //    }
                //}
                octs = tempOcts;
                cullCount += tempCullCount;
                if (octs.size() > 0)
                {
                    lineLength = octs.get(0).GetLineLength();
                }
                else
                {
                    return null;// lineLength = minimumLineLength;
                }
            }
            //System.Diagnostics.Debug.WriteLine("Culled " + cullCount + " hedrons.");cullCount = 0;
            //tempOcts = new List<FractalHedron>();

            //cull some more
            //foreach (FractalSubdivision fff in octs)
            //{
            //    int state = fff.GetSpanningState(sdf, lineLength);
            //    state *= state;//remove sign. 2 and -2 will become 4. anything else we will keep first pass
            //    if (state != 4)
            //    {
            //        tempOcts.Add(fff);
            //    }
            //    else
            //    {
            //        cullCount++;
            //    }
            //}
            //System.Diagnostics.Debug.WriteLine("Culled " + cullCount + " hedrons."); cullCount = 0;
            //octs = tempOcts;
            tempOcts = new ArrayList<FractalHedron>();
            //the following repeats the above just for those objects that are spanning, subdividing them a bit more and reculling...
            lineLength = octs.get(0).GetLineLength();
            //List<FractalHedron> tempOctsNearlyFinal = new List<FractalHedron>();
            while (lineLength > minimumLineLength)
            {
                int tempCullCount = 0;
                tempOcts = ThreadSubdivideHedron.HedronSubdivide(sdf, octs, epsilon, threadCount, lineLength, false);
                //tempOcts = HedronSubdivide(sdf, octs, epsilon, lineLength, false);
                //tempOcts = new List<FractalHedron>();
                //foreach (FractalHedron ff in octs)
                //{
                //    int state = ff.GetSpanningState(sdf, lineLength, epsilon);
                //    //don't bother to subdivide if it's far from the object...
                //    if (state * state != 4)
                //    {
                //        //double normalDiff = ff.GetSurfaceNormalVariance(sdf, .0000001);
                //        //if (normalDiff < .1)
                //        //{
                //        //    tempOctsNearlyFinal.Add(ff);
                //        //}
                //        //else { 
                //            List<FractalHedron> candidates = ff.Subdivide();
                //            foreach (FractalHedron fff in candidates)
                //            {
                //                state = fff.GetSpanningState(sdf, lineLength / 2, epsilon);
                //                if (state*state != 4)
                //                {
                //                    tempOcts.Add(fff);
                //                }
                //                else
                //                {
                //                    cullCount++;
                //                }
                //                //tempOcts.AddRange(ff.Subdivide());
                //            }
                //        //}


                //        //tempOcts.AddRange(ff.Subdivide());
                //    }
                //    else
                //    {
                //        cullCount++;
                //    }
                //}
                octs = tempOcts;
                cullCount += tempCullCount;
                if (octs.size() > 0)
                {
                    lineLength = octs.get(0).GetLineLength();
                }
                else
                {
                    return null;
                }
                //lineLength /= 2;
            }
            //octs.AddRange(tempOctsNearlyFinal);
            //System.Diagnostics.Debug.WriteLine("Culled " + cullCount + " hedrons."); cullCount = 0;
            tempOcts = new ArrayList<FractalHedron>();
            {
                int tempCullCount = 0;
                //tempOcts = HedronSubdivide(sdf, octs, epsilon, lineLength, out tempCullCount, true);//true is different here!!!!!!
                //tempOcts = new List<FractalHedron>();

                for (FractalHedron fff : octs)
                {
                    if (fff.GetSpanningState(sdf, 0, epsilon) == 0)
                    {
                        tempOcts.add(fff);
                    }
                    else
                    {
                        cullCount++;
                    }
                }
                octs = tempOcts;
                cullCount += tempCullCount;
            }
            //System.Diagnostics.Debug.WriteLine("Culled " + cullCount + " hedrons."); cullCount = 0;


            List<Face> returnFaces = new ArrayList<Face>();
            for(FractalHedron ffff : octs)
            {
                returnFaces.addAll(ffff.GetOutsideFacesOnly(sdf,epsilon));
                //returnFaces.AddRange(ffff.GetFaces());
            }
            return new Solid(returnFaces);

        }

        


        //private List<Face> GravityShrinkWrap_ThreadEntry1(SignedDistanceField3d sdf, List<Vector3d> gravityPoints, double ratioOfDistanceToTravel, int threadCount, int threadNumber)
        //{
        //    List<Face> returnFaces = new List<Face>();
        //    for (int a = threadNumber; a < returnFaces.Count; a += threadCount) { 
        //        Face f = this.faces[a];
          
        //        Face f2 = f.GravityShrinkWrap(sdf, gravityPoints, ratioOfDistanceToTravel);
        //        returnFaces.Add(f2);
        //    }
        //    return returnFaces;
        //}

        public Solid ShrinkWrap2(SignedDistanceField3d sdf, double maxTravel, int iterations)
        {
            Solid returnSolid = this;
            for (int a = 0; a < iterations; a++)
            {
                returnSolid = returnSolid.ShrinkWrap2(sdf, maxTravel);
            }
            return returnSolid;
        }
        public Solid ShrinkWrap2(SignedDistanceField3d sdf, double maxTravel)
        {
            List<Face> returnFaces = new ArrayList<Face>();
            for (Face f : this.faces)
            {
                Face f2 = f.ShrinkWrap2(sdf, maxTravel);
                returnFaces.add(f2);
            }
            return new Solid(returnFaces);
        }

        public Solid(List<Face> faces)
        {
            this.faces = new Face[faces.size()];
            this.faces = faces.toArray(this.faces);
        }

        public Solid(Face[] faces)
        {
            this.faces = faces;
        }
        public Solid(Solid toCopy)
        {
            Face[] newFaces = new Face[toCopy.faces.length];
            for (int a = 0; a < newFaces.length; a++) {

                newFaces[a] = toCopy.faces[a];
            }
            this.faces = newFaces;
        }
        public Solid Copy()
        {
            return new Solid(this);
        }
        public Face GetFace(int index)
        {
            return faces[index];
        }
       
        public int GetFaceCount()
        {
            
            return faces.length;

        }
     

        public Solid Scale(double scale)
        {
            return Scale(Vector3d.ORIGIN, scale);
        }
        public Solid Scale(Vector3d origin, double scale)
        {
            List<Face> returnList = new ArrayList<Face>();
            for(Face f : this.faces)
            {
                returnList.add(f.Scale(origin, scale));
            }
            return new Solid(returnList);
        }
        public byte[] toBinary()
        {
            int counter = 0;
            byte[] allData = new byte[80 + 4 + (this.faces.length * 50)];
            counter = 80;//skip over unused header info.
           
            
            
            
            
            counter = Util.AppendBytes(allData, faces.length, counter);
            for(Face f : this.faces)
            {
                counter = Util.AppendBytes(allData, f.ToBinary(), counter);
            }
            return allData;
        }

      

        public Solid Subdivide()
        {
            List<Face> newFaces = new ArrayList<Face>();
            for(Face f : this.faces) {
                Vector3d newPointFour = f.one.Midpoint(f.two);
                Vector3d newPointFive = f.two.Midpoint(f.three);
                Vector3d newPointSix = f.three.Midpoint(f.one);

                newFaces.add(
                    new Face(f.one, newPointFour, newPointSix)
                );
                newFaces.add(
                    new Face(newPointFour, f.two, newPointFive)
                );
                newFaces.add(
                    new Face(newPointFour, newPointFive, newPointSix)
                );
                newFaces.add(
                    new Face(newPointSix, newPointFive, f.three)
                );


            }
            return new Solid(newFaces);
        }
        
     
        public Solid SnapFacesToUnitSphere()
        {
            List<Face> newFaces = new ArrayList<Face>();
            for(Face f : this.faces)
            {
                newFaces.add(f.SnapToUnitSphere());
            }
            return new Solid(newFaces);
        }
        public Solid NegateZAxis()
        {
            List<Face> newFaces = new ArrayList<Face>();
            for(Face f : this.faces)
            {
                newFaces.add(
                    new Face(
                        f.one.x, f.one.y, -f.one.z,
                        f.two.x, f.two.y, -f.two.z,
                        f.three.x, f.three.y, -f.three.z
                    )
                ); ; 
            }
            return new Solid(newFaces);
        }
        public Solid Translate(Vector3d v)
        {
            List<Face> newFaces = new ArrayList<Face>();
            for (Face f : this.faces)
            {

                newFaces.add(f.Translate(v));
              
            }
            return new Solid(newFaces);
        }
        public Solid Translate(double x, double y, double z)
        {
            return Translate(new Vector3d(x, y, z));
        }
        public Solid InsideOut()
        {
            List<Face> newFaces = new ArrayList<Face>();
            for (Face f : this.faces)
            {
                newFaces.add(f.Flip());
              
            }
            return new Solid(newFaces);
        }

        // private solid GenerateAdditionalFacesForCSG(Solid otherSolid)
//
//        private static boolean AlreadyInList(List<Intersection> l, Vector3d v)
//        {
//            foreach(Intersection i in l)
//            {
//                Vector3d vv = i.Location;
//                if (vv.X == v.X && vv.Y == v.Y && vv.Z == v.Z)
//                {
//                    return true;
//                }
//            }
//            return false;
//        } 

        //private Solid SubdivideOnIntersectingFace(Face intersecting)
        //{
        //    List<Face> returnFaces = new List<Face>();

        //    this.RefreshFaceMeta();
        //    foreach(FaceMeta fm in this.faces)
        //    {

        //    }
            

            
        //}
//
//        private bool FaceExists(Face faceToCheck)
//        {
//            //return true if a similar face is already in the list(same three end points in same rotation.
//            foreach(Face f in this.faces)
//            {
//                if (f.One == faceToCheck.One)
//                {
//                    if (f.Two == faceToCheck.Two)
//                    {
//                        if (f.Three == faceToCheck.Three)
//                        {
//                            return true;
//                        }
//                    }
//                }
//
//                if (f.Two == faceToCheck.One)
//                {
//                    if (f.Three == faceToCheck.Two)
//                    {
//                        if (f.One == faceToCheck.Three)
//                        {
//                            return true;
//                        }
//                    }
//                }
//
//                if (f.Three == faceToCheck.One)
//                {
//                    if (f.One == faceToCheck.Two)
//                    {
//                        if (f.Two == faceToCheck.Three)
//                        {
//                            return true;
//                        }
//                    }
//                }
//            }
//            return false;
//        }

//        public Solid NaiveMerge(Solid otherSolid)
//        {
//            //join two solids without any merging or splitting of faces.
//            //can result in invalid solids.
//            List<Face> newFaces = new List<Face>();
//
//            newFaces.AddRange(this.faces);
//            newFaces.AddRange(otherSolid.faces);
//          
//            return new Solid(newFaces);
//        }

//        private static List<Face> GenerateAdditionalFaces(List<Face> partialFaceList, List<Face> possibleCollisionFaces, boolean interiorOnly, boolean exteriorOnly)
//        {
//            return GenerateAdditionalFaces(partialFaceList, possibleCollisionFaces, 0, interiorOnly, exteriorOnly);
//        }


        //private static void FindIntersectionFaces(List<Face> faceList, List<Face> possibleCollisionFaces, out List<Face> nonIntersectingFaces, out List<Face> intersectingFaces, out List<Face> ConfirmedCollisionFaces)
        //{
        //    List<Face> nonIntersects = new List<Face>();
        //    List<Face> intersects = new List<Face>();
        //    List<Face> confirmedCollisions = new List<Face>();
        //    foreach(Face f in faceList)
        //    {
        //        if (Util.LineIntersectsTriangle(
        //    }
        //}


        //private static BreakLineIntersections(List<FaceMeta> facesToBreak, Face foreignFace)
        //{
        //    List<FaceMeta> oldList = new List<FaceMeta>();
        //    oldList.AddRange(facesToBreak);
        //    //List<FaceMeta> newLIst = new List<FaceMeta>();

        //    for (int a = 0; a < facesToBreak.Count; a++)
        //    {
        //        FaceMeta fm = facesToBreak[a];
        //        Face f = fm.Face;

        //        Vector intersectionLocation = Util.LineIntersectsTriangle(f.One, f.Two, foreignFace);
        //        if (intersectionLocation != null)
        //        {
        //            FaceMeta joiner = fm.OneTwoNeighbor;
        //            oldList.Remove(joiner);
        //            oldList.Remove(fm);

        //            Face aa = new Face(f.One, intersectionLocation, f.Three);
        //            Face bb = new Face(f.Two, f.Three, intersectionLocation);
        //            Face cc = new Face(f.One, fm.OneTwoNonCommonVector, intersectionLocation);
        //            Face dd = new Face(f.Two, intersectionLocation, fm.OneTwoNonCommonVector);

        //            FaceMeta aaa = new FaceMeta(a);
                    
        //        }
        //    }
        //}
        //private List<FaceMeta> BreakTwoFacesOnCommonLinePoint(FaceMeta one, FaceMeta two, Vector intersectionLocation, Vector commonFirst, Vector commonSecond)
        //{
            
        //}

//        private static List<Face> SubDivideOnForeignLine(Face f, Vector3d lineStart, Vector3d lineEnd)
//        {
//            List<Face> returnFaces = new List<Face>();
//            Vector3d intersection = Util.LineIntersectsTriangle(lineStart, lineEnd, f);
//            if (intersection == null)
//            {
//                returnFaces.Add(f);
//                return returnFaces;
//            }
//
//            Face a = new Face(f.One, f.Two, intersection);
//            Face b = new Face(f.One, intersection, f.Three);
//            Face c = new Face(f.Two, f.Three, intersection);
//            if (!a.HasNoArea)
//            {
//                returnFaces.Add(a);
//            }
//            if (!b.HasNoArea)
//            {
//                returnFaces.Add(b);
//            }
//            if (!c.HasNoArea)
//            {
//                returnFaces.Add(c);
//            }
//            return returnFaces;
//        }
//
//        private static List<Face> SubDivideOnLine(List<Face> facesToDivide, Vector3d lineStart, Vector3d lineEnd){
//            int countInitial = facesToDivide.Count;
//            List<Face> returnFaces = new List<Face>();
//          
//            foreach(Face f in facesToDivide)
//            {
//                returnFaces.AddRange(Solid.SubDivideOnForeignLine(f, lineStart, lineEnd));
//            }
//
//            return returnFaces;
//        }

//        private static List<Face> SubDivideOnFace(List<Face> facesToDivide, Face knife)
//        {
//            List<Face> returnFaces = SubDivideOnLine(facesToDivide, knife.One, knife.Two);
//            returnFaces = SubDivideOnLine(returnFaces, knife.Two, knife.Three);
//            returnFaces = SubDivideOnLine(returnFaces, knife.Three, knife.One);
//
//
//
//            return returnFaces;
//        }
        //public Solid DivideFaces(Solid otherSolid)
        //{
        //    List<Face> returnFaces = this.faces;
        //    foreach (Face f in otherSolid.faces)
        //    {
        //        returnFaces = Solid.SubDivideOnFace(returnFaces,f);
        //    }



        //    return new Solid(returnFaces);
        //}

        //public List<Face> SubdivideOnFace(Face f, Face toDivide)
        //{

        //    //List<Intersection> intersections = new List<Intersection>();

        //    //Vector intersection1 = Util.LineIntersectsTriangle(f.One, f.Two, toDivide);
        //    //Vector intersection2 = Util.LineIntersectsTriangle(f.Two, f.Three, toDivide);
        //    //Vector intersection3 = Util.LineIntersectsTriangle(f.Three, f.One, toDivide);
        //    //Vector intersection4 = Util.LineIntersectsTriangle(toDivide.One, toDivide.Two, f);
        //    //Vector intersection5 = Util.LineIntersectsTriangle(toDivide.Two, toDivide.Three, f);
        //    //Vector intersection6 = Util.LineIntersectsTriangle(toDivide.Three, toDivide.One, f);


        //    //if (intersection1 != null)
        //    //{
        //    //    intersections.Add(new Intersection(intersection1, true, f.One, f.Two, toDivide, f));
        //    //    //break;
        //    //}
        //    //if (intersection2 != null)
        //    //{
        //    //    intersections.Add(new Intersection(intersection2, true, f.Two, f.Three, toDivide, f));
        //    //    //break;
        //    //}
        //    //if (intersection3 != null)
        //    //{
        //    //    intersections.Add(new Intersection(intersection3, true, f.Three, f.One, toDivide, f));
        //    //    //break;
        //    //}

        //    //if (intersection4 != null)
        //    //{
        //    //    intersections.Add(new Intersection(intersection4, true, toDivide.One, toDivide.Two, toDivide, f));
        //    //    //break;
        //    //}
        //    //if (intersection5 != null)
        //    //{
        //    //    intersections.Add(new Intersection(intersection5, true, toDivide.Two, toDivide.Three, toDivide, f));
        //    //    //break;
        //    //}
        //    //if (intersection6 != null)
        //    //{
        //    //    intersections.Add(new Intersection(intersection6, true, toDivide.Three, toDivide.One, toDivide, f));
        //    //    //break;
        //    //}

        //    //if (intersections.Count == 0)
        //    //{
        //    //    return new List<Face>() { toDivide };
        //    //}
        //    //if (intersections.Count == 2)
        //    //{
        //    //    if (intersections[0].IsInterior && intersections[1].IsInterior)
        //    //    {
        //    //        Intersection closest = intersections[0];
        //    //        Intersection farther = intersections[1];

        //    //        if (Line.GetDistance(f.One, f.Two, intersections[1].Location) < Line.GetDistance(f.One, f.Two, intersections[0].Location))
        //    //        {
        //    //            closest = intersections[1];
        //    //            farther = intersections[0];
        //    //        }

        //    //        Face a = new Face(f.One, f.Two, closest.Location);
        //    //        Face b = new Face()

        //    //    }
        //    //}



        //}
        //public Solid SubDivideOnFace(Face f)
        //{
        //    bool intersects = false;
        //    List<Face> passOneFaces = new List<Face>();
        //    List<Face> additionalFaces = new List<Face>();

        //    List<Intersection> intersections = new List<Intersection>();
        //    foreach (Face g in this.faces)
        //    {
               

        //        if (intersection1 != null)
        //        {
        //            intersections.Add(new Intersection(intersection1, false, f.One, f.Two, g, f));
        //            //break;
        //        }
        //        if (intersection2 != null)
        //        {
        //            intersections.Add(new Intersection(intersection2, false, f.Two, f.Three, g, f));
        //            //break;
        //        }
        //        if (intersection3 != null)
        //        {
        //            intersections.Add(new Intersection(intersection3, false, f.Three, f.One, g, f));
        //            //break;
        //        }


        //        //below seem good
                
        //        if (intersection4 != null)
        //        {
        //            intersections.Add(new Intersection(intersection4, true, g.One, g.Two, g, f));
        //            //break;
        //        }

                
        //        if (intersection5 != null)
        //        {
        //            intersections.Add(new Intersection(intersection5, true, g.Two, g.Three, g, f));
        //            //break;
        //        }


                
        //        if (intersection6 != null)
        //        {
        //            intersections.Add(new Intersection(intersection6, true, g.Three, g.One, g, f));
        //            //break;
        //        }

        //        if (intersections.Count == 0)
        //        {
        //            passOneFaces.Add(g);
        //        }
        //        //theoretically at most 2 instersections.
        //        if (intersections.Count == 2)
        //        {
        //            if (intersections[0].IsInterior && intersections[1].IsInterior)
        //            {
        //                if (VectorParticipationCount(f.One, intersections) == 2)
        //                {
        //                    Vector n = FindPoint(f.One, f.Two, intersections);
        //                    Vector o = FindPoint(f.One, f.Three, intersections);
        //                    Face a = new Face(f.One, n, o);
        //                    Face b = new Face(n, f.Two, f.Three);
        //                    Face c = new Face(n, f.Three, o);
        //                    list1.Add(a);
        //                    list1.Add(b);
        //                    list1.Add(c);
        //                    return CullNoAreaTriangles(list1);

        //                }




        //            }
        //        }



        //    }
        //}
//        private static List<Face> GenerateAdditionalFaces(List<Face> partialFaceList, List<Face> possibleCollisionFaces, int depth, bool interiorOnly, bool exteriorOnly)
//        {
//            //of the faces provided in partialFaceList, recursively split them until no further splits occur, then
//            //return the final list. the list past iin may be a partial list of all the solids faces.
//
//            if (depth > 50)
//            {
//                return partialFaceList;
//            }
//
//            int initialCount = partialFaceList.Count;
//            List<Face> returnList = new List<Face>();
//            //bool facesAdded = false;
//            for (int a = 0; a < partialFaceList.Count; a++)
//            {
//                Face aa = partialFaceList[a];
//
//                List<Intersection> intersections = new List<Intersection>();
//
//                bool intersectionsFound = false;
//                for (int b = 0; b < possibleCollisionFaces.Count; b++)
//                {
//                   
//
//                    //we are just finding the first intersection here.
//                    Face bb = possibleCollisionFaces[b];
//
//                    Vector3d intersection1 = Util.LineIntersectsTriangle(aa.One, aa.Two, bb);
//
//
//
//                    if (intersection1 != null && !interiorOnly)
//                    {
//                        intersections.Add(new Intersection(intersection1, false, aa.One, aa.Two,aa,bb));
//                        //break;
//                    }
//                   
//
//                    Vector3d intersection2 = Util.LineIntersectsTriangle(aa.Two, aa.Three, bb);
//
//                    if (intersection2 != null && !AlreadyInList(intersections, intersection2) && !interiorOnly)
//                    {
//                        intersections.Add(new Intersection(intersection2, false, aa.Two, aa.Three, aa, bb));
//                        //break;
//                    }
//
//                    Vector3d intersection3 = Util.LineIntersectsTriangle(aa.Three, aa.One, bb);
//
//                    if (intersection3 != null && !AlreadyInList(intersections, intersection3) && !interiorOnly)
//                    {
//                        intersections.Add(new Intersection(intersection3, false, aa.Three, aa.One, aa, bb));
//                        //break;
//                    }
//
//                    //below seem good
//                    Vector3d intersection4 = Util.LineIntersectsTriangle(bb.One, bb.Two, aa);
//                    if (intersection4 != null && !AlreadyInList(intersections, intersection4) && !exteriorOnly)
//                    {
//                        intersections.Add(new Intersection(intersection4, true, bb.One, bb.Two, aa, bb));
//                        //break;
//                    }
//
//                    Vector3d intersection5 = Util.LineIntersectsTriangle(bb.Two, bb.Three, aa);
//                    if (intersection5 != null && !AlreadyInList(intersections, intersection5) && !exteriorOnly)
//                    {
//                        intersections.Add(new Intersection(intersection5, true, bb.Two, bb.Three, aa, bb));
//                        //break;
//                    }
//
//                    Vector3d intersection6 = Util.LineIntersectsTriangle(bb.Three, bb.One, aa);
//
//                    if (intersection6 != null && !AlreadyInList(intersections, intersection6) && !exteriorOnly)
//                    {
//                        intersections.Add(new Intersection(intersection6, true, bb.Three, bb.One, aa, bb));
//                        //break;
//                    }
//                    //if (intersections.Count!=1&&intersections.Count != 0 && intersections.Count != 2)
//                    //{
//                    //    throw new Exception(intersections.Count.ToString());
//                    //}
//
//                    if (intersections.Count == 2)
//                    {
//                        intersectionsFound = true;
//                        break;
//                    }
//                    else
//                    {
//                        if (intersections.Count > 0)
//                        {
//                            intersections = new List<Intersection>();
//                        }
//                    }
//                }
//                if (!intersectionsFound)
//                {
//                    returnList.Add(aa);//face is good. no intersections we can act upon.
//                }
//                else
//                {
//                    
//                    //facesAdded = true;
//                    returnList.AddRange(Break(aa, intersections,depth));
//
//                    //we have two intersections.
//                }
//              
//
//            }
//            if (initialCount==returnList.Count)
//            {
//                return returnList;
//            }
//            else
//            {
//                return GenerateAdditionalFaces(returnList, possibleCollisionFaces, depth+1,false, false);
//            }
//
//
//
//        }
//        private static List<Face> CullNoAreaTriangles(List<Face> list)
//        {
//            List<Face> newList = new List<Face>();
//            foreach(Face f in list)
//            {
//                if (!f.HasNoArea)
//                {
//                    newList.Add(f);
//                }
//            }
//            return newList;
//        }
//        private static bool debugdisableMixedPoints = false;
//        public static List<Face> Break(Face f, List<Intersection> intersections, int depth)
//        {
//            List<Face> list1 = new List<Face>();
//            if (intersections[0].IsInterior && intersections[1].IsInterior)
//            {
//                //first three new triangles based on first intersection...
//                list1.Add(new Face(f.One, f.Two, intersections[0].Location));
//                list1.Add(new Face(f.Two, f.Three, intersections[0].Location));
//                list1.Add(new Face(f.Three, f.One, intersections[0].Location));
//                //second intersection is now within one of the three triangles...
//                List<Face> list2 = GenerateAdditionalFaces(list1, new List<Face>() { intersections[1].ForeignFace },depth+1,true,false);
//                return CullNoAreaTriangles(list2);
//            }
//            else if (!intersections[0].IsInterior && !intersections[1].IsInterior){
//                if (VectorParticipationCount(f.One, intersections) == 2)
//                {
//                    Vector3d n = FindPoint(f.One, f.Two, intersections); 
//                    Vector3d o = FindPoint(f.One, f.Three, intersections);
//                    Face a = new Face(f.One, n, o);
//                    Face b = new Face(n, f.Two, f.Three);
//                    Face c = new Face(n, f.Three,o);
//                    list1.Add(a);
//                    list1.Add(b);
//                    list1.Add(c);
//                    return CullNoAreaTriangles(list1);
//
//                }
//                else if (VectorParticipationCount(f.Two, intersections) == 2)
//                {
//                    Vector3d n = FindPoint(f.Two, f.Three, intersections);
//                    Vector3d o = FindPoint(f.Two, f.One, intersections);
//                    Face a = new Face(f.Two, n, o);
//                    Face b = new Face(n, f.Three, f.One);
//                    Face c = new Face(n, f.One, o);
//                    list1.Add(a);
//                    list1.Add(b);
//                    list1.Add(c);
//                    return CullNoAreaTriangles(list1);
//                }
//                else if (VectorParticipationCount(f.Three, intersections) == 2)
//                {
//                    Vector3d n = FindPoint(f.Three, f.One, intersections);
//                    Vector3d o = FindPoint(f.Three, f.Two, intersections);
//                    Face a = new Face(f.Three, n, o);
//                    Face b = new Face(n, f.One, f.Two);
//                    Face c = new Face(n, f.Two, o);
//                    list1.Add(a);
//                    list1.Add(b);
//                    list1.Add(c);
//                    return CullNoAreaTriangles(list1);
//                }
//            }
//            else
//            {
//                if (!debugdisableMixedPoints)
//                {
//                    Intersection interior;
//                    Intersection exterior;
//                    if (intersections[0].IsInterior)
//                    {
//                        interior = intersections[0];
//                        exterior = intersections[1];
//                    }
//                    else
//                    {
//                        interior = intersections[1];
//                        exterior = intersections[0];
//                    }
//
//                    if (VectorParticipationCount(f.One, new List<Intersection>() { exterior }) == 0)
//                    {
//                        Face a = new Face(f.One, f.Two, interior.Location);
//                        Face b = new Face(f.One, interior.Location, f.Three);
//                        Face c = new Face(f.Two, exterior.Location, interior.Location);
//                        Face d = new Face(f.Three, interior.Location, exterior.Location);
//                        list1.Add(a);
//                        list1.Add(b);
//                        list1.Add(c);
//                        list1.Add(d);
//                        return CullNoAreaTriangles(list1);
//                    }
//
//                    if (VectorParticipationCount(f.Two, new List<Intersection>() { exterior }) == 0)
//                    {
//                        Face a = new Face(f.Two, f.Three, interior.Location);
//                        Face b = new Face(f.Two, interior.Location, f.One);
//                        Face c = new Face(f.Three, exterior.Location, interior.Location);
//                        Face d = new Face(f.One, interior.Location, exterior.Location);
//                        list1.Add(a);
//                        list1.Add(b);
//                        list1.Add(c);
//                        list1.Add(d);
//                        return CullNoAreaTriangles(list1);
//                    }
//
//                    if (VectorParticipationCount(f.Three, new List<Intersection>() { exterior }) == 0)
//                    {
//                        Face a = new Face(f.Three, f.One, interior.Location);
//                        Face b = new Face(f.Three, interior.Location, f.Two);
//                        Face c = new Face(f.One, exterior.Location, interior.Location);
//                        Face d = new Face(f.Two, interior.Location, exterior.Location);
//                        list1.Add(a);
//                        list1.Add(b);
//                        list1.Add(c);
//                        list1.Add(d);
//                        return CullNoAreaTriangles(list1);
//                    }
//                }
//
//            }
//            return new List<Face> { f };
//            //throw new Exception("what?");
//            //return new List<Face>();
//        }
//        private static Vector3d FindPoint(Vector3d oneSide, Vector3d otherSide, List<Intersection> intersections)
//        {
//            foreach(Intersection i in intersections)
//            {
//                if (i.SegmentEnd == oneSide && i.SegmentStart == otherSide)
//                {
//                    return i.Location;
//                }
//                else if (i.SegmentStart == oneSide && i.SegmentEnd == otherSide)
//                {
//                    return i.Location;
//                }
//            }
//            throw new Exception("Point not found!");
//        }
//        private static int VectorParticipationCount(Vector3d v, List<Intersection> intersections)
//        {
//            int count = 0;
//            foreach(Intersection i in intersections)
//            {
//                if (i.SegmentStart == v || i.SegmentEnd == v)
//                {
//                    count++;
//                }
//            }
//            return count;
//        }

        //public Solid RemoveTrianglesInsideOtherSolid(Solid otherSolid)
        //{
        //    List<Face> newFaces = new List<Face>();
        //    foreach (Face f in this.faces)
        //    {
        //        bool remove = false;
        //        if (otherSolid.PointIsInside(f.Center))
        //        {
        //            remove = true;
        //        }
        //        if (!remove)
        //        {
        //            newFaces.Add(f);
        //        }
        //    }
        //    return new Solid(newFaces);
        //}
        //public Solid RemoveTrianglesOutsideOtherSolid(Solid otherSolid)
        //{
        //    List<Face> newFaces = new List<Face>();
        //    foreach (Face f in this.faces)
        //    {
        //        bool remove = false;
        //        if (!otherSolid.PointIsInside(f.One))
        //        {
        //            remove = true;
        //        }
        //        if (!remove)
        //        {
        //            newFaces.Add(f);
        //        }
        //    }
        //    return new Solid(newFaces);
        //}
        //public Solid GenerateAdditionalFacesForCSG(Solid otherSolid)
        //{
        //    List<Face> tempList2 = GenerateAdditionalFaces(this.faces, otherSolid.faces, false,false);
        //    //tempList2 = GenerateAdditionalFaces(tempList2, otherSolid.faces, true, false);
        //    //tempList2 = GenerateAdditionalFaces(tempList2, otherSolid.faces, true, false);

        //    //List<Face> tempList = tempList2;// GenerateAdditionalFaces(tempList2, otherSolid.faces, false, true);


        //    List<Face> finalList = new List<Face>();
        //    foreach(Face f in tempList2)
        //    {
        //        bool found = false;
        //        foreach(Face g in finalList)
        //        {
        //            if (f.IsSameFace(g))
        //            {
        //                found = true;
        //                break;
        //            }
        //        }
        //        if (!found)
        //        {
        //            finalList.Add(f);
        //        }
        //    }
        //    return new Solid(finalList);

        //}



        //public Solid Difference(Solid other)
        //{
        //    CSG c = new CSG(this);
        //    CSG d = new CSG(other);
        //    CSG e = c.Subtract(d);
        //    return e.ToSolid();

        //}

        //public Solid GridSnap(double gridsize)
        //{
        //    List<Face> returnList = new List<Face>();
        //    for (int a = 0; a < this.faces.Count; a++)
        //    {
        //        Face faceInQuestion = this.faces[a];
        //        Vector3d one = faceInQuestion.One;
        //        Vector3d two = faceInQuestion.Two;
        //        Vector3d three = faceInQuestion.Three;

        //        one = one.GridSnap(gridsize);
        //        two = two.GridSnap(gridsize);
        //        three = three.GridSnap(gridsize);

        //        returnList.Add(new Face(one, two, three));
        //    }
        //    return new Solid(returnList);
        //}
        //public Solid PrecisionSnap(int zed)
        //{
        //    List<Face> returnList = new List<Face>();
        //    for (int a = 0; a < this.faces.Count; a++)
        //    {
        //        Face faceInQuestion = this.faces[a];
        //        Vector3d one = faceInQuestion.One;
        //        Vector3d two = faceInQuestion.Two;
        //        Vector3d three = faceInQuestion.Three;

        //        one = one.PrecisionSnap(zed);
        //        two = two.PrecisionSnap(zed);
        //        three = three.PrecisionSnap(zed);
          
        //        returnList.Add(new Face(one, two, three));
        //    }
        //    return new Solid(returnList);
        //}

        //public Solid AlignVertexes(double maxDist)
        //{

        //    List<Face> returnList = new List<Face>();
        //    for (int a = 0; a < this.faces.Count; a++)
        //    {
        //        Face faceInQuestion = this.faces[a];
        //        Vector3d one = faceInQuestion.One;
        //        Vector3d two = faceInQuestion.Two;
        //        Vector3d three = faceInQuestion.Three;

        //        one = findFirstCloseMatch(one, maxDist, returnList);
        //        two = findFirstCloseMatch(two, maxDist, returnList);
        //        three = findFirstCloseMatch(three, maxDist, returnList);
        //        returnList.Add(new Face(one, two, three));
        //    }
        //    return new Solid(returnList);
        //}
//        private Vector3d findFirstCloseMatch(Vector3d v, double maxDist, List<Face> faces)
//        {
//            Vector3d returnVertex = v;
//            double maxDistSquared = maxDist * maxDist;
//            for (int a = 0; a < faces.Count; a++)
//            {
//
//                Vector3d c1 = faces[a].One;
//                Vector3d c2 = faces[a].Two;
//                Vector3d c3 = faces[a].Three;
//                if (v.Subtract(c1).MagnitudeSquared < maxDistSquared)
//                {
//                    return c1;
//                }
//                if (v.Subtract(c2).MagnitudeSquared < maxDistSquared)
//                {
//                    return c2;
//                }
//                if (v.Subtract(c3).MagnitudeSquared < maxDistSquared)
//                {
//                    return c3;
//                }
//
//            }
//            return returnVertex;
//        }

        //public Solid SnapVertecies(int decimals)
        //{
        //    List<Face> newFaces = new List<Face>();
        //    foreach(Face f in this.faces)
        //    {
        //        newFaces.Add(f.SnapVertices(this.faces, decimals));
        //    }
        //    return new Solid(newFaces);
        //}

        //public Solid SimpleHoleFix(int decimalPlaces)
        //{
        //    if (true)
        //    {
        //        throw new NotImplementedException();
        //    }
        //    Solid x = SimpleHoleFix(this, decimalPlaces);
        //    if (x.faces.Count == this.faces.Count)
        //    {
        //        return x;
        //    }
        //    return x.SimpleHoleFix(decimalPlaces);
        //}
        //private static Solid SimpleHoleFix(Solid y, int decimalPlaces)
        //{
        //    Solid x = y.SnapVertecies(decimalPlaces);


        //    List <Line> nakedEdges = x.GetNakedEdges();

        //    List<Face> newFaces = new List<Face>();

        //    while (nakedEdges.Count > 1)
        //    {
        //        Line candidateOne = null;
        //        Line candidateTwo = null;
        //        double lastDist = Double.MaxValue;
        //        //double lastDist = candidateOne.GetCrudeDistance(candidateTwo);
        //        for (int a = 0; a < nakedEdges.Count-1; a++)
        //        {
        //            Line c1 = nakedEdges[a];
        //            for (int b = a + 1; b < nakedEdges.Count; b++)
        //            {
        //                Line c2 = nakedEdges[b];
        //                double temp = c1.GetCrudeDistance(c2);
        //                if (temp < lastDist)
        //                {
        //                    lastDist = temp;
        //                    candidateOne = c1;
        //                    candidateTwo = c2;
        //                }
        //            }

        //        }

        //        if (candidateOne.Start == candidateTwo.End)
        //        {
        //            //the othersides can't match or it wouldn't be naked.
        //            newFaces.Add(new Face(candidateOne.Start, candidateTwo.Start, candidateOne.End));
                   
        //        }
        //        else if (candidateOne.End == candidateTwo.Start)
        //        {
        //            newFaces.Add(new Face(candidateOne.End, candidateOne.Start, candidateTwo.End));
        //        }
        //        else
        //        {
        //            newFaces.Add(new Face(candidateOne.End, candidateTwo.End, candidateTwo.Start));
        //            newFaces.Add(new Face(candidateOne.End, candidateOne.Start, candidateTwo.End));
        //        }
        //        nakedEdges.Remove(candidateOne);
        //        nakedEdges.Remove(candidateTwo);

        //    }


        //    //newFaces.AddRange(x.faces);

        //    List<Face> returnFaces = x.faces;
        //    AddFacesIfNotAlreadyPresent(returnFaces, newFaces);

        //    return new Solid(returnFaces); 

        //}

//        private static void AddFacesIfNotAlreadyPresent(List<Face> faceList, List<Face> candidates)
//        {
//            foreach(Face f in candidates)
//            {
//                AddFaceIfNotAlreadyPresent(faceList, f);
//            }
//        }
//        private static void AddFaceIfNotAlreadyPresent(List<Face> faceList, Face candidate)
//        {
//            foreach(Face f in faceList)
//            {
//                if (f.IsSameFace(candidate))
//                {
//                    return;
//                }
//            }
//            faceList.Add(candidate);
//        }
//        public List<Line> GetNakedEdges()
//        {
//            if (this.faceMeta == null)
//            {
//                RefreshFaceMeta();
//            }
//            List<Line> returnList = new List<Line>();
//            foreach(FaceMeta fm in this.faceMeta)
//            {
//                returnList.AddRange(fm.GetNakedEdges());
//            }
//            return returnList;
//        }
//        public void RefreshFaceMeta()
//        {
//            this.faceMeta = FaceMeta.FromFaces(this.faces);
//        }
//        
        


//        public int CountNakedEdges()
//        {
//            if (this.faceMeta == null)
//            {
//                RefreshFaceMeta();
//            }
//            
//            int nakedEdgeCount = 0;
//            foreach(FaceMeta fm in this.faceMeta)
//            {
//                if (fm.OneTwoNeighbor == null)
//                {
//                    nakedEdgeCount++;
//                }
//                if (fm.TwoThreeNeighbor == null)
//                {
//                    nakedEdgeCount++;
//                }
//                if (fm.ThreeOneNeighbor == null)
//                {
//                    nakedEdgeCount++;
//                }
//            }
//            return nakedEdgeCount;
//        }

        ////////private static List<Face> BreakFaceOnSingleInteriorPoint(Face oldFace, Intersection i)
        ////////{

        ////////    if (oldFace.One == i.Location || oldFace.Two == i.Location || oldFace.Three == i.Location)
        ////////    {
        ////////        return new List<Face>();
        ////////    }



        ////////    List<Face> tempFaces1 = new List<Face>();
           
        ////////    if (i.IsInterior)
        ////////    {
        ////////        Face a = new Face(oldFace.One, oldFace.Two, i.Location);
        ////////        Face b = new Face(oldFace.Two, oldFace.Three, i.Location);
        ////////        Face c = new Face(oldFace.Three, oldFace.One, i.Location);
        ////////        tempFaces1.Add(a);
        ////////        tempFaces1.Add(b);
        ////////        tempFaces1.Add(c);
        ////////        //return returnFaces;
        ////////    }
        ////////    //else
        ////////    //{
        ////////    //    tempFaces1.Add(oldFace);

        ////////    //    //
        ////////    //    if (oldFace.One != i.SegmentStart && oldFace.One != i.SegmentEnd)
        ////////    //    {
        ////////    //        Face a = new Face(oldFace.One, oldFace.Two, i.Location);
        ////////    //        Face b = new Face(oldFace.One, i.Location, oldFace.Three);
        ////////    //        tempFaces1.Add(a);
        ////////    //        tempFaces1.Add(b);
        ////////    //        //return returnFaces;
        ////////    //    }
        ////////    //    if (oldFace.Two != i.SegmentStart && oldFace.Two != i.SegmentEnd)
        ////////    //    {
        ////////    //        Face a = new Face(oldFace.Two, oldFace.Three, i.Location);
        ////////    //        Face b = new Face(oldFace.Two, i.Location, oldFace.One);
        ////////    //        tempFaces1.Add(a);
        ////////    //        tempFaces1.Add(b);
        ////////    //        //return returnFaces;
        ////////    //    }
        ////////    //    if (oldFace.Three != i.SegmentStart && oldFace.Three != i.SegmentEnd)
        ////////    //    {
        ////////    //        Face a = new Face(oldFace.Three, oldFace.One, i.Location);
        ////////    //        Face b = new Face(oldFace.Three, i.Location, oldFace.Two);
        ////////    //        tempFaces1.Add(a);
        ////////    //        tempFaces1.Add(b);
        ////////    //        //return returnFaces;
        ////////    //    }
        ////////    //}
        ////////    List<Face> tempFaces2 = new List<Face>();
        ////////    foreach (Face f in tempFaces1)
        ////////    {
        ////////        if (!f.IsSameFace(oldFace))
        ////////        {
        ////////            tempFaces2.Add(f);
        ////////        }
        ////////    }
        ////////    List<Face> tempFaces3 = new List<Face>();

        ////////    for (int a = 0; a < tempFaces2.Count; a++)
        ////////    {
        ////////        Face xx = tempFaces2[a];
        ////////        bool found = false;
        ////////        for (int b = a + 1; b < tempFaces3.Count; b++)
        ////////        {
        ////////            if (xx.IsSameFace(tempFaces3[b]))
        ////////            {
        ////////                found = true;
        ////////                break;
        ////////            }
        ////////        }
        ////////        if (!found)
        ////////        {
        ////////            tempFaces3.Add(xx);
        ////////        }
        ////////    }
        ////////    return tempFaces3;
        ////////}

//        public Solid RemoveFacesInsideOtherSolid(Solid otherSolid)
//        {
//            List<Face> returnList = new List<Face>();
//            foreach(Face f in this.faces)
//            {
//                if (otherSolid.PointIsInside(f.One)|| otherSolid.PointIsInside(f.Two)||otherSolid.PointIsInside(f.Three))
//                {
//                    bool x = false;
//                    //nop
//                }
//                else
//                {
//                    returnList.Add(f);
//                }
//            }
//            return new Solid(returnList);
//        }
//        public Solid RemoveFacesOutsideOtherSolid(Solid otherSolid)
//        {
//            List<Face> returnList = new List<Face>();
//            foreach (Face f in this.faces)
//            {
//                if (otherSolid.PointIsInside(f.One) || otherSolid.PointIsInside(f.Two) || otherSolid.PointIsInside(f.Three))
//                {
//                    returnList.Add(f);
//                }
//                else
//                {
//                    //nop
//                }
//            }
//            return new Solid(returnList);
//        }

//        public boolean PointIsInside(Vector3d point)
//        {
//            int intersectionCount = 0;
//            Vector3d testOrigin1 = new Vector3d(1000, 2000, 3000);
//            foreach(Face f in this.faces)
//            {
//                Vector3d x = Util.LineIntersectsTriangle(point, testOrigin1, f);
//                if (x!=null)
//                {
//                    intersectionCount++;
//                }
//            }
//            return (intersectionCount) % 2 == 1;
//        }
        public static Solid GetOctahedron()
        {
            
                List<Face> returnFaces = new ArrayList<Face>();
                Vector3d one = new Vector3d(-1, -1, 0);
                Vector3d two = new Vector3d(1, -1, 0);
                Vector3d three = new Vector3d(1, 1, 0);
                Vector3d four = new Vector3d(-1, 1, 0);
                Vector3d five = new Vector3d(0, 0, Math.sqrt(2));
                Vector3d six = new Vector3d(0, 0, -Math.sqrt(2));

                returnFaces.add(new Face(three, four, five));
                returnFaces.add(new Face(four, three, six));

                returnFaces.add(new Face(one, two, five));
                returnFaces.add(new Face(two, one, six));

                returnFaces.add(new Face(two, three, five));
                returnFaces.add(new Face(three, two, six));

                returnFaces.add(new Face(four, one, five));
                returnFaces.add(new Face(one, four, six));

                return new Solid(returnFaces);
                //Vector one = new Vector(-1, 1, -1);
                //Vector two = new Vector(1, 1, -1);
                //Vector three = new Vector(1, -1, -1);
                //Vector four = new Vector(-1, -1, -1);
                //Vector five = new Vector(-1, 1, 1);
                //Vector six = new Vector(1, 1, 1);
            
        }


       
        //public static Solid ClipTo(Solid s)
        //{

        //}

        public Line GetBoundingBox()
        {
            double minx = Double.MAX_VALUE;
            double miny = Double.MAX_VALUE;
            double minz = Double.MAX_VALUE;
            double maxx = Double.MIN_VALUE;
            double maxy = Double.MIN_VALUE;
            double maxz = Double.MIN_VALUE;

            for (Face m : this.faces)
            {
                Line b = m.GetBoundingBox();
                if (b.start.x < minx)
                {
                    minx = b.start.x;
                }


                if (b.start.y < miny)
                {
                    miny = b.start.y;
                }

                if (b.start.z < minz)
                {
                    minz = b.start.z;
                }



                if (b.end.x > maxx)
                {
                    maxx = b.end.x;
                }


                if (b.end.y > maxy)
                {
                    maxy = b.end.y;
                }

                if (b.end.z > maxz)
                {
                    maxz = b.end.z;
                }



            }
            return new Line(new Vector3d(minx, miny, minz), new Vector3d(maxx, maxy, maxz));
        }

        public static Solid GetSimpleCube()
        {
            
            


            List<Face> returnFaces = new ArrayList<Face>();
            Vector3d one = new Vector3d(-1, 1, -1);
            Vector3d two = new Vector3d(1, 1, -1);
            Vector3d three = new Vector3d(1, -1, -1);
            Vector3d four = new Vector3d(-1, -1, -1);

            Vector3d five = new Vector3d(-1, 1, 1);
            Vector3d six = new Vector3d(1, 1, 1);
            Vector3d seven = new Vector3d(1, -1, 1);
            Vector3d eight = new Vector3d(-1, -1, 1);

            returnFaces.add(new Face(one, two, four));
            returnFaces.add(new Face(four, two, three));

            returnFaces.add(new Face(five, eight, six));
            returnFaces.add(new Face(six, eight, seven));

            returnFaces.add(new Face(one, five, two));
            returnFaces.add(new Face(two, five, six));

            returnFaces.add(new Face(seven, eight, four));
            returnFaces.add(new Face(seven, four, three));

            returnFaces.add(new Face(four, eight, one));
            returnFaces.add(new Face(one, eight, five));//

            returnFaces.add(new Face(six, seven, two));
            returnFaces.add(new Face(two, seven, three));

            return new Solid(returnFaces);
        }
        
}
