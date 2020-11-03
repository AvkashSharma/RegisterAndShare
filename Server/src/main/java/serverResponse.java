import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class serverResponse {

    public static void sendResponse(DatagramPacket packet, DatagramSocket clientSocket){

        // Received data from the client
        String receivedData = new String(packet.getData());

        byte[] sendingDataBuffer = receivedData.toUpperCase().getBytes();

        // Obtain Client's IP address and the port
        InetAddress clientAddress = packet.getAddress();
        int clientPort = packet.getPort();

        // Create new UDP packet with data to send to the client
        DatagramPacket outputPacket = new DatagramPacket(sendingDataBuffer,sendingDataBuffer.length,clientAddress,clientPort);

        try{
            // Send the created packet to the client
             clientSocket.send(outputPacket);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
