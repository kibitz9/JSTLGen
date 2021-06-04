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
public class ThreadSideSplitStressedFaces extends ThreadBase{

    //public Face[] results2 = null;
    
    Face[] facePool = null;
    SignedDistanceField3d sdf;
    double epsilon;
    double threshold;
    
    
    
    
    public static Face[] SideSplitStressedFaces(SignedDistanceField3d sdf,  double epsilon, int threadCount, Face[] facePool,  double threshold)
    {
        //List<Face> temp = new ArrayList<Face>();

        activeThreads = new ArrayList<ThreadBase>();
        toAwaken = Thread.currentThread();

        for (int a = 0; a < threadCount; a++)
        {
            activeThreads.add(new ThreadSideSplitStressedFaces(threadCount, a, facePool, sdf, epsilon, threshold));
        }

        for (ThreadBase t : activeThreads){
            while(true){

                t.Join();

                if (t.Done){
                    break;
                }

            }
        }
        
        int faceCount=0;
        for (ThreadBase g : activeThreads)
        {
            faceCount+=((Face[])g.results).length;
        }
        Face[] returnFaces=new Face[faceCount];
        
        int index=0;        
        for (ThreadBase g : activeThreads)
        {
            Face[] threadFaces = (Face[]) g.results;
            
            for (int a=0;a<threadFaces.length;a++){
                returnFaces[index++]=threadFaces[a];
            }
            
        }
        return returnFaces;
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
    public ThreadSideSplitStressedFaces(int threadCount, int threadNumber, Face[] facePool, SignedDistanceField3d sdf, double epsilon, double threshold)
      {

          //this.facePool = new List<Face>();
          //this.facePool.AddRange(facePool);
          this.facePool = facePool;
          this.threshold = threshold;
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
         


      }
    
    
    @Override
    public void DoWork() {
        int threadCount = this.threadCount;
        Face[] facePool = this.facePool;
        //List<Face> newFaces = new List<Face>();
        Face[] newFaces = new Face[((facePool.length / threadCount) + threadCount) * 4];//up this if more splits possible
        // System.Diagnostics.Debug.WriteLine("Thread " + threadNumber + " started...");
        //int count = 0;
        int index = 0;


        for (int a = threadNumber; a < facePool.length; a += threadCount) {
           
            Face ff = this.facePool[a];



            Face[] temp = ff.SideSplitOnTension(sdf, epsilon, threshold);
            //count += (temp.length - 1);
            //newFaces.AddRange(temp);
            for (int aaa = 0; aaa < temp.length; aaa++)
            {

                newFaces[index++] = temp[aaa];
            }
        }
       
        
        
        //index holds the size of our new array
        Face[] tempFaces = new Face[index];
        for (int ccc=0;ccc<index;ccc++){
            tempFaces[ccc]=newFaces[ccc];
        }
        this.results=tempFaces;
        
        //results = newFaces;//new Face[index];
//        for (int ccc = 0; ccc < index; ccc++)
//        {
//            results[ccc] = newFaces[ccc];
//        }
       
        //this.FacesAdded = count;
        this.Done = true;
    }
    
}
