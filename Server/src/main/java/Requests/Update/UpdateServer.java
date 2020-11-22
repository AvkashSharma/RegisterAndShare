package Requests.Update;
import java.io.Serializable;
import java.net.InetSocketAddress;

import Requests.Request;
import Requests.RequestType;

/*
  - Request # 
  - Ip address
  - Socket
  */

public class UpdateServer extends Request implements Serializable{

    InetSocketAddress clientSocketAddress;
   
  
    public UpdateServer( InetSocketAddress clientSocketAddress) {
        super(RequestType.UPDATE_SERVER);
       
        this.clientSocketAddress = clientSocketAddress;
    }

  
    
    public InetSocketAddress getClientSocketAddress(){
        return clientSocketAddress;
    }

    @Override
    public String toString() {
        return RequestType.UPDATE_SERVER +" " + getClientSocketAddress();
    }

    public void print(){
        System.out.println(this.toString());
    }

    
}
