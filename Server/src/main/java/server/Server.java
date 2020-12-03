package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import db.Database;
import db.User;
import handlers.*;
import requests.server.ServerPingServer;
import requests.Update.ChangeServer;
import requests.server.ServeRequest;

public class Server implements Runnable {

    public final Scanner scanner = new Scanner(System.in);
    private final int bufferSize;
    private static String display = "";
    private static volatile DatagramSocket socket;
    ClientReceiver clientReceiver;
    Thread threadClientReceiver;

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
            startActiveTimer();
            while (true) {

                byte[] buffer = new byte[bufferSize];

                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

                try {
                    socket.receive(incoming);

                    // Need to pass received data
                    clientReceiver = new ClientReceiver(incoming, socket);

                    // Create a new Thread
                    threadClientReceiver = new Thread(clientReceiver);

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

    /**
     * configure server on startup
     */
    public void serverConfig() {
        System.out.println("Enter server timeout(seconds): ");
        ServerData.sleepTime.set(scanner.nextInt());
        ServerData.activeInterval = ServerData.sleepTime.get();
        ServerData.inactiveInterval = ServerData.sleepTime.get() + ServerData.timeout;

        System.out.print("Enter Port Number for server: ");
        ServerData.port.set(scanner.nextInt());

        while (true) {
            // Is it the first server?
            System.out.print("Is this your first server(y/n): ");
            String firstServer = scanner.next();
            if (firstServer.equals("y")) {
                break;
            } else if (firstServer.equals("n")) {
                try {
                    System.out.println("Enter other server's address(" + InetAddress.getLocalHost().toString() + "): ");
                } catch (UnknownHostException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
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
                    + ServerData.port.get() + "---------------- Online: " + ServerData.isServing.get() + "\t");
            System.out.println("Enter 'crtl+C' to exit Server, Press 'ENTER' to refresh");
            System.out.println("1-Change Server's Port (UPDATE-SERVER)");
            System.out.println("2-Stop Serving Clients");
            System.out.println("3-Serve Clients");
            System.out.println("4-Update Server");
            System.out.println("5-Update User's (CHANGE-SERVER)");

            System.out.print("\t\t\t\tChoice: ");
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
                    System.out.println("Stop serving clients");
                    stopServing();
                    break;
                case "3":
                    System.out.println("Serving clients");
                    serve();
                    break;
                case "4":
                    System.out.println("Serving clients");
                    // serve();
                    break;

                case "-1":
                    continue;
                default:
                    System.out.println("Not a valid option");
                    continue;
            }
        }
    }

    /**
     * Start the timer for the server to be ONLINE
     */
    public static void startActiveTimer() {
        ServerData.inactiveTimer.cancel();
        ServerData.inactiveTimer = new Timer();
        ServerData.activeTimer.cancel();
        ServerData.activeTimer = new Timer();
        ServerData.activeInterval = ServerData.sleepTime.get();
        ServerData.activeTimer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                System.out.print("\r" + ServerData.activeInterval + "\t" + ServerData.inactiveInterval + "---- Online: "
                        + ServerData.isServing.get() + "\t" + display);
                if (ServerData.activeInterval <= 0) {

                    if (ServerData.isServing.get()) {
                        stopServing();
                    } else
                        serve();

                }
                --ServerData.activeInterval;
            }
        }, 1000, 1000);
    }

    public static void startInactiveTimer() {
        ServerData.activeTimer.cancel();
        ServerData.activeTimer = new Timer();
        ServerData.inactiveTimer.cancel();
        ServerData.inactiveTimer = new Timer();
        ServerData.inactiveInterval = ServerData.sleepTime.get() + ServerData.timeout;
        ServerData.inactiveTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.print("\r" + ServerData.activeInterval + "\t" + ServerData.inactiveInterval + "---- Online: "
                        + ServerData.isServing.get() + "\t" + display);
                if (ServerData.inactiveInterval <= 0) {
                    serve();
                    changeServer();
                }

                --ServerData.inactiveInterval;
            }
        }, 1000, 1000);
    }

    public void updatePort() {
        // display = "Enter port number: ";
        System.out.println("\t Enter port number: ");
        ServerData.port.set(scanner.nextInt());
        
        threadClientReceiver.interrupt();
    
        // socket.close();
        // try {
        //     socket = new DatagramSocket(ServerData.port.get());
        // } catch (SocketException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }

        // socket.close();
        // change port number
        // close socket and reopen on new port
        // inform server of change
    }

    /**
     * Server verifies if backup server can go ONLINE only then this server can go
     * OFFLINE, if not the server stays online
     * <p>
     * Tell Backup server to go online
     * <p>
     * Wait for Backup server to be online
     * <p>
     * Let Clients know about the change
     * <p>
     * Only then this server goes offline and start the offline timer
     */
    public static void stopServing() {
        boolean bServingStatus = false;

        try {
            // ping other server to see if its up and running
            bServingStatus = ServerPingServer.ping(ServerData.addressB.get(), ServerData.portB.get());
            System.out.println(bServingStatus);
            if (bServingStatus) {
                // send Serve Request
                ServeRequest serveRequest = new ServeRequest(true);
                Sender.sendTo(serveRequest, socket, ServerData.addressB.get(), ServerData.portB.get());
                ServerData.isServing.set(false);
                changeServer();

                // reset inactive timer
                startInactiveTimer();
            }

            else {
                serve();
            }

        } catch (IOException e) {
            bServingStatus = false;
        }

    }

    /**
     * Server goes ONLINE and starts its timer
     */
    public static void serve() {
        ServerData.activeInterval = ServerData.sleepTime.get();
        ServerData.isServing.set(true);
        startActiveTimer();
    }

    /**
     * Let know the clients that server has changed address
     */
    // TODO create threads instead when sending
    public static void changeServer() {
        Database db = new Database();
        List<User> Users = db.getUsers();
        for (User user : Users) {
            ChangeServer changeServer = new ChangeServer(ServerData.addressB.get(), ServerData.portB.get());
            System.out.println(user.getUsername() + " " + user.getUserIP() + ":" + user.getUserSocket());
            byte[] buffer = new byte[1024];

            SocketAddress socketAddress = new InetSocketAddress(user.getUserIP(), user.getUserSocket());
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, socketAddress);

            try {
                ClientSender.sendResponse(changeServer, packet, socket);
            } catch (IOException e) {
                // TODO to
                System.out.println("Could not connect to client");
                e.printStackTrace();
            }
        }
        db.close();
    }
}
