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
public abstract class ThreadBase {
    protected int threadCount;

   
    public abstract void DoWork();

    
    
    public static final Object syncLock = new Object();
    //public abstract void AlertDone();
    
    protected int threadNumber;
    //public object result;
    protected static List<GenericThread> threads = new ArrayList<GenericThread>();

    public boolean Done = false;
    private static boolean initialized = false;

    
    static List<ThreadBase> activeThreads=null;
    static java.lang.Thread toAwaken;
    
    public Object results;

//    private static synchronized void prep()
//    {
//        if (initialized){
//            return;
//        }    
//        for (int a = 0; a < 48; a++)
//        {
//            GenericThread t = new GenericThread();
//            threads.add(t);
//            t.Go();
//        }
//        initialized=true;
//        return;
//    }

    public void Join(){
        try{
            currentThread.join();
        }
        catch(java.lang.InterruptedException err){}
//        }
//        catch(java.lang.InterruptedException e){
//            System.out.println(e.getMessage());
//        }
        
    }
   
//    public static void KillThreads()
//    {
//        if (threads != null)
//        {
//            for (GenericThread t : threads)
//            {
//                t.exit = true;
//                t.Wake();
//            }
//        }
//
//    }

    
    private GenericThread currentThread = null;
    
    public static synchronized GenericThread GetAvailableThread(ThreadBase tb)
    {
//        prep();
//
//        for (GenericThread t : threads)
//        {
//            if (t.threadbase == null)
//            {
//                tb.currentThread=t;
//                t.threadbase = tb;
//                t.Wake();
//                return t;
//            }
//        }

        //return null;
        GenericThread t = new GenericThread();
        t.threadbase = tb;
        t.start();
        tb.currentThread = t;
        return t;
    }
    
    public void AlertDone() {
        boolean done = true;
        for (ThreadBase g : activeThreads)
        {

            // g.t.Join();
            if (!g.Done)
            {
                done = false;
                break;
            }
        }

        if (done)
        {
            toAwaken.interrupt();
        }
    }
    

}
