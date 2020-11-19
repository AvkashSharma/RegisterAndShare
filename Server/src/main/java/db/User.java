package db;

public class User {
    String username;
    String ip;
    int socket;
    Database db = new Database();

    public User(String username, String ip, int socket){
        this.username = username;
        this.ip = ip;
        this.socket = socket;
    }
    public boolean exist(String Username){
        return db.userExist(username);
    }

}
