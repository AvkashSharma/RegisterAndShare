package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Database instance that connects to AWS MYSQL database
 */
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
                    String.format("INSERT users(username, ip, socket, isActive) VALUES ('%s','%s',%s, %s)", username, ip, socket, 1));
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

    /**
     * Get user information
     * 
     * @param username
     * @return
     */
    public User getUser(String username) {
        try {
            User user;

            PreparedStatement ps = conn
                    .prepareStatement(String.format("SELECT * FROM users WHERE username ='%s'", username));
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                rs.first(); // moves cursor to the first row
                user = new User(rs.getString("username"), rs.getString("ip"), rs.getInt("socket"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Connection error");
        }
        return null;
    }

    // get List of all registered users
    public List<User> getUsers() {
        try {
            List<User> users = new ArrayList<User>();

            PreparedStatement ps = conn.prepareStatement("SELECT * from users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getString("username"), rs.getString("ip"), rs.getInt("socket")));
            }
            return users;
        } catch (SQLException e) {
            System.out.println("Connection error");
        }
        return null;
    }

    // delete user from database
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

    // update user information
    public boolean updateUser(String username, String ip, int socket, int isActive) {
        try {
            if (userExist(username)) {
                PreparedStatement ps = conn.prepareStatement(
                        String.format("UPDATE users SET ip='%s', socket=%s, isActive=%s WHERE username='%s'", ip, socket, isActive, username));
                ps.execute();
                return true;
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

        // update user information
        public boolean disconnectUser(String username, int isActive) {
            try {
                if (userExist(username)) {
                    PreparedStatement ps = conn.prepareStatement(
                            String.format("UPDATE users SET isActive=%s WHERE username='%s'", isActive, username));
                    ps.execute();
                    return true;
                }
    
            } catch (SQLException e) {
                System.out.println(e);
            }
            return false;
        }

    

    // check if subject exists
    public boolean subjectExist(String subject) {
        try {
            PreparedStatement ps = conn
                    .prepareStatement(String.format("SELECT * FROM subjects WHERE subject='%s'", subject));
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

    // get all available subjects
    public List<String> getSubjects() {
        try {
            List<String> subjects = new ArrayList<String>();

            PreparedStatement ps = conn.prepareStatement("SELECT subject FROM subjects");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                subjects.add(rs.getString("subject"));
            }
            return subjects;
        } catch (SQLException e) {
            System.out.println("Connection error");
        }
        return null;
    }

    // subscribe user to a subject
    public boolean addFavoriteSubject(String username, String subject) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    String.format("INSERT subscriptions(subject, username) VALUES('%s', '%s')", subject, username));
            ps.execute();
            return true;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // Add a user's favorite list of subject
    public void addFavoriteSubjects(String username, List<String> favoriteSubjects) {
        for (String subject : favoriteSubjects) {
            addFavoriteSubject(username, subject);
        }
    }

    /**
     * Remove a user's subscribed subject
     * 
     * @param username
     * @param subject
     * @return
     */
    public boolean removeAFavSubject(String username, String subject) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    String.format("DELETE FROM subscriptions WHERE username='%s' and subject='%s'", username, subject));
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // get list of all subscriptions for specified users
    public List<String> getFavoriteSubjects(String username) {
        try {
            List<String> subjects = new ArrayList<String>();

            PreparedStatement ps = conn
                    .prepareStatement(String.format("SELECT * FROM subscriptions where username = '%s'", username));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                subjects.add(rs.getString("subject"));
            }
            return subjects;
        } catch (SQLException e) {
            System.out.println("Connection error");
        }
        return null;
    }

    // get List of all users subscribed to a subject
    public List<User> getSubjectUsers(String subject) {
        try {
            List<User> users = new ArrayList<User>();

            PreparedStatement ps = conn.prepareStatement(String.format(
                    "SELECT users.* FROM subscriptions LEFT join users on subscriptions.username = users.username where subject = '%s' and isActive=1",
                    subject));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(rs.getString("username"), rs.getString("ip"), rs.getInt("socket")));
            }
            return users;
        } catch (SQLException e) {
            System.out.println("Connection error");
        }
        return null;
    }

    // user adds a message to the subject if they are subscribed to it
    public boolean addMessage(String username, String subject, String message) {
        try {
            PreparedStatement ps = conn.prepareStatement(String.format(
                    "INSERT messages(subject, username, message) values ('%s','%s','%s')", subject, username, message));
            ps.execute();
            return true;

        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    /**
     * Close Database connection to avoid many open connections
     */
    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
