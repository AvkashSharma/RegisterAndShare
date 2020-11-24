package handlers;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import requests.Registration.ClientRegisterConfirmed;
import requests.Registration.ClientRegisterDenied;
import requests.Registration.DeRegisterConfirmed;
import client.Client;
import client.ClientData;

public class ServerReceiver implements Runnable {

  private DatagramSocket clientSocket;

  public ServerReceiver(DatagramSocket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void run() {
    try {
      
      while (true) {

        byte[] incomingData = new byte[1024];

        DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
        clientSocket.receive(incomingPacket);

        byte[] dataBuffer = incomingPacket.getData();
        ByteArrayInputStream byteStream = new ByteArrayInputStream(dataBuffer);
        ObjectInputStream is = new ObjectInputStream(byteStream);

        Object o = (Object) is.readObject();
        Writer.appendToFile(o); 
      
        // Create an object of RequestHandler
        RequestHandler handler = new RequestHandler();

        // call handleRequest
        handler.handleRequest(o);
      }
    } catch (IOException e) {
          System.out.println("Receiver IOException " + e.getMessage());
    }
    catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
  }
}

class RequestHandler {

  public void handleRequest(Object request) {

    // Handle Successful Register Request - Don't think we need it
    if (request instanceof ClientRegisterConfirmed) {
      ClientData.isRegistered.set(true);
      System.out.println("\n"+request.toString());
    }

    // Upon reception of REGISTER-DENIED, the user will give up for a little while
    // before retrying again depending on the reason.
    else if (request instanceof ClientRegisterDenied) {
      System.out.println(request.toString());
      ClientData.isRegistered.set(false);
    } 
    else if (request instanceof DeRegisterConfirmed) {
      System.out.println(request.toString());
      ClientData.isRegistered.set(false);
      ClientData.username.set("");
    } 
    
    else {
      System.out.println(request.toString());
    }
  }
}