package handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Handler to send responses to clients
 */
public class ClientSender {

    /**
     * Send response using the datagrampacket location
     * @param toSend
     * @param packet
     * @param clientSocket
     * @throws IOException
     */
    public static void sendResponse(Object toSend, DatagramPacket packet, DatagramSocket clientSocket) throws IOException{
        try{
        // Obtain Client's IP address and the port
        InetAddress clientAddress = packet.getAddress();
        int clientPort = packet.getPort();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(toSend);

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

    /**
     * Send reponse to client using the ip address and port
     * @param toSend
     * @param clientSocket
     * @param address
     * @param port
     * @throws IOException
     */
    public static void sendResponse(Object toSend, DatagramSocket clientSocket,  String address, int port) throws IOException{
        try{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(outputStream);
        os.writeObject(toSend);

        byte[] data = outputStream.toByteArray();

        // Create new UDP packet with data to send to the client
        InetAddress addr =InetAddress.getByName(address);
        DatagramPacket outputPacket = new DatagramPacket(data, data.length,addr, port);
        
        // Send the created packet to the client
        clientSocket.send(outputPacket);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
