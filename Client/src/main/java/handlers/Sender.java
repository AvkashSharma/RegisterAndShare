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

    public static void sendTo(Object object, InetAddress address, int port, DatagramSocket datagramSocket) throws IOException {
        try {
            // DatagramSocket datagramSocket = new DatagramSocket();
            byte[] incomingData = new byte[1024];
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(outputStream);

            os.writeObject(object);

            byte[] data = outputStream.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, address, port);
            datagramSocket.send(sendPacket);
            System.out.println("Message sent from client");


            // Wait for server response
            // DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
            // datagramSocket.receive(incomingPacket);
            
            // byte[] dataBuffer = incomingPacket.getData();
            // ByteArrayInputStream byteStream = new ByteArrayInputStream(dataBuffer);
            // ObjectInputStream is = new ObjectInputStream(byteStream);
            // RegisterMessage o = (RegisterMessage)is.readObject();
            // System.out.println(o);

            // Thread.sleep(2000);

            // datagramSocket.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        // catch (ClassNotFoundException e) {
        //     e.printStackTrace();
        // }
    }
}
