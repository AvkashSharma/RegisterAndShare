package requests.Update;

import requests.Request;
import requests.RequestType;

/*
- Request # 
- Unique name
- Ip address
- Socket
  */

public class UpdateRequest extends Request  {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String address;
    private int port;
    String clientName;

    public UpdateRequest(int rqNumber, String clientName, String address, int port) {
        super(RequestType.UPDATE, rqNumber);
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
        return RequestType.UPDATE + " " + this.getRid() + " " + getClientName() + " " + getAddress() + ":"
                + getPort();
    }

    public void print(){
        System.out.println(this.toString());
    }
    
}
