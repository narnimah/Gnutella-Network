/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos_prj_2;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.exit;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikitha Mahesh
 */
public class ThreadOfHelloServer implements Runnable{
  public static  GetPropertyValues properties= new GetPropertyValues();  
    public void run()  
   { 
           long responseTime = 0;
            long endTime=0;
            long avgResponseTime=0;
     try   
      {
           String fileTobeSearched=null;
          Integer sequenceID=0;
          String peerID=null;
          String messageID=null;
          Integer tTL=null;
          String tmpPrID=null;
          String destDir=null;
         System.out.println("Total Number of nodes in the network are " + properties.finalList.size());   
          LocateRegistry.createRegistry(Integer.parseInt(properties.finalList.get(0).getPort_no()));
          HelloInterface hello = new Hello();    
         Naming.rebind("Hello", hello)  ; 
            
         System.out.println("Ready to send Query Message"); 
          System.out.println("Sending query message to neighbouring nodes of Peer :1");
         // set the parameters of the server for file search
         tTL=properties.finalList.get(0).getNeighbour_id();
         peerID=properties.finalList.get(0).getPeer_id();
         destDir=properties.finalList.get(0).getFile_name();
         //System.out.println("destDir is "+destDir);
         
         // register the clients
         //System.out.println("value properties.finalList.size() "+properties.finalList.size());
            for(int i=1;i<properties.finalList.size();i++)
         {
            //  System.out.println("value of i is "+i);
         LocateRegistry.createRegistry(Integer.parseInt(properties.finalList.get(i).getPort_no())); 
         HelloClient fi = new FileImpl(properties.finalList.get(i).getFile_name());
            System.out.println("Sending query message to neighbouring nodes of Peer :"+properties.finalList.get(i).getPeer_id());
         Naming.rebind("rmi://localhost:"+properties.finalList.get(i).getPort_no()+"/FileServer", fi);
        
         
         // register the files to the directory
          File directoryList = new File(properties.finalList.get(i).getFile_name());
            String[] store = directoryList.list();
            int counter=0; 
            while(counter<store.length)
                   {
                   File currentFile = new File(store[counter]);
                           try {
                               
                               hello.registerFiles(properties.finalList.get(i).getPeer_id(), currentFile.getName(),properties.finalList.get(i).getPort_no(),properties.finalList.get(i).getFile_name(),store.length);
                           } catch (RemoteException ex) {
                               //Logger.getLogger(ThreadOfHelloClient.class.getName()).log(Level.SEVERE, null, ex);
                           }
                           counter++;
                 } 
            
            
         }
             
            // Search for file - peer ID 1 - Star Topology
            ArrayList<FileDetails> arr = new ArrayList<FileDetails>();
            fileTobeSearched="test_cases.docx";
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String fileSrchOption=null;
            System.out.print("Do you want to search for a file- Yes or No\n");
         try {
             fileSrchOption = br.readLine();
         } catch (IOException ex) {
             Logger.getLogger(ThreadOfHelloServer.class.getName()).log(Level.SEVERE, null, ex);
         }
            if(fileSrchOption.equalsIgnoreCase("Yes"))
            {
                sequenceID++;
                        messageID=peerID.concat("+").concat(sequenceID.toString());
               try {
                   arr=hello.search(messageID, tTL, fileTobeSearched);
                   System.out.println("Following are the hit query messages");
                             for(int i = 0; i < arr.size(); i++) {
            System.out.println("Hit query message are"+"\n"+"PeerID "+arr.get(i).peerId+"\t"+"Port Number "+arr.get(i).portNumber+"\t"+"FileName "+arr.get(i).FileName+"\t"+"Message ID is"+arr.get(i).messageID+"\t"+"Time to Live"+arr.get(i).timeToLive);
        }
                   for(int i = 0; i < arr.size(); i++) {
            System.out.println("Peer ID's having the given file are"+arr.get(i).peerId);
        }
                   //hello.downloadFromPeer(peerID, arr);
               } catch (RemoteException ex) {
                   Logger.getLogger(ThreadOfHelloServer.class.getName()).log(Level.SEVERE, null, ex);
               }
          
           BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
            String PeerToDwnld=null;
            System.out.println("Enter the peer ID you want to connect and download the file from");
               try {
                   PeerToDwnld = br1.readLine();
               } catch (IOException ex) {
                   Logger.getLogger(ThreadOfHelloServer.class.getName()).log(Level.SEVERE, null, ex);
               }
               try {
                   hello.downloadFromPeer(PeerToDwnld,arr,destDir);
                   System.out.println("File downloaded successfully");
                   String option=null;
                  System.out.println("Do you want to continue searching Yes or No\n");
                   BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
                   option=br2.readLine();
                    if(fileSrchOption.equalsIgnoreCase("Yes"))
                    {
                             sequenceID++;
                       messageID=peerID.concat("+").concat(sequenceID.toString());
                                    try {
                                        arr = new ArrayList<FileDetails>();
                   arr=hello.search(messageID, tTL, fileTobeSearched);
                   System.out.println("Following are the hit query messages");
                             for(int i = 0; i < arr.size(); i++) {
            System.out.println("Hit query message are"+"\n"+"PeerID"+arr.get(i).peerId+"Port Number"+arr.get(i).portNumber+"FileName is"+arr.get(i).FileName+"Message ID is"+arr.get(i).messageID+"Time to Live"+arr.get(i).timeToLive);
        }
                   for(int i = 0; i < arr.size(); i++) {
           // System.out.println("Peer ID's having the given file are"+arr.get(i).peerId);
        }
                   //hello.downloadFromPeer(peerID, arr);
               } catch (RemoteException ex) {
                   Logger.getLogger(ThreadOfHelloServer.class.getName()).log(Level.SEVERE, null, ex);
               }
                                    try
                                    {
                                         BufferedReader br3 = new BufferedReader(new InputStreamReader(System.in));
                                 String peerDwnld=null;
                                    System.out.println("Enter the peer ID you want to connect and download the file from");
                                     peerDwnld = br3.readLine();
                                     
                                     hello.downloadFromPeer(peerDwnld,arr,destDir);
                   System.out.println("File downloaded successfully");
                   System.out.println("Computing the avg response time");
                   
                   // Measure the avg response time
                    //long responseTime = 0;
            //long endTime=0;
                   for(int i=0;i<100;i++)
                   {
                        long start = System.currentTimeMillis(); 
                   arr = new ArrayList<FileDetails>();
                   arr=hello.search(messageID, tTL, fileTobeSearched);
                   for(int j = 0; j < arr.size(); j++) {
                       hello.downloadFromPeer(peerDwnld,arr,destDir);
                       endTime= System.currentTimeMillis()-start;
                responseTime=responseTime+ endTime;
                   }
                 //System.out.println("trackingCount"+i);
               //System.out.println("responseTime is"+responseTime+"ms");
         avgResponseTime=responseTime/1000;
        //System.out.println("avgResponseTime is"+avgResponseTime+"ms");
                 }
                   System.out.println("responseTime is"+responseTime+"ms");
                   System.out.println("avgResponseTime is"+avgResponseTime+"ms");
                    exit(0);
                                     
                                    }catch(Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                        
                    }
                    else
                    {
                        exit(0);
                    }
                   
                   
               } catch (RemoteException ex) {
                   Logger.getLogger(ThreadOfHelloServer.class.getName()).log(Level.SEVERE, null, ex);
               }
            }
            else
            {
                System.out.print("Thank you !");
            }
    
         }     catch (Exception e)    
      {   
          e.printStackTrace();
         // System.out.println("Hello Server failed: " + e.printStackTrace());    
      }    
    
     
    
      
   }  
   public static void main(String [] args) throws IOException    
   {    
	properties.getPropValues();
       new ThreadOfHelloServer().run();
         

   } 


    
}
