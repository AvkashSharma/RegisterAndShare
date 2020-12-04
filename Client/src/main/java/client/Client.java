package client;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import handlers.*;
import requests.Publish.PublishRequest;
import requests.Registration.DeRegisterRequest;
import requests.Registration.RegisterRequest;
import requests.Update.SubjectsRequest;
import requests.Update.UpdateRequest;

public class Client {

    public final Scanner scanner = new Scanner(System.in);
    public static DatagramSocket clientSocket;

    public Client() {
        // ClientData.checkActiveServer(scanner);
        ClientData.getServerAddress(scanner);
        System.out.println("-----------------------------------------");

        try {
            clientSocket = new DatagramSocket();
            ClientData.CLIENT_IP = InetAddress.getLocalHost().getHostAddress().toString();
            ClientData.CLIENT_PORT = clientSocket.getLocalPort();
        } catch (SocketException | UnknownHostException e) {
            System.out.println("Socket Exception" + e.getMessage());
        }
    }

    public void start() {

        // Add a thread to listen to server messages

        // Thread to receive messages from the server.
        ServerReceiver receiver = new ServerReceiver(clientSocket);
        Thread receiverThread = new Thread(receiver);
        receiverThread.start();
        ui();
    }

    public void ui() {
        String val = "";
        while (!val.equals("exit")) {
            System.out.println("------------------@" + ClientData.CLIENT_IP + ":" + ClientData.CLIENT_PORT
                    + "------------------------");
            System.out.println("---------Connected: @" + ClientData.ACTIVE_IP + ":" + ClientData.ACTIVE_PORT
                    + "------------------------");
            System.out.println("Enter 'ctrl+C' to exit Client, Press 'ENTER' to refresh");

            if (!ClientData.isRegistered.get()) {
                System.out.println("1-Register");
                System.out.println("2-Update location");
                System.out.println("3-Configure server addresses");
            } else {
                System.out.println("Logged in as " + ClientData.username.get());
                System.out.println("2-Deregister");
                System.out.println("3-Update location(ip, port)");
                System.out.println("4-See Available list of Subjects");
                System.out.println("5-Publish message on subjects of interest");
                System.out.println("6-Choose a list of Subjects to subscribe on");
            }

            System.out.print("Choice: ");
            val = scanner.nextLine();

            if (val.isEmpty()) {
                val = "-1";
            }
            switch (val) {
                case "1":
                    register();
                    break;
                case "2":
                    if (!ClientData.isRegistered.get())
                        update(0);
                    else
                        deregister();
                    break;
                case "3":
                    if (!ClientData.isRegistered.get())
                       ClientData.getServerAddress(scanner);
                    else
                        update(1);
                    break;
                case "4":
                    getListOfSubjects();
                    break;
                case "5":
                    publishRequest();
                    break;
                case "6":
                    subscribeToSubjects();
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
     * When clients register, sends request to both server
     */
    public void register() {
        System.out.print("\tEnter Username to register: ");
        String username = "";
        username = scanner.next();

        try {
            RegisterRequest registerMessage = new RegisterRequest(ClientData.requestCounter.incrementAndGet(), username,
                    ClientData.CLIENT_IP, ClientData.CLIENT_PORT);
            // Sender.sendTo(registerMessage, clientSocket);
            Sender.sendTo(registerMessage, clientSocket, ClientData.SERVER_1_IP, ClientData.SERVER_1_PORT);
            Sender.sendTo(registerMessage, clientSocket, ClientData.SERVER_2_IP, ClientData.SERVER_2_PORT);
            ClientData.username.set(username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update user ip address and port
     * 
     * @param type 1 = user already logged in, 0 = user not logged in
     */
    public void update(int type) {
        UpdateRequest updateRequest;
        if (type == 1) {
            System.out.print("\tEnter IP address: ");
            String ip = scanner.next();
            System.out.print("\tEnter Port Number: ");
            int port = scanner.nextInt();
            updateRequest = new UpdateRequest(ClientData.requestCounter.incrementAndGet(), ClientData.username.get(),
                    ip, port);
        } else {
            System.out.print("\tEnter login username: ");
            String username = scanner.next();
            updateRequest = new UpdateRequest(ClientData.requestCounter.incrementAndGet(), username,
                    ClientData.CLIENT_IP, ClientData.CLIENT_PORT);
            ClientData.username.set(username);
        }

        try {
            Sender.sendTo(updateRequest, clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deregister() {
        System.out.print("\tDo you want to deregister " + ClientData.username.get() + "(y/n): ");
        String response = "";
        response = scanner.next();
        if (response.equals("n")) {
            return;
        }
        DeRegisterRequest deregisterMessage = new DeRegisterRequest(ClientData.requestCounter.incrementAndGet(),
                ClientData.username.get());
        try {
            Sender.sendTo(deregisterMessage, clientSocket);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void getListOfSubjects() {
        List<String> listOfSubjects = new ArrayList<String>();
        AvailableListOfSubjects sRequest = new AvailableListOfSubjects(ClientData.requestCounter.incrementAndGet(),
                ClientData.username.get(), listOfSubjects);

        try {
            Sender.sendTo(sRequest, clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subscribeToSubjects() {

        // confirm the request or deny it
        // update the database
        System.out.print("\tEnter the subject (enter exit when you're done): ");
        String subject = "";
        subject = scanner.next();
        List<String> subjectsToSubscribe = new ArrayList<String>();
        while (!subject.equals("exit")) {
            subjectsToSubscribe.add(subject);
            System.out.print("\tEnter the subject (enter exit when you're done): ");
            subject = scanner.next();
        }
        SubjectsRequest sRequest = new SubjectsRequest(ClientData.requestCounter.incrementAndGet(),
                ClientData.username.get(), subjectsToSubscribe);

        try {

            Sender.sendTo(sRequest, clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void publishRequest() {
        System.out.print("\tEnter subject of interest:  ");
        String subject = "";
        subject = scanner.next();
        System.out.print("\tEnter the message:  ");
        String message = "";
        message = scanner.next();
        message += scanner.nextLine();

        PublishRequest pRequest = new PublishRequest(ClientData.requestCounter.incrementAndGet(),
                ClientData.username.get(), subject, message);

        try {
            Sender.sendTo(pRequest, clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
