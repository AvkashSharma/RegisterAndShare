import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class Server implements Runnable {

    private final int bufferSize;
    private DatagramSocket socket; 
    private int port;
    private volatile boolean isShutDown = false;  

    public Server(int port, int bufferSize){
        this.bufferSize = bufferSize;
        this.port = port; 
    }

    public Server(int port){
        this(port,1024); 
    }

    public void run() {
        System.out.println("----------------Server Listening on port " + port + "----------------");
        byte [] buffer = new byte[bufferSize]; 

        try {
            socket = new DatagramSocket(port);
            while(true){

                DatagramPacket incoming  = new DatagramPacket(buffer, buffer.length);
                
                try {
                    socket.receive(incoming);

                    // Need to pass received data
                    ClientHandler clientHandler = new ClientHandler(incoming, socket);

                    // Create a new Thread
                    Thread threadClientHandler = new Thread(clientHandler);

                    // Start the thread
                    threadClientHandler.start();
                } catch(SocketTimeoutException ex){
                    System.out.println("SocketTimeoutException: " + ex.getMessage());

                } catch(IOException ex){
                    System.out.println("IOException " + ex.getMessage());
                }            
            } // end while
        } catch (SocketException ex){
            System.out.println("SocketException: " + ex.getMessage());
        }
    }

    public void shutDown(){
        this.isShutDown = true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Port Number for server");

        int portNumber = sc.nextInt();  
        
        Server server = new Server(portNumber);
        Thread serverThread = new Thread(server);
        serverThread.start();
        sc.close();
    }
}

