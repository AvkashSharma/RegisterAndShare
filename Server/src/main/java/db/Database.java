package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Database {
    
    final String connectionUrl = "jdbc:mysql://rss-db.cn1qcrpwdo7e.us-east-2.rds.amazonaws.com:3306/rss-db";
    Connection conn;

    public Database(){
        try{
           conn  = DriverManager.getConnection(connectionUrl, "admin", "MEMRECOlZoGgkWceaWWJ");

        }
        catch(SQLException e)
        {
            System.out.println("Connection error");
            System.err.println();
            // handle the exception
        }
    }

    public void addUser(String username, ){

    }
    public boolean userExist(){
        return true;
    }
    public void getUsers(){
        try{
            String sqlSelectAllPersons = "SELECT * FROM users";
            PreparedStatement ps = conn.prepareStatement(sqlSelectAllPersons);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String name = rs.getString("ip");
                String lastName = rs.getString("socket");
    
                System.out.println(username);
                // do something with the extracted data...
            }
        }catch(SQLException e)
        {
            System.out.println("Connection error");
            System.err.println();
            // handle the exception
        }
    }

    public void removeUser(){

    }

    public void updateUser(){

    }

    public void addFavoriteSubject(String username, String Subject){

    }
    public void addFavoriteSubjects(String username, List<String> favoriteSubjects){

    }

    public void favoriteSubjectExist(String username, String subject){

    }

    public void getFavorites(String username){
        
    }

    //user adds a message to the subject if they are subscribed to it
    public void addMessage(String username, String subject, String text){

    }

    public void getUsersOfSubject(String subject){

    }

    public void changeServerAddress(String serverID){

    }

    
}
