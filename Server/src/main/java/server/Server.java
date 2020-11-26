package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;
import handlers.*;

public class Server implements Runnable {

    public final Scanner scanner = new Scanner(System.in);
    private final int bufferSize;
    private volatile DatagramSocket socket;

    public Server() {
        serverConfig();
        try {
            ServerData.address.set(InetAddress.getLocalHost().toString());
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.bufferSize = 1024;
    }

    public void run() {
        try {
            socket = new DatagramSocket(ServerData.port.get());
            while (true) {
                byte[] buffer = new byte[bufferSize];

                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

                try {
                    socket.receive(incoming);

                    // Need to pass received data
                    ClientReceiver clientReceiver = new ClientReceiver(incoming, socket);

                    // Create a new Thread
                    Thread threadClientReceiver = new Thread(clientReceiver);

                    // Start the thread
                    threadClientReceiver.start();

                } catch (SocketTimeoutException ex) {
                    System.out.println("SocketTimeoutException: " + ex.getMessage());

                } catch (IOException ex) {
                    System.out.println("IOException " + ex.getMessage());
                }
            } // end while
        } catch (SocketException ex) {
            System.out.println("SocketException: " + ex.getMessage());
        }
    }

    public void serverConfig() {
        System.out.print("Enter Port Number for server: ");
        ServerData.port.set(scanner.nextInt());

        System.out.print("Enter a server name: ");
        ServerData.name = scanner.next();

        //Is it the first server?
        
        System.out.println("Enter second server address: ");
        ServerData.addressB.set(scanner.next());

        System.out.println("Enter second server port: ");
        ServerData.portB.set(scanner.nextInt());

        //ping server B
        

    }

    public void ui() {
        String val = "";
        while (!val.equals("exit")) {
            System.out.println("\n----------------Server Listening on " + ServerData.address + ":" + ServerData.port.get()
                    + "----------------");
            System.out.println("Enter 'crtl+C' to exit Server, Press 'ENTER' to refresh");
            System.out.println("1-Change Port");
            System.out.println("2-Change IP");
            System.out.println("3-Stop Serving Clients");
            System.out.println("4-Serve Clients");

            System.out.print("Choice: ");
            val = scanner.nextLine();

            if (val.isEmpty()) {
                val = "-1";
            }
            switch (val) {
                case "1":
                    System.out.println("Changing Server port");
                    updatePort();
                    break;
                case "2":
                    System.out.println("Changing Server address");
                    updateIP();
                    break;
                case "3":
                    System.out.println("Stop serving clients");
                    stopServing();
                    break;
                case "4":
                    System.out.println("Serving clients");
                    serve();
                    break;
                case "-1":
                    continue;
                default:
                    System.out.println("Not a valid option");
                    continue;
            }
        }
    }

    public void updatePort() {
        System.out.println("Enter port number: ");
        //change port number
        //close socket and reopen on new port
        //inform server of change
    }

    public void updateIP() {

    }

    public void stopServing() {
        ServerData.active.set(false);
    }

    public void serve() {
        ServerData.active.set(true);
    }
}
