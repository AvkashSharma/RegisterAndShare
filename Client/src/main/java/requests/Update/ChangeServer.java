package requests.Update;

import requests.Request;
import requests.RequestType;
/*
- Request # 
- Ip
- Socket #

  */

public class ChangeServer extends Request  {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String address;
    private int port;

    public ChangeServer(String address, int port) {
        super(RequestType.CHANGE_SERVER);
        this.address = address;
        this.port = port;
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
