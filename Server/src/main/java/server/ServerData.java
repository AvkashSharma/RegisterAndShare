package server;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ServerData {
    public static String name = "";
    public static AtomicReference<String> address = new AtomicReference<String>("");
    public static AtomicInteger port = new AtomicInteger(0);
    public static AtomicBoolean active = new AtomicBoolean(true);
    public static AtomicInteger timeout = new AtomicInteger(60*1000);

    public static AtomicReference<String> addressB = new AtomicReference<String>("");
    public static AtomicInteger portB = new AtomicInteger(0);
}
