package handlers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.List;

import db.Database;
import db.User;
import server.Server;
import requests.Registration.RegisterRequest;
import requests.Registration.ServerRegisterConfirmed;
import requests.Registration.ServerRegisterDenied;
import requests.Update.ChangeServer;
import requests.Update.SubjectsRequest;
import requests.Update.UpdateConfirmed;
import requests.Update.UpdateRequest;
import requests.Update.UpdateServer;
import requests.server.ServeConfirmed;
import requests.server.ServeRequest;
import server.ServerData;
import requests.ClientPingServer;
import requests.RequestType;
import requests.server.ServerPingServer;
import requests.Publish.MessageConfirmation;
import requests.Publish.PublishDenied;
import requests.Publish.PublishRequest;
import requests.Registration.ClientRegisterConfirmed;
import requests.Registration.ClientRegisterDenied;
import requests.Registration.DeRegisterConfirmed;
import requests.Registration.DeRegisterRequest;
import requests.Registration.DeRegisterServerToServer;
import requests.Registration.DisconnectClientServerToServer;
import requests.Registration.DisconnectRequest;
import requests.Registration.DisconnectionConfirmed;

public class ClientReceiver implements Runnable {

    private DatagramPacket packetReceived;
    // client socket name is not appropriate. this is the server socket that is used to send response
    private DatagramSocket clientSocket;
    byte[] dataBuffer;
    Thread threadClientReceiver;

