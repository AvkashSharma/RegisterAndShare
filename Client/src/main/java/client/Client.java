package client;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
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
import requests.Registration.LoginRequest;
import requests.Registration.RegisterRequest;
import requests.Update.SubjectsRequest;
import requests.Update.SubjectsUpdated;
import requests.Update.UpdateRequest;

public class Client {

    public final Scanner scanner = new Scanner(System.in);
    public static DatagramSocket clientSocket;

    public Client() {
        ClientData.checkActiveServer(scanner);
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
            System.out.println("Enter 'ctrl+C' to exit Client, Press 'ENTER' to refresh");

            if (!ClientData.isRegistered.get()) {
                System.out.println("1-Register");
                System.out.println("2-Login");
            } else {
                System.out.println("Logged in as " + ClientData.username.get());
                System.out.println("2-Deregister");
                System.out.println("3-Update User location(ip, port)");
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
                        login();
                    else
                        deregister();
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

    public void register() {
        System.out.print("\tEnter Username to register: ");
        String username = "";
        username = scanner.next();

        try {
            RegisterRequest registerMessage = new RegisterRequest(ClientData.requestCounter.incrementAndGet(), username,
                    ClientData.CLIENT_IP, ClientData.CLIENT_PORT);
            Sender.sendTo(registerMessage, clientSocket);
            ClientData.username.set(username);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void login() {
        System.out.println("\tEnter login username: ");
        String username = "";
        username = scanner.next();

        LoginRequest loginRequest = new LoginRequest(ClientData.requestCounter.incrementAndGet(), username,
                ClientData.CLIENT_IP, ClientData.CLIENT_PORT);
        try {
            Sender.sendTo(loginRequest, clientSocket);
            ClientData.username.set(username);
        } catch (IOException e) {
            // TODO Auto-generated catch block
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

    public void updateUser() {
        try {
            UpdateRequest uRequest = new UpdateRequest(ClientData.requestCounter.incrementAndGet(),
                    ClientData.username.get(), "127.0.0.1", "50002");

            Sender.sendTo(uRequest, clientSocket);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void getListOfSubjects(){
        List<String> listOfSubjects=new ArrayList<String>();
        AvailableListOfSubjects sRequest = new AvailableListOfSubjects(ClientData.requestCounter.incrementAndGet(), ClientData.username.get(),listOfSubjects);
        
        try {
            // Sender.sendTo(sRequest, activeServerIP, activeServerPort, clientSocket);
            Sender.sendTo(sRequest, clientSocket);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void subscribeToSubjects(){
        
        
        //confirm the request or deny it
        //update the database
        System.out.print("\tEnter the subject (enter exit when you're done): ");
        String subject ="";
        subject=scanner.next();
        List<String> subjectsToSubscribe=new ArrayList<String>();
        while(!subject.equals("exit")){
        subjectsToSubscribe.add(subject);
        System.out.print("\tEnter the subject (enter exit when you're done): ");
        subject=scanner.next();
        }
        SubjectsRequest sRequest = new SubjectsRequest(ClientData.requestCounter.incrementAndGet(), ClientData.username.get(),subjectsToSubscribe);
        
        try {
            
            Sender.sendTo(sRequest, clientSocket);
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
