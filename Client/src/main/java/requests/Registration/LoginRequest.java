package requests.Registration;

import java.io.Serializable;

import requests.Request;
import requests.RequestType;


public class LoginRequest extends Request implements Serializable{
    /**
     *
     */
    private String address;
    private int port;
    private String clientName;

    public LoginRequest(int rqNumber, String clientName, String address, int port) {
        super(RequestType.LOGIN, rqNumber);
        this.clientName = clientName;
        this.address = address;
        this.port = port;
    }

    public String getClientName() {
        return clientName;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return RequestType.LOGIN + " " + this.getRid() + " " + getClientName() + " " + getAddress() + ":"
                + getPort();
    }

    public void print() {
        System.out.println(this.toString());
    }
}
