import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

import javax.print.event.PrintEvent;
import javax.sound.sampled.SourceDataLine;

public class Client {

    /* The server port to which
    the client socket is going to connect */
    // public final static int SERVICE_PORT = 50001;
    public static String SERVER_1_IP = "192.168.167.1";
    public static int SERVER_1_PORT = 50000;

    public static String SERVER_2_IP = "192.168.167.1";
    public static int SERVER_2_PORT = 60000;

    public static String ACTIVE_IP = "192.168.167.1";
    public static int ACTIVE_SERVER = 50000;

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.print("Enter server 1 port: ");
            // todo - validate that its a valid port
            SERVER_1_PORT = scanner.nextInt();

            System.out.print("Enter server 2 port: ");
            // todo - validate that its a valid port
            SERVER_2_PORT = scanner.nextInt();


            // logic to check which server is active
            checkActiveServer();

            // Get the IP address of the server
            InetAddress address = InetAddress.getLocalHost();

            address.isReachable(5);
            
            System.out.println(address);
            InetAddress address1 = InetAddress.getName(address);
            System.out.println();

            /* Instantiate client socket.
             No need to bind to a specific port */
            DatagramSocket datagramSocket = new DatagramSocket();

            // Time client waits for a response before timing out
            // datagramSocket.setSoTimeout(5000);

            
            String echoString;

            do {
                System.out.println("Enter string to be echoed: " );
                echoString = scanner.nextLine();

                byte[] outgoingBuffer = echoString.getBytes();

                DatagramPacket packet = new DatagramPacket(outgoingBuffer,outgoingBuffer.length, address, ACTIVE_SERVER);
                datagramSocket.send(packet);

                byte[] incomingBuffer = new byte[50];
                packet = new DatagramPacket(incomingBuffer, incomingBuffer.length);
                datagramSocket.receive(packet);
                System.out.println("Text received is: " + new String(incomingBuffer, 0, packet.getLength()));

            } while(!echoString.equals("exit"));
            scanner.close();

        } catch(SocketTimeoutException e) {
            System.out.println("The socket timed out");
        } catch(IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }


    public static void checkActiveServer(){
        ACTIVE_SERVER = SERVER_1_PORT;
        ACTIVE_IP = SERVER_1_IP;

        System.out.println("Connected to server with port "+ACTIVE_SERVER);
    }

}
