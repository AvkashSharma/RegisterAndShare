package client;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ClientData {
    public static AtomicBoolean isRegistered = new AtomicBoolean(false);
    public static AtomicReference<String> username = new AtomicReference<String>("");
}
