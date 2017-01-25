/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aos_prj_2;

import java.util.ArrayList;

/**
 *
 * @author Nikitha Mahesh
 */
 public class IntialisingPeerDetails 
{

    public String getPeer_id() {
        return peer_id;
    }

    public void setPeer_id(String peer_id) {
        this.peer_id = peer_id;
    }

    public String getPort_no() {
        return port_no;
    }

    public void setPort_no(String port_no) {
        this.port_no = port_no;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public Integer getNeighbour_id() {
        return neighbour_id;
    }

    public void setNeighbour_id(Integer neighbour_id) {
        this.neighbour_id = neighbour_id;
    }
    
    String peer_id;
    String port_no;
    String file_name;
    Integer neighbour_id;
    

    
    ArrayList<String> numberOfNeig = new ArrayList<String>();

    public ArrayList<String> getNumberOfNeig() {
        return numberOfNeig;
    }

    public void setNumberOfNeig(ArrayList<String> numberOfNeig) {
        this.numberOfNeig = numberOfNeig;
    }
  
public void setPeer_Details(String peer_id,String port_no,String file_name,Integer neighbour_id)
{
    this.peer_id = peer_id;
    this.port_no = port_no;
    this.file_name = file_name;
    this.neighbour_id = neighbour_id;
}

    


    
}
