package requests.Registration;
import java.io.Serializable;
import java.net.InetSocketAddress;

import requests.Request;
import requests.RequestType;


/*
Register Request
- Request #
- Unique name
- IP Address 
- Socket #
*/

public class RegisterRequest extends Request implements Serializable{

    InetSocketAddress clientSocketAddress;
    String clientName;

    public RegisterRequest(int rqNumber, String clientName, InetSocketAddress clientSocketAddress) {
        super(RequestType.REGISTER,rqNumber);
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
    public String toString() {
        return RequestType.REGISTER + " " + this.getRid()+" "+ getClientName() + " " + getClientSocketAddress();
    }

    public void print(){
        System.out.println(this.toString());
    }

    // TEST CODE
    // public static void main(String[] args) throws UnknownHostException {
    //     RegisterMessage rm = new RegisterMessage("rrr", new InetSocketAddress(InetAddress.getLocalHost(), 6000));

    //     System.out.println(rm.clientSocketAddress.getPort());

    // }

}
