package Requests.Update;
import java.io.Serializable;
import java.net.InetSocketAddress;
/*
  - Request # 
  - Ip
  - Socket #

  */

import Requests.Request;
import Requests.RequestType;

public class ChangeServer extends Request implements Serializable {
    
    InetSocketAddress clientSocketAddress;

    public ChangeServer(InetSocketAddress clientSocketAddress){

        super(RequestType.CHANGE_SERVER);
       
       this.clientSocketAddress=clientSocketAddress;

    }
    
     
     public InetSocketAddress getClientSocketAddress(){
        return clientSocketAddress;
    }
@Override
    public String toString() {
        return RequestType.CHANGE_SERVER + " "+ getClientSocketAddress()+ " " ;
    }

    public void print(){
        System.out.println(this.toString());
    }
}
