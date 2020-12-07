package handlers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import requests.Update.ChangeServer;
import requests.Update.UpdateConfirmed;
import requests.Update.UpdateDenied;
import requests.Registration.ClientRegisterConfirmed;
import requests.Registration.ClientRegisterDenied;
import requests.Registration.DeRegisterConfirmed;
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

        System.out.println("received packet");
        Object o = (Object) is.readObject();
        //Writer.appendToFile(o);
        Tracker.handleReceivedResponse(o);
        // call handleRequest
        handleRequest(o, incomingPacket);
      }
    } catch (IOException e) {
      System.out.println("Receiver IOException " + e.getMessage());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void handleRequest(Object request, DatagramPacket packet) {

    // Handle Successful Register Request - Don't think we need it
    if (request instanceof ClientRegisterConfirmed) {
      ClientData.setActiveAddress(packet.getAddress().toString().replace("/", ""), packet.getPort());
      ClientData.isRegistered.set(true);
      System.out.println("\n" + request.toString());
    }

    // Upon reception of REGISTER-DENIED, the user will give up for a little while
    // before retrying again depending on the reason.
    else if (request instanceof ClientRegisterDenied) {
      ClientData.setActiveAddress(packet.getAddress().toString().replace("/", ""), packet.getPort());
      System.out.println(request.toString());
      ClientData.isRegistered.set(false);

    } else if (request instanceof DeRegisterConfirmed) {
      System.out.println(request.toString());
      ClientData.isRegistered.set(false);
      ClientData.username.set("");

    } else if (request instanceof UpdateConfirmed) {
      ClientData.setActiveAddress(packet.getAddress().toString().replace("/", ""), packet.getPort());
      ClientData.isRegistered.set(true);
      System.out.println(request.toString());

    } else if (request instanceof UpdateDenied) {
      ClientData.setActiveAddress(packet.getAddress().toString().replace("/", ""), packet.getPort());
      ClientData.isRegistered.set(false);
      System.out.println(request.toString());
      
    } else if (request instanceof ChangeServer) {

      System.out.println(request.toString());

      ChangeServer ser = (ChangeServer) request;
      ClientData.ACTIVE_IP = ser.getAddress();
      ClientData.ACTIVE_PORT = ser.getPort();
      try {
        ClientData.activeServerAddress = InetAddress.getByName(ser.getAddress());
      } catch (UnknownHostException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } else {
      System.out.println(request.toString());
    }
  }
}