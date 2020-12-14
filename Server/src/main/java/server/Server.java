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
import requests.Update.UpdateServer;
import requests.server.ServeRequest;

public class Server implements Runnable {

    public final Scanner scanner = new Scanner(System.in);
    private final int bufferSize = 1024;
    private static String display = "";
    private static volatile DatagramSocket socket;
    ClientReceiver clientReceiver;
    private static boolean closeSocket = true;

    public Server() {
        serverConfig();
        try {
            ServerData.address.set(InetAddress.getLocalHost().getHostAddress().toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        // try {
        //     socket = new DatagramSocket(ServerData.port.get());
        // } catch (SocketException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        startActiveTimer();
        while (true) {

            byte[] buffer = new byte[bufferSize];
            DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);

            try {
                socket.receive(incoming);

                if (closeSocket) {
                    // Need to pass received data
                    clientReceiver = new ClientReceiver(incoming, socket);
                }

            } catch (SocketTimeoutException ex) {
                System.out.println("SocketTimeoutException: " + ex.getMessage());

            } catch (IOException ex) {
                System.out.println("IOException " + ex.getMessage());
            }
        } // end while

    }

    /**
     * configure server on startup
     */
    public void serverConfig() {
        ServerData.sleepTime.set(Common.scanInt(scanner, "Enter server's sleepTime (seconds): "));
        ServerData.activeInterval = ServerData.sleepTime.get();
        ServerData.inactiveInterval = ServerData.sleepTime.get() + ServerData.timeout;

        //check if socket is available
        ServerData.port.set(Common.scanInt(scanner, "Enter Port Number for server: "));
        while (true) {
            try {
                socket = new DatagramSocket(ServerData.port.get());
                break;
            } catch (SocketException ex) {
                // socket.close();
                System.out.println("SocketException: " + ex.getMessage());
                ServerData.port.set(Common.scanInt(scanner, "Enter an available Port Number for server: "));
            }
        }

        ServerData.serverName.set(Common.scanString(scanner, "Enter name of the server: "));

        while (true) {
            // Is it the first server?
            System.out.print("Is this your first server(y/n): ");
            String firstServer = scanner.next();
            if (firstServer.equals("y")) {
                break;
            } else if (firstServer.equals("n")) {
                try {
                    System.out.println("Enter other server's address("
                            + InetAddress.getLocalHost().getHostAddress().toString() + "): ");
                    ServerData.addressB.set(Common.scanIp(scanner));
                    ServerData.portB.set(Common.scanInt(scanner, "Enter other server's port: "));

                    ServerData.isServing.set(!ServerPingServer.ping(ServerData.addressB.get(), ServerData.portB.get()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public void ui() {
        String val = "";
        while (!val.equals("exit")) {
            System.out.println("\n----------------Server Listening on " + ServerData.address.get() + ":"
                    + ServerData.port.get() + "---------------- Online: " + ServerData.isServing.get() + "\t");
            System.out.println("----------------Server B: " + ServerData.addressB.get() + ":" + ServerData.portB.get()
                    + "---------------- Online: " + "\t");
            System.out.println("Waiting Request: " + ServerData.requestMap.size());
            System.out.println("1-Change Server's Port (UPDATE-SERVER)");
            System.out.println("2-Inform User's (CHANGE-SERVER)");
            // System.out.println("3-Stop Serving Clients (DEBUG)");
            // System.out.println("4-Serve Clients (DEBUG)");
            System.out.println("Enter 'crtl+C' to exit Server, Press 'ENTER' to refresh");
            System.out.print("\t\t\tChoice: ");
            val = scanner.nextLine();

            if (val.isEmpty()) {
                val = "-1";
            }
            switch (val) {
                case "1":
                    if(ServerData.isServing.get()){
                        System.out.println("Cannot change server address when its serving");
                        break;
                    }
                    System.out.println("Changing server address");
                    updatePort();
                    break;
                case "2":
                    System.out.println("Informing user's");
                    changeServer();
                    break;
                // case "3":
                //     System.out.println("Stop serving clients");
                //     stopServing();
                //     break;
                // case "4":
                //     System.out.println("Serving clients");
                //     serve();
                //     break;

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
                // System.out.print("\r" + ServerData.activeInterval + "\t" + ServerData.inactiveInterval + "---- Online: "
                //         + ServerData.isServing.get() + "\t" + display);
                if (ServerData.activeInterval <= 0) {

                    if (ServerData.isServing.get())
                        stopServing();
                    else
                        serve();
                }
                --ServerData.activeInterval;
            }
        }, 1000, 1000);
    }

    public static void startInactiveTimer() {
        resetTimers();
        ServerData.inactiveInterval = ServerData.sleepTime.get() + ServerData.timeout;
        ServerData.inactiveTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                // System.out.print("\r" + ServerData.activeInterval + "\t" + ServerData.inactiveInterval + "---- Online: "
                //         + ServerData.isServing.get() + "\t" + display);
                if (ServerData.inactiveInterval <= 0) {
                    serve();
                    changeServer();
                }

                --ServerData.inactiveInterval;
            }
        }, 1000, 1000);
    }

    /***
     * Reset the active and inactive Timer
     */
    public static void resetTimers() {
        ServerData.activeTimer.cancel();
        ServerData.activeTimer = new Timer();
        ServerData.inactiveTimer.cancel();
        ServerData.inactiveTimer = new Timer();
    }

    /**
     * Change server address (ip and port)
     */
    public void updatePort() {
        resetTimers();
        int originalPort = ServerData.port.get();
        System.out.print("Enter Ip address: ");
        ServerData.address.set(Common.scanIp(scanner));
        ServerData.port.set(Common.scanInt(scanner, "Enter port number: "));

        closeSocket = false;

        try {
            socket.close();
            socket = new DatagramSocket(ServerData.port.get());
            closeSocket = true;
            // serve();
            // changeServer();
            System.out.println(ServerData.address.get());
            UpdateServer update = new UpdateServer(ServerData.address.get(), ServerData.port.get());
            ServerSender.sendResponse(update, socket);
            // Sender.sendTo(update, socket, ServerData.addressB.get(),
            // ServerData.portB.get());
        } catch (SocketException e) {
            ServerData.port.set(originalPort);
            try {
                socket = new DatagramSocket(ServerData.port.get());
            } catch (SocketException e1) {
                e1.printStackTrace();
            }
            System.out.println("Error with selected port. Please retry");
            updatePort();
            // e.printStackTrace();
        }

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
            // System.out.println(bServingStatus);
            if (bServingStatus) {
                // send Serve Request
                ServeRequest serveRequest = new ServeRequest(true);
                ServerSender.sendResponse(serveRequest, socket);
                // Sender.sendTo(serveRequest, socket, ServerData.addressB.get(),
                // ServerData.portB.get());
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
        closeSocket = true;
        startActiveTimer();
    }

    /**
     * Let know the clients that server has changed address
     */
    public static void changeServer() {
        Database db = new Database();
        List<User> Users = db.getUsers();
        for (User user : Users) {
            ChangeServer changeServer = new ChangeServer(ServerData.addressB.get(), ServerData.portB.get(),
                    ServerData.address.get(), ServerData.port.get());
            System.out.println(user.getUsername() + " " + user.getUserIP() + ":" + user.getUserSocket());
            byte[] buffer = new byte[1024];

            try {
            // SocketAddress socketAddress = new InetSocketAddress(user.getUserIP(), user.getUserSocket());
            InetAddress add = InetAddress.getByName(user.getUserIP());
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, add, user.getUserSocket());
            ClientSender.sendResponse(changeServer, packet, socket);
            } catch (IOException e) {
                System.out.println("Could not connect to client");
                // e.printStackTrace();
            }
        }
        db.close();
    }
}
