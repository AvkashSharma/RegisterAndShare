package requests.Update;

import requests.Request;
import requests.RequestType;

/*
  - Request # 
  - Ip address
  - Socket
  */
/**
 * Update server's port number
 */
public class UpdateServer extends Request {

    private static final long serialVersionUID = 1L;
    private String address;
    private int port;

    public UpdateServer(String address, int port) {
        super(RequestType.UPDATE_SERVER);

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
        return RequestType.UPDATE_SERVER +" " + getAddress()+":"+getPort();
    }

    public void print(){
        System.out.println(this.toString());
    }
    
}
