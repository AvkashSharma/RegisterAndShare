package handlers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.Arrays;
import java.util.List;

import db.Database;
import db.User;
import server.Server;
import requests.Registration.RegisterRequest;
import requests.Update.ChangeServer;
import requests.Update.SubjectsRejected;
import requests.Update.SubjectsRequest;
import requests.Update.UpdateRequest;
import requests.Update.UpdateServer;
import requests.server.ServeConfirmed;
import requests.server.ServeRequest;
import requests.server.ServeRequest.*;
import server.ServerData;
import requests.ClientPingServer;
import requests.ServerPingServer;
import requests.Publish.MessageConfirmation;
import requests.Publish.PublishDenied;
import requests.Publish.PublishRequest;
import requests.Registration.ClientRegisterConfirmed;
import requests.Registration.ClientRegisterDenied;
import requests.Registration.DeRegisterConfirmed;
import requests.Registration.DeRegisterRequest;
import requests.Registration.LoginRequest;

public class ClientReceiver implements Runnable {

    private DatagramPacket packetReceived;
    private DatagramSocket clientSocket;
    byte[] dataBuffer;

    public ClientReceiver(DatagramPacket packetReceived, DatagramSocket clientSocket) {
        this.packetReceived = packetReceived;
        this.clientSocket = clientSocket;
    }

