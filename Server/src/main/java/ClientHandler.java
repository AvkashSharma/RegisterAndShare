import java.io.IOException;
import java.net.*;

public class ClientHandler implements Runnable {

    private DatagramPacket packetReceived;
    private DatagramSocket clientSocket;
    private String receivedData;


    public ClientHandler(DatagramPacket packetReceived, DatagramSocket clientSocket)
    {
        this.packetReceived = packetReceived;
        this.clientSocket = clientSocket;
    }
        public void run() {

                    // Received data from the client
                    receivedData = new String(packetReceived.getData());

                    // Print data sent by the client
                    System.out.println("Received: " + receivedData);

                    serverResponse.sendResponse(packetReceived,clientSocket);


        }
    }

