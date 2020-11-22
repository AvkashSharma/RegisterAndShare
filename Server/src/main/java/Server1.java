import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;
import java.util.Scanner;

import db.Database;

public class Server1 {

    // Server UDP socket runs at this port
    // public final static int SERVICE_PORT=50001;
    public static int SERVICE_PORT = 0;
    public static boolean SERVING = true; //if serving respond else ignore

    public static void main(String[] args) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address);

            Database db = new Database();
            // db.getUsers();
            // System.out.println(db.userExist("karthi"));
            // System.out.println(db.addUser("karthi1", "192.245.23.1", 2345));
            // System.out.println(db.removeUser("karthi1"));
            // System.out.println(db.updateUser("karthi1312", "192.245.23.1", 1234));
            // System.out.println(db.subjectExist("sports"));
            List<String> subjects = db.getSubjects();
            for (String string : subjects) {
                System.out.println(string);
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter port to run server(50000): ");
            // todo - validate that its a valid port
            SERVICE_PORT = scanner.nextInt();
            scanner.close();
            System.out.println("Server Started on port "+SERVICE_PORT+"...");



            DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);
            while (true) {

                byte[] dataBuffer = new byte[1024];

                /* Instantiate a UDP packet to store the
                client data using the buffer for receiving data*/
                DatagramPacket packetReceived = new DatagramPacket(dataBuffer, dataBuffer.length);

                // Receive data from the client and store in inputPacket
                serverSocket.receive(packetReceived);
                System.out.println("Receiveed packet");

                // Need to pass received data
                ClientHandler clientHandler  = new ClientHandler(packetReceived, serverSocket);

                // Create a new Thread
                Thread threadClientHandler = new Thread(clientHandler);


//                try {
//                    Thread.sleep(15000);
//
//                } catch(InterruptedException e) {
//                    System.out.println("Thread interrupted");
//
//                }

                // Start the thread
                threadClientHandler.start();

            }
        } catch (SocketException e) {
            System.out.println("SocketException: " + e.getMessage());
        }
        catch(IOException e)
        {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
