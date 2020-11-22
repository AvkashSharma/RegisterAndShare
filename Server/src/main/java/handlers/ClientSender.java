package handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientSender {

    public static void sendResponse(Object toSend, DatagramPacket packet, DatagramSocket clientSocket) throws IOException{
        try{
        // Obtain Client's IP address and the port
        InetAddress clientAddress = packet.getAddress();
        int clientPort = packet.getPort();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(toSend);
        // Received data from the client
        // String receivedData = new String(packet.getData());

        // byte[] sendingDataBuffer = receivedData.toUpperCase().getBytes();
        byte[] data = outputStream.toByteArray();

        
        

        // Create new UDP packet with data to send to the client
        DatagramPacket outputPacket = new DatagramPacket(data, data.length,clientAddress,clientPort);

        
            // Send the created packet to the client
             clientSocket.send(outputPacket);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
