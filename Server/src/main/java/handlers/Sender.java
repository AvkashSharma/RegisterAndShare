package handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;


//used to send meesages
public class Sender {

    public static void sendTo(Object object, DatagramSocket datagramSocket, String address, int port)
            throws IOException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);

            os.writeObject(object);

            byte[] data = outputStream.toByteArray();
            InetAddress add = InetAddress.getByName(address);
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, add, port);
            datagramSocket.send(sendPacket);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}