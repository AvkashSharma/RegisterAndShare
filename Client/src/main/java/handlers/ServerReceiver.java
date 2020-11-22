package handlers;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import requests.Registration.ClientRegisterDenied;
import requests.Registration.RegisterRequest;

public class ServerReceiver implements Runnable {

  private DatagramSocket clientSocket; 

  public ServerReceiver(DatagramSocket clientSocket){
    this.clientSocket = clientSocket;
  }

  public void run() {
      try{
          while(true){

            byte[] incomingData = new byte[1024];

            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            clientSocket.receive(incomingPacket);

            byte[] dataBuffer = incomingPacket.getData();
            ByteArrayInputStream byteStream = new ByteArrayInputStream(dataBuffer);
            ObjectInputStream is = new ObjectInputStream(byteStream);
            RegisterRequest o = (RegisterRequest)is.readObject();
            // System.out.println("Received " + o.requestType.toString());

            // Create an object of RequestHandler
            RequestHandler handler = new RequestHandler();
            // call handleRequest
            handler.handleRequest(o);
          }

      } catch (IOException e) {
          System.out.println("Receiver IOException " + e.getMessage());
      } catch (ClassNotFoundException e) {
        
        e.printStackTrace();
      }
  }

}



  class RequestHandler {

    public void handleRequest(Object request){

      // Handle Successful Register Request - Don't think we need it
      if(request instanceof RegisterRequest){
        System.out.println("True");
      }
      else {
        System.out.println("False");
      }

      // Upon reception of REGISTER-DENIED, the user will give up for a little while before retrying again depending on the reason. 
      if (request instanceof ClientRegisterDenied){
        System.out.println("True");
      }
      else {
        System.out.println("False");
      }
    }
}