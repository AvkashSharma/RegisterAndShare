package requests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class ClientPingServer implements Serializable {
    private boolean active;

    public ClientPingServer(boolean active) {
        this.active = active;
    }

    public static void ping(String ipAddress, int port) throws IOException {
        try {
            ClientPingServer pingServer = new ClientPingServer(true);
            DatagramSocket datagramSocket = new DatagramSocket();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);

            os.writeObject(pingServer);

            byte[] data = outputStream.toByteArray();
            byte[] incomingData = new byte[1024];
            InetAddress add = InetAddress.getByName(ipAddress);
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, add, port);
            datagramSocket.send(sendPacket);
            System.out.println("Pinging Server");

            // Wait for server response
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            datagramSocket.receive(incomingPacket);
            String response = new String(incomingPacket.getData());

            datagramSocket.setSoTimeout(1000);
            byte[] dataBuffer = incomingPacket.getData();
            ByteArrayInputStream byteStream = new ByteArrayInputStream(dataBuffer);
            ObjectInputStream is = new ObjectInputStream(byteStream);

            try {
                ClientPingServer o = (ClientPingServer) is.readObject();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("server not avaiable");
            }

             System.out.println("Response from server:" + response);



        } catch (UnknownHostException e) {
            System.out.println("server not avaiable");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("server not avaiable");
            e.printStackTrace();
        }
    }
}
