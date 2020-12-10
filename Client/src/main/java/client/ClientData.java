package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import handlers.Common;
import requests.ClientPingServer;

public class ClientData {
    public static AtomicBoolean isRegistered = new AtomicBoolean(false);
    public static AtomicBoolean isDisconnected= new AtomicBoolean(true);
    public static AtomicReference<String> username = new AtomicReference<String>("");
    public static AtomicInteger requestCounter = new AtomicInteger(0);

    // public static InetAddress server1Address;
    public static String SERVER_1_IP = "127.0.0.1";
    public static int SERVER_1_PORT = 50001;

    // public static InetAddress server2Address;
    public static String SERVER_2_IP = "127.0.0.1";
    public static int SERVER_2_PORT = 50002;

    public static InetAddress activeServerAddress;
    public static String ACTIVE_IP = ClientData.SERVER_1_IP;
    public static int ACTIVE_PORT = ClientData.SERVER_1_PORT;
    public static InetSocketAddress serverSocket;

    public static String CLIENT_IP;
    public static int CLIENT_PORT;

    public static ConcurrentHashMap<Integer,Object> requestMap = new ConcurrentHashMap<>();
    public static AtomicInteger retryAttempt = new AtomicInteger(0);
    public static AtomicBoolean uiTakeOver = new AtomicBoolean(true);

    public static void setActiveAddress(String address, int port){
        ACTIVE_PORT = port;
        ACTIVE_IP = address;
        try {
            activeServerAddress = InetAddress.getByName(ACTIVE_IP.toString());
        } catch (UnknownHostException e) { 
            e.printStackTrace();
        }
    }
    // UI to enter Server IP address
    public static void getServerAddress(Scanner s) {
        ClientData.uiTakeOver.set(false);
        String localAddress = "";
        try {
            localAddress = InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.print("Enter server 1 \n\t\tIp Address("+localAddress+"): ");
        SERVER_1_IP = Common.scanIp(s);
        SERVER_1_PORT = Common.scanInt(s, "\t\tPort: ");
        // Ports should be between 49152 - 65535
        if (SERVER_1_PORT < 1 || SERVER_1_PORT > 65535) {
            System.out.println("port out of range");
            SERVER_1_PORT = Common.scanInt(s, "\t\tPort: ");
        }

        System.out.print("Enter server 2 \n\t\tIp Address("+localAddress+"): ");
        SERVER_2_IP =  Common.scanIp(s);
        SERVER_2_PORT = Common.scanInt(s, "\t\tPort: ");

        // Ports should be between 49152 - 65535
        if (SERVER_2_PORT < 1 || SERVER_2_PORT > 65535) {
            System.out.println("port out of range");
            SERVER_2_PORT = Common.scanInt(s, "\t\tPort: ");
        }
        ClientData.uiTakeOver.set(true);
        Client.ui();
    }

    // Use this to return active server ip and port
    public static InetSocketAddress checkActiveServer(Scanner scanner) {

        while (true) {

            // COMMENT to skip entering IP address
            getServerAddress(scanner);// <---------------------------------------------------------TO
            // SKIP ENTERING IP
            try {
                boolean server1Active = ClientPingServer.ping(SERVER_1_IP, SERVER_1_PORT);;
                boolean server2Active =  ClientPingServer.ping(SERVER_2_IP, SERVER_2_PORT);

                if (server1Active) {
                    ACTIVE_PORT = SERVER_1_PORT;
                    ACTIVE_IP = SERVER_1_IP;
                    break;
                } else if (server2Active) {
                    ACTIVE_PORT = SERVER_2_PORT;
                    ACTIVE_IP = SERVER_2_IP;
                    break;
                } else {
                    System.out.println("Server 1 and Server 2 are unreachable. Please enter valid Server Address");
                }

            } catch (UnknownHostException e) {
                // e1.printStackTrace();
            } catch (IOException e) { 
                // e1.printStackTrace();
            }
        }

        try {
            activeServerAddress = InetAddress.getByName(ACTIVE_IP.toString());
            serverSocket = new InetSocketAddress(activeServerAddress, ACTIVE_PORT);
            return serverSocket;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return null;

    }

}
