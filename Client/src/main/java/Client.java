import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Client {

    /* The server port to which
    the client socket is going to connect */
    public final static int SERVICE_PORT = 50001;

    public static void main(String[] args) {
        try {

            // Get the IP address of the server
            InetAddress address = InetAddress.getLocalHost();

            /* Instantiate client socket.
             No need to bind to a specific port */
            DatagramSocket datagramSocket = new DatagramSocket();

            Scanner scanner = new Scanner(System.in);
            String echoString;

            do {
                System.out.println("Enter string to be echoed: " );
                echoString = scanner.nextLine();

                byte[] outgoingBuffer = echoString.getBytes();

                DatagramPacket packet = new DatagramPacket(outgoingBuffer,outgoingBuffer.length, address, SERVICE_PORT);
                datagramSocket.send(packet);

                byte[] incomingBuffer = new byte[50];
                packet = new DatagramPacket(incomingBuffer, incomingBuffer.length);
                datagramSocket.receive(packet);
                System.out.println("Text received is: " + new String(incomingBuffer, 0, packet.getLength()));

            } while(!echoString.equals("exit"));

        } catch(SocketTimeoutException e) {
            System.out.println("The socket timed out");
        } catch(IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
