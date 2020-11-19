import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

import Requests.Registration.RegisterRequest;

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
                        RegisterRequest o = (RegisterRequest)is.readObject();
                        System.out.println(o);

                        RegisterRequest respondMessage = new RegisterRequest("Object from server "+o.getClientName(), new InetSocketAddress(InetAddress.getLocalHost(), 1234));
                        serverResponse.sendResponse(respondMessage, packetReceived,clientSocket);

                        is.close();
                        
                    } catch (IOException e) {
                        System.err.println("Exception:  " + e);
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
        }
    }

