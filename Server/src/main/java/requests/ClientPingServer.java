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
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class ClientPingServer implements Serializable {
    /**
     *
     */
    private boolean active;
    private InetSocketAddress clientSocketAddress;

    public ClientPingServer(boolean active, InetSocketAddress address) {
        this.setActive(active);
        this.setClientSocketAddress(address);
    }

    public InetSocketAddress getClientSocketAddress() {
        return clientSocketAddress;
    }

    public void setClientSocketAddress(InetSocketAddress clientSocketAddress) {
        this.clientSocketAddress = clientSocketAddress;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString(){
        return "CLIENT PING "+ clientSocketAddress;
    }

    public static Boolean ping(String ipAddress, int port) throws IOException {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);

            //send ping object to server
            ClientPingServer pingServer = new ClientPingServer(true, new InetSocketAddress(ipAddress, port));
            os.writeObject(pingServer);

            byte[] data = outputStream.toByteArray();
            byte[] incomingData = new byte[1024];
            InetAddress add = InetAddress.getByName(ipAddress);
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, add, port);
            datagramSocket.send(sendPacket);
            System.out.println("Pinging Server @"+ipAddress+":"+port);

            // Wait for server response
            datagramSocket.setSoTimeout(1000);
            DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            datagramSocket.receive(incomingPacket);

            
            byte[] dataBuffer = incomingPacket.getData();
            ByteArrayInputStream byteStream = new ByteArrayInputStream(dataBuffer);
            ObjectInputStream is = new ObjectInputStream(byteStream);
            datagramSocket.close();

            try {
                ClientPingServer o = (ClientPingServer) is.readObject();
                
            } catch (ClassNotFoundException e) {
                System.out.println("Not able to serialize response");
            }
            return true;

        } catch (UnknownHostException e) {
            System.out.println("Unknown address: "+ipAddress+":"+port);
            // e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Connection timeout for "+ipAddress+":"+port);
            // e.printStackTrace();
        }
        return false;
    }
}
