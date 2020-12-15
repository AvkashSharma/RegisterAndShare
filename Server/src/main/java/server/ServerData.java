package server;

import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Contains information related to server configuration
 */
public class ServerData {
    /**
     * Server's Ip address
     * 
     * @SERVER
     */
    public static AtomicReference<String> address = new AtomicReference<String>("");
    /**
     * Server's Port number
     * 
     * @SERVER
     */
    public static AtomicInteger port = new AtomicInteger(0);
    /**
     * Server status ===> ONLINE = true, OFFLINE = false
     * 
     * @SERVER
     */
    public static AtomicBoolean isServing = new AtomicBoolean(true);
    /**
     * Server should sleep after this long
     * 
     * @SERVER
     */
    public static AtomicInteger sleepTime = new AtomicInteger(60 * 1000);

    /**
     * Backup Server ip address
     * 
     * @BACKUP-SERVER
     */
    public static AtomicReference<String> addressB = new AtomicReference<String>("");
    /**
     * Backup Server port number
     * 
     * @BACKUP-SERVER
     */
    public static AtomicInteger portB = new AtomicInteger(0);

    /**
     * Server's serverName
     * 
     * @SERVER
     */
    public static AtomicReference<String> serverName = new AtomicReference<String>("");

    /**
     * List of active requsts
     */
    public static ConcurrentHashMap<String,Object> requestMap = new ConcurrentHashMap<>();
    
    /**
     * Timer for activeness of server
     */
    public static Timer activeTimer = new Timer();
    /**
     * Timer for inactiveness of server
     */
    public static Timer inactiveTimer = new Timer();
    /**
     * Interval of how long server should be active in seconds
     */
    public static int activeInterval;
    /**
     * Interval of how long server should be inactive in seconds
     */
    public static int inactiveInterval;
    /**
     * Buffer added to the inactive Timer
     */
    public static int timeout = 10;
}
