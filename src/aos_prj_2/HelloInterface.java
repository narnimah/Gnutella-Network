/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos_prj_2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Nikitha Mahesh
 */
public interface HelloInterface extends Remote{

 public void registerFiles(String peerId, String filename,String portno,String srcDir, int fileCnt)throws RemoteException; 
 public ArrayList<FileDetails> search(String messaheID,Integer timeToLive,String filename)throws RemoteException; 
 public void downloadFromPeer(String peerid,ArrayList<FileDetails> arr,String destDir) throws  RemoteException;
}
