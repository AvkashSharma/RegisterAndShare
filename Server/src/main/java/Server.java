import com.sun.security.ntlm.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

    // Server UDP socket runs at this port
    public final static int SERVICE_PORT=50001;

    public static void main(String[] args) {

        System.out.println("Server Started...");
        byte[] dataBuffer = new byte[1024];

        try {
            DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);
            while (true) {

                /* Instantiate a UDP packet to store the
                client data using the buffer for receiving data*/
                DatagramPacket packetReceived = new DatagramPacket(dataBuffer, dataBuffer.length);

                // Receive data from the client and store in inputPacket
                serverSocket.receive(packetReceived);

                // Need to pass received data
                ClientHandler clientHandler  = new ClientHandler(packetReceived,serverSocket);

                System.out.println("Assigning new thread for this client");

                // Create a new Thread
                Thread threadClientHandler = new Thread(clientHandler);

                // Start the thread
                threadClientHandler.start();

            }
        } catch (SocketException e) {
            System.out.println("SocketException: " + e.getMessage());
        }
        catch(IOException e)
        {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
