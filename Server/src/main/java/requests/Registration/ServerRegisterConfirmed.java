package requests.Registration;
import requests.Request;
import requests.RequestType;
import java.net.InetSocketAddress;

/*
Register Request
- Request #
- Unique Name
- Ip
- Socket
*/

public class ServerRegisterConfirmed extends Request {
    
    /**
   *
   */
  private static final long serialVersionUID = 1L;
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
