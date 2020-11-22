import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javax.print.event.PrintEvent;
import javax.sound.sampled.SourceDataLine;

import handlers.*;
import requests.*;
import requests.Request;
import requests.RequestType;
import requests.Registration.ClientRegisterDenied;
import requests.Registration.DeRegisterRequest;
import requests.Registration.RegisterRequest;

public class Client {

    public final Scanner scanner = new Scanner(System.in);

    // we can store this as INET ADDRESS later on
    public static String SERVER_1_HOSTNAME = "KJ-ZENBOOK";
    public static String SERVER_1_IP = "192.168.167.1";
    public static int SERVER_1_PORT = 1234;

    public static String SERVER_2_HOSTNAME = "KJ-ZENBOOK";
    public static String SERVER_2_IP = "192.168.167.1";
    public static int SERVER_2_PORT = 60000;

    public static String ACTIVE_HOSTNAME = "Avkash-MacBook-Pro.local";
    public static String ACTIVE_IP = "127.0.0.1";
    public static int ACTIVE_PORT = 50001;

    private AtomicInteger requestCounter = new AtomicInteger(0);
    private InetAddress activeServerIP;
    private int activeServerPort;
    private static DatagramSocket clientSocket;

    public static AtomicBoolean isRegister = new AtomicBoolean(false);

    public Client() {
        InetSocketAddress activeServer = checkActiveServer();
        this.activeServerIP = activeServer.getAddress();
        this.activeServerPort = activeServer.getPort();

        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("Socket Exception" + e.getMessage());
        }
    }

    // Sends ping request to a provided IP address
    public static boolean sendPingRequest(String ipAddress, int port) throws UnknownHostException, IOException {
        InetAddress add = InetAddress.getByName(ipAddress);
        System.out.println("Sending Ping Request to " + ipAddress);
        if (add.isReachable(port)) {
            System.out.println("Host " + ipAddress + ":" + port + " is reachable");
            return true;
        } else {
            System.out.println("Sorry ! We can't reach to this host - " + ipAddress + ":" + port);
            return false;
        }
    }

    // UI to enter Server IP address
    public static void getServerAddress(Scanner s) {
        // System.out.print("Enter server 1 HostName: ");
        // SERVER_1_HOSTNAME = s.next();
        System.out.print("Enter server 1 \n\t\tIp Address: ");
        SERVER_1_IP = s.next();
        System.out.print("\t\tPort: ");
        // todo - validate that its a valid port
        SERVER_1_PORT = s.nextInt();
        // Ports should be between 49152 - 65535
        if (SERVER_1_PORT < 1 || SERVER_1_PORT > 65535) {
            throw new IllegalArgumentException("Port out of range");
        }

        // System.out.print("Enter server 2 HostName: ");
        // SERVER_2_HOSTNAME = s.next();
        System.out.print("Enter server 2 \n\t\tIp Address: ");
        SERVER_2_IP = s.next();
        System.out.print("\t\tPort: ");
        // todo - validate that its a valid port
        SERVER_2_PORT = s.nextInt();

        // Ports should be between 49152 - 65535
        if (SERVER_2_PORT < 1 || SERVER_2_PORT > 65535) {
            throw new IllegalArgumentException("Port out of range");
        }
    }

    // Use this to return active server ip and port
    public InetSocketAddress checkActiveServer() {
        while (true) {

            //
            //
            //COMMENT to skip entering IP address
            //
            //
            // getServerAddress(scanner);//<---------------------------------------------------------TO SKIP ENTERING IP
            try {
                boolean server1Active = sendPingRequest(SERVER_1_IP, SERVER_1_PORT);
                boolean server2Active = sendPingRequest(SERVER_2_IP, SERVER_2_PORT);

                if (server1Active) {
                    ACTIVE_HOSTNAME = SERVER_1_HOSTNAME;
                    ACTIVE_PORT = SERVER_1_PORT;
                    ACTIVE_IP = SERVER_1_IP;
                    break;
                } else if (server2Active) {
                    ACTIVE_HOSTNAME = SERVER_2_HOSTNAME;
                    ACTIVE_PORT = SERVER_2_PORT;
                    ACTIVE_IP = SERVER_2_IP;
                    break;
                } else {
                    System.out.println("Server 1 and Server 2 are unreachable. Please enter valid Server Address");
                }

            } catch (UnknownHostException e1) {
                // TODO Auto-generated catch block
                // e1.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                // e1.printStackTrace();
            }
        }

        try {
            InetAddress ACTIVE_SERVER;
            ACTIVE_SERVER = InetAddress.getByName(ACTIVE_IP.toString());
            return new InetSocketAddress(ACTIVE_SERVER, ACTIVE_PORT);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }

        return null;

    }

    public String toString() {
        return activeServerIP + " " + activeServerPort;
    }

    public void start() {

        // Add a thread to listen to server messages

        // Thread to receive messages from the server.
        ServerReceiver receiver = new ServerReceiver(clientSocket);
        Thread receiverThread = new Thread(receiver);
        receiverThread.start();

        registrationUI();
    }

    public void registrationUI() {
        String val = "";
        while (!val.equals("exit")) {
            // if (!isRegister.get())
                System.out.println("1-Register");
            // else
                System.out.println("2-Deregister");

            System.out.println("Enter 'exit' to exit application");
            System.out.print("Choice: ");
            val = scanner.next();

            switch (val) {
                case "1":
                    register();
                    break;
                case "2":
                    deregister();
                    break;
                default:
                    System.out.println("Not a valid option");
            }
        }
    }

    public void register() {
        System.out.println("Enter Username: ");
        String username = "";
        username = scanner.next();
        RegisterRequest registerMessage = new RegisterRequest(requestCounter.incrementAndGet(), username,
                new InetSocketAddress(activeServerIP, ACTIVE_PORT));
        try {
            Sender.sendTo(registerMessage, activeServerIP, activeServerPort, clientSocket);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void deregister(){
        String username = "";
        username = scanner.next();
        DeRegisterRequest deregisterMessage = new DeRegisterRequest(requestCounter.incrementAndGet(), username);
        try {
            Sender.sendTo(deregisterMessage, activeServerIP, activeServerPort, clientSocket);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Client client = new Client();
        client.start();
    }
}
