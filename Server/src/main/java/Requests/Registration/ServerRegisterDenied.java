package Requests.Registration;
import java.io.Serializable;
import java.net.InetSocketAddress;

import Requests.Request;
import Requests.RequestType;


/*
Register Request
- Request #
- Name
- Ip address
- Socket#
*/
public class ServerRegisterDenied extends Request implements Serializable {
   
    
    String clientName;
    InetSocketAddress ServerSocketAddress;

    public ServerRegisterDenied(String clientName, InetSocketAddress ServerSocketAddress){
      super(RequestType.SERVER_REGISTER_DENIED);
      
      this.clientName = clientName;
        this.ServerSocketAddress = ServerSocketAddress;

    }
    public String getName() {
        return clientName;
    }
     public InetSocketAddress getServerSocketAddress(){
      return ServerSocketAddress;
    }

    @Override
    public String toString(){
        return RequestType.SERVER_REGISTER_DENIED+ " "  + this.getRid()+" "+getName()+" "+getServerSocketAddress() ;
    }

    public void print(){
        System.out.println(this.toString());
    }

}

