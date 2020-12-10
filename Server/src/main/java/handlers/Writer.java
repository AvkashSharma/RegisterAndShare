package handlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import server.ServerData;

public class Writer {


  public static void appendToFile(Object object) throws IOException{

    String fileName = ServerData.serverName.toString()+ ".txt";

    try(BufferedWriter br = new BufferedWriter(new FileWriter(fileName,true))){
      // System.out.println("Writer "+ object.toString());
      StringBuilder str = new StringBuilder();

      str.append("----------------NEW MESSAGE--------------------"); 
      br.write(str.toString());
      br.newLine();
      br.write(object.toString());
      br.newLine();

    }
  }
  
}
