package Requests.Registration;
import java.io.Serializable;
import java.net.InetSocketAddress;

import Requests.Request;
import Requests.RequestType;

/*
Register Request
- Request #
- Unique Name
- Ip
- Socket
*/

public class ServerRegisterConfirmed extends Request implements Serializable {
    
    InetSocketAddress serverSocketAddress;
    String clientName;
    public ServerRegisterConfirmed( String clientName,  InetSocketAddress serverSocketAddress){
      super(RequestType.SERVER_REGISTER_CONFIRMED);
      this.clientName=clientName;
      this.serverSocketAddress=serverSocketAddress;
    }
    
    public String getClientName() {
        return clientName;
    }
    
    public InetSocketAddress getServerSocketAddress(){
      return serverSocketAddress;
    } 

    @Override
    public String toString(){
        return RequestType.SERVER_REGISTER_CONFIRMED+ " " + this.getRid()+ " "+ getClientName()+ " "+ getServerSocketAddress() ;
    }

    public void print(){
        System.out.println(this.toString());
    }


}
