package Requests.Registration;
import java.io.Serializable;
import java.net.InetSocketAddress;

import Requests.Request;
import Requests.RequestType;


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

    public RegisterRequest(String clientName, InetSocketAddress clientSocketAddress) {
        super(RequestType.REGISTER);
        this.clientName = clientName;
        this.clientSocketAddress = clientSocketAddress;
    }

    public String getClientName() {
        return clientName;
    }

    @Override
    public String toString() {
        return RequestType.REGISTER + " " + clientName + " " + clientSocketAddress;
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
