package Requests.Update;
import java.io.Serializable;
import java.net.InetSocketAddress;

import Requests.Request;
import Requests.RequestType;

/*
- Request #
- Unique Name
- Ip Address
- Socket #
*/

public class UpdateConfirmed extends Request implements Serializable {
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
