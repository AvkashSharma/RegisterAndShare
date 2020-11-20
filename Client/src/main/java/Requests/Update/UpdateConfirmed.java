package Requests.Update;
import java.io.Serializable;
import Requests.Request;
import Requests.RequestType;
import java.net.InetSocketAddress;



public class UpdateConfirmed extends Request implements Serializable {
     InetSocketAddress clientSocketAddress;
    String clientName;

    public UpdateConfirmed(String clientName, InetSocketAddress clientSocketAddress){
      super(RequestType.UPDATE_CONFIRMED);
      this.clientName = clientName;
      this.clientSocketAddress = clientSocketAddress;

    }
    
    @Override
    public String toString(){
        return RequestType.UPDATE_CONFIRMED+ " " + this.getRid()+" "+ clientName+" "+clientSocketAddress ;
    }

    public void print(){
        System.out.println(this.toString());
    }


}
