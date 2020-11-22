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
import java.util.concurrent.atomic.AtomicInteger;

import javax.print.event.PrintEvent;
import javax.sound.sampled.SourceDataLine;

import handlers.*;
import requests.*;
import requests.Request;
import requests.RequestType;
import requests.Publish.PublishDenied;
import requests.Publish.PublishRequest;
import requests.Registration.ClientRegisterDenied;
import requests.Registration.DeRegisterRequest;
import requests.Registration.RegisterRequest;
import requests.Update.SubjectsRequest;
import requests.Update.SubjectsUpdated;
import requests.Update.UpdateRequest;

public class Client {

    public final Scanner scanner = new Scanner(System.in);

    // we can store this as INET ADDRESS later on
    public static String SERVER_1_HOSTNAME = "KJ-ZENBOOK";
    public static String SERVER_1_IP = "192.168.0.80";
    public static int SERVER_1_PORT = 1234;

    public static String SERVER_2_HOSTNAME = "KJ-ZENBOOK";
    public static String SERVER_2_IP = "192.168.167.1";
    public static int SERVER_2_PORT = 60000;

    public static String ACTIVE_HOSTNAME = "Avkash-MacBook-Pro.local";
    public static String ACTIVE_IP = "127.0.0.1";
    public static int ACTIVE_PORT = 50001;

    public static String AVKASH_HOSTNAME = "Avkash-MacBook-Pro.local";
    public static String AVKASH_IP = "127.0.0.1";
    public static int AVKASH_PORT = 50001;

    private AtomicInteger requestCounter = new AtomicInteger(0);
    private InetAddress activeServerIP;
    private int activeServerPort;
    private static DatagramSocket clientSocket;
    private String clientName;

    public Client() {
        // Scanner s = new Scanner(System.in);
        // getServerAddress(scanner);
        // s.close();
        InetSocketAddress activeServer = checkActiveServer();
        this.activeServerIP = activeServer.getAddress();
        this.activeServerPort = activeServer.getPort();
        this.clientName = getClientName();

        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("Socket Exception" + e.getMessage());

        }
    }

    // Use this to return active server ip and port
    public InetSocketAddress checkActiveServer() {
        ACTIVE_HOSTNAME = AVKASH_HOSTNAME;
        ACTIVE_PORT = AVKASH_PORT;
        ACTIVE_IP = AVKASH_IP;

        InetAddress ACTIVE_SERVER;
        try {
            ACTIVE_SERVER = InetAddress.getByName(ACTIVE_IP.toString());
            return new InetSocketAddress(ACTIVE_SERVER, ACTIVE_PORT);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        return activeServerIP + " " + activeServerPort;
    }

    public static void main(String[] args) {

        // get all address of servers
        // getServerAddress(scanner);
        // verify which server is active
        // InetAddress ACTIVE_SERVER = checkActiveServer();

        Client client = new Client();

        System.out.println(client);
        client.registerUser();
        client.start();
    }
    
    public void start() {

        // Add a thread to listen to server messages

         // Thread to receive messages from the server. 
        ServerReceiver receiver = new ServerReceiver(clientSocket);
        Thread receiverThread = new Thread(receiver);
        receiverThread.start();
}


    public String getClientName(){
        Scanner scanner = new Scanner(System.in); 
        System.out.println("Enter Client Name: ");
        String input = scanner.next(); 
        scanner.close();
        return input; 
    }


    public void registerUser(){

        try {
            RegisterRequest testMessage = new RegisterRequest(requestCounter.incrementAndGet(), clientName,
                    new InetSocketAddress(InetAddress.getLocalHost(), 1234));
            
                Sender.sendTo(testMessage, activeServerIP, activeServerPort, clientSocket); 
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
        // Ports should be between 49152 - 65535
        if (SERVER_1_PORT < 1 || SERVER_1_PORT > 65535){
            throw new IllegalArgumentException("Port out of range");
        }

        
        System.out.print("Enter server 2 HostName: ");
        SERVER_2_HOSTNAME = s.next();
        System.out.print("Enter server 2 Ip Address: ");
        SERVER_2_IP = s.next();
        System.out.print("Enter server 2 port: ");
        // todo - validate that its a valid port
        SERVER_2_PORT = s.nextInt();

        // Ports should be between 49152 - 65535
        if (SERVER_2_PORT < 1 || SERVER_2_PORT > 65535){
            throw new IllegalArgumentException("Port out of range");
        }
    }


    public void publishRequest(){
        try {
            String[] list = {"Operating System", " Networking"};
            SubjectsRequest sRequest = new SubjectsRequest(requestCounter.incrementAndGet(),clientName, list); 

            PublishRequest pRequest = new PublishRequest(requestCounter.incrementAndGet(), clientName, "Computer", "Engineering");
            
            Sender.sendTo(pRequest, activeServerIP, activeServerPort, clientSocket); 
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void updateUser(){
        try {
            UpdateRequest uRequest = new UpdateRequest(requestCounter.incrementAndGet(), clientName, "127.0.0.1", "50002");
            
                Sender.sendTo(uRequest, activeServerIP, activeServerPort, clientSocket); 
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    
    
}
