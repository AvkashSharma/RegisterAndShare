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

    public Database() {
        try {
            conn = DriverManager.getConnection(connectionUrl, "admin", "MEMRECOlZoGgkWceaWWJ");

        } catch (SQLException e) {
            System.out.println("Connection error");
        }
    }

    // add a user
    public boolean addUser(String username, String ip, int socket) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    String.format("INSERT users(username, ip, socket) VALUES ('%s','%s',%s)", username, ip, socket));
            ps.execute();
            return true;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // Verifie username exist in database
    public boolean userExist(String username) {
        try {
            PreparedStatement ps = conn
                    .prepareStatement(String.format("SELECT * FROM users WHERE username='%s'", username));
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                rs.last(); // moves cursor to the last row
                int size = rs.getRow();
                if (size != 0)
                    return true;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public void getUser(String username) {

    }

    public void getUserByIp(String ip) {

    }

    public void getUsers() {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString("username");
                String name = rs.getString("ip");
                String lastName = rs.getString("socket");

                System.out.println(username);
                // do something with the extracted data...
            }
        } catch (SQLException e) {
            System.out.println("Connection error");
            // handle the exception
        }
    }

    public boolean removeUser(String username) {
        try {
            PreparedStatement ps = conn
                    .prepareStatement(String.format("DELETE FROM users WHERE username='%s'", username));
            ps.execute();
            return userExist(username);

        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean updateUser(String username, String ip, int socket) {
        try {
            if (userExist(username)) {
                PreparedStatement ps = conn.prepareStatement(
                        String.format("UPDATE users SET ip='%s', socket=%s WHERE username='%s'", ip, socket, username));
                ps.execute();
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public void addFavoriteSubject(String username, String Subject) {

    }

    public void addFavoriteSubjects(String username, List<String> favoriteSubjects) {

    }

    public void favoriteSubjectExist(String username, String subject) {

    }

    public void getFavorites(String username) {

    }

    // user adds a message to the subject if they are subscribed to it
    public void addMessage(String username, String subject, String text) {

    }

    public void getUsersOfSubject(String subject) {

    }

    public void changeServerAddress(String serverID) {

    }

}
