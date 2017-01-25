/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos_prj_2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import jdk.nashorn.internal.objects.NativeString;

/**
 *
 * @author Nikitha Mahesh
 */
public class Hello extends UnicastRemoteObject implements HelloInterface    
{       

    
   private ArrayList<FileDetails> Files;
    List<String> tmpFileList= new ArrayList<String>();
   //private ArrayList<FileDetails> FilesMatched;
   public Hello() throws RemoteException    
   {    
      super();    
     Files=new ArrayList<FileDetails>(); 
    
   }    
   
 
    
     public synchronized void registerFiles(String peerId, String fileName,String portno,String srcDir, int fileNct) throws RemoteException {
          String tempPeerId=null;
        String tempfileName=null;
        String tempPortId=null;
        String tempSourceDirectoryName=null;
       
        
        tempPeerId=peerId;
        tempfileName=fileName;
        tempPortId=portno;
        tempSourceDirectoryName=srcDir;
        tmpFileList.add(fileName);
        if(tmpFileList.size()==fileNct)
        {
        FileDetails fd = new FileDetails();
        fd.peerId=tempPeerId;
        fd.FileName=tempfileName;
        fd.portNumber=tempPortId;
        fd.SourceDirectoryName=tempSourceDirectoryName;
        fd.files.addAll(tmpFileList);
    	this.Files.add(fd);
    
        // System.out.println("File name"+" "+fd.FileName+"registered with peerID"+" "+fd.peerId+"on port number"+fd.portNumber+"and the directory is"+fd.SourceDirectoryName);
        //getClientDetail(this.chatClients);
        tmpFileList=new ArrayList<String>();
     }  
         //System.out.println("message sent to "+this.Files.size());
     }

    
    public ArrayList<FileDetails> search(String messageID,Integer timeToLive,String filename) throws RemoteException {
         ArrayList<FileDetails> FilesMatched= new ArrayList<FileDetails>();
         boolean matchCntFlg=false;
        System.out.println("File to be searched is "+filename);
       String seq= messageID.substring(messageID.indexOf("+")+1, messageID.length());
        System.out.println("Sequence Number  is "+seq);
        for(int i =0;i<this.Files.size();i++)
        {
            if(this.Files.get(i).messageID!=null)
            {
            if(this.Files.get(i).messageID.contains(seq))
            {   
                System.out.println("Message already present");
                }
            else
            {
               //System.out.println("i is "+i);
             System.out.println("this.Files.get(i).files.size() is "+this.Files.get(i).files.size());
         for(int j=0;j<this.Files.get(i).files.size();j++)
         {
             //System.out.println("j is "+j);
             //System.out.println("file name is "+this.Files.get(i).files.get(j));
             if(filename.equalsIgnoreCase(this.Files.get(i).files.get(j)))
             {
                timeToLive--;
                matchCntFlg=true;
                 System.out.println("the files match");
                 FileDetails matchedFile = new FileDetails();
               matchedFile.messageID=messageID;
               matchedFile.timeToLive=timeToLive;
                matchedFile.FileName=filename;
                matchedFile.peerId=Files.get(i).peerId;
                matchedFile.portNumber=Files.get(i).portNumber;
                matchedFile.SourceDirectoryName=Files.get(i).SourceDirectoryName;
                FilesMatched.add(matchedFile);
                System.out.println("Time to Live"+timeToLive);
                System.out.println("Peer ID containg the file is"+Files.get(i).peerId);
                System.out.println("Port number of the peer"+Files.get(i).portNumber);
                //break;
             }
         }
         if(!matchCntFlg)
         {
             timeToLive--;
             System.out.println("Time to Live"+timeToLive);
         }
            }
        }
            else
            {
                            //System.out.println("i is "+i);
             //System.out.println("this.Files.get(i).files.size() is "+this.Files.get(i).files.size());
         for(int j=0;j<this.Files.get(i).files.size();j++)
         {
             //System.out.println("j is "+j);
             //System.out.println("file name is "+this.Files.get(i).files.get(j));
             if(filename.equalsIgnoreCase(this.Files.get(i).files.get(j)))
             {
                timeToLive--;
                matchCntFlg=true;
                 System.out.println("File match found");
                 FileDetails matchedFile = new FileDetails();
               matchedFile.messageID=messageID;
               matchedFile.timeToLive=timeToLive;
                matchedFile.FileName=filename;
                matchedFile.peerId=Files.get(i).peerId;
                matchedFile.portNumber=Files.get(i).portNumber;
                matchedFile.SourceDirectoryName=Files.get(i).SourceDirectoryName;
                FilesMatched.add(matchedFile);
                System.out.println("Time to Live"+timeToLive);
                System.out.println("Peer ID containg the file is"+Files.get(i).peerId);
                System.out.println("Port number of the peer"+Files.get(i).portNumber);
                //break;
             }
         }
         if(!matchCntFlg)
         {
             timeToLive--;
             System.out.println("Time to Live"+timeToLive);
         }
            }
        }
       
        System.out.println("Count of the number of files matched"+FilesMatched.size());
       return (FilesMatched); 
    }

    //@Override
    public void downloadFromPeer(String peerid, ArrayList<FileDetails> arr, String destDir) throws RemoteException {
        //get port
  String portForAnotherClient=null;
  String sourceDir=null;
  String fileTobeSearched=null;
  for(int i=0;i<arr.size();i++){
      //System.out.println("Port Number"+arr.get(i).portNumber);
     // System.out.println("Peer id"+peerid);
      if(peerid.equalsIgnoreCase(arr.get(i).peerId)){
          // System.out.println("matches");
          portForAnotherClient=arr.get(i).portNumber;
          //System.out.println("Port Number"+arr.get(i).portNumber);
         // System.out.println("Source Directory Name is"+arr.get(i).SourceDirectoryName);
          sourceDir=arr.get(i).SourceDirectoryName;
          fileTobeSearched=arr.get(i).FileName;
          try {
           HelloClient peerServer = (HelloClient) Naming.lookup("rmi://localhost:"+portForAnotherClient+"/FileServer");
           //String filetoDownload=peerServer.
       } catch (NotBoundException ex) {
           Logger.getLogger(Hello.class.getName()).log(Level.SEVERE, null, ex);
       } catch (MalformedURLException ex) {
           Logger.getLogger(Hello.class.getName()).log(Level.SEVERE, null, ex);
       }
      }
      
  }
       
       

  String source = sourceDir+"\\"+fileTobeSearched;
  //System.out.println("source "+source);
       //directory where file will be copied
       String target =destDir;
      
        InputStream is = null;
    OutputStream os = null;
    try {
        File srcFile = new File(source);
        File destFile = new File(target);
        //System.out.println("file "+destFile);
        if(!destFile.exists())
        {
            destFile.createNewFile();
        }
        is = new FileInputStream(srcFile);
        
        os = new FileOutputStream(target+"\\"+srcFile.getName());
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
    } 
      catch(Exception e)
            {
            e.printStackTrace();
            }
    finally {
      try {
          is.close();
      } catch (IOException ex) {
          Logger.getLogger(Hello.class.getName()).log(Level.SEVERE, null, ex);
      }
      try {
          os.close();
      } catch (IOException ex) {
          Logger.getLogger(Hello.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    }

   
  
   
}    
