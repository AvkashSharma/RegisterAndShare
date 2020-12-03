package requests.Update;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

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
    InetSocketAddress clientSocketAddress;
    String clientName;

    public UpdateRequest(int rqNumber, String clientName, String address, String port) {
        super(RequestType.UPDATE, rqNumber);
        this.clientName = clientName;
        try {
            this.clientSocketAddress = new InetSocketAddress(InetAddress.getByName(address.toString()),
                    Integer.parseInt(port.trim()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return clientName;
    }
    
    public InetSocketAddress getClientSocketAddress(){
        return clientSocketAddress;
    }

    @Override
    public String toString() {
        return RequestType.UPDATE + " " + this.getRid()+" "+ getClientName() + " " + getClientSocketAddress();
    }

    public void print(){
        System.out.println(this.toString());
    }

    
}
