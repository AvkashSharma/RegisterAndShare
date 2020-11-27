package server;

import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ServerData {
    public static String name = "";
    public static AtomicReference<String> address = new AtomicReference<String>("");
    public static AtomicInteger port = new AtomicInteger(0);
    public static AtomicBoolean isServing = new AtomicBoolean(true);
    public static AtomicInteger timeout = new AtomicInteger(60*1000);

    public static AtomicReference<String> addressB = new AtomicReference<String>("");
    public static AtomicInteger portB = new AtomicInteger(0);

    public static Timer timer = new Timer();
    public static int interval;

    public static final int setInterval(){
        if(interval == 1){
            timer.cancel();
        }
        return --interval;
    }
}
