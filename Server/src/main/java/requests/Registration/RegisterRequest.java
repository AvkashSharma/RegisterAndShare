package requests.Registration;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import requests.Request;
import requests.RequestType;


/*
Register Request
- Request #
- Unique name
- IP Address 
- Socket #
*/

public class RegisterRequest extends Request implements Serializable {

    private String address;
    private int port;
    private String clientName;

    public RegisterRequest(int rqNumber, String clientName, String address, int port) {
        super(RequestType.REGISTER, rqNumber);
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
        return RequestType.REGISTER + " " + this.getRid() + " " + getClientName() + " " + getAddress() + ":"
                + getPort();
    }

    public void print() {
        System.out.println(this.toString());
    }

}