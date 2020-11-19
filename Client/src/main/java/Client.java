import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.print.event.PrintEvent;
import javax.sound.sampled.SourceDataLine;

import Requests.Message;
import Requests.RequestType;
import Requests.Sender;
import Requests.Registration.RegisterMessage;

public class Client {

    /*
     * The server port to which the client socket is going to connect
     */
    // public final static int SERVICE_PORT = 50001;

    // we can store this as INET ADDRESS later on
    public static String SERVER_1_HOSTNAME = "KJ-ZENBOOK";
    public static String SERVER_1_IP = "192.168.167.1";
    public static int SERVER_1_PORT = 1234;

    public static String SERVER_2_HOSTNAME = "KJ-ZENBOOK";
    public static String SERVER_2_IP = "192.168.167.1";
    public static int SERVER_2_PORT = 60000;

    public static String ACTIVE_HOSTNAME = "KJ-ZENBOOK";
    public static String ACTIVE_IP = "192.168.167.1";
    public static int ACTIVE_PORT = 50000;

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            // get all address of servers
            // getServerAddress(scanner);
            // verify which server is active
            InetAddress ACTIVE_SERVER = checkActiveServer();

            DatagramSocket datagramSocket = new DatagramSocket();

            // Time client waits for a response before timing out
            // datagramSocket.setSoTimeout(5000);

            String cmdInput = "";
            do {
            System.out.println("Enter username: ");
            cmdInput = scanner.next();
            RegisterMessage testMessage = new RegisterMessage(cmdInput, new InetSocketAddress(InetAddress.getLocalHost(), 1234));

            testMessage.print();
            Sender.sendTo(testMessage, ACTIVE_SERVER, ACTIVE_PORT);
            // System.out.println(testMessage.getClientName());
            // byte[] outgoingBuffer = echoString.getBytes();

            // DatagramPacket packet = new DatagramPacket(outgoingBuffer,
            // outgoingBuffer.length, ACTIVE_SERVER,
            // ACTIVE_PORT);
            // datagramSocket.send(packet);

            // byte[] incomingBuffer = new byte[50];
            // packet = new DatagramPacket(incomingBuffer, incomingBuffer.length);
            // datagramSocket.receive(packet);
            // System.out.println("Text received is: " + new String(incomingBuffer, 0,
            // packet.getLength()));

            } while (!cmdInput.equals("exit"));
            scanner.close();

        } catch (SocketTimeoutException e) {
            System.out.println("The socket timed out");
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

    public static void getServerAddress(Scanner s) {
        System.out.print("Enter server 1 HostName: ");
        SERVER_1_HOSTNAME = s.next();
        System.out.print("Enter server 1 Ip Address: ");
        SERVER_1_IP = s.next();
        System.out.print("Enter server 1 port: ");
        // todo - validate that its a valid port
        SERVER_1_PORT = s.nextInt();

        System.out.print("Enter server 2 HostName: ");
        SERVER_2_HOSTNAME = s.next();
        System.out.print("Enter server 2 Ip Address: ");
        SERVER_2_IP = s.next();
        System.out.print("Enter server 2 port: ");
        // todo - validate that its a valid port
        SERVER_2_PORT = s.nextInt();
    }

    public static InetAddress checkActiveServer() {
        ACTIVE_PORT = SERVER_1_PORT;
        ACTIVE_IP = SERVER_1_IP;
        ACTIVE_HOSTNAME = SERVER_1_HOSTNAME;
        InetAddress ACTIVE_SERVER;
        try {
            ACTIVE_SERVER = InetAddress.getByName(ACTIVE_IP.toString());
            return ACTIVE_SERVER;

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }

        // ACTIVE_SERVER = InetAddress.getHostAddress(ACTIVE_HOSTNAME);

        // System.out.println("Connected to server with port "+ACTIVE_PORT);
    }

    public static void UserExist() {

    }
}
