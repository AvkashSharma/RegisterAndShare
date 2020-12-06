package requests.Update;

import requests.Request;
import requests.RequestType;
/*
- Request # 
- Ip
- Socket #

  */

public class ChangeServer extends Request {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String address;
    private int port;

    private String addressB;
    private int portB;

    public ChangeServer(String address, int port, String addressB, int portB) {
        super(RequestType.CHANGE_SERVER);
        this.address = address;
        this.port = port;
        this.addressB = addressB;
        this.portB = portB;
    }

    public int getPortB() {
        return portB;
    }

    public String getAddressB() {
        return addressB;
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return RequestType.CHANGE_SERVER + " " + getAddress() + ":" + getPort();
    }

    public void print() {
        System.out.println(this.toString());
    }

}
