package requests.server;

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

import server.ServerData;

/**
 * Server pings the other server to know if it is up and running
 */
public class ServerPingServer implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String type = "Server";
    private Boolean isServing;
    private String ipAddress;
    private int port;

    public ServerPingServer(Boolean isServing, String ipAddress, int port) {
        this.isServing = isServing;
        this.setIpAddress(ipAddress);
        this.setPort(port);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Boolean getIsServing() {
        return isServing;
    }

    public void setIsServing(Boolean isServing) {
        this.isServing = isServing;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SERVER PING" + " " + this.getIsServing() + " " + getIpAddress() + ":" + getPort();
    }

    public void print() {
        System.out.println(this.toString());
    }

    // return is Serving status
    public static Boolean ping(String ipAddress, int port) throws IOException {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);

            // send ping object to server
            ServerPingServer pingServer = new ServerPingServer(ServerData.isServing.get(), ipAddress, ServerData.port.get());
            os.writeObject(pingServer);

            byte[] data = outputStream.toByteArray();
            byte[] incomingData = new byte[1024];
            InetAddress add = InetAddress.getByName(ipAddress);
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, add, port);
            datagramSocket.send(sendPacket);
            System.out.println("Pinging Server @" + ipAddress + ":" + port);

            // Wait for server response
            datagramSocket.setSoTimeout(1000);
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            datagramSocket.receive(incomingPacket);

            byte[] dataBuffer = incomingPacket.getData();
            ByteArrayInputStream byteStream = new ByteArrayInputStream(dataBuffer);
            ObjectInputStream is = new ObjectInputStream(byteStream);
            datagramSocket.close();

            try {
                ServerPingServer o = (ServerPingServer) is.readObject();

            } catch (ClassNotFoundException e) {
                System.out.println("Not able to serialize response");
            }
            return true;

        } catch (UnknownHostException e) {
            System.out.println("Unknown address: " + ipAddress + ":" + port);
        } catch (IOException e) {
            System.out.println("Connection timeout for " + ipAddress + ":" + port);
        }
        return false;
    }

}
