package requests.Update;
import java.io.Serializable;
import java.net.InetSocketAddress;

import requests.Request;
import requests.RequestType;

/*
  - Request # 
  - Unique name
  - Ip address
  - Socket
  */

public class UpdateRequest extends Request implements Serializable{

    InetSocketAddress clientSocketAddress;
    String clientName;
  
    public UpdateRequest(String clientName, InetSocketAddress clientSocketAddress) {
        super(RequestType.UPDATE);
        this.clientName = clientName;
        this.clientSocketAddress = clientSocketAddress;
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
