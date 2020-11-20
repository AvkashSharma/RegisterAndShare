package Requests.Update;
import java.io.Serializable;
import java.net.InetSocketAddress;

import Requests.Request;
import Requests.RequestType;



public class UpdateRequest extends Request implements Serializable{

    InetSocketAddress clientSocketAddress;
    String clientName;

    public UpdateRequest(String clientName, InetSocketAddress clientSocketAddress) {
        super(RequestType.UPDATE);
        this.clientName = clientName;
        this.clientSocketAddress = clientSocketAddress;
    }

    public String getClientName() {
        return clientName;
    }

    @Override
    public String toString() {
        return RequestType.UPDATE + " " + this.getRid()+" "+ clientName + " " + clientSocketAddress;
    }

    public void print(){
        System.out.println(this.toString());
    }

    
}
