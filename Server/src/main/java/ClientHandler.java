import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

import Requests.Registration.RegisterRequest;
import Requests.Update.UpdateRequest;
import Requests.Registration.ClientRegisterDenied;
import Requests.Registration.DeRegisterRequest;

public class ClientHandler implements Runnable {

    private DatagramPacket packetReceived;
    private DatagramSocket clientSocket;
    private String receivedData;
    byte[] dataBuffer;


    public ClientHandler(DatagramPacket packetReceived, DatagramSocket clientSocket)
    {
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
                        Object o=(Object)is.readObject();
                        // RegisterRequest o = (RegisterRequest)is.readObject();
                        System.out.println(o.toString());
                        if(o instanceof RegisterRequest )
                        {
                            System.out.println("register request");
                        System.out.println(o.toString());
                        }
                        else if(o instanceof ClientRegisterDenied){
                            System.out.println("register denied");
                        }

                        // RegisterRequest respondMessage = new RegisterRequest("Object from server "+o.getClientName(), new InetSocketAddress(InetAddress.getLocalHost(), 1234));
                        serverResponse.sendResponse(o, packetReceived,clientSocket);

                        is.close();
                        
                    } catch (IOException e) {
                        System.err.println("Exception:  " + e);
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
        }


        public void requestHandler(Object request){

            if (request instanceof RegisterRequest){
                System.out.println("RegisterRequest");

                // Upon reception of this message the current server, can accept or refuse the registration. 
                // Registration can be denied if the provided Name is already in use
                // if Registration is accepted send Registered packet
                // else send Register-Denied


                // Serving server needs to inform the other server with the outcome of the registration, accepted or denied using the messages

                // ServerRegistrationConfirmed

                // else ServerRegisterDenied

            }
            else if(request instanceof DeRegisterRequest){

                System.out.println("Received DeRegisterRequest");

                // If name is already registered, the current server will remove the name and all the information related to this user.

                // Also, inform the other server about this using DeregisterServerToServer

                // in the case Name is not registered the message is just ignored by the current server. No further action is required

            }
            else if(request instanceof UpdateRequest){
                System.out.println("Update Request Received");

                // Upon reception of this message the current server can accept the update and reply to the user using the message
                // Check if name exists
                    // if not send UpdateDenied
                    // else Send UpdateConfirmed to client Send UpdateConfirmed to secondServer
            }
            


        }


    }


    
