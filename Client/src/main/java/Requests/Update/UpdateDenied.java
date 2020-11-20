package Requests.Update;
import java.io.Serializable;
import Requests.Request;
import Requests.RequestType;
import java.net.InetSocketAddress;

public class UpdateDenied extends Request implements Serializable {
    InetSocketAddress clientSocketAddress;
    String clientName;
    String reason;

    public UpdateDenied(String clientName, InetSocketAddress clientSocketAddress, String reason){
      super(RequestType.UPDATE_DENIED);
      this.clientName = clientName;
      this.clientSocketAddress = clientSocketAddress;
      this.reason=reason;

    }
    public String getClientName() {
        return clientName;
    }
    @Override
    public String toString(){
        return RequestType.UPDATE_DENIED+ " "  + this.getRid()+" "+reason ;
    }

    public void print(){
        System.out.println(this.toString());
    }

}

