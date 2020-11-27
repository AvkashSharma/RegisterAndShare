package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.TimerTask;

import handlers.*;
import requests.ServerPingServer;

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
            startTimer();
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
        System.out.println("Enter server timeout(seconds): ");
        ServerData.timeout.set(scanner.nextInt());
        ServerData.interval = ServerData.timeout.get();

        System.out.print("Enter Port Number for server: ");
        ServerData.port.set(scanner.nextInt());

        System.out.print("Enter a server name: ");
        ServerData.name = scanner.next();

        while (true) {
            // Is it the first server?
            System.out.print("Is this your first server(y/n): ");
            String firstServer = scanner.nextLine();
            if (firstServer.equals("y")) {
                break;
            } else if (firstServer.equals("n")) {
                System.out.println("Enter other server's address: ");
                ServerData.addressB.set(scanner.next());

                System.out.println("Enter other server's port: ");
                ServerData.portB.set(scanner.nextInt());

                try {
                    ServerData.isServing.set(!ServerPingServer.ping(ServerData.addressB.get(), ServerData.portB.get()));

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void ui() {
        String val = "";
        while (!val.equals("exit")) {
            System.out.println("\n----------------Server Listening on " + ServerData.address + ":"
                    + ServerData.port.get() + "---------------- Online: " + ServerData.isServing.get());
            System.out.println("Enter 'crtl+C' to exit Server, Press 'ENTER' to refresh");
            System.out.println("1-Change Port");
            System.out.println("2-Change IP");
            System.out.println("3-Stop Serving Clients");
            System.out.println("4-Serve Clients");
            System.out.println("5-Ping other server");
            System.out.println("6-Swap Server");

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

    public void startTimer() {
        ServerData.timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                if(ServerData.interval == 1){
                    if(ServerData.isServing.get()){
                    //  before going offline make sure other server goes online
                        ServerData.isServing.set(false);
                    }
                    else
                        ServerData.isServing.set(true);
                    // ServerData.timer.cancel();
                    ServerData.interval = ServerData.timeout.get();
                }
                --ServerData.interval;

                // int timeS = ServerData.setInterval();
                System.out.println(ServerData.interval);
            }
        }, 1000, 1000);
    }

    public void updatePort() {
        System.out.println("Enter port number: ");
        // change port number
        // close socket and reopen on new port
        // inform server of change
    }

    public void updateIP() {

    }

    public void stopServing() {
        ServerData.isServing.set(false);
    }

    public void serve() {
        ServerData.isServing.set(true);
    }

    // swap server
    public void swap() {

    }
}
