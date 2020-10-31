import java.io.IOException;
import java.net.*;

public class ClientHandler implements Runnable {

    private DatagramPacket packetReceived;
    private DatagramSocket serverSocket;


    public ClientHandler(DatagramPacket packetReceived, DatagramSocket serverSocket)
    {
        this.packetReceived = packetReceived;
        this.serverSocket = serverSocket;
    }

    public void run() {

        // Received data from the client
         String receivedData = new String(packetReceived.getData());

        // Print data sent by the client
        System.out.println("Sent by the client: " + receivedData);

        serverResponse.sendResponse(packetReceived,serverSocket);

        }
    }