    public ClientReceiver(DatagramPacket packetReceived, DatagramSocket clientSocket) {
        this.packetReceived = packetReceived;
        this.clientSocket = clientSocket;

        threadClientReceiver = new Thread(this);
        threadClientReceiver.start();
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

            //output to file
            Writer.appendToFile(o);
            //track the received request
            Tracker.receivedRequest(o, packetReceived);
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
                e.printStackTrace();
            }
        }
            else if (request instanceof UpdateServer) {
              updateServer((UpdateServer)request);
        }

        // client requests
        else if (ServerData.isServing.get()) {
            if (request instanceof RegisterRequest) {
                register((RegisterRequest) request);

            } else if (request instanceof DeRegisterRequest) {
                deregister((DeRegisterRequest) request);

            }
              else if (request instanceof DisconnectRequest){
                disconnect((DisconnectRequest) request);
            }
            
            else if (request instanceof AvailableListOfSubjects) {
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
                update((UpdateRequest) request);

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

            }  else if (request instanceof ClientPingServer) {
                System.out.println("Client Pinging");
                try {
                    ((ClientPingServer) request).setActive(true);
                    ClientSender.sendResponse(request, packetReceived, clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("No such request present to handle the case");
            }
        }
    }

    public void updateServer(UpdateServer request){
        System.out.println("Backup server has changed location");
        ServerData.addressB.set(request.getAddress());
        ServerData.portB.set(request.getPort());
    }
    /**
     * Upon reception of this message the current server, can accept or refuse the
     * registration.
     * <p>
     * Registration can be denied if the provided Name is already in use if
     * Registration is accepted send Registered packet else send Register-Denied
     * 
     * Serving server needs to inform the other server with the outcome of the
     * registration, accepted or denied using the messages
     * 
     * ServerRegistrationConfirmed
     * 
     * else ServerRegisterDenied
     */
    public void register(RegisterRequest request) {
        try {
            String username = request.getClientName();
            boolean dbResponse = false;
            Database db = new Database();
            if (!db.userExist(username)) {
                dbResponse = db.addUser(username, request.getAddress(), request.getPort());
                db.close();
                if (dbResponse) {
                    ClientRegisterConfirmed confirmation = new ClientRegisterConfirmed(request.getRid());
                    ClientSender.sendResponse(confirmation, packetReceived, clientSocket);

                    // Send confirmation to IDLE server
                    ServerRegisterConfirmed serverConfirmation = new ServerRegisterConfirmed(request.getRid(), request.getClientName(), request.getAddress(), request.getPort());
                    System.out.print("ACTIVE TO IDLE: ");
                    System.out.println(serverConfirmation.toString());
                    ServerSender.sendResponse(serverConfirmation,clientSocket);
                    
                } else {
                    ClientRegisterDenied denied = new ClientRegisterDenied("Problem with database", request.getRid());
                    ClientSender.sendResponse(denied, packetReceived, clientSocket);

                    ServerRegisterDenied serverDenied = new ServerRegisterDenied(request.getRid(), request.getClientName(), request.getAddress(), request.getPort());
                    System.out.print("ACTIVE TO IDLE: ");
                    System.out.println(serverDenied.toString());
                    ServerSender.sendResponse(serverDenied,clientSocket);
                }
            } else {
                
                ClientRegisterDenied denied = new ClientRegisterDenied("Username exists already", request.getRid());
                ClientSender.sendResponse(denied, packetReceived, clientSocket);

                ServerRegisterDenied serverDenied = new ServerRegisterDenied(request.getRid(), request.getClientName(), request.getAddress(), request.getPort());
                System.out.print("ACTIVE TO IDLE: ");
                System.out.println(serverDenied.toString());
                ServerSender.sendResponse(serverDenied,clientSocket);
            }
            //remove from tracker
            Tracker.stop(request.getRid(), packetReceived);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * If name is already registered, the current server will remove the name and
     * all the information related to this user.
     * 
     * Also, inform the other server about this using DeregisterServerToServer
     * 
     * Upon reception of this message the current server can accept the update and
     * reply to the user using the message Check if name exists
     * <p>
     * if not end UpdateDenied
     * <p>
     * else Send UpdateConfirmed to client Send UpdateConfirmed to secondServer
     * 
     * @param request
     */
    public void deregister(DeRegisterRequest request) {
        try {
            String username = request.getClientName();
            boolean dbResponse = false;
            Database db = new Database();
            if (db.userExist(username)) {
                dbResponse = db.removeUser(username);
                db.close();
                if (!dbResponse) {
                    DeRegisterConfirmed confirmation = new DeRegisterConfirmed();
                    ClientSender.sendResponse(confirmation, packetReceived, clientSocket);

                    // send de-register confirmation to IDLE server
                    DeRegisterServerToServer serverConfirmation = new DeRegisterServerToServer(username);
                    System.out.print("ACTIVE TO IDLE: DE-REGISTER");
                    System.out.println(serverConfirmation.toString());
                    ServerSender.sendResponse(serverConfirmation,clientSocket);
                }
            }
             //remove from tracker
             Tracker.stop(request.getRid(), packetReceived);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(DisconnectRequest request){
        try {
            String username = request.getClientName();
            boolean dbResponse = false;
            Database db = new Database();
            if (db.userExist(username)) {
                db.close();
                if (!dbResponse) {
                    DisconnectionConfirmed confirmation = new DisconnectionConfirmed();
                    ClientSender.sendResponse(confirmation, packetReceived, clientSocket);

                    // send disconnection confirmation to IDLE server
                    DisconnectClientServerToServer serverConfirmation = new DisconnectClientServerToServer(username);
                    System.out.print("ACTIVE TO IDLE: DISCONNECT CLIENT");
                    System.out.println(serverConfirmation.toString());
                    ServerSender.sendResponse(serverConfirmation,clientSocket);
                }
            }
            Tracker.stop(request.getRid(), packetReceived);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(UpdateRequest request) {
        try {
            String username = request.getClientName();
            boolean dbResponse = false;
            Database db = new Database();
            if (db.userExist(username)) {
                dbResponse = db.updateUser(request.getClientName(), request.getAddress(), request.getPort());
                db.close();
                if (dbResponse) {
                    UpdateConfirmed updateConfirmed = new UpdateConfirmed(request.getRid(), request.getClientName(),
                            request.getAddress(), request.getPort());
                    ClientSender.sendResponse(updateConfirmed, packetReceived, clientSocket);

                    System.out.print("ACTIVE TO IDLE: DE-REGISTER");
                    System.out.println(updateConfirmed.toString());
                    ServerSender.sendResponse(updateConfirmed,clientSocket);
                }
            }
            Tracker.stop(request.getRid(), packetReceived);
            
        } catch (IOException e) {
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
        db.close();
    }

    public void sendListOfSubjects(AvailableListOfSubjects request) {

        String username = request.getClientName();
        Database db = new Database();
        try {
            // Check if user is registered
            if (db.userExist(username)) {
                List<String> subjects = db.getSubjects();
                db.close();
                // System.out.println("These are available subjects "+subjects);
                String list = "";
                for (String subject : subjects) {
                    list += subject;
                        if (subjects.get(subjects.size()-2)==subject){
                            list+=" and ";
                        }
                       else if(!(subjects.get(subjects.size()-1)==subject)){
                            list+=", ";
                        }
                        
                }
                ClientSender.sendResponse("\n\t Choose among the following subjects: " + list, packetReceived,
                        clientSocket);
            } else {
                String noSubjects = "No subjects available";
                System.out.println(noSubjects);
                ClientSender.sendResponse(noSubjects, packetReceived, clientSocket);
            }
            Tracker.stop(request.getRid(), packetReceived);
        } catch (IOException e) {
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
                String subjectsInString="\n\tThe following are your subscribed subjects: ";
                List<String> subscribedList;
                for (int i = 0; i < subjects.size(); i++) {
                    subject = subjects.get(i);
                    // check if subject to be subscribed on is in the list of available subjects
                    contained = db.getSubjects().contains(subject);
                    alreadyExist = db.getFavoriteSubjects(username).contains(subject);
                    if (contained) {
                        if (!alreadyExist) {
                            db.addFavoriteSubject(username, subject);
                            reply = "\n\t" + "SUBJECTS-UPDATED "+request.getRid()+" "+request.getClientName()+" "+subject;
                            ClientSender.sendResponse(reply, packetReceived, clientSocket);
                        } else {
                            reply = "\n\t" + "SUBJECTS-REJECTED "+ request.getRid()+" "+ request.getClientName()+" "+subject +" was already in the list";
                            ClientSender.sendResponse(reply, packetReceived, clientSocket);
                        }
                        // System.out.println(subject+" is in the available subjects");

                    }

                    else {
                        // System.out.println(subject+" is not in the available subjects");
                        reply = "\n\t"+"SUBJECTS-REJECTED"+" "+ request.getRid()+" "+ request.getClientName()+" "+subject + " is not available";
                        ClientSender.sendResponse(reply, packetReceived, clientSocket);
                    }
                }        
                subscribedList = db.getFavoriteSubjects(username);
                //turning the list into a string
                for(String sub:subscribedList){
                   subjectsInString+=sub;
                   if (subscribedList.get(subscribedList.size()-2)==sub){
                            subjectsInString+=" and ";
                        }
                       else if(!(subscribedList.get(subscribedList.size()-1)==sub)){
                           subjectsInString+=", ";
                        }
                }
                ClientSender.sendResponse(subjectsInString, packetReceived, clientSocket);

                System.out.print("ACTIVE TO IDLE: Users Updating their subject of interest");
                System.out.println(subscribedList.toString());
                ServerSender.sendResponse(subscribedList,clientSocket);

            } else {
                String denied = "The user does not exist";
                ClientSender.sendResponse(denied, packetReceived, clientSocket);
            }
            Tracker.stop(request.getRid(), packetReceived);
            db.close();
        } catch (IOException e) {
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
            Tracker.stop(request.getRid(), packetReceived);
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
