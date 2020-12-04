package handlers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import client.ClientData;

public class Writer {

  // static String fileName = "log_"+ClientData.CLIENT_IP.toString()+"_"+ClientData.CLIENT_PORT+".txt";

  public static void appendToFile(Object object) throws IOException{
    String fileName = ClientData.username.get().toString()+ ".txt";
    try(BufferedWriter br = new BufferedWriter(new FileWriter(fileName,true))){
      System.out.println("Writer "+ object.toString());
      StringBuilder str = new StringBuilder();

      str.append("----------------NEW MESSAGE--------------------"); 
      br.write(str.toString());
      br.newLine();
      br.write(object.toString());
      br.newLine();

    }
  }

  public static void sendRequestToFile(Object object, String serverAddress, int serverPort) throws IOException {
    String fileName = ClientData.username.get().toString()+ ".txt";
    try(BufferedWriter br = new BufferedWriter(new FileWriter(fileName,true))){
      System.out.println("Writer "+ object.toString());
      StringBuilder str = new StringBuilder();

      str.append("----------------SENDING-------------"); 
      br.write(str.toString());
      br.newLine();
      br.write(object.toString());
      br.newLine();
      br.write("Sending to ->"+serverAddress+":"+serverPort);
      br.newLine();

    }
  }
  
}
