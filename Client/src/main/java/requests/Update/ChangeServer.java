package requests.Update;
import java.io.Serializable;
import java.net.InetSocketAddress;
/*
  - Request # 
  - Ip
  - Socket #

  */

import requests.Request;
import requests.RequestType;

public class ChangeServer extends Request implements Serializable {

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
