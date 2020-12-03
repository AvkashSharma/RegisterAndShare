package requests.Update;
import java.net.InetSocketAddress;

import requests.Request;
import requests.RequestType;

/*
- Request #
- Unique Name
- Ip Address
- Socket #
*/

public class UpdateConfirmed extends Request  {
     /**
    *
    */
    private static final long serialVersionUID = 1L;
    InetSocketAddress clientSocketAddress;
    String clientName;

    public UpdateConfirmed(String clientName, InetSocketAddress clientSocketAddress){
      super(RequestType.UPDATE_CONFIRMED);
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
    public String toString(){
        return RequestType.UPDATE_CONFIRMED+ " " + this.getRid()+" "+ getClientSocketAddress()+" "+getClientSocketAddress() ;
    }

    public void print(){
        System.out.println(this.toString());
    }


}
