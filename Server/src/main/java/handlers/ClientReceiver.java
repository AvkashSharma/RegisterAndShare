package handlers;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

import requests.Registration.RegisterRequest;
import requests.Update.ChangeServer;
import requests.Update.SubjectsRequest;
import requests.Update.UpdateRequest;
import requests.Update.UpdateServer;
import requests.Publish.PublishRequest;
import requests.Registration.ClientRegisterConfirmed;
import requests.Registration.ClientRegisterDenied;
import requests.Registration.DeRegisterRequest;

public class ClientReceiver implements Runnable {

    private DatagramPacket packetReceived;
    private DatagramSocket clientSocket;
    private String receivedData;
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
            System.out.println(is);
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

        if (request instanceof RegisterRequest) {
            register(request);
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

            System.out.println("Received DeRegisterRequest");

            // If name is already registered, the current server will remove the name and
            // all the information related to this user.

            // Also, inform the other server about this using DeregisterServerToServer

            // in the case Name is not registered the message is just ignored by the current
            // server. No further action is required

        } else if (request instanceof UpdateRequest) {
            System.out.println(" User Update Request Received");

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
            System.out.println("Publish Request Received");
            // check name of the user , subject and if the subject is in the list of
            // subjects of interest for the user.
            // if yes, send MessageConfirmation to all users who have this subject in their
            // list of interest
            // in case of errors, send PublishDenied to the original user
        } else if (request instanceof ChangeServer) {
            System.out.println("Received change server Request");
            // Server needs to inform all the registered users about Change server Request
        } else if (request instanceof UpdateServer) {
            // when a server is not serving it can change its IP address and socket#, but
            // informs only the current(serving) server with the following message
            System.out.println("Received Update Server Request");
        } else {
            System.out.println("No such request present to handle the case");
        }
    }
    public void register(Object request){
        System.out.println("RegisterRequest");
        try {
            ClientRegisterConfirmed confirmation = new ClientRegisterConfirmed(1);
            ClientSender.sendResponse(confirmation, packetReceived, clientSocket);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}