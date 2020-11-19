package Requests.Registration;
import java.io.Serializable;
import Requests.Request;
import Requests.RequestType;
import java.net.InetSocketAddress;

public class RegisteredDenied extends Request implements Serializable {
    InetSocketAddress clientSocketAddress;
    String clientName;

    public RegisteredDenied(String clientName, InetSocketAddress clientSocketAddress){
      super(RequestType.REGISTERED_DENIED);
      this.clientName = clientName;
      this.clientSocketAddress = clientSocketAddress;

    }
    public String getClientName() {
        return clientName;
    }
    @Override
    public String toString(){
        return RequestType.REGISTERED_DENIED+ " " + clientName +" "+clientSocketAddress;
    }

    public void print(){
        System.out.println(this.toString());
    }

}