    public void run() {

        try {
            this.dataBuffer = packetReceived.getData();
            System.out.println("Processing received data");

            ByteArrayInputStream byteStream = new ByteArrayInputStream(dataBuffer);
            ObjectInputStream is = new ObjectInputStream(byteStream);
            // System.out.println(is);
            Object o = (Object) is.readObject();

            System.out.println(o.toString());
            if (o instanceof RegisterRequest) {
                System.out.println("register request");
                System.out.println(o.toString());
            } else if (o instanceof ClientRegisterDenied) {
                System.out.println("register denied");
            }

            // RegisterRequest respondMessage = new RegisterRequest("Object from server
            // "+o.getClientName(), new InetSocketAddress(InetAddress.getLocalHost(),
            // 1234));

            // ClientSender.sendResponse(o, packetReceived, clientSocket);
            Writer.appendToFile(o);
            requestHandler(o);

            is.close();

        } catch (IOException e) {
            System.err.println("Exception:  " + e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized void requestHandler(Object request) {

        // Server requests
        if (request instanceof ServerPingServer) {
            System.out.println("Received ping from server");
            ((ServerPingServer) request).setIsServing(ServerData.isServing.get());
            try {
                ClientSender.sendResponse(request, packetReceived, clientSocket);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (request instanceof ServeRequest) {
            System.out.println("Server requested to go online");
            // Server server and start the timer
            Server.serve();
            // let know the other server its online
            try {

                ClientSender.sendResponse(new ServeConfirmed("Serving"), packetReceived, clientSocket);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // client requests
        else if (ServerData.isServing.get()) {
            if (request instanceof RegisterRequest) {
                register((RegisterRequest) request);
                // Upon reception of this message the current server, can accept or refuse the
                // registration.
                // Registration can be denied if the provided Name is already in use
                // if Registration is accepted send Registered packet
                // else send Register-Denied

                // Serving server needs to inform the other server with the outcome of the
                // registration, accepted or denied using the messages

                // ServerRegistrationConfirmed

                // else ServerRegisterDenied

            } else if (request instanceof DeRegisterRequest) {
                deregister((DeRegisterRequest) request);

                // If name is already registered, the current server will remove the name and
                // all the information related to this user.

                // Also, inform the other server about this using DeregisterServerToServer

                // Upon reception of this message the current server can accept the update and
                // reply to the user using the message
                // Check if name exists
                // if not send UpdateDenied
                // else Send UpdateConfirmed to client Send UpdateConfirmed to secondServer
            } else if (request instanceof AvailableListOfSubjects) {
                sendListOfSubjects((AvailableListOfSubjects) request);
            } else if (request instanceof SubjectsRequest) {
                subscribeToSubjects((SubjectsRequest) request);

                // System.out.println("Update Subjects ");
                // send a confirmation if the subject subscription is confirmed on not
                // current server can accept the update or reject it because of errors in the
                // name or in the list of subjects.
                // check for errors in the name or in the list of subjects
                // in the case of accept request send SubjectsUpdated to the user and to the
                // other server
                // in the case of denial send SubjectsRejected to the user
            } else if (request instanceof UpdateRequest) {
                System.out.println(request.toString());

                // Upon reception of this message the current server can accept the update and
                // reply to the user using the message
                // Check if name exists
                // if not send UpdateDenied
                // else Send UpdateConfirmed to client Send UpdateConfirmed to secondServer
            } else if (request instanceof SubjectsRequest) {
                System.out.println("Update Subjects ");
                // current server can accept the update or reject it because of errors in the
                // name or in the list of subjects.
                // check for errors in the name or in the list of subjects
                // in the case of accept request send SubjectsUpdated to the user and to the
                // other server
                // in the case of denial send SubjectsRejected to the user
            } else if (request instanceof PublishRequest) {

                publish((PublishRequest) request);

            } else if (request instanceof ChangeServer) {
                System.out.println("Received change server Request");
                // Server needs to inform all the registered users about Change server Request
            } else if (request instanceof UpdateServer) {
                // when a server is not serving it can change its IP address and socket#, but
                // informs only the current(serving) server with the following message
                System.out.println("Received Update Server Request");
            } else if (request instanceof ClientPingServer) {
                System.out.println("Client Pinging");
                try {
                    ((ClientPingServer) request).setActive(true);
                    ClientSender.sendResponse(request, packetReceived, clientSocket);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            else if (request instanceof LoginRequest) {
                System.out.println();
            } else {
                System.out.println("No such request present to handle the case");
            }
        }
    }

    public void register(RegisterRequest request) {
        try {
            String username = request.getClientName();
            boolean dbResponse = false;
            Database db = new Database();
            if (!db.userExist(username)) {
                dbResponse = db.addUser(username, request.getAddress(), request.getPort());
                if (dbResponse) {
                    ClientRegisterConfirmed confirmation = new ClientRegisterConfirmed(request.getRid());
                    ClientSender.sendResponse(confirmation, packetReceived, clientSocket);
                    return;
                } else {
                    ClientRegisterDenied denied = new ClientRegisterDenied("Problem with database", request.getRid());
                    ClientSender.sendResponse(denied, packetReceived, clientSocket);
                }
            } else {
                ClientRegisterDenied denied = new ClientRegisterDenied("Username exists", request.getRid());
                ClientSender.sendResponse(denied, packetReceived, clientSocket);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void deregister(DeRegisterRequest request) {
        try {
            String username = request.getClientName();
            boolean dbResponse = false;
            Database db = new Database();
            if (db.userExist(username)) {
                dbResponse = db.removeUser(username);
                if (!dbResponse) {
                    DeRegisterConfirmed confirmation = new DeRegisterConfirmed();
                    ClientSender.sendResponse(confirmation, packetReceived, clientSocket);
                    return;
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // This is a helper method used to add subjects.
    public void addSubjects() {
        Database db = new Database();

        // db.addFavoriteSubject("avkash", "Food");
        // db.addFavoriteSubject("avkash", "Formula1");
        // db.addFavoriteSubject("avkash", "Sports");

        db.addFavoriteSubject("tom", "Food");
        db.addFavoriteSubject("tom", "Formula1");
        db.addFavoriteSubject("tom", "Sports");

    }

    public void sendListOfSubjects(AvailableListOfSubjects request) {

        String username = request.getClientName();
        Database db = new Database();
        try {
            // Check if user is registered
            if (db.userExist(username)) {
                List<String> subjects = db.getSubjects();
                // System.out.println("These are available subjects "+subjects);
                String list = "";
                for (String subject : subjects) {
                    list += " " + subject;
                }
                ClientSender.sendResponse("\n\t Choose among the following subjects: " + list, packetReceived,
                        clientSocket);
            } else {
                String noSubjects = "No subjects available";
                System.out.println(noSubjects);
                ClientSender.sendResponse(noSubjects, packetReceived, clientSocket);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void subscribeToSubjects(SubjectsRequest request) {
        String username = request.getClientName();
        List<String> subjects = request.getSubjectsToSubscribe();
        Database db = new Database();
        try {
            // check if user is registered
            if (db.userExist(username)) {
                // check if the subject exist
                String subject = "";
                boolean contained;
                boolean alreadyExist;
                String reply = "";
                String subscribedSubjects = "";
                List<String> subscribedList;
                for (int i = 0; i < subjects.size(); i++) {
                    subject = subjects.get(i);
                    // check if subject to be subscribed on is in the list of available subjects
                    contained = db.getSubjects().contains(subject);
                    alreadyExist = db.getFavoriteSubjects(username).contains(subject);
                    if (contained) {
                        if (!alreadyExist) {
                            db.addFavoriteSubject(username, subject);
                            reply = "\n\t" + subject + " has been added to your subscribed subjects";
                            ClientSender.sendResponse(reply, packetReceived, clientSocket);
                        } else {
                            reply = "\n\t" + subject + " was already in your subscribed subjects";
                            ClientSender.sendResponse(reply, packetReceived, clientSocket);
                        }
                        // System.out.println(subject+" is in the available subjects");

                    }

                    else {
                        // System.out.println(subject+" is not in the available subjects");
                        reply = "\n\t" + subject + " is not available in the available subject and has not been added";
                        ClientSender.sendResponse(reply, packetReceived, clientSocket);
                    }
                }
                subscribedSubjects = "\n\tThe following are your subscribed subjects: ";
                ClientSender.sendResponse(subscribedSubjects, packetReceived, clientSocket);
                subscribedList = db.getFavoriteSubjects(username);
                ClientSender.sendResponse("\n\t" + subscribedList, packetReceived, clientSocket);

            } else {
                String denied = "The user does not exist";
                ClientSender.sendResponse(denied, packetReceived, clientSocket);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void publish(PublishRequest request) {

        String username = request.getClientName();
        String subject = request.getSubject();
        String message = request.getText();

        Database db = new Database();

        try {
            // Check if user is registered
            if (db.userExist(username)) {
                // Check subject
                if (db.subjectExist(subject)) {

                    // if the subject is in the list of subjects of interest for the user
                    List<String> subjects = db.getFavoriteSubjects(username);

                    if (subjects.contains(subject)) {

                        // user adds a message to the subject
                        db.addMessage(username, subject, message);

                        // get list of all users subscribed to that subject
                        List<User> subscribedUsers = db.getSubjectUsers(subject);
                        // dispatch the message to all the subscribed users
                        for (User user : subscribedUsers) {
                            MessageConfirmation confirmation = new MessageConfirmation(username, subject, message);
                            System.out.println(user.getUsername());
                            System.out.println(user.getUserSocket());
                            // Send confirmation to all users
                            // DatagramSocket socket = new DatagramSocket(user.getUserSocket());

                            byte[] buffer = new byte[1024];
                            // InetAddress address = InetAddress.getByName(user.getUserIP());

                            // TODO can only test on LocalHost
                            InetAddress address = InetAddress.getLocalHost();
                            SocketAddress socketAddress = new InetSocketAddress(address, user.getUserSocket());

                            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, socketAddress);

                            ClientSender.sendResponse(confirmation, packet, clientSocket);
                        }
                    } else {
                        // publish denied
                        PublishDenied denied = new PublishDenied(request.getRid(),
                                "Subject is not in present in your list of subjects");
                        ClientSender.sendResponse(denied, packetReceived, clientSocket);
                    }
                } else {
                    // handle user does not have subject in the list
                    PublishDenied denied = new PublishDenied(request.getRid(), "Subject does not exist");
                    ClientSender.sendResponse(denied, packetReceived, clientSocket);
                }
            } else {
                // handle username not registered
                PublishDenied denied = new PublishDenied(request.getRid(), "You are not registered. Please register");
                ClientSender.sendResponse(denied, packetReceived, clientSocket);
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // let know the clients that server has changed address
    public void changeServer() {

        Database db = new Database();

        List<User> Users = db.getUsers();
        for (User user : Users) {
            ChangeServer changeServer = new ChangeServer(ServerData.addressB.get(), ServerData.portB.get());
            System.out.println(user.getUsername() + " " + user.getUserSocket());
            byte[] buffer = new byte[1024];
            
            SocketAddress socketAddress = new InetSocketAddress(user.getUserIP(), user.getUserSocket());
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, socketAddress);

            try {
                ClientSender.sendResponse(changeServer, packet, clientSocket);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("Could not connect to client");
                e.printStackTrace();
            }
        }
    }

}
