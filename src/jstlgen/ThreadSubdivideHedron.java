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
public class ThreadSubdivideHedron extends ThreadBase{
    
       

        //int threadCount;
        //int threadNumber;
        //public bool Done = false;
        //public List<FractalHedron> results = new ArrayList<FractalHedron>();
        List<FractalHedron> hedronPool = null;
        SignedDistanceField3d sdf;
        public int cullCount = 0;
        double lineLength;
        double epsilon;

        boolean finalCull = false;

        //public GenericThread t = null;




        //public static GenericThread GetAvailableThread(GenericThread.WorkDelegate d)
        //{
        //    lock (threads)
        //    {
        //        foreach (GenericThread t in threads)
        //        {
        //            if (t.workDelegate == null)
        //            {
        //                t.workDelegate = d;
        //                t.Wake();
        //                return t;
        //            }
        //        }
        //    }
        //    return null;
        //}
        
        public static List<FractalHedron> HedronSubdivide(SignedDistanceField3d sdf, List<FractalHedron> hedronPool, double epsilon, int threadCount, double lineLength,  boolean finalCull)
        {
            
            activeThreads = new ArrayList<ThreadBase>();
            toAwaken = Thread.currentThread();

            List<FractalHedron> temp = new ArrayList<FractalHedron>();
            for (int a = 0; a < threadCount; a++)
            {
                activeThreads.add(new ThreadSubdivideHedron(threadCount, a, hedronPool, sdf, lineLength, epsilon, finalCull));
                //work begins immediately
            }
            
            for (ThreadBase t : activeThreads){
                t.Join();
            }
            
            //for (ThreadSubdivideHedron s in )
           
//            System.out.println("******************************************");
//            //}
           
            for (ThreadBase g : activeThreads)
            {
                temp.addAll((List<FractalHedron>)(g.results));
            }
            
            return temp;

        }

        public ThreadSubdivideHedron(int threadCount, int threadNumber, List<FractalHedron> hedronPool, SignedDistanceField3d sdf, double lineLength, double epsilon, boolean finalCull)
        {
            
            this.hedronPool = new ArrayList<FractalHedron>();
            for (int a = threadNumber; a < hedronPool.size(); a += threadCount)
            {
                this.hedronPool.add(hedronPool.get(a));//perhaps an unnessessary copy
            }
            //this.hedronPool.AddRange(hedronPool);
            //this.hedronPool = hedronPool;
            this.threadCount = threadCount;
            this.threadNumber = threadNumber;
            this.sdf = sdf.Clone();
            this.epsilon = epsilon;
            this.lineLength = lineLength;
            this.finalCull = false;
            //t = new System.Threading.Thread(new System.Threading.ThreadStart(SubdivideHedron));
            //t.Priority = System.Threading.ThreadPriority.Highest;
            //t.Start();
            GetAvailableThread(this);//this actually starts the thread on doing work below...
        }

        
        public void DoWork()
        {
            
            if (!finalCull)
            {

                List<FractalHedron> tempOcts = new ArrayList<FractalHedron>();
                //for (int a = threadNumber; a < hedronPool.Count; a += threadCount)
                //{
                for(FractalHedron ff : hedronPool) { 
                    //FractalHedron ff = hedronPool[a];
                    int state = ff.GetSpanningState(sdf, lineLength, epsilon);
                    //don't bother to subdivide if it's far from the object...
                    if (state * state != 4)
                    {
                        List<FractalHedron> candidates = ff.Subdivide();
                        for (FractalHedron fff : candidates)
                        {
                            state = fff.GetSpanningState(sdf, lineLength / 2, epsilon);
                            if (state * state != 4)
                            {
                                tempOcts.add(fff);
                            }
                            else
                            {
                                cullCount++;
                            }
                            //tempOcts.AddRange(ff.Subdivide());
                        }
                    }
                    else
                    {
                        cullCount++;
                    }
                }
                this.results = tempOcts;
            }
            else
            {
                List<FractalHedron> tempOcts = new ArrayList<FractalHedron>();
                //for (int a = threadNumber; a < hedronPool.Count; a += threadCount)
                //{
                for (FractalHedron ff : hedronPool)
                {
                    //FractalHedron ff = hedronPool[a];
                    if (ff.GetSpanningState(sdf, 0, epsilon) == 0)
                    {
                        tempOcts.add(ff);
                    }
                    else
                    {
                        cullCount++;
                    }
                }
                this.results = tempOcts;
            }
            
            //System.Diagnostics.Debug.WriteLine("thread " + threadNumber + ":" + sw.ElapsedMilliseconds);
            Done = true;
            return;

        }
}
