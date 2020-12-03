package requests.Update;
import java.net.InetSocketAddress;
import requests.Request;
import requests.RequestType;

/*
  - Request # 
  - Ip address
  - Socket
  */

public class UpdateServer extends Request {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
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
