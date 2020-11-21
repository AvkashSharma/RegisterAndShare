package Requests;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import Requests.Registration.RegisterMessage;

public class ClientReceiver implements Runnable {

  private DatagramSocket clientSocket; 

  public ClientReceiver(DatagramSocket clientSocket){
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
            RegisterMessage o = (RegisterMessage)is.readObject();
            System.out.println("Received " + o.requestType.toString());
          }

      } catch (IOException e) {
          System.out.println("Receiver IOException " + e.getMessage());
      } catch (ClassNotFoundException e) {
        
        e.printStackTrace();
      }
  }

}
