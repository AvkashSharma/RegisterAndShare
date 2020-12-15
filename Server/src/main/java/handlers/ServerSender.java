package handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import server.ServerData;

/**
 * Sends responses to the other server
 */
public class ServerSender {

  // need to change the datagram socket from client to other server socket
  public static void sendResponse(Object toSend, DatagramSocket datagramSocket) {
    try {

      // Obtain Client's IP address and the port
      InetAddress addressB = InetAddress.getByName(ServerData.addressB.get());
      int portB = ServerData.portB.get();

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ObjectOutputStream os = new ObjectOutputStream(outputStream);
      os.writeObject(toSend);
      byte[] data = outputStream.toByteArray();

      // Create new UDP packet with data to send to the other server
      DatagramPacket outputPacket = new DatagramPacket(data, data.length, addressB, portB);

      // Send the created packet to the IDLE server
      // System.out.println("INFORMING IDLE Server ");
      datagramSocket.send(outputPacket);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
