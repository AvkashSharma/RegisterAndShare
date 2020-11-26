import server.Server;

public class Main {
    
    public static void main(String[] args) {        
        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();
        server.ui();
    }
}
