package requests;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import client.Client;
import client.ClientData;
import requests.Registration.RegisterRequest;

public class Request implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    protected RequestType requestType;
    protected int rid;
    private transient Timer timer;

    public Request() {
        timer = new Timer();
    }

    public Request(RequestType requestType) {
        this.requestType = requestType;
        this.timer = new Timer();
    }

    public Request(RequestType requestType, int reqNumber) {
        this.requestType = requestType;
        this.rid = reqNumber;
        this.timer = new Timer();
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getRid() {
        return rid;
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
    }

    public void startTimer() {

        timer.scheduleAtFixedRate(new TimerTask() {
            int timePeriod = 3000;

            @Override
            public void run() {
                if (timePeriod <= 0) {
                    timer.cancel();
                    done();
                }
                timePeriod = timePeriod - 1000;
            }

        }, 10, 1000);
    } 

    public synchronized void done() {
        System.out.println("REQUEST TIMEOUT: " + this.toString());

        if (requestType == RequestType.REGISTER) {
            RegisterRequest req = (RegisterRequest) ClientData.requestMap.get(this.rid);
            RegisterRequest newreq = new RegisterRequest(this.rid, req.getClientName(), req.getAddress(),
                    req.getPort());

            if (ClientData.retryAttempt.get() < 3) {
                ClientData.retryAttempt.incrementAndGet();
                ClientData.requestMap.remove(this.rid);
                Client.register(newreq);
            } else {

                // print the size of the map
                // System.out.println("SIZE OF HASH MAP BEFORE: " + ClientData.requestMap.size());
                ClientData.getServerAddress(new Scanner(System.in));
                ClientData.retryAttempt.set(0);
                ClientData.requestMap.remove(this.rid);
            }
        }
    }

    public void stopTimer() {
        // System.out.println("STOP TIMER CALLED");
        timer.cancel();
    }
}
