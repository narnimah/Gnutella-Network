/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos_prj_2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 * @author Nikitha Mahesh
 */
class GetPropertyValues{

   String result = "";
	InputStream inputStream;
        ArrayList<GetPropertyValues> listOfPeers = new ArrayList<GetPropertyValues>();
        ArrayList<IntialisingPeerDetails> finalList=new ArrayList<IntialisingPeerDetails>();
        
 Scanner readIndividualFile = null;
    public ArrayList<IntialisingPeerDetails> getPropValues() throws IOException
        {
            
          ArrayList<IntialisingPeerDetails> tokenzisedList = new ArrayList<IntialisingPeerDetails>();

              int busCount=0;
                        
                        String filepath="C:\\Users\\Nikitha Mahesh\\Documents\\mesh_topology_config.txt";
                    readIndividualFile = new Scanner(new File(filepath));

                     while((readIndividualFile.hasNextLine())) {

                           String sb = readIndividualFile.nextLine();

                           if (!sb.startsWith("*") && sb.length() != 0) {

                                  if (!sb.contains("Peer")) 
                                  
                                   {

                                         tokenzisedList = getTokenzisedList (sb);

                                         //System.out.println("bus ID count is"+busCount);

                                         busCount++;    
                                    }
                           }
                           
                           
                     }   
                           readIndividualFile.close();

             // getAgents(tokenzisedList); 
              
           
       return tokenzisedList;     

       }
                           
              private  ArrayList<IntialisingPeerDetails> getTokenzisedList(String sb) {

              

              String[] tokens = sb.split("\\t");

              //System.out.println("array length is" + tokens.length);

              ArrayList<String> extractedList = new ArrayList<String>(

                           Arrays.asList(tokens));

              ArrayList<String> tmpList = new ArrayList<String>();

              for (int i = 0; i < extractedList.size(); i++) {

                     if (extractedList.get(i).length() != 0) {

 

                           tmpList.add(extractedList.get(i));

                     }

              }

              //System.out.println("temp list is /;/;/;"+tmpList.size());

 
              IntialisingPeerDetails p1 =new IntialisingPeerDetails();
              //System.out.println("Configuration Details of the Peers are as follows");
              System.out.println("Peer ID :"+tmpList.get(0));
              p1.setPeer_id(tmpList.get(0));
              System.out.println("Port Number :"+tmpList.get(1));
              p1.setPort_no(tmpList.get(1));
               System.out.println("Directory Path :"+tmpList.get(2));
              p1.setFile_name(tmpList.get(2));
              // tkenize the beighbouring list
              getNeighbours(tmpList.get(3));
              //System.out.println("tmpList.get(3)"+tmpList.get(3));
              p1.setNeighbour_id(getNeighbours(tmpList.get(3)).size());
              p1.setNumberOfNeig(getNeighbours(tmpList.get(3)));
              for(int i=0;i<getNeighbours(tmpList.get(3)).size();i++)
              {
                  System.out.println("Neighbouring node is"+getNeighbours(tmpList.get(3)).get(i));
              }
              
            

              
             finalList.add(p1);

              //System.out.println("Number of nodes in the network is"+finalList.size());

              return finalList;
              
              }
              
     public ArrayList<String> getNeighbours(String ngh)
     {
        
         String[] tokens = ngh.split(",");

              //System.out.println("array length is" + tokens.length);

              ArrayList<String> neighbrList = new ArrayList<String>( Arrays.asList(tokens));
              
             

     return neighbrList;
       }
}


