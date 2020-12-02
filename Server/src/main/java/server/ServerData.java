package server;

import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ServerData {
    public static AtomicReference<String> address = new AtomicReference<String>("");
    public static AtomicInteger port = new AtomicInteger(0);
    public static AtomicBoolean isServing = new AtomicBoolean(true);
    public static AtomicInteger sleepTime = new AtomicInteger(60*1000);

    public static AtomicReference<String> addressB = new AtomicReference<String>("");
    public static AtomicInteger portB = new AtomicInteger(0);


    public static Timer activeTimer = new Timer();
    public static Timer inactiveTimer = new Timer();
    public static int activeInterval;
    public static int inactiveInterval;
    public static int timeout = 10;


    public static final int setInterval(){
        if(activeInterval == 1){
            activeTimer.cancel();
        }
        return --activeInterval;
    }
}
