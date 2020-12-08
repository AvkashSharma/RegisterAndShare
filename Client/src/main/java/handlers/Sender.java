package handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import client.ClientData;



//used to send meesages
public class Sender {

    public static void sendTo(Object object, DatagramSocket datagramSocket)
            throws IOException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);

            os.writeObject(object);
            Writer.sendRequestToFile(object, ClientData.ACTIVE_IP, ClientData.ACTIVE_PORT);

            Tracker.handleSentRequest(object);

            byte[] data = outputStream.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, ClientData.activeServerAddress, ClientData.ACTIVE_PORT);
            datagramSocket.send(sendPacket);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendTo(Object object, DatagramSocket datagramSocket, String address, int port)
            throws IOException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);

            os.writeObject(object);
            Writer.sendRequestToFile(object, address, port);

            Tracker.handleSentRequest(object);


            byte[] data = outputStream.toByteArray();
            InetAddress addr =InetAddress.getByName(address);
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, addr, port);
            datagramSocket.send(sendPacket);
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
