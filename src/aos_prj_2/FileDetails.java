package aos_prj_2;


import java.io.Serializable;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nikitha Mahesh
 */
public class FileDetails implements Serializable
{

        String peerId;
	String FileName;
        String portNumber;
        String SourceDirectoryName;
        String messageID;
        Integer timeToLive;
        ArrayList<String>files= new ArrayList<String>();

}

