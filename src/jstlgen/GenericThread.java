/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen;

/**
 *
 * @author Christopher.Miller
 */

    
    public class GenericThread extends java.lang.Thread
    {
        
        //public java.lang.Thread t;
        //public System.Threading.Thread t;
        //public delegate void WorkDelegate();
        public boolean exit = false;
        public ThreadBase threadbase = null;
        //public delegate void DoneDelegate();
        //public DoneDelegate doneDelegate = null;


        public GenericThread()
        {
            this.setDaemon(true);
            //this.start();
            
//            t = new System.Threading.Thread(new System.Threading.ThreadStart(LookForWorkAndSleep) );
//            t.IsBackground = true;
//            t.Start();
            
        }

        
        public void Go(){
            this.start();
        }
//        public void Wake()
//        {
//            this.interrupt();
//        }
        @Override
        public synchronized void run()
        {
//            while (!exit)
//            {
//                if (threadbase == null)
//                {
//                    try{
//                        this.wait();
//                    }
//                    catch(java.lang.InterruptedException err){
//                        
//                    }
//                        //System.Threading.Thread.Sleep(100000000);
//                    
//                }
//                else
//                {
                    threadbase.DoWork();
                   
                    threadbase.AlertDone();
                        threadbase = null;
//                }
//            }
        }
            
    }
  


