package Requests.Registration;
import java.io.Serializable;
import Requests.Request;
import Requests.RequestType;
import java.net.InetSocketAddress;



public class Registered extends Request implements Serializable {
     InetSocketAddress clientSocketAddress;
    String clientName;

    public Registered(String clientName, InetSocketAddress clientSocketAddress){
      super(RequestType.REGISTERED);
      this.clientName = clientName;
      this.clientSocketAddress = clientSocketAddress;

    }
    public String getClientName() {
        return clientName;
    }
    @Override
    public String toString(){
        return RequestType.REGISTERED+ " " + clientName +" "+clientSocketAddress;
    }

    public void print(){
        System.out.println(this.toString());
    }


}
