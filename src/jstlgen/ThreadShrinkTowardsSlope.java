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
public class ThreadShrinkTowardsSlope extends ThreadBase{
     //private bool done = false;
        //public bool Done
        //{
        //    get
        //    {
        //        return done;
        //    }
        //}
        //public bool Done = false;
        //public Face[] results = null;
        public List<Face> results = new ArrayList<Face>();
        Face[] facePool = null;
        SignedDistanceField3d sdf;
        double epsilon;
        
      
        //public System.Threading.Thread t = null;


     

        //private static List<GenericThread> threads = new List<GenericThread>();

        //static ThreadShrinkTowardsSlope()
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

        
        
        public static void ShrinkTowardsSlopeNoBurn(SignedDistanceField3d sdf, double epsilon, int threadCount, Face[] faces)
        {
            //List<Face> temp = new ArrayList<Face>();

            activeThreads = new ArrayList<ThreadBase>();
            toAwaken = Thread.currentThread();

            for (int a = 0; a < threadCount; a++)
            {
                activeThreads.add(new ThreadShrinkTowardsSlope(threadCount, a, faces, sdf, epsilon));
            }

            try
            {
                Thread.currentThread().wait();
            }
            catch(java.lang.InterruptedException wake)
            {

            }
            //bool done = false;
            //while (!done)
            //{
                
            //    done = true;
            //    foreach (ThreadShrinkTowardsSlope g in shrinkTowardsSlopThreads)
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

            //foreach (ShrinkTowardsSlopeThread g in threads)
            //{
            //    temp.AddRange(g.results);
            //}

            //return new Solid(temp);
            //return this;

        }
        

//        public ThreadShrinkTowardsSlope(int threadCount, int threadNumber, List<Face> facePool, SignedDistanceField3d sdf, double epsilon, boolean noBurn)
//        {
//
//            //this.facePool = new List<Face>();
//            //this.facePool.AddRange(facePool);
//            this.facePool = facePool.toArray(this.facePool);
//
//            //this.facePool = new List<Face>();
//            //for (int a = threadNumber; a < facePool.Count; a += threadCount)
//            //{
//            //    this.facePool.Add(facePool[a]);
//            //}
//
//            this.threadCount = threadCount;
//            this.threadNumber = threadNumber;
//            this.sdf = sdf.Clone();
//            this.epsilon = epsilon;
//            this.noBurn = noBurn;
//            GetAvailableThread(this);//this actually starts the thread too.
//            //t = new System.Threading.Thread(new System.Threading.ThreadStart(ShrinkTowardsSlope));
//            //t.Priority = System.Threading.ThreadPriority.Highest;
//            //t.Start();
//
//
//        }

        
        //public static void RunMultipleThreads(int threadCount, int threadNumber, Face[] facePool, SignedDistanceField3d sdf, double epsilon, bool noBurn)
        //{
        //    List<ThreadShrinkTowardsSlope> threads = new List<ThreadShrinkTowardsSlope>();
        //    for (int a = 0; a < threadCount; a++)
        //    {
        //        threads.Add(new ThreadShrinkTowardsSlope(threadCount, a, facePool, sdf, epsilon, true));
        //    }

        //    //bool done = false;
        //    //while (!done)
        //    //{

        //    //    done = true;
        //    //    foreach (ThreadShrinkTowardsSlope g in threads)
        //    //    {

        //    //        // g.t.Join();
        //    //        if (!g.Done)
        //    //        {
        //    //            done = false;
        //    //            break;
        //    //        }
        //    //    }
        //    //    System.Threading.Thread.Sleep(100);
        //    //}

        //}

        public ThreadShrinkTowardsSlope(int threadCount, int threadNumber, Face[] facePool, SignedDistanceField3d sdf, double epsilon)
        {

            //this.facePool = new List<Face>();
            //this.facePool.AddRange(facePool);
            this.facePool = facePool;

            //this.facePool = new List<Face>();
            //for (int a = threadNumber; a < facePool.Count; a += threadCount)
            //{
            //    this.facePool.Add(facePool[a]);
            //}

            this.threadCount = threadCount;
            this.threadNumber = threadNumber;
            this.sdf = sdf.Clone();
            this.epsilon = epsilon;
            
            GetAvailableThread(this);//this actually starts the thread too.
            //t = new System.Threading.Thread(new System.Threading.ThreadStart(ShrinkTowardsSlope));
            //t.Priority = System.Threading.ThreadPriority.Highest;
            //t.Start();


        }
        @Override
        public void DoWork()
        {
            int threadCount = this.threadCount;
            int threadNumber = this.threadNumber;
            Face[] facePool = this.facePool;
            int length = facePool.length;
            
            // System.Diagnostics.Debug.WriteLine("Thread " + threadNumber + " started...");
            for (int a = threadNumber; a < length; a += threadCount) {
                //List<Face> facePool = this.facePool;

                //foreach (Face ff in facePool)
                //{
                Face ff = facePool[a];



                //Vector3d slope1 = sdf.Slope(ff.One, epsilon);
                //Vector3d normal1 = slope1.UnitVector;
                //double dist1 = sdf.GetDistance(ff.One) * (1 - epsilon);


                //Vector3d slope2 = sdf.Slope(ff.Two, epsilon);
                //Vector3d normal2 = slope2.UnitVector;
                //double dist2 = sdf.GetDistance(ff.Two) * (1 - epsilon);

                //Vector3d slope3 = sdf.Slope(ff.Three, epsilon);
                //Vector3d normal3 = slope3.UnitVector;
                //double dist3 = sdf.GetDistance(ff.Three) * (1 - epsilon);

                ////bool inside = dist < 0;
                ////if (inside)
                ////{
                ////    string brk = "";
                ////}
                ////dist = Math.Abs(dist);//since the normal changes direction too, we don't want to double negate.
                ////doesn't seem to work inside at all - at least not for a torus test.
                //Vector3d scaledMove1 = normal1.Scale(dist1).Negate();//we want to move in the opposite direction of the normal.
                //Vector3d scaledMove2 = normal2.Scale(dist2).Negate();//we want to move in the opposite direction of the normal.
                //Vector3d scaledMove3 = normal3.Scale(dist3).Negate();//we want to move in the opposite direction of the normal.


                // Face newFace = new Face(scaledMove1, scaledMove2, scaledMove3);



                //Face newFace = new Face(ff.One.ShrinkTowardsSlope(sdf, epsilon), ff.Two.ShrinkTowardsSlope(sdf, epsilon), ff.Three.ShrinkTowardsSlope(sdf, epsilon));
//                if (noBurn)
//                {
                    ff.ShrinkTowardsSlopeNoBurn(sdf, epsilon);
                    //results.Add(ff);
                    //already in the array it needs to be in.
//                }
//                else
//                {
//                    Face newFace = ff.ShrinkTowardsSlope(sdf, epsilon);
//                    results.add(newFace);
//                }
            }
            //}
            
            Done = true;
            //System.Diagnostics.Debug.WriteLine("Thread " + threadNumber + " done.");

        }

    
//    public void DoWork() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    
    
}
