package db;

public class User {
    String username;
    String ip;
    int socket;

    public User(String username, String ip, int socket){
        this.username = username;
        this.ip = ip;
        this.socket = socket;
    }

    public String getUsername(){
        return username;
    }

    public String getUserIP(){
        return ip;
    }

    public int getUserSocket(){
        return socket;
    }

}
