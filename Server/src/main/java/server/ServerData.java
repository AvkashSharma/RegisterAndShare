package server;

import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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

    public static Timer activeTimer = new Timer();
    public static Timer inactiveTimer = new Timer();
    public static int activeInterval;
    public static int inactiveInterval;
    public static int timeout = 10;
}
